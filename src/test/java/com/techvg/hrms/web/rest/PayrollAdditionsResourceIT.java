package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.PayrollAdditions;
import com.techvg.hrms.repository.PayrollAdditionsRepository;
import com.techvg.hrms.service.criteria.PayrollAdditionsCriteria;
import com.techvg.hrms.service.dto.PayrollAdditionsDTO;
import com.techvg.hrms.service.mapper.PayrollAdditionsMapper;
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
 * Integration tests for the {@link PayrollAdditionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayrollAdditionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_UNIT_CAL = false;
    private static final Boolean UPDATED_HAS_UNIT_CAL = true;

    private static final Double DEFAULT_UNIT_AMOUNT = 1D;
    private static final Double UPDATED_UNIT_AMOUNT = 2D;
    private static final Double SMALLER_UNIT_AMOUNT = 1D - 1D;

    private static final String DEFAULT_ASSIGN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGN_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/payroll-additions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayrollAdditionsRepository payrollAdditionsRepository;

    @Autowired
    private PayrollAdditionsMapper payrollAdditionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayrollAdditionsMockMvc;

    private PayrollAdditions payrollAdditions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollAdditions createEntity(EntityManager em) {
        PayrollAdditions payrollAdditions = new PayrollAdditions()
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY)
            .hasUnitCal(DEFAULT_HAS_UNIT_CAL)
            .unitAmount(DEFAULT_UNIT_AMOUNT)
            .assignType(DEFAULT_ASSIGN_TYPE)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
           // .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return payrollAdditions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollAdditions createUpdatedEntity(EntityManager em) {
        PayrollAdditions payrollAdditions = new PayrollAdditions()
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .assignType(UPDATED_ASSIGN_TYPE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return payrollAdditions;
    }

    @BeforeEach
    public void initTest() {
        payrollAdditions = createEntity(em);
    }

    @Test
    @Transactional
    void createPayrollAdditions() throws Exception {
        int databaseSizeBeforeCreate = payrollAdditionsRepository.findAll().size();
        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);
        restPayrollAdditionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollAdditions testPayrollAdditions = payrollAdditionsList.get(payrollAdditionsList.size() - 1);
        assertThat(testPayrollAdditions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayrollAdditions.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPayrollAdditions.getHasUnitCal()).isEqualTo(DEFAULT_HAS_UNIT_CAL);
        assertThat(testPayrollAdditions.getUnitAmount()).isEqualTo(DEFAULT_UNIT_AMOUNT);
        assertThat(testPayrollAdditions.getAssignType()).isEqualTo(DEFAULT_ASSIGN_TYPE);
        assertThat(testPayrollAdditions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayrollAdditions.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testPayrollAdditions.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPayrollAdditions.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPayrollAdditions.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPayrollAdditionsWithExistingId() throws Exception {
        // Create the PayrollAdditions with an existing ID
        payrollAdditions.setId(1L);
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        int databaseSizeBeforeCreate = payrollAdditionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollAdditionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayrollAdditions() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList
        restPayrollAdditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollAdditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].hasUnitCal").value(hasItem(DEFAULT_HAS_UNIT_CAL.booleanValue())))
            .andExpect(jsonPath("$.[*].unitAmount").value(hasItem(DEFAULT_UNIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].assignType").value(hasItem(DEFAULT_ASSIGN_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPayrollAdditions() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get the payrollAdditions
        restPayrollAdditionsMockMvc
            .perform(get(ENTITY_API_URL_ID, payrollAdditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payrollAdditions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.hasUnitCal").value(DEFAULT_HAS_UNIT_CAL.booleanValue()))
            .andExpect(jsonPath("$.unitAmount").value(DEFAULT_UNIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.assignType").value(DEFAULT_ASSIGN_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPayrollAdditionsByIdFiltering() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        Long id = payrollAdditions.getId();

        defaultPayrollAdditionsShouldBeFound("id.equals=" + id);
        defaultPayrollAdditionsShouldNotBeFound("id.notEquals=" + id);

        defaultPayrollAdditionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPayrollAdditionsShouldNotBeFound("id.greaterThan=" + id);

        defaultPayrollAdditionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPayrollAdditionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where name equals to DEFAULT_NAME
        defaultPayrollAdditionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the payrollAdditionsList where name equals to UPDATED_NAME
        defaultPayrollAdditionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPayrollAdditionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the payrollAdditionsList where name equals to UPDATED_NAME
        defaultPayrollAdditionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where name is not null
        defaultPayrollAdditionsShouldBeFound("name.specified=true");

        // Get all the payrollAdditionsList where name is null
        defaultPayrollAdditionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByNameContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where name contains DEFAULT_NAME
        defaultPayrollAdditionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the payrollAdditionsList where name contains UPDATED_NAME
        defaultPayrollAdditionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where name does not contain DEFAULT_NAME
        defaultPayrollAdditionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the payrollAdditionsList where name does not contain UPDATED_NAME
        defaultPayrollAdditionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where category equals to DEFAULT_CATEGORY
        defaultPayrollAdditionsShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the payrollAdditionsList where category equals to UPDATED_CATEGORY
        defaultPayrollAdditionsShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultPayrollAdditionsShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the payrollAdditionsList where category equals to UPDATED_CATEGORY
        defaultPayrollAdditionsShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where category is not null
        defaultPayrollAdditionsShouldBeFound("category.specified=true");

        // Get all the payrollAdditionsList where category is null
        defaultPayrollAdditionsShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCategoryContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where category contains DEFAULT_CATEGORY
        defaultPayrollAdditionsShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the payrollAdditionsList where category contains UPDATED_CATEGORY
        defaultPayrollAdditionsShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where category does not contain DEFAULT_CATEGORY
        defaultPayrollAdditionsShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the payrollAdditionsList where category does not contain UPDATED_CATEGORY
        defaultPayrollAdditionsShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByHasUnitCalIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where hasUnitCal equals to DEFAULT_HAS_UNIT_CAL
        defaultPayrollAdditionsShouldBeFound("hasUnitCal.equals=" + DEFAULT_HAS_UNIT_CAL);

        // Get all the payrollAdditionsList where hasUnitCal equals to UPDATED_HAS_UNIT_CAL
        defaultPayrollAdditionsShouldNotBeFound("hasUnitCal.equals=" + UPDATED_HAS_UNIT_CAL);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByHasUnitCalIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where hasUnitCal in DEFAULT_HAS_UNIT_CAL or UPDATED_HAS_UNIT_CAL
        defaultPayrollAdditionsShouldBeFound("hasUnitCal.in=" + DEFAULT_HAS_UNIT_CAL + "," + UPDATED_HAS_UNIT_CAL);

        // Get all the payrollAdditionsList where hasUnitCal equals to UPDATED_HAS_UNIT_CAL
        defaultPayrollAdditionsShouldNotBeFound("hasUnitCal.in=" + UPDATED_HAS_UNIT_CAL);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByHasUnitCalIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where hasUnitCal is not null
        defaultPayrollAdditionsShouldBeFound("hasUnitCal.specified=true");

        // Get all the payrollAdditionsList where hasUnitCal is null
        defaultPayrollAdditionsShouldNotBeFound("hasUnitCal.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount equals to DEFAULT_UNIT_AMOUNT
        defaultPayrollAdditionsShouldBeFound("unitAmount.equals=" + DEFAULT_UNIT_AMOUNT);

        // Get all the payrollAdditionsList where unitAmount equals to UPDATED_UNIT_AMOUNT
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.equals=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount in DEFAULT_UNIT_AMOUNT or UPDATED_UNIT_AMOUNT
        defaultPayrollAdditionsShouldBeFound("unitAmount.in=" + DEFAULT_UNIT_AMOUNT + "," + UPDATED_UNIT_AMOUNT);

        // Get all the payrollAdditionsList where unitAmount equals to UPDATED_UNIT_AMOUNT
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.in=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount is not null
        defaultPayrollAdditionsShouldBeFound("unitAmount.specified=true");

        // Get all the payrollAdditionsList where unitAmount is null
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount is greater than or equal to DEFAULT_UNIT_AMOUNT
        defaultPayrollAdditionsShouldBeFound("unitAmount.greaterThanOrEqual=" + DEFAULT_UNIT_AMOUNT);

        // Get all the payrollAdditionsList where unitAmount is greater than or equal to UPDATED_UNIT_AMOUNT
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.greaterThanOrEqual=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount is less than or equal to DEFAULT_UNIT_AMOUNT
        defaultPayrollAdditionsShouldBeFound("unitAmount.lessThanOrEqual=" + DEFAULT_UNIT_AMOUNT);

        // Get all the payrollAdditionsList where unitAmount is less than or equal to SMALLER_UNIT_AMOUNT
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.lessThanOrEqual=" + SMALLER_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount is less than DEFAULT_UNIT_AMOUNT
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.lessThan=" + DEFAULT_UNIT_AMOUNT);

        // Get all the payrollAdditionsList where unitAmount is less than UPDATED_UNIT_AMOUNT
        defaultPayrollAdditionsShouldBeFound("unitAmount.lessThan=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByUnitAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where unitAmount is greater than DEFAULT_UNIT_AMOUNT
        defaultPayrollAdditionsShouldNotBeFound("unitAmount.greaterThan=" + DEFAULT_UNIT_AMOUNT);

        // Get all the payrollAdditionsList where unitAmount is greater than SMALLER_UNIT_AMOUNT
        defaultPayrollAdditionsShouldBeFound("unitAmount.greaterThan=" + SMALLER_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByAssignTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where assignType equals to DEFAULT_ASSIGN_TYPE
        defaultPayrollAdditionsShouldBeFound("assignType.equals=" + DEFAULT_ASSIGN_TYPE);

        // Get all the payrollAdditionsList where assignType equals to UPDATED_ASSIGN_TYPE
        defaultPayrollAdditionsShouldNotBeFound("assignType.equals=" + UPDATED_ASSIGN_TYPE);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByAssignTypeIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where assignType in DEFAULT_ASSIGN_TYPE or UPDATED_ASSIGN_TYPE
        defaultPayrollAdditionsShouldBeFound("assignType.in=" + DEFAULT_ASSIGN_TYPE + "," + UPDATED_ASSIGN_TYPE);

        // Get all the payrollAdditionsList where assignType equals to UPDATED_ASSIGN_TYPE
        defaultPayrollAdditionsShouldNotBeFound("assignType.in=" + UPDATED_ASSIGN_TYPE);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByAssignTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where assignType is not null
        defaultPayrollAdditionsShouldBeFound("assignType.specified=true");

        // Get all the payrollAdditionsList where assignType is null
        defaultPayrollAdditionsShouldNotBeFound("assignType.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByAssignTypeContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where assignType contains DEFAULT_ASSIGN_TYPE
        defaultPayrollAdditionsShouldBeFound("assignType.contains=" + DEFAULT_ASSIGN_TYPE);

        // Get all the payrollAdditionsList where assignType contains UPDATED_ASSIGN_TYPE
        defaultPayrollAdditionsShouldNotBeFound("assignType.contains=" + UPDATED_ASSIGN_TYPE);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByAssignTypeNotContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where assignType does not contain DEFAULT_ASSIGN_TYPE
        defaultPayrollAdditionsShouldNotBeFound("assignType.doesNotContain=" + DEFAULT_ASSIGN_TYPE);

        // Get all the payrollAdditionsList where assignType does not contain UPDATED_ASSIGN_TYPE
        defaultPayrollAdditionsShouldBeFound("assignType.doesNotContain=" + UPDATED_ASSIGN_TYPE);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where status equals to DEFAULT_STATUS
        defaultPayrollAdditionsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the payrollAdditionsList where status equals to UPDATED_STATUS
        defaultPayrollAdditionsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPayrollAdditionsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the payrollAdditionsList where status equals to UPDATED_STATUS
        defaultPayrollAdditionsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where status is not null
        defaultPayrollAdditionsShouldBeFound("status.specified=true");

        // Get all the payrollAdditionsList where status is null
        defaultPayrollAdditionsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where status contains DEFAULT_STATUS
        defaultPayrollAdditionsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the payrollAdditionsList where status contains UPDATED_STATUS
        defaultPayrollAdditionsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where status does not contain DEFAULT_STATUS
        defaultPayrollAdditionsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the payrollAdditionsList where status does not contain UPDATED_STATUS
        defaultPayrollAdditionsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultPayrollAdditionsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the payrollAdditionsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPayrollAdditionsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultPayrollAdditionsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the payrollAdditionsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPayrollAdditionsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId is not null
        defaultPayrollAdditionsShouldBeFound("employeeId.specified=true");

        // Get all the payrollAdditionsList where employeeId is null
        defaultPayrollAdditionsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultPayrollAdditionsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the payrollAdditionsList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultPayrollAdditionsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultPayrollAdditionsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the payrollAdditionsList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultPayrollAdditionsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultPayrollAdditionsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the payrollAdditionsList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultPayrollAdditionsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultPayrollAdditionsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the payrollAdditionsList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultPayrollAdditionsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId equals to DEFAULT_COMPANY_ID
        defaultPayrollAdditionsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the payrollAdditionsList where companyId equals to UPDATED_COMPANY_ID
        defaultPayrollAdditionsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultPayrollAdditionsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the payrollAdditionsList where companyId equals to UPDATED_COMPANY_ID
        defaultPayrollAdditionsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId is not null
        defaultPayrollAdditionsShouldBeFound("companyId.specified=true");

        // Get all the payrollAdditionsList where companyId is null
        defaultPayrollAdditionsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultPayrollAdditionsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the payrollAdditionsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultPayrollAdditionsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultPayrollAdditionsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the payrollAdditionsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultPayrollAdditionsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId is less than DEFAULT_COMPANY_ID
        defaultPayrollAdditionsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the payrollAdditionsList where companyId is less than UPDATED_COMPANY_ID
        defaultPayrollAdditionsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultPayrollAdditionsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the payrollAdditionsList where companyId is greater than SMALLER_COMPANY_ID
        defaultPayrollAdditionsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPayrollAdditionsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the payrollAdditionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPayrollAdditionsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPayrollAdditionsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the payrollAdditionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPayrollAdditionsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModified is not null
        defaultPayrollAdditionsShouldBeFound("lastModified.specified=true");

        // Get all the payrollAdditionsList where lastModified is null
        defaultPayrollAdditionsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the payrollAdditionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the payrollAdditionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModifiedBy is not null
        defaultPayrollAdditionsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the payrollAdditionsList where lastModifiedBy is null
        defaultPayrollAdditionsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the payrollAdditionsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPayrollAdditionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        // Get all the payrollAdditionsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the payrollAdditionsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPayrollAdditionsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPayrollAdditionsShouldBeFound(String filter) throws Exception {
        restPayrollAdditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollAdditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].hasUnitCal").value(hasItem(DEFAULT_HAS_UNIT_CAL.booleanValue())))
            .andExpect(jsonPath("$.[*].unitAmount").value(hasItem(DEFAULT_UNIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].assignType").value(hasItem(DEFAULT_ASSIGN_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPayrollAdditionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPayrollAdditionsShouldNotBeFound(String filter) throws Exception {
        restPayrollAdditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPayrollAdditionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPayrollAdditions() throws Exception {
        // Get the payrollAdditions
        restPayrollAdditionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPayrollAdditions() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();

        // Update the payrollAdditions
        PayrollAdditions updatedPayrollAdditions = payrollAdditionsRepository.findById(payrollAdditions.getId()).get();
        // Disconnect from session so that the updates on updatedPayrollAdditions are not directly saved in db
        em.detach(updatedPayrollAdditions);
        updatedPayrollAdditions
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .assignType(UPDATED_ASSIGN_TYPE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(updatedPayrollAdditions);

        restPayrollAdditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollAdditionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
        PayrollAdditions testPayrollAdditions = payrollAdditionsList.get(payrollAdditionsList.size() - 1);
        assertThat(testPayrollAdditions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayrollAdditions.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPayrollAdditions.getHasUnitCal()).isEqualTo(UPDATED_HAS_UNIT_CAL);
        assertThat(testPayrollAdditions.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testPayrollAdditions.getAssignType()).isEqualTo(UPDATED_ASSIGN_TYPE);
        assertThat(testPayrollAdditions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayrollAdditions.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPayrollAdditions.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPayrollAdditions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPayrollAdditions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPayrollAdditions() throws Exception {
        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();
        payrollAdditions.setId(count.incrementAndGet());

        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollAdditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollAdditionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayrollAdditions() throws Exception {
        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();
        payrollAdditions.setId(count.incrementAndGet());

        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollAdditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayrollAdditions() throws Exception {
        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();
        payrollAdditions.setId(count.incrementAndGet());

        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollAdditionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayrollAdditionsWithPatch() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();

        // Update the payrollAdditions using partial update
        PayrollAdditions partialUpdatedPayrollAdditions = new PayrollAdditions();
        partialUpdatedPayrollAdditions.setId(payrollAdditions.getId());

        partialUpdatedPayrollAdditions
            .category(UPDATED_CATEGORY)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .assignType(UPDATED_ASSIGN_TYPE);
         //   .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPayrollAdditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollAdditions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollAdditions))
            )
            .andExpect(status().isOk());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
        PayrollAdditions testPayrollAdditions = payrollAdditionsList.get(payrollAdditionsList.size() - 1);
        assertThat(testPayrollAdditions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPayrollAdditions.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPayrollAdditions.getHasUnitCal()).isEqualTo(DEFAULT_HAS_UNIT_CAL);
        assertThat(testPayrollAdditions.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testPayrollAdditions.getAssignType()).isEqualTo(UPDATED_ASSIGN_TYPE);
        assertThat(testPayrollAdditions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayrollAdditions.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testPayrollAdditions.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPayrollAdditions.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPayrollAdditions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePayrollAdditionsWithPatch() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();

        // Update the payrollAdditions using partial update
        PayrollAdditions partialUpdatedPayrollAdditions = new PayrollAdditions();
        partialUpdatedPayrollAdditions.setId(payrollAdditions.getId());

        partialUpdatedPayrollAdditions
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .assignType(UPDATED_ASSIGN_TYPE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPayrollAdditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollAdditions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollAdditions))
            )
            .andExpect(status().isOk());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
        PayrollAdditions testPayrollAdditions = payrollAdditionsList.get(payrollAdditionsList.size() - 1);
        assertThat(testPayrollAdditions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPayrollAdditions.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPayrollAdditions.getHasUnitCal()).isEqualTo(UPDATED_HAS_UNIT_CAL);
        assertThat(testPayrollAdditions.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testPayrollAdditions.getAssignType()).isEqualTo(UPDATED_ASSIGN_TYPE);
        assertThat(testPayrollAdditions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayrollAdditions.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPayrollAdditions.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPayrollAdditions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPayrollAdditions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPayrollAdditions() throws Exception {
        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();
        payrollAdditions.setId(count.incrementAndGet());

        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollAdditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payrollAdditionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayrollAdditions() throws Exception {
        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();
        payrollAdditions.setId(count.incrementAndGet());

        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollAdditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayrollAdditions() throws Exception {
        int databaseSizeBeforeUpdate = payrollAdditionsRepository.findAll().size();
        payrollAdditions.setId(count.incrementAndGet());

        // Create the PayrollAdditions
        PayrollAdditionsDTO payrollAdditionsDTO = payrollAdditionsMapper.toDto(payrollAdditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollAdditionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollAdditionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollAdditions in the database
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayrollAdditions() throws Exception {
        // Initialize the database
        payrollAdditionsRepository.saveAndFlush(payrollAdditions);

        int databaseSizeBeforeDelete = payrollAdditionsRepository.findAll().size();

        // Delete the payrollAdditions
        restPayrollAdditionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, payrollAdditions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayrollAdditions> payrollAdditionsList = payrollAdditionsRepository.findAll();
        assertThat(payrollAdditionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
