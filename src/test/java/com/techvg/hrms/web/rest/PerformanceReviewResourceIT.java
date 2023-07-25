package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.PerformanceIndicator;
import com.techvg.hrms.domain.PerformanceReview;
import com.techvg.hrms.repository.PerformanceReviewRepository;
import com.techvg.hrms.service.criteria.PerformanceReviewCriteria;
import com.techvg.hrms.service.dto.PerformanceReviewDTO;
import com.techvg.hrms.service.mapper.PerformanceReviewMapper;
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
 * Integration tests for the {@link PerformanceReviewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerformanceReviewResourceIT {

    private static final Long DEFAULT_APPRAISAL_REVIEW_ID = 1L;
    private static final Long UPDATED_APPRAISAL_REVIEW_ID = 2L;
    private static final Long SMALLER_APPRAISAL_REVIEW_ID = 1L - 1L;

    private static final String DEFAULT_EMPLOYEE_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_APPRAISER_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_APPRAISER_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEWER_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWER_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_SELF_SCORED = "AAAAAAAAAA";
    private static final String UPDATED_SELF_SCORED = "BBBBBBBBBB";

    private static final String DEFAULT_SCORED_BY_APPRAISER = "AAAAAAAAAA";
    private static final String UPDATED_SCORED_BY_APPRAISER = "BBBBBBBBBB";

    private static final String DEFAULT_SCORED_BY_REVIEWER = "AAAAAAAAAA";
    private static final String UPDATED_SCORED_BY_REVIEWER = "BBBBBBBBBB";

    private static final String DEFAULT_APPRAISAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_APPRAISAL_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUBMISSION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMISSION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_APPRAISAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPRAISAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REVIEW_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVIEW_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Long DEFAULT_TARGET = 1L;
    private static final Long UPDATED_TARGET = 2L;
    private static final Long SMALLER_TARGET = 1L - 1L;

    private static final Long DEFAULT_TARGET_ACHIVED = 1L;
    private static final Long UPDATED_TARGET_ACHIVED = 2L;
    private static final Long SMALLER_TARGET_ACHIVED = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/performance-reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    private PerformanceReviewMapper performanceReviewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerformanceReviewMockMvc;

    private PerformanceReview performanceReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceReview createEntity(EntityManager em) {
        PerformanceReview performanceReview = new PerformanceReview()
            .appraisalReviewId(DEFAULT_APPRAISAL_REVIEW_ID)
            .employeeRemark(DEFAULT_EMPLOYEE_REMARK)
            .appraiserRemark(DEFAULT_APPRAISER_REMARK)
            .reviewerRemark(DEFAULT_REVIEWER_REMARK)
            .selfScored(DEFAULT_SELF_SCORED)
            .scoredByAppraiser(DEFAULT_SCORED_BY_APPRAISER)
            .scoredByReviewer(DEFAULT_SCORED_BY_REVIEWER)
            .appraisalStatus(DEFAULT_APPRAISAL_STATUS)
            .submissionDate(DEFAULT_SUBMISSION_DATE)
            .appraisalDate(DEFAULT_APPRAISAL_DATE)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .status(DEFAULT_STATUS)
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .target(DEFAULT_TARGET)
            .targetAchived(DEFAULT_TARGET_ACHIVED);
        return performanceReview;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceReview createUpdatedEntity(EntityManager em) {
        PerformanceReview performanceReview = new PerformanceReview()
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .employeeRemark(UPDATED_EMPLOYEE_REMARK)
            .appraiserRemark(UPDATED_APPRAISER_REMARK)
            .reviewerRemark(UPDATED_REVIEWER_REMARK)
            .selfScored(UPDATED_SELF_SCORED)
            .scoredByAppraiser(UPDATED_SCORED_BY_APPRAISER)
            .scoredByReviewer(UPDATED_SCORED_BY_REVIEWER)
            .appraisalStatus(UPDATED_APPRAISAL_STATUS)
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .appraisalDate(UPDATED_APPRAISAL_DATE)
            .reviewDate(UPDATED_REVIEW_DATE)
            .status(UPDATED_STATUS)
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .target(UPDATED_TARGET)
            .targetAchived(UPDATED_TARGET_ACHIVED);
        return performanceReview;
    }

    @BeforeEach
    public void initTest() {
        performanceReview = createEntity(em);
    }

    @Test
    @Transactional
    void createPerformanceReview() throws Exception {
        int databaseSizeBeforeCreate = performanceReviewRepository.findAll().size();
        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);
        restPerformanceReviewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeCreate + 1);
        PerformanceReview testPerformanceReview = performanceReviewList.get(performanceReviewList.size() - 1);
        assertThat(testPerformanceReview.getAppraisalReviewId()).isEqualTo(DEFAULT_APPRAISAL_REVIEW_ID);
        assertThat(testPerformanceReview.getEmployeeRemark()).isEqualTo(DEFAULT_EMPLOYEE_REMARK);
        assertThat(testPerformanceReview.getAppraiserRemark()).isEqualTo(DEFAULT_APPRAISER_REMARK);
        assertThat(testPerformanceReview.getReviewerRemark()).isEqualTo(DEFAULT_REVIEWER_REMARK);
        assertThat(testPerformanceReview.getSelfScored()).isEqualTo(DEFAULT_SELF_SCORED);
        assertThat(testPerformanceReview.getScoredByAppraiser()).isEqualTo(DEFAULT_SCORED_BY_APPRAISER);
        assertThat(testPerformanceReview.getScoredByReviewer()).isEqualTo(DEFAULT_SCORED_BY_REVIEWER);
        assertThat(testPerformanceReview.getAppraisalStatus()).isEqualTo(DEFAULT_APPRAISAL_STATUS);
        assertThat(testPerformanceReview.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testPerformanceReview.getAppraisalDate()).isEqualTo(DEFAULT_APPRAISAL_DATE);
        assertThat(testPerformanceReview.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testPerformanceReview.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerformanceReview.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPerformanceReview.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPerformanceReview.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPerformanceReview.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testPerformanceReview.getTargetAchived()).isEqualTo(DEFAULT_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void createPerformanceReviewWithExistingId() throws Exception {
        // Create the PerformanceReview with an existing ID
        performanceReview.setId(1L);
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        int databaseSizeBeforeCreate = performanceReviewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceReviewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerformanceReviews() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeRemark").value(hasItem(DEFAULT_EMPLOYEE_REMARK)))
            .andExpect(jsonPath("$.[*].appraiserRemark").value(hasItem(DEFAULT_APPRAISER_REMARK)))
            .andExpect(jsonPath("$.[*].reviewerRemark").value(hasItem(DEFAULT_REVIEWER_REMARK)))
            .andExpect(jsonPath("$.[*].selfScored").value(hasItem(DEFAULT_SELF_SCORED)))
            .andExpect(jsonPath("$.[*].scoredByAppraiser").value(hasItem(DEFAULT_SCORED_BY_APPRAISER)))
            .andExpect(jsonPath("$.[*].scoredByReviewer").value(hasItem(DEFAULT_SCORED_BY_REVIEWER)))
            .andExpect(jsonPath("$.[*].appraisalStatus").value(hasItem(DEFAULT_APPRAISAL_STATUS)))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].appraisalDate").value(hasItem(DEFAULT_APPRAISAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.intValue())))
            .andExpect(jsonPath("$.[*].targetAchived").value(hasItem(DEFAULT_TARGET_ACHIVED.intValue())));
    }

    @Test
    @Transactional
    void getPerformanceReview() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get the performanceReview
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, performanceReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(performanceReview.getId().intValue()))
            .andExpect(jsonPath("$.appraisalReviewId").value(DEFAULT_APPRAISAL_REVIEW_ID.intValue()))
            .andExpect(jsonPath("$.employeeRemark").value(DEFAULT_EMPLOYEE_REMARK))
            .andExpect(jsonPath("$.appraiserRemark").value(DEFAULT_APPRAISER_REMARK))
            .andExpect(jsonPath("$.reviewerRemark").value(DEFAULT_REVIEWER_REMARK))
            .andExpect(jsonPath("$.selfScored").value(DEFAULT_SELF_SCORED))
            .andExpect(jsonPath("$.scoredByAppraiser").value(DEFAULT_SCORED_BY_APPRAISER))
            .andExpect(jsonPath("$.scoredByReviewer").value(DEFAULT_SCORED_BY_REVIEWER))
            .andExpect(jsonPath("$.appraisalStatus").value(DEFAULT_APPRAISAL_STATUS))
            .andExpect(jsonPath("$.submissionDate").value(DEFAULT_SUBMISSION_DATE.toString()))
            .andExpect(jsonPath("$.appraisalDate").value(DEFAULT_APPRAISAL_DATE.toString()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET.intValue()))
            .andExpect(jsonPath("$.targetAchived").value(DEFAULT_TARGET_ACHIVED.intValue()));
    }

    @Test
    @Transactional
    void getPerformanceReviewsByIdFiltering() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        Long id = performanceReview.getId();

        defaultPerformanceReviewShouldBeFound("id.equals=" + id);
        defaultPerformanceReviewShouldNotBeFound("id.notEquals=" + id);

        defaultPerformanceReviewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPerformanceReviewShouldNotBeFound("id.greaterThan=" + id);

        defaultPerformanceReviewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPerformanceReviewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId equals to DEFAULT_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.equals=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the performanceReviewList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.equals=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId in DEFAULT_APPRAISAL_REVIEW_ID or UPDATED_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.in=" + DEFAULT_APPRAISAL_REVIEW_ID + "," + UPDATED_APPRAISAL_REVIEW_ID);

        // Get all the performanceReviewList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.in=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId is not null
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.specified=true");

        // Get all the performanceReviewList where appraisalReviewId is null
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId is greater than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.greaterThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the performanceReviewList where appraisalReviewId is greater than or equal to UPDATED_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.greaterThanOrEqual=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId is less than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.lessThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the performanceReviewList where appraisalReviewId is less than or equal to SMALLER_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.lessThanOrEqual=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId is less than DEFAULT_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.lessThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the performanceReviewList where appraisalReviewId is less than UPDATED_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.lessThan=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalReviewIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalReviewId is greater than DEFAULT_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldNotBeFound("appraisalReviewId.greaterThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the performanceReviewList where appraisalReviewId is greater than SMALLER_APPRAISAL_REVIEW_ID
        defaultPerformanceReviewShouldBeFound("appraisalReviewId.greaterThan=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByEmployeeRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where employeeRemark equals to DEFAULT_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldBeFound("employeeRemark.equals=" + DEFAULT_EMPLOYEE_REMARK);

        // Get all the performanceReviewList where employeeRemark equals to UPDATED_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldNotBeFound("employeeRemark.equals=" + UPDATED_EMPLOYEE_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByEmployeeRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where employeeRemark in DEFAULT_EMPLOYEE_REMARK or UPDATED_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldBeFound("employeeRemark.in=" + DEFAULT_EMPLOYEE_REMARK + "," + UPDATED_EMPLOYEE_REMARK);

        // Get all the performanceReviewList where employeeRemark equals to UPDATED_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldNotBeFound("employeeRemark.in=" + UPDATED_EMPLOYEE_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByEmployeeRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where employeeRemark is not null
        defaultPerformanceReviewShouldBeFound("employeeRemark.specified=true");

        // Get all the performanceReviewList where employeeRemark is null
        defaultPerformanceReviewShouldNotBeFound("employeeRemark.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByEmployeeRemarkContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where employeeRemark contains DEFAULT_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldBeFound("employeeRemark.contains=" + DEFAULT_EMPLOYEE_REMARK);

        // Get all the performanceReviewList where employeeRemark contains UPDATED_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldNotBeFound("employeeRemark.contains=" + UPDATED_EMPLOYEE_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByEmployeeRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where employeeRemark does not contain DEFAULT_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldNotBeFound("employeeRemark.doesNotContain=" + DEFAULT_EMPLOYEE_REMARK);

        // Get all the performanceReviewList where employeeRemark does not contain UPDATED_EMPLOYEE_REMARK
        defaultPerformanceReviewShouldBeFound("employeeRemark.doesNotContain=" + UPDATED_EMPLOYEE_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraiserRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraiserRemark equals to DEFAULT_APPRAISER_REMARK
        defaultPerformanceReviewShouldBeFound("appraiserRemark.equals=" + DEFAULT_APPRAISER_REMARK);

        // Get all the performanceReviewList where appraiserRemark equals to UPDATED_APPRAISER_REMARK
        defaultPerformanceReviewShouldNotBeFound("appraiserRemark.equals=" + UPDATED_APPRAISER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraiserRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraiserRemark in DEFAULT_APPRAISER_REMARK or UPDATED_APPRAISER_REMARK
        defaultPerformanceReviewShouldBeFound("appraiserRemark.in=" + DEFAULT_APPRAISER_REMARK + "," + UPDATED_APPRAISER_REMARK);

        // Get all the performanceReviewList where appraiserRemark equals to UPDATED_APPRAISER_REMARK
        defaultPerformanceReviewShouldNotBeFound("appraiserRemark.in=" + UPDATED_APPRAISER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraiserRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraiserRemark is not null
        defaultPerformanceReviewShouldBeFound("appraiserRemark.specified=true");

        // Get all the performanceReviewList where appraiserRemark is null
        defaultPerformanceReviewShouldNotBeFound("appraiserRemark.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraiserRemarkContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraiserRemark contains DEFAULT_APPRAISER_REMARK
        defaultPerformanceReviewShouldBeFound("appraiserRemark.contains=" + DEFAULT_APPRAISER_REMARK);

        // Get all the performanceReviewList where appraiserRemark contains UPDATED_APPRAISER_REMARK
        defaultPerformanceReviewShouldNotBeFound("appraiserRemark.contains=" + UPDATED_APPRAISER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraiserRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraiserRemark does not contain DEFAULT_APPRAISER_REMARK
        defaultPerformanceReviewShouldNotBeFound("appraiserRemark.doesNotContain=" + DEFAULT_APPRAISER_REMARK);

        // Get all the performanceReviewList where appraiserRemark does not contain UPDATED_APPRAISER_REMARK
        defaultPerformanceReviewShouldBeFound("appraiserRemark.doesNotContain=" + UPDATED_APPRAISER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewerRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewerRemark equals to DEFAULT_REVIEWER_REMARK
        defaultPerformanceReviewShouldBeFound("reviewerRemark.equals=" + DEFAULT_REVIEWER_REMARK);

        // Get all the performanceReviewList where reviewerRemark equals to UPDATED_REVIEWER_REMARK
        defaultPerformanceReviewShouldNotBeFound("reviewerRemark.equals=" + UPDATED_REVIEWER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewerRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewerRemark in DEFAULT_REVIEWER_REMARK or UPDATED_REVIEWER_REMARK
        defaultPerformanceReviewShouldBeFound("reviewerRemark.in=" + DEFAULT_REVIEWER_REMARK + "," + UPDATED_REVIEWER_REMARK);

        // Get all the performanceReviewList where reviewerRemark equals to UPDATED_REVIEWER_REMARK
        defaultPerformanceReviewShouldNotBeFound("reviewerRemark.in=" + UPDATED_REVIEWER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewerRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewerRemark is not null
        defaultPerformanceReviewShouldBeFound("reviewerRemark.specified=true");

        // Get all the performanceReviewList where reviewerRemark is null
        defaultPerformanceReviewShouldNotBeFound("reviewerRemark.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewerRemarkContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewerRemark contains DEFAULT_REVIEWER_REMARK
        defaultPerformanceReviewShouldBeFound("reviewerRemark.contains=" + DEFAULT_REVIEWER_REMARK);

        // Get all the performanceReviewList where reviewerRemark contains UPDATED_REVIEWER_REMARK
        defaultPerformanceReviewShouldNotBeFound("reviewerRemark.contains=" + UPDATED_REVIEWER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewerRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewerRemark does not contain DEFAULT_REVIEWER_REMARK
        defaultPerformanceReviewShouldNotBeFound("reviewerRemark.doesNotContain=" + DEFAULT_REVIEWER_REMARK);

        // Get all the performanceReviewList where reviewerRemark does not contain UPDATED_REVIEWER_REMARK
        defaultPerformanceReviewShouldBeFound("reviewerRemark.doesNotContain=" + UPDATED_REVIEWER_REMARK);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySelfScoredIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where selfScored equals to DEFAULT_SELF_SCORED
        defaultPerformanceReviewShouldBeFound("selfScored.equals=" + DEFAULT_SELF_SCORED);

        // Get all the performanceReviewList where selfScored equals to UPDATED_SELF_SCORED
        defaultPerformanceReviewShouldNotBeFound("selfScored.equals=" + UPDATED_SELF_SCORED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySelfScoredIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where selfScored in DEFAULT_SELF_SCORED or UPDATED_SELF_SCORED
        defaultPerformanceReviewShouldBeFound("selfScored.in=" + DEFAULT_SELF_SCORED + "," + UPDATED_SELF_SCORED);

        // Get all the performanceReviewList where selfScored equals to UPDATED_SELF_SCORED
        defaultPerformanceReviewShouldNotBeFound("selfScored.in=" + UPDATED_SELF_SCORED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySelfScoredIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where selfScored is not null
        defaultPerformanceReviewShouldBeFound("selfScored.specified=true");

        // Get all the performanceReviewList where selfScored is null
        defaultPerformanceReviewShouldNotBeFound("selfScored.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySelfScoredContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where selfScored contains DEFAULT_SELF_SCORED
        defaultPerformanceReviewShouldBeFound("selfScored.contains=" + DEFAULT_SELF_SCORED);

        // Get all the performanceReviewList where selfScored contains UPDATED_SELF_SCORED
        defaultPerformanceReviewShouldNotBeFound("selfScored.contains=" + UPDATED_SELF_SCORED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySelfScoredNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where selfScored does not contain DEFAULT_SELF_SCORED
        defaultPerformanceReviewShouldNotBeFound("selfScored.doesNotContain=" + DEFAULT_SELF_SCORED);

        // Get all the performanceReviewList where selfScored does not contain UPDATED_SELF_SCORED
        defaultPerformanceReviewShouldBeFound("selfScored.doesNotContain=" + UPDATED_SELF_SCORED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByAppraiserIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByAppraiser equals to DEFAULT_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldBeFound("scoredByAppraiser.equals=" + DEFAULT_SCORED_BY_APPRAISER);

        // Get all the performanceReviewList where scoredByAppraiser equals to UPDATED_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldNotBeFound("scoredByAppraiser.equals=" + UPDATED_SCORED_BY_APPRAISER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByAppraiserIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByAppraiser in DEFAULT_SCORED_BY_APPRAISER or UPDATED_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldBeFound("scoredByAppraiser.in=" + DEFAULT_SCORED_BY_APPRAISER + "," + UPDATED_SCORED_BY_APPRAISER);

        // Get all the performanceReviewList where scoredByAppraiser equals to UPDATED_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldNotBeFound("scoredByAppraiser.in=" + UPDATED_SCORED_BY_APPRAISER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByAppraiserIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByAppraiser is not null
        defaultPerformanceReviewShouldBeFound("scoredByAppraiser.specified=true");

        // Get all the performanceReviewList where scoredByAppraiser is null
        defaultPerformanceReviewShouldNotBeFound("scoredByAppraiser.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByAppraiserContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByAppraiser contains DEFAULT_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldBeFound("scoredByAppraiser.contains=" + DEFAULT_SCORED_BY_APPRAISER);

        // Get all the performanceReviewList where scoredByAppraiser contains UPDATED_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldNotBeFound("scoredByAppraiser.contains=" + UPDATED_SCORED_BY_APPRAISER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByAppraiserNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByAppraiser does not contain DEFAULT_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldNotBeFound("scoredByAppraiser.doesNotContain=" + DEFAULT_SCORED_BY_APPRAISER);

        // Get all the performanceReviewList where scoredByAppraiser does not contain UPDATED_SCORED_BY_APPRAISER
        defaultPerformanceReviewShouldBeFound("scoredByAppraiser.doesNotContain=" + UPDATED_SCORED_BY_APPRAISER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByReviewerIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByReviewer equals to DEFAULT_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldBeFound("scoredByReviewer.equals=" + DEFAULT_SCORED_BY_REVIEWER);

        // Get all the performanceReviewList where scoredByReviewer equals to UPDATED_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldNotBeFound("scoredByReviewer.equals=" + UPDATED_SCORED_BY_REVIEWER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByReviewerIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByReviewer in DEFAULT_SCORED_BY_REVIEWER or UPDATED_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldBeFound("scoredByReviewer.in=" + DEFAULT_SCORED_BY_REVIEWER + "," + UPDATED_SCORED_BY_REVIEWER);

        // Get all the performanceReviewList where scoredByReviewer equals to UPDATED_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldNotBeFound("scoredByReviewer.in=" + UPDATED_SCORED_BY_REVIEWER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByReviewerIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByReviewer is not null
        defaultPerformanceReviewShouldBeFound("scoredByReviewer.specified=true");

        // Get all the performanceReviewList where scoredByReviewer is null
        defaultPerformanceReviewShouldNotBeFound("scoredByReviewer.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByReviewerContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByReviewer contains DEFAULT_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldBeFound("scoredByReviewer.contains=" + DEFAULT_SCORED_BY_REVIEWER);

        // Get all the performanceReviewList where scoredByReviewer contains UPDATED_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldNotBeFound("scoredByReviewer.contains=" + UPDATED_SCORED_BY_REVIEWER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByScoredByReviewerNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where scoredByReviewer does not contain DEFAULT_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldNotBeFound("scoredByReviewer.doesNotContain=" + DEFAULT_SCORED_BY_REVIEWER);

        // Get all the performanceReviewList where scoredByReviewer does not contain UPDATED_SCORED_BY_REVIEWER
        defaultPerformanceReviewShouldBeFound("scoredByReviewer.doesNotContain=" + UPDATED_SCORED_BY_REVIEWER);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalStatus equals to DEFAULT_APPRAISAL_STATUS
        defaultPerformanceReviewShouldBeFound("appraisalStatus.equals=" + DEFAULT_APPRAISAL_STATUS);

        // Get all the performanceReviewList where appraisalStatus equals to UPDATED_APPRAISAL_STATUS
        defaultPerformanceReviewShouldNotBeFound("appraisalStatus.equals=" + UPDATED_APPRAISAL_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalStatus in DEFAULT_APPRAISAL_STATUS or UPDATED_APPRAISAL_STATUS
        defaultPerformanceReviewShouldBeFound("appraisalStatus.in=" + DEFAULT_APPRAISAL_STATUS + "," + UPDATED_APPRAISAL_STATUS);

        // Get all the performanceReviewList where appraisalStatus equals to UPDATED_APPRAISAL_STATUS
        defaultPerformanceReviewShouldNotBeFound("appraisalStatus.in=" + UPDATED_APPRAISAL_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalStatus is not null
        defaultPerformanceReviewShouldBeFound("appraisalStatus.specified=true");

        // Get all the performanceReviewList where appraisalStatus is null
        defaultPerformanceReviewShouldNotBeFound("appraisalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalStatusContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalStatus contains DEFAULT_APPRAISAL_STATUS
        defaultPerformanceReviewShouldBeFound("appraisalStatus.contains=" + DEFAULT_APPRAISAL_STATUS);

        // Get all the performanceReviewList where appraisalStatus contains UPDATED_APPRAISAL_STATUS
        defaultPerformanceReviewShouldNotBeFound("appraisalStatus.contains=" + UPDATED_APPRAISAL_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalStatus does not contain DEFAULT_APPRAISAL_STATUS
        defaultPerformanceReviewShouldNotBeFound("appraisalStatus.doesNotContain=" + DEFAULT_APPRAISAL_STATUS);

        // Get all the performanceReviewList where appraisalStatus does not contain UPDATED_APPRAISAL_STATUS
        defaultPerformanceReviewShouldBeFound("appraisalStatus.doesNotContain=" + UPDATED_APPRAISAL_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySubmissionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where submissionDate equals to DEFAULT_SUBMISSION_DATE
        defaultPerformanceReviewShouldBeFound("submissionDate.equals=" + DEFAULT_SUBMISSION_DATE);

        // Get all the performanceReviewList where submissionDate equals to UPDATED_SUBMISSION_DATE
        defaultPerformanceReviewShouldNotBeFound("submissionDate.equals=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySubmissionDateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where submissionDate in DEFAULT_SUBMISSION_DATE or UPDATED_SUBMISSION_DATE
        defaultPerformanceReviewShouldBeFound("submissionDate.in=" + DEFAULT_SUBMISSION_DATE + "," + UPDATED_SUBMISSION_DATE);

        // Get all the performanceReviewList where submissionDate equals to UPDATED_SUBMISSION_DATE
        defaultPerformanceReviewShouldNotBeFound("submissionDate.in=" + UPDATED_SUBMISSION_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsBySubmissionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where submissionDate is not null
        defaultPerformanceReviewShouldBeFound("submissionDate.specified=true");

        // Get all the performanceReviewList where submissionDate is null
        defaultPerformanceReviewShouldNotBeFound("submissionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalDate equals to DEFAULT_APPRAISAL_DATE
        defaultPerformanceReviewShouldBeFound("appraisalDate.equals=" + DEFAULT_APPRAISAL_DATE);

        // Get all the performanceReviewList where appraisalDate equals to UPDATED_APPRAISAL_DATE
        defaultPerformanceReviewShouldNotBeFound("appraisalDate.equals=" + UPDATED_APPRAISAL_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalDateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalDate in DEFAULT_APPRAISAL_DATE or UPDATED_APPRAISAL_DATE
        defaultPerformanceReviewShouldBeFound("appraisalDate.in=" + DEFAULT_APPRAISAL_DATE + "," + UPDATED_APPRAISAL_DATE);

        // Get all the performanceReviewList where appraisalDate equals to UPDATED_APPRAISAL_DATE
        defaultPerformanceReviewShouldNotBeFound("appraisalDate.in=" + UPDATED_APPRAISAL_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByAppraisalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where appraisalDate is not null
        defaultPerformanceReviewShouldBeFound("appraisalDate.specified=true");

        // Get all the performanceReviewList where appraisalDate is null
        defaultPerformanceReviewShouldNotBeFound("appraisalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate equals to DEFAULT_REVIEW_DATE
        defaultPerformanceReviewShouldBeFound("reviewDate.equals=" + DEFAULT_REVIEW_DATE);

        // Get all the performanceReviewList where reviewDate equals to UPDATED_REVIEW_DATE
        defaultPerformanceReviewShouldNotBeFound("reviewDate.equals=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate in DEFAULT_REVIEW_DATE or UPDATED_REVIEW_DATE
        defaultPerformanceReviewShouldBeFound("reviewDate.in=" + DEFAULT_REVIEW_DATE + "," + UPDATED_REVIEW_DATE);

        // Get all the performanceReviewList where reviewDate equals to UPDATED_REVIEW_DATE
        defaultPerformanceReviewShouldNotBeFound("reviewDate.in=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate is not null
        defaultPerformanceReviewShouldBeFound("reviewDate.specified=true");

        // Get all the performanceReviewList where reviewDate is null
        defaultPerformanceReviewShouldNotBeFound("reviewDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where status equals to DEFAULT_STATUS
        defaultPerformanceReviewShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the performanceReviewList where status equals to UPDATED_STATUS
        defaultPerformanceReviewShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPerformanceReviewShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the performanceReviewList where status equals to UPDATED_STATUS
        defaultPerformanceReviewShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where status is not null
        defaultPerformanceReviewShouldBeFound("status.specified=true");

        // Get all the performanceReviewList where status is null
        defaultPerformanceReviewShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByStatusContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where status contains DEFAULT_STATUS
        defaultPerformanceReviewShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the performanceReviewList where status contains UPDATED_STATUS
        defaultPerformanceReviewShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where status does not contain DEFAULT_STATUS
        defaultPerformanceReviewShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the performanceReviewList where status does not contain UPDATED_STATUS
        defaultPerformanceReviewShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId equals to DEFAULT_COMPANY_ID
        defaultPerformanceReviewShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the performanceReviewList where companyId equals to UPDATED_COMPANY_ID
        defaultPerformanceReviewShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultPerformanceReviewShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the performanceReviewList where companyId equals to UPDATED_COMPANY_ID
        defaultPerformanceReviewShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId is not null
        defaultPerformanceReviewShouldBeFound("companyId.specified=true");

        // Get all the performanceReviewList where companyId is null
        defaultPerformanceReviewShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultPerformanceReviewShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the performanceReviewList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultPerformanceReviewShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultPerformanceReviewShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the performanceReviewList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultPerformanceReviewShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId is less than DEFAULT_COMPANY_ID
        defaultPerformanceReviewShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the performanceReviewList where companyId is less than UPDATED_COMPANY_ID
        defaultPerformanceReviewShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where companyId is greater than DEFAULT_COMPANY_ID
        defaultPerformanceReviewShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the performanceReviewList where companyId is greater than SMALLER_COMPANY_ID
        defaultPerformanceReviewShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPerformanceReviewShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the performanceReviewList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPerformanceReviewShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPerformanceReviewShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the performanceReviewList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPerformanceReviewShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModified is not null
        defaultPerformanceReviewShouldBeFound("lastModified.specified=true");

        // Get all the performanceReviewList where lastModified is null
        defaultPerformanceReviewShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the performanceReviewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the performanceReviewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModifiedBy is not null
        defaultPerformanceReviewShouldBeFound("lastModifiedBy.specified=true");

        // Get all the performanceReviewList where lastModifiedBy is null
        defaultPerformanceReviewShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the performanceReviewList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the performanceReviewList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPerformanceReviewShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target equals to DEFAULT_TARGET
        defaultPerformanceReviewShouldBeFound("target.equals=" + DEFAULT_TARGET);

        // Get all the performanceReviewList where target equals to UPDATED_TARGET
        defaultPerformanceReviewShouldNotBeFound("target.equals=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target in DEFAULT_TARGET or UPDATED_TARGET
        defaultPerformanceReviewShouldBeFound("target.in=" + DEFAULT_TARGET + "," + UPDATED_TARGET);

        // Get all the performanceReviewList where target equals to UPDATED_TARGET
        defaultPerformanceReviewShouldNotBeFound("target.in=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target is not null
        defaultPerformanceReviewShouldBeFound("target.specified=true");

        // Get all the performanceReviewList where target is null
        defaultPerformanceReviewShouldNotBeFound("target.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target is greater than or equal to DEFAULT_TARGET
        defaultPerformanceReviewShouldBeFound("target.greaterThanOrEqual=" + DEFAULT_TARGET);

        // Get all the performanceReviewList where target is greater than or equal to UPDATED_TARGET
        defaultPerformanceReviewShouldNotBeFound("target.greaterThanOrEqual=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target is less than or equal to DEFAULT_TARGET
        defaultPerformanceReviewShouldBeFound("target.lessThanOrEqual=" + DEFAULT_TARGET);

        // Get all the performanceReviewList where target is less than or equal to SMALLER_TARGET
        defaultPerformanceReviewShouldNotBeFound("target.lessThanOrEqual=" + SMALLER_TARGET);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target is less than DEFAULT_TARGET
        defaultPerformanceReviewShouldNotBeFound("target.lessThan=" + DEFAULT_TARGET);

        // Get all the performanceReviewList where target is less than UPDATED_TARGET
        defaultPerformanceReviewShouldBeFound("target.lessThan=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where target is greater than DEFAULT_TARGET
        defaultPerformanceReviewShouldNotBeFound("target.greaterThan=" + DEFAULT_TARGET);

        // Get all the performanceReviewList where target is greater than SMALLER_TARGET
        defaultPerformanceReviewShouldBeFound("target.greaterThan=" + SMALLER_TARGET);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived equals to DEFAULT_TARGET_ACHIVED
        defaultPerformanceReviewShouldBeFound("targetAchived.equals=" + DEFAULT_TARGET_ACHIVED);

        // Get all the performanceReviewList where targetAchived equals to UPDATED_TARGET_ACHIVED
        defaultPerformanceReviewShouldNotBeFound("targetAchived.equals=" + UPDATED_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived in DEFAULT_TARGET_ACHIVED or UPDATED_TARGET_ACHIVED
        defaultPerformanceReviewShouldBeFound("targetAchived.in=" + DEFAULT_TARGET_ACHIVED + "," + UPDATED_TARGET_ACHIVED);

        // Get all the performanceReviewList where targetAchived equals to UPDATED_TARGET_ACHIVED
        defaultPerformanceReviewShouldNotBeFound("targetAchived.in=" + UPDATED_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived is not null
        defaultPerformanceReviewShouldBeFound("targetAchived.specified=true");

        // Get all the performanceReviewList where targetAchived is null
        defaultPerformanceReviewShouldNotBeFound("targetAchived.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived is greater than or equal to DEFAULT_TARGET_ACHIVED
        defaultPerformanceReviewShouldBeFound("targetAchived.greaterThanOrEqual=" + DEFAULT_TARGET_ACHIVED);

        // Get all the performanceReviewList where targetAchived is greater than or equal to UPDATED_TARGET_ACHIVED
        defaultPerformanceReviewShouldNotBeFound("targetAchived.greaterThanOrEqual=" + UPDATED_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived is less than or equal to DEFAULT_TARGET_ACHIVED
        defaultPerformanceReviewShouldBeFound("targetAchived.lessThanOrEqual=" + DEFAULT_TARGET_ACHIVED);

        // Get all the performanceReviewList where targetAchived is less than or equal to SMALLER_TARGET_ACHIVED
        defaultPerformanceReviewShouldNotBeFound("targetAchived.lessThanOrEqual=" + SMALLER_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived is less than DEFAULT_TARGET_ACHIVED
        defaultPerformanceReviewShouldNotBeFound("targetAchived.lessThan=" + DEFAULT_TARGET_ACHIVED);

        // Get all the performanceReviewList where targetAchived is less than UPDATED_TARGET_ACHIVED
        defaultPerformanceReviewShouldBeFound("targetAchived.lessThan=" + UPDATED_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByTargetAchivedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where targetAchived is greater than DEFAULT_TARGET_ACHIVED
        defaultPerformanceReviewShouldNotBeFound("targetAchived.greaterThan=" + DEFAULT_TARGET_ACHIVED);

        // Get all the performanceReviewList where targetAchived is greater than SMALLER_TARGET_ACHIVED
        defaultPerformanceReviewShouldBeFound("targetAchived.greaterThan=" + SMALLER_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByPerformanceIndicatorIsEqualToSomething() throws Exception {
        PerformanceIndicator performanceIndicator;
        if (TestUtil.findAll(em, PerformanceIndicator.class).isEmpty()) {
            performanceReviewRepository.saveAndFlush(performanceReview);
            performanceIndicator = PerformanceIndicatorResourceIT.createEntity(em);
        } else {
            performanceIndicator = TestUtil.findAll(em, PerformanceIndicator.class).get(0);
        }
        em.persist(performanceIndicator);
        em.flush();
        performanceReview.setPerformanceIndicator(performanceIndicator);
        performanceReviewRepository.saveAndFlush(performanceReview);
        Long performanceIndicatorId = performanceIndicator.getId();

        // Get all the performanceReviewList where performanceIndicator equals to performanceIndicatorId
        defaultPerformanceReviewShouldBeFound("performanceIndicatorId.equals=" + performanceIndicatorId);

        // Get all the performanceReviewList where performanceIndicator equals to (performanceIndicatorId + 1)
        defaultPerformanceReviewShouldNotBeFound("performanceIndicatorId.equals=" + (performanceIndicatorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerformanceReviewShouldBeFound(String filter) throws Exception {
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeRemark").value(hasItem(DEFAULT_EMPLOYEE_REMARK)))
            .andExpect(jsonPath("$.[*].appraiserRemark").value(hasItem(DEFAULT_APPRAISER_REMARK)))
            .andExpect(jsonPath("$.[*].reviewerRemark").value(hasItem(DEFAULT_REVIEWER_REMARK)))
            .andExpect(jsonPath("$.[*].selfScored").value(hasItem(DEFAULT_SELF_SCORED)))
            .andExpect(jsonPath("$.[*].scoredByAppraiser").value(hasItem(DEFAULT_SCORED_BY_APPRAISER)))
            .andExpect(jsonPath("$.[*].scoredByReviewer").value(hasItem(DEFAULT_SCORED_BY_REVIEWER)))
            .andExpect(jsonPath("$.[*].appraisalStatus").value(hasItem(DEFAULT_APPRAISAL_STATUS)))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].appraisalDate").value(hasItem(DEFAULT_APPRAISAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.intValue())))
            .andExpect(jsonPath("$.[*].targetAchived").value(hasItem(DEFAULT_TARGET_ACHIVED.intValue())));

        // Check, that the count call also returns 1
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerformanceReviewShouldNotBeFound(String filter) throws Exception {
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerformanceReview() throws Exception {
        // Get the performanceReview
        restPerformanceReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerformanceReview() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();

        // Update the performanceReview
        PerformanceReview updatedPerformanceReview = performanceReviewRepository.findById(performanceReview.getId()).get();
        // Disconnect from session so that the updates on updatedPerformanceReview are not directly saved in db
        em.detach(updatedPerformanceReview);
        updatedPerformanceReview
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .employeeRemark(UPDATED_EMPLOYEE_REMARK)
            .appraiserRemark(UPDATED_APPRAISER_REMARK)
            .reviewerRemark(UPDATED_REVIEWER_REMARK)
            .selfScored(UPDATED_SELF_SCORED)
            .scoredByAppraiser(UPDATED_SCORED_BY_APPRAISER)
            .scoredByReviewer(UPDATED_SCORED_BY_REVIEWER)
            .appraisalStatus(UPDATED_APPRAISAL_STATUS)
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .appraisalDate(UPDATED_APPRAISAL_DATE)
            .reviewDate(UPDATED_REVIEW_DATE)
            .status(UPDATED_STATUS)
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .target(UPDATED_TARGET)
            .targetAchived(UPDATED_TARGET_ACHIVED);
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(updatedPerformanceReview);

        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
        PerformanceReview testPerformanceReview = performanceReviewList.get(performanceReviewList.size() - 1);
        assertThat(testPerformanceReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testPerformanceReview.getEmployeeRemark()).isEqualTo(UPDATED_EMPLOYEE_REMARK);
        assertThat(testPerformanceReview.getAppraiserRemark()).isEqualTo(UPDATED_APPRAISER_REMARK);
        assertThat(testPerformanceReview.getReviewerRemark()).isEqualTo(UPDATED_REVIEWER_REMARK);
        assertThat(testPerformanceReview.getSelfScored()).isEqualTo(UPDATED_SELF_SCORED);
        assertThat(testPerformanceReview.getScoredByAppraiser()).isEqualTo(UPDATED_SCORED_BY_APPRAISER);
        assertThat(testPerformanceReview.getScoredByReviewer()).isEqualTo(UPDATED_SCORED_BY_REVIEWER);
        assertThat(testPerformanceReview.getAppraisalStatus()).isEqualTo(UPDATED_APPRAISAL_STATUS);
        assertThat(testPerformanceReview.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testPerformanceReview.getAppraisalDate()).isEqualTo(UPDATED_APPRAISAL_DATE);
        assertThat(testPerformanceReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testPerformanceReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerformanceReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPerformanceReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPerformanceReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPerformanceReview.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testPerformanceReview.getTargetAchived()).isEqualTo(UPDATED_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void putNonExistingPerformanceReview() throws Exception {
        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();
        performanceReview.setId(count.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerformanceReview() throws Exception {
        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();
        performanceReview.setId(count.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerformanceReview() throws Exception {
        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();
        performanceReview.setId(count.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerformanceReviewWithPatch() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();

        // Update the performanceReview using partial update
        PerformanceReview partialUpdatedPerformanceReview = new PerformanceReview();
        partialUpdatedPerformanceReview.setId(performanceReview.getId());

        partialUpdatedPerformanceReview
            .employeeRemark(UPDATED_EMPLOYEE_REMARK)
            .reviewerRemark(UPDATED_REVIEWER_REMARK)
            .appraisalStatus(UPDATED_APPRAISAL_STATUS)
            .appraisalDate(UPDATED_APPRAISAL_DATE)
            .reviewDate(UPDATED_REVIEW_DATE);

        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceReview))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
        PerformanceReview testPerformanceReview = performanceReviewList.get(performanceReviewList.size() - 1);
        assertThat(testPerformanceReview.getAppraisalReviewId()).isEqualTo(DEFAULT_APPRAISAL_REVIEW_ID);
        assertThat(testPerformanceReview.getEmployeeRemark()).isEqualTo(UPDATED_EMPLOYEE_REMARK);
        assertThat(testPerformanceReview.getAppraiserRemark()).isEqualTo(DEFAULT_APPRAISER_REMARK);
        assertThat(testPerformanceReview.getReviewerRemark()).isEqualTo(UPDATED_REVIEWER_REMARK);
        assertThat(testPerformanceReview.getSelfScored()).isEqualTo(DEFAULT_SELF_SCORED);
        assertThat(testPerformanceReview.getScoredByAppraiser()).isEqualTo(DEFAULT_SCORED_BY_APPRAISER);
        assertThat(testPerformanceReview.getScoredByReviewer()).isEqualTo(DEFAULT_SCORED_BY_REVIEWER);
        assertThat(testPerformanceReview.getAppraisalStatus()).isEqualTo(UPDATED_APPRAISAL_STATUS);
        assertThat(testPerformanceReview.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testPerformanceReview.getAppraisalDate()).isEqualTo(UPDATED_APPRAISAL_DATE);
        assertThat(testPerformanceReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testPerformanceReview.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerformanceReview.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPerformanceReview.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPerformanceReview.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPerformanceReview.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testPerformanceReview.getTargetAchived()).isEqualTo(DEFAULT_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void fullUpdatePerformanceReviewWithPatch() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();

        // Update the performanceReview using partial update
        PerformanceReview partialUpdatedPerformanceReview = new PerformanceReview();
        partialUpdatedPerformanceReview.setId(performanceReview.getId());

        partialUpdatedPerformanceReview
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .employeeRemark(UPDATED_EMPLOYEE_REMARK)
            .appraiserRemark(UPDATED_APPRAISER_REMARK)
            .reviewerRemark(UPDATED_REVIEWER_REMARK)
            .selfScored(UPDATED_SELF_SCORED)
            .scoredByAppraiser(UPDATED_SCORED_BY_APPRAISER)
            .scoredByReviewer(UPDATED_SCORED_BY_REVIEWER)
            .appraisalStatus(UPDATED_APPRAISAL_STATUS)
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .appraisalDate(UPDATED_APPRAISAL_DATE)
            .reviewDate(UPDATED_REVIEW_DATE)
            .status(UPDATED_STATUS)
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .target(UPDATED_TARGET)
            .targetAchived(UPDATED_TARGET_ACHIVED);

        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceReview))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
        PerformanceReview testPerformanceReview = performanceReviewList.get(performanceReviewList.size() - 1);
        assertThat(testPerformanceReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testPerformanceReview.getEmployeeRemark()).isEqualTo(UPDATED_EMPLOYEE_REMARK);
        assertThat(testPerformanceReview.getAppraiserRemark()).isEqualTo(UPDATED_APPRAISER_REMARK);
        assertThat(testPerformanceReview.getReviewerRemark()).isEqualTo(UPDATED_REVIEWER_REMARK);
        assertThat(testPerformanceReview.getSelfScored()).isEqualTo(UPDATED_SELF_SCORED);
        assertThat(testPerformanceReview.getScoredByAppraiser()).isEqualTo(UPDATED_SCORED_BY_APPRAISER);
        assertThat(testPerformanceReview.getScoredByReviewer()).isEqualTo(UPDATED_SCORED_BY_REVIEWER);
        assertThat(testPerformanceReview.getAppraisalStatus()).isEqualTo(UPDATED_APPRAISAL_STATUS);
        assertThat(testPerformanceReview.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testPerformanceReview.getAppraisalDate()).isEqualTo(UPDATED_APPRAISAL_DATE);
        assertThat(testPerformanceReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testPerformanceReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerformanceReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPerformanceReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPerformanceReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPerformanceReview.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testPerformanceReview.getTargetAchived()).isEqualTo(UPDATED_TARGET_ACHIVED);
    }

    @Test
    @Transactional
    void patchNonExistingPerformanceReview() throws Exception {
        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();
        performanceReview.setId(count.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, performanceReviewDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerformanceReview() throws Exception {
        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();
        performanceReview.setId(count.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerformanceReview() throws Exception {
        int databaseSizeBeforeUpdate = performanceReviewRepository.findAll().size();
        performanceReview.setId(count.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceReviewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceReview in the database
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerformanceReview() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        int databaseSizeBeforeDelete = performanceReviewRepository.findAll().size();

        // Delete the performanceReview
        restPerformanceReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, performanceReview.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerformanceReview> performanceReviewList = performanceReviewRepository.findAll();
        assertThat(performanceReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
