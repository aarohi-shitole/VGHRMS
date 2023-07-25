package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Deduction;
import com.techvg.hrms.repository.DeductionRepository;
import com.techvg.hrms.service.criteria.DeductionCriteria;
import com.techvg.hrms.service.dto.DeductionDTO;
import com.techvg.hrms.service.mapper.DeductionMapper;
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
 * Integration tests for the {@link DeductionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeductionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_UNIT_CAL = false;
    private static final Boolean UPDATED_HAS_UNIT_CAL = true;

    private static final Double DEFAULT_UNIT_AMOUNT = 1D;
    private static final Double UPDATED_UNIT_AMOUNT = 2D;
    private static final Double SMALLER_UNIT_AMOUNT = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/deductions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private DeductionMapper deductionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeductionMockMvc;

    private Deduction deduction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deduction createEntity(EntityManager em) {
        Deduction deduction = new Deduction()
            .name(DEFAULT_NAME)
            .hasUnitCal(DEFAULT_HAS_UNIT_CAL)
            .unitAmount(DEFAULT_UNIT_AMOUNT)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return deduction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deduction createUpdatedEntity(EntityManager em) {
        Deduction deduction = new Deduction()
            .name(UPDATED_NAME)
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return deduction;
    }

    @BeforeEach
    public void initTest() {
        deduction = createEntity(em);
    }

    @Test
    @Transactional
    void createDeduction() throws Exception {
        int databaseSizeBeforeCreate = deductionRepository.findAll().size();
        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);
        restDeductionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deductionDTO)))
            .andExpect(status().isCreated());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeCreate + 1);
        Deduction testDeduction = deductionList.get(deductionList.size() - 1);
        assertThat(testDeduction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeduction.getHasUnitCal()).isEqualTo(DEFAULT_HAS_UNIT_CAL);
        assertThat(testDeduction.getUnitAmount()).isEqualTo(DEFAULT_UNIT_AMOUNT);
        assertThat(testDeduction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeduction.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testDeduction.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testDeduction.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDeduction.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createDeductionWithExistingId() throws Exception {
        // Create the Deduction with an existing ID
        deduction.setId(1L);
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        int databaseSizeBeforeCreate = deductionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeductionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deductionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeductions() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList
        restDeductionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deduction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hasUnitCal").value(hasItem(DEFAULT_HAS_UNIT_CAL.booleanValue())))
            .andExpect(jsonPath("$.[*].unitAmount").value(hasItem(DEFAULT_UNIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getDeduction() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get the deduction
        restDeductionMockMvc
            .perform(get(ENTITY_API_URL_ID, deduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deduction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hasUnitCal").value(DEFAULT_HAS_UNIT_CAL.booleanValue()))
            .andExpect(jsonPath("$.unitAmount").value(DEFAULT_UNIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getDeductionsByIdFiltering() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        Long id = deduction.getId();

        defaultDeductionShouldBeFound("id.equals=" + id);
        defaultDeductionShouldNotBeFound("id.notEquals=" + id);

        defaultDeductionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeductionShouldNotBeFound("id.greaterThan=" + id);

        defaultDeductionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeductionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDeductionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where name equals to DEFAULT_NAME
        defaultDeductionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deductionList where name equals to UPDATED_NAME
        defaultDeductionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeductionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeductionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deductionList where name equals to UPDATED_NAME
        defaultDeductionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeductionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where name is not null
        defaultDeductionShouldBeFound("name.specified=true");

        // Get all the deductionList where name is null
        defaultDeductionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByNameContainsSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where name contains DEFAULT_NAME
        defaultDeductionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deductionList where name contains UPDATED_NAME
        defaultDeductionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeductionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where name does not contain DEFAULT_NAME
        defaultDeductionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deductionList where name does not contain UPDATED_NAME
        defaultDeductionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeductionsByHasUnitCalIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where hasUnitCal equals to DEFAULT_HAS_UNIT_CAL
        defaultDeductionShouldBeFound("hasUnitCal.equals=" + DEFAULT_HAS_UNIT_CAL);

        // Get all the deductionList where hasUnitCal equals to UPDATED_HAS_UNIT_CAL
        defaultDeductionShouldNotBeFound("hasUnitCal.equals=" + UPDATED_HAS_UNIT_CAL);
    }

    @Test
    @Transactional
    void getAllDeductionsByHasUnitCalIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where hasUnitCal in DEFAULT_HAS_UNIT_CAL or UPDATED_HAS_UNIT_CAL
        defaultDeductionShouldBeFound("hasUnitCal.in=" + DEFAULT_HAS_UNIT_CAL + "," + UPDATED_HAS_UNIT_CAL);

        // Get all the deductionList where hasUnitCal equals to UPDATED_HAS_UNIT_CAL
        defaultDeductionShouldNotBeFound("hasUnitCal.in=" + UPDATED_HAS_UNIT_CAL);
    }

    @Test
    @Transactional
    void getAllDeductionsByHasUnitCalIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where hasUnitCal is not null
        defaultDeductionShouldBeFound("hasUnitCal.specified=true");

        // Get all the deductionList where hasUnitCal is null
        defaultDeductionShouldNotBeFound("hasUnitCal.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount equals to DEFAULT_UNIT_AMOUNT
        defaultDeductionShouldBeFound("unitAmount.equals=" + DEFAULT_UNIT_AMOUNT);

        // Get all the deductionList where unitAmount equals to UPDATED_UNIT_AMOUNT
        defaultDeductionShouldNotBeFound("unitAmount.equals=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount in DEFAULT_UNIT_AMOUNT or UPDATED_UNIT_AMOUNT
        defaultDeductionShouldBeFound("unitAmount.in=" + DEFAULT_UNIT_AMOUNT + "," + UPDATED_UNIT_AMOUNT);

        // Get all the deductionList where unitAmount equals to UPDATED_UNIT_AMOUNT
        defaultDeductionShouldNotBeFound("unitAmount.in=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount is not null
        defaultDeductionShouldBeFound("unitAmount.specified=true");

        // Get all the deductionList where unitAmount is null
        defaultDeductionShouldNotBeFound("unitAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount is greater than or equal to DEFAULT_UNIT_AMOUNT
        defaultDeductionShouldBeFound("unitAmount.greaterThanOrEqual=" + DEFAULT_UNIT_AMOUNT);

        // Get all the deductionList where unitAmount is greater than or equal to UPDATED_UNIT_AMOUNT
        defaultDeductionShouldNotBeFound("unitAmount.greaterThanOrEqual=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount is less than or equal to DEFAULT_UNIT_AMOUNT
        defaultDeductionShouldBeFound("unitAmount.lessThanOrEqual=" + DEFAULT_UNIT_AMOUNT);

        // Get all the deductionList where unitAmount is less than or equal to SMALLER_UNIT_AMOUNT
        defaultDeductionShouldNotBeFound("unitAmount.lessThanOrEqual=" + SMALLER_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount is less than DEFAULT_UNIT_AMOUNT
        defaultDeductionShouldNotBeFound("unitAmount.lessThan=" + DEFAULT_UNIT_AMOUNT);

        // Get all the deductionList where unitAmount is less than UPDATED_UNIT_AMOUNT
        defaultDeductionShouldBeFound("unitAmount.lessThan=" + UPDATED_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDeductionsByUnitAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where unitAmount is greater than DEFAULT_UNIT_AMOUNT
        defaultDeductionShouldNotBeFound("unitAmount.greaterThan=" + DEFAULT_UNIT_AMOUNT);

        // Get all the deductionList where unitAmount is greater than SMALLER_UNIT_AMOUNT
        defaultDeductionShouldBeFound("unitAmount.greaterThan=" + SMALLER_UNIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllDeductionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where status equals to DEFAULT_STATUS
        defaultDeductionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the deductionList where status equals to UPDATED_STATUS
        defaultDeductionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDeductionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDeductionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the deductionList where status equals to UPDATED_STATUS
        defaultDeductionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDeductionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where status is not null
        defaultDeductionShouldBeFound("status.specified=true");

        // Get all the deductionList where status is null
        defaultDeductionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where status contains DEFAULT_STATUS
        defaultDeductionShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the deductionList where status contains UPDATED_STATUS
        defaultDeductionShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDeductionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where status does not contain DEFAULT_STATUS
        defaultDeductionShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the deductionList where status does not contain UPDATED_STATUS
        defaultDeductionShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultDeductionShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the deductionList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultDeductionShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultDeductionShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the deductionList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultDeductionShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId is not null
        defaultDeductionShouldBeFound("employeeId.specified=true");

        // Get all the deductionList where employeeId is null
        defaultDeductionShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultDeductionShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the deductionList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultDeductionShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultDeductionShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the deductionList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultDeductionShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultDeductionShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the deductionList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultDeductionShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultDeductionShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the deductionList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultDeductionShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId equals to DEFAULT_COMPANY_ID
        defaultDeductionShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the deductionList where companyId equals to UPDATED_COMPANY_ID
        defaultDeductionShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultDeductionShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the deductionList where companyId equals to UPDATED_COMPANY_ID
        defaultDeductionShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId is not null
        defaultDeductionShouldBeFound("companyId.specified=true");

        // Get all the deductionList where companyId is null
        defaultDeductionShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultDeductionShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the deductionList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultDeductionShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultDeductionShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the deductionList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultDeductionShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId is less than DEFAULT_COMPANY_ID
        defaultDeductionShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the deductionList where companyId is less than UPDATED_COMPANY_ID
        defaultDeductionShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where companyId is greater than DEFAULT_COMPANY_ID
        defaultDeductionShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the deductionList where companyId is greater than SMALLER_COMPANY_ID
        defaultDeductionShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDeductionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the deductionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDeductionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDeductionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the deductionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDeductionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModified is not null
        defaultDeductionShouldBeFound("lastModified.specified=true");

        // Get all the deductionList where lastModified is null
        defaultDeductionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDeductionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the deductionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDeductionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDeductionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the deductionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDeductionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModifiedBy is not null
        defaultDeductionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the deductionList where lastModifiedBy is null
        defaultDeductionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDeductionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the deductionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDeductionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeductionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        // Get all the deductionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDeductionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the deductionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDeductionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeductionShouldBeFound(String filter) throws Exception {
        restDeductionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deduction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hasUnitCal").value(hasItem(DEFAULT_HAS_UNIT_CAL.booleanValue())))
            .andExpect(jsonPath("$.[*].unitAmount").value(hasItem(DEFAULT_UNIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restDeductionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeductionShouldNotBeFound(String filter) throws Exception {
        restDeductionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeductionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeduction() throws Exception {
        // Get the deduction
        restDeductionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeduction() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();

        // Update the deduction
        Deduction updatedDeduction = deductionRepository.findById(deduction.getId()).get();
        // Disconnect from session so that the updates on updatedDeduction are not directly saved in db
        em.detach(updatedDeduction);
        updatedDeduction
            .name(UPDATED_NAME)
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        DeductionDTO deductionDTO = deductionMapper.toDto(updatedDeduction);

        restDeductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deductionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deductionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
        Deduction testDeduction = deductionList.get(deductionList.size() - 1);
        assertThat(testDeduction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeduction.getHasUnitCal()).isEqualTo(UPDATED_HAS_UNIT_CAL);
        assertThat(testDeduction.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testDeduction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeduction.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testDeduction.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDeduction.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDeduction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDeduction() throws Exception {
        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();
        deduction.setId(count.incrementAndGet());

        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deductionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeduction() throws Exception {
        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();
        deduction.setId(count.incrementAndGet());

        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeduction() throws Exception {
        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();
        deduction.setId(count.incrementAndGet());

        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeductionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deductionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeductionWithPatch() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();

        // Update the deduction using partial update
        Deduction partialUpdatedDeduction = new Deduction();
        partialUpdatedDeduction.setId(deduction.getId());

        partialUpdatedDeduction
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);

        restDeductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeduction))
            )
            .andExpect(status().isOk());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
        Deduction testDeduction = deductionList.get(deductionList.size() - 1);
        assertThat(testDeduction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeduction.getHasUnitCal()).isEqualTo(UPDATED_HAS_UNIT_CAL);
        assertThat(testDeduction.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testDeduction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeduction.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testDeduction.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testDeduction.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDeduction.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDeductionWithPatch() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();

        // Update the deduction using partial update
        Deduction partialUpdatedDeduction = new Deduction();
        partialUpdatedDeduction.setId(deduction.getId());

        partialUpdatedDeduction
            .name(UPDATED_NAME)
            .hasUnitCal(UPDATED_HAS_UNIT_CAL)
            .unitAmount(UPDATED_UNIT_AMOUNT)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restDeductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeduction))
            )
            .andExpect(status().isOk());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
        Deduction testDeduction = deductionList.get(deductionList.size() - 1);
        assertThat(testDeduction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeduction.getHasUnitCal()).isEqualTo(UPDATED_HAS_UNIT_CAL);
        assertThat(testDeduction.getUnitAmount()).isEqualTo(UPDATED_UNIT_AMOUNT);
        assertThat(testDeduction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeduction.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testDeduction.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDeduction.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDeduction.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDeduction() throws Exception {
        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();
        deduction.setId(count.incrementAndGet());

        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deductionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeduction() throws Exception {
        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();
        deduction.setId(count.incrementAndGet());

        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeduction() throws Exception {
        int databaseSizeBeforeUpdate = deductionRepository.findAll().size();
        deduction.setId(count.incrementAndGet());

        // Create the Deduction
        DeductionDTO deductionDTO = deductionMapper.toDto(deduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeductionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deductionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deduction in the database
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeduction() throws Exception {
        // Initialize the database
        deductionRepository.saveAndFlush(deduction);

        int databaseSizeBeforeDelete = deductionRepository.findAll().size();

        // Delete the deduction
        restDeductionMockMvc
            .perform(delete(ENTITY_API_URL_ID, deduction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deduction> deductionList = deductionRepository.findAll();
        assertThat(deductionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
