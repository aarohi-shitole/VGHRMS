package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.EmployeeGoalsReview;
import com.techvg.hrms.repository.EmployeeGoalsReviewRepository;
import com.techvg.hrms.service.criteria.EmployeeGoalsReviewCriteria;
import com.techvg.hrms.service.dto.EmployeeGoalsReviewDTO;
import com.techvg.hrms.service.mapper.EmployeeGoalsReviewMapper;
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
 * Integration tests for the {@link EmployeeGoalsReviewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeGoalsReviewResourceIT {

    private static final String DEFAULT_GOAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_GOAL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_GOAL_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_GOAL_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_GOALTYPE = "AAAAAAAAAA";
    private static final String UPDATED_GOALTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GOAL_SET_BY = "AAAAAAAAAA";
    private static final String UPDATED_GOAL_SET_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_APPRAISAL_REVIEW_ID = 1L;
    private static final Long UPDATED_APPRAISAL_REVIEW_ID = 2L;
    private static final Long SMALLER_APPRAISAL_REVIEW_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-goals-reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeGoalsReviewRepository employeeGoalsReviewRepository;

    @Autowired
    private EmployeeGoalsReviewMapper employeeGoalsReviewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeGoalsReviewMockMvc;

    private EmployeeGoalsReview employeeGoalsReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeGoalsReview createEntity(EntityManager em) {
        EmployeeGoalsReview employeeGoalsReview = new EmployeeGoalsReview()
            .goalDescription(DEFAULT_GOAL_DESCRIPTION)
            .goalCategory(DEFAULT_GOAL_CATEGORY)
            .goaltype(DEFAULT_GOALTYPE)
            .goalSetBy(DEFAULT_GOAL_SET_BY)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .appraisalReviewId(DEFAULT_APPRAISAL_REVIEW_ID)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return employeeGoalsReview;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeGoalsReview createUpdatedEntity(EntityManager em) {
        EmployeeGoalsReview employeeGoalsReview = new EmployeeGoalsReview()
            .goalDescription(UPDATED_GOAL_DESCRIPTION)
            .goalCategory(UPDATED_GOAL_CATEGORY)
            .goaltype(UPDATED_GOALTYPE)
            .goalSetBy(UPDATED_GOAL_SET_BY)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return employeeGoalsReview;
    }

    @BeforeEach
    public void initTest() {
        employeeGoalsReview = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeCreate = employeeGoalsReviewRepository.findAll().size();
        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);
        restEmployeeGoalsReviewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeGoalsReview testEmployeeGoalsReview = employeeGoalsReviewList.get(employeeGoalsReviewList.size() - 1);
        assertThat(testEmployeeGoalsReview.getGoalDescription()).isEqualTo(DEFAULT_GOAL_DESCRIPTION);
        assertThat(testEmployeeGoalsReview.getGoalCategory()).isEqualTo(DEFAULT_GOAL_CATEGORY);
        assertThat(testEmployeeGoalsReview.getGoaltype()).isEqualTo(DEFAULT_GOALTYPE);
        assertThat(testEmployeeGoalsReview.getGoalSetBy()).isEqualTo(DEFAULT_GOAL_SET_BY);
        assertThat(testEmployeeGoalsReview.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeGoalsReview.getAppraisalReviewId()).isEqualTo(DEFAULT_APPRAISAL_REVIEW_ID);
        assertThat(testEmployeeGoalsReview.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeGoalsReview.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEmployeeGoalsReview.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEmployeeGoalsReview.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEmployeeGoalsReviewWithExistingId() throws Exception {
        // Create the EmployeeGoalsReview with an existing ID
        employeeGoalsReview.setId(1L);
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        int databaseSizeBeforeCreate = employeeGoalsReviewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeGoalsReviewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviews() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList
        restEmployeeGoalsReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeGoalsReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].goalDescription").value(hasItem(DEFAULT_GOAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].goalCategory").value(hasItem(DEFAULT_GOAL_CATEGORY)))
            .andExpect(jsonPath("$.[*].goaltype").value(hasItem(DEFAULT_GOALTYPE)))
            .andExpect(jsonPath("$.[*].goalSetBy").value(hasItem(DEFAULT_GOAL_SET_BY)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getEmployeeGoalsReview() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get the employeeGoalsReview
        restEmployeeGoalsReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeGoalsReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeGoalsReview.getId().intValue()))
            .andExpect(jsonPath("$.goalDescription").value(DEFAULT_GOAL_DESCRIPTION))
            .andExpect(jsonPath("$.goalCategory").value(DEFAULT_GOAL_CATEGORY))
            .andExpect(jsonPath("$.goaltype").value(DEFAULT_GOALTYPE))
            .andExpect(jsonPath("$.goalSetBy").value(DEFAULT_GOAL_SET_BY))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.appraisalReviewId").value(DEFAULT_APPRAISAL_REVIEW_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getEmployeeGoalsReviewsByIdFiltering() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        Long id = employeeGoalsReview.getId();

        defaultEmployeeGoalsReviewShouldBeFound("id.equals=" + id);
        defaultEmployeeGoalsReviewShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeGoalsReviewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeGoalsReviewShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeGoalsReviewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeGoalsReviewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalDescription equals to DEFAULT_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldBeFound("goalDescription.equals=" + DEFAULT_GOAL_DESCRIPTION);

        // Get all the employeeGoalsReviewList where goalDescription equals to UPDATED_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldNotBeFound("goalDescription.equals=" + UPDATED_GOAL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalDescription in DEFAULT_GOAL_DESCRIPTION or UPDATED_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldBeFound("goalDescription.in=" + DEFAULT_GOAL_DESCRIPTION + "," + UPDATED_GOAL_DESCRIPTION);

        // Get all the employeeGoalsReviewList where goalDescription equals to UPDATED_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldNotBeFound("goalDescription.in=" + UPDATED_GOAL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalDescription is not null
        defaultEmployeeGoalsReviewShouldBeFound("goalDescription.specified=true");

        // Get all the employeeGoalsReviewList where goalDescription is null
        defaultEmployeeGoalsReviewShouldNotBeFound("goalDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalDescription contains DEFAULT_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldBeFound("goalDescription.contains=" + DEFAULT_GOAL_DESCRIPTION);

        // Get all the employeeGoalsReviewList where goalDescription contains UPDATED_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldNotBeFound("goalDescription.contains=" + UPDATED_GOAL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalDescription does not contain DEFAULT_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldNotBeFound("goalDescription.doesNotContain=" + DEFAULT_GOAL_DESCRIPTION);

        // Get all the employeeGoalsReviewList where goalDescription does not contain UPDATED_GOAL_DESCRIPTION
        defaultEmployeeGoalsReviewShouldBeFound("goalDescription.doesNotContain=" + UPDATED_GOAL_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalCategory equals to DEFAULT_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldBeFound("goalCategory.equals=" + DEFAULT_GOAL_CATEGORY);

        // Get all the employeeGoalsReviewList where goalCategory equals to UPDATED_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalCategory.equals=" + UPDATED_GOAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalCategory in DEFAULT_GOAL_CATEGORY or UPDATED_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldBeFound("goalCategory.in=" + DEFAULT_GOAL_CATEGORY + "," + UPDATED_GOAL_CATEGORY);

        // Get all the employeeGoalsReviewList where goalCategory equals to UPDATED_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalCategory.in=" + UPDATED_GOAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalCategory is not null
        defaultEmployeeGoalsReviewShouldBeFound("goalCategory.specified=true");

        // Get all the employeeGoalsReviewList where goalCategory is null
        defaultEmployeeGoalsReviewShouldNotBeFound("goalCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalCategoryContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalCategory contains DEFAULT_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldBeFound("goalCategory.contains=" + DEFAULT_GOAL_CATEGORY);

        // Get all the employeeGoalsReviewList where goalCategory contains UPDATED_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalCategory.contains=" + UPDATED_GOAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalCategory does not contain DEFAULT_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalCategory.doesNotContain=" + DEFAULT_GOAL_CATEGORY);

        // Get all the employeeGoalsReviewList where goalCategory does not contain UPDATED_GOAL_CATEGORY
        defaultEmployeeGoalsReviewShouldBeFound("goalCategory.doesNotContain=" + UPDATED_GOAL_CATEGORY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoaltypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goaltype equals to DEFAULT_GOALTYPE
        defaultEmployeeGoalsReviewShouldBeFound("goaltype.equals=" + DEFAULT_GOALTYPE);

        // Get all the employeeGoalsReviewList where goaltype equals to UPDATED_GOALTYPE
        defaultEmployeeGoalsReviewShouldNotBeFound("goaltype.equals=" + UPDATED_GOALTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoaltypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goaltype in DEFAULT_GOALTYPE or UPDATED_GOALTYPE
        defaultEmployeeGoalsReviewShouldBeFound("goaltype.in=" + DEFAULT_GOALTYPE + "," + UPDATED_GOALTYPE);

        // Get all the employeeGoalsReviewList where goaltype equals to UPDATED_GOALTYPE
        defaultEmployeeGoalsReviewShouldNotBeFound("goaltype.in=" + UPDATED_GOALTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoaltypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goaltype is not null
        defaultEmployeeGoalsReviewShouldBeFound("goaltype.specified=true");

        // Get all the employeeGoalsReviewList where goaltype is null
        defaultEmployeeGoalsReviewShouldNotBeFound("goaltype.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoaltypeContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goaltype contains DEFAULT_GOALTYPE
        defaultEmployeeGoalsReviewShouldBeFound("goaltype.contains=" + DEFAULT_GOALTYPE);

        // Get all the employeeGoalsReviewList where goaltype contains UPDATED_GOALTYPE
        defaultEmployeeGoalsReviewShouldNotBeFound("goaltype.contains=" + UPDATED_GOALTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoaltypeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goaltype does not contain DEFAULT_GOALTYPE
        defaultEmployeeGoalsReviewShouldNotBeFound("goaltype.doesNotContain=" + DEFAULT_GOALTYPE);

        // Get all the employeeGoalsReviewList where goaltype does not contain UPDATED_GOALTYPE
        defaultEmployeeGoalsReviewShouldBeFound("goaltype.doesNotContain=" + UPDATED_GOALTYPE);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalSetByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalSetBy equals to DEFAULT_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldBeFound("goalSetBy.equals=" + DEFAULT_GOAL_SET_BY);

        // Get all the employeeGoalsReviewList where goalSetBy equals to UPDATED_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalSetBy.equals=" + UPDATED_GOAL_SET_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalSetByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalSetBy in DEFAULT_GOAL_SET_BY or UPDATED_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldBeFound("goalSetBy.in=" + DEFAULT_GOAL_SET_BY + "," + UPDATED_GOAL_SET_BY);

        // Get all the employeeGoalsReviewList where goalSetBy equals to UPDATED_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalSetBy.in=" + UPDATED_GOAL_SET_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalSetByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalSetBy is not null
        defaultEmployeeGoalsReviewShouldBeFound("goalSetBy.specified=true");

        // Get all the employeeGoalsReviewList where goalSetBy is null
        defaultEmployeeGoalsReviewShouldNotBeFound("goalSetBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalSetByContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalSetBy contains DEFAULT_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldBeFound("goalSetBy.contains=" + DEFAULT_GOAL_SET_BY);

        // Get all the employeeGoalsReviewList where goalSetBy contains UPDATED_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalSetBy.contains=" + UPDATED_GOAL_SET_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByGoalSetByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where goalSetBy does not contain DEFAULT_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("goalSetBy.doesNotContain=" + DEFAULT_GOAL_SET_BY);

        // Get all the employeeGoalsReviewList where goalSetBy does not contain UPDATED_GOAL_SET_BY
        defaultEmployeeGoalsReviewShouldBeFound("goalSetBy.doesNotContain=" + UPDATED_GOAL_SET_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeGoalsReviewList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the employeeGoalsReviewList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId is not null
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.specified=true");

        // Get all the employeeGoalsReviewList where employeeId is null
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeGoalsReviewList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeGoalsReviewList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeGoalsReviewList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeGoalsReviewList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultEmployeeGoalsReviewShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId equals to DEFAULT_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.equals=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the employeeGoalsReviewList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.equals=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId in DEFAULT_APPRAISAL_REVIEW_ID or UPDATED_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.in=" + DEFAULT_APPRAISAL_REVIEW_ID + "," + UPDATED_APPRAISAL_REVIEW_ID);

        // Get all the employeeGoalsReviewList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.in=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId is not null
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.specified=true");

        // Get all the employeeGoalsReviewList where appraisalReviewId is null
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId is greater than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.greaterThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the employeeGoalsReviewList where appraisalReviewId is greater than or equal to UPDATED_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.greaterThanOrEqual=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId is less than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.lessThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the employeeGoalsReviewList where appraisalReviewId is less than or equal to SMALLER_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.lessThanOrEqual=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId is less than DEFAULT_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.lessThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the employeeGoalsReviewList where appraisalReviewId is less than UPDATED_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.lessThan=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByAppraisalReviewIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where appraisalReviewId is greater than DEFAULT_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("appraisalReviewId.greaterThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the employeeGoalsReviewList where appraisalReviewId is greater than SMALLER_APPRAISAL_REVIEW_ID
        defaultEmployeeGoalsReviewShouldBeFound("appraisalReviewId.greaterThan=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where status equals to DEFAULT_STATUS
        defaultEmployeeGoalsReviewShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeGoalsReviewList where status equals to UPDATED_STATUS
        defaultEmployeeGoalsReviewShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeGoalsReviewShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeGoalsReviewList where status equals to UPDATED_STATUS
        defaultEmployeeGoalsReviewShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where status is not null
        defaultEmployeeGoalsReviewShouldBeFound("status.specified=true");

        // Get all the employeeGoalsReviewList where status is null
        defaultEmployeeGoalsReviewShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByStatusContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where status contains DEFAULT_STATUS
        defaultEmployeeGoalsReviewShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the employeeGoalsReviewList where status contains UPDATED_STATUS
        defaultEmployeeGoalsReviewShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where status does not contain DEFAULT_STATUS
        defaultEmployeeGoalsReviewShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the employeeGoalsReviewList where status does not contain UPDATED_STATUS
        defaultEmployeeGoalsReviewShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId equals to DEFAULT_COMPANY_ID
        defaultEmployeeGoalsReviewShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the employeeGoalsReviewList where companyId equals to UPDATED_COMPANY_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultEmployeeGoalsReviewShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the employeeGoalsReviewList where companyId equals to UPDATED_COMPANY_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId is not null
        defaultEmployeeGoalsReviewShouldBeFound("companyId.specified=true");

        // Get all the employeeGoalsReviewList where companyId is null
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultEmployeeGoalsReviewShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the employeeGoalsReviewList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultEmployeeGoalsReviewShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the employeeGoalsReviewList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId is less than DEFAULT_COMPANY_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the employeeGoalsReviewList where companyId is less than UPDATED_COMPANY_ID
        defaultEmployeeGoalsReviewShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where companyId is greater than DEFAULT_COMPANY_ID
        defaultEmployeeGoalsReviewShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the employeeGoalsReviewList where companyId is greater than SMALLER_COMPANY_ID
        defaultEmployeeGoalsReviewShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEmployeeGoalsReviewShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the employeeGoalsReviewList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEmployeeGoalsReviewShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the employeeGoalsReviewList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModified is not null
        defaultEmployeeGoalsReviewShouldBeFound("lastModified.specified=true");

        // Get all the employeeGoalsReviewList where lastModified is null
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeGoalsReviewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the employeeGoalsReviewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModifiedBy is not null
        defaultEmployeeGoalsReviewShouldBeFound("lastModifiedBy.specified=true");

        // Get all the employeeGoalsReviewList where lastModifiedBy is null
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeGoalsReviewList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeGoalsReviewsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        // Get all the employeeGoalsReviewList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeGoalsReviewList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmployeeGoalsReviewShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeGoalsReviewShouldBeFound(String filter) throws Exception {
        restEmployeeGoalsReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeGoalsReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].goalDescription").value(hasItem(DEFAULT_GOAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].goalCategory").value(hasItem(DEFAULT_GOAL_CATEGORY)))
            .andExpect(jsonPath("$.[*].goaltype").value(hasItem(DEFAULT_GOALTYPE)))
            .andExpect(jsonPath("$.[*].goalSetBy").value(hasItem(DEFAULT_GOAL_SET_BY)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restEmployeeGoalsReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeGoalsReviewShouldNotBeFound(String filter) throws Exception {
        restEmployeeGoalsReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeGoalsReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeGoalsReview() throws Exception {
        // Get the employeeGoalsReview
        restEmployeeGoalsReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeGoalsReview() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();

        // Update the employeeGoalsReview
        EmployeeGoalsReview updatedEmployeeGoalsReview = employeeGoalsReviewRepository.findById(employeeGoalsReview.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeGoalsReview are not directly saved in db
        em.detach(updatedEmployeeGoalsReview);
        updatedEmployeeGoalsReview
            .goalDescription(UPDATED_GOAL_DESCRIPTION)
            .goalCategory(UPDATED_GOAL_CATEGORY)
            .goaltype(UPDATED_GOALTYPE)
            .goalSetBy(UPDATED_GOAL_SET_BY)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(updatedEmployeeGoalsReview);

        restEmployeeGoalsReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeGoalsReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
        EmployeeGoalsReview testEmployeeGoalsReview = employeeGoalsReviewList.get(employeeGoalsReviewList.size() - 1);
        assertThat(testEmployeeGoalsReview.getGoalDescription()).isEqualTo(UPDATED_GOAL_DESCRIPTION);
        assertThat(testEmployeeGoalsReview.getGoalCategory()).isEqualTo(UPDATED_GOAL_CATEGORY);
        assertThat(testEmployeeGoalsReview.getGoaltype()).isEqualTo(UPDATED_GOALTYPE);
        assertThat(testEmployeeGoalsReview.getGoalSetBy()).isEqualTo(UPDATED_GOAL_SET_BY);
        assertThat(testEmployeeGoalsReview.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeGoalsReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testEmployeeGoalsReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeGoalsReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEmployeeGoalsReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeGoalsReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();
        employeeGoalsReview.setId(count.incrementAndGet());

        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeGoalsReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeGoalsReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();
        employeeGoalsReview.setId(count.incrementAndGet());

        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeGoalsReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();
        employeeGoalsReview.setId(count.incrementAndGet());

        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeGoalsReviewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeGoalsReviewWithPatch() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();

        // Update the employeeGoalsReview using partial update
        EmployeeGoalsReview partialUpdatedEmployeeGoalsReview = new EmployeeGoalsReview();
        partialUpdatedEmployeeGoalsReview.setId(employeeGoalsReview.getId());

        partialUpdatedEmployeeGoalsReview
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED);

        restEmployeeGoalsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeGoalsReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeGoalsReview))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
        EmployeeGoalsReview testEmployeeGoalsReview = employeeGoalsReviewList.get(employeeGoalsReviewList.size() - 1);
        assertThat(testEmployeeGoalsReview.getGoalDescription()).isEqualTo(DEFAULT_GOAL_DESCRIPTION);
        assertThat(testEmployeeGoalsReview.getGoalCategory()).isEqualTo(DEFAULT_GOAL_CATEGORY);
        assertThat(testEmployeeGoalsReview.getGoaltype()).isEqualTo(DEFAULT_GOALTYPE);
        assertThat(testEmployeeGoalsReview.getGoalSetBy()).isEqualTo(DEFAULT_GOAL_SET_BY);
        assertThat(testEmployeeGoalsReview.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeGoalsReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testEmployeeGoalsReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeGoalsReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEmployeeGoalsReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeGoalsReview.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeGoalsReviewWithPatch() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();

        // Update the employeeGoalsReview using partial update
        EmployeeGoalsReview partialUpdatedEmployeeGoalsReview = new EmployeeGoalsReview();
        partialUpdatedEmployeeGoalsReview.setId(employeeGoalsReview.getId());

        partialUpdatedEmployeeGoalsReview
            .goalDescription(UPDATED_GOAL_DESCRIPTION)
            .goalCategory(UPDATED_GOAL_CATEGORY)
            .goaltype(UPDATED_GOALTYPE)
            .goalSetBy(UPDATED_GOAL_SET_BY)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restEmployeeGoalsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeGoalsReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeGoalsReview))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
        EmployeeGoalsReview testEmployeeGoalsReview = employeeGoalsReviewList.get(employeeGoalsReviewList.size() - 1);
        assertThat(testEmployeeGoalsReview.getGoalDescription()).isEqualTo(UPDATED_GOAL_DESCRIPTION);
        assertThat(testEmployeeGoalsReview.getGoalCategory()).isEqualTo(UPDATED_GOAL_CATEGORY);
        assertThat(testEmployeeGoalsReview.getGoaltype()).isEqualTo(UPDATED_GOALTYPE);
        assertThat(testEmployeeGoalsReview.getGoalSetBy()).isEqualTo(UPDATED_GOAL_SET_BY);
        assertThat(testEmployeeGoalsReview.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeGoalsReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testEmployeeGoalsReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeGoalsReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEmployeeGoalsReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeGoalsReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();
        employeeGoalsReview.setId(count.incrementAndGet());

        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeGoalsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeGoalsReviewDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();
        employeeGoalsReview.setId(count.incrementAndGet());

        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeGoalsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeGoalsReview() throws Exception {
        int databaseSizeBeforeUpdate = employeeGoalsReviewRepository.findAll().size();
        employeeGoalsReview.setId(count.incrementAndGet());

        // Create the EmployeeGoalsReview
        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = employeeGoalsReviewMapper.toDto(employeeGoalsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeGoalsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeGoalsReviewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeGoalsReview in the database
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeGoalsReview() throws Exception {
        // Initialize the database
        employeeGoalsReviewRepository.saveAndFlush(employeeGoalsReview);

        int databaseSizeBeforeDelete = employeeGoalsReviewRepository.findAll().size();

        // Delete the employeeGoalsReview
        restEmployeeGoalsReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeGoalsReview.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeGoalsReview> employeeGoalsReviewList = employeeGoalsReviewRepository.findAll();
        assertThat(employeeGoalsReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
