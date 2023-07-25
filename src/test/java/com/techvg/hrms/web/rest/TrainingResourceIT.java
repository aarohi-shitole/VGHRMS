package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Training;
import com.techvg.hrms.repository.TrainingRepository;
import com.techvg.hrms.service.criteria.TrainingCriteria;
import com.techvg.hrms.service.dto.TrainingDTO;
import com.techvg.hrms.service.mapper.TrainingMapper;
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
 * Integration tests for the {@link TrainingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainingResourceIT {

    private static final Double DEFAULT_TRAINING_COST = 1D;
    private static final Double UPDATED_TRAINING_COST = 2D;
    private static final Double SMALLER_TRAINING_COST = 1D - 1D;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TRAINING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_TRAINER_ID = 1L;
    private static final Long UPDATED_TRAINER_ID = 2L;
    private static final Long SMALLER_TRAINER_ID = 1L - 1L;

    private static final Long DEFAULT_TRAINING_TYPE_ID = 1L;
    private static final Long UPDATED_TRAINING_TYPE_ID = 2L;
    private static final Long SMALLER_TRAINING_TYPE_ID = 1L - 1L;

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/trainings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingMapper trainingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingMockMvc;

    private Training training;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .trainingCost(DEFAULT_TRAINING_COST)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION)
            .trainingStatus(DEFAULT_TRAINING_STATUS)
            .status(DEFAULT_STATUS)
            .trainerId(DEFAULT_TRAINER_ID)
            .trainingTypeId(DEFAULT_TRAINING_TYPE_ID)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return training;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createUpdatedEntity(EntityManager em) {
        Training training = new Training()
            .trainingCost(UPDATED_TRAINING_COST)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .trainingStatus(UPDATED_TRAINING_STATUS)
            .status(UPDATED_STATUS)
            .trainerId(UPDATED_TRAINER_ID)
            .trainingTypeId(UPDATED_TRAINING_TYPE_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return training;
    }

    @BeforeEach
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);
        restTrainingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTrainingCost()).isEqualTo(DEFAULT_TRAINING_COST);
        assertThat(testTraining.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTraining.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTraining.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTraining.getTrainingStatus()).isEqualTo(DEFAULT_TRAINING_STATUS);
        assertThat(testTraining.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTraining.getTrainerId()).isEqualTo(DEFAULT_TRAINER_ID);
        assertThat(testTraining.getTrainingTypeId()).isEqualTo(DEFAULT_TRAINING_TYPE_ID);
        assertThat(testTraining.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testTraining.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testTraining.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTraining.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTraining.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTrainingWithExistingId() throws Exception {
        // Create the Training with an existing ID
        training.setId(1L);
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainingCost").value(hasItem(DEFAULT_TRAINING_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].trainingStatus").value(hasItem(DEFAULT_TRAINING_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].trainerId").value(hasItem(DEFAULT_TRAINER_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainingTypeId").value(hasItem(DEFAULT_TRAINING_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL_ID, training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.trainingCost").value(DEFAULT_TRAINING_COST.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.trainingStatus").value(DEFAULT_TRAINING_STATUS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.trainerId").value(DEFAULT_TRAINER_ID.intValue()))
            .andExpect(jsonPath("$.trainingTypeId").value(DEFAULT_TRAINING_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTrainingsByIdFiltering() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        Long id = training.getId();

        defaultTrainingShouldBeFound("id.equals=" + id);
        defaultTrainingShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost equals to DEFAULT_TRAINING_COST
        defaultTrainingShouldBeFound("trainingCost.equals=" + DEFAULT_TRAINING_COST);

        // Get all the trainingList where trainingCost equals to UPDATED_TRAINING_COST
        defaultTrainingShouldNotBeFound("trainingCost.equals=" + UPDATED_TRAINING_COST);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost in DEFAULT_TRAINING_COST or UPDATED_TRAINING_COST
        defaultTrainingShouldBeFound("trainingCost.in=" + DEFAULT_TRAINING_COST + "," + UPDATED_TRAINING_COST);

        // Get all the trainingList where trainingCost equals to UPDATED_TRAINING_COST
        defaultTrainingShouldNotBeFound("trainingCost.in=" + UPDATED_TRAINING_COST);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost is not null
        defaultTrainingShouldBeFound("trainingCost.specified=true");

        // Get all the trainingList where trainingCost is null
        defaultTrainingShouldNotBeFound("trainingCost.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost is greater than or equal to DEFAULT_TRAINING_COST
        defaultTrainingShouldBeFound("trainingCost.greaterThanOrEqual=" + DEFAULT_TRAINING_COST);

        // Get all the trainingList where trainingCost is greater than or equal to UPDATED_TRAINING_COST
        defaultTrainingShouldNotBeFound("trainingCost.greaterThanOrEqual=" + UPDATED_TRAINING_COST);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost is less than or equal to DEFAULT_TRAINING_COST
        defaultTrainingShouldBeFound("trainingCost.lessThanOrEqual=" + DEFAULT_TRAINING_COST);

        // Get all the trainingList where trainingCost is less than or equal to SMALLER_TRAINING_COST
        defaultTrainingShouldNotBeFound("trainingCost.lessThanOrEqual=" + SMALLER_TRAINING_COST);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost is less than DEFAULT_TRAINING_COST
        defaultTrainingShouldNotBeFound("trainingCost.lessThan=" + DEFAULT_TRAINING_COST);

        // Get all the trainingList where trainingCost is less than UPDATED_TRAINING_COST
        defaultTrainingShouldBeFound("trainingCost.lessThan=" + UPDATED_TRAINING_COST);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingCost is greater than DEFAULT_TRAINING_COST
        defaultTrainingShouldNotBeFound("trainingCost.greaterThan=" + DEFAULT_TRAINING_COST);

        // Get all the trainingList where trainingCost is greater than SMALLER_TRAINING_COST
        defaultTrainingShouldBeFound("trainingCost.greaterThan=" + SMALLER_TRAINING_COST);
    }

    @Test
    @Transactional
    void getAllTrainingsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where startDate equals to DEFAULT_START_DATE
        defaultTrainingShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the trainingList where startDate equals to UPDATED_START_DATE
        defaultTrainingShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultTrainingShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the trainingList where startDate equals to UPDATED_START_DATE
        defaultTrainingShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where startDate is not null
        defaultTrainingShouldBeFound("startDate.specified=true");

        // Get all the trainingList where startDate is null
        defaultTrainingShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where endDate equals to DEFAULT_END_DATE
        defaultTrainingShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the trainingList where endDate equals to UPDATED_END_DATE
        defaultTrainingShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultTrainingShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the trainingList where endDate equals to UPDATED_END_DATE
        defaultTrainingShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where endDate is not null
        defaultTrainingShouldBeFound("endDate.specified=true");

        // Get all the trainingList where endDate is null
        defaultTrainingShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where description equals to DEFAULT_DESCRIPTION
        defaultTrainingShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the trainingList where description equals to UPDATED_DESCRIPTION
        defaultTrainingShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTrainingShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the trainingList where description equals to UPDATED_DESCRIPTION
        defaultTrainingShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where description is not null
        defaultTrainingShouldBeFound("description.specified=true");

        // Get all the trainingList where description is null
        defaultTrainingShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where description contains DEFAULT_DESCRIPTION
        defaultTrainingShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the trainingList where description contains UPDATED_DESCRIPTION
        defaultTrainingShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where description does not contain DEFAULT_DESCRIPTION
        defaultTrainingShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the trainingList where description does not contain UPDATED_DESCRIPTION
        defaultTrainingShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingStatus equals to DEFAULT_TRAINING_STATUS
        defaultTrainingShouldBeFound("trainingStatus.equals=" + DEFAULT_TRAINING_STATUS);

        // Get all the trainingList where trainingStatus equals to UPDATED_TRAINING_STATUS
        defaultTrainingShouldNotBeFound("trainingStatus.equals=" + UPDATED_TRAINING_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingStatusIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingStatus in DEFAULT_TRAINING_STATUS or UPDATED_TRAINING_STATUS
        defaultTrainingShouldBeFound("trainingStatus.in=" + DEFAULT_TRAINING_STATUS + "," + UPDATED_TRAINING_STATUS);

        // Get all the trainingList where trainingStatus equals to UPDATED_TRAINING_STATUS
        defaultTrainingShouldNotBeFound("trainingStatus.in=" + UPDATED_TRAINING_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingStatus is not null
        defaultTrainingShouldBeFound("trainingStatus.specified=true");

        // Get all the trainingList where trainingStatus is null
        defaultTrainingShouldNotBeFound("trainingStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingStatusContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingStatus contains DEFAULT_TRAINING_STATUS
        defaultTrainingShouldBeFound("trainingStatus.contains=" + DEFAULT_TRAINING_STATUS);

        // Get all the trainingList where trainingStatus contains UPDATED_TRAINING_STATUS
        defaultTrainingShouldNotBeFound("trainingStatus.contains=" + UPDATED_TRAINING_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingStatusNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingStatus does not contain DEFAULT_TRAINING_STATUS
        defaultTrainingShouldNotBeFound("trainingStatus.doesNotContain=" + DEFAULT_TRAINING_STATUS);

        // Get all the trainingList where trainingStatus does not contain UPDATED_TRAINING_STATUS
        defaultTrainingShouldBeFound("trainingStatus.doesNotContain=" + UPDATED_TRAINING_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where status equals to DEFAULT_STATUS
        defaultTrainingShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the trainingList where status equals to UPDATED_STATUS
        defaultTrainingShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTrainingShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the trainingList where status equals to UPDATED_STATUS
        defaultTrainingShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where status is not null
        defaultTrainingShouldBeFound("status.specified=true");

        // Get all the trainingList where status is null
        defaultTrainingShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where status contains DEFAULT_STATUS
        defaultTrainingShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the trainingList where status contains UPDATED_STATUS
        defaultTrainingShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where status does not contain DEFAULT_STATUS
        defaultTrainingShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the trainingList where status does not contain UPDATED_STATUS
        defaultTrainingShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId equals to DEFAULT_TRAINER_ID
        defaultTrainingShouldBeFound("trainerId.equals=" + DEFAULT_TRAINER_ID);

        // Get all the trainingList where trainerId equals to UPDATED_TRAINER_ID
        defaultTrainingShouldNotBeFound("trainerId.equals=" + UPDATED_TRAINER_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId in DEFAULT_TRAINER_ID or UPDATED_TRAINER_ID
        defaultTrainingShouldBeFound("trainerId.in=" + DEFAULT_TRAINER_ID + "," + UPDATED_TRAINER_ID);

        // Get all the trainingList where trainerId equals to UPDATED_TRAINER_ID
        defaultTrainingShouldNotBeFound("trainerId.in=" + UPDATED_TRAINER_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId is not null
        defaultTrainingShouldBeFound("trainerId.specified=true");

        // Get all the trainingList where trainerId is null
        defaultTrainingShouldNotBeFound("trainerId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId is greater than or equal to DEFAULT_TRAINER_ID
        defaultTrainingShouldBeFound("trainerId.greaterThanOrEqual=" + DEFAULT_TRAINER_ID);

        // Get all the trainingList where trainerId is greater than or equal to UPDATED_TRAINER_ID
        defaultTrainingShouldNotBeFound("trainerId.greaterThanOrEqual=" + UPDATED_TRAINER_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId is less than or equal to DEFAULT_TRAINER_ID
        defaultTrainingShouldBeFound("trainerId.lessThanOrEqual=" + DEFAULT_TRAINER_ID);

        // Get all the trainingList where trainerId is less than or equal to SMALLER_TRAINER_ID
        defaultTrainingShouldNotBeFound("trainerId.lessThanOrEqual=" + SMALLER_TRAINER_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId is less than DEFAULT_TRAINER_ID
        defaultTrainingShouldNotBeFound("trainerId.lessThan=" + DEFAULT_TRAINER_ID);

        // Get all the trainingList where trainerId is less than UPDATED_TRAINER_ID
        defaultTrainingShouldBeFound("trainerId.lessThan=" + UPDATED_TRAINER_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainerId is greater than DEFAULT_TRAINER_ID
        defaultTrainingShouldNotBeFound("trainerId.greaterThan=" + DEFAULT_TRAINER_ID);

        // Get all the trainingList where trainerId is greater than SMALLER_TRAINER_ID
        defaultTrainingShouldBeFound("trainerId.greaterThan=" + SMALLER_TRAINER_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId equals to DEFAULT_TRAINING_TYPE_ID
        defaultTrainingShouldBeFound("trainingTypeId.equals=" + DEFAULT_TRAINING_TYPE_ID);

        // Get all the trainingList where trainingTypeId equals to UPDATED_TRAINING_TYPE_ID
        defaultTrainingShouldNotBeFound("trainingTypeId.equals=" + UPDATED_TRAINING_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId in DEFAULT_TRAINING_TYPE_ID or UPDATED_TRAINING_TYPE_ID
        defaultTrainingShouldBeFound("trainingTypeId.in=" + DEFAULT_TRAINING_TYPE_ID + "," + UPDATED_TRAINING_TYPE_ID);

        // Get all the trainingList where trainingTypeId equals to UPDATED_TRAINING_TYPE_ID
        defaultTrainingShouldNotBeFound("trainingTypeId.in=" + UPDATED_TRAINING_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId is not null
        defaultTrainingShouldBeFound("trainingTypeId.specified=true");

        // Get all the trainingList where trainingTypeId is null
        defaultTrainingShouldNotBeFound("trainingTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId is greater than or equal to DEFAULT_TRAINING_TYPE_ID
        defaultTrainingShouldBeFound("trainingTypeId.greaterThanOrEqual=" + DEFAULT_TRAINING_TYPE_ID);

        // Get all the trainingList where trainingTypeId is greater than or equal to UPDATED_TRAINING_TYPE_ID
        defaultTrainingShouldNotBeFound("trainingTypeId.greaterThanOrEqual=" + UPDATED_TRAINING_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId is less than or equal to DEFAULT_TRAINING_TYPE_ID
        defaultTrainingShouldBeFound("trainingTypeId.lessThanOrEqual=" + DEFAULT_TRAINING_TYPE_ID);

        // Get all the trainingList where trainingTypeId is less than or equal to SMALLER_TRAINING_TYPE_ID
        defaultTrainingShouldNotBeFound("trainingTypeId.lessThanOrEqual=" + SMALLER_TRAINING_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId is less than DEFAULT_TRAINING_TYPE_ID
        defaultTrainingShouldNotBeFound("trainingTypeId.lessThan=" + DEFAULT_TRAINING_TYPE_ID);

        // Get all the trainingList where trainingTypeId is less than UPDATED_TRAINING_TYPE_ID
        defaultTrainingShouldBeFound("trainingTypeId.lessThan=" + UPDATED_TRAINING_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByTrainingTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where trainingTypeId is greater than DEFAULT_TRAINING_TYPE_ID
        defaultTrainingShouldNotBeFound("trainingTypeId.greaterThan=" + DEFAULT_TRAINING_TYPE_ID);

        // Get all the trainingList where trainingTypeId is greater than SMALLER_TRAINING_TYPE_ID
        defaultTrainingShouldBeFound("trainingTypeId.greaterThan=" + SMALLER_TRAINING_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultTrainingShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultTrainingShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultTrainingShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the trainingList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultTrainingShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId is not null
        defaultTrainingShouldBeFound("departmentId.specified=true");

        // Get all the trainingList where departmentId is null
        defaultTrainingShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultTrainingShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultTrainingShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultTrainingShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultTrainingShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultTrainingShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultTrainingShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultTrainingShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultTrainingShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultTrainingShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainingList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultTrainingShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultTrainingShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the trainingList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultTrainingShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId is not null
        defaultTrainingShouldBeFound("employeeId.specified=true");

        // Get all the trainingList where employeeId is null
        defaultTrainingShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultTrainingShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainingList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultTrainingShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultTrainingShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainingList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultTrainingShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultTrainingShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainingList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultTrainingShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultTrainingShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the trainingList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultTrainingShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId equals to DEFAULT_COMPANY_ID
        defaultTrainingShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the trainingList where companyId equals to UPDATED_COMPANY_ID
        defaultTrainingShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultTrainingShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the trainingList where companyId equals to UPDATED_COMPANY_ID
        defaultTrainingShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId is not null
        defaultTrainingShouldBeFound("companyId.specified=true");

        // Get all the trainingList where companyId is null
        defaultTrainingShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultTrainingShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the trainingList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultTrainingShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultTrainingShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the trainingList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultTrainingShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId is less than DEFAULT_COMPANY_ID
        defaultTrainingShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the trainingList where companyId is less than UPDATED_COMPANY_ID
        defaultTrainingShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where companyId is greater than DEFAULT_COMPANY_ID
        defaultTrainingShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the trainingList where companyId is greater than SMALLER_COMPANY_ID
        defaultTrainingShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTrainingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the trainingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTrainingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTrainingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the trainingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTrainingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModified is not null
        defaultTrainingShouldBeFound("lastModified.specified=true");

        // Get all the trainingList where lastModified is null
        defaultTrainingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTrainingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTrainingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTrainingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the trainingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTrainingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModifiedBy is not null
        defaultTrainingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the trainingList where lastModifiedBy is null
        defaultTrainingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTrainingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTrainingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTrainingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTrainingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingShouldBeFound(String filter) throws Exception {
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainingCost").value(hasItem(DEFAULT_TRAINING_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].trainingStatus").value(hasItem(DEFAULT_TRAINING_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].trainerId").value(hasItem(DEFAULT_TRAINER_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainingTypeId").value(hasItem(DEFAULT_TRAINING_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingShouldNotBeFound(String filter) throws Exception {
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .trainingCost(UPDATED_TRAINING_COST)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .trainingStatus(UPDATED_TRAINING_STATUS)
            .status(UPDATED_STATUS)
            .trainerId(UPDATED_TRAINER_ID)
            .trainingTypeId(UPDATED_TRAINING_TYPE_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TrainingDTO trainingDTO = trainingMapper.toDto(updatedTraining);

        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTrainingCost()).isEqualTo(UPDATED_TRAINING_COST);
        assertThat(testTraining.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTraining.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTraining.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTraining.getTrainingStatus()).isEqualTo(UPDATED_TRAINING_STATUS);
        assertThat(testTraining.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTraining.getTrainerId()).isEqualTo(UPDATED_TRAINER_ID);
        assertThat(testTraining.getTrainingTypeId()).isEqualTo(UPDATED_TRAINING_TYPE_ID);
        assertThat(testTraining.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTraining.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTraining.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTraining.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTraining.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingWithPatch() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training using partial update
        Training partialUpdatedTraining = new Training();
        partialUpdatedTraining.setId(training.getId());

        partialUpdatedTraining
            .trainingCost(UPDATED_TRAINING_COST)
            .startDate(UPDATED_START_DATE)
            .description(UPDATED_DESCRIPTION)
            .trainingStatus(UPDATED_TRAINING_STATUS)
            .trainerId(UPDATED_TRAINER_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
      //      .companyId(UPDATED_COMPANY_ID);

        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTrainingCost()).isEqualTo(UPDATED_TRAINING_COST);
        assertThat(testTraining.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTraining.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTraining.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTraining.getTrainingStatus()).isEqualTo(UPDATED_TRAINING_STATUS);
        assertThat(testTraining.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTraining.getTrainerId()).isEqualTo(UPDATED_TRAINER_ID);
        assertThat(testTraining.getTrainingTypeId()).isEqualTo(DEFAULT_TRAINING_TYPE_ID);
        assertThat(testTraining.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTraining.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTraining.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTraining.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTraining.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTrainingWithPatch() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training using partial update
        Training partialUpdatedTraining = new Training();
        partialUpdatedTraining.setId(training.getId());

        partialUpdatedTraining
            .trainingCost(UPDATED_TRAINING_COST)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .trainingStatus(UPDATED_TRAINING_STATUS)
            .status(UPDATED_STATUS)
            .trainerId(UPDATED_TRAINER_ID)
            .trainingTypeId(UPDATED_TRAINING_TYPE_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
            )
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTrainingCost()).isEqualTo(UPDATED_TRAINING_COST);
        assertThat(testTraining.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTraining.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTraining.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTraining.getTrainingStatus()).isEqualTo(UPDATED_TRAINING_STATUS);
        assertThat(testTraining.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTraining.getTrainerId()).isEqualTo(UPDATED_TRAINER_ID);
        assertThat(testTraining.getTrainingTypeId()).isEqualTo(UPDATED_TRAINING_TYPE_ID);
        assertThat(testTraining.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTraining.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTraining.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTraining.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTraining.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
        training.setId(count.incrementAndGet());

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc
            .perform(delete(ENTITY_API_URL_ID, training.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
