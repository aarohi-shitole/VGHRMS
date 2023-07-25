package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.AppraisalEvaluation;
import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import com.techvg.hrms.repository.AppraisalEvaluationRepository;
import com.techvg.hrms.service.criteria.AppraisalEvaluationCriteria;
import com.techvg.hrms.service.dto.AppraisalEvaluationDTO;
import com.techvg.hrms.service.mapper.AppraisalEvaluationMapper;
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
 * Integration tests for the {@link AppraisalEvaluationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppraisalEvaluationResourceIT {

    private static final Boolean DEFAULT_ANSWER_FLAG = false;
    private static final Boolean UPDATED_ANSWER_FLAG = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_APPRAISAL_REVIEW_ID = 1L;
    private static final Long UPDATED_APPRAISAL_REVIEW_ID = 2L;
    private static final Long SMALLER_APPRAISAL_REVIEW_ID = 1L - 1L;

    private static final Long DEFAULT_AVAILABLE_POINTS = 1L;
    private static final Long UPDATED_AVAILABLE_POINTS = 2L;
    private static final Long SMALLER_AVAILABLE_POINTS = 1L - 1L;

    private static final Long DEFAULT_SCORED_POINTS = 1L;
    private static final Long UPDATED_SCORED_POINTS = 2L;
    private static final Long SMALLER_SCORED_POINTS = 1L - 1L;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/appraisal-evaluations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppraisalEvaluationRepository appraisalEvaluationRepository;

    @Autowired
    private AppraisalEvaluationMapper appraisalEvaluationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppraisalEvaluationMockMvc;

    private AppraisalEvaluation appraisalEvaluation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppraisalEvaluation createEntity(EntityManager em) {
        AppraisalEvaluation appraisalEvaluation = new AppraisalEvaluation()
            .answerFlag(DEFAULT_ANSWER_FLAG)
            .description(DEFAULT_DESCRIPTION)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .appraisalReviewId(DEFAULT_APPRAISAL_REVIEW_ID)
            .availablePoints(DEFAULT_AVAILABLE_POINTS)
            .scoredPoints(DEFAULT_SCORED_POINTS)
            .remark(DEFAULT_REMARK)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return appraisalEvaluation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppraisalEvaluation createUpdatedEntity(EntityManager em) {
        AppraisalEvaluation appraisalEvaluation = new AppraisalEvaluation()
            .answerFlag(UPDATED_ANSWER_FLAG)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .availablePoints(UPDATED_AVAILABLE_POINTS)
            .scoredPoints(UPDATED_SCORED_POINTS)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS);
          //  .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return appraisalEvaluation;
    }

    @BeforeEach
    public void initTest() {
        appraisalEvaluation = createEntity(em);
    }

    @Test
    @Transactional
    void createAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeCreate = appraisalEvaluationRepository.findAll().size();
        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);
        restAppraisalEvaluationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
           //         .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeCreate + 1);
        AppraisalEvaluation testAppraisalEvaluation = appraisalEvaluationList.get(appraisalEvaluationList.size() - 1);
        assertThat(testAppraisalEvaluation.getAnswerFlag()).isEqualTo(DEFAULT_ANSWER_FLAG);
        assertThat(testAppraisalEvaluation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppraisalEvaluation.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAppraisalEvaluation.getAppraisalReviewId()).isEqualTo(DEFAULT_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalEvaluation.getAvailablePoints()).isEqualTo(DEFAULT_AVAILABLE_POINTS);
        assertThat(testAppraisalEvaluation.getScoredPoints()).isEqualTo(DEFAULT_SCORED_POINTS);
        assertThat(testAppraisalEvaluation.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testAppraisalEvaluation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppraisalEvaluation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAppraisalEvaluation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAppraisalEvaluation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAppraisalEvaluationWithExistingId() throws Exception {
        // Create the AppraisalEvaluation with an existing ID
        appraisalEvaluation.setId(1L);
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        int databaseSizeBeforeCreate = appraisalEvaluationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppraisalEvaluationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
           //         .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluations() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList
        restAppraisalEvaluationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraisalEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerFlag").value(hasItem(DEFAULT_ANSWER_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].availablePoints").value(hasItem(DEFAULT_AVAILABLE_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].scoredPoints").value(hasItem(DEFAULT_SCORED_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAppraisalEvaluation() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get the appraisalEvaluation
        restAppraisalEvaluationMockMvc
            .perform(get(ENTITY_API_URL_ID, appraisalEvaluation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appraisalEvaluation.getId().intValue()))
            .andExpect(jsonPath("$.answerFlag").value(DEFAULT_ANSWER_FLAG.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.appraisalReviewId").value(DEFAULT_APPRAISAL_REVIEW_ID.intValue()))
            .andExpect(jsonPath("$.availablePoints").value(DEFAULT_AVAILABLE_POINTS.intValue()))
            .andExpect(jsonPath("$.scoredPoints").value(DEFAULT_SCORED_POINTS.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAppraisalEvaluationsByIdFiltering() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        Long id = appraisalEvaluation.getId();

        defaultAppraisalEvaluationShouldBeFound("id.equals=" + id);
        defaultAppraisalEvaluationShouldNotBeFound("id.notEquals=" + id);

        defaultAppraisalEvaluationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAppraisalEvaluationShouldNotBeFound("id.greaterThan=" + id);

        defaultAppraisalEvaluationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAppraisalEvaluationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAnswerFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where answerFlag equals to DEFAULT_ANSWER_FLAG
        defaultAppraisalEvaluationShouldBeFound("answerFlag.equals=" + DEFAULT_ANSWER_FLAG);

        // Get all the appraisalEvaluationList where answerFlag equals to UPDATED_ANSWER_FLAG
        defaultAppraisalEvaluationShouldNotBeFound("answerFlag.equals=" + UPDATED_ANSWER_FLAG);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAnswerFlagIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where answerFlag in DEFAULT_ANSWER_FLAG or UPDATED_ANSWER_FLAG
        defaultAppraisalEvaluationShouldBeFound("answerFlag.in=" + DEFAULT_ANSWER_FLAG + "," + UPDATED_ANSWER_FLAG);

        // Get all the appraisalEvaluationList where answerFlag equals to UPDATED_ANSWER_FLAG
        defaultAppraisalEvaluationShouldNotBeFound("answerFlag.in=" + UPDATED_ANSWER_FLAG);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAnswerFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where answerFlag is not null
        defaultAppraisalEvaluationShouldBeFound("answerFlag.specified=true");

        // Get all the appraisalEvaluationList where answerFlag is null
        defaultAppraisalEvaluationShouldNotBeFound("answerFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where description equals to DEFAULT_DESCRIPTION
        defaultAppraisalEvaluationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the appraisalEvaluationList where description equals to UPDATED_DESCRIPTION
        defaultAppraisalEvaluationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAppraisalEvaluationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the appraisalEvaluationList where description equals to UPDATED_DESCRIPTION
        defaultAppraisalEvaluationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where description is not null
        defaultAppraisalEvaluationShouldBeFound("description.specified=true");

        // Get all the appraisalEvaluationList where description is null
        defaultAppraisalEvaluationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where description contains DEFAULT_DESCRIPTION
        defaultAppraisalEvaluationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the appraisalEvaluationList where description contains UPDATED_DESCRIPTION
        defaultAppraisalEvaluationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where description does not contain DEFAULT_DESCRIPTION
        defaultAppraisalEvaluationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the appraisalEvaluationList where description does not contain UPDATED_DESCRIPTION
        defaultAppraisalEvaluationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the appraisalEvaluationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the appraisalEvaluationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId is not null
        defaultAppraisalEvaluationShouldBeFound("employeeId.specified=true");

        // Get all the appraisalEvaluationList where employeeId is null
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the appraisalEvaluationList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the appraisalEvaluationList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the appraisalEvaluationList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the appraisalEvaluationList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultAppraisalEvaluationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId equals to DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.equals=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalEvaluationList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.equals=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId in DEFAULT_APPRAISAL_REVIEW_ID or UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.in=" + DEFAULT_APPRAISAL_REVIEW_ID + "," + UPDATED_APPRAISAL_REVIEW_ID);

        // Get all the appraisalEvaluationList where appraisalReviewId equals to UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.in=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId is not null
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.specified=true");

        // Get all the appraisalEvaluationList where appraisalReviewId is null
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId is greater than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.greaterThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalEvaluationList where appraisalReviewId is greater than or equal to UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.greaterThanOrEqual=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId is less than or equal to DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.lessThanOrEqual=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalEvaluationList where appraisalReviewId is less than or equal to SMALLER_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.lessThanOrEqual=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId is less than DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.lessThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalEvaluationList where appraisalReviewId is less than UPDATED_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.lessThan=" + UPDATED_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalReviewIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where appraisalReviewId is greater than DEFAULT_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldNotBeFound("appraisalReviewId.greaterThan=" + DEFAULT_APPRAISAL_REVIEW_ID);

        // Get all the appraisalEvaluationList where appraisalReviewId is greater than SMALLER_APPRAISAL_REVIEW_ID
        defaultAppraisalEvaluationShouldBeFound("appraisalReviewId.greaterThan=" + SMALLER_APPRAISAL_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints equals to DEFAULT_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldBeFound("availablePoints.equals=" + DEFAULT_AVAILABLE_POINTS);

        // Get all the appraisalEvaluationList where availablePoints equals to UPDATED_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.equals=" + UPDATED_AVAILABLE_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints in DEFAULT_AVAILABLE_POINTS or UPDATED_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldBeFound("availablePoints.in=" + DEFAULT_AVAILABLE_POINTS + "," + UPDATED_AVAILABLE_POINTS);

        // Get all the appraisalEvaluationList where availablePoints equals to UPDATED_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.in=" + UPDATED_AVAILABLE_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints is not null
        defaultAppraisalEvaluationShouldBeFound("availablePoints.specified=true");

        // Get all the appraisalEvaluationList where availablePoints is null
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints is greater than or equal to DEFAULT_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldBeFound("availablePoints.greaterThanOrEqual=" + DEFAULT_AVAILABLE_POINTS);

        // Get all the appraisalEvaluationList where availablePoints is greater than or equal to UPDATED_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.greaterThanOrEqual=" + UPDATED_AVAILABLE_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints is less than or equal to DEFAULT_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldBeFound("availablePoints.lessThanOrEqual=" + DEFAULT_AVAILABLE_POINTS);

        // Get all the appraisalEvaluationList where availablePoints is less than or equal to SMALLER_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.lessThanOrEqual=" + SMALLER_AVAILABLE_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints is less than DEFAULT_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.lessThan=" + DEFAULT_AVAILABLE_POINTS);

        // Get all the appraisalEvaluationList where availablePoints is less than UPDATED_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldBeFound("availablePoints.lessThan=" + UPDATED_AVAILABLE_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAvailablePointsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where availablePoints is greater than DEFAULT_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("availablePoints.greaterThan=" + DEFAULT_AVAILABLE_POINTS);

        // Get all the appraisalEvaluationList where availablePoints is greater than SMALLER_AVAILABLE_POINTS
        defaultAppraisalEvaluationShouldBeFound("availablePoints.greaterThan=" + SMALLER_AVAILABLE_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints equals to DEFAULT_SCORED_POINTS
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.equals=" + DEFAULT_SCORED_POINTS);

        // Get all the appraisalEvaluationList where scoredPoints equals to UPDATED_SCORED_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.equals=" + UPDATED_SCORED_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints in DEFAULT_SCORED_POINTS or UPDATED_SCORED_POINTS
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.in=" + DEFAULT_SCORED_POINTS + "," + UPDATED_SCORED_POINTS);

        // Get all the appraisalEvaluationList where scoredPoints equals to UPDATED_SCORED_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.in=" + UPDATED_SCORED_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints is not null
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.specified=true");

        // Get all the appraisalEvaluationList where scoredPoints is null
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints is greater than or equal to DEFAULT_SCORED_POINTS
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.greaterThanOrEqual=" + DEFAULT_SCORED_POINTS);

        // Get all the appraisalEvaluationList where scoredPoints is greater than or equal to UPDATED_SCORED_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.greaterThanOrEqual=" + UPDATED_SCORED_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints is less than or equal to DEFAULT_SCORED_POINTS
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.lessThanOrEqual=" + DEFAULT_SCORED_POINTS);

        // Get all the appraisalEvaluationList where scoredPoints is less than or equal to SMALLER_SCORED_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.lessThanOrEqual=" + SMALLER_SCORED_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints is less than DEFAULT_SCORED_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.lessThan=" + DEFAULT_SCORED_POINTS);

        // Get all the appraisalEvaluationList where scoredPoints is less than UPDATED_SCORED_POINTS
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.lessThan=" + UPDATED_SCORED_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByScoredPointsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where scoredPoints is greater than DEFAULT_SCORED_POINTS
        defaultAppraisalEvaluationShouldNotBeFound("scoredPoints.greaterThan=" + DEFAULT_SCORED_POINTS);

        // Get all the appraisalEvaluationList where scoredPoints is greater than SMALLER_SCORED_POINTS
        defaultAppraisalEvaluationShouldBeFound("scoredPoints.greaterThan=" + SMALLER_SCORED_POINTS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where remark equals to DEFAULT_REMARK
        defaultAppraisalEvaluationShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the appraisalEvaluationList where remark equals to UPDATED_REMARK
        defaultAppraisalEvaluationShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultAppraisalEvaluationShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the appraisalEvaluationList where remark equals to UPDATED_REMARK
        defaultAppraisalEvaluationShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where remark is not null
        defaultAppraisalEvaluationShouldBeFound("remark.specified=true");

        // Get all the appraisalEvaluationList where remark is null
        defaultAppraisalEvaluationShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where remark contains DEFAULT_REMARK
        defaultAppraisalEvaluationShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the appraisalEvaluationList where remark contains UPDATED_REMARK
        defaultAppraisalEvaluationShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where remark does not contain DEFAULT_REMARK
        defaultAppraisalEvaluationShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the appraisalEvaluationList where remark does not contain UPDATED_REMARK
        defaultAppraisalEvaluationShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where status equals to DEFAULT_STATUS
        defaultAppraisalEvaluationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the appraisalEvaluationList where status equals to UPDATED_STATUS
        defaultAppraisalEvaluationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAppraisalEvaluationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the appraisalEvaluationList where status equals to UPDATED_STATUS
        defaultAppraisalEvaluationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where status is not null
        defaultAppraisalEvaluationShouldBeFound("status.specified=true");

        // Get all the appraisalEvaluationList where status is null
        defaultAppraisalEvaluationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where status contains DEFAULT_STATUS
        defaultAppraisalEvaluationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the appraisalEvaluationList where status contains UPDATED_STATUS
        defaultAppraisalEvaluationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where status does not contain DEFAULT_STATUS
        defaultAppraisalEvaluationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the appraisalEvaluationList where status does not contain UPDATED_STATUS
        defaultAppraisalEvaluationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId equals to DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationList where companyId equals to UPDATED_COMPANY_ID
        defaultAppraisalEvaluationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultAppraisalEvaluationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the appraisalEvaluationList where companyId equals to UPDATED_COMPANY_ID
        defaultAppraisalEvaluationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId is not null
        defaultAppraisalEvaluationShouldBeFound("companyId.specified=true");

        // Get all the appraisalEvaluationList where companyId is null
        defaultAppraisalEvaluationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultAppraisalEvaluationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultAppraisalEvaluationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId is less than DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationList where companyId is less than UPDATED_COMPANY_ID
        defaultAppraisalEvaluationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationList where companyId is greater than SMALLER_COMPANY_ID
        defaultAppraisalEvaluationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAppraisalEvaluationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the appraisalEvaluationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAppraisalEvaluationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAppraisalEvaluationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the appraisalEvaluationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAppraisalEvaluationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModified is not null
        defaultAppraisalEvaluationShouldBeFound("lastModified.specified=true");

        // Get all the appraisalEvaluationList where lastModified is null
        defaultAppraisalEvaluationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModifiedBy is not null
        defaultAppraisalEvaluationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the appraisalEvaluationList where lastModifiedBy is null
        defaultAppraisalEvaluationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        // Get all the appraisalEvaluationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationsByAppraisalEvaluationParameterIsEqualToSomething() throws Exception {
        AppraisalEvaluationParameter appraisalEvaluationParameter;
//        if (TestUtil.findAll(em, AppraisalEvaluationParameter.class).isEmpty()) {
//            appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);
//            appraisalEvaluationParameter = AppraisalEvaluationParameterResourceIT.createEntity(em);
//        } else {
//            appraisalEvaluationParameter = TestUtil.findAll(em, AppraisalEvaluationParameter.class).get(0);
//        }
//        em.persist(appraisalEvaluationParameter);
//        em.flush();
//        appraisalEvaluation.setAppraisalEvaluationParameter(appraisalEvaluationParameter);
//        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);
//        Long appraisalEvaluationParameterId = appraisalEvaluationParameter.getId();
//
//        // Get all the appraisalEvaluationList where appraisalEvaluationParameter equals to appraisalEvaluationParameterId
//        defaultAppraisalEvaluationShouldBeFound("appraisalEvaluationParameterId.equals=" + appraisalEvaluationParameterId);
//
//        // Get all the appraisalEvaluationList where appraisalEvaluationParameter equals to (appraisalEvaluationParameterId + 1)
//        defaultAppraisalEvaluationShouldNotBeFound("appraisalEvaluationParameterId.equals=" + (appraisalEvaluationParameterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppraisalEvaluationShouldBeFound(String filter) throws Exception {
        restAppraisalEvaluationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraisalEvaluation.getId().intValue())))
            .andExpect(jsonPath("$.[*].answerFlag").value(hasItem(DEFAULT_ANSWER_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].appraisalReviewId").value(hasItem(DEFAULT_APPRAISAL_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].availablePoints").value(hasItem(DEFAULT_AVAILABLE_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].scoredPoints").value(hasItem(DEFAULT_SCORED_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAppraisalEvaluationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppraisalEvaluationShouldNotBeFound(String filter) throws Exception {
        restAppraisalEvaluationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppraisalEvaluationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAppraisalEvaluation() throws Exception {
        // Get the appraisalEvaluation
        restAppraisalEvaluationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppraisalEvaluation() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();

        // Update the appraisalEvaluation
        AppraisalEvaluation updatedAppraisalEvaluation = appraisalEvaluationRepository.findById(appraisalEvaluation.getId()).get();
        // Disconnect from session so that the updates on updatedAppraisalEvaluation are not directly saved in db
        em.detach(updatedAppraisalEvaluation);
        updatedAppraisalEvaluation
            .answerFlag(UPDATED_ANSWER_FLAG)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .availablePoints(UPDATED_AVAILABLE_POINTS)
            .scoredPoints(UPDATED_SCORED_POINTS)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(updatedAppraisalEvaluation);

        restAppraisalEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appraisalEvaluationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
         //           .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
        AppraisalEvaluation testAppraisalEvaluation = appraisalEvaluationList.get(appraisalEvaluationList.size() - 1);
        assertThat(testAppraisalEvaluation.getAnswerFlag()).isEqualTo(UPDATED_ANSWER_FLAG);
        assertThat(testAppraisalEvaluation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppraisalEvaluation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAppraisalEvaluation.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalEvaluation.getAvailablePoints()).isEqualTo(UPDATED_AVAILABLE_POINTS);
        assertThat(testAppraisalEvaluation.getScoredPoints()).isEqualTo(UPDATED_SCORED_POINTS);
        assertThat(testAppraisalEvaluation.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testAppraisalEvaluation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalEvaluation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalEvaluation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalEvaluation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();
        appraisalEvaluation.setId(count.incrementAndGet());

        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppraisalEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appraisalEvaluationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
           //         .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();
        appraisalEvaluation.setId(count.incrementAndGet());

        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
               //     .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();
        appraisalEvaluation.setId(count.incrementAndGet());

        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
              //      .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppraisalEvaluationWithPatch() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();

        // Update the appraisalEvaluation using partial update
        AppraisalEvaluation partialUpdatedAppraisalEvaluation = new AppraisalEvaluation();
        partialUpdatedAppraisalEvaluation.setId(appraisalEvaluation.getId());

        partialUpdatedAppraisalEvaluation
            .description(UPDATED_DESCRIPTION)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS);
      //      .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAppraisalEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppraisalEvaluation.getId())
                    .contentType("application/merge-patch+json")
           //         .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppraisalEvaluation))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
        AppraisalEvaluation testAppraisalEvaluation = appraisalEvaluationList.get(appraisalEvaluationList.size() - 1);
        assertThat(testAppraisalEvaluation.getAnswerFlag()).isEqualTo(DEFAULT_ANSWER_FLAG);
        assertThat(testAppraisalEvaluation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppraisalEvaluation.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAppraisalEvaluation.getAppraisalReviewId()).isEqualTo(DEFAULT_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalEvaluation.getAvailablePoints()).isEqualTo(DEFAULT_AVAILABLE_POINTS);
        assertThat(testAppraisalEvaluation.getScoredPoints()).isEqualTo(DEFAULT_SCORED_POINTS);
        assertThat(testAppraisalEvaluation.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testAppraisalEvaluation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalEvaluation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAppraisalEvaluation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAppraisalEvaluation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAppraisalEvaluationWithPatch() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();

        // Update the appraisalEvaluation using partial update
        AppraisalEvaluation partialUpdatedAppraisalEvaluation = new AppraisalEvaluation();
        partialUpdatedAppraisalEvaluation.setId(appraisalEvaluation.getId());

        partialUpdatedAppraisalEvaluation
            .answerFlag(UPDATED_ANSWER_FLAG)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .appraisalReviewId(UPDATED_APPRAISAL_REVIEW_ID)
            .availablePoints(UPDATED_AVAILABLE_POINTS)
            .scoredPoints(UPDATED_SCORED_POINTS)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAppraisalEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppraisalEvaluation.getId())
                    .contentType("application/merge-patch+json")
              //      .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppraisalEvaluation))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
        AppraisalEvaluation testAppraisalEvaluation = appraisalEvaluationList.get(appraisalEvaluationList.size() - 1);
        assertThat(testAppraisalEvaluation.getAnswerFlag()).isEqualTo(UPDATED_ANSWER_FLAG);
        assertThat(testAppraisalEvaluation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppraisalEvaluation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAppraisalEvaluation.getAppraisalReviewId()).isEqualTo(UPDATED_APPRAISAL_REVIEW_ID);
        assertThat(testAppraisalEvaluation.getAvailablePoints()).isEqualTo(UPDATED_AVAILABLE_POINTS);
        assertThat(testAppraisalEvaluation.getScoredPoints()).isEqualTo(UPDATED_SCORED_POINTS);
        assertThat(testAppraisalEvaluation.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testAppraisalEvaluation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalEvaluation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalEvaluation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalEvaluation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();
        appraisalEvaluation.setId(count.incrementAndGet());

        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppraisalEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appraisalEvaluationDTO.getId())
                    .contentType("application/merge-patch+json")
           //         .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();
        appraisalEvaluation.setId(count.incrementAndGet());

        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
           //         .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppraisalEvaluation() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationRepository.findAll().size();
        appraisalEvaluation.setId(count.incrementAndGet());

        // Create the AppraisalEvaluation
        AppraisalEvaluationDTO appraisalEvaluationDTO = appraisalEvaluationMapper.toDto(appraisalEvaluation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
            //        .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppraisalEvaluation in the database
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppraisalEvaluation() throws Exception {
        // Initialize the database
        appraisalEvaluationRepository.saveAndFlush(appraisalEvaluation);

        int databaseSizeBeforeDelete = appraisalEvaluationRepository.findAll().size();

        // Delete the appraisalEvaluation
        restAppraisalEvaluationMockMvc
            .perform(delete(ENTITY_API_URL_ID, appraisalEvaluation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppraisalEvaluation> appraisalEvaluationList = appraisalEvaluationRepository.findAll();
        assertThat(appraisalEvaluationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
