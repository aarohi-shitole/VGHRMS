package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Resignation;
import com.techvg.hrms.repository.ResignationRepository;
import com.techvg.hrms.service.criteria.ResignationCriteria;
import com.techvg.hrms.service.dto.ResignationDTO;
import com.techvg.hrms.service.mapper.ResignationMapper;
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
 * Integration tests for the {@link ResignationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResignationResourceIT {

    private static final String DEFAULT_EMP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESIGN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESIGN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_NOTICE_PERIOD_INDAYS = 1L;
    private static final Long UPDATED_NOTICE_PERIOD_INDAYS = 2L;
    private static final Long SMALLER_NOTICE_PERIOD_INDAYS = 1L - 1L;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_RESIGN_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RESIGN_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_WORKING_DAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_WORKING_DAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/resignations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResignationRepository resignationRepository;

    @Autowired
    private ResignationMapper resignationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResignationMockMvc;

    private Resignation resignation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resignation createEntity(EntityManager em) {
        Resignation resignation = new Resignation()
            .empName(DEFAULT_EMP_NAME)
            .resignDate(DEFAULT_RESIGN_DATE)
            .noticePeriodIndays(DEFAULT_NOTICE_PERIOD_INDAYS)
            .reason(DEFAULT_REASON)
            .resignStatus(DEFAULT_RESIGN_STATUS)
            .lastWorkingDay(DEFAULT_LAST_WORKING_DAY)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return resignation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resignation createUpdatedEntity(EntityManager em) {
        Resignation resignation = new Resignation()
            .empName(UPDATED_EMP_NAME)
            .resignDate(UPDATED_RESIGN_DATE)
            .noticePeriodIndays(UPDATED_NOTICE_PERIOD_INDAYS)
            .reason(UPDATED_REASON)
            .resignStatus(UPDATED_RESIGN_STATUS)
            .lastWorkingDay(UPDATED_LAST_WORKING_DAY)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return resignation;
    }

    @BeforeEach
    public void initTest() {
        resignation = createEntity(em);
    }

    @Test
    @Transactional
    void createResignation() throws Exception {
        int databaseSizeBeforeCreate = resignationRepository.findAll().size();
        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);
        restResignationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeCreate + 1);
        Resignation testResignation = resignationList.get(resignationList.size() - 1);
        assertThat(testResignation.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testResignation.getResignDate()).isEqualTo(DEFAULT_RESIGN_DATE);
        assertThat(testResignation.getNoticePeriodIndays()).isEqualTo(DEFAULT_NOTICE_PERIOD_INDAYS);
        assertThat(testResignation.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testResignation.getResignStatus()).isEqualTo(DEFAULT_RESIGN_STATUS);
        assertThat(testResignation.getLastWorkingDay()).isEqualTo(DEFAULT_LAST_WORKING_DAY);
        assertThat(testResignation.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testResignation.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testResignation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testResignation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testResignation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testResignation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createResignationWithExistingId() throws Exception {
        // Create the Resignation with an existing ID
        resignation.setId(1L);
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        int databaseSizeBeforeCreate = resignationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResignationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResignations() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList
        restResignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resignation.getId().intValue())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME)))
            .andExpect(jsonPath("$.[*].resignDate").value(hasItem(DEFAULT_RESIGN_DATE.toString())))
            .andExpect(jsonPath("$.[*].noticePeriodIndays").value(hasItem(DEFAULT_NOTICE_PERIOD_INDAYS.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].resignStatus").value(hasItem(DEFAULT_RESIGN_STATUS)))
            .andExpect(jsonPath("$.[*].lastWorkingDay").value(hasItem(DEFAULT_LAST_WORKING_DAY.toString())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getResignation() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get the resignation
        restResignationMockMvc
            .perform(get(ENTITY_API_URL_ID, resignation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resignation.getId().intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME))
            .andExpect(jsonPath("$.resignDate").value(DEFAULT_RESIGN_DATE.toString()))
            .andExpect(jsonPath("$.noticePeriodIndays").value(DEFAULT_NOTICE_PERIOD_INDAYS.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.resignStatus").value(DEFAULT_RESIGN_STATUS))
            .andExpect(jsonPath("$.lastWorkingDay").value(DEFAULT_LAST_WORKING_DAY.toString()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getResignationsByIdFiltering() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        Long id = resignation.getId();

        defaultResignationShouldBeFound("id.equals=" + id);
        defaultResignationShouldNotBeFound("id.notEquals=" + id);

        defaultResignationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResignationShouldNotBeFound("id.greaterThan=" + id);

        defaultResignationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResignationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResignationsByEmpNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where empName equals to DEFAULT_EMP_NAME
        defaultResignationShouldBeFound("empName.equals=" + DEFAULT_EMP_NAME);

        // Get all the resignationList where empName equals to UPDATED_EMP_NAME
        defaultResignationShouldNotBeFound("empName.equals=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllResignationsByEmpNameIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where empName in DEFAULT_EMP_NAME or UPDATED_EMP_NAME
        defaultResignationShouldBeFound("empName.in=" + DEFAULT_EMP_NAME + "," + UPDATED_EMP_NAME);

        // Get all the resignationList where empName equals to UPDATED_EMP_NAME
        defaultResignationShouldNotBeFound("empName.in=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllResignationsByEmpNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where empName is not null
        defaultResignationShouldBeFound("empName.specified=true");

        // Get all the resignationList where empName is null
        defaultResignationShouldNotBeFound("empName.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByEmpNameContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where empName contains DEFAULT_EMP_NAME
        defaultResignationShouldBeFound("empName.contains=" + DEFAULT_EMP_NAME);

        // Get all the resignationList where empName contains UPDATED_EMP_NAME
        defaultResignationShouldNotBeFound("empName.contains=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllResignationsByEmpNameNotContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where empName does not contain DEFAULT_EMP_NAME
        defaultResignationShouldNotBeFound("empName.doesNotContain=" + DEFAULT_EMP_NAME);

        // Get all the resignationList where empName does not contain UPDATED_EMP_NAME
        defaultResignationShouldBeFound("empName.doesNotContain=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllResignationsByResignDateIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignDate equals to DEFAULT_RESIGN_DATE
        defaultResignationShouldBeFound("resignDate.equals=" + DEFAULT_RESIGN_DATE);

        // Get all the resignationList where resignDate equals to UPDATED_RESIGN_DATE
        defaultResignationShouldNotBeFound("resignDate.equals=" + UPDATED_RESIGN_DATE);
    }

    @Test
    @Transactional
    void getAllResignationsByResignDateIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignDate in DEFAULT_RESIGN_DATE or UPDATED_RESIGN_DATE
        defaultResignationShouldBeFound("resignDate.in=" + DEFAULT_RESIGN_DATE + "," + UPDATED_RESIGN_DATE);

        // Get all the resignationList where resignDate equals to UPDATED_RESIGN_DATE
        defaultResignationShouldNotBeFound("resignDate.in=" + UPDATED_RESIGN_DATE);
    }

    @Test
    @Transactional
    void getAllResignationsByResignDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignDate is not null
        defaultResignationShouldBeFound("resignDate.specified=true");

        // Get all the resignationList where resignDate is null
        defaultResignationShouldNotBeFound("resignDate.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays equals to DEFAULT_NOTICE_PERIOD_INDAYS
        defaultResignationShouldBeFound("noticePeriodIndays.equals=" + DEFAULT_NOTICE_PERIOD_INDAYS);

        // Get all the resignationList where noticePeriodIndays equals to UPDATED_NOTICE_PERIOD_INDAYS
        defaultResignationShouldNotBeFound("noticePeriodIndays.equals=" + UPDATED_NOTICE_PERIOD_INDAYS);
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays in DEFAULT_NOTICE_PERIOD_INDAYS or UPDATED_NOTICE_PERIOD_INDAYS
        defaultResignationShouldBeFound("noticePeriodIndays.in=" + DEFAULT_NOTICE_PERIOD_INDAYS + "," + UPDATED_NOTICE_PERIOD_INDAYS);

        // Get all the resignationList where noticePeriodIndays equals to UPDATED_NOTICE_PERIOD_INDAYS
        defaultResignationShouldNotBeFound("noticePeriodIndays.in=" + UPDATED_NOTICE_PERIOD_INDAYS);
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays is not null
        defaultResignationShouldBeFound("noticePeriodIndays.specified=true");

        // Get all the resignationList where noticePeriodIndays is null
        defaultResignationShouldNotBeFound("noticePeriodIndays.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays is greater than or equal to DEFAULT_NOTICE_PERIOD_INDAYS
        defaultResignationShouldBeFound("noticePeriodIndays.greaterThanOrEqual=" + DEFAULT_NOTICE_PERIOD_INDAYS);

        // Get all the resignationList where noticePeriodIndays is greater than or equal to UPDATED_NOTICE_PERIOD_INDAYS
        defaultResignationShouldNotBeFound("noticePeriodIndays.greaterThanOrEqual=" + UPDATED_NOTICE_PERIOD_INDAYS);
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays is less than or equal to DEFAULT_NOTICE_PERIOD_INDAYS
        defaultResignationShouldBeFound("noticePeriodIndays.lessThanOrEqual=" + DEFAULT_NOTICE_PERIOD_INDAYS);

        // Get all the resignationList where noticePeriodIndays is less than or equal to SMALLER_NOTICE_PERIOD_INDAYS
        defaultResignationShouldNotBeFound("noticePeriodIndays.lessThanOrEqual=" + SMALLER_NOTICE_PERIOD_INDAYS);
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsLessThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays is less than DEFAULT_NOTICE_PERIOD_INDAYS
        defaultResignationShouldNotBeFound("noticePeriodIndays.lessThan=" + DEFAULT_NOTICE_PERIOD_INDAYS);

        // Get all the resignationList where noticePeriodIndays is less than UPDATED_NOTICE_PERIOD_INDAYS
        defaultResignationShouldBeFound("noticePeriodIndays.lessThan=" + UPDATED_NOTICE_PERIOD_INDAYS);
    }

    @Test
    @Transactional
    void getAllResignationsByNoticePeriodIndaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where noticePeriodIndays is greater than DEFAULT_NOTICE_PERIOD_INDAYS
        defaultResignationShouldNotBeFound("noticePeriodIndays.greaterThan=" + DEFAULT_NOTICE_PERIOD_INDAYS);

        // Get all the resignationList where noticePeriodIndays is greater than SMALLER_NOTICE_PERIOD_INDAYS
        defaultResignationShouldBeFound("noticePeriodIndays.greaterThan=" + SMALLER_NOTICE_PERIOD_INDAYS);
    }

    @Test
    @Transactional
    void getAllResignationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where reason equals to DEFAULT_REASON
        defaultResignationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the resignationList where reason equals to UPDATED_REASON
        defaultResignationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllResignationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultResignationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the resignationList where reason equals to UPDATED_REASON
        defaultResignationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllResignationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where reason is not null
        defaultResignationShouldBeFound("reason.specified=true");

        // Get all the resignationList where reason is null
        defaultResignationShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where reason contains DEFAULT_REASON
        defaultResignationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the resignationList where reason contains UPDATED_REASON
        defaultResignationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllResignationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where reason does not contain DEFAULT_REASON
        defaultResignationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the resignationList where reason does not contain UPDATED_REASON
        defaultResignationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllResignationsByResignStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignStatus equals to DEFAULT_RESIGN_STATUS
        defaultResignationShouldBeFound("resignStatus.equals=" + DEFAULT_RESIGN_STATUS);

        // Get all the resignationList where resignStatus equals to UPDATED_RESIGN_STATUS
        defaultResignationShouldNotBeFound("resignStatus.equals=" + UPDATED_RESIGN_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByResignStatusIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignStatus in DEFAULT_RESIGN_STATUS or UPDATED_RESIGN_STATUS
        defaultResignationShouldBeFound("resignStatus.in=" + DEFAULT_RESIGN_STATUS + "," + UPDATED_RESIGN_STATUS);

        // Get all the resignationList where resignStatus equals to UPDATED_RESIGN_STATUS
        defaultResignationShouldNotBeFound("resignStatus.in=" + UPDATED_RESIGN_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByResignStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignStatus is not null
        defaultResignationShouldBeFound("resignStatus.specified=true");

        // Get all the resignationList where resignStatus is null
        defaultResignationShouldNotBeFound("resignStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByResignStatusContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignStatus contains DEFAULT_RESIGN_STATUS
        defaultResignationShouldBeFound("resignStatus.contains=" + DEFAULT_RESIGN_STATUS);

        // Get all the resignationList where resignStatus contains UPDATED_RESIGN_STATUS
        defaultResignationShouldNotBeFound("resignStatus.contains=" + UPDATED_RESIGN_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByResignStatusNotContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where resignStatus does not contain DEFAULT_RESIGN_STATUS
        defaultResignationShouldNotBeFound("resignStatus.doesNotContain=" + DEFAULT_RESIGN_STATUS);

        // Get all the resignationList where resignStatus does not contain UPDATED_RESIGN_STATUS
        defaultResignationShouldBeFound("resignStatus.doesNotContain=" + UPDATED_RESIGN_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByLastWorkingDayIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastWorkingDay equals to DEFAULT_LAST_WORKING_DAY
        defaultResignationShouldBeFound("lastWorkingDay.equals=" + DEFAULT_LAST_WORKING_DAY);

        // Get all the resignationList where lastWorkingDay equals to UPDATED_LAST_WORKING_DAY
        defaultResignationShouldNotBeFound("lastWorkingDay.equals=" + UPDATED_LAST_WORKING_DAY);
    }

    @Test
    @Transactional
    void getAllResignationsByLastWorkingDayIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastWorkingDay in DEFAULT_LAST_WORKING_DAY or UPDATED_LAST_WORKING_DAY
        defaultResignationShouldBeFound("lastWorkingDay.in=" + DEFAULT_LAST_WORKING_DAY + "," + UPDATED_LAST_WORKING_DAY);

        // Get all the resignationList where lastWorkingDay equals to UPDATED_LAST_WORKING_DAY
        defaultResignationShouldNotBeFound("lastWorkingDay.in=" + UPDATED_LAST_WORKING_DAY);
    }

    @Test
    @Transactional
    void getAllResignationsByLastWorkingDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastWorkingDay is not null
        defaultResignationShouldBeFound("lastWorkingDay.specified=true");

        // Get all the resignationList where lastWorkingDay is null
        defaultResignationShouldNotBeFound("lastWorkingDay.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultResignationShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the resignationList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultResignationShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultResignationShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the resignationList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultResignationShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId is not null
        defaultResignationShouldBeFound("departmentId.specified=true");

        // Get all the resignationList where departmentId is null
        defaultResignationShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultResignationShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the resignationList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultResignationShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultResignationShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the resignationList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultResignationShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultResignationShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the resignationList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultResignationShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultResignationShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the resignationList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultResignationShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultResignationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the resignationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultResignationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultResignationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the resignationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultResignationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId is not null
        defaultResignationShouldBeFound("employeeId.specified=true");

        // Get all the resignationList where employeeId is null
        defaultResignationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultResignationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the resignationList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultResignationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultResignationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the resignationList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultResignationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultResignationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the resignationList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultResignationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultResignationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the resignationList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultResignationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where status equals to DEFAULT_STATUS
        defaultResignationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the resignationList where status equals to UPDATED_STATUS
        defaultResignationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultResignationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the resignationList where status equals to UPDATED_STATUS
        defaultResignationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where status is not null
        defaultResignationShouldBeFound("status.specified=true");

        // Get all the resignationList where status is null
        defaultResignationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where status contains DEFAULT_STATUS
        defaultResignationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the resignationList where status contains UPDATED_STATUS
        defaultResignationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where status does not contain DEFAULT_STATUS
        defaultResignationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the resignationList where status does not contain UPDATED_STATUS
        defaultResignationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId equals to DEFAULT_COMPANY_ID
        defaultResignationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the resignationList where companyId equals to UPDATED_COMPANY_ID
        defaultResignationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultResignationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the resignationList where companyId equals to UPDATED_COMPANY_ID
        defaultResignationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId is not null
        defaultResignationShouldBeFound("companyId.specified=true");

        // Get all the resignationList where companyId is null
        defaultResignationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultResignationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the resignationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultResignationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultResignationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the resignationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultResignationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId is less than DEFAULT_COMPANY_ID
        defaultResignationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the resignationList where companyId is less than UPDATED_COMPANY_ID
        defaultResignationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultResignationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the resignationList where companyId is greater than SMALLER_COMPANY_ID
        defaultResignationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultResignationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the resignationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultResignationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultResignationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the resignationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultResignationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModified is not null
        defaultResignationShouldBeFound("lastModified.specified=true");

        // Get all the resignationList where lastModified is null
        defaultResignationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultResignationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the resignationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultResignationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultResignationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the resignationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultResignationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModifiedBy is not null
        defaultResignationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the resignationList where lastModifiedBy is null
        defaultResignationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultResignationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the resignationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultResignationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllResignationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        // Get all the resignationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultResignationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the resignationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultResignationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResignationShouldBeFound(String filter) throws Exception {
        restResignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resignation.getId().intValue())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME)))
            .andExpect(jsonPath("$.[*].resignDate").value(hasItem(DEFAULT_RESIGN_DATE.toString())))
            .andExpect(jsonPath("$.[*].noticePeriodIndays").value(hasItem(DEFAULT_NOTICE_PERIOD_INDAYS.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].resignStatus").value(hasItem(DEFAULT_RESIGN_STATUS)))
            .andExpect(jsonPath("$.[*].lastWorkingDay").value(hasItem(DEFAULT_LAST_WORKING_DAY.toString())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restResignationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResignationShouldNotBeFound(String filter) throws Exception {
        restResignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResignationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResignation() throws Exception {
        // Get the resignation
        restResignationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResignation() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();

        // Update the resignation
        Resignation updatedResignation = resignationRepository.findById(resignation.getId()).get();
        // Disconnect from session so that the updates on updatedResignation are not directly saved in db
        em.detach(updatedResignation);
        updatedResignation
            .empName(UPDATED_EMP_NAME)
            .resignDate(UPDATED_RESIGN_DATE)
            .noticePeriodIndays(UPDATED_NOTICE_PERIOD_INDAYS)
            .reason(UPDATED_REASON)
            .resignStatus(UPDATED_RESIGN_STATUS)
            .lastWorkingDay(UPDATED_LAST_WORKING_DAY)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ResignationDTO resignationDTO = resignationMapper.toDto(updatedResignation);

        restResignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resignationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
        Resignation testResignation = resignationList.get(resignationList.size() - 1);
        assertThat(testResignation.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testResignation.getResignDate()).isEqualTo(UPDATED_RESIGN_DATE);
        assertThat(testResignation.getNoticePeriodIndays()).isEqualTo(UPDATED_NOTICE_PERIOD_INDAYS);
        assertThat(testResignation.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testResignation.getResignStatus()).isEqualTo(UPDATED_RESIGN_STATUS);
        assertThat(testResignation.getLastWorkingDay()).isEqualTo(UPDATED_LAST_WORKING_DAY);
        assertThat(testResignation.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testResignation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testResignation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testResignation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testResignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testResignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingResignation() throws Exception {
        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();
        resignation.setId(count.incrementAndGet());

        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resignationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResignation() throws Exception {
        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();
        resignation.setId(count.incrementAndGet());

        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResignation() throws Exception {
        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();
        resignation.setId(count.incrementAndGet());

        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResignationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resignationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResignationWithPatch() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();

        // Update the resignation using partial update
        Resignation partialUpdatedResignation = new Resignation();
        partialUpdatedResignation.setId(resignation.getId());

     //   partialUpdatedResignation.departmentId(UPDATED_DEPARTMENT_ID).companyId(UPDATED_COMPANY_ID);

        restResignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResignation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResignation))
            )
            .andExpect(status().isOk());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
        Resignation testResignation = resignationList.get(resignationList.size() - 1);
        assertThat(testResignation.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testResignation.getResignDate()).isEqualTo(DEFAULT_RESIGN_DATE);
        assertThat(testResignation.getNoticePeriodIndays()).isEqualTo(DEFAULT_NOTICE_PERIOD_INDAYS);
        assertThat(testResignation.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testResignation.getResignStatus()).isEqualTo(DEFAULT_RESIGN_STATUS);
        assertThat(testResignation.getLastWorkingDay()).isEqualTo(DEFAULT_LAST_WORKING_DAY);
        assertThat(testResignation.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testResignation.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testResignation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testResignation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testResignation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testResignation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateResignationWithPatch() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();

        // Update the resignation using partial update
        Resignation partialUpdatedResignation = new Resignation();
        partialUpdatedResignation.setId(resignation.getId());

        partialUpdatedResignation
            .empName(UPDATED_EMP_NAME)
            .resignDate(UPDATED_RESIGN_DATE)
            .noticePeriodIndays(UPDATED_NOTICE_PERIOD_INDAYS)
            .reason(UPDATED_REASON)
            .resignStatus(UPDATED_RESIGN_STATUS)
            .lastWorkingDay(UPDATED_LAST_WORKING_DAY)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restResignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResignation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResignation))
            )
            .andExpect(status().isOk());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
        Resignation testResignation = resignationList.get(resignationList.size() - 1);
        assertThat(testResignation.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testResignation.getResignDate()).isEqualTo(UPDATED_RESIGN_DATE);
        assertThat(testResignation.getNoticePeriodIndays()).isEqualTo(UPDATED_NOTICE_PERIOD_INDAYS);
        assertThat(testResignation.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testResignation.getResignStatus()).isEqualTo(UPDATED_RESIGN_STATUS);
        assertThat(testResignation.getLastWorkingDay()).isEqualTo(UPDATED_LAST_WORKING_DAY);
        assertThat(testResignation.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testResignation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testResignation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testResignation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testResignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testResignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingResignation() throws Exception {
        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();
        resignation.setId(count.incrementAndGet());

        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resignationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResignation() throws Exception {
        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();
        resignation.setId(count.incrementAndGet());

        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResignation() throws Exception {
        int databaseSizeBeforeUpdate = resignationRepository.findAll().size();
        resignation.setId(count.incrementAndGet());

        // Create the Resignation
        ResignationDTO resignationDTO = resignationMapper.toDto(resignation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResignationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resignationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resignation in the database
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResignation() throws Exception {
        // Initialize the database
        resignationRepository.saveAndFlush(resignation);

        int databaseSizeBeforeDelete = resignationRepository.findAll().size();

        // Delete the resignation
        restResignationMockMvc
            .perform(delete(ENTITY_API_URL_ID, resignation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resignation> resignationList = resignationRepository.findAll();
        assertThat(resignationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
