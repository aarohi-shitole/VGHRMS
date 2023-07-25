package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.MasterPerformanceIndicator;
import com.techvg.hrms.repository.MasterPerformanceIndicatorRepository;
import com.techvg.hrms.service.criteria.MasterPerformanceIndicatorCriteria;
import com.techvg.hrms.service.dto.MasterPerformanceIndicatorDTO;
import com.techvg.hrms.service.mapper.MasterPerformanceIndicatorMapper;
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
 * Integration tests for the {@link MasterPerformanceIndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MasterPerformanceIndicatorResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_WEIGHTAGE = 1L;
    private static final Long UPDATED_WEIGHTAGE = 2L;
    private static final Long SMALLER_WEIGHTAGE = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/master-performance-indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository;

    @Autowired
    private MasterPerformanceIndicatorMapper masterPerformanceIndicatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMasterPerformanceIndicatorMockMvc;

    private MasterPerformanceIndicator masterPerformanceIndicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterPerformanceIndicator createEntity(EntityManager em) {
        MasterPerformanceIndicator masterPerformanceIndicator = new MasterPerformanceIndicator()
            .category(DEFAULT_CATEGORY)
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .weightage(DEFAULT_WEIGHTAGE)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return masterPerformanceIndicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterPerformanceIndicator createUpdatedEntity(EntityManager em) {
        MasterPerformanceIndicator masterPerformanceIndicator = new MasterPerformanceIndicator()
            .category(UPDATED_CATEGORY)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .weightage(UPDATED_WEIGHTAGE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return masterPerformanceIndicator;
    }

    @BeforeEach
    public void initTest() {
        masterPerformanceIndicator = createEntity(em);
    }

    @Test
    @Transactional
    void createMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeCreate = masterPerformanceIndicatorRepository.findAll().size();
        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);
        restMasterPerformanceIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        MasterPerformanceIndicator testMasterPerformanceIndicator = masterPerformanceIndicatorList.get(
            masterPerformanceIndicatorList.size() - 1
        );
        assertThat(testMasterPerformanceIndicator.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testMasterPerformanceIndicator.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMasterPerformanceIndicator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMasterPerformanceIndicator.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMasterPerformanceIndicator.getWeightage()).isEqualTo(DEFAULT_WEIGHTAGE);
        assertThat(testMasterPerformanceIndicator.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMasterPerformanceIndicator.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testMasterPerformanceIndicator.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMasterPerformanceIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createMasterPerformanceIndicatorWithExistingId() throws Exception {
        // Create the MasterPerformanceIndicator with an existing ID
        masterPerformanceIndicator.setId(1L);
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        int databaseSizeBeforeCreate = masterPerformanceIndicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasterPerformanceIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicators() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList
        restMasterPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterPerformanceIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getMasterPerformanceIndicator() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get the masterPerformanceIndicator
        restMasterPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, masterPerformanceIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(masterPerformanceIndicator.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.weightage").value(DEFAULT_WEIGHTAGE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getMasterPerformanceIndicatorsByIdFiltering() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        Long id = masterPerformanceIndicator.getId();

        defaultMasterPerformanceIndicatorShouldBeFound("id.equals=" + id);
        defaultMasterPerformanceIndicatorShouldNotBeFound("id.notEquals=" + id);

        defaultMasterPerformanceIndicatorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMasterPerformanceIndicatorShouldNotBeFound("id.greaterThan=" + id);

        defaultMasterPerformanceIndicatorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMasterPerformanceIndicatorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where category equals to DEFAULT_CATEGORY
        defaultMasterPerformanceIndicatorShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the masterPerformanceIndicatorList where category equals to UPDATED_CATEGORY
        defaultMasterPerformanceIndicatorShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultMasterPerformanceIndicatorShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the masterPerformanceIndicatorList where category equals to UPDATED_CATEGORY
        defaultMasterPerformanceIndicatorShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where category is not null
        defaultMasterPerformanceIndicatorShouldBeFound("category.specified=true");

        // Get all the masterPerformanceIndicatorList where category is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCategoryContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where category contains DEFAULT_CATEGORY
        defaultMasterPerformanceIndicatorShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the masterPerformanceIndicatorList where category contains UPDATED_CATEGORY
        defaultMasterPerformanceIndicatorShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where category does not contain DEFAULT_CATEGORY
        defaultMasterPerformanceIndicatorShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the masterPerformanceIndicatorList where category does not contain UPDATED_CATEGORY
        defaultMasterPerformanceIndicatorShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where type equals to DEFAULT_TYPE
        defaultMasterPerformanceIndicatorShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the masterPerformanceIndicatorList where type equals to UPDATED_TYPE
        defaultMasterPerformanceIndicatorShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMasterPerformanceIndicatorShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the masterPerformanceIndicatorList where type equals to UPDATED_TYPE
        defaultMasterPerformanceIndicatorShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where type is not null
        defaultMasterPerformanceIndicatorShouldBeFound("type.specified=true");

        // Get all the masterPerformanceIndicatorList where type is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByTypeContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where type contains DEFAULT_TYPE
        defaultMasterPerformanceIndicatorShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the masterPerformanceIndicatorList where type contains UPDATED_TYPE
        defaultMasterPerformanceIndicatorShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where type does not contain DEFAULT_TYPE
        defaultMasterPerformanceIndicatorShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the masterPerformanceIndicatorList where type does not contain UPDATED_TYPE
        defaultMasterPerformanceIndicatorShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where name equals to DEFAULT_NAME
        defaultMasterPerformanceIndicatorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the masterPerformanceIndicatorList where name equals to UPDATED_NAME
        defaultMasterPerformanceIndicatorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMasterPerformanceIndicatorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the masterPerformanceIndicatorList where name equals to UPDATED_NAME
        defaultMasterPerformanceIndicatorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where name is not null
        defaultMasterPerformanceIndicatorShouldBeFound("name.specified=true");

        // Get all the masterPerformanceIndicatorList where name is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByNameContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where name contains DEFAULT_NAME
        defaultMasterPerformanceIndicatorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the masterPerformanceIndicatorList where name contains UPDATED_NAME
        defaultMasterPerformanceIndicatorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where name does not contain DEFAULT_NAME
        defaultMasterPerformanceIndicatorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the masterPerformanceIndicatorList where name does not contain UPDATED_NAME
        defaultMasterPerformanceIndicatorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where description equals to DEFAULT_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the masterPerformanceIndicatorList where description equals to UPDATED_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the masterPerformanceIndicatorList where description equals to UPDATED_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where description is not null
        defaultMasterPerformanceIndicatorShouldBeFound("description.specified=true");

        // Get all the masterPerformanceIndicatorList where description is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where description contains DEFAULT_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the masterPerformanceIndicatorList where description contains UPDATED_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where description does not contain DEFAULT_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the masterPerformanceIndicatorList where description does not contain UPDATED_DESCRIPTION
        defaultMasterPerformanceIndicatorShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage equals to DEFAULT_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.equals=" + DEFAULT_WEIGHTAGE);

        // Get all the masterPerformanceIndicatorList where weightage equals to UPDATED_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.equals=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage in DEFAULT_WEIGHTAGE or UPDATED_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.in=" + DEFAULT_WEIGHTAGE + "," + UPDATED_WEIGHTAGE);

        // Get all the masterPerformanceIndicatorList where weightage equals to UPDATED_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.in=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage is not null
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.specified=true");

        // Get all the masterPerformanceIndicatorList where weightage is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage is greater than or equal to DEFAULT_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.greaterThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the masterPerformanceIndicatorList where weightage is greater than or equal to UPDATED_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.greaterThanOrEqual=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage is less than or equal to DEFAULT_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.lessThanOrEqual=" + DEFAULT_WEIGHTAGE);

        // Get all the masterPerformanceIndicatorList where weightage is less than or equal to SMALLER_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.lessThanOrEqual=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsLessThanSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage is less than DEFAULT_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.lessThan=" + DEFAULT_WEIGHTAGE);

        // Get all the masterPerformanceIndicatorList where weightage is less than UPDATED_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.lessThan=" + UPDATED_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByWeightageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where weightage is greater than DEFAULT_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldNotBeFound("weightage.greaterThan=" + DEFAULT_WEIGHTAGE);

        // Get all the masterPerformanceIndicatorList where weightage is greater than SMALLER_WEIGHTAGE
        defaultMasterPerformanceIndicatorShouldBeFound("weightage.greaterThan=" + SMALLER_WEIGHTAGE);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where status equals to DEFAULT_STATUS
        defaultMasterPerformanceIndicatorShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the masterPerformanceIndicatorList where status equals to UPDATED_STATUS
        defaultMasterPerformanceIndicatorShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMasterPerformanceIndicatorShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the masterPerformanceIndicatorList where status equals to UPDATED_STATUS
        defaultMasterPerformanceIndicatorShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where status is not null
        defaultMasterPerformanceIndicatorShouldBeFound("status.specified=true");

        // Get all the masterPerformanceIndicatorList where status is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByStatusContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where status contains DEFAULT_STATUS
        defaultMasterPerformanceIndicatorShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the masterPerformanceIndicatorList where status contains UPDATED_STATUS
        defaultMasterPerformanceIndicatorShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where status does not contain DEFAULT_STATUS
        defaultMasterPerformanceIndicatorShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the masterPerformanceIndicatorList where status does not contain UPDATED_STATUS
        defaultMasterPerformanceIndicatorShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId equals to DEFAULT_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the masterPerformanceIndicatorList where companyId equals to UPDATED_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the masterPerformanceIndicatorList where companyId equals to UPDATED_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId is not null
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.specified=true");

        // Get all the masterPerformanceIndicatorList where companyId is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the masterPerformanceIndicatorList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the masterPerformanceIndicatorList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId is less than DEFAULT_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the masterPerformanceIndicatorList where companyId is less than UPDATED_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where companyId is greater than DEFAULT_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the masterPerformanceIndicatorList where companyId is greater than SMALLER_COMPANY_ID
        defaultMasterPerformanceIndicatorShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMasterPerformanceIndicatorShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the masterPerformanceIndicatorList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMasterPerformanceIndicatorShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the masterPerformanceIndicatorList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModified is not null
        defaultMasterPerformanceIndicatorShouldBeFound("lastModified.specified=true");

        // Get all the masterPerformanceIndicatorList where lastModified is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy is not null
        defaultMasterPerformanceIndicatorShouldBeFound("lastModifiedBy.specified=true");

        // Get all the masterPerformanceIndicatorList where lastModifiedBy is null
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterPerformanceIndicatorsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the masterPerformanceIndicatorList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMasterPerformanceIndicatorShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMasterPerformanceIndicatorShouldBeFound(String filter) throws Exception {
        restMasterPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterPerformanceIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].weightage").value(hasItem(DEFAULT_WEIGHTAGE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restMasterPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMasterPerformanceIndicatorShouldNotBeFound(String filter) throws Exception {
        restMasterPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMasterPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMasterPerformanceIndicator() throws Exception {
        // Get the masterPerformanceIndicator
        restMasterPerformanceIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMasterPerformanceIndicator() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();

        // Update the masterPerformanceIndicator
        MasterPerformanceIndicator updatedMasterPerformanceIndicator = masterPerformanceIndicatorRepository
            .findById(masterPerformanceIndicator.getId())
            .get();
        // Disconnect from session so that the updates on updatedMasterPerformanceIndicator are not directly saved in db
        em.detach(updatedMasterPerformanceIndicator);
        updatedMasterPerformanceIndicator
            .category(UPDATED_CATEGORY)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .weightage(UPDATED_WEIGHTAGE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(
            updatedMasterPerformanceIndicator
        );

        restMasterPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, masterPerformanceIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
        MasterPerformanceIndicator testMasterPerformanceIndicator = masterPerformanceIndicatorList.get(
            masterPerformanceIndicatorList.size() - 1
        );
        assertThat(testMasterPerformanceIndicator.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMasterPerformanceIndicator.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMasterPerformanceIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMasterPerformanceIndicator.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMasterPerformanceIndicator.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testMasterPerformanceIndicator.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMasterPerformanceIndicator.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testMasterPerformanceIndicator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMasterPerformanceIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();
        masterPerformanceIndicator.setId(count.incrementAndGet());

        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, masterPerformanceIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();
        masterPerformanceIndicator.setId(count.incrementAndGet());

        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();
        masterPerformanceIndicator.setId(count.incrementAndGet());

        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMasterPerformanceIndicatorWithPatch() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();

        // Update the masterPerformanceIndicator using partial update
        MasterPerformanceIndicator partialUpdatedMasterPerformanceIndicator = new MasterPerformanceIndicator();
        partialUpdatedMasterPerformanceIndicator.setId(masterPerformanceIndicator.getId());

        partialUpdatedMasterPerformanceIndicator
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .weightage(UPDATED_WEIGHTAGE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED);

        restMasterPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasterPerformanceIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMasterPerformanceIndicator))
            )
            .andExpect(status().isOk());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
        MasterPerformanceIndicator testMasterPerformanceIndicator = masterPerformanceIndicatorList.get(
            masterPerformanceIndicatorList.size() - 1
        );
        assertThat(testMasterPerformanceIndicator.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMasterPerformanceIndicator.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMasterPerformanceIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMasterPerformanceIndicator.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMasterPerformanceIndicator.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testMasterPerformanceIndicator.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMasterPerformanceIndicator.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testMasterPerformanceIndicator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMasterPerformanceIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateMasterPerformanceIndicatorWithPatch() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();

        // Update the masterPerformanceIndicator using partial update
        MasterPerformanceIndicator partialUpdatedMasterPerformanceIndicator = new MasterPerformanceIndicator();
        partialUpdatedMasterPerformanceIndicator.setId(masterPerformanceIndicator.getId());

        partialUpdatedMasterPerformanceIndicator
            .category(UPDATED_CATEGORY)
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .weightage(UPDATED_WEIGHTAGE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restMasterPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasterPerformanceIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMasterPerformanceIndicator))
            )
            .andExpect(status().isOk());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
        MasterPerformanceIndicator testMasterPerformanceIndicator = masterPerformanceIndicatorList.get(
            masterPerformanceIndicatorList.size() - 1
        );
        assertThat(testMasterPerformanceIndicator.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMasterPerformanceIndicator.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMasterPerformanceIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMasterPerformanceIndicator.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMasterPerformanceIndicator.getWeightage()).isEqualTo(UPDATED_WEIGHTAGE);
        assertThat(testMasterPerformanceIndicator.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMasterPerformanceIndicator.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testMasterPerformanceIndicator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMasterPerformanceIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();
        masterPerformanceIndicator.setId(count.incrementAndGet());

        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, masterPerformanceIndicatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();
        masterPerformanceIndicator.setId(count.incrementAndGet());

        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMasterPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = masterPerformanceIndicatorRepository.findAll().size();
        masterPerformanceIndicator.setId(count.incrementAndGet());

        // Create the MasterPerformanceIndicator
        MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO = masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(masterPerformanceIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MasterPerformanceIndicator in the database
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMasterPerformanceIndicator() throws Exception {
        // Initialize the database
        masterPerformanceIndicatorRepository.saveAndFlush(masterPerformanceIndicator);

        int databaseSizeBeforeDelete = masterPerformanceIndicatorRepository.findAll().size();

        // Delete the masterPerformanceIndicator
        restMasterPerformanceIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, masterPerformanceIndicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MasterPerformanceIndicator> masterPerformanceIndicatorList = masterPerformanceIndicatorRepository.findAll();
        assertThat(masterPerformanceIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
