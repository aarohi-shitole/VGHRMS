package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Branch;
import com.techvg.hrms.repository.BranchRepository;
import com.techvg.hrms.service.criteria.BranchCriteria;
import com.techvg.hrms.service.dto.BranchDTO;
import com.techvg.hrms.service.mapper.BranchMapper;
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
 * Integration tests for the {@link BranchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BranchResourceIT {

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCHCODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCHCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final Long DEFAULT_BRANCH_ID = 1L;
    private static final Long UPDATED_BRANCH_ID = 2L;
    private static final Long SMALLER_BRANCH_ID = 1L - 1L;

    private static final Long DEFAULT_REGION_ID = 1L;
    private static final Long UPDATED_REGION_ID = 2L;
    private static final Long SMALLER_REGION_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/branches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBranchMockMvc;

    private Branch branch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createEntity(EntityManager em) {
        Branch branch = new Branch()
            .branchName(DEFAULT_BRANCH_NAME)
            .description(DEFAULT_DESCRIPTION)
            .branchcode(DEFAULT_BRANCHCODE)
            .branchType(DEFAULT_BRANCH_TYPE)
            .webSite(DEFAULT_WEB_SITE)
            .branchId(DEFAULT_BRANCH_ID)
            .regionId(DEFAULT_REGION_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return branch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branch createUpdatedEntity(EntityManager em) {
        Branch branch = new Branch()
            .branchName(UPDATED_BRANCH_NAME)
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .branchType(UPDATED_BRANCH_TYPE)
            .webSite(UPDATED_WEB_SITE)
            .branchId(UPDATED_BRANCH_ID)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return branch;
    }

    @BeforeEach
    public void initTest() {
        branch = createEntity(em);
    }

    @Test
    @Transactional
    void createBranch() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();
        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);
        restBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate + 1);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(DEFAULT_BRANCHCODE);
        assertThat(testBranch.getBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
        assertThat(testBranch.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testBranch.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testBranch.getRegionId()).isEqualTo(DEFAULT_REGION_ID);
        assertThat(testBranch.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testBranch.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBranch.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createBranchWithExistingId() throws Exception {
        // Create the Branch with an existing ID
        branch.setId(1L);
        BranchDTO branchDTO = branchMapper.toDto(branch);

        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setBranchName(null);

        // Create the Branch, which fails.
        BranchDTO branchDTO = branchMapper.toDto(branch);

        restBranchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBranches() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].branchcode").value(hasItem(DEFAULT_BRANCHCODE)))
            .andExpect(jsonPath("$.[*].branchType").value(hasItem(DEFAULT_BRANCH_TYPE)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].regionId").value(hasItem(DEFAULT_REGION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get the branch
        restBranchMockMvc
            .perform(get(ENTITY_API_URL_ID, branch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(branch.getId().intValue()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.branchcode").value(DEFAULT_BRANCHCODE))
            .andExpect(jsonPath("$.branchType").value(DEFAULT_BRANCH_TYPE))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID.intValue()))
            .andExpect(jsonPath("$.regionId").value(DEFAULT_REGION_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getBranchesByIdFiltering() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        Long id = branch.getId();

        defaultBranchShouldBeFound("id.equals=" + id);
        defaultBranchShouldNotBeFound("id.notEquals=" + id);

        defaultBranchShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBranchShouldNotBeFound("id.greaterThan=" + id);

        defaultBranchShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBranchShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName equals to DEFAULT_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the branchList where branchName equals to UPDATED_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the branchList where branchName equals to UPDATED_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName is not null
        defaultBranchShouldBeFound("branchName.specified=true");

        // Get all the branchList where branchName is null
        defaultBranchShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName contains DEFAULT_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the branchList where branchName contains UPDATED_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultBranchShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the branchList where branchName does not contain UPDATED_BRANCH_NAME
        defaultBranchShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description equals to DEFAULT_DESCRIPTION
        defaultBranchShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the branchList where description equals to UPDATED_DESCRIPTION
        defaultBranchShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBranchShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the branchList where description equals to UPDATED_DESCRIPTION
        defaultBranchShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description is not null
        defaultBranchShouldBeFound("description.specified=true");

        // Get all the branchList where description is null
        defaultBranchShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description contains DEFAULT_DESCRIPTION
        defaultBranchShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the branchList where description contains UPDATED_DESCRIPTION
        defaultBranchShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where description does not contain DEFAULT_DESCRIPTION
        defaultBranchShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the branchList where description does not contain UPDATED_DESCRIPTION
        defaultBranchShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode equals to DEFAULT_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.equals=" + DEFAULT_BRANCHCODE);

        // Get all the branchList where branchcode equals to UPDATED_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.equals=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode in DEFAULT_BRANCHCODE or UPDATED_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.in=" + DEFAULT_BRANCHCODE + "," + UPDATED_BRANCHCODE);

        // Get all the branchList where branchcode equals to UPDATED_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.in=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode is not null
        defaultBranchShouldBeFound("branchcode.specified=true");

        // Get all the branchList where branchcode is null
        defaultBranchShouldNotBeFound("branchcode.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode contains DEFAULT_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.contains=" + DEFAULT_BRANCHCODE);

        // Get all the branchList where branchcode contains UPDATED_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.contains=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchcodeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchcode does not contain DEFAULT_BRANCHCODE
        defaultBranchShouldNotBeFound("branchcode.doesNotContain=" + DEFAULT_BRANCHCODE);

        // Get all the branchList where branchcode does not contain UPDATED_BRANCHCODE
        defaultBranchShouldBeFound("branchcode.doesNotContain=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType equals to DEFAULT_BRANCH_TYPE
        defaultBranchShouldBeFound("branchType.equals=" + DEFAULT_BRANCH_TYPE);

        // Get all the branchList where branchType equals to UPDATED_BRANCH_TYPE
        defaultBranchShouldNotBeFound("branchType.equals=" + UPDATED_BRANCH_TYPE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType in DEFAULT_BRANCH_TYPE or UPDATED_BRANCH_TYPE
        defaultBranchShouldBeFound("branchType.in=" + DEFAULT_BRANCH_TYPE + "," + UPDATED_BRANCH_TYPE);

        // Get all the branchList where branchType equals to UPDATED_BRANCH_TYPE
        defaultBranchShouldNotBeFound("branchType.in=" + UPDATED_BRANCH_TYPE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType is not null
        defaultBranchShouldBeFound("branchType.specified=true");

        // Get all the branchList where branchType is null
        defaultBranchShouldNotBeFound("branchType.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType contains DEFAULT_BRANCH_TYPE
        defaultBranchShouldBeFound("branchType.contains=" + DEFAULT_BRANCH_TYPE);

        // Get all the branchList where branchType contains UPDATED_BRANCH_TYPE
        defaultBranchShouldNotBeFound("branchType.contains=" + UPDATED_BRANCH_TYPE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchTypeNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchType does not contain DEFAULT_BRANCH_TYPE
        defaultBranchShouldNotBeFound("branchType.doesNotContain=" + DEFAULT_BRANCH_TYPE);

        // Get all the branchList where branchType does not contain UPDATED_BRANCH_TYPE
        defaultBranchShouldBeFound("branchType.doesNotContain=" + UPDATED_BRANCH_TYPE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite equals to DEFAULT_WEB_SITE
        defaultBranchShouldBeFound("webSite.equals=" + DEFAULT_WEB_SITE);

        // Get all the branchList where webSite equals to UPDATED_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.equals=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite in DEFAULT_WEB_SITE or UPDATED_WEB_SITE
        defaultBranchShouldBeFound("webSite.in=" + DEFAULT_WEB_SITE + "," + UPDATED_WEB_SITE);

        // Get all the branchList where webSite equals to UPDATED_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.in=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite is not null
        defaultBranchShouldBeFound("webSite.specified=true");

        // Get all the branchList where webSite is null
        defaultBranchShouldNotBeFound("webSite.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite contains DEFAULT_WEB_SITE
        defaultBranchShouldBeFound("webSite.contains=" + DEFAULT_WEB_SITE);

        // Get all the branchList where webSite contains UPDATED_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.contains=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByWebSiteNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where webSite does not contain DEFAULT_WEB_SITE
        defaultBranchShouldNotBeFound("webSite.doesNotContain=" + DEFAULT_WEB_SITE);

        // Get all the branchList where webSite does not contain UPDATED_WEB_SITE
        defaultBranchShouldBeFound("webSite.doesNotContain=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId equals to DEFAULT_BRANCH_ID
        defaultBranchShouldBeFound("branchId.equals=" + DEFAULT_BRANCH_ID);

        // Get all the branchList where branchId equals to UPDATED_BRANCH_ID
        defaultBranchShouldNotBeFound("branchId.equals=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId in DEFAULT_BRANCH_ID or UPDATED_BRANCH_ID
        defaultBranchShouldBeFound("branchId.in=" + DEFAULT_BRANCH_ID + "," + UPDATED_BRANCH_ID);

        // Get all the branchList where branchId equals to UPDATED_BRANCH_ID
        defaultBranchShouldNotBeFound("branchId.in=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId is not null
        defaultBranchShouldBeFound("branchId.specified=true");

        // Get all the branchList where branchId is null
        defaultBranchShouldNotBeFound("branchId.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId is greater than or equal to DEFAULT_BRANCH_ID
        defaultBranchShouldBeFound("branchId.greaterThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the branchList where branchId is greater than or equal to UPDATED_BRANCH_ID
        defaultBranchShouldNotBeFound("branchId.greaterThanOrEqual=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId is less than or equal to DEFAULT_BRANCH_ID
        defaultBranchShouldBeFound("branchId.lessThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the branchList where branchId is less than or equal to SMALLER_BRANCH_ID
        defaultBranchShouldNotBeFound("branchId.lessThanOrEqual=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId is less than DEFAULT_BRANCH_ID
        defaultBranchShouldNotBeFound("branchId.lessThan=" + DEFAULT_BRANCH_ID);

        // Get all the branchList where branchId is less than UPDATED_BRANCH_ID
        defaultBranchShouldBeFound("branchId.lessThan=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByBranchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where branchId is greater than DEFAULT_BRANCH_ID
        defaultBranchShouldNotBeFound("branchId.greaterThan=" + DEFAULT_BRANCH_ID);

        // Get all the branchList where branchId is greater than SMALLER_BRANCH_ID
        defaultBranchShouldBeFound("branchId.greaterThan=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId equals to DEFAULT_REGION_ID
        defaultBranchShouldBeFound("regionId.equals=" + DEFAULT_REGION_ID);

        // Get all the branchList where regionId equals to UPDATED_REGION_ID
        defaultBranchShouldNotBeFound("regionId.equals=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId in DEFAULT_REGION_ID or UPDATED_REGION_ID
        defaultBranchShouldBeFound("regionId.in=" + DEFAULT_REGION_ID + "," + UPDATED_REGION_ID);

        // Get all the branchList where regionId equals to UPDATED_REGION_ID
        defaultBranchShouldNotBeFound("regionId.in=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId is not null
        defaultBranchShouldBeFound("regionId.specified=true");

        // Get all the branchList where regionId is null
        defaultBranchShouldNotBeFound("regionId.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId is greater than or equal to DEFAULT_REGION_ID
        defaultBranchShouldBeFound("regionId.greaterThanOrEqual=" + DEFAULT_REGION_ID);

        // Get all the branchList where regionId is greater than or equal to UPDATED_REGION_ID
        defaultBranchShouldNotBeFound("regionId.greaterThanOrEqual=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId is less than or equal to DEFAULT_REGION_ID
        defaultBranchShouldBeFound("regionId.lessThanOrEqual=" + DEFAULT_REGION_ID);

        // Get all the branchList where regionId is less than or equal to SMALLER_REGION_ID
        defaultBranchShouldNotBeFound("regionId.lessThanOrEqual=" + SMALLER_REGION_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId is less than DEFAULT_REGION_ID
        defaultBranchShouldNotBeFound("regionId.lessThan=" + DEFAULT_REGION_ID);

        // Get all the branchList where regionId is less than UPDATED_REGION_ID
        defaultBranchShouldBeFound("regionId.lessThan=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByRegionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where regionId is greater than DEFAULT_REGION_ID
        defaultBranchShouldNotBeFound("regionId.greaterThan=" + DEFAULT_REGION_ID);

        // Get all the branchList where regionId is greater than SMALLER_REGION_ID
        defaultBranchShouldBeFound("regionId.greaterThan=" + SMALLER_REGION_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId equals to DEFAULT_COMPANY_ID
        defaultBranchShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the branchList where companyId equals to UPDATED_COMPANY_ID
        defaultBranchShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultBranchShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the branchList where companyId equals to UPDATED_COMPANY_ID
        defaultBranchShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId is not null
        defaultBranchShouldBeFound("companyId.specified=true");

        // Get all the branchList where companyId is null
        defaultBranchShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultBranchShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the branchList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultBranchShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultBranchShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the branchList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultBranchShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId is less than DEFAULT_COMPANY_ID
        defaultBranchShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the branchList where companyId is less than UPDATED_COMPANY_ID
        defaultBranchShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where companyId is greater than DEFAULT_COMPANY_ID
        defaultBranchShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the branchList where companyId is greater than SMALLER_COMPANY_ID
        defaultBranchShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBranchesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where status equals to DEFAULT_STATUS
        defaultBranchShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the branchList where status equals to UPDATED_STATUS
        defaultBranchShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBranchesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBranchShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the branchList where status equals to UPDATED_STATUS
        defaultBranchShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBranchesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where status is not null
        defaultBranchShouldBeFound("status.specified=true");

        // Get all the branchList where status is null
        defaultBranchShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByStatusContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where status contains DEFAULT_STATUS
        defaultBranchShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the branchList where status contains UPDATED_STATUS
        defaultBranchShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBranchesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where status does not contain DEFAULT_STATUS
        defaultBranchShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the branchList where status does not contain UPDATED_STATUS
        defaultBranchShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBranchShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the branchList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBranchShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBranchShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the branchList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBranchShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModified is not null
        defaultBranchShouldBeFound("lastModified.specified=true");

        // Get all the branchList where lastModified is null
        defaultBranchShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy is not null
        defaultBranchShouldBeFound("lastModifiedBy.specified=true");

        // Get all the branchList where lastModifiedBy is null
        defaultBranchShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBranchesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBranchShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the branchList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBranchShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBranchShouldBeFound(String filter) throws Exception {
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].branchcode").value(hasItem(DEFAULT_BRANCHCODE)))
            .andExpect(jsonPath("$.[*].branchType").value(hasItem(DEFAULT_BRANCH_TYPE)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].regionId").value(hasItem(DEFAULT_REGION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBranchShouldNotBeFound(String filter) throws Exception {
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBranchMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBranch() throws Exception {
        // Get the branch
        restBranchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch
        Branch updatedBranch = branchRepository.findById(branch.getId()).get();
        // Disconnect from session so that the updates on updatedBranch are not directly saved in db
        em.detach(updatedBranch);
        updatedBranch
            .branchName(UPDATED_BRANCH_NAME)
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .branchType(UPDATED_BRANCH_TYPE)
            .webSite(UPDATED_WEB_SITE)
            .branchId(UPDATED_BRANCH_ID)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        BranchDTO branchDTO = branchMapper.toDto(updatedBranch);

        restBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, branchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testBranch.getBranchType()).isEqualTo(UPDATED_BRANCH_TYPE);
        assertThat(testBranch.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testBranch.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testBranch.getRegionId()).isEqualTo(UPDATED_REGION_ID);
        assertThat(testBranch.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testBranch.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBranch.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, branchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(branchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBranchWithPatch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch using partial update
        Branch partialUpdatedBranch = new Branch();
        partialUpdatedBranch.setId(branch.getId());

        partialUpdatedBranch
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .webSite(UPDATED_WEB_SITE)
            .branchId(UPDATED_BRANCH_ID)
            .regionId(UPDATED_REGION_ID)//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBranch))
            )
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testBranch.getBranchType()).isEqualTo(DEFAULT_BRANCH_TYPE);
        assertThat(testBranch.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testBranch.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testBranch.getRegionId()).isEqualTo(UPDATED_REGION_ID);
        assertThat(testBranch.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testBranch.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBranch.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBranchWithPatch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch using partial update
        Branch partialUpdatedBranch = new Branch();
        partialUpdatedBranch.setId(branch.getId());

        partialUpdatedBranch
            .branchName(UPDATED_BRANCH_NAME)
            .description(UPDATED_DESCRIPTION)
            .branchcode(UPDATED_BRANCHCODE)
            .branchType(UPDATED_BRANCH_TYPE)
            .webSite(UPDATED_WEB_SITE)
            .branchId(UPDATED_BRANCH_ID)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBranch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBranch))
            )
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBranch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBranch.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testBranch.getBranchType()).isEqualTo(UPDATED_BRANCH_TYPE);
        assertThat(testBranch.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testBranch.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testBranch.getRegionId()).isEqualTo(UPDATED_REGION_ID);
        assertThat(testBranch.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testBranch.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBranch.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBranch.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, branchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();
        branch.setId(count.incrementAndGet());

        // Create the Branch
        BranchDTO branchDTO = branchMapper.toDto(branch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBranchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(branchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        int databaseSizeBeforeDelete = branchRepository.findAll().size();

        // Delete the branch
        restBranchMockMvc
            .perform(delete(ENTITY_API_URL_ID, branch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
