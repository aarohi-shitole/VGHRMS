package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.TrainingType;
import com.techvg.hrms.repository.TrainingTypeRepository;
import com.techvg.hrms.service.criteria.TrainingTypeCriteria;
import com.techvg.hrms.service.dto.TrainingTypeDTO;
import com.techvg.hrms.service.mapper.TrainingTypeMapper;
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
 * Integration tests for the {@link TrainingTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainingTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/training-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    private TrainingTypeMapper trainingTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingTypeMockMvc;

    private TrainingType trainingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingType createEntity(EntityManager em) {
        TrainingType trainingType = new TrainingType()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .departmentId(DEFAULT_DEPARTMENT_ID);
 //           .companyId(DEFAULT_COMPANY_ID)
 //           .lastModified(DEFAULT_LAST_MODIFIED)
 //           .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return trainingType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingType createUpdatedEntity(EntityManager em) {
        TrainingType trainingType = new TrainingType()
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .departmentId(UPDATED_DEPARTMENT_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return trainingType;
    }

    @BeforeEach
    public void initTest() {
        trainingType = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainingType() throws Exception {
        int databaseSizeBeforeCreate = trainingTypeRepository.findAll().size();
        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);
        restTrainingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingType testTrainingType = trainingTypeList.get(trainingTypeList.size() - 1);
        assertThat(testTrainingType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTrainingType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrainingType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrainingType.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testTrainingType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTrainingType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTrainingType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTrainingTypeWithExistingId() throws Exception {
        // Create the TrainingType with an existing ID
        trainingType.setId(1L);
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        int databaseSizeBeforeCreate = trainingTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainingTypes() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList
        restTrainingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTrainingType() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get the trainingType
        restTrainingTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, trainingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTrainingTypesByIdFiltering() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        Long id = trainingType.getId();

        defaultTrainingTypeShouldBeFound("id.equals=" + id);
        defaultTrainingTypeShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where type equals to DEFAULT_TYPE
        defaultTrainingTypeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the trainingTypeList where type equals to UPDATED_TYPE
        defaultTrainingTypeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTrainingTypeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the trainingTypeList where type equals to UPDATED_TYPE
        defaultTrainingTypeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where type is not null
        defaultTrainingTypeShouldBeFound("type.specified=true");

        // Get all the trainingTypeList where type is null
        defaultTrainingTypeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByTypeContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where type contains DEFAULT_TYPE
        defaultTrainingTypeShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the trainingTypeList where type contains UPDATED_TYPE
        defaultTrainingTypeShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where type does not contain DEFAULT_TYPE
        defaultTrainingTypeShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the trainingTypeList where type does not contain UPDATED_TYPE
        defaultTrainingTypeShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where description equals to DEFAULT_DESCRIPTION
        defaultTrainingTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the trainingTypeList where description equals to UPDATED_DESCRIPTION
        defaultTrainingTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTrainingTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the trainingTypeList where description equals to UPDATED_DESCRIPTION
        defaultTrainingTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where description is not null
        defaultTrainingTypeShouldBeFound("description.specified=true");

        // Get all the trainingTypeList where description is null
        defaultTrainingTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where description contains DEFAULT_DESCRIPTION
        defaultTrainingTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the trainingTypeList where description contains UPDATED_DESCRIPTION
        defaultTrainingTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultTrainingTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the trainingTypeList where description does not contain UPDATED_DESCRIPTION
        defaultTrainingTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where status equals to DEFAULT_STATUS
        defaultTrainingTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the trainingTypeList where status equals to UPDATED_STATUS
        defaultTrainingTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTrainingTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the trainingTypeList where status equals to UPDATED_STATUS
        defaultTrainingTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where status is not null
        defaultTrainingTypeShouldBeFound("status.specified=true");

        // Get all the trainingTypeList where status is null
        defaultTrainingTypeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByStatusContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where status contains DEFAULT_STATUS
        defaultTrainingTypeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the trainingTypeList where status contains UPDATED_STATUS
        defaultTrainingTypeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where status does not contain DEFAULT_STATUS
        defaultTrainingTypeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the trainingTypeList where status does not contain UPDATED_STATUS
        defaultTrainingTypeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultTrainingTypeShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingTypeList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultTrainingTypeShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultTrainingTypeShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the trainingTypeList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultTrainingTypeShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId is not null
        defaultTrainingTypeShouldBeFound("departmentId.specified=true");

        // Get all the trainingTypeList where departmentId is null
        defaultTrainingTypeShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultTrainingTypeShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingTypeList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultTrainingTypeShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultTrainingTypeShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingTypeList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultTrainingTypeShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultTrainingTypeShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingTypeList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultTrainingTypeShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultTrainingTypeShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the trainingTypeList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultTrainingTypeShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId equals to DEFAULT_COMPANY_ID
        defaultTrainingTypeShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the trainingTypeList where companyId equals to UPDATED_COMPANY_ID
        defaultTrainingTypeShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultTrainingTypeShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the trainingTypeList where companyId equals to UPDATED_COMPANY_ID
        defaultTrainingTypeShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId is not null
        defaultTrainingTypeShouldBeFound("companyId.specified=true");

        // Get all the trainingTypeList where companyId is null
        defaultTrainingTypeShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultTrainingTypeShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the trainingTypeList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultTrainingTypeShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultTrainingTypeShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the trainingTypeList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultTrainingTypeShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId is less than DEFAULT_COMPANY_ID
        defaultTrainingTypeShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the trainingTypeList where companyId is less than UPDATED_COMPANY_ID
        defaultTrainingTypeShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where companyId is greater than DEFAULT_COMPANY_ID
        defaultTrainingTypeShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the trainingTypeList where companyId is greater than SMALLER_COMPANY_ID
        defaultTrainingTypeShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTrainingTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the trainingTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTrainingTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTrainingTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the trainingTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTrainingTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModified is not null
        defaultTrainingTypeShouldBeFound("lastModified.specified=true");

        // Get all the trainingTypeList where lastModified is null
        defaultTrainingTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTrainingTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainingTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTrainingTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTrainingTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the trainingTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTrainingTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModifiedBy is not null
        defaultTrainingTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the trainingTypeList where lastModifiedBy is null
        defaultTrainingTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTrainingTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainingTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTrainingTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTrainingTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTrainingTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the trainingTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTrainingTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingTypeShouldBeFound(String filter) throws Exception {
        restTrainingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTrainingTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingTypeShouldNotBeFound(String filter) throws Exception {
        restTrainingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTrainingType() throws Exception {
        // Get the trainingType
        restTrainingTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainingType() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();

        // Update the trainingType
        TrainingType updatedTrainingType = trainingTypeRepository.findById(trainingType.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingType are not directly saved in db
        em.detach(updatedTrainingType);
        updatedTrainingType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .departmentId(UPDATED_DEPARTMENT_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(updatedTrainingType);

        restTrainingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainingType testTrainingType = trainingTypeList.get(trainingTypeList.size() - 1);
        assertThat(testTrainingType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTrainingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainingType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingType.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTrainingType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTrainingType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTrainingType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();
        trainingType.setId(count.incrementAndGet());

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();
        trainingType.setId(count.incrementAndGet());

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();
        trainingType.setId(count.incrementAndGet());

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingTypeWithPatch() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();

        // Update the trainingType using partial update
        TrainingType partialUpdatedTrainingType = new TrainingType();
        partialUpdatedTrainingType.setId(trainingType.getId());

        partialUpdatedTrainingType.description(UPDATED_DESCRIPTION).status(UPDATED_STATUS).departmentId(UPDATED_DEPARTMENT_ID);

        restTrainingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainingType))
            )
            .andExpect(status().isOk());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainingType testTrainingType = trainingTypeList.get(trainingTypeList.size() - 1);
        assertThat(testTrainingType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTrainingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainingType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingType.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTrainingType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTrainingType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTrainingType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTrainingTypeWithPatch() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();

        // Update the trainingType using partial update
        TrainingType partialUpdatedTrainingType = new TrainingType();
        partialUpdatedTrainingType.setId(trainingType.getId());

        partialUpdatedTrainingType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .departmentId(UPDATED_DEPARTMENT_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTrainingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainingType))
            )
            .andExpect(status().isOk());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainingType testTrainingType = trainingTypeList.get(trainingTypeList.size() - 1);
        assertThat(testTrainingType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTrainingType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrainingType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrainingType.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTrainingType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTrainingType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTrainingType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();
        trainingType.setId(count.incrementAndGet());

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();
        trainingType.setId(count.incrementAndGet());

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();
        trainingType.setId(count.incrementAndGet());

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainingType() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        int databaseSizeBeforeDelete = trainingTypeRepository.findAll().size();

        // Delete the trainingType
        restTrainingTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainingType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
