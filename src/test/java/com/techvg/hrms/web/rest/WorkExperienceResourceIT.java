package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.repository.WorkExperienceRepository;
import com.techvg.hrms.service.criteria.WorkExperienceCriteria;
import com.techvg.hrms.service.dto.WorkExperienceDTO;
import com.techvg.hrms.service.mapper.WorkExperienceMapper;
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
 * Integration tests for the {@link WorkExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkExperienceResourceIT {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ADDRESS_ID = 1L;
    private static final Long UPDATED_ADDRESS_ID = 2L;
    private static final Long SMALLER_ADDRESS_ID = 1L - 1L;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_JOB_DESC = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private WorkExperienceMapper workExperienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkExperienceMockMvc;

    private WorkExperience workExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .jobTitle(DEFAULT_JOB_TITLE)
            .companyName(DEFAULT_COMPANY_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .addressId(DEFAULT_ADDRESS_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .jobDesc(DEFAULT_JOB_DESC)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return workExperience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createUpdatedEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .addressId(UPDATED_ADDRESS_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .jobDesc(UPDATED_JOB_DESC)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return workExperience;
    }

    @BeforeEach
    public void initTest() {
        workExperience = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkExperience() throws Exception {
        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();
        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkExperience.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testWorkExperience.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(DEFAULT_JOB_DESC);
        assertThat(testWorkExperience.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createWorkExperienceWithExistingId() throws Exception {
        // Create the WorkExperience with an existing ID
        workExperience.setId(1L);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkExperiences() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].jobDesc").value(hasItem(DEFAULT_JOB_DESC)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get the workExperience
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, workExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workExperience.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID.intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.jobDesc").value(DEFAULT_JOB_DESC))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getWorkExperiencesByIdFiltering() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        Long id = workExperience.getId();

        defaultWorkExperienceShouldBeFound("id.equals=" + id);
        defaultWorkExperienceShouldNotBeFound("id.notEquals=" + id);

        defaultWorkExperienceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkExperienceShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkExperienceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkExperienceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the workExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle is not null
        defaultWorkExperienceShouldBeFound("jobTitle.specified=true");

        // Get all the workExperienceList where jobTitle is null
        defaultWorkExperienceShouldNotBeFound("jobTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle contains DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.contains=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle contains UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.contains=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle does not contain DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.doesNotContain=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle does not contain UPDATED_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.doesNotContain=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName equals to DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the workExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName is not null
        defaultWorkExperienceShouldBeFound("companyName.specified=true");

        // Get all the workExperienceList where companyName is null
        defaultWorkExperienceShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName contains DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName contains UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName does not contain UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate equals to DEFAULT_START_DATE
        defaultWorkExperienceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the workExperienceList where startDate equals to UPDATED_START_DATE
        defaultWorkExperienceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultWorkExperienceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the workExperienceList where startDate equals to UPDATED_START_DATE
        defaultWorkExperienceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate is not null
        defaultWorkExperienceShouldBeFound("startDate.specified=true");

        // Get all the workExperienceList where startDate is null
        defaultWorkExperienceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate equals to DEFAULT_END_DATE
        defaultWorkExperienceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the workExperienceList where endDate equals to UPDATED_END_DATE
        defaultWorkExperienceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultWorkExperienceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the workExperienceList where endDate equals to UPDATED_END_DATE
        defaultWorkExperienceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate is not null
        defaultWorkExperienceShouldBeFound("endDate.specified=true");

        // Get all the workExperienceList where endDate is null
        defaultWorkExperienceShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId equals to DEFAULT_ADDRESS_ID
        defaultWorkExperienceShouldBeFound("addressId.equals=" + DEFAULT_ADDRESS_ID);

        // Get all the workExperienceList where addressId equals to UPDATED_ADDRESS_ID
        defaultWorkExperienceShouldNotBeFound("addressId.equals=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId in DEFAULT_ADDRESS_ID or UPDATED_ADDRESS_ID
        defaultWorkExperienceShouldBeFound("addressId.in=" + DEFAULT_ADDRESS_ID + "," + UPDATED_ADDRESS_ID);

        // Get all the workExperienceList where addressId equals to UPDATED_ADDRESS_ID
        defaultWorkExperienceShouldNotBeFound("addressId.in=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId is not null
        defaultWorkExperienceShouldBeFound("addressId.specified=true");

        // Get all the workExperienceList where addressId is null
        defaultWorkExperienceShouldNotBeFound("addressId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId is greater than or equal to DEFAULT_ADDRESS_ID
        defaultWorkExperienceShouldBeFound("addressId.greaterThanOrEqual=" + DEFAULT_ADDRESS_ID);

        // Get all the workExperienceList where addressId is greater than or equal to UPDATED_ADDRESS_ID
        defaultWorkExperienceShouldNotBeFound("addressId.greaterThanOrEqual=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId is less than or equal to DEFAULT_ADDRESS_ID
        defaultWorkExperienceShouldBeFound("addressId.lessThanOrEqual=" + DEFAULT_ADDRESS_ID);

        // Get all the workExperienceList where addressId is less than or equal to SMALLER_ADDRESS_ID
        defaultWorkExperienceShouldNotBeFound("addressId.lessThanOrEqual=" + SMALLER_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId is less than DEFAULT_ADDRESS_ID
        defaultWorkExperienceShouldNotBeFound("addressId.lessThan=" + DEFAULT_ADDRESS_ID);

        // Get all the workExperienceList where addressId is less than UPDATED_ADDRESS_ID
        defaultWorkExperienceShouldBeFound("addressId.lessThan=" + UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByAddressIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where addressId is greater than DEFAULT_ADDRESS_ID
        defaultWorkExperienceShouldNotBeFound("addressId.greaterThan=" + DEFAULT_ADDRESS_ID);

        // Get all the workExperienceList where addressId is greater than SMALLER_ADDRESS_ID
        defaultWorkExperienceShouldBeFound("addressId.greaterThan=" + SMALLER_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is not null
        defaultWorkExperienceShouldBeFound("employeeId.specified=true");

        // Get all the workExperienceList where employeeId is null
        defaultWorkExperienceShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultWorkExperienceShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the workExperienceList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultWorkExperienceShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId equals to DEFAULT_COMPANY_ID
        defaultWorkExperienceShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the workExperienceList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkExperienceShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultWorkExperienceShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the workExperienceList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkExperienceShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId is not null
        defaultWorkExperienceShouldBeFound("companyId.specified=true");

        // Get all the workExperienceList where companyId is null
        defaultWorkExperienceShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultWorkExperienceShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workExperienceList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultWorkExperienceShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultWorkExperienceShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workExperienceList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultWorkExperienceShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId is less than DEFAULT_COMPANY_ID
        defaultWorkExperienceShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the workExperienceList where companyId is less than UPDATED_COMPANY_ID
        defaultWorkExperienceShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyId is greater than DEFAULT_COMPANY_ID
        defaultWorkExperienceShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the workExperienceList where companyId is greater than SMALLER_COMPANY_ID
        defaultWorkExperienceShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc equals to DEFAULT_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.equals=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc equals to UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.equals=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc in DEFAULT_JOB_DESC or UPDATED_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.in=" + DEFAULT_JOB_DESC + "," + UPDATED_JOB_DESC);

        // Get all the workExperienceList where jobDesc equals to UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.in=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc is not null
        defaultWorkExperienceShouldBeFound("jobDesc.specified=true");

        // Get all the workExperienceList where jobDesc is null
        defaultWorkExperienceShouldNotBeFound("jobDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc contains DEFAULT_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.contains=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc contains UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.contains=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc does not contain DEFAULT_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.doesNotContain=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc does not contain UPDATED_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.doesNotContain=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where status equals to DEFAULT_STATUS
        defaultWorkExperienceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the workExperienceList where status equals to UPDATED_STATUS
        defaultWorkExperienceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWorkExperienceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the workExperienceList where status equals to UPDATED_STATUS
        defaultWorkExperienceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where status is not null
        defaultWorkExperienceShouldBeFound("status.specified=true");

        // Get all the workExperienceList where status is null
        defaultWorkExperienceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStatusContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where status contains DEFAULT_STATUS
        defaultWorkExperienceShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the workExperienceList where status contains UPDATED_STATUS
        defaultWorkExperienceShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where status does not contain DEFAULT_STATUS
        defaultWorkExperienceShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the workExperienceList where status does not contain UPDATED_STATUS
        defaultWorkExperienceShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWorkExperienceShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the workExperienceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the workExperienceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified is not null
        defaultWorkExperienceShouldBeFound("lastModified.specified=true");

        // Get all the workExperienceList where lastModified is null
        defaultWorkExperienceShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy is not null
        defaultWorkExperienceShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workExperienceList where lastModifiedBy is null
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkExperienceShouldBeFound(String filter) throws Exception {
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].jobDesc").value(hasItem(DEFAULT_JOB_DESC)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkExperienceShouldNotBeFound(String filter) throws Exception {
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkExperience() throws Exception {
        // Get the workExperience
        restWorkExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience
        WorkExperience updatedWorkExperience = workExperienceRepository.findById(workExperience.getId()).get();
        // Disconnect from session so that the updates on updatedWorkExperience are not directly saved in db
        em.detach(updatedWorkExperience);
        updatedWorkExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .addressId(UPDATED_ADDRESS_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .jobDesc(UPDATED_JOB_DESC)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(updatedWorkExperience);

        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testWorkExperience.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
        assertThat(testWorkExperience.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .addressId(UPDATED_ADDRESS_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .jobDesc(UPDATED_JOB_DESC)//            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testWorkExperience.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
        assertThat(testWorkExperience.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .addressId(UPDATED_ADDRESS_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .jobDesc(UPDATED_JOB_DESC)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testWorkExperience.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testWorkExperience.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
        assertThat(testWorkExperience.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeDelete = workExperienceRepository.findAll().size();

        // Delete the workExperience
        restWorkExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workExperience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
