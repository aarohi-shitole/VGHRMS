package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.WorkingHours;
import com.techvg.hrms.repository.WorkingHoursRepository;
import com.techvg.hrms.service.criteria.WorkingHoursCriteria;
import com.techvg.hrms.service.dto.WorkingHoursDTO;
import com.techvg.hrms.service.mapper.WorkingHoursMapper;
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
 * Integration tests for the {@link WorkingHoursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkingHoursResourceIT {

    private static final Double DEFAULT_NO_OF_HOURS = 1D;
    private static final Double UPDATED_NO_OF_HOURS = 2D;

    private static final Long DEFAULT_EMPLOYMENT_TYPE_ID = 1L;
    private static final Long UPDATED_EMPLOYMENT_TYPE_ID = 2L;
    private static final Long SMALLER_EMPLOYMENT_TYPE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REF_TABLE = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TABLE_ID = 1L;
    private static final Long UPDATED_REF_TABLE_ID = 2L;
    private static final Long SMALLER_REF_TABLE_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/working-hours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private WorkingHoursMapper workingHoursMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingHoursMockMvc;

    private WorkingHours workingHours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingHours createEntity(EntityManager em) {
        WorkingHours workingHours = new WorkingHours()
        		   .noOfHours(DEFAULT_NO_OF_HOURS)
            .employmentTypeId(DEFAULT_EMPLOYMENT_TYPE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)
            //            .lastModified(DEFAULT_LAST_MODIFIED)
            //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .refTable(DEFAULT_REF_TABLE)
            .refTableId(DEFAULT_REF_TABLE_ID);
        return workingHours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingHours createUpdatedEntity(EntityManager em) {
        WorkingHours workingHours = new WorkingHours()
            .noOfHours(UPDATED_NO_OF_HOURS)
            .employmentTypeId(UPDATED_EMPLOYMENT_TYPE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            //            .lastModified(UPDATED_LAST_MODIFIED)
            //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID);
        return workingHours;
    }

    @BeforeEach
    public void initTest() {
        workingHours = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkingHours() throws Exception {
        int databaseSizeBeforeCreate = workingHoursRepository.findAll().size();
        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);
        restWorkingHoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingHours testWorkingHours = workingHoursList.get(workingHoursList.size() - 1);
        assertThat(testWorkingHours.getNoOfHours()).isEqualTo(DEFAULT_NO_OF_HOURS);
        assertThat(testWorkingHours.getEmploymentTypeId()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE_ID);
        assertThat(testWorkingHours.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testWorkingHours.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkingHours.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkingHours.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkingHours.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testWorkingHours.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void createWorkingHoursWithExistingId() throws Exception {
        // Create the WorkingHours with an existing ID
        workingHours.setId(1L);
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        int databaseSizeBeforeCreate = workingHoursRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingHoursMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList
        restWorkingHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfHours").value(hasItem(DEFAULT_NO_OF_HOURS)))
            .andExpect(jsonPath("$.[*].employmentTypeId").value(hasItem(DEFAULT_EMPLOYMENT_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())));
    }

    @Test
    @Transactional
    void getWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get the workingHours
        restWorkingHoursMockMvc
            .perform(get(ENTITY_API_URL_ID, workingHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingHours.getId().intValue()))
            .andExpect(jsonPath("$.noOfHours").value(DEFAULT_NO_OF_HOURS))
            .andExpect(jsonPath("$.employmentTypeId").value(DEFAULT_EMPLOYMENT_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
            .andExpect(jsonPath("$.refTableId").value(DEFAULT_REF_TABLE_ID.intValue()));
    }

    @Test
    @Transactional
    void getWorkingHoursByIdFiltering() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        Long id = workingHours.getId();

        defaultWorkingHoursShouldBeFound("id.equals=" + id);
        defaultWorkingHoursShouldNotBeFound("id.notEquals=" + id);

        defaultWorkingHoursShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkingHoursShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkingHoursShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkingHoursShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByNoOfHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where noOfHours equals to DEFAULT_NO_OF_HOURS
        defaultWorkingHoursShouldBeFound("noOfHours.equals=" + DEFAULT_NO_OF_HOURS);

        // Get all the workingHoursList where noOfHours equals to UPDATED_NO_OF_HOURS
        defaultWorkingHoursShouldNotBeFound("noOfHours.equals=" + UPDATED_NO_OF_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByNoOfHoursIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where noOfHours in DEFAULT_NO_OF_HOURS or UPDATED_NO_OF_HOURS
        defaultWorkingHoursShouldBeFound("noOfHours.in=" + DEFAULT_NO_OF_HOURS + "," + UPDATED_NO_OF_HOURS);

        // Get all the workingHoursList where noOfHours equals to UPDATED_NO_OF_HOURS
        defaultWorkingHoursShouldNotBeFound("noOfHours.in=" + UPDATED_NO_OF_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByNoOfHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where noOfHours is not null
        defaultWorkingHoursShouldBeFound("noOfHours.specified=true");

        // Get all the workingHoursList where noOfHours is null
        defaultWorkingHoursShouldNotBeFound("noOfHours.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByNoOfHoursContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where noOfHours contains DEFAULT_NO_OF_HOURS
        defaultWorkingHoursShouldBeFound("noOfHours.contains=" + DEFAULT_NO_OF_HOURS);

        // Get all the workingHoursList where noOfHours contains UPDATED_NO_OF_HOURS
        defaultWorkingHoursShouldNotBeFound("noOfHours.contains=" + UPDATED_NO_OF_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByNoOfHoursNotContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where noOfHours does not contain DEFAULT_NO_OF_HOURS
        defaultWorkingHoursShouldNotBeFound("noOfHours.doesNotContain=" + DEFAULT_NO_OF_HOURS);

        // Get all the workingHoursList where noOfHours does not contain UPDATED_NO_OF_HOURS
        defaultWorkingHoursShouldBeFound("noOfHours.doesNotContain=" + UPDATED_NO_OF_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId equals to DEFAULT_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldBeFound("employmentTypeId.equals=" + DEFAULT_EMPLOYMENT_TYPE_ID);

        // Get all the workingHoursList where employmentTypeId equals to UPDATED_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.equals=" + UPDATED_EMPLOYMENT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId in DEFAULT_EMPLOYMENT_TYPE_ID or UPDATED_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldBeFound("employmentTypeId.in=" + DEFAULT_EMPLOYMENT_TYPE_ID + "," + UPDATED_EMPLOYMENT_TYPE_ID);

        // Get all the workingHoursList where employmentTypeId equals to UPDATED_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.in=" + UPDATED_EMPLOYMENT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId is not null
        defaultWorkingHoursShouldBeFound("employmentTypeId.specified=true");

        // Get all the workingHoursList where employmentTypeId is null
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId is greater than or equal to DEFAULT_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldBeFound("employmentTypeId.greaterThanOrEqual=" + DEFAULT_EMPLOYMENT_TYPE_ID);

        // Get all the workingHoursList where employmentTypeId is greater than or equal to UPDATED_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.greaterThanOrEqual=" + UPDATED_EMPLOYMENT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId is less than or equal to DEFAULT_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldBeFound("employmentTypeId.lessThanOrEqual=" + DEFAULT_EMPLOYMENT_TYPE_ID);

        // Get all the workingHoursList where employmentTypeId is less than or equal to SMALLER_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.lessThanOrEqual=" + SMALLER_EMPLOYMENT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId is less than DEFAULT_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.lessThan=" + DEFAULT_EMPLOYMENT_TYPE_ID);

        // Get all the workingHoursList where employmentTypeId is less than UPDATED_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldBeFound("employmentTypeId.lessThan=" + UPDATED_EMPLOYMENT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByEmploymentTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where employmentTypeId is greater than DEFAULT_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldNotBeFound("employmentTypeId.greaterThan=" + DEFAULT_EMPLOYMENT_TYPE_ID);

        // Get all the workingHoursList where employmentTypeId is greater than SMALLER_EMPLOYMENT_TYPE_ID
        defaultWorkingHoursShouldBeFound("employmentTypeId.greaterThan=" + SMALLER_EMPLOYMENT_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId equals to DEFAULT_COMPANY_ID
        defaultWorkingHoursShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the workingHoursList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkingHoursShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultWorkingHoursShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the workingHoursList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkingHoursShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId is not null
        defaultWorkingHoursShouldBeFound("companyId.specified=true");

        // Get all the workingHoursList where companyId is null
        defaultWorkingHoursShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultWorkingHoursShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workingHoursList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultWorkingHoursShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultWorkingHoursShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workingHoursList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultWorkingHoursShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId is less than DEFAULT_COMPANY_ID
        defaultWorkingHoursShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the workingHoursList where companyId is less than UPDATED_COMPANY_ID
        defaultWorkingHoursShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where companyId is greater than DEFAULT_COMPANY_ID
        defaultWorkingHoursShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the workingHoursList where companyId is greater than SMALLER_COMPANY_ID
        defaultWorkingHoursShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where status equals to DEFAULT_STATUS
        defaultWorkingHoursShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the workingHoursList where status equals to UPDATED_STATUS
        defaultWorkingHoursShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWorkingHoursShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the workingHoursList where status equals to UPDATED_STATUS
        defaultWorkingHoursShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where status is not null
        defaultWorkingHoursShouldBeFound("status.specified=true");

        // Get all the workingHoursList where status is null
        defaultWorkingHoursShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByStatusContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where status contains DEFAULT_STATUS
        defaultWorkingHoursShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the workingHoursList where status contains UPDATED_STATUS
        defaultWorkingHoursShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where status does not contain DEFAULT_STATUS
        defaultWorkingHoursShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the workingHoursList where status does not contain UPDATED_STATUS
        defaultWorkingHoursShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWorkingHoursShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the workingHoursList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkingHoursShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWorkingHoursShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the workingHoursList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkingHoursShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModified is not null
        defaultWorkingHoursShouldBeFound("lastModified.specified=true");

        // Get all the workingHoursList where lastModified is null
        defaultWorkingHoursShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkingHoursShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workingHoursList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkingHoursShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkingHoursShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workingHoursList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkingHoursShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModifiedBy is not null
        defaultWorkingHoursShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workingHoursList where lastModifiedBy is null
        defaultWorkingHoursShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkingHoursShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workingHoursList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkingHoursShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkingHoursShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workingHoursList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkingHoursShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTable equals to DEFAULT_REF_TABLE
        defaultWorkingHoursShouldBeFound("refTable.equals=" + DEFAULT_REF_TABLE);

        // Get all the workingHoursList where refTable equals to UPDATED_REF_TABLE
        defaultWorkingHoursShouldNotBeFound("refTable.equals=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTable in DEFAULT_REF_TABLE or UPDATED_REF_TABLE
        defaultWorkingHoursShouldBeFound("refTable.in=" + DEFAULT_REF_TABLE + "," + UPDATED_REF_TABLE);

        // Get all the workingHoursList where refTable equals to UPDATED_REF_TABLE
        defaultWorkingHoursShouldNotBeFound("refTable.in=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTable is not null
        defaultWorkingHoursShouldBeFound("refTable.specified=true");

        // Get all the workingHoursList where refTable is null
        defaultWorkingHoursShouldNotBeFound("refTable.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTable contains DEFAULT_REF_TABLE
        defaultWorkingHoursShouldBeFound("refTable.contains=" + DEFAULT_REF_TABLE);

        // Get all the workingHoursList where refTable contains UPDATED_REF_TABLE
        defaultWorkingHoursShouldNotBeFound("refTable.contains=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTable does not contain DEFAULT_REF_TABLE
        defaultWorkingHoursShouldNotBeFound("refTable.doesNotContain=" + DEFAULT_REF_TABLE);

        // Get all the workingHoursList where refTable does not contain UPDATED_REF_TABLE
        defaultWorkingHoursShouldBeFound("refTable.doesNotContain=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId equals to DEFAULT_REF_TABLE_ID
        defaultWorkingHoursShouldBeFound("refTableId.equals=" + DEFAULT_REF_TABLE_ID);

        // Get all the workingHoursList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultWorkingHoursShouldNotBeFound("refTableId.equals=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId in DEFAULT_REF_TABLE_ID or UPDATED_REF_TABLE_ID
        defaultWorkingHoursShouldBeFound("refTableId.in=" + DEFAULT_REF_TABLE_ID + "," + UPDATED_REF_TABLE_ID);

        // Get all the workingHoursList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultWorkingHoursShouldNotBeFound("refTableId.in=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId is not null
        defaultWorkingHoursShouldBeFound("refTableId.specified=true");

        // Get all the workingHoursList where refTableId is null
        defaultWorkingHoursShouldNotBeFound("refTableId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId is greater than or equal to DEFAULT_REF_TABLE_ID
        defaultWorkingHoursShouldBeFound("refTableId.greaterThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the workingHoursList where refTableId is greater than or equal to UPDATED_REF_TABLE_ID
        defaultWorkingHoursShouldNotBeFound("refTableId.greaterThanOrEqual=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId is less than or equal to DEFAULT_REF_TABLE_ID
        defaultWorkingHoursShouldBeFound("refTableId.lessThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the workingHoursList where refTableId is less than or equal to SMALLER_REF_TABLE_ID
        defaultWorkingHoursShouldNotBeFound("refTableId.lessThanOrEqual=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId is less than DEFAULT_REF_TABLE_ID
        defaultWorkingHoursShouldNotBeFound("refTableId.lessThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the workingHoursList where refTableId is less than UPDATED_REF_TABLE_ID
        defaultWorkingHoursShouldBeFound("refTableId.lessThan=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllWorkingHoursByRefTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        // Get all the workingHoursList where refTableId is greater than DEFAULT_REF_TABLE_ID
        defaultWorkingHoursShouldNotBeFound("refTableId.greaterThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the workingHoursList where refTableId is greater than SMALLER_REF_TABLE_ID
        defaultWorkingHoursShouldBeFound("refTableId.greaterThan=" + SMALLER_REF_TABLE_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkingHoursShouldBeFound(String filter) throws Exception {
        restWorkingHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfHours").value(hasItem(DEFAULT_NO_OF_HOURS)))
            .andExpect(jsonPath("$.[*].employmentTypeId").value(hasItem(DEFAULT_EMPLOYMENT_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())));

        // Check, that the count call also returns 1
        restWorkingHoursMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkingHoursShouldNotBeFound(String filter) throws Exception {
        restWorkingHoursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkingHoursMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkingHours() throws Exception {
        // Get the workingHours
        restWorkingHoursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();

        // Update the workingHours
        WorkingHours updatedWorkingHours = workingHoursRepository.findById(workingHours.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingHours are not directly saved in db
        em.detach(updatedWorkingHours);
        updatedWorkingHours
            .noOfHours(UPDATED_NO_OF_HOURS)
            .employmentTypeId(UPDATED_EMPLOYMENT_TYPE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            //            .lastModified(UPDATED_LAST_MODIFIED)
            //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID);
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(updatedWorkingHours);

        restWorkingHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingHoursDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
        WorkingHours testWorkingHours = workingHoursList.get(workingHoursList.size() - 1);
        assertThat(testWorkingHours.getNoOfHours()).isEqualTo(UPDATED_NO_OF_HOURS);
        assertThat(testWorkingHours.getEmploymentTypeId()).isEqualTo(UPDATED_EMPLOYMENT_TYPE_ID);
        assertThat(testWorkingHours.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkingHours.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkingHours.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkingHours.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkingHours.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testWorkingHours.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void putNonExistingWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();
        workingHours.setId(count.incrementAndGet());

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingHoursDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();
        workingHours.setId(count.incrementAndGet());

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();
        workingHours.setId(count.incrementAndGet());

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkingHoursWithPatch() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();

        // Update the workingHours using partial update
        WorkingHours partialUpdatedWorkingHours = new WorkingHours();
        partialUpdatedWorkingHours.setId(workingHours.getId());

        partialUpdatedWorkingHours.employmentTypeId(UPDATED_EMPLOYMENT_TYPE_ID)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restWorkingHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingHours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingHours))
            )
            .andExpect(status().isOk());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
        WorkingHours testWorkingHours = workingHoursList.get(workingHoursList.size() - 1);
        assertThat(testWorkingHours.getNoOfHours()).isEqualTo(DEFAULT_NO_OF_HOURS);
        assertThat(testWorkingHours.getEmploymentTypeId()).isEqualTo(UPDATED_EMPLOYMENT_TYPE_ID);
        assertThat(testWorkingHours.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkingHours.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkingHours.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkingHours.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkingHours.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testWorkingHours.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void fullUpdateWorkingHoursWithPatch() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();

        // Update the workingHours using partial update
        WorkingHours partialUpdatedWorkingHours = new WorkingHours();
        partialUpdatedWorkingHours.setId(workingHours.getId());

        partialUpdatedWorkingHours
            .noOfHours(UPDATED_NO_OF_HOURS)
            .employmentTypeId(UPDATED_EMPLOYMENT_TYPE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)
            //            .lastModified(UPDATED_LAST_MODIFIED)
            //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID);

        restWorkingHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingHours.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingHours))
            )
            .andExpect(status().isOk());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
        WorkingHours testWorkingHours = workingHoursList.get(workingHoursList.size() - 1);
        assertThat(testWorkingHours.getNoOfHours()).isEqualTo(UPDATED_NO_OF_HOURS);
        assertThat(testWorkingHours.getEmploymentTypeId()).isEqualTo(UPDATED_EMPLOYMENT_TYPE_ID);
        assertThat(testWorkingHours.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkingHours.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkingHours.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkingHours.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkingHours.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testWorkingHours.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();
        workingHours.setId(count.incrementAndGet());

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workingHoursDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();
        workingHours.setId(count.incrementAndGet());

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkingHours() throws Exception {
        int databaseSizeBeforeUpdate = workingHoursRepository.findAll().size();
        workingHours.setId(count.incrementAndGet());

        // Create the WorkingHours
        WorkingHoursDTO workingHoursDTO = workingHoursMapper.toDto(workingHours);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingHoursMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingHoursDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingHours in the database
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkingHours() throws Exception {
        // Initialize the database
        workingHoursRepository.saveAndFlush(workingHours);

        int databaseSizeBeforeDelete = workingHoursRepository.findAll().size();

        // Delete the workingHours
        restWorkingHoursMockMvc
            .perform(delete(ENTITY_API_URL_ID, workingHours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingHours> workingHoursList = workingHoursRepository.findAll();
        assertThat(workingHoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
