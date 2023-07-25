package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.ApprovalLevel;
import com.techvg.hrms.repository.ApprovalLevelRepository;
import com.techvg.hrms.service.criteria.ApprovalLevelCriteria;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.service.mapper.ApprovalLevelMapper;
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
 * Integration tests for the {@link ApprovalLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprovalLevelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SQUENCE = 1L;
    private static final Long UPDATED_SQUENCE = 2L;
    private static final Long SMALLER_SQUENCE = 1L - 1L;

    private static final Long DEFAULT_APPROVAL_SETTING_ID = 1L;
    private static final Long UPDATED_APPROVAL_SETTING_ID = 2L;
    private static final Long SMALLER_APPROVAL_SETTING_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/approval-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprovalLevelRepository approvalLevelRepository;

    @Autowired
    private ApprovalLevelMapper approvalLevelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprovalLevelMockMvc;

    private ApprovalLevel approvalLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalLevel createEntity(EntityManager em) {
        ApprovalLevel approvalLevel = new ApprovalLevel()
            //            .designationId(DEFAULT_DESIGNATION_ID)
            .squence(DEFAULT_SQUENCE)
            .approvalSettingId(DEFAULT_APPROVAL_SETTING_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS);
        //            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return approvalLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovalLevel createUpdatedEntity(EntityManager em) {
        ApprovalLevel approvalLevel = new ApprovalLevel()
            //            .name(UPDATED_NAME)
            .squence(UPDATED_SQUENCE)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return approvalLevel;
    }

    @BeforeEach
    public void initTest() {
        approvalLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createApprovalLevel() throws Exception {
        int databaseSizeBeforeCreate = approvalLevelRepository.findAll().size();
        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);
        restApprovalLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeCreate + 1);
        ApprovalLevel testApprovalLevel = approvalLevelList.get(approvalLevelList.size() - 1);
        //        assertThat(testApprovalLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApprovalLevel.getSquence()).isEqualTo(DEFAULT_SQUENCE);
        assertThat(testApprovalLevel.getApprovalSettingId()).isEqualTo(DEFAULT_APPROVAL_SETTING_ID);
        assertThat(testApprovalLevel.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testApprovalLevel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApprovalLevel.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testApprovalLevel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createApprovalLevelWithExistingId() throws Exception {
        // Create the ApprovalLevel with an existing ID
        approvalLevel.setId(1L);
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        int databaseSizeBeforeCreate = approvalLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovalLevels() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList
        restApprovalLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvalLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].squence").value(hasItem(DEFAULT_SQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].approvalSettingId").value(hasItem(DEFAULT_APPROVAL_SETTING_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getApprovalLevel() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get the approvalLevel
        restApprovalLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, approvalLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approvalLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.squence").value(DEFAULT_SQUENCE.intValue()))
            .andExpect(jsonPath("$.approvalSettingId").value(DEFAULT_APPROVAL_SETTING_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getApprovalLevelsByIdFiltering() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        Long id = approvalLevel.getId();

        defaultApprovalLevelShouldBeFound("id.equals=" + id);
        defaultApprovalLevelShouldNotBeFound("id.notEquals=" + id);

        defaultApprovalLevelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApprovalLevelShouldNotBeFound("id.greaterThan=" + id);

        defaultApprovalLevelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApprovalLevelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where name equals to DEFAULT_NAME
        defaultApprovalLevelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the approvalLevelList where name equals to UPDATED_NAME
        defaultApprovalLevelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApprovalLevelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the approvalLevelList where name equals to UPDATED_NAME
        defaultApprovalLevelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where name is not null
        defaultApprovalLevelShouldBeFound("name.specified=true");

        // Get all the approvalLevelList where name is null
        defaultApprovalLevelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByNameContainsSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where name contains DEFAULT_NAME
        defaultApprovalLevelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the approvalLevelList where name contains UPDATED_NAME
        defaultApprovalLevelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where name does not contain DEFAULT_NAME
        defaultApprovalLevelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the approvalLevelList where name does not contain UPDATED_NAME
        defaultApprovalLevelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence equals to DEFAULT_SQUENCE
        defaultApprovalLevelShouldBeFound("squence.equals=" + DEFAULT_SQUENCE);

        // Get all the approvalLevelList where squence equals to UPDATED_SQUENCE
        defaultApprovalLevelShouldNotBeFound("squence.equals=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence in DEFAULT_SQUENCE or UPDATED_SQUENCE
        defaultApprovalLevelShouldBeFound("squence.in=" + DEFAULT_SQUENCE + "," + UPDATED_SQUENCE);

        // Get all the approvalLevelList where squence equals to UPDATED_SQUENCE
        defaultApprovalLevelShouldNotBeFound("squence.in=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence is not null
        defaultApprovalLevelShouldBeFound("squence.specified=true");

        // Get all the approvalLevelList where squence is null
        defaultApprovalLevelShouldNotBeFound("squence.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence is greater than or equal to DEFAULT_SQUENCE
        defaultApprovalLevelShouldBeFound("squence.greaterThanOrEqual=" + DEFAULT_SQUENCE);

        // Get all the approvalLevelList where squence is greater than or equal to UPDATED_SQUENCE
        defaultApprovalLevelShouldNotBeFound("squence.greaterThanOrEqual=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence is less than or equal to DEFAULT_SQUENCE
        defaultApprovalLevelShouldBeFound("squence.lessThanOrEqual=" + DEFAULT_SQUENCE);

        // Get all the approvalLevelList where squence is less than or equal to SMALLER_SQUENCE
        defaultApprovalLevelShouldNotBeFound("squence.lessThanOrEqual=" + SMALLER_SQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence is less than DEFAULT_SQUENCE
        defaultApprovalLevelShouldNotBeFound("squence.lessThan=" + DEFAULT_SQUENCE);

        // Get all the approvalLevelList where squence is less than UPDATED_SQUENCE
        defaultApprovalLevelShouldBeFound("squence.lessThan=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsBySquenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where squence is greater than DEFAULT_SQUENCE
        defaultApprovalLevelShouldNotBeFound("squence.greaterThan=" + DEFAULT_SQUENCE);

        // Get all the approvalLevelList where squence is greater than SMALLER_SQUENCE
        defaultApprovalLevelShouldBeFound("squence.greaterThan=" + SMALLER_SQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId equals to DEFAULT_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldBeFound("approvalSettingId.equals=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approvalLevelList where approvalSettingId equals to UPDATED_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.equals=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId in DEFAULT_APPROVAL_SETTING_ID or UPDATED_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldBeFound("approvalSettingId.in=" + DEFAULT_APPROVAL_SETTING_ID + "," + UPDATED_APPROVAL_SETTING_ID);

        // Get all the approvalLevelList where approvalSettingId equals to UPDATED_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.in=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId is not null
        defaultApprovalLevelShouldBeFound("approvalSettingId.specified=true");

        // Get all the approvalLevelList where approvalSettingId is null
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId is greater than or equal to DEFAULT_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldBeFound("approvalSettingId.greaterThanOrEqual=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approvalLevelList where approvalSettingId is greater than or equal to UPDATED_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.greaterThanOrEqual=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId is less than or equal to DEFAULT_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldBeFound("approvalSettingId.lessThanOrEqual=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approvalLevelList where approvalSettingId is less than or equal to SMALLER_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.lessThanOrEqual=" + SMALLER_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId is less than DEFAULT_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.lessThan=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approvalLevelList where approvalSettingId is less than UPDATED_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldBeFound("approvalSettingId.lessThan=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByApprovalSettingIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where approvalSettingId is greater than DEFAULT_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldNotBeFound("approvalSettingId.greaterThan=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the approvalLevelList where approvalSettingId is greater than SMALLER_APPROVAL_SETTING_ID
        defaultApprovalLevelShouldBeFound("approvalSettingId.greaterThan=" + SMALLER_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId equals to DEFAULT_COMPANY_ID
        defaultApprovalLevelShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the approvalLevelList where companyId equals to UPDATED_COMPANY_ID
        defaultApprovalLevelShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultApprovalLevelShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the approvalLevelList where companyId equals to UPDATED_COMPANY_ID
        defaultApprovalLevelShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId is not null
        defaultApprovalLevelShouldBeFound("companyId.specified=true");

        // Get all the approvalLevelList where companyId is null
        defaultApprovalLevelShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultApprovalLevelShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approvalLevelList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultApprovalLevelShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultApprovalLevelShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approvalLevelList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultApprovalLevelShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId is less than DEFAULT_COMPANY_ID
        defaultApprovalLevelShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the approvalLevelList where companyId is less than UPDATED_COMPANY_ID
        defaultApprovalLevelShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where companyId is greater than DEFAULT_COMPANY_ID
        defaultApprovalLevelShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the approvalLevelList where companyId is greater than SMALLER_COMPANY_ID
        defaultApprovalLevelShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where status equals to DEFAULT_STATUS
        defaultApprovalLevelShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the approvalLevelList where status equals to UPDATED_STATUS
        defaultApprovalLevelShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApprovalLevelShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the approvalLevelList where status equals to UPDATED_STATUS
        defaultApprovalLevelShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where status is not null
        defaultApprovalLevelShouldBeFound("status.specified=true");

        // Get all the approvalLevelList where status is null
        defaultApprovalLevelShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByStatusContainsSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where status contains DEFAULT_STATUS
        defaultApprovalLevelShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the approvalLevelList where status contains UPDATED_STATUS
        defaultApprovalLevelShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where status does not contain DEFAULT_STATUS
        defaultApprovalLevelShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the approvalLevelList where status does not contain UPDATED_STATUS
        defaultApprovalLevelShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultApprovalLevelShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the approvalLevelList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApprovalLevelShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultApprovalLevelShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the approvalLevelList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApprovalLevelShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModified is not null
        defaultApprovalLevelShouldBeFound("lastModified.specified=true");

        // Get all the approvalLevelList where lastModified is null
        defaultApprovalLevelShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApprovalLevelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalLevelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApprovalLevelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApprovalLevelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the approvalLevelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApprovalLevelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModifiedBy is not null
        defaultApprovalLevelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the approvalLevelList where lastModifiedBy is null
        defaultApprovalLevelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApprovalLevelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalLevelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApprovalLevelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalLevelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        // Get all the approvalLevelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApprovalLevelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalLevelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApprovalLevelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApprovalLevelShouldBeFound(String filter) throws Exception {
        restApprovalLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approvalLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].squence").value(hasItem(DEFAULT_SQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].approvalSettingId").value(hasItem(DEFAULT_APPROVAL_SETTING_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restApprovalLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApprovalLevelShouldNotBeFound(String filter) throws Exception {
        restApprovalLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApprovalLevelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApprovalLevel() throws Exception {
        // Get the approvalLevel
        restApprovalLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApprovalLevel() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();

        // Update the approvalLevel
        ApprovalLevel updatedApprovalLevel = approvalLevelRepository.findById(approvalLevel.getId()).get();
        // Disconnect from session so that the updates on updatedApprovalLevel are not directly saved in db
        em.detach(updatedApprovalLevel);
        updatedApprovalLevel
            //            .name(UPDATED_NAME)
            .squence(UPDATED_SQUENCE)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(updatedApprovalLevel);

        restApprovalLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
        ApprovalLevel testApprovalLevel = approvalLevelList.get(approvalLevelList.size() - 1);
        //        assertThat(testApprovalLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApprovalLevel.getSquence()).isEqualTo(UPDATED_SQUENCE);
        assertThat(testApprovalLevel.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testApprovalLevel.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testApprovalLevel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovalLevel.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprovalLevel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingApprovalLevel() throws Exception {
        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();
        approvalLevel.setId(count.incrementAndGet());

        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprovalLevel() throws Exception {
        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();
        approvalLevel.setId(count.incrementAndGet());

        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprovalLevel() throws Exception {
        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();
        approvalLevel.setId(count.incrementAndGet());

        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalLevelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprovalLevelWithPatch() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();

        // Update the approvalLevel using partial update
        ApprovalLevel partialUpdatedApprovalLevel = new ApprovalLevel();
        partialUpdatedApprovalLevel.setId(approvalLevel.getId());

        partialUpdatedApprovalLevel
            //            .name(UPDATED_NAME)
            .squence(UPDATED_SQUENCE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restApprovalLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalLevel))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
        ApprovalLevel testApprovalLevel = approvalLevelList.get(approvalLevelList.size() - 1);
        //        assertThat(testApprovalLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApprovalLevel.getSquence()).isEqualTo(UPDATED_SQUENCE);
        assertThat(testApprovalLevel.getApprovalSettingId()).isEqualTo(DEFAULT_APPROVAL_SETTING_ID);
        assertThat(testApprovalLevel.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testApprovalLevel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovalLevel.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testApprovalLevel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateApprovalLevelWithPatch() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();

        // Update the approvalLevel using partial update
        ApprovalLevel partialUpdatedApprovalLevel = new ApprovalLevel();
        partialUpdatedApprovalLevel.setId(approvalLevel.getId());

        partialUpdatedApprovalLevel
            //            .name(UPDATED_NAME)
            .squence(UPDATED_SQUENCE)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restApprovalLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprovalLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprovalLevel))
            )
            .andExpect(status().isOk());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
        ApprovalLevel testApprovalLevel = approvalLevelList.get(approvalLevelList.size() - 1);
        //        assertThat(testApprovalLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApprovalLevel.getSquence()).isEqualTo(UPDATED_SQUENCE);
        assertThat(testApprovalLevel.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testApprovalLevel.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testApprovalLevel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApprovalLevel.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApprovalLevel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingApprovalLevel() throws Exception {
        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();
        approvalLevel.setId(count.incrementAndGet());

        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalLevelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprovalLevel() throws Exception {
        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();
        approvalLevel.setId(count.incrementAndGet());

        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprovalLevel() throws Exception {
        int databaseSizeBeforeUpdate = approvalLevelRepository.findAll().size();
        approvalLevel.setId(count.incrementAndGet());

        // Create the ApprovalLevel
        ApprovalLevelDTO approvalLevelDTO = approvalLevelMapper.toDto(approvalLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalLevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApprovalLevel in the database
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprovalLevel() throws Exception {
        // Initialize the database
        approvalLevelRepository.saveAndFlush(approvalLevel);

        int databaseSizeBeforeDelete = approvalLevelRepository.findAll().size();

        // Delete the approvalLevel
        restApprovalLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, approvalLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApprovalLevel> approvalLevelList = approvalLevelRepository.findAll();
        assertThat(approvalLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
