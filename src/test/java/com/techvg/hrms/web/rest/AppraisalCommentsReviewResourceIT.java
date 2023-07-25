package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.AppraisalCommentsReview;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.repository.AppraisalCommentsReviewRepository;
import com.techvg.hrms.service.criteria.AppraisalCommentsReviewCriteria;
import com.techvg.hrms.service.dto.AppraisalCommentsReviewDTO;
import com.techvg.hrms.service.mapper.AppraisalCommentsReviewMapper;
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
 * Integration tests for the {@link AppraisalCommentsReviewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppraisalCommentsReviewResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_APPRAISAL_REVIEW_ID = 1L;
    private static final Long UPDATED_APPRAISAL_REVIEW_ID = 2L;
    private static final Long SMALLER_APPRAISAL_REVIEW_ID = 1L - 1L;

    private static final String DEFAULT_REF_FORM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REF_FORM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/appraisal-comments-reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppraisalCommentsReviewRepository appraisalCommentsReviewRepository;

    @Autowired
    private AppraisalCommentsReviewMapper appraisalCommentsReviewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppraisalCommentsReviewMockMvc;

    private AppraisalCommentsReview appraisalCommentsReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppraisalCommentsReview createEntity(EntityManager em) {
        AppraisalCommentsReview appraisalCommentsReview = new AppraisalCommentsReview()
            .comment(DEFAULT_COMMENT)
            .commentType(DEFAULT_COMMENT_TYPE)
            .appraisalReviewId(DEFAULT_APPRAISAL_REVIEW_ID)
            .refFormName(DEFAULT_REF_FORM_NAME)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return appraisalCommentsReview;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppraisalCommentsReview createUpdatedEntity(EntityManager em) {
        AppraisalCommentsReview appraisalCommentsReview = new AppraisalCommentsReview()
            .comment(UPDATED_COMMENT)
            .commentType(UPDATED_COMMENT_TYPE)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .refFormName(UPDATED_REF_FORM_NAME)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return appraisalCommentsReview;
    }

    @BeforeEach
    public void initTest() {
        appraisalCommentsReview = createEntity(em);
    }

    @Test
    @Transactional
    void createAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeCreate = appraisalCommentsReviewRepository.findAll().size();
        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);
        restAppraisalCommentsReviewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
               //     .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeCreate + 1);
        AppraisalCommentsReview testAppraisalCommentsReview = appraisalCommentsReviewList.get(appraisalCommentsReviewList.size() - 1);
        assertThat(testAppraisalCommentsReview.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testAppraisalCommentsReview.getCommentType()).isEqualTo(DEFAULT_COMMENT_TYPE);
        assertThat(testAppraisalCommentsReview.getAppraisalReviewId()).isEqualTo(DEFAULT_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalCommentsReview.getRefFormName()).isEqualTo(DEFAULT_REF_FORM_NAME);
        assertThat(testAppraisalCommentsReview.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppraisalCommentsReview.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAppraisalCommentsReview.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAppraisalCommentsReview.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAppraisalCommentsReviewWithExistingId() throws Exception {
        // Create the AppraisalCommentsReview with an existing ID
        appraisalCommentsReview.setId(1L);
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        int databaseSizeBeforeCreate = appraisalCommentsReviewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppraisalCommentsReviewMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
             //       .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviews() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList
        restAppraisalCommentsReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraisalCommentsReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].commentType").value(hasItem(DEFAULT_COMMENT_TYPE)))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].refFormName").value(hasItem(DEFAULT_REF_FORM_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAppraisalCommentsReview() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get the appraisalCommentsReview
        restAppraisalCommentsReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, appraisalCommentsReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appraisalCommentsReview.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.commentType").value(DEFAULT_COMMENT_TYPE))
            .andExpect(jsonPath("$.appraisalReviewId").value(DEFAULT_APPRAISAL_REVIEW_ID.intValue()))
            .andExpect(jsonPath("$.refFormName").value(DEFAULT_REF_FORM_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAppraisalCommentsReviewsByIdFiltering() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        Long id = appraisalCommentsReview.getId();

        defaultAppraisalCommentsReviewShouldBeFound("id.equals=" + id);
        defaultAppraisalCommentsReviewShouldNotBeFound("id.notEquals=" + id);

        defaultAppraisalCommentsReviewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAppraisalCommentsReviewShouldNotBeFound("id.greaterThan=" + id);

        defaultAppraisalCommentsReviewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAppraisalCommentsReviewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where comment equals to DEFAULT_COMMENT
        defaultAppraisalCommentsReviewShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the appraisalCommentsReviewList where comment equals to UPDATED_COMMENT
        defaultAppraisalCommentsReviewShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultAppraisalCommentsReviewShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the appraisalCommentsReviewList where comment equals to UPDATED_COMMENT
        defaultAppraisalCommentsReviewShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where comment is not null
        defaultAppraisalCommentsReviewShouldBeFound("comment.specified=true");

        // Get all the appraisalCommentsReviewList where comment is null
        defaultAppraisalCommentsReviewShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where comment contains DEFAULT_COMMENT
        defaultAppraisalCommentsReviewShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the appraisalCommentsReviewList where comment contains UPDATED_COMMENT
        defaultAppraisalCommentsReviewShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where comment does not contain DEFAULT_COMMENT
        defaultAppraisalCommentsReviewShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the appraisalCommentsReviewList where comment does not contain UPDATED_COMMENT
        defaultAppraisalCommentsReviewShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where commentType equals to DEFAULT_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldBeFound("commentType.equals=" + DEFAULT_COMMENT_TYPE);

        // Get all the appraisalCommentsReviewList where commentType equals to UPDATED_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldNotBeFound("commentType.equals=" + UPDATED_COMMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where commentType in DEFAULT_COMMENT_TYPE or UPDATED_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldBeFound("commentType.in=" + DEFAULT_COMMENT_TYPE + "," + UPDATED_COMMENT_TYPE);

        // Get all the appraisalCommentsReviewList where commentType equals to UPDATED_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldNotBeFound("commentType.in=" + UPDATED_COMMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where commentType is not null
        defaultAppraisalCommentsReviewShouldBeFound("commentType.specified=true");

        // Get all the appraisalCommentsReviewList where commentType is null
        defaultAppraisalCommentsReviewShouldNotBeFound("commentType.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentTypeContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where commentType contains DEFAULT_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldBeFound("commentType.contains=" + DEFAULT_COMMENT_TYPE);

        // Get all the appraisalCommentsReviewList where commentType contains UPDATED_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldNotBeFound("commentType.contains=" + UPDATED_COMMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCommentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where commentType does not contain DEFAULT_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldNotBeFound("commentType.doesNotContain=" + DEFAULT_COMMENT_TYPE);

        // Get all the appraisalCommentsReviewList where commentType does not contain UPDATED_COMMENT_TYPE
        defaultAppraisalCommentsReviewShouldBeFound("commentType.doesNotContain=" + UPDATED_COMMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId equals to DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldBeFound("appraisalReviewId.equals=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalCommentsReviewList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.equals=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId in DEFAULT_APPRAISAL_REVIEW_ID or UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldBeFound(
            "appraisalReviewId.in=" + DEFAULT_APPRAISAL_REVIEW_ID + "," + UPDATED_APPRAISAL_REVIEW_ID
        );

        // Get all the appraisalCommentsReviewList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.in=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is not null
        defaultAppraisalCommentsReviewShouldBeFound("appraisalReviewId.specified=true");

        // Get all the appraisalCommentsReviewList where appraisalReviewId is null
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is greater than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldBeFound("appraisalReviewId.greaterThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is greater than or equal to UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.greaterThanOrEqual=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is less than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldBeFound("appraisalReviewId.lessThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is less than or equal to SMALLER_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.lessThanOrEqual=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is less than DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.lessThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is less than UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldBeFound("appraisalReviewId.lessThan=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByAppraisalReviewIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is greater than DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("appraisalReviewId.greaterThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalCommentsReviewList where appraisalReviewId is greater than SMALLER_APPRAISAL_REVIEW_ID
        defaultAppraisalCommentsReviewShouldBeFound("appraisalReviewId.greaterThan=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByRefFormNameIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where refFormName equals to DEFAULT_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldBeFound("refFormName.equals=" + DEFAULT_REF_FORM_NAME);

        // Get all the appraisalCommentsReviewList where refFormName equals to UPDATED_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldNotBeFound("refFormName.equals=" + UPDATED_REF_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByRefFormNameIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where refFormName in DEFAULT_REF_FORM_NAME or UPDATED_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldBeFound("refFormName.in=" + DEFAULT_REF_FORM_NAME + "," + UPDATED_REF_FORM_NAME);

        // Get all the appraisalCommentsReviewList where refFormName equals to UPDATED_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldNotBeFound("refFormName.in=" + UPDATED_REF_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByRefFormNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where refFormName is not null
        defaultAppraisalCommentsReviewShouldBeFound("refFormName.specified=true");

        // Get all the appraisalCommentsReviewList where refFormName is null
        defaultAppraisalCommentsReviewShouldNotBeFound("refFormName.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByRefFormNameContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where refFormName contains DEFAULT_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldBeFound("refFormName.contains=" + DEFAULT_REF_FORM_NAME);

        // Get all the appraisalCommentsReviewList where refFormName contains UPDATED_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldNotBeFound("refFormName.contains=" + UPDATED_REF_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByRefFormNameNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where refFormName does not contain DEFAULT_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldNotBeFound("refFormName.doesNotContain=" + DEFAULT_REF_FORM_NAME);

        // Get all the appraisalCommentsReviewList where refFormName does not contain UPDATED_REF_FORM_NAME
        defaultAppraisalCommentsReviewShouldBeFound("refFormName.doesNotContain=" + UPDATED_REF_FORM_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where status equals to DEFAULT_STATUS
        defaultAppraisalCommentsReviewShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the appraisalCommentsReviewList where status equals to UPDATED_STATUS
        defaultAppraisalCommentsReviewShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAppraisalCommentsReviewShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the appraisalCommentsReviewList where status equals to UPDATED_STATUS
        defaultAppraisalCommentsReviewShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where status is not null
        defaultAppraisalCommentsReviewShouldBeFound("status.specified=true");

        // Get all the appraisalCommentsReviewList where status is null
        defaultAppraisalCommentsReviewShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByStatusContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where status contains DEFAULT_STATUS
        defaultAppraisalCommentsReviewShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the appraisalCommentsReviewList where status contains UPDATED_STATUS
        defaultAppraisalCommentsReviewShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where status does not contain DEFAULT_STATUS
        defaultAppraisalCommentsReviewShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the appraisalCommentsReviewList where status does not contain UPDATED_STATUS
        defaultAppraisalCommentsReviewShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId equals to DEFAULT_COMPANY_ID
        defaultAppraisalCommentsReviewShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalCommentsReviewList where companyId equals to UPDATED_COMPANY_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultAppraisalCommentsReviewShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the appraisalCommentsReviewList where companyId equals to UPDATED_COMPANY_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId is not null
        defaultAppraisalCommentsReviewShouldBeFound("companyId.specified=true");

        // Get all the appraisalCommentsReviewList where companyId is null
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultAppraisalCommentsReviewShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalCommentsReviewList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultAppraisalCommentsReviewShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalCommentsReviewList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId is less than DEFAULT_COMPANY_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalCommentsReviewList where companyId is less than UPDATED_COMPANY_ID
        defaultAppraisalCommentsReviewShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where companyId is greater than DEFAULT_COMPANY_ID
        defaultAppraisalCommentsReviewShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalCommentsReviewList where companyId is greater than SMALLER_COMPANY_ID
        defaultAppraisalCommentsReviewShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAppraisalCommentsReviewShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the appraisalCommentsReviewList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAppraisalCommentsReviewShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the appraisalCommentsReviewList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModified is not null
        defaultAppraisalCommentsReviewShouldBeFound("lastModified.specified=true");

        // Get all the appraisalCommentsReviewList where lastModified is null
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalCommentsReviewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the appraisalCommentsReviewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModifiedBy is not null
        defaultAppraisalCommentsReviewShouldBeFound("lastModifiedBy.specified=true");

        // Get all the appraisalCommentsReviewList where lastModifiedBy is null
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalCommentsReviewList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        // Get all the appraisalCommentsReviewList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalCommentsReviewList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAppraisalCommentsReviewShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalCommentsReviewsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
//      //  if (TestUtil.findAll(em, Employee.class).isEmpty()) {
//            appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);
//         //   employee = EmployeeResourceIT.createEntity(em);
//        } else {
//            employee = TestUtil.findAll(em, Employee.class).get(0);
//        }
//        em.persist(employee);
//        em.flush();
//        appraisalCommentsReview.setEmployee(employee);
//        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);
//        Long employeeId = employee.getId();

        // Get all the appraisalCommentsReviewList where employee equals to employeeId
       // defaultAppraisalCommentsReviewShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the appraisalCommentsReviewList where employee equals to (employeeId + 1)
    //    defaultAppraisalCommentsReviewShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppraisalCommentsReviewShouldBeFound(String filter) throws Exception {
        restAppraisalCommentsReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraisalCommentsReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].commentType").value(hasItem(DEFAULT_COMMENT_TYPE)))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].refFormName").value(hasItem(DEFAULT_REF_FORM_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAppraisalCommentsReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppraisalCommentsReviewShouldNotBeFound(String filter) throws Exception {
        restAppraisalCommentsReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppraisalCommentsReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAppraisalCommentsReview() throws Exception {
        // Get the appraisalCommentsReview
        restAppraisalCommentsReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppraisalCommentsReview() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();

        // Update the appraisalCommentsReview
        AppraisalCommentsReview updatedAppraisalCommentsReview = appraisalCommentsReviewRepository
            .findById(appraisalCommentsReview.getId())
            .get();
        // Disconnect from session so that the updates on updatedAppraisalCommentsReview are not directly saved in db
        em.detach(updatedAppraisalCommentsReview);
        updatedAppraisalCommentsReview
            .comment(UPDATED_COMMENT)
            .commentType(UPDATED_COMMENT_TYPE)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .refFormName(UPDATED_REF_FORM_NAME)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(updatedAppraisalCommentsReview);

        restAppraisalCommentsReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appraisalCommentsReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
      //              .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
        AppraisalCommentsReview testAppraisalCommentsReview = appraisalCommentsReviewList.get(appraisalCommentsReviewList.size() - 1);
        assertThat(testAppraisalCommentsReview.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testAppraisalCommentsReview.getCommentType()).isEqualTo(UPDATED_COMMENT_TYPE);
        assertThat(testAppraisalCommentsReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalCommentsReview.getRefFormName()).isEqualTo(UPDATED_REF_FORM_NAME);
        assertThat(testAppraisalCommentsReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalCommentsReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalCommentsReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalCommentsReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();
        appraisalCommentsReview.setId(count.incrementAndGet());

        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppraisalCommentsReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appraisalCommentsReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
         //           .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();
        appraisalCommentsReview.setId(count.incrementAndGet());

        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalCommentsReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
       //             .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();
        appraisalCommentsReview.setId(count.incrementAndGet());

        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalCommentsReviewMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
            //        .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppraisalCommentsReviewWithPatch() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();

        // Update the appraisalCommentsReview using partial update
        AppraisalCommentsReview partialUpdatedAppraisalCommentsReview = new AppraisalCommentsReview();
        partialUpdatedAppraisalCommentsReview.setId(appraisalCommentsReview.getId());

        partialUpdatedAppraisalCommentsReview
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAppraisalCommentsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppraisalCommentsReview.getId())
                    .contentType("application/merge-patch+json")
        //            .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppraisalCommentsReview))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
        AppraisalCommentsReview testAppraisalCommentsReview = appraisalCommentsReviewList.get(appraisalCommentsReviewList.size() - 1);
        assertThat(testAppraisalCommentsReview.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testAppraisalCommentsReview.getCommentType()).isEqualTo(DEFAULT_COMMENT_TYPE);
        assertThat(testAppraisalCommentsReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalCommentsReview.getRefFormName()).isEqualTo(DEFAULT_REF_FORM_NAME);
        assertThat(testAppraisalCommentsReview.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppraisalCommentsReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalCommentsReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalCommentsReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAppraisalCommentsReviewWithPatch() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();

        // Update the appraisalCommentsReview using partial update
        AppraisalCommentsReview partialUpdatedAppraisalCommentsReview = new AppraisalCommentsReview();
        partialUpdatedAppraisalCommentsReview.setId(appraisalCommentsReview.getId());

        partialUpdatedAppraisalCommentsReview
            .comment(UPDATED_COMMENT)
            .commentType(UPDATED_COMMENT_TYPE)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .refFormName(UPDATED_REF_FORM_NAME)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAppraisalCommentsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppraisalCommentsReview.getId())
                    .contentType("application/merge-patch+json")
    //                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppraisalCommentsReview))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
        AppraisalCommentsReview testAppraisalCommentsReview = appraisalCommentsReviewList.get(appraisalCommentsReviewList.size() - 1);
        assertThat(testAppraisalCommentsReview.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testAppraisalCommentsReview.getCommentType()).isEqualTo(UPDATED_COMMENT_TYPE);
        assertThat(testAppraisalCommentsReview.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalCommentsReview.getRefFormName()).isEqualTo(UPDATED_REF_FORM_NAME);
        assertThat(testAppraisalCommentsReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalCommentsReview.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalCommentsReview.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalCommentsReview.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();
        appraisalCommentsReview.setId(count.incrementAndGet());

        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppraisalCommentsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appraisalCommentsReviewDTO.getId())
                    .contentType("application/merge-patch+json")
          //          .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();
        appraisalCommentsReview.setId(count.incrementAndGet());

        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalCommentsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
            //        .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppraisalCommentsReview() throws Exception {
        int databaseSizeBeforeUpdate = appraisalCommentsReviewRepository.findAll().size();
        appraisalCommentsReview.setId(count.incrementAndGet());

        // Create the AppraisalCommentsReview
        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalCommentsReviewMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
        //            .content(TestUtil.convertObjectToJsonBytes(appraisalCommentsReviewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppraisalCommentsReview in the database
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppraisalCommentsReview() throws Exception {
        // Initialize the database
        appraisalCommentsReviewRepository.saveAndFlush(appraisalCommentsReview);

        int databaseSizeBeforeDelete = appraisalCommentsReviewRepository.findAll().size();

        // Delete the appraisalCommentsReview
        restAppraisalCommentsReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, appraisalCommentsReview.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppraisalCommentsReview> appraisalCommentsReviewList = appraisalCommentsReviewRepository.findAll();
        assertThat(appraisalCommentsReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
