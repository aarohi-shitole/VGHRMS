package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Trainer;
import com.techvg.hrms.repository.TrainerRepository;
import com.techvg.hrms.service.criteria.TrainerCriteria;
import com.techvg.hrms.service.dto.TrainerDTO;
import com.techvg.hrms.service.mapper.TrainerMapper;
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
 * Integration tests for the {@link TrainerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/trainers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainerMockMvc;

    private Trainer trainer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainer createEntity(EntityManager em) {
        Trainer trainer = new Trainer()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .description(DEFAULT_DESCRIPTION)
            .role(DEFAULT_ROLE)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return trainer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trainer createUpdatedEntity(EntityManager em) {
        Trainer trainer = new Trainer()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return trainer;
    }

    @BeforeEach
    public void initTest() {
        trainer = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainer() throws Exception {
        int databaseSizeBeforeCreate = trainerRepository.findAll().size();
        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);
        restTrainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isCreated());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeCreate + 1);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTrainer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTrainer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrainer.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testTrainer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainer.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testTrainer.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTrainer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTrainer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTrainerWithExistingId() throws Exception {
        // Create the Trainer with an existing ID
        trainer.setId(1L);
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        int databaseSizeBeforeCreate = trainerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainers() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get the trainer
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL_ID, trainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainer.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTrainersByIdFiltering() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        Long id = trainer.getId();

        defaultTrainerShouldBeFound("id.equals=" + id);
        defaultTrainerShouldNotBeFound("id.notEquals=" + id);

        defaultTrainerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainerShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrainersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where firstName equals to DEFAULT_FIRST_NAME
        defaultTrainerShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the trainerList where firstName equals to UPDATED_FIRST_NAME
        defaultTrainerShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultTrainerShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the trainerList where firstName equals to UPDATED_FIRST_NAME
        defaultTrainerShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where firstName is not null
        defaultTrainerShouldBeFound("firstName.specified=true");

        // Get all the trainerList where firstName is null
        defaultTrainerShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where firstName contains DEFAULT_FIRST_NAME
        defaultTrainerShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the trainerList where firstName contains UPDATED_FIRST_NAME
        defaultTrainerShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where firstName does not contain DEFAULT_FIRST_NAME
        defaultTrainerShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the trainerList where firstName does not contain UPDATED_FIRST_NAME
        defaultTrainerShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastName equals to DEFAULT_LAST_NAME
        defaultTrainerShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the trainerList where lastName equals to UPDATED_LAST_NAME
        defaultTrainerShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultTrainerShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the trainerList where lastName equals to UPDATED_LAST_NAME
        defaultTrainerShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastName is not null
        defaultTrainerShouldBeFound("lastName.specified=true");

        // Get all the trainerList where lastName is null
        defaultTrainerShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastName contains DEFAULT_LAST_NAME
        defaultTrainerShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the trainerList where lastName contains UPDATED_LAST_NAME
        defaultTrainerShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastName does not contain DEFAULT_LAST_NAME
        defaultTrainerShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the trainerList where lastName does not contain UPDATED_LAST_NAME
        defaultTrainerShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllTrainersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where description equals to DEFAULT_DESCRIPTION
        defaultTrainerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the trainerList where description equals to UPDATED_DESCRIPTION
        defaultTrainerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTrainerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the trainerList where description equals to UPDATED_DESCRIPTION
        defaultTrainerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where description is not null
        defaultTrainerShouldBeFound("description.specified=true");

        // Get all the trainerList where description is null
        defaultTrainerShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where description contains DEFAULT_DESCRIPTION
        defaultTrainerShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the trainerList where description contains UPDATED_DESCRIPTION
        defaultTrainerShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where description does not contain DEFAULT_DESCRIPTION
        defaultTrainerShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the trainerList where description does not contain UPDATED_DESCRIPTION
        defaultTrainerShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainersByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where role equals to DEFAULT_ROLE
        defaultTrainerShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the trainerList where role equals to UPDATED_ROLE
        defaultTrainerShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllTrainersByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultTrainerShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the trainerList where role equals to UPDATED_ROLE
        defaultTrainerShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllTrainersByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where role is not null
        defaultTrainerShouldBeFound("role.specified=true");

        // Get all the trainerList where role is null
        defaultTrainerShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByRoleContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where role contains DEFAULT_ROLE
        defaultTrainerShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the trainerList where role contains UPDATED_ROLE
        defaultTrainerShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllTrainersByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where role does not contain DEFAULT_ROLE
        defaultTrainerShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the trainerList where role does not contain UPDATED_ROLE
        defaultTrainerShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllTrainersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where status equals to DEFAULT_STATUS
        defaultTrainerShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the trainerList where status equals to UPDATED_STATUS
        defaultTrainerShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTrainerShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the trainerList where status equals to UPDATED_STATUS
        defaultTrainerShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where status is not null
        defaultTrainerShouldBeFound("status.specified=true");

        // Get all the trainerList where status is null
        defaultTrainerShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByStatusContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where status contains DEFAULT_STATUS
        defaultTrainerShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the trainerList where status contains UPDATED_STATUS
        defaultTrainerShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainersByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where status does not contain DEFAULT_STATUS
        defaultTrainerShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the trainerList where status does not contain UPDATED_STATUS
        defaultTrainerShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultTrainerShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainerList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultTrainerShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultTrainerShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the trainerList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultTrainerShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId is not null
        defaultTrainerShouldBeFound("employeeId.specified=true");

        // Get all the trainerList where employeeId is null
        defaultTrainerShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultTrainerShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainerList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultTrainerShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultTrainerShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainerList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultTrainerShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultTrainerShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainerList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultTrainerShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultTrainerShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainerList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultTrainerShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId equals to DEFAULT_COMPANY_ID
        defaultTrainerShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the trainerList where companyId equals to UPDATED_COMPANY_ID
        defaultTrainerShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultTrainerShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the trainerList where companyId equals to UPDATED_COMPANY_ID
        defaultTrainerShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId is not null
        defaultTrainerShouldBeFound("companyId.specified=true");

        // Get all the trainerList where companyId is null
        defaultTrainerShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultTrainerShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the trainerList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultTrainerShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultTrainerShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the trainerList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultTrainerShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId is less than DEFAULT_COMPANY_ID
        defaultTrainerShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the trainerList where companyId is less than UPDATED_COMPANY_ID
        defaultTrainerShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where companyId is greater than DEFAULT_COMPANY_ID
        defaultTrainerShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the trainerList where companyId is greater than SMALLER_COMPANY_ID
        defaultTrainerShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTrainerShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the trainerList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTrainerShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTrainerShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the trainerList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTrainerShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModified is not null
        defaultTrainerShouldBeFound("lastModified.specified=true");

        // Get all the trainerList where lastModified is null
        defaultTrainerShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTrainerShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainerList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTrainerShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTrainerShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the trainerList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTrainerShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModifiedBy is not null
        defaultTrainerShouldBeFound("lastModifiedBy.specified=true");

        // Get all the trainerList where lastModifiedBy is null
        defaultTrainerShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTrainerShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainerList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTrainerShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        // Get all the trainerList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTrainerShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainerList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTrainerShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainerShouldBeFound(String filter) throws Exception {
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainerShouldNotBeFound(String filter) throws Exception {
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTrainer() throws Exception {
        // Get the trainer
        restTrainerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer
        Trainer updatedTrainer = trainerRepository.findById(trainer.getId()).get();
        // Disconnect from session so that the updates on updatedTrainer are not directly saved in db
        em.detach(updatedTrainer);
        updatedTrainer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TrainerDTO trainerDTO = trainerMapper.toDto(updatedTrainer);

        restTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTrainer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTrainer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainer.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testTrainer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainer.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTrainer.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTrainer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTrainer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainerWithPatch() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer using partial update
        Trainer partialUpdatedTrainer = new Trainer();
        partialUpdatedTrainer.setId(trainer.getId());

        partialUpdatedTrainer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainer))
            )
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTrainer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTrainer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainer.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testTrainer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainer.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testTrainer.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTrainer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTrainer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTrainerWithPatch() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();

        // Update the trainer using partial update
        Trainer partialUpdatedTrainer = new Trainer();
        partialUpdatedTrainer.setId(trainer.getId());

        partialUpdatedTrainer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainer))
            )
            .andExpect(status().isOk());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
        Trainer testTrainer = trainerList.get(trainerList.size() - 1);
        assertThat(testTrainer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTrainer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTrainer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainer.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testTrainer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainer.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTrainer.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTrainer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTrainer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainer() throws Exception {
        int databaseSizeBeforeUpdate = trainerRepository.findAll().size();
        trainer.setId(count.incrementAndGet());

        // Create the Trainer
        TrainerDTO trainerDTO = trainerMapper.toDto(trainer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trainer in the database
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainer() throws Exception {
        // Initialize the database
        trainerRepository.saveAndFlush(trainer);

        int databaseSizeBeforeDelete = trainerRepository.findAll().size();

        // Delete the trainer
        restTrainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trainer> trainerList = trainerRepository.findAll();
        assertThat(trainerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
