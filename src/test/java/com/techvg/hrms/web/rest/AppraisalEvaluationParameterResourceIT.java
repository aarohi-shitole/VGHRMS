package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import com.techvg.hrms.repository.AppraisalEvaluationParameterRepository;
import com.techvg.hrms.service.criteria.AppraisalEvaluationParameterCriteria;
import com.techvg.hrms.service.dto.AppraisalEvaluationParameterDTO;
import com.techvg.hrms.service.mapper.AppraisalEvaluationParameterMapper;
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
 * Integration tests for the {@link AppraisalEvaluationParameterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppraisalEvaluationParameterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/appraisal-evaluation-parameters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository;

    @Autowired
    private AppraisalEvaluationParameterMapper appraisalEvaluationParameterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppraisalEvaluationParameterMockMvc;

    private AppraisalEvaluationParameter appraisalEvaluationParameter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppraisalEvaluationParameter createEntity(EntityManager em) {
        AppraisalEvaluationParameter appraisalEvaluationParameter = new AppraisalEvaluationParameter()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
          //  .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS);
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return appraisalEvaluationParameter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppraisalEvaluationParameter createUpdatedEntity(EntityManager em) {
        AppraisalEvaluationParameter appraisalEvaluationParameter = new AppraisalEvaluationParameter()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
       //     .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return appraisalEvaluationParameter;
    }

    @BeforeEach
    public void initTest() {
        appraisalEvaluationParameter = createEntity(em);
    }

    @Test
    @Transactional
    void createAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeCreate = appraisalEvaluationParameterRepository.findAll().size();
        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );
        restAppraisalEvaluationParameterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                //    .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeCreate + 1);
        AppraisalEvaluationParameter testAppraisalEvaluationParameter = appraisalEvaluationParameterList.get(
            appraisalEvaluationParameterList.size() - 1
        );
        assertThat(testAppraisalEvaluationParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppraisalEvaluationParameter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppraisalEvaluationParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppraisalEvaluationParameter.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAppraisalEvaluationParameter.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppraisalEvaluationParameter.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAppraisalEvaluationParameter.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAppraisalEvaluationParameterWithExistingId() throws Exception {
        // Create the AppraisalEvaluationParameter with an existing ID
        appraisalEvaluationParameter.setId(1L);
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        int databaseSizeBeforeCreate = appraisalEvaluationParameterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppraisalEvaluationParameterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
             //       .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParameters() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList
        restAppraisalEvaluationParameterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraisalEvaluationParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAppraisalEvaluationParameter() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get the appraisalEvaluationParameter
        restAppraisalEvaluationParameterMockMvc
            .perform(get(ENTITY_API_URL_ID, appraisalEvaluationParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appraisalEvaluationParameter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAppraisalEvaluationParametersByIdFiltering() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        Long id = appraisalEvaluationParameter.getId();

        defaultAppraisalEvaluationParameterShouldBeFound("id.equals=" + id);
        defaultAppraisalEvaluationParameterShouldNotBeFound("id.notEquals=" + id);

        defaultAppraisalEvaluationParameterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAppraisalEvaluationParameterShouldNotBeFound("id.greaterThan=" + id);

        defaultAppraisalEvaluationParameterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAppraisalEvaluationParameterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where name equals to DEFAULT_NAME
        defaultAppraisalEvaluationParameterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the appraisalEvaluationParameterList where name equals to UPDATED_NAME
        defaultAppraisalEvaluationParameterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAppraisalEvaluationParameterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the appraisalEvaluationParameterList where name equals to UPDATED_NAME
        defaultAppraisalEvaluationParameterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where name is not null
        defaultAppraisalEvaluationParameterShouldBeFound("name.specified=true");

        // Get all the appraisalEvaluationParameterList where name is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByNameContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where name contains DEFAULT_NAME
        defaultAppraisalEvaluationParameterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the appraisalEvaluationParameterList where name contains UPDATED_NAME
        defaultAppraisalEvaluationParameterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where name does not contain DEFAULT_NAME
        defaultAppraisalEvaluationParameterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the appraisalEvaluationParameterList where name does not contain UPDATED_NAME
        defaultAppraisalEvaluationParameterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where description equals to DEFAULT_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the appraisalEvaluationParameterList where description equals to UPDATED_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the appraisalEvaluationParameterList where description equals to UPDATED_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where description is not null
        defaultAppraisalEvaluationParameterShouldBeFound("description.specified=true");

        // Get all the appraisalEvaluationParameterList where description is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where description contains DEFAULT_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the appraisalEvaluationParameterList where description contains UPDATED_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where description does not contain DEFAULT_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the appraisalEvaluationParameterList where description does not contain UPDATED_DESCRIPTION
        defaultAppraisalEvaluationParameterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where type equals to DEFAULT_TYPE
        defaultAppraisalEvaluationParameterShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the appraisalEvaluationParameterList where type equals to UPDATED_TYPE
        defaultAppraisalEvaluationParameterShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAppraisalEvaluationParameterShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the appraisalEvaluationParameterList where type equals to UPDATED_TYPE
        defaultAppraisalEvaluationParameterShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where type is not null
        defaultAppraisalEvaluationParameterShouldBeFound("type.specified=true");

        // Get all the appraisalEvaluationParameterList where type is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByTypeContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where type contains DEFAULT_TYPE
        defaultAppraisalEvaluationParameterShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the appraisalEvaluationParameterList where type contains UPDATED_TYPE
        defaultAppraisalEvaluationParameterShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where type does not contain DEFAULT_TYPE
        defaultAppraisalEvaluationParameterShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the appraisalEvaluationParameterList where type does not contain UPDATED_TYPE
        defaultAppraisalEvaluationParameterShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId equals to DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationParameterList where companyId equals to UPDATED_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the appraisalEvaluationParameterList where companyId equals to UPDATED_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId is not null
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.specified=true");

        // Get all the appraisalEvaluationParameterList where companyId is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationParameterList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationParameterList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId is less than DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationParameterList where companyId is less than UPDATED_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where companyId is greater than DEFAULT_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the appraisalEvaluationParameterList where companyId is greater than SMALLER_COMPANY_ID
        defaultAppraisalEvaluationParameterShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where status equals to DEFAULT_STATUS
        defaultAppraisalEvaluationParameterShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the appraisalEvaluationParameterList where status equals to UPDATED_STATUS
        defaultAppraisalEvaluationParameterShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAppraisalEvaluationParameterShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the appraisalEvaluationParameterList where status equals to UPDATED_STATUS
        defaultAppraisalEvaluationParameterShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where status is not null
        defaultAppraisalEvaluationParameterShouldBeFound("status.specified=true");

        // Get all the appraisalEvaluationParameterList where status is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByStatusContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where status contains DEFAULT_STATUS
        defaultAppraisalEvaluationParameterShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the appraisalEvaluationParameterList where status contains UPDATED_STATUS
        defaultAppraisalEvaluationParameterShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where status does not contain DEFAULT_STATUS
        defaultAppraisalEvaluationParameterShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the appraisalEvaluationParameterList where status does not contain UPDATED_STATUS
        defaultAppraisalEvaluationParameterShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAppraisalEvaluationParameterShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the appraisalEvaluationParameterList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAppraisalEvaluationParameterShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the appraisalEvaluationParameterList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModified is not null
        defaultAppraisalEvaluationParameterShouldBeFound("lastModified.specified=true");

        // Get all the appraisalEvaluationParameterList where lastModified is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy is not null
        defaultAppraisalEvaluationParameterShouldBeFound("lastModifiedBy.specified=true");

        // Get all the appraisalEvaluationParameterList where lastModifiedBy is null
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAppraisalEvaluationParametersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the appraisalEvaluationParameterList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAppraisalEvaluationParameterShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppraisalEvaluationParameterShouldBeFound(String filter) throws Exception {
        restAppraisalEvaluationParameterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraisalEvaluationParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAppraisalEvaluationParameterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppraisalEvaluationParameterShouldNotBeFound(String filter) throws Exception {
        restAppraisalEvaluationParameterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppraisalEvaluationParameterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAppraisalEvaluationParameter() throws Exception {
        // Get the appraisalEvaluationParameter
        restAppraisalEvaluationParameterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppraisalEvaluationParameter() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();

        // Update the appraisalEvaluationParameter
        AppraisalEvaluationParameter updatedAppraisalEvaluationParameter = appraisalEvaluationParameterRepository
            .findById(appraisalEvaluationParameter.getId())
            .get();
        // Disconnect from session so that the updates on updatedAppraisalEvaluationParameter are not directly saved in db
        em.detach(updatedAppraisalEvaluationParameter);
        updatedAppraisalEvaluationParameter
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
        //    .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            updatedAppraisalEvaluationParameter
        );

        restAppraisalEvaluationParameterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appraisalEvaluationParameterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
               //     .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
        AppraisalEvaluationParameter testAppraisalEvaluationParameter = appraisalEvaluationParameterList.get(
            appraisalEvaluationParameterList.size() - 1
        );
        assertThat(testAppraisalEvaluationParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppraisalEvaluationParameter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppraisalEvaluationParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppraisalEvaluationParameter.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalEvaluationParameter.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalEvaluationParameter.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalEvaluationParameter.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();
        appraisalEvaluationParameter.setId(count.incrementAndGet());

        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppraisalEvaluationParameterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appraisalEvaluationParameterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
              //      .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();
        appraisalEvaluationParameter.setId(count.incrementAndGet());

        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationParameterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
           //         .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();
        appraisalEvaluationParameter.setId(count.incrementAndGet());

        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationParameterMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
       //             .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppraisalEvaluationParameterWithPatch() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();

        // Update the appraisalEvaluationParameter using partial update
        AppraisalEvaluationParameter partialUpdatedAppraisalEvaluationParameter = new AppraisalEvaluationParameter();
        partialUpdatedAppraisalEvaluationParameter.setId(appraisalEvaluationParameter.getId());

        partialUpdatedAppraisalEvaluationParameter.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAppraisalEvaluationParameterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppraisalEvaluationParameter.getId())
                    .contentType("application/merge-patch+json")
         //           .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppraisalEvaluationParameter))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
        AppraisalEvaluationParameter testAppraisalEvaluationParameter = appraisalEvaluationParameterList.get(
            appraisalEvaluationParameterList.size() - 1
        );
        assertThat(testAppraisalEvaluationParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppraisalEvaluationParameter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppraisalEvaluationParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppraisalEvaluationParameter.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAppraisalEvaluationParameter.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppraisalEvaluationParameter.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAppraisalEvaluationParameter.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAppraisalEvaluationParameterWithPatch() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();

        // Update the appraisalEvaluationParameter using partial update
        AppraisalEvaluationParameter partialUpdatedAppraisalEvaluationParameter = new AppraisalEvaluationParameter();
        partialUpdatedAppraisalEvaluationParameter.setId(appraisalEvaluationParameter.getId());

        partialUpdatedAppraisalEvaluationParameter
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
        //    .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAppraisalEvaluationParameterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppraisalEvaluationParameter.getId())
                    .contentType("application/merge-patch+json")
              //      .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppraisalEvaluationParameter))
            )
            .andExpect(status().isOk());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
        AppraisalEvaluationParameter testAppraisalEvaluationParameter = appraisalEvaluationParameterList.get(
            appraisalEvaluationParameterList.size() - 1
        );
        assertThat(testAppraisalEvaluationParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppraisalEvaluationParameter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppraisalEvaluationParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppraisalEvaluationParameter.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAppraisalEvaluationParameter.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppraisalEvaluationParameter.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAppraisalEvaluationParameter.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();
        appraisalEvaluationParameter.setId(count.incrementAndGet());

        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppraisalEvaluationParameterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appraisalEvaluationParameterDTO.getId())
                    .contentType("application/merge-patch+json")
              //      .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();
        appraisalEvaluationParameter.setId(count.incrementAndGet());

        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationParameterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
          //          .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppraisalEvaluationParameter() throws Exception {
        int databaseSizeBeforeUpdate = appraisalEvaluationParameterRepository.findAll().size();
        appraisalEvaluationParameter.setId(count.incrementAndGet());

        // Create the AppraisalEvaluationParameter
        AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO = appraisalEvaluationParameterMapper.toDto(
            appraisalEvaluationParameter
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppraisalEvaluationParameterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
            //        .content(TestUtil.convertObjectToJsonBytes(appraisalEvaluationParameterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppraisalEvaluationParameter in the database
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppraisalEvaluationParameter() throws Exception {
        // Initialize the database
        appraisalEvaluationParameterRepository.saveAndFlush(appraisalEvaluationParameter);

        int databaseSizeBeforeDelete = appraisalEvaluationParameterRepository.findAll().size();

        // Delete the appraisalEvaluationParameter
        restAppraisalEvaluationParameterMockMvc
            .perform(delete(ENTITY_API_URL_ID, appraisalEvaluationParameter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppraisalEvaluationParameter> appraisalEvaluationParameterList = appraisalEvaluationParameterRepository.findAll();
        assertThat(appraisalEvaluationParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
