package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.AssetApplication;
import com.techvg.hrms.repository.AssetApplicationRepository;
import com.techvg.hrms.service.criteria.AssetApplicationCriteria;
import com.techvg.hrms.service.dto.AssetApplicationDTO;
import com.techvg.hrms.service.mapper.AssetApplicationMapper;
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
 * Integration tests for the {@link AssetApplicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetApplicationResourceIT {

    private static final Long DEFAULT_ASSET_ID = 1L;
    private static final Long UPDATED_ASSET_ID = 2L;
    private static final Long SMALLER_ASSET_ID = 1L - 1L;

    private static final String DEFAULT_ASSETYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSETYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;
    private static final Long SMALLER_QUANTITY = 1L - 1L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REQ_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_REQ_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPLY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ASSGIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSGIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asset-applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssetApplicationRepository assetApplicationRepository;

    @Autowired
    private AssetApplicationMapper assetApplicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetApplicationMockMvc;

    private AssetApplication assetApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetApplication createEntity(EntityManager em) {
        AssetApplication assetApplication = new AssetApplication()
            .assetId(DEFAULT_ASSET_ID)
            .assetype(DEFAULT_ASSETYPE)
            .quantity(DEFAULT_QUANTITY)
            .description(DEFAULT_DESCRIPTION)
            .reqStatus(DEFAULT_REQ_STATUS)
            .applyDate(DEFAULT_APPLY_DATE)
            .assginDate(DEFAULT_ASSGIN_DATE)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return assetApplication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetApplication createUpdatedEntity(EntityManager em) {
        AssetApplication assetApplication = new AssetApplication()
            .assetId(UPDATED_ASSET_ID)
            .assetype(UPDATED_ASSETYPE)
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION)
            .reqStatus(UPDATED_REQ_STATUS)
            .applyDate(UPDATED_APPLY_DATE)
            .assginDate(UPDATED_ASSGIN_DATE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return assetApplication;
    }

    @BeforeEach
    public void initTest() {
        assetApplication = createEntity(em);
    }

    @Test
    @Transactional
    void createAssetApplication() throws Exception {
        int databaseSizeBeforeCreate = assetApplicationRepository.findAll().size();
        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);
        restAssetApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        AssetApplication testAssetApplication = assetApplicationList.get(assetApplicationList.size() - 1);
        assertThat(testAssetApplication.getAssetId()).isEqualTo(DEFAULT_ASSET_ID);
        assertThat(testAssetApplication.getAssetype()).isEqualTo(DEFAULT_ASSETYPE);
        assertThat(testAssetApplication.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAssetApplication.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetApplication.getReqStatus()).isEqualTo(DEFAULT_REQ_STATUS);
        assertThat(testAssetApplication.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testAssetApplication.getAssginDate()).isEqualTo(DEFAULT_ASSGIN_DATE);
        assertThat(testAssetApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetApplication.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAssetApplication.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAssetApplication.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAssetApplication.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAssetApplicationWithExistingId() throws Exception {
        // Create the AssetApplication with an existing ID
        assetApplication.setId(1L);
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        int databaseSizeBeforeCreate = assetApplicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetApplications() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList
        restAssetApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetId").value(hasItem(DEFAULT_ASSET_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetype").value(hasItem(DEFAULT_ASSETYPE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].reqStatus").value(hasItem(DEFAULT_REQ_STATUS)))
            .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
            .andExpect(jsonPath("$.[*].assginDate").value(hasItem(DEFAULT_ASSGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAssetApplication() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get the assetApplication
        restAssetApplicationMockMvc
            .perform(get(ENTITY_API_URL_ID, assetApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetApplication.getId().intValue()))
            .andExpect(jsonPath("$.assetId").value(DEFAULT_ASSET_ID.intValue()))
            .andExpect(jsonPath("$.assetype").value(DEFAULT_ASSETYPE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.reqStatus").value(DEFAULT_REQ_STATUS))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()))
            .andExpect(jsonPath("$.assginDate").value(DEFAULT_ASSGIN_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAssetApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        Long id = assetApplication.getId();

        defaultAssetApplicationShouldBeFound("id.equals=" + id);
        defaultAssetApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultAssetApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAssetApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultAssetApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAssetApplicationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId equals to DEFAULT_ASSET_ID
        defaultAssetApplicationShouldBeFound("assetId.equals=" + DEFAULT_ASSET_ID);

        // Get all the assetApplicationList where assetId equals to UPDATED_ASSET_ID
        defaultAssetApplicationShouldNotBeFound("assetId.equals=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId in DEFAULT_ASSET_ID or UPDATED_ASSET_ID
        defaultAssetApplicationShouldBeFound("assetId.in=" + DEFAULT_ASSET_ID + "," + UPDATED_ASSET_ID);

        // Get all the assetApplicationList where assetId equals to UPDATED_ASSET_ID
        defaultAssetApplicationShouldNotBeFound("assetId.in=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId is not null
        defaultAssetApplicationShouldBeFound("assetId.specified=true");

        // Get all the assetApplicationList where assetId is null
        defaultAssetApplicationShouldNotBeFound("assetId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId is greater than or equal to DEFAULT_ASSET_ID
        defaultAssetApplicationShouldBeFound("assetId.greaterThanOrEqual=" + DEFAULT_ASSET_ID);

        // Get all the assetApplicationList where assetId is greater than or equal to UPDATED_ASSET_ID
        defaultAssetApplicationShouldNotBeFound("assetId.greaterThanOrEqual=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId is less than or equal to DEFAULT_ASSET_ID
        defaultAssetApplicationShouldBeFound("assetId.lessThanOrEqual=" + DEFAULT_ASSET_ID);

        // Get all the assetApplicationList where assetId is less than or equal to SMALLER_ASSET_ID
        defaultAssetApplicationShouldNotBeFound("assetId.lessThanOrEqual=" + SMALLER_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId is less than DEFAULT_ASSET_ID
        defaultAssetApplicationShouldNotBeFound("assetId.lessThan=" + DEFAULT_ASSET_ID);

        // Get all the assetApplicationList where assetId is less than UPDATED_ASSET_ID
        defaultAssetApplicationShouldBeFound("assetId.lessThan=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetId is greater than DEFAULT_ASSET_ID
        defaultAssetApplicationShouldNotBeFound("assetId.greaterThan=" + DEFAULT_ASSET_ID);

        // Get all the assetApplicationList where assetId is greater than SMALLER_ASSET_ID
        defaultAssetApplicationShouldBeFound("assetId.greaterThan=" + SMALLER_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetype equals to DEFAULT_ASSETYPE
        defaultAssetApplicationShouldBeFound("assetype.equals=" + DEFAULT_ASSETYPE);

        // Get all the assetApplicationList where assetype equals to UPDATED_ASSETYPE
        defaultAssetApplicationShouldNotBeFound("assetype.equals=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetypeIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetype in DEFAULT_ASSETYPE or UPDATED_ASSETYPE
        defaultAssetApplicationShouldBeFound("assetype.in=" + DEFAULT_ASSETYPE + "," + UPDATED_ASSETYPE);

        // Get all the assetApplicationList where assetype equals to UPDATED_ASSETYPE
        defaultAssetApplicationShouldNotBeFound("assetype.in=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetype is not null
        defaultAssetApplicationShouldBeFound("assetype.specified=true");

        // Get all the assetApplicationList where assetype is null
        defaultAssetApplicationShouldNotBeFound("assetype.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetypeContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetype contains DEFAULT_ASSETYPE
        defaultAssetApplicationShouldBeFound("assetype.contains=" + DEFAULT_ASSETYPE);

        // Get all the assetApplicationList where assetype contains UPDATED_ASSETYPE
        defaultAssetApplicationShouldNotBeFound("assetype.contains=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssetypeNotContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assetype does not contain DEFAULT_ASSETYPE
        defaultAssetApplicationShouldNotBeFound("assetype.doesNotContain=" + DEFAULT_ASSETYPE);

        // Get all the assetApplicationList where assetype does not contain UPDATED_ASSETYPE
        defaultAssetApplicationShouldBeFound("assetype.doesNotContain=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity equals to DEFAULT_QUANTITY
        defaultAssetApplicationShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the assetApplicationList where quantity equals to UPDATED_QUANTITY
        defaultAssetApplicationShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultAssetApplicationShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the assetApplicationList where quantity equals to UPDATED_QUANTITY
        defaultAssetApplicationShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity is not null
        defaultAssetApplicationShouldBeFound("quantity.specified=true");

        // Get all the assetApplicationList where quantity is null
        defaultAssetApplicationShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultAssetApplicationShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the assetApplicationList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultAssetApplicationShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultAssetApplicationShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the assetApplicationList where quantity is less than or equal to SMALLER_QUANTITY
        defaultAssetApplicationShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity is less than DEFAULT_QUANTITY
        defaultAssetApplicationShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the assetApplicationList where quantity is less than UPDATED_QUANTITY
        defaultAssetApplicationShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where quantity is greater than DEFAULT_QUANTITY
        defaultAssetApplicationShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the assetApplicationList where quantity is greater than SMALLER_QUANTITY
        defaultAssetApplicationShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where description equals to DEFAULT_DESCRIPTION
        defaultAssetApplicationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the assetApplicationList where description equals to UPDATED_DESCRIPTION
        defaultAssetApplicationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAssetApplicationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the assetApplicationList where description equals to UPDATED_DESCRIPTION
        defaultAssetApplicationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where description is not null
        defaultAssetApplicationShouldBeFound("description.specified=true");

        // Get all the assetApplicationList where description is null
        defaultAssetApplicationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where description contains DEFAULT_DESCRIPTION
        defaultAssetApplicationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the assetApplicationList where description contains UPDATED_DESCRIPTION
        defaultAssetApplicationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where description does not contain DEFAULT_DESCRIPTION
        defaultAssetApplicationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the assetApplicationList where description does not contain UPDATED_DESCRIPTION
        defaultAssetApplicationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByReqStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where reqStatus equals to DEFAULT_REQ_STATUS
        defaultAssetApplicationShouldBeFound("reqStatus.equals=" + DEFAULT_REQ_STATUS);

        // Get all the assetApplicationList where reqStatus equals to UPDATED_REQ_STATUS
        defaultAssetApplicationShouldNotBeFound("reqStatus.equals=" + UPDATED_REQ_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByReqStatusIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where reqStatus in DEFAULT_REQ_STATUS or UPDATED_REQ_STATUS
        defaultAssetApplicationShouldBeFound("reqStatus.in=" + DEFAULT_REQ_STATUS + "," + UPDATED_REQ_STATUS);

        // Get all the assetApplicationList where reqStatus equals to UPDATED_REQ_STATUS
        defaultAssetApplicationShouldNotBeFound("reqStatus.in=" + UPDATED_REQ_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByReqStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where reqStatus is not null
        defaultAssetApplicationShouldBeFound("reqStatus.specified=true");

        // Get all the assetApplicationList where reqStatus is null
        defaultAssetApplicationShouldNotBeFound("reqStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByReqStatusContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where reqStatus contains DEFAULT_REQ_STATUS
        defaultAssetApplicationShouldBeFound("reqStatus.contains=" + DEFAULT_REQ_STATUS);

        // Get all the assetApplicationList where reqStatus contains UPDATED_REQ_STATUS
        defaultAssetApplicationShouldNotBeFound("reqStatus.contains=" + UPDATED_REQ_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByReqStatusNotContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where reqStatus does not contain DEFAULT_REQ_STATUS
        defaultAssetApplicationShouldNotBeFound("reqStatus.doesNotContain=" + DEFAULT_REQ_STATUS);

        // Get all the assetApplicationList where reqStatus does not contain UPDATED_REQ_STATUS
        defaultAssetApplicationShouldBeFound("reqStatus.doesNotContain=" + UPDATED_REQ_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByApplyDateIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where applyDate equals to DEFAULT_APPLY_DATE
        defaultAssetApplicationShouldBeFound("applyDate.equals=" + DEFAULT_APPLY_DATE);

        // Get all the assetApplicationList where applyDate equals to UPDATED_APPLY_DATE
        defaultAssetApplicationShouldNotBeFound("applyDate.equals=" + UPDATED_APPLY_DATE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByApplyDateIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where applyDate in DEFAULT_APPLY_DATE or UPDATED_APPLY_DATE
        defaultAssetApplicationShouldBeFound("applyDate.in=" + DEFAULT_APPLY_DATE + "," + UPDATED_APPLY_DATE);

        // Get all the assetApplicationList where applyDate equals to UPDATED_APPLY_DATE
        defaultAssetApplicationShouldNotBeFound("applyDate.in=" + UPDATED_APPLY_DATE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByApplyDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where applyDate is not null
        defaultAssetApplicationShouldBeFound("applyDate.specified=true");

        // Get all the assetApplicationList where applyDate is null
        defaultAssetApplicationShouldNotBeFound("applyDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssginDateIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assginDate equals to DEFAULT_ASSGIN_DATE
        defaultAssetApplicationShouldBeFound("assginDate.equals=" + DEFAULT_ASSGIN_DATE);

        // Get all the assetApplicationList where assginDate equals to UPDATED_ASSGIN_DATE
        defaultAssetApplicationShouldNotBeFound("assginDate.equals=" + UPDATED_ASSGIN_DATE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssginDateIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assginDate in DEFAULT_ASSGIN_DATE or UPDATED_ASSGIN_DATE
        defaultAssetApplicationShouldBeFound("assginDate.in=" + DEFAULT_ASSGIN_DATE + "," + UPDATED_ASSGIN_DATE);

        // Get all the assetApplicationList where assginDate equals to UPDATED_ASSGIN_DATE
        defaultAssetApplicationShouldNotBeFound("assginDate.in=" + UPDATED_ASSGIN_DATE);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByAssginDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where assginDate is not null
        defaultAssetApplicationShouldBeFound("assginDate.specified=true");

        // Get all the assetApplicationList where assginDate is null
        defaultAssetApplicationShouldNotBeFound("assginDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where status equals to DEFAULT_STATUS
        defaultAssetApplicationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the assetApplicationList where status equals to UPDATED_STATUS
        defaultAssetApplicationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAssetApplicationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the assetApplicationList where status equals to UPDATED_STATUS
        defaultAssetApplicationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where status is not null
        defaultAssetApplicationShouldBeFound("status.specified=true");

        // Get all the assetApplicationList where status is null
        defaultAssetApplicationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where status contains DEFAULT_STATUS
        defaultAssetApplicationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the assetApplicationList where status contains UPDATED_STATUS
        defaultAssetApplicationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where status does not contain DEFAULT_STATUS
        defaultAssetApplicationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the assetApplicationList where status does not contain UPDATED_STATUS
        defaultAssetApplicationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultAssetApplicationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the assetApplicationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAssetApplicationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultAssetApplicationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the assetApplicationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAssetApplicationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId is not null
        defaultAssetApplicationShouldBeFound("employeeId.specified=true");

        // Get all the assetApplicationList where employeeId is null
        defaultAssetApplicationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultAssetApplicationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the assetApplicationList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultAssetApplicationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultAssetApplicationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the assetApplicationList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultAssetApplicationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultAssetApplicationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the assetApplicationList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultAssetApplicationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultAssetApplicationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the assetApplicationList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultAssetApplicationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId equals to DEFAULT_COMPANY_ID
        defaultAssetApplicationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the assetApplicationList where companyId equals to UPDATED_COMPANY_ID
        defaultAssetApplicationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultAssetApplicationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the assetApplicationList where companyId equals to UPDATED_COMPANY_ID
        defaultAssetApplicationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId is not null
        defaultAssetApplicationShouldBeFound("companyId.specified=true");

        // Get all the assetApplicationList where companyId is null
        defaultAssetApplicationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultAssetApplicationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the assetApplicationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultAssetApplicationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultAssetApplicationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the assetApplicationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultAssetApplicationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId is less than DEFAULT_COMPANY_ID
        defaultAssetApplicationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the assetApplicationList where companyId is less than UPDATED_COMPANY_ID
        defaultAssetApplicationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultAssetApplicationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the assetApplicationList where companyId is greater than SMALLER_COMPANY_ID
        defaultAssetApplicationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAssetApplicationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the assetApplicationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAssetApplicationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAssetApplicationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the assetApplicationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAssetApplicationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModified is not null
        defaultAssetApplicationShouldBeFound("lastModified.specified=true");

        // Get all the assetApplicationList where lastModified is null
        defaultAssetApplicationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAssetApplicationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assetApplicationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAssetApplicationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAssetApplicationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the assetApplicationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAssetApplicationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModifiedBy is not null
        defaultAssetApplicationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the assetApplicationList where lastModifiedBy is null
        defaultAssetApplicationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAssetApplicationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assetApplicationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAssetApplicationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssetApplicationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        // Get all the assetApplicationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAssetApplicationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the assetApplicationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAssetApplicationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetApplicationShouldBeFound(String filter) throws Exception {
        restAssetApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetId").value(hasItem(DEFAULT_ASSET_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetype").value(hasItem(DEFAULT_ASSETYPE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].reqStatus").value(hasItem(DEFAULT_REQ_STATUS)))
            .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
            .andExpect(jsonPath("$.[*].assginDate").value(hasItem(DEFAULT_ASSGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAssetApplicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetApplicationShouldNotBeFound(String filter) throws Exception {
        restAssetApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetApplicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAssetApplication() throws Exception {
        // Get the assetApplication
        restAssetApplicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetApplication() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();

        // Update the assetApplication
        AssetApplication updatedAssetApplication = assetApplicationRepository.findById(assetApplication.getId()).get();
        // Disconnect from session so that the updates on updatedAssetApplication are not directly saved in db
        em.detach(updatedAssetApplication);
        updatedAssetApplication
            .assetId(UPDATED_ASSET_ID)
            .assetype(UPDATED_ASSETYPE)
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION)
            .reqStatus(UPDATED_REQ_STATUS)
            .applyDate(UPDATED_APPLY_DATE)
            .assginDate(UPDATED_ASSGIN_DATE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(updatedAssetApplication);

        restAssetApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetApplicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
        AssetApplication testAssetApplication = assetApplicationList.get(assetApplicationList.size() - 1);
        assertThat(testAssetApplication.getAssetId()).isEqualTo(UPDATED_ASSET_ID);
        assertThat(testAssetApplication.getAssetype()).isEqualTo(UPDATED_ASSETYPE);
        assertThat(testAssetApplication.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAssetApplication.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetApplication.getReqStatus()).isEqualTo(UPDATED_REQ_STATUS);
        assertThat(testAssetApplication.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testAssetApplication.getAssginDate()).isEqualTo(UPDATED_ASSGIN_DATE);
        assertThat(testAssetApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetApplication.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAssetApplication.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAssetApplication.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAssetApplication.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAssetApplication() throws Exception {
        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();
        assetApplication.setId(count.incrementAndGet());

        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetApplicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetApplication() throws Exception {
        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();
        assetApplication.setId(count.incrementAndGet());

        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetApplication() throws Exception {
        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();
        assetApplication.setId(count.incrementAndGet());

        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetApplicationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetApplicationWithPatch() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();

        // Update the assetApplication using partial update
        AssetApplication partialUpdatedAssetApplication = new AssetApplication();
        partialUpdatedAssetApplication.setId(assetApplication.getId());

        partialUpdatedAssetApplication.description(UPDATED_DESCRIPTION).reqStatus(UPDATED_REQ_STATUS).status(UPDATED_STATUS);

        restAssetApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetApplication))
            )
            .andExpect(status().isOk());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
        AssetApplication testAssetApplication = assetApplicationList.get(assetApplicationList.size() - 1);
        assertThat(testAssetApplication.getAssetId()).isEqualTo(DEFAULT_ASSET_ID);
        assertThat(testAssetApplication.getAssetype()).isEqualTo(DEFAULT_ASSETYPE);
        assertThat(testAssetApplication.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAssetApplication.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetApplication.getReqStatus()).isEqualTo(UPDATED_REQ_STATUS);
        assertThat(testAssetApplication.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testAssetApplication.getAssginDate()).isEqualTo(DEFAULT_ASSGIN_DATE);
        assertThat(testAssetApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetApplication.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAssetApplication.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testAssetApplication.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAssetApplication.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAssetApplicationWithPatch() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();

        // Update the assetApplication using partial update
        AssetApplication partialUpdatedAssetApplication = new AssetApplication();
        partialUpdatedAssetApplication.setId(assetApplication.getId());

        partialUpdatedAssetApplication
            .assetId(UPDATED_ASSET_ID)
            .assetype(UPDATED_ASSETYPE)
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION)
            .reqStatus(UPDATED_REQ_STATUS)
            .applyDate(UPDATED_APPLY_DATE)
            .assginDate(UPDATED_ASSGIN_DATE)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAssetApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetApplication))
            )
            .andExpect(status().isOk());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
        AssetApplication testAssetApplication = assetApplicationList.get(assetApplicationList.size() - 1);
        assertThat(testAssetApplication.getAssetId()).isEqualTo(UPDATED_ASSET_ID);
        assertThat(testAssetApplication.getAssetype()).isEqualTo(UPDATED_ASSETYPE);
        assertThat(testAssetApplication.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAssetApplication.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetApplication.getReqStatus()).isEqualTo(UPDATED_REQ_STATUS);
        assertThat(testAssetApplication.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testAssetApplication.getAssginDate()).isEqualTo(UPDATED_ASSGIN_DATE);
        assertThat(testAssetApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetApplication.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAssetApplication.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testAssetApplication.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAssetApplication.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAssetApplication() throws Exception {
        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();
        assetApplication.setId(count.incrementAndGet());

        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetApplicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetApplication() throws Exception {
        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();
        assetApplication.setId(count.incrementAndGet());

        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetApplication() throws Exception {
        int databaseSizeBeforeUpdate = assetApplicationRepository.findAll().size();
        assetApplication.setId(count.incrementAndGet());

        // Create the AssetApplication
        AssetApplicationDTO assetApplicationDTO = assetApplicationMapper.toDto(assetApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetApplicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetApplication in the database
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetApplication() throws Exception {
        // Initialize the database
        assetApplicationRepository.saveAndFlush(assetApplication);

        int databaseSizeBeforeDelete = assetApplicationRepository.findAll().size();

        // Delete the assetApplication
        restAssetApplicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetApplication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetApplication> assetApplicationList = assetApplicationRepository.findAll();
        assertThat(assetApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
