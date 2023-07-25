package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.CustomLeavePolicy;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.LeavePolicy;
import com.techvg.hrms.repository.CustomLeavePolicyRepository;
import com.techvg.hrms.service.criteria.CustomLeavePolicyCriteria;
import com.techvg.hrms.service.dto.CustomLeavePolicyDTO;
import com.techvg.hrms.service.mapper.CustomLeavePolicyMapper;
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
 * Integration tests for the {@link CustomLeavePolicyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomLeavePolicyResourceIT {

    private static final String DEFAULT_CUSTOM_POLICY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_POLICY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DAYS = 1L;
    private static final Long UPDATED_DAYS = 2L;
    private static final Long SMALLER_DAYS = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";


    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";
    private static final String ENTITY_API_URL = "/api/custom-leave-policies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomLeavePolicyRepository customLeavePolicyRepository;

    @Autowired
    private CustomLeavePolicyMapper customLeavePolicyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomLeavePolicyMockMvc;

    private CustomLeavePolicy customLeavePolicy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomLeavePolicy createEntity(EntityManager em) {
        CustomLeavePolicy customLeavePolicy = new CustomLeavePolicy()
            .customPolicyName(DEFAULT_CUSTOM_POLICY_NAME)
            .days(DEFAULT_DAYS)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return customLeavePolicy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomLeavePolicy createUpdatedEntity(EntityManager em) {
        CustomLeavePolicy customLeavePolicy = new CustomLeavePolicy()
            .customPolicyName(UPDATED_CUSTOM_POLICY_NAME)
            .days(UPDATED_DAYS)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return customLeavePolicy;
    }

    @BeforeEach
    public void initTest() {
        customLeavePolicy = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeCreate = customLeavePolicyRepository.findAll().size();
        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);
        restCustomLeavePolicyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeCreate + 1);
        CustomLeavePolicy testCustomLeavePolicy = customLeavePolicyList.get(customLeavePolicyList.size() - 1);
        assertThat(testCustomLeavePolicy.getCustomPolicyName()).isEqualTo(DEFAULT_CUSTOM_POLICY_NAME);
        assertThat(testCustomLeavePolicy.getDays()).isEqualTo(DEFAULT_DAYS);
        assertThat(testCustomLeavePolicy.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCustomLeavePolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
      //  assertThat(testCustomLeavePolicy.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCustomLeavePolicy.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCustomLeavePolicyWithExistingId() throws Exception {
        // Create the CustomLeavePolicy with an existing ID
        customLeavePolicy.setId(1L);
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        int databaseSizeBeforeCreate = customLeavePolicyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomLeavePolicyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomLeavePolicies() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList
        restCustomLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customLeavePolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].customPolicyName").value(hasItem(DEFAULT_CUSTOM_POLICY_NAME)))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
   //         .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCustomLeavePolicy() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get the customLeavePolicy
        restCustomLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL_ID, customLeavePolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customLeavePolicy.getId().intValue()))
            .andExpect(jsonPath("$.customPolicyName").value(DEFAULT_CUSTOM_POLICY_NAME))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
        //    .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getCustomLeavePoliciesByIdFiltering() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        Long id = customLeavePolicy.getId();

        defaultCustomLeavePolicyShouldBeFound("id.equals=" + id);
        defaultCustomLeavePolicyShouldNotBeFound("id.notEquals=" + id);

        defaultCustomLeavePolicyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomLeavePolicyShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomLeavePolicyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomLeavePolicyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCustomPolicyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where customPolicyName equals to DEFAULT_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldBeFound("customPolicyName.equals=" + DEFAULT_CUSTOM_POLICY_NAME);

        // Get all the customLeavePolicyList where customPolicyName equals to UPDATED_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldNotBeFound("customPolicyName.equals=" + UPDATED_CUSTOM_POLICY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCustomPolicyNameIsInShouldWork() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where customPolicyName in DEFAULT_CUSTOM_POLICY_NAME or UPDATED_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldBeFound("customPolicyName.in=" + DEFAULT_CUSTOM_POLICY_NAME + "," + UPDATED_CUSTOM_POLICY_NAME);

        // Get all the customLeavePolicyList where customPolicyName equals to UPDATED_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldNotBeFound("customPolicyName.in=" + UPDATED_CUSTOM_POLICY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCustomPolicyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where customPolicyName is not null
        defaultCustomLeavePolicyShouldBeFound("customPolicyName.specified=true");

        // Get all the customLeavePolicyList where customPolicyName is null
        defaultCustomLeavePolicyShouldNotBeFound("customPolicyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCustomPolicyNameContainsSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where customPolicyName contains DEFAULT_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldBeFound("customPolicyName.contains=" + DEFAULT_CUSTOM_POLICY_NAME);

        // Get all the customLeavePolicyList where customPolicyName contains UPDATED_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldNotBeFound("customPolicyName.contains=" + UPDATED_CUSTOM_POLICY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCustomPolicyNameNotContainsSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where customPolicyName does not contain DEFAULT_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldNotBeFound("customPolicyName.doesNotContain=" + DEFAULT_CUSTOM_POLICY_NAME);

        // Get all the customLeavePolicyList where customPolicyName does not contain UPDATED_CUSTOM_POLICY_NAME
        defaultCustomLeavePolicyShouldBeFound("customPolicyName.doesNotContain=" + UPDATED_CUSTOM_POLICY_NAME);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days equals to DEFAULT_DAYS
        defaultCustomLeavePolicyShouldBeFound("days.equals=" + DEFAULT_DAYS);

        // Get all the customLeavePolicyList where days equals to UPDATED_DAYS
        defaultCustomLeavePolicyShouldNotBeFound("days.equals=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsInShouldWork() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days in DEFAULT_DAYS or UPDATED_DAYS
        defaultCustomLeavePolicyShouldBeFound("days.in=" + DEFAULT_DAYS + "," + UPDATED_DAYS);

        // Get all the customLeavePolicyList where days equals to UPDATED_DAYS
        defaultCustomLeavePolicyShouldNotBeFound("days.in=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days is not null
        defaultCustomLeavePolicyShouldBeFound("days.specified=true");

        // Get all the customLeavePolicyList where days is null
        defaultCustomLeavePolicyShouldNotBeFound("days.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days is greater than or equal to DEFAULT_DAYS
        defaultCustomLeavePolicyShouldBeFound("days.greaterThanOrEqual=" + DEFAULT_DAYS);

        // Get all the customLeavePolicyList where days is greater than or equal to UPDATED_DAYS
        defaultCustomLeavePolicyShouldNotBeFound("days.greaterThanOrEqual=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days is less than or equal to DEFAULT_DAYS
        defaultCustomLeavePolicyShouldBeFound("days.lessThanOrEqual=" + DEFAULT_DAYS);

        // Get all the customLeavePolicyList where days is less than or equal to SMALLER_DAYS
        defaultCustomLeavePolicyShouldNotBeFound("days.lessThanOrEqual=" + SMALLER_DAYS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days is less than DEFAULT_DAYS
        defaultCustomLeavePolicyShouldNotBeFound("days.lessThan=" + DEFAULT_DAYS);

        // Get all the customLeavePolicyList where days is less than UPDATED_DAYS
        defaultCustomLeavePolicyShouldBeFound("days.lessThan=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where days is greater than DEFAULT_DAYS
        defaultCustomLeavePolicyShouldNotBeFound("days.greaterThan=" + DEFAULT_DAYS);

        // Get all the customLeavePolicyList where days is greater than SMALLER_DAYS
        defaultCustomLeavePolicyShouldBeFound("days.greaterThan=" + SMALLER_DAYS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId equals to DEFAULT_COMPANY_ID
        defaultCustomLeavePolicyShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the customLeavePolicyList where companyId equals to UPDATED_COMPANY_ID
        defaultCustomLeavePolicyShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultCustomLeavePolicyShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the customLeavePolicyList where companyId equals to UPDATED_COMPANY_ID
        defaultCustomLeavePolicyShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId is not null
        defaultCustomLeavePolicyShouldBeFound("companyId.specified=true");

        // Get all the customLeavePolicyList where companyId is null
        defaultCustomLeavePolicyShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultCustomLeavePolicyShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the customLeavePolicyList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultCustomLeavePolicyShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultCustomLeavePolicyShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the customLeavePolicyList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultCustomLeavePolicyShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId is less than DEFAULT_COMPANY_ID
        defaultCustomLeavePolicyShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the customLeavePolicyList where companyId is less than UPDATED_COMPANY_ID
        defaultCustomLeavePolicyShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where companyId is greater than DEFAULT_COMPANY_ID
        defaultCustomLeavePolicyShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the customLeavePolicyList where companyId is greater than SMALLER_COMPANY_ID
        defaultCustomLeavePolicyShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where status equals to DEFAULT_STATUS
        defaultCustomLeavePolicyShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the customLeavePolicyList where status equals to UPDATED_STATUS
        defaultCustomLeavePolicyShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustomLeavePolicyShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the customLeavePolicyList where status equals to UPDATED_STATUS
        defaultCustomLeavePolicyShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where status is not null
        defaultCustomLeavePolicyShouldBeFound("status.specified=true");

        // Get all the customLeavePolicyList where status is null
        defaultCustomLeavePolicyShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByStatusContainsSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where status contains DEFAULT_STATUS
        defaultCustomLeavePolicyShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the customLeavePolicyList where status contains UPDATED_STATUS
        defaultCustomLeavePolicyShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where status does not contain DEFAULT_STATUS
        defaultCustomLeavePolicyShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the customLeavePolicyList where status does not contain UPDATED_STATUS
        defaultCustomLeavePolicyShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

 //       // Get all the customLeavePolicyList where lastModified equals to DEFAULT_LAST_MODIFIED
  //      defaultCustomLeavePolicyShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the customLeavePolicyList where lastModified equals to UPDATED_LAST_MODIFIED
 //       defaultCustomLeavePolicyShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

//        // Get all the customLeavePolicyList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
//        defaultCustomLeavePolicyShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);
//
//        // Get all the customLeavePolicyList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultCustomLeavePolicyShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where lastModified is not null
        defaultCustomLeavePolicyShouldBeFound("lastModified.specified=true");

        // Get all the customLeavePolicyList where lastModified is null
        defaultCustomLeavePolicyShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customLeavePolicyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the customLeavePolicyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where lastModifiedBy is not null
        defaultCustomLeavePolicyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the customLeavePolicyList where lastModifiedBy is null
        defaultCustomLeavePolicyShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customLeavePolicyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        // Get all the customLeavePolicyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customLeavePolicyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultCustomLeavePolicyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByLeavePolicyIsEqualToSomething() throws Exception {
        LeavePolicy leavePolicy;
        if (TestUtil.findAll(em, LeavePolicy.class).isEmpty()) {
            customLeavePolicyRepository.saveAndFlush(customLeavePolicy);
            leavePolicy = LeavePolicyResourceIT.createEntity(em);
        } else {
            leavePolicy = TestUtil.findAll(em, LeavePolicy.class).get(0);
        }
        em.persist(leavePolicy);
        em.flush();
        customLeavePolicy.setLeavePolicy(leavePolicy);
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);
        Long leavePolicyId = leavePolicy.getId();

        // Get all the customLeavePolicyList where leavePolicy equals to leavePolicyId
        defaultCustomLeavePolicyShouldBeFound("leavePolicyId.equals=" + leavePolicyId);

        // Get all the customLeavePolicyList where leavePolicy equals to (leavePolicyId + 1)
        defaultCustomLeavePolicyShouldNotBeFound("leavePolicyId.equals=" + (leavePolicyId + 1));
    }

    @Test
    @Transactional
    void getAllCustomLeavePoliciesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            customLeavePolicyRepository.saveAndFlush(customLeavePolicy);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        customLeavePolicy.setEmployee(employee);
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);
        Long employeeId = employee.getId();

        // Get all the customLeavePolicyList where employee equals to employeeId
        defaultCustomLeavePolicyShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the customLeavePolicyList where employee equals to (employeeId + 1)
        defaultCustomLeavePolicyShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomLeavePolicyShouldBeFound(String filter) throws Exception {
        restCustomLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customLeavePolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].customPolicyName").value(hasItem(DEFAULT_CUSTOM_POLICY_NAME)))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
 //           .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restCustomLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomLeavePolicyShouldNotBeFound(String filter) throws Exception {
        restCustomLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomLeavePolicy() throws Exception {
        // Get the customLeavePolicy
        restCustomLeavePolicyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomLeavePolicy() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();

        // Update the customLeavePolicy
        CustomLeavePolicy updatedCustomLeavePolicy = customLeavePolicyRepository.findById(customLeavePolicy.getId()).get();
        // Disconnect from session so that the updates on updatedCustomLeavePolicy are not directly saved in db
        em.detach(updatedCustomLeavePolicy);
        updatedCustomLeavePolicy
            .customPolicyName(UPDATED_CUSTOM_POLICY_NAME)
            .days(UPDATED_DAYS)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(updatedCustomLeavePolicy);

        restCustomLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customLeavePolicyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
        CustomLeavePolicy testCustomLeavePolicy = customLeavePolicyList.get(customLeavePolicyList.size() - 1);
        assertThat(testCustomLeavePolicy.getCustomPolicyName()).isEqualTo(UPDATED_CUSTOM_POLICY_NAME);
        assertThat(testCustomLeavePolicy.getDays()).isEqualTo(UPDATED_DAYS);
        assertThat(testCustomLeavePolicy.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomLeavePolicy.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testCustomLeavePolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCustomLeavePolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();
        customLeavePolicy.setId(count.incrementAndGet());

        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customLeavePolicyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();
        customLeavePolicy.setId(count.incrementAndGet());

        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();
        customLeavePolicy.setId(count.incrementAndGet());

        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomLeavePolicyWithPatch() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();

        // Update the customLeavePolicy using partial update
        CustomLeavePolicy partialUpdatedCustomLeavePolicy = new CustomLeavePolicy();
        partialUpdatedCustomLeavePolicy.setId(customLeavePolicy.getId());

        partialUpdatedCustomLeavePolicy//            .companyId(UPDATED_COMPANY_ID)
        .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restCustomLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomLeavePolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomLeavePolicy))
            )
            .andExpect(status().isOk());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
        CustomLeavePolicy testCustomLeavePolicy = customLeavePolicyList.get(customLeavePolicyList.size() - 1);
        assertThat(testCustomLeavePolicy.getCustomPolicyName()).isEqualTo(DEFAULT_CUSTOM_POLICY_NAME);
        assertThat(testCustomLeavePolicy.getDays()).isEqualTo(DEFAULT_DAYS);
        assertThat(testCustomLeavePolicy.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomLeavePolicy.getStatus()).isEqualTo(UPDATED_STATUS);
  //      assertThat(testCustomLeavePolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCustomLeavePolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCustomLeavePolicyWithPatch() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();

        // Update the customLeavePolicy using partial update
        CustomLeavePolicy partialUpdatedCustomLeavePolicy = new CustomLeavePolicy();
        partialUpdatedCustomLeavePolicy.setId(customLeavePolicy.getId());

        partialUpdatedCustomLeavePolicy
            .customPolicyName(UPDATED_CUSTOM_POLICY_NAME)
            .days(UPDATED_DAYS)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restCustomLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomLeavePolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomLeavePolicy))
            )
            .andExpect(status().isOk());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
        CustomLeavePolicy testCustomLeavePolicy = customLeavePolicyList.get(customLeavePolicyList.size() - 1);
        assertThat(testCustomLeavePolicy.getCustomPolicyName()).isEqualTo(UPDATED_CUSTOM_POLICY_NAME);
        assertThat(testCustomLeavePolicy.getDays()).isEqualTo(UPDATED_DAYS);
        assertThat(testCustomLeavePolicy.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomLeavePolicy.getStatus()).isEqualTo(UPDATED_STATUS);
  //      assertThat(testCustomLeavePolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCustomLeavePolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();
        customLeavePolicy.setId(count.incrementAndGet());

        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customLeavePolicyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();
        customLeavePolicy.setId(count.incrementAndGet());

        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = customLeavePolicyRepository.findAll().size();
        customLeavePolicy.setId(count.incrementAndGet());

        // Create the CustomLeavePolicy
        CustomLeavePolicyDTO customLeavePolicyDTO = customLeavePolicyMapper.toDto(customLeavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customLeavePolicyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomLeavePolicy in the database
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomLeavePolicy() throws Exception {
        // Initialize the database
        customLeavePolicyRepository.saveAndFlush(customLeavePolicy);

        int databaseSizeBeforeDelete = customLeavePolicyRepository.findAll().size();

        // Delete the customLeavePolicy
        restCustomLeavePolicyMockMvc
            .perform(delete(ENTITY_API_URL_ID, customLeavePolicy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomLeavePolicy> customLeavePolicyList = customLeavePolicyRepository.findAll();
        assertThat(customLeavePolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
