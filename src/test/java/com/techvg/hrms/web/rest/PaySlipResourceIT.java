package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.PaySlip;
import com.techvg.hrms.repository.PaySlipRepository;
import com.techvg.hrms.service.criteria.PaySlipCriteria;
import com.techvg.hrms.service.dto.PaySlipDTO;
import com.techvg.hrms.service.mapper.PaySlipMapper;
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
 * Integration tests for the {@link PaySlipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaySlipResourceIT {

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;
    private static final Double SMALLER_SALARY = 1D - 1D;

    private static final Long DEFAULT_BRANCH_ID = 1L;
    private static final Long UPDATED_BRANCH_ID = 2L;
    private static final Long SMALLER_BRANCH_ID = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/pay-slips";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaySlipRepository paySlipRepository;

    @Autowired
    private PaySlipMapper paySlipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaySlipMockMvc;

    private PaySlip paySlip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaySlip createEntity(EntityManager em) {
        PaySlip paySlip = new PaySlip()
            .month(DEFAULT_MONTH)
            .salary(DEFAULT_SALARY)
            .branchId(DEFAULT_BRANCH_ID)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return paySlip;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaySlip createUpdatedEntity(EntityManager em) {
        PaySlip paySlip = new PaySlip()
            .month(UPDATED_MONTH)
            .salary(UPDATED_SALARY)
            .branchId(UPDATED_BRANCH_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return paySlip;
    }

    @BeforeEach
    public void initTest() {
        paySlip = createEntity(em);
    }

    @Test
    @Transactional
    void createPaySlip() throws Exception {
        int databaseSizeBeforeCreate = paySlipRepository.findAll().size();
        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);
        restPaySlipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paySlipDTO)))
            .andExpect(status().isCreated());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeCreate + 1);
        PaySlip testPaySlip = paySlipList.get(paySlipList.size() - 1);
        assertThat(testPaySlip.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPaySlip.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testPaySlip.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testPaySlip.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaySlip.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testPaySlip.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPaySlip.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPaySlip.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPaySlipWithExistingId() throws Exception {
        // Create the PaySlip with an existing ID
        paySlip.setId(1L);
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        int databaseSizeBeforeCreate = paySlipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaySlipMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paySlipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaySlips() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList
        restPaySlipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paySlip.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPaySlip() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get the paySlip
        restPaySlipMockMvc
            .perform(get(ENTITY_API_URL_ID, paySlip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paySlip.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPaySlipsByIdFiltering() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        Long id = paySlip.getId();

        defaultPaySlipShouldBeFound("id.equals=" + id);
        defaultPaySlipShouldNotBeFound("id.notEquals=" + id);

        defaultPaySlipShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaySlipShouldNotBeFound("id.greaterThan=" + id);

        defaultPaySlipShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaySlipShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaySlipsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where month equals to DEFAULT_MONTH
        defaultPaySlipShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the paySlipList where month equals to UPDATED_MONTH
        defaultPaySlipShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPaySlipsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultPaySlipShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the paySlipList where month equals to UPDATED_MONTH
        defaultPaySlipShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPaySlipsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where month is not null
        defaultPaySlipShouldBeFound("month.specified=true");

        // Get all the paySlipList where month is null
        defaultPaySlipShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByMonthContainsSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where month contains DEFAULT_MONTH
        defaultPaySlipShouldBeFound("month.contains=" + DEFAULT_MONTH);

        // Get all the paySlipList where month contains UPDATED_MONTH
        defaultPaySlipShouldNotBeFound("month.contains=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPaySlipsByMonthNotContainsSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where month does not contain DEFAULT_MONTH
        defaultPaySlipShouldNotBeFound("month.doesNotContain=" + DEFAULT_MONTH);

        // Get all the paySlipList where month does not contain UPDATED_MONTH
        defaultPaySlipShouldBeFound("month.doesNotContain=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary equals to DEFAULT_SALARY
        defaultPaySlipShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the paySlipList where salary equals to UPDATED_SALARY
        defaultPaySlipShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultPaySlipShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the paySlipList where salary equals to UPDATED_SALARY
        defaultPaySlipShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary is not null
        defaultPaySlipShouldBeFound("salary.specified=true");

        // Get all the paySlipList where salary is null
        defaultPaySlipShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary is greater than or equal to DEFAULT_SALARY
        defaultPaySlipShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

        // Get all the paySlipList where salary is greater than or equal to UPDATED_SALARY
        defaultPaySlipShouldNotBeFound("salary.greaterThanOrEqual=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary is less than or equal to DEFAULT_SALARY
        defaultPaySlipShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

        // Get all the paySlipList where salary is less than or equal to SMALLER_SALARY
        defaultPaySlipShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary is less than DEFAULT_SALARY
        defaultPaySlipShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

        // Get all the paySlipList where salary is less than UPDATED_SALARY
        defaultPaySlipShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllPaySlipsBySalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where salary is greater than DEFAULT_SALARY
        defaultPaySlipShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

        // Get all the paySlipList where salary is greater than SMALLER_SALARY
        defaultPaySlipShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId equals to DEFAULT_BRANCH_ID
        defaultPaySlipShouldBeFound("branchId.equals=" + DEFAULT_BRANCH_ID);

        // Get all the paySlipList where branchId equals to UPDATED_BRANCH_ID
        defaultPaySlipShouldNotBeFound("branchId.equals=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId in DEFAULT_BRANCH_ID or UPDATED_BRANCH_ID
        defaultPaySlipShouldBeFound("branchId.in=" + DEFAULT_BRANCH_ID + "," + UPDATED_BRANCH_ID);

        // Get all the paySlipList where branchId equals to UPDATED_BRANCH_ID
        defaultPaySlipShouldNotBeFound("branchId.in=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId is not null
        defaultPaySlipShouldBeFound("branchId.specified=true");

        // Get all the paySlipList where branchId is null
        defaultPaySlipShouldNotBeFound("branchId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId is greater than or equal to DEFAULT_BRANCH_ID
        defaultPaySlipShouldBeFound("branchId.greaterThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the paySlipList where branchId is greater than or equal to UPDATED_BRANCH_ID
        defaultPaySlipShouldNotBeFound("branchId.greaterThanOrEqual=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId is less than or equal to DEFAULT_BRANCH_ID
        defaultPaySlipShouldBeFound("branchId.lessThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the paySlipList where branchId is less than or equal to SMALLER_BRANCH_ID
        defaultPaySlipShouldNotBeFound("branchId.lessThanOrEqual=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId is less than DEFAULT_BRANCH_ID
        defaultPaySlipShouldNotBeFound("branchId.lessThan=" + DEFAULT_BRANCH_ID);

        // Get all the paySlipList where branchId is less than UPDATED_BRANCH_ID
        defaultPaySlipShouldBeFound("branchId.lessThan=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByBranchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where branchId is greater than DEFAULT_BRANCH_ID
        defaultPaySlipShouldNotBeFound("branchId.greaterThan=" + DEFAULT_BRANCH_ID);

        // Get all the paySlipList where branchId is greater than SMALLER_BRANCH_ID
        defaultPaySlipShouldBeFound("branchId.greaterThan=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where status equals to DEFAULT_STATUS
        defaultPaySlipShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the paySlipList where status equals to UPDATED_STATUS
        defaultPaySlipShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaySlipsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPaySlipShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the paySlipList where status equals to UPDATED_STATUS
        defaultPaySlipShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaySlipsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where status is not null
        defaultPaySlipShouldBeFound("status.specified=true");

        // Get all the paySlipList where status is null
        defaultPaySlipShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByStatusContainsSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where status contains DEFAULT_STATUS
        defaultPaySlipShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the paySlipList where status contains UPDATED_STATUS
        defaultPaySlipShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaySlipsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where status does not contain DEFAULT_STATUS
        defaultPaySlipShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the paySlipList where status does not contain UPDATED_STATUS
        defaultPaySlipShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultPaySlipShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the paySlipList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPaySlipShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultPaySlipShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the paySlipList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPaySlipShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId is not null
        defaultPaySlipShouldBeFound("employeeId.specified=true");

        // Get all the paySlipList where employeeId is null
        defaultPaySlipShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultPaySlipShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the paySlipList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultPaySlipShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultPaySlipShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the paySlipList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultPaySlipShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultPaySlipShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the paySlipList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultPaySlipShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultPaySlipShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the paySlipList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultPaySlipShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId equals to DEFAULT_COMPANY_ID
        defaultPaySlipShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the paySlipList where companyId equals to UPDATED_COMPANY_ID
        defaultPaySlipShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultPaySlipShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the paySlipList where companyId equals to UPDATED_COMPANY_ID
        defaultPaySlipShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId is not null
        defaultPaySlipShouldBeFound("companyId.specified=true");

        // Get all the paySlipList where companyId is null
        defaultPaySlipShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultPaySlipShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the paySlipList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultPaySlipShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultPaySlipShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the paySlipList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultPaySlipShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId is less than DEFAULT_COMPANY_ID
        defaultPaySlipShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the paySlipList where companyId is less than UPDATED_COMPANY_ID
        defaultPaySlipShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where companyId is greater than DEFAULT_COMPANY_ID
        defaultPaySlipShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the paySlipList where companyId is greater than SMALLER_COMPANY_ID
        defaultPaySlipShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPaySlipShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the paySlipList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPaySlipShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPaySlipShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the paySlipList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPaySlipShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModified is not null
        defaultPaySlipShouldBeFound("lastModified.specified=true");

        // Get all the paySlipList where lastModified is null
        defaultPaySlipShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPaySlipShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the paySlipList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPaySlipShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPaySlipShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the paySlipList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPaySlipShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModifiedBy is not null
        defaultPaySlipShouldBeFound("lastModifiedBy.specified=true");

        // Get all the paySlipList where lastModifiedBy is null
        defaultPaySlipShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPaySlipShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the paySlipList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPaySlipShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPaySlipsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        // Get all the paySlipList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPaySlipShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the paySlipList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPaySlipShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaySlipShouldBeFound(String filter) throws Exception {
        restPaySlipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paySlip.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPaySlipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaySlipShouldNotBeFound(String filter) throws Exception {
        restPaySlipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaySlipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPaySlip() throws Exception {
        // Get the paySlip
        restPaySlipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaySlip() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();

        // Update the paySlip
        PaySlip updatedPaySlip = paySlipRepository.findById(paySlip.getId()).get();
        // Disconnect from session so that the updates on updatedPaySlip are not directly saved in db
        em.detach(updatedPaySlip);
        updatedPaySlip
            .month(UPDATED_MONTH)
            .salary(UPDATED_SALARY)
            .branchId(UPDATED_BRANCH_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(updatedPaySlip);

        restPaySlipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paySlipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paySlipDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
        PaySlip testPaySlip = paySlipList.get(paySlipList.size() - 1);
        assertThat(testPaySlip.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPaySlip.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testPaySlip.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testPaySlip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaySlip.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPaySlip.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPaySlip.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPaySlip.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPaySlip() throws Exception {
        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();
        paySlip.setId(count.incrementAndGet());

        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaySlipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paySlipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paySlipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaySlip() throws Exception {
        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();
        paySlip.setId(count.incrementAndGet());

        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaySlipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paySlipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaySlip() throws Exception {
        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();
        paySlip.setId(count.incrementAndGet());

        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaySlipMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paySlipDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaySlipWithPatch() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();

        // Update the paySlip using partial update
        PaySlip partialUpdatedPaySlip = new PaySlip();
        partialUpdatedPaySlip.setId(paySlip.getId());

        partialUpdatedPaySlip
            .salary(UPDATED_SALARY)
            .branchId(UPDATED_BRANCH_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
           // .lastModified(UPDATED_LAST_MODIFIED);

        restPaySlipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaySlip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaySlip))
            )
            .andExpect(status().isOk());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
        PaySlip testPaySlip = paySlipList.get(paySlipList.size() - 1);
        assertThat(testPaySlip.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPaySlip.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testPaySlip.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testPaySlip.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaySlip.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPaySlip.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPaySlip.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPaySlip.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePaySlipWithPatch() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();

        // Update the paySlip using partial update
        PaySlip partialUpdatedPaySlip = new PaySlip();
        partialUpdatedPaySlip.setId(paySlip.getId());

        partialUpdatedPaySlip
            .month(UPDATED_MONTH)
            .salary(UPDATED_SALARY)
            .branchId(UPDATED_BRANCH_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPaySlipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaySlip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaySlip))
            )
            .andExpect(status().isOk());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
        PaySlip testPaySlip = paySlipList.get(paySlipList.size() - 1);
        assertThat(testPaySlip.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPaySlip.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testPaySlip.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testPaySlip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaySlip.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPaySlip.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPaySlip.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPaySlip.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPaySlip() throws Exception {
        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();
        paySlip.setId(count.incrementAndGet());

        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaySlipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paySlipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paySlipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaySlip() throws Exception {
        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();
        paySlip.setId(count.incrementAndGet());

        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaySlipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paySlipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaySlip() throws Exception {
        int databaseSizeBeforeUpdate = paySlipRepository.findAll().size();
        paySlip.setId(count.incrementAndGet());

        // Create the PaySlip
        PaySlipDTO paySlipDTO = paySlipMapper.toDto(paySlip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaySlipMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paySlipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaySlip in the database
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaySlip() throws Exception {
        // Initialize the database
        paySlipRepository.saveAndFlush(paySlip);

        int databaseSizeBeforeDelete = paySlipRepository.findAll().size();

        // Delete the paySlip
        restPaySlipMockMvc
            .perform(delete(ENTITY_API_URL_ID, paySlip.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaySlip> paySlipList = paySlipRepository.findAll();
        assertThat(paySlipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
