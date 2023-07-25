package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Salary;
import com.techvg.hrms.repository.SalaryRepository;
import com.techvg.hrms.service.criteria.SalaryCriteria;
import com.techvg.hrms.service.dto.SalaryDTO;
import com.techvg.hrms.service.mapper.SalaryMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SalaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalaryResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final Boolean DEFAULT_ISDEDUCTION = false;
    private static final Boolean UPDATED_ISDEDUCTION = true;

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/salaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalaryMockMvc;

    private Salary salary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salary createEntity(EntityManager em) {
        Salary salary = new Salary()
            .type(DEFAULT_TYPE)
            .amount(DEFAULT_AMOUNT)
            .isdeduction(DEFAULT_ISDEDUCTION)
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return salary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salary createUpdatedEntity(EntityManager em) {
        Salary salary = new Salary()
            .type(UPDATED_TYPE)
            .amount(UPDATED_AMOUNT)
            .isdeduction(UPDATED_ISDEDUCTION)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return salary;
    }

    @BeforeEach
    public void initTest() {
        salary = createEntity(em);
    }

    @Test
    @Transactional
    void createSalary() throws Exception {
        int databaseSizeBeforeCreate = salaryRepository.findAll().size();
        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);
        restSalaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeCreate + 1);
        Salary testSalary = salaryList.get(salaryList.size() - 1);
        assertThat(testSalary.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSalary.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSalary.getIsdeduction()).isEqualTo(DEFAULT_ISDEDUCTION);
        assertThat(testSalary.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testSalary.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSalary.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalary.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testSalary.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testSalary.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSalary.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createSalaryWithExistingId() throws Exception {
        // Create the Salary with an existing ID
        salary.setId(1L);
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        int databaseSizeBeforeCreate = salaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalaries() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList
        restSalaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salary.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isdeduction").value(hasItem(DEFAULT_ISDEDUCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get the salary
        restSalaryMockMvc
            .perform(get(ENTITY_API_URL_ID, salary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salary.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.isdeduction").value(DEFAULT_ISDEDUCTION.booleanValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getSalariesByIdFiltering() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        Long id = salary.getId();

        defaultSalaryShouldBeFound("id.equals=" + id);
        defaultSalaryShouldNotBeFound("id.notEquals=" + id);

        defaultSalaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSalaryShouldNotBeFound("id.greaterThan=" + id);

        defaultSalaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSalaryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalariesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where type equals to DEFAULT_TYPE
        defaultSalaryShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the salaryList where type equals to UPDATED_TYPE
        defaultSalaryShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSalariesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSalaryShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the salaryList where type equals to UPDATED_TYPE
        defaultSalaryShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSalariesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where type is not null
        defaultSalaryShouldBeFound("type.specified=true");

        // Get all the salaryList where type is null
        defaultSalaryShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByTypeContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where type contains DEFAULT_TYPE
        defaultSalaryShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the salaryList where type contains UPDATED_TYPE
        defaultSalaryShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSalariesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where type does not contain DEFAULT_TYPE
        defaultSalaryShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the salaryList where type does not contain UPDATED_TYPE
        defaultSalaryShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount equals to DEFAULT_AMOUNT
        defaultSalaryShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the salaryList where amount equals to UPDATED_AMOUNT
        defaultSalaryShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultSalaryShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the salaryList where amount equals to UPDATED_AMOUNT
        defaultSalaryShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount is not null
        defaultSalaryShouldBeFound("amount.specified=true");

        // Get all the salaryList where amount is null
        defaultSalaryShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultSalaryShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the salaryList where amount is greater than or equal to UPDATED_AMOUNT
        defaultSalaryShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount is less than or equal to DEFAULT_AMOUNT
        defaultSalaryShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the salaryList where amount is less than or equal to SMALLER_AMOUNT
        defaultSalaryShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount is less than DEFAULT_AMOUNT
        defaultSalaryShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the salaryList where amount is less than UPDATED_AMOUNT
        defaultSalaryShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalariesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where amount is greater than DEFAULT_AMOUNT
        defaultSalaryShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the salaryList where amount is greater than SMALLER_AMOUNT
        defaultSalaryShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalariesByIsdeductionIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where isdeduction equals to DEFAULT_ISDEDUCTION
        defaultSalaryShouldBeFound("isdeduction.equals=" + DEFAULT_ISDEDUCTION);

        // Get all the salaryList where isdeduction equals to UPDATED_ISDEDUCTION
        defaultSalaryShouldNotBeFound("isdeduction.equals=" + UPDATED_ISDEDUCTION);
    }

    @Test
    @Transactional
    void getAllSalariesByIsdeductionIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where isdeduction in DEFAULT_ISDEDUCTION or UPDATED_ISDEDUCTION
        defaultSalaryShouldBeFound("isdeduction.in=" + DEFAULT_ISDEDUCTION + "," + UPDATED_ISDEDUCTION);

        // Get all the salaryList where isdeduction equals to UPDATED_ISDEDUCTION
        defaultSalaryShouldNotBeFound("isdeduction.in=" + UPDATED_ISDEDUCTION);
    }

    @Test
    @Transactional
    void getAllSalariesByIsdeductionIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where isdeduction is not null
        defaultSalaryShouldBeFound("isdeduction.specified=true");

        // Get all the salaryList where isdeduction is null
        defaultSalaryShouldNotBeFound("isdeduction.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where month equals to DEFAULT_MONTH
        defaultSalaryShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the salaryList where month equals to UPDATED_MONTH
        defaultSalaryShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllSalariesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultSalaryShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the salaryList where month equals to UPDATED_MONTH
        defaultSalaryShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllSalariesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where month is not null
        defaultSalaryShouldBeFound("month.specified=true");

        // Get all the salaryList where month is null
        defaultSalaryShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByMonthContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where month contains DEFAULT_MONTH
        defaultSalaryShouldBeFound("month.contains=" + DEFAULT_MONTH);

        // Get all the salaryList where month contains UPDATED_MONTH
        defaultSalaryShouldNotBeFound("month.contains=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllSalariesByMonthNotContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where month does not contain DEFAULT_MONTH
        defaultSalaryShouldNotBeFound("month.doesNotContain=" + DEFAULT_MONTH);

        // Get all the salaryList where month does not contain UPDATED_MONTH
        defaultSalaryShouldBeFound("month.doesNotContain=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllSalariesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where year equals to DEFAULT_YEAR
        defaultSalaryShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the salaryList where year equals to UPDATED_YEAR
        defaultSalaryShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSalariesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSalaryShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the salaryList where year equals to UPDATED_YEAR
        defaultSalaryShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSalariesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where year is not null
        defaultSalaryShouldBeFound("year.specified=true");

        // Get all the salaryList where year is null
        defaultSalaryShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByYearContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where year contains DEFAULT_YEAR
        defaultSalaryShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the salaryList where year contains UPDATED_YEAR
        defaultSalaryShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSalariesByYearNotContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where year does not contain DEFAULT_YEAR
        defaultSalaryShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the salaryList where year does not contain UPDATED_YEAR
        defaultSalaryShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSalariesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where status equals to DEFAULT_STATUS
        defaultSalaryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the salaryList where status equals to UPDATED_STATUS
        defaultSalaryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalariesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSalaryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the salaryList where status equals to UPDATED_STATUS
        defaultSalaryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalariesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where status is not null
        defaultSalaryShouldBeFound("status.specified=true");

        // Get all the salaryList where status is null
        defaultSalaryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByStatusContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where status contains DEFAULT_STATUS
        defaultSalaryShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the salaryList where status contains UPDATED_STATUS
        defaultSalaryShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalariesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where status does not contain DEFAULT_STATUS
        defaultSalaryShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the salaryList where status does not contain UPDATED_STATUS
        defaultSalaryShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultSalaryShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the salaryList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultSalaryShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultSalaryShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the salaryList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultSalaryShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId is not null
        defaultSalaryShouldBeFound("employeeId.specified=true");

        // Get all the salaryList where employeeId is null
        defaultSalaryShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultSalaryShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the salaryList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultSalaryShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultSalaryShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the salaryList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultSalaryShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultSalaryShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the salaryList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultSalaryShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultSalaryShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the salaryList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultSalaryShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId equals to DEFAULT_COMPANY_ID
        defaultSalaryShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the salaryList where companyId equals to UPDATED_COMPANY_ID
        defaultSalaryShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultSalaryShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the salaryList where companyId equals to UPDATED_COMPANY_ID
        defaultSalaryShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId is not null
        defaultSalaryShouldBeFound("companyId.specified=true");

        // Get all the salaryList where companyId is null
        defaultSalaryShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultSalaryShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the salaryList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultSalaryShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultSalaryShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the salaryList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultSalaryShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId is less than DEFAULT_COMPANY_ID
        defaultSalaryShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the salaryList where companyId is less than UPDATED_COMPANY_ID
        defaultSalaryShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where companyId is greater than DEFAULT_COMPANY_ID
        defaultSalaryShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the salaryList where companyId is greater than SMALLER_COMPANY_ID
        defaultSalaryShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSalaryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the salaryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSalaryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSalaryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the salaryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSalaryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModified is not null
        defaultSalaryShouldBeFound("lastModified.specified=true");

        // Get all the salaryList where lastModified is null
        defaultSalaryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSalaryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salaryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSalaryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSalaryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the salaryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSalaryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModifiedBy is not null
        defaultSalaryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the salaryList where lastModifiedBy is null
        defaultSalaryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSalaryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salaryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSalaryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalariesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSalaryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salaryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSalaryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalaryShouldBeFound(String filter) throws Exception {
        restSalaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salary.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isdeduction").value(hasItem(DEFAULT_ISDEDUCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSalaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalaryShouldNotBeFound(String filter) throws Exception {
        restSalaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalaryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalary() throws Exception {
        // Get the salary
        restSalaryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();

        // Update the salary
        Salary updatedSalary = salaryRepository.findById(salary.getId()).get();
        // Disconnect from session so that the updates on updatedSalary are not directly saved in db
        em.detach(updatedSalary);
        updatedSalary
            .type(UPDATED_TYPE)
            .amount(UPDATED_AMOUNT)
            .isdeduction(UPDATED_ISDEDUCTION)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        SalaryDTO salaryDTO = salaryMapper.toDto(updatedSalary);

        restSalaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salaryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salaryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
        Salary testSalary = salaryList.get(salaryList.size() - 1);
        assertThat(testSalary.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSalary.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSalary.getIsdeduction()).isEqualTo(UPDATED_ISDEDUCTION);
        assertThat(testSalary.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSalary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSalary.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalary.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testSalary.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSalary.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalary.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();
        salary.setId(count.incrementAndGet());

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salaryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();
        salary.setId(count.incrementAndGet());

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();
        salary.setId(count.incrementAndGet());

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalaryWithPatch() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();

        // Update the salary using partial update
        Salary partialUpdatedSalary = new Salary();
        partialUpdatedSalary.setId(salary.getId());

        partialUpdatedSalary
            .amount(UPDATED_AMOUNT)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR);
//            .employeeId(UPDATED_EMPLOYEE_ID)
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSalaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalary))
            )
            .andExpect(status().isOk());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
        Salary testSalary = salaryList.get(salaryList.size() - 1);
        assertThat(testSalary.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSalary.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSalary.getIsdeduction()).isEqualTo(DEFAULT_ISDEDUCTION);
        assertThat(testSalary.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSalary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSalary.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalary.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testSalary.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSalary.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSalary.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateSalaryWithPatch() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();

        // Update the salary using partial update
        Salary partialUpdatedSalary = new Salary();
        partialUpdatedSalary.setId(salary.getId());

        partialUpdatedSalary
            .type(UPDATED_TYPE)
            .amount(UPDATED_AMOUNT)
            .isdeduction(UPDATED_ISDEDUCTION)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSalaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalary))
            )
            .andExpect(status().isOk());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
        Salary testSalary = salaryList.get(salaryList.size() - 1);
        assertThat(testSalary.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSalary.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSalary.getIsdeduction()).isEqualTo(UPDATED_ISDEDUCTION);
        assertThat(testSalary.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSalary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSalary.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalary.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testSalary.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSalary.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalary.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();
        salary.setId(count.incrementAndGet());

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salaryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();
        salary.setId(count.incrementAndGet());

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();
        salary.setId(count.incrementAndGet());

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(salaryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        int databaseSizeBeforeDelete = salaryRepository.findAll().size();

        // Delete the salary
        restSalaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, salary.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
