package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Remuneration;
import com.techvg.hrms.repository.RemunerationRepository;
import com.techvg.hrms.service.criteria.RemunerationCriteria;
import com.techvg.hrms.service.dto.RemunerationDTO;
import com.techvg.hrms.service.mapper.RemunerationMapper;
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
 * Integration tests for the {@link RemunerationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemunerationResourceIT {

    private static final String DEFAULT_SALARY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SALARY_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYE_ID = 1L;
    private static final Long UPDATED_EMPLOYE_ID = 2L;
    private static final Long SMALLER_EMPLOYE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/remunerations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RemunerationRepository remunerationRepository;

    @Autowired
    private RemunerationMapper remunerationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemunerationMockMvc;

    private Remuneration remuneration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remuneration createEntity(EntityManager em) {
        Remuneration remuneration = new Remuneration()
            .salaryType(DEFAULT_SALARY_TYPE)
            .amount(DEFAULT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .employeeId(DEFAULT_EMPLOYE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return remuneration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remuneration createUpdatedEntity(EntityManager em) {
        Remuneration remuneration = new Remuneration()
            .salaryType(UPDATED_SALARY_TYPE)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .employeeId(UPDATED_EMPLOYE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return remuneration;
    }

    @BeforeEach
    public void initTest() {
        remuneration = createEntity(em);
    }

    @Test
    @Transactional
    void createRemuneration() throws Exception {
        int databaseSizeBeforeCreate = remunerationRepository.findAll().size();
        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);
        restRemunerationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeCreate + 1);
        Remuneration testRemuneration = remunerationList.get(remunerationList.size() - 1);
        assertThat(testRemuneration.getSalaryType()).isEqualTo(DEFAULT_SALARY_TYPE);
        assertThat(testRemuneration.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRemuneration.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testRemuneration.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testRemuneration.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testRemuneration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRemuneration.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRemuneration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createRemunerationWithExistingId() throws Exception {
        // Create the Remuneration with an existing ID
        remuneration.setId(1L);
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        int databaseSizeBeforeCreate = remunerationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemunerationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRemunerations() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList
        restRemunerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remuneration.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryType").value(hasItem(DEFAULT_SALARY_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getRemuneration() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get the remuneration
        restRemunerationMockMvc
            .perform(get(ENTITY_API_URL_ID, remuneration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remuneration.getId().intValue()))
            .andExpect(jsonPath("$.salaryType").value(DEFAULT_SALARY_TYPE))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getRemunerationsByIdFiltering() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        Long id = remuneration.getId();

        defaultRemunerationShouldBeFound("id.equals=" + id);
        defaultRemunerationShouldNotBeFound("id.notEquals=" + id);

        defaultRemunerationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRemunerationShouldNotBeFound("id.greaterThan=" + id);

        defaultRemunerationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRemunerationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRemunerationsBySalaryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where salaryType equals to DEFAULT_SALARY_TYPE
        defaultRemunerationShouldBeFound("salaryType.equals=" + DEFAULT_SALARY_TYPE);

        // Get all the remunerationList where salaryType equals to UPDATED_SALARY_TYPE
        defaultRemunerationShouldNotBeFound("salaryType.equals=" + UPDATED_SALARY_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsBySalaryTypeIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where salaryType in DEFAULT_SALARY_TYPE or UPDATED_SALARY_TYPE
        defaultRemunerationShouldBeFound("salaryType.in=" + DEFAULT_SALARY_TYPE + "," + UPDATED_SALARY_TYPE);

        // Get all the remunerationList where salaryType equals to UPDATED_SALARY_TYPE
        defaultRemunerationShouldNotBeFound("salaryType.in=" + UPDATED_SALARY_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsBySalaryTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where salaryType is not null
        defaultRemunerationShouldBeFound("salaryType.specified=true");

        // Get all the remunerationList where salaryType is null
        defaultRemunerationShouldNotBeFound("salaryType.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsBySalaryTypeContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where salaryType contains DEFAULT_SALARY_TYPE
        defaultRemunerationShouldBeFound("salaryType.contains=" + DEFAULT_SALARY_TYPE);

        // Get all the remunerationList where salaryType contains UPDATED_SALARY_TYPE
        defaultRemunerationShouldNotBeFound("salaryType.contains=" + UPDATED_SALARY_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsBySalaryTypeNotContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where salaryType does not contain DEFAULT_SALARY_TYPE
        defaultRemunerationShouldNotBeFound("salaryType.doesNotContain=" + DEFAULT_SALARY_TYPE);

        // Get all the remunerationList where salaryType does not contain UPDATED_SALARY_TYPE
        defaultRemunerationShouldBeFound("salaryType.doesNotContain=" + UPDATED_SALARY_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount equals to DEFAULT_AMOUNT
        defaultRemunerationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the remunerationList where amount equals to UPDATED_AMOUNT
        defaultRemunerationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultRemunerationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the remunerationList where amount equals to UPDATED_AMOUNT
        defaultRemunerationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount is not null
        defaultRemunerationShouldBeFound("amount.specified=true");

        // Get all the remunerationList where amount is null
        defaultRemunerationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultRemunerationShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the remunerationList where amount is greater than or equal to UPDATED_AMOUNT
        defaultRemunerationShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount is less than or equal to DEFAULT_AMOUNT
        defaultRemunerationShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the remunerationList where amount is less than or equal to SMALLER_AMOUNT
        defaultRemunerationShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount is less than DEFAULT_AMOUNT
        defaultRemunerationShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the remunerationList where amount is less than UPDATED_AMOUNT
        defaultRemunerationShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllRemunerationsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where amount is greater than DEFAULT_AMOUNT
        defaultRemunerationShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the remunerationList where amount is greater than SMALLER_AMOUNT
        defaultRemunerationShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllRemunerationsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultRemunerationShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the remunerationList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultRemunerationShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultRemunerationShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the remunerationList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultRemunerationShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where paymentType is not null
        defaultRemunerationShouldBeFound("paymentType.specified=true");

        // Get all the remunerationList where paymentType is null
        defaultRemunerationShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByPaymentTypeContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where paymentType contains DEFAULT_PAYMENT_TYPE
        defaultRemunerationShouldBeFound("paymentType.contains=" + DEFAULT_PAYMENT_TYPE);

        // Get all the remunerationList where paymentType contains UPDATED_PAYMENT_TYPE
        defaultRemunerationShouldNotBeFound("paymentType.contains=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsByPaymentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where paymentType does not contain DEFAULT_PAYMENT_TYPE
        defaultRemunerationShouldNotBeFound("paymentType.doesNotContain=" + DEFAULT_PAYMENT_TYPE);

        // Get all the remunerationList where paymentType does not contain UPDATED_PAYMENT_TYPE
        defaultRemunerationShouldBeFound("paymentType.doesNotContain=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId equals to DEFAULT_EMPLOYE_ID
        defaultRemunerationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the remunerationList where employeeId equals to UPDATED_EMPLOYE_ID
        defaultRemunerationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultRemunerationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the remunerationList where employeeId equals to UPDATED_EMPLOYE_ID
        defaultRemunerationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId is not null
        defaultRemunerationShouldBeFound("employeeId.specified=true");

        // Get all the remunerationList where employeeId is null
        defaultRemunerationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultRemunerationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the remunerationList where employeeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultRemunerationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultRemunerationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the remunerationList where employeeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultRemunerationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId is less than DEFAULT_EMPLOYE_ID
        defaultRemunerationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the remunerationList where employeeId is less than UPDATED_EMPLOYE_ID
        defaultRemunerationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByemployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where employeeId is greater than DEFAULT_EMPLOYE_ID
        defaultRemunerationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the remunerationList where employeeId is greater than SMALLER_EMPLOYE_ID
        defaultRemunerationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId equals to DEFAULT_COMPANY_ID
        defaultRemunerationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the remunerationList where companyId equals to UPDATED_COMPANY_ID
        defaultRemunerationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultRemunerationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the remunerationList where companyId equals to UPDATED_COMPANY_ID
        defaultRemunerationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId is not null
        defaultRemunerationShouldBeFound("companyId.specified=true");

        // Get all the remunerationList where companyId is null
        defaultRemunerationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultRemunerationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the remunerationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultRemunerationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultRemunerationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the remunerationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultRemunerationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId is less than DEFAULT_COMPANY_ID
        defaultRemunerationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the remunerationList where companyId is less than UPDATED_COMPANY_ID
        defaultRemunerationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultRemunerationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the remunerationList where companyId is greater than SMALLER_COMPANY_ID
        defaultRemunerationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRemunerationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where status equals to DEFAULT_STATUS
        defaultRemunerationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the remunerationList where status equals to UPDATED_STATUS
        defaultRemunerationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRemunerationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRemunerationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the remunerationList where status equals to UPDATED_STATUS
        defaultRemunerationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRemunerationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where status is not null
        defaultRemunerationShouldBeFound("status.specified=true");

        // Get all the remunerationList where status is null
        defaultRemunerationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where status contains DEFAULT_STATUS
        defaultRemunerationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the remunerationList where status contains UPDATED_STATUS
        defaultRemunerationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRemunerationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where status does not contain DEFAULT_STATUS
        defaultRemunerationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the remunerationList where status does not contain UPDATED_STATUS
        defaultRemunerationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRemunerationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the remunerationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRemunerationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRemunerationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the remunerationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRemunerationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModified is not null
        defaultRemunerationShouldBeFound("lastModified.specified=true");

        // Get all the remunerationList where lastModified is null
        defaultRemunerationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRemunerationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the remunerationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRemunerationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRemunerationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the remunerationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRemunerationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModifiedBy is not null
        defaultRemunerationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the remunerationList where lastModifiedBy is null
        defaultRemunerationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRemunerationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the remunerationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRemunerationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemunerationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        // Get all the remunerationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRemunerationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the remunerationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRemunerationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRemunerationShouldBeFound(String filter) throws Exception {
        restRemunerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remuneration.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryType").value(hasItem(DEFAULT_SALARY_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restRemunerationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRemunerationShouldNotBeFound(String filter) throws Exception {
        restRemunerationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRemunerationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRemuneration() throws Exception {
        // Get the remuneration
        restRemunerationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemuneration() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();

        // Update the remuneration
        Remuneration updatedRemuneration = remunerationRepository.findById(remuneration.getId()).get();
        // Disconnect from session so that the updates on updatedRemuneration are not directly saved in db
        em.detach(updatedRemuneration);
        updatedRemuneration
            .salaryType(UPDATED_SALARY_TYPE)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .employeeId(UPDATED_EMPLOYE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(updatedRemuneration);

        restRemunerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remunerationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
        Remuneration testRemuneration = remunerationList.get(remunerationList.size() - 1);
        assertThat(testRemuneration.getSalaryType()).isEqualTo(UPDATED_SALARY_TYPE);
        assertThat(testRemuneration.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRemuneration.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testRemuneration.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testRemuneration.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testRemuneration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRemuneration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRemuneration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRemuneration() throws Exception {
        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();
        remuneration.setId(count.incrementAndGet());

        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemunerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remunerationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemuneration() throws Exception {
        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();
        remuneration.setId(count.incrementAndGet());

        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemunerationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemuneration() throws Exception {
        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();
        remuneration.setId(count.incrementAndGet());

        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemunerationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemunerationWithPatch() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();

        // Update the remuneration using partial update
        Remuneration partialUpdatedRemuneration = new Remuneration();
        partialUpdatedRemuneration.setId(remuneration.getId());

        partialUpdatedRemuneration.amount(UPDATED_AMOUNT).paymentType(UPDATED_PAYMENT_TYPE)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restRemunerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemuneration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemuneration))
            )
            .andExpect(status().isOk());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
        Remuneration testRemuneration = remunerationList.get(remunerationList.size() - 1);
        assertThat(testRemuneration.getSalaryType()).isEqualTo(DEFAULT_SALARY_TYPE);
        assertThat(testRemuneration.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRemuneration.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testRemuneration.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testRemuneration.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testRemuneration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRemuneration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRemuneration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRemunerationWithPatch() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();

        // Update the remuneration using partial update
        Remuneration partialUpdatedRemuneration = new Remuneration();
        partialUpdatedRemuneration.setId(remuneration.getId());

        partialUpdatedRemuneration
            .salaryType(UPDATED_SALARY_TYPE)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .employeeId(UPDATED_EMPLOYE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restRemunerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemuneration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemuneration))
            )
            .andExpect(status().isOk());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
        Remuneration testRemuneration = remunerationList.get(remunerationList.size() - 1);
        assertThat(testRemuneration.getSalaryType()).isEqualTo(UPDATED_SALARY_TYPE);
        assertThat(testRemuneration.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRemuneration.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testRemuneration.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testRemuneration.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testRemuneration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRemuneration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRemuneration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRemuneration() throws Exception {
        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();
        remuneration.setId(count.incrementAndGet());

        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemunerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remunerationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemuneration() throws Exception {
        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();
        remuneration.setId(count.incrementAndGet());

        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemunerationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemuneration() throws Exception {
        int databaseSizeBeforeUpdate = remunerationRepository.findAll().size();
        remuneration.setId(count.incrementAndGet());

        // Create the Remuneration
        RemunerationDTO remunerationDTO = remunerationMapper.toDto(remuneration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemunerationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remunerationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remuneration in the database
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemuneration() throws Exception {
        // Initialize the database
        remunerationRepository.saveAndFlush(remuneration);

        int databaseSizeBeforeDelete = remunerationRepository.findAll().size();

        // Delete the remuneration
        restRemunerationMockMvc
            .perform(delete(ENTITY_API_URL_ID, remuneration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remuneration> remunerationList = remunerationRepository.findAll();
        assertThat(remunerationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
