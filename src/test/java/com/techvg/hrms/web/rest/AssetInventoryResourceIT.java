package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.AssetInventory;
import com.techvg.hrms.repository.AssetInventoryRepository;
import com.techvg.hrms.service.criteria.AssetInventoryCriteria;
import com.techvg.hrms.service.dto.AssetInventoryDTO;
import com.techvg.hrms.service.mapper.AssetInventoryMapper;
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
 * Integration tests for the {@link AssetInventoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetInventoryResourceIT {

    private static final String DEFAULT_ASSET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSETYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSETYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_ID = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_FROM = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_TO = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_TO = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER = "BBBBBBBBBB";

    private static final Long DEFAULT_WARRANTY_IN_MONTHS = 1L;
    private static final Long UPDATED_WARRANTY_IN_MONTHS = 2L;
    private static final Long SMALLER_WARRANTY_IN_MONTHS = 1L - 1L;

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;
    private static final Double SMALLER_VALUE = 1D - 1D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSET_USER_ID = 1L;
    private static final Long UPDATED_ASSET_USER_ID = 2L;
    private static final Long SMALLER_ASSET_USER_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_SUBMITTED_AMT = 1D;
    private static final Double UPDATED_SUBMITTED_AMT = 2D;
    private static final Double SMALLER_SUBMITTED_AMT = 1D - 1D;

    private static final Double DEFAULT_REFUND_AMT = 1D;
    private static final Double UPDATED_REFUND_AMT = 2D;
    private static final Double SMALLER_REFUND_AMT = 1D - 1D;

    private static final Double DEFAULT_FINE_AMT = 1D;
    private static final Double UPDATED_FINE_AMT = 2D;
    private static final Double SMALLER_FINE_AMT = 1D - 1D;

//    private static final Long DEFAULT_COMPANY_ID = 1L;
//    private static final Long UPDATED_COMPANY_ID = 2L;
//    private static final Long SMALLER_COMPANY_ID = 1L - 1L;
//
//    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asset-inventories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssetInventoryRepository assetInventoryRepository;

    @Autowired
    private AssetInventoryMapper assetInventoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetInventoryMockMvc;

    private AssetInventory assetInventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetInventory createEntity(EntityManager em) {
        AssetInventory assetInventory = new AssetInventory()
            .assetName(DEFAULT_ASSET_NAME)
            .assetype(DEFAULT_ASSETYPE)
            .assetId(DEFAULT_ASSET_ID)
            .purchaseFrom(DEFAULT_PURCHASE_FROM)
            .purchaseTo(DEFAULT_PURCHASE_TO)
            .manufacturer(DEFAULT_MANUFACTURER)
            .model(DEFAULT_MODEL)
            .productNumber(DEFAULT_PRODUCT_NUMBER)
            .supplier(DEFAULT_SUPPLIER)
            .warrantyInMonths(DEFAULT_WARRANTY_IN_MONTHS)
            .condition(DEFAULT_CONDITION)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .assetStatus(DEFAULT_ASSET_STATUS)
            .assetUserId(DEFAULT_ASSET_USER_ID)
            .status(DEFAULT_STATUS)
            .submittedAmt(DEFAULT_SUBMITTED_AMT)
            .refundAmt(DEFAULT_REFUND_AMT)
            .fineAmt(DEFAULT_FINE_AMT);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return assetInventory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetInventory createUpdatedEntity(EntityManager em) {
        AssetInventory assetInventory = new AssetInventory()
            .assetName(UPDATED_ASSET_NAME)
            .assetype(UPDATED_ASSETYPE)
            .assetId(UPDATED_ASSET_ID)
            .purchaseFrom(UPDATED_PURCHASE_FROM)
            .purchaseTo(UPDATED_PURCHASE_TO)
            .manufacturer(UPDATED_MANUFACTURER)
            .model(UPDATED_MODEL)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .supplier(UPDATED_SUPPLIER)
            .warrantyInMonths(UPDATED_WARRANTY_IN_MONTHS)
            .condition(UPDATED_CONDITION)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .assetStatus(UPDATED_ASSET_STATUS)
            .assetUserId(UPDATED_ASSET_USER_ID)
            .status(UPDATED_STATUS)
            .submittedAmt(UPDATED_SUBMITTED_AMT)
            .refundAmt(UPDATED_REFUND_AMT)
            .fineAmt(UPDATED_FINE_AMT);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return assetInventory;
    }

    @BeforeEach
    public void initTest() {
        assetInventory = createEntity(em);
    }

    @Test
    @Transactional
    void createAssetInventory() throws Exception {
        int databaseSizeBeforeCreate = assetInventoryRepository.findAll().size();
        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);
        restAssetInventoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        AssetInventory testAssetInventory = assetInventoryList.get(assetInventoryList.size() - 1);
        assertThat(testAssetInventory.getAssetName()).isEqualTo(DEFAULT_ASSET_NAME);
        assertThat(testAssetInventory.getAssetype()).isEqualTo(DEFAULT_ASSETYPE);
        assertThat(testAssetInventory.getAssetId()).isEqualTo(DEFAULT_ASSET_ID);
        assertThat(testAssetInventory.getPurchaseFrom()).isEqualTo(DEFAULT_PURCHASE_FROM);
        assertThat(testAssetInventory.getPurchaseTo()).isEqualTo(DEFAULT_PURCHASE_TO);
        assertThat(testAssetInventory.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testAssetInventory.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testAssetInventory.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testAssetInventory.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testAssetInventory.getWarrantyInMonths()).isEqualTo(DEFAULT_WARRANTY_IN_MONTHS);
        assertThat(testAssetInventory.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testAssetInventory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAssetInventory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetInventory.getAssetStatus()).isEqualTo(DEFAULT_ASSET_STATUS);
        assertThat(testAssetInventory.getAssetUserId()).isEqualTo(DEFAULT_ASSET_USER_ID);
        assertThat(testAssetInventory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetInventory.getSubmittedAmt()).isEqualTo(DEFAULT_SUBMITTED_AMT);
        assertThat(testAssetInventory.getRefundAmt()).isEqualTo(DEFAULT_REFUND_AMT);
        assertThat(testAssetInventory.getFineAmt()).isEqualTo(DEFAULT_FINE_AMT);
//        assertThat(testAssetInventory.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
//        assertThat(testAssetInventory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
//        assertThat(testAssetInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAssetInventoryWithExistingId() throws Exception {
        // Create the AssetInventory with an existing ID
        assetInventory.setId(1L);
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        int databaseSizeBeforeCreate = assetInventoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetInventoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetInventories() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList
        restAssetInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetName").value(hasItem(DEFAULT_ASSET_NAME)))
            .andExpect(jsonPath("$.[*].assetype").value(hasItem(DEFAULT_ASSETYPE)))
            .andExpect(jsonPath("$.[*].assetId").value(hasItem(DEFAULT_ASSET_ID)))
            .andExpect(jsonPath("$.[*].purchaseFrom").value(hasItem(DEFAULT_PURCHASE_FROM)))
            .andExpect(jsonPath("$.[*].purchaseTo").value(hasItem(DEFAULT_PURCHASE_TO)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].warrantyInMonths").value(hasItem(DEFAULT_WARRANTY_IN_MONTHS.intValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].assetStatus").value(hasItem(DEFAULT_ASSET_STATUS)))
            .andExpect(jsonPath("$.[*].assetUserId").value(hasItem(DEFAULT_ASSET_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].submittedAmt").value(hasItem(DEFAULT_SUBMITTED_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmt").value(hasItem(DEFAULT_REFUND_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].fineAmt").value(hasItem(DEFAULT_FINE_AMT.doubleValue())));
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAssetInventory() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get the assetInventory
        restAssetInventoryMockMvc
            .perform(get(ENTITY_API_URL_ID, assetInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetInventory.getId().intValue()))
            .andExpect(jsonPath("$.assetName").value(DEFAULT_ASSET_NAME))
            .andExpect(jsonPath("$.assetype").value(DEFAULT_ASSETYPE))
            .andExpect(jsonPath("$.assetId").value(DEFAULT_ASSET_ID))
            .andExpect(jsonPath("$.purchaseFrom").value(DEFAULT_PURCHASE_FROM))
            .andExpect(jsonPath("$.purchaseTo").value(DEFAULT_PURCHASE_TO))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.productNumber").value(DEFAULT_PRODUCT_NUMBER))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER))
            .andExpect(jsonPath("$.warrantyInMonths").value(DEFAULT_WARRANTY_IN_MONTHS.intValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.assetStatus").value(DEFAULT_ASSET_STATUS))
            .andExpect(jsonPath("$.assetUserId").value(DEFAULT_ASSET_USER_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.submittedAmt").value(DEFAULT_SUBMITTED_AMT.doubleValue()))
            .andExpect(jsonPath("$.refundAmt").value(DEFAULT_REFUND_AMT.doubleValue()))
            .andExpect(jsonPath("$.fineAmt").value(DEFAULT_FINE_AMT.doubleValue()));
//            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
//            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAssetInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        Long id = assetInventory.getId();

        defaultAssetInventoryShouldBeFound("id.equals=" + id);
        defaultAssetInventoryShouldNotBeFound("id.notEquals=" + id);

        defaultAssetInventoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAssetInventoryShouldNotBeFound("id.greaterThan=" + id);

        defaultAssetInventoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAssetInventoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetNameIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetName equals to DEFAULT_ASSET_NAME
        defaultAssetInventoryShouldBeFound("assetName.equals=" + DEFAULT_ASSET_NAME);

        // Get all the assetInventoryList where assetName equals to UPDATED_ASSET_NAME
        defaultAssetInventoryShouldNotBeFound("assetName.equals=" + UPDATED_ASSET_NAME);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetNameIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetName in DEFAULT_ASSET_NAME or UPDATED_ASSET_NAME
        defaultAssetInventoryShouldBeFound("assetName.in=" + DEFAULT_ASSET_NAME + "," + UPDATED_ASSET_NAME);

        // Get all the assetInventoryList where assetName equals to UPDATED_ASSET_NAME
        defaultAssetInventoryShouldNotBeFound("assetName.in=" + UPDATED_ASSET_NAME);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetName is not null
        defaultAssetInventoryShouldBeFound("assetName.specified=true");

        // Get all the assetInventoryList where assetName is null
        defaultAssetInventoryShouldNotBeFound("assetName.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetNameContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetName contains DEFAULT_ASSET_NAME
        defaultAssetInventoryShouldBeFound("assetName.contains=" + DEFAULT_ASSET_NAME);

        // Get all the assetInventoryList where assetName contains UPDATED_ASSET_NAME
        defaultAssetInventoryShouldNotBeFound("assetName.contains=" + UPDATED_ASSET_NAME);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetNameNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetName does not contain DEFAULT_ASSET_NAME
        defaultAssetInventoryShouldNotBeFound("assetName.doesNotContain=" + DEFAULT_ASSET_NAME);

        // Get all the assetInventoryList where assetName does not contain UPDATED_ASSET_NAME
        defaultAssetInventoryShouldBeFound("assetName.doesNotContain=" + UPDATED_ASSET_NAME);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetype equals to DEFAULT_ASSETYPE
        defaultAssetInventoryShouldBeFound("assetype.equals=" + DEFAULT_ASSETYPE);

        // Get all the assetInventoryList where assetype equals to UPDATED_ASSETYPE
        defaultAssetInventoryShouldNotBeFound("assetype.equals=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetypeIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetype in DEFAULT_ASSETYPE or UPDATED_ASSETYPE
        defaultAssetInventoryShouldBeFound("assetype.in=" + DEFAULT_ASSETYPE + "," + UPDATED_ASSETYPE);

        // Get all the assetInventoryList where assetype equals to UPDATED_ASSETYPE
        defaultAssetInventoryShouldNotBeFound("assetype.in=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetype is not null
        defaultAssetInventoryShouldBeFound("assetype.specified=true");

        // Get all the assetInventoryList where assetype is null
        defaultAssetInventoryShouldNotBeFound("assetype.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetypeContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetype contains DEFAULT_ASSETYPE
        defaultAssetInventoryShouldBeFound("assetype.contains=" + DEFAULT_ASSETYPE);

        // Get all the assetInventoryList where assetype contains UPDATED_ASSETYPE
        defaultAssetInventoryShouldNotBeFound("assetype.contains=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetypeNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetype does not contain DEFAULT_ASSETYPE
        defaultAssetInventoryShouldNotBeFound("assetype.doesNotContain=" + DEFAULT_ASSETYPE);

        // Get all the assetInventoryList where assetype does not contain UPDATED_ASSETYPE
        defaultAssetInventoryShouldBeFound("assetype.doesNotContain=" + UPDATED_ASSETYPE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetId equals to DEFAULT_ASSET_ID
        defaultAssetInventoryShouldBeFound("assetId.equals=" + DEFAULT_ASSET_ID);

        // Get all the assetInventoryList where assetId equals to UPDATED_ASSET_ID
        defaultAssetInventoryShouldNotBeFound("assetId.equals=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetId in DEFAULT_ASSET_ID or UPDATED_ASSET_ID
        defaultAssetInventoryShouldBeFound("assetId.in=" + DEFAULT_ASSET_ID + "," + UPDATED_ASSET_ID);

        // Get all the assetInventoryList where assetId equals to UPDATED_ASSET_ID
        defaultAssetInventoryShouldNotBeFound("assetId.in=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetId is not null
        defaultAssetInventoryShouldBeFound("assetId.specified=true");

        // Get all the assetInventoryList where assetId is null
        defaultAssetInventoryShouldNotBeFound("assetId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetIdContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetId contains DEFAULT_ASSET_ID
        defaultAssetInventoryShouldBeFound("assetId.contains=" + DEFAULT_ASSET_ID);

        // Get all the assetInventoryList where assetId contains UPDATED_ASSET_ID
        defaultAssetInventoryShouldNotBeFound("assetId.contains=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetIdNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetId does not contain DEFAULT_ASSET_ID
        defaultAssetInventoryShouldNotBeFound("assetId.doesNotContain=" + DEFAULT_ASSET_ID);

        // Get all the assetInventoryList where assetId does not contain UPDATED_ASSET_ID
        defaultAssetInventoryShouldBeFound("assetId.doesNotContain=" + UPDATED_ASSET_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseFromIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseFrom equals to DEFAULT_PURCHASE_FROM
        defaultAssetInventoryShouldBeFound("purchaseFrom.equals=" + DEFAULT_PURCHASE_FROM);

        // Get all the assetInventoryList where purchaseFrom equals to UPDATED_PURCHASE_FROM
        defaultAssetInventoryShouldNotBeFound("purchaseFrom.equals=" + UPDATED_PURCHASE_FROM);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseFromIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseFrom in DEFAULT_PURCHASE_FROM or UPDATED_PURCHASE_FROM
        defaultAssetInventoryShouldBeFound("purchaseFrom.in=" + DEFAULT_PURCHASE_FROM + "," + UPDATED_PURCHASE_FROM);

        // Get all the assetInventoryList where purchaseFrom equals to UPDATED_PURCHASE_FROM
        defaultAssetInventoryShouldNotBeFound("purchaseFrom.in=" + UPDATED_PURCHASE_FROM);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseFrom is not null
        defaultAssetInventoryShouldBeFound("purchaseFrom.specified=true");

        // Get all the assetInventoryList where purchaseFrom is null
        defaultAssetInventoryShouldNotBeFound("purchaseFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseFromContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseFrom contains DEFAULT_PURCHASE_FROM
        defaultAssetInventoryShouldBeFound("purchaseFrom.contains=" + DEFAULT_PURCHASE_FROM);

        // Get all the assetInventoryList where purchaseFrom contains UPDATED_PURCHASE_FROM
        defaultAssetInventoryShouldNotBeFound("purchaseFrom.contains=" + UPDATED_PURCHASE_FROM);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseFromNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseFrom does not contain DEFAULT_PURCHASE_FROM
        defaultAssetInventoryShouldNotBeFound("purchaseFrom.doesNotContain=" + DEFAULT_PURCHASE_FROM);

        // Get all the assetInventoryList where purchaseFrom does not contain UPDATED_PURCHASE_FROM
        defaultAssetInventoryShouldBeFound("purchaseFrom.doesNotContain=" + UPDATED_PURCHASE_FROM);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseToIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseTo equals to DEFAULT_PURCHASE_TO
        defaultAssetInventoryShouldBeFound("purchaseTo.equals=" + DEFAULT_PURCHASE_TO);

        // Get all the assetInventoryList where purchaseTo equals to UPDATED_PURCHASE_TO
        defaultAssetInventoryShouldNotBeFound("purchaseTo.equals=" + UPDATED_PURCHASE_TO);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseToIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseTo in DEFAULT_PURCHASE_TO or UPDATED_PURCHASE_TO
        defaultAssetInventoryShouldBeFound("purchaseTo.in=" + DEFAULT_PURCHASE_TO + "," + UPDATED_PURCHASE_TO);

        // Get all the assetInventoryList where purchaseTo equals to UPDATED_PURCHASE_TO
        defaultAssetInventoryShouldNotBeFound("purchaseTo.in=" + UPDATED_PURCHASE_TO);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseToIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseTo is not null
        defaultAssetInventoryShouldBeFound("purchaseTo.specified=true");

        // Get all the assetInventoryList where purchaseTo is null
        defaultAssetInventoryShouldNotBeFound("purchaseTo.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseToContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseTo contains DEFAULT_PURCHASE_TO
        defaultAssetInventoryShouldBeFound("purchaseTo.contains=" + DEFAULT_PURCHASE_TO);

        // Get all the assetInventoryList where purchaseTo contains UPDATED_PURCHASE_TO
        defaultAssetInventoryShouldNotBeFound("purchaseTo.contains=" + UPDATED_PURCHASE_TO);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByPurchaseToNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where purchaseTo does not contain DEFAULT_PURCHASE_TO
        defaultAssetInventoryShouldNotBeFound("purchaseTo.doesNotContain=" + DEFAULT_PURCHASE_TO);

        // Get all the assetInventoryList where purchaseTo does not contain UPDATED_PURCHASE_TO
        defaultAssetInventoryShouldBeFound("purchaseTo.doesNotContain=" + UPDATED_PURCHASE_TO);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByManufacturerIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where manufacturer equals to DEFAULT_MANUFACTURER
        defaultAssetInventoryShouldBeFound("manufacturer.equals=" + DEFAULT_MANUFACTURER);

        // Get all the assetInventoryList where manufacturer equals to UPDATED_MANUFACTURER
        defaultAssetInventoryShouldNotBeFound("manufacturer.equals=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByManufacturerIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where manufacturer in DEFAULT_MANUFACTURER or UPDATED_MANUFACTURER
        defaultAssetInventoryShouldBeFound("manufacturer.in=" + DEFAULT_MANUFACTURER + "," + UPDATED_MANUFACTURER);

        // Get all the assetInventoryList where manufacturer equals to UPDATED_MANUFACTURER
        defaultAssetInventoryShouldNotBeFound("manufacturer.in=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByManufacturerIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where manufacturer is not null
        defaultAssetInventoryShouldBeFound("manufacturer.specified=true");

        // Get all the assetInventoryList where manufacturer is null
        defaultAssetInventoryShouldNotBeFound("manufacturer.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByManufacturerContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where manufacturer contains DEFAULT_MANUFACTURER
        defaultAssetInventoryShouldBeFound("manufacturer.contains=" + DEFAULT_MANUFACTURER);

        // Get all the assetInventoryList where manufacturer contains UPDATED_MANUFACTURER
        defaultAssetInventoryShouldNotBeFound("manufacturer.contains=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByManufacturerNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where manufacturer does not contain DEFAULT_MANUFACTURER
        defaultAssetInventoryShouldNotBeFound("manufacturer.doesNotContain=" + DEFAULT_MANUFACTURER);

        // Get all the assetInventoryList where manufacturer does not contain UPDATED_MANUFACTURER
        defaultAssetInventoryShouldBeFound("manufacturer.doesNotContain=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where model equals to DEFAULT_MODEL
        defaultAssetInventoryShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the assetInventoryList where model equals to UPDATED_MODEL
        defaultAssetInventoryShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByModelIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultAssetInventoryShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the assetInventoryList where model equals to UPDATED_MODEL
        defaultAssetInventoryShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where model is not null
        defaultAssetInventoryShouldBeFound("model.specified=true");

        // Get all the assetInventoryList where model is null
        defaultAssetInventoryShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByModelContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where model contains DEFAULT_MODEL
        defaultAssetInventoryShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the assetInventoryList where model contains UPDATED_MODEL
        defaultAssetInventoryShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByModelNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where model does not contain DEFAULT_MODEL
        defaultAssetInventoryShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the assetInventoryList where model does not contain UPDATED_MODEL
        defaultAssetInventoryShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByProductNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where productNumber equals to DEFAULT_PRODUCT_NUMBER
        defaultAssetInventoryShouldBeFound("productNumber.equals=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the assetInventoryList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultAssetInventoryShouldNotBeFound("productNumber.equals=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByProductNumberIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where productNumber in DEFAULT_PRODUCT_NUMBER or UPDATED_PRODUCT_NUMBER
        defaultAssetInventoryShouldBeFound("productNumber.in=" + DEFAULT_PRODUCT_NUMBER + "," + UPDATED_PRODUCT_NUMBER);

        // Get all the assetInventoryList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultAssetInventoryShouldNotBeFound("productNumber.in=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByProductNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where productNumber is not null
        defaultAssetInventoryShouldBeFound("productNumber.specified=true");

        // Get all the assetInventoryList where productNumber is null
        defaultAssetInventoryShouldNotBeFound("productNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByProductNumberContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where productNumber contains DEFAULT_PRODUCT_NUMBER
        defaultAssetInventoryShouldBeFound("productNumber.contains=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the assetInventoryList where productNumber contains UPDATED_PRODUCT_NUMBER
        defaultAssetInventoryShouldNotBeFound("productNumber.contains=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByProductNumberNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where productNumber does not contain DEFAULT_PRODUCT_NUMBER
        defaultAssetInventoryShouldNotBeFound("productNumber.doesNotContain=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the assetInventoryList where productNumber does not contain UPDATED_PRODUCT_NUMBER
        defaultAssetInventoryShouldBeFound("productNumber.doesNotContain=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where supplier equals to DEFAULT_SUPPLIER
        defaultAssetInventoryShouldBeFound("supplier.equals=" + DEFAULT_SUPPLIER);

        // Get all the assetInventoryList where supplier equals to UPDATED_SUPPLIER
        defaultAssetInventoryShouldNotBeFound("supplier.equals=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySupplierIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where supplier in DEFAULT_SUPPLIER or UPDATED_SUPPLIER
        defaultAssetInventoryShouldBeFound("supplier.in=" + DEFAULT_SUPPLIER + "," + UPDATED_SUPPLIER);

        // Get all the assetInventoryList where supplier equals to UPDATED_SUPPLIER
        defaultAssetInventoryShouldNotBeFound("supplier.in=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySupplierIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where supplier is not null
        defaultAssetInventoryShouldBeFound("supplier.specified=true");

        // Get all the assetInventoryList where supplier is null
        defaultAssetInventoryShouldNotBeFound("supplier.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySupplierContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where supplier contains DEFAULT_SUPPLIER
        defaultAssetInventoryShouldBeFound("supplier.contains=" + DEFAULT_SUPPLIER);

        // Get all the assetInventoryList where supplier contains UPDATED_SUPPLIER
        defaultAssetInventoryShouldNotBeFound("supplier.contains=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySupplierNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where supplier does not contain DEFAULT_SUPPLIER
        defaultAssetInventoryShouldNotBeFound("supplier.doesNotContain=" + DEFAULT_SUPPLIER);

        // Get all the assetInventoryList where supplier does not contain UPDATED_SUPPLIER
        defaultAssetInventoryShouldBeFound("supplier.doesNotContain=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths equals to DEFAULT_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldBeFound("warrantyInMonths.equals=" + DEFAULT_WARRANTY_IN_MONTHS);

        // Get all the assetInventoryList where warrantyInMonths equals to UPDATED_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.equals=" + UPDATED_WARRANTY_IN_MONTHS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths in DEFAULT_WARRANTY_IN_MONTHS or UPDATED_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldBeFound("warrantyInMonths.in=" + DEFAULT_WARRANTY_IN_MONTHS + "," + UPDATED_WARRANTY_IN_MONTHS);

        // Get all the assetInventoryList where warrantyInMonths equals to UPDATED_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.in=" + UPDATED_WARRANTY_IN_MONTHS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths is not null
        defaultAssetInventoryShouldBeFound("warrantyInMonths.specified=true");

        // Get all the assetInventoryList where warrantyInMonths is null
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths is greater than or equal to DEFAULT_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldBeFound("warrantyInMonths.greaterThanOrEqual=" + DEFAULT_WARRANTY_IN_MONTHS);

        // Get all the assetInventoryList where warrantyInMonths is greater than or equal to UPDATED_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.greaterThanOrEqual=" + UPDATED_WARRANTY_IN_MONTHS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths is less than or equal to DEFAULT_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldBeFound("warrantyInMonths.lessThanOrEqual=" + DEFAULT_WARRANTY_IN_MONTHS);

        // Get all the assetInventoryList where warrantyInMonths is less than or equal to SMALLER_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.lessThanOrEqual=" + SMALLER_WARRANTY_IN_MONTHS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths is less than DEFAULT_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.lessThan=" + DEFAULT_WARRANTY_IN_MONTHS);

        // Get all the assetInventoryList where warrantyInMonths is less than UPDATED_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldBeFound("warrantyInMonths.lessThan=" + UPDATED_WARRANTY_IN_MONTHS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByWarrantyInMonthsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where warrantyInMonths is greater than DEFAULT_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldNotBeFound("warrantyInMonths.greaterThan=" + DEFAULT_WARRANTY_IN_MONTHS);

        // Get all the assetInventoryList where warrantyInMonths is greater than SMALLER_WARRANTY_IN_MONTHS
        defaultAssetInventoryShouldBeFound("warrantyInMonths.greaterThan=" + SMALLER_WARRANTY_IN_MONTHS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where condition equals to DEFAULT_CONDITION
        defaultAssetInventoryShouldBeFound("condition.equals=" + DEFAULT_CONDITION);

        // Get all the assetInventoryList where condition equals to UPDATED_CONDITION
        defaultAssetInventoryShouldNotBeFound("condition.equals=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByConditionIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where condition in DEFAULT_CONDITION or UPDATED_CONDITION
        defaultAssetInventoryShouldBeFound("condition.in=" + DEFAULT_CONDITION + "," + UPDATED_CONDITION);

        // Get all the assetInventoryList where condition equals to UPDATED_CONDITION
        defaultAssetInventoryShouldNotBeFound("condition.in=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where condition is not null
        defaultAssetInventoryShouldBeFound("condition.specified=true");

        // Get all the assetInventoryList where condition is null
        defaultAssetInventoryShouldNotBeFound("condition.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByConditionContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where condition contains DEFAULT_CONDITION
        defaultAssetInventoryShouldBeFound("condition.contains=" + DEFAULT_CONDITION);

        // Get all the assetInventoryList where condition contains UPDATED_CONDITION
        defaultAssetInventoryShouldNotBeFound("condition.contains=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByConditionNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where condition does not contain DEFAULT_CONDITION
        defaultAssetInventoryShouldNotBeFound("condition.doesNotContain=" + DEFAULT_CONDITION);

        // Get all the assetInventoryList where condition does not contain UPDATED_CONDITION
        defaultAssetInventoryShouldBeFound("condition.doesNotContain=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value equals to DEFAULT_VALUE
        defaultAssetInventoryShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the assetInventoryList where value equals to UPDATED_VALUE
        defaultAssetInventoryShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAssetInventoryShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the assetInventoryList where value equals to UPDATED_VALUE
        defaultAssetInventoryShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value is not null
        defaultAssetInventoryShouldBeFound("value.specified=true");

        // Get all the assetInventoryList where value is null
        defaultAssetInventoryShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value is greater than or equal to DEFAULT_VALUE
        defaultAssetInventoryShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the assetInventoryList where value is greater than or equal to UPDATED_VALUE
        defaultAssetInventoryShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value is less than or equal to DEFAULT_VALUE
        defaultAssetInventoryShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the assetInventoryList where value is less than or equal to SMALLER_VALUE
        defaultAssetInventoryShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value is less than DEFAULT_VALUE
        defaultAssetInventoryShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the assetInventoryList where value is less than UPDATED_VALUE
        defaultAssetInventoryShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where value is greater than DEFAULT_VALUE
        defaultAssetInventoryShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the assetInventoryList where value is greater than SMALLER_VALUE
        defaultAssetInventoryShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where description equals to DEFAULT_DESCRIPTION
        defaultAssetInventoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the assetInventoryList where description equals to UPDATED_DESCRIPTION
        defaultAssetInventoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAssetInventoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the assetInventoryList where description equals to UPDATED_DESCRIPTION
        defaultAssetInventoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where description is not null
        defaultAssetInventoryShouldBeFound("description.specified=true");

        // Get all the assetInventoryList where description is null
        defaultAssetInventoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where description contains DEFAULT_DESCRIPTION
        defaultAssetInventoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the assetInventoryList where description contains UPDATED_DESCRIPTION
        defaultAssetInventoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where description does not contain DEFAULT_DESCRIPTION
        defaultAssetInventoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the assetInventoryList where description does not contain UPDATED_DESCRIPTION
        defaultAssetInventoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetStatus equals to DEFAULT_ASSET_STATUS
        defaultAssetInventoryShouldBeFound("assetStatus.equals=" + DEFAULT_ASSET_STATUS);

        // Get all the assetInventoryList where assetStatus equals to UPDATED_ASSET_STATUS
        defaultAssetInventoryShouldNotBeFound("assetStatus.equals=" + UPDATED_ASSET_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetStatusIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetStatus in DEFAULT_ASSET_STATUS or UPDATED_ASSET_STATUS
        defaultAssetInventoryShouldBeFound("assetStatus.in=" + DEFAULT_ASSET_STATUS + "," + UPDATED_ASSET_STATUS);

        // Get all the assetInventoryList where assetStatus equals to UPDATED_ASSET_STATUS
        defaultAssetInventoryShouldNotBeFound("assetStatus.in=" + UPDATED_ASSET_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetStatus is not null
        defaultAssetInventoryShouldBeFound("assetStatus.specified=true");

        // Get all the assetInventoryList where assetStatus is null
        defaultAssetInventoryShouldNotBeFound("assetStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetStatusContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetStatus contains DEFAULT_ASSET_STATUS
        defaultAssetInventoryShouldBeFound("assetStatus.contains=" + DEFAULT_ASSET_STATUS);

        // Get all the assetInventoryList where assetStatus contains UPDATED_ASSET_STATUS
        defaultAssetInventoryShouldNotBeFound("assetStatus.contains=" + UPDATED_ASSET_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetStatusNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetStatus does not contain DEFAULT_ASSET_STATUS
        defaultAssetInventoryShouldNotBeFound("assetStatus.doesNotContain=" + DEFAULT_ASSET_STATUS);

        // Get all the assetInventoryList where assetStatus does not contain UPDATED_ASSET_STATUS
        defaultAssetInventoryShouldBeFound("assetStatus.doesNotContain=" + UPDATED_ASSET_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId equals to DEFAULT_ASSET_USER_ID
        defaultAssetInventoryShouldBeFound("assetUserId.equals=" + DEFAULT_ASSET_USER_ID);

        // Get all the assetInventoryList where assetUserId equals to UPDATED_ASSET_USER_ID
        defaultAssetInventoryShouldNotBeFound("assetUserId.equals=" + UPDATED_ASSET_USER_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId in DEFAULT_ASSET_USER_ID or UPDATED_ASSET_USER_ID
        defaultAssetInventoryShouldBeFound("assetUserId.in=" + DEFAULT_ASSET_USER_ID + "," + UPDATED_ASSET_USER_ID);

        // Get all the assetInventoryList where assetUserId equals to UPDATED_ASSET_USER_ID
        defaultAssetInventoryShouldNotBeFound("assetUserId.in=" + UPDATED_ASSET_USER_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId is not null
        defaultAssetInventoryShouldBeFound("assetUserId.specified=true");

        // Get all the assetInventoryList where assetUserId is null
        defaultAssetInventoryShouldNotBeFound("assetUserId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId is greater than or equal to DEFAULT_ASSET_USER_ID
        defaultAssetInventoryShouldBeFound("assetUserId.greaterThanOrEqual=" + DEFAULT_ASSET_USER_ID);

        // Get all the assetInventoryList where assetUserId is greater than or equal to UPDATED_ASSET_USER_ID
        defaultAssetInventoryShouldNotBeFound("assetUserId.greaterThanOrEqual=" + UPDATED_ASSET_USER_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId is less than or equal to DEFAULT_ASSET_USER_ID
        defaultAssetInventoryShouldBeFound("assetUserId.lessThanOrEqual=" + DEFAULT_ASSET_USER_ID);

        // Get all the assetInventoryList where assetUserId is less than or equal to SMALLER_ASSET_USER_ID
        defaultAssetInventoryShouldNotBeFound("assetUserId.lessThanOrEqual=" + SMALLER_ASSET_USER_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId is less than DEFAULT_ASSET_USER_ID
        defaultAssetInventoryShouldNotBeFound("assetUserId.lessThan=" + DEFAULT_ASSET_USER_ID);

        // Get all the assetInventoryList where assetUserId is less than UPDATED_ASSET_USER_ID
        defaultAssetInventoryShouldBeFound("assetUserId.lessThan=" + UPDATED_ASSET_USER_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByAssetUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where assetUserId is greater than DEFAULT_ASSET_USER_ID
        defaultAssetInventoryShouldNotBeFound("assetUserId.greaterThan=" + DEFAULT_ASSET_USER_ID);

        // Get all the assetInventoryList where assetUserId is greater than SMALLER_ASSET_USER_ID
        defaultAssetInventoryShouldBeFound("assetUserId.greaterThan=" + SMALLER_ASSET_USER_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where status equals to DEFAULT_STATUS
        defaultAssetInventoryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the assetInventoryList where status equals to UPDATED_STATUS
        defaultAssetInventoryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAssetInventoryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the assetInventoryList where status equals to UPDATED_STATUS
        defaultAssetInventoryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where status is not null
        defaultAssetInventoryShouldBeFound("status.specified=true");

        // Get all the assetInventoryList where status is null
        defaultAssetInventoryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByStatusContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where status contains DEFAULT_STATUS
        defaultAssetInventoryShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the assetInventoryList where status contains UPDATED_STATUS
        defaultAssetInventoryShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where status does not contain DEFAULT_STATUS
        defaultAssetInventoryShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the assetInventoryList where status does not contain UPDATED_STATUS
        defaultAssetInventoryShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt equals to DEFAULT_SUBMITTED_AMT
        defaultAssetInventoryShouldBeFound("submittedAmt.equals=" + DEFAULT_SUBMITTED_AMT);

        // Get all the assetInventoryList where submittedAmt equals to UPDATED_SUBMITTED_AMT
        defaultAssetInventoryShouldNotBeFound("submittedAmt.equals=" + UPDATED_SUBMITTED_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt in DEFAULT_SUBMITTED_AMT or UPDATED_SUBMITTED_AMT
        defaultAssetInventoryShouldBeFound("submittedAmt.in=" + DEFAULT_SUBMITTED_AMT + "," + UPDATED_SUBMITTED_AMT);

        // Get all the assetInventoryList where submittedAmt equals to UPDATED_SUBMITTED_AMT
        defaultAssetInventoryShouldNotBeFound("submittedAmt.in=" + UPDATED_SUBMITTED_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt is not null
        defaultAssetInventoryShouldBeFound("submittedAmt.specified=true");

        // Get all the assetInventoryList where submittedAmt is null
        defaultAssetInventoryShouldNotBeFound("submittedAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt is greater than or equal to DEFAULT_SUBMITTED_AMT
        defaultAssetInventoryShouldBeFound("submittedAmt.greaterThanOrEqual=" + DEFAULT_SUBMITTED_AMT);

        // Get all the assetInventoryList where submittedAmt is greater than or equal to UPDATED_SUBMITTED_AMT
        defaultAssetInventoryShouldNotBeFound("submittedAmt.greaterThanOrEqual=" + UPDATED_SUBMITTED_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt is less than or equal to DEFAULT_SUBMITTED_AMT
        defaultAssetInventoryShouldBeFound("submittedAmt.lessThanOrEqual=" + DEFAULT_SUBMITTED_AMT);

        // Get all the assetInventoryList where submittedAmt is less than or equal to SMALLER_SUBMITTED_AMT
        defaultAssetInventoryShouldNotBeFound("submittedAmt.lessThanOrEqual=" + SMALLER_SUBMITTED_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt is less than DEFAULT_SUBMITTED_AMT
        defaultAssetInventoryShouldNotBeFound("submittedAmt.lessThan=" + DEFAULT_SUBMITTED_AMT);

        // Get all the assetInventoryList where submittedAmt is less than UPDATED_SUBMITTED_AMT
        defaultAssetInventoryShouldBeFound("submittedAmt.lessThan=" + UPDATED_SUBMITTED_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesBySubmittedAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where submittedAmt is greater than DEFAULT_SUBMITTED_AMT
        defaultAssetInventoryShouldNotBeFound("submittedAmt.greaterThan=" + DEFAULT_SUBMITTED_AMT);

        // Get all the assetInventoryList where submittedAmt is greater than SMALLER_SUBMITTED_AMT
        defaultAssetInventoryShouldBeFound("submittedAmt.greaterThan=" + SMALLER_SUBMITTED_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt equals to DEFAULT_REFUND_AMT
        defaultAssetInventoryShouldBeFound("refundAmt.equals=" + DEFAULT_REFUND_AMT);

        // Get all the assetInventoryList where refundAmt equals to UPDATED_REFUND_AMT
        defaultAssetInventoryShouldNotBeFound("refundAmt.equals=" + UPDATED_REFUND_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt in DEFAULT_REFUND_AMT or UPDATED_REFUND_AMT
        defaultAssetInventoryShouldBeFound("refundAmt.in=" + DEFAULT_REFUND_AMT + "," + UPDATED_REFUND_AMT);

        // Get all the assetInventoryList where refundAmt equals to UPDATED_REFUND_AMT
        defaultAssetInventoryShouldNotBeFound("refundAmt.in=" + UPDATED_REFUND_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt is not null
        defaultAssetInventoryShouldBeFound("refundAmt.specified=true");

        // Get all the assetInventoryList where refundAmt is null
        defaultAssetInventoryShouldNotBeFound("refundAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt is greater than or equal to DEFAULT_REFUND_AMT
        defaultAssetInventoryShouldBeFound("refundAmt.greaterThanOrEqual=" + DEFAULT_REFUND_AMT);

        // Get all the assetInventoryList where refundAmt is greater than or equal to UPDATED_REFUND_AMT
        defaultAssetInventoryShouldNotBeFound("refundAmt.greaterThanOrEqual=" + UPDATED_REFUND_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt is less than or equal to DEFAULT_REFUND_AMT
        defaultAssetInventoryShouldBeFound("refundAmt.lessThanOrEqual=" + DEFAULT_REFUND_AMT);

        // Get all the assetInventoryList where refundAmt is less than or equal to SMALLER_REFUND_AMT
        defaultAssetInventoryShouldNotBeFound("refundAmt.lessThanOrEqual=" + SMALLER_REFUND_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt is less than DEFAULT_REFUND_AMT
        defaultAssetInventoryShouldNotBeFound("refundAmt.lessThan=" + DEFAULT_REFUND_AMT);

        // Get all the assetInventoryList where refundAmt is less than UPDATED_REFUND_AMT
        defaultAssetInventoryShouldBeFound("refundAmt.lessThan=" + UPDATED_REFUND_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByRefundAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where refundAmt is greater than DEFAULT_REFUND_AMT
        defaultAssetInventoryShouldNotBeFound("refundAmt.greaterThan=" + DEFAULT_REFUND_AMT);

        // Get all the assetInventoryList where refundAmt is greater than SMALLER_REFUND_AMT
        defaultAssetInventoryShouldBeFound("refundAmt.greaterThan=" + SMALLER_REFUND_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt equals to DEFAULT_FINE_AMT
        defaultAssetInventoryShouldBeFound("fineAmt.equals=" + DEFAULT_FINE_AMT);

        // Get all the assetInventoryList where fineAmt equals to UPDATED_FINE_AMT
        defaultAssetInventoryShouldNotBeFound("fineAmt.equals=" + UPDATED_FINE_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt in DEFAULT_FINE_AMT or UPDATED_FINE_AMT
        defaultAssetInventoryShouldBeFound("fineAmt.in=" + DEFAULT_FINE_AMT + "," + UPDATED_FINE_AMT);

        // Get all the assetInventoryList where fineAmt equals to UPDATED_FINE_AMT
        defaultAssetInventoryShouldNotBeFound("fineAmt.in=" + UPDATED_FINE_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt is not null
        defaultAssetInventoryShouldBeFound("fineAmt.specified=true");

        // Get all the assetInventoryList where fineAmt is null
        defaultAssetInventoryShouldNotBeFound("fineAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt is greater than or equal to DEFAULT_FINE_AMT
        defaultAssetInventoryShouldBeFound("fineAmt.greaterThanOrEqual=" + DEFAULT_FINE_AMT);

        // Get all the assetInventoryList where fineAmt is greater than or equal to UPDATED_FINE_AMT
        defaultAssetInventoryShouldNotBeFound("fineAmt.greaterThanOrEqual=" + UPDATED_FINE_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt is less than or equal to DEFAULT_FINE_AMT
        defaultAssetInventoryShouldBeFound("fineAmt.lessThanOrEqual=" + DEFAULT_FINE_AMT);

        // Get all the assetInventoryList where fineAmt is less than or equal to SMALLER_FINE_AMT
        defaultAssetInventoryShouldNotBeFound("fineAmt.lessThanOrEqual=" + SMALLER_FINE_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt is less than DEFAULT_FINE_AMT
        defaultAssetInventoryShouldNotBeFound("fineAmt.lessThan=" + DEFAULT_FINE_AMT);

        // Get all the assetInventoryList where fineAmt is less than UPDATED_FINE_AMT
        defaultAssetInventoryShouldBeFound("fineAmt.lessThan=" + UPDATED_FINE_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByFineAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where fineAmt is greater than DEFAULT_FINE_AMT
        defaultAssetInventoryShouldNotBeFound("fineAmt.greaterThan=" + DEFAULT_FINE_AMT);

        // Get all the assetInventoryList where fineAmt is greater than SMALLER_FINE_AMT
        defaultAssetInventoryShouldBeFound("fineAmt.greaterThan=" + SMALLER_FINE_AMT);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where companyId equals to DEFAULT_COMPANY_ID
//        defaultAssetInventoryShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);
//
//        // Get all the assetInventoryList where companyId equals to UPDATED_COMPANY_ID
//        defaultAssetInventoryShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
//        defaultAssetInventoryShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);
//
//        // Get all the assetInventoryList where companyId equals to UPDATED_COMPANY_ID
//        defaultAssetInventoryShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where companyId is not null
        defaultAssetInventoryShouldBeFound("companyId.specified=true");

        // Get all the assetInventoryList where companyId is null
        defaultAssetInventoryShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where companyId is greater than or equal to DEFAULT_COMPANY_ID
//        defaultAssetInventoryShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the assetInventoryList where companyId is greater than or equal to UPDATED_COMPANY_ID
//        defaultAssetInventoryShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where companyId is less than or equal to DEFAULT_COMPANY_ID
//        defaultAssetInventoryShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the assetInventoryList where companyId is less than or equal to SMALLER_COMPANY_ID
//        defaultAssetInventoryShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where companyId is less than DEFAULT_COMPANY_ID
//        defaultAssetInventoryShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the assetInventoryList where companyId is less than UPDATED_COMPANY_ID
//        defaultAssetInventoryShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where companyId is greater than DEFAULT_COMPANY_ID
//        defaultAssetInventoryShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the assetInventoryList where companyId is greater than SMALLER_COMPANY_ID
//        defaultAssetInventoryShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where lastModified equals to DEFAULT_LAST_MODIFIED
//        defaultAssetInventoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);
//
//        // Get all the assetInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultAssetInventoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
//        defaultAssetInventoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);
//
//        // Get all the assetInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultAssetInventoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where lastModified is not null
        defaultAssetInventoryShouldBeFound("lastModified.specified=true");

        // Get all the assetInventoryList where lastModified is null
        defaultAssetInventoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the assetInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);
//
//        // Get all the assetInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        // Get all the assetInventoryList where lastModifiedBy is not null
        defaultAssetInventoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the assetInventoryList where lastModifiedBy is null
        defaultAssetInventoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the assetInventoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAssetInventoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

//        // Get all the assetInventoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the assetInventoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
//        defaultAssetInventoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetInventoryShouldBeFound(String filter) throws Exception {
        restAssetInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetName").value(hasItem(DEFAULT_ASSET_NAME)))
            .andExpect(jsonPath("$.[*].assetype").value(hasItem(DEFAULT_ASSETYPE)))
            .andExpect(jsonPath("$.[*].assetId").value(hasItem(DEFAULT_ASSET_ID)))
            .andExpect(jsonPath("$.[*].purchaseFrom").value(hasItem(DEFAULT_PURCHASE_FROM)))
            .andExpect(jsonPath("$.[*].purchaseTo").value(hasItem(DEFAULT_PURCHASE_TO)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].warrantyInMonths").value(hasItem(DEFAULT_WARRANTY_IN_MONTHS.intValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].assetStatus").value(hasItem(DEFAULT_ASSET_STATUS)))
            .andExpect(jsonPath("$.[*].assetUserId").value(hasItem(DEFAULT_ASSET_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].submittedAmt").value(hasItem(DEFAULT_SUBMITTED_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmt").value(hasItem(DEFAULT_REFUND_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].fineAmt").value(hasItem(DEFAULT_FINE_AMT.doubleValue())));
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAssetInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetInventoryShouldNotBeFound(String filter) throws Exception {
        restAssetInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAssetInventory() throws Exception {
        // Get the assetInventory
        restAssetInventoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetInventory() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();

        // Update the assetInventory
        AssetInventory updatedAssetInventory = assetInventoryRepository.findById(assetInventory.getId()).get();
        // Disconnect from session so that the updates on updatedAssetInventory are not directly saved in db
        em.detach(updatedAssetInventory);
        updatedAssetInventory
            .assetName(UPDATED_ASSET_NAME)
            .assetype(UPDATED_ASSETYPE)
            .assetId(UPDATED_ASSET_ID)
            .purchaseFrom(UPDATED_PURCHASE_FROM)
            .purchaseTo(UPDATED_PURCHASE_TO)
            .manufacturer(UPDATED_MANUFACTURER)
            .model(UPDATED_MODEL)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .supplier(UPDATED_SUPPLIER)
            .warrantyInMonths(UPDATED_WARRANTY_IN_MONTHS)
            .condition(UPDATED_CONDITION)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .assetStatus(UPDATED_ASSET_STATUS)
            .assetUserId(UPDATED_ASSET_USER_ID)
            .status(UPDATED_STATUS)
            .submittedAmt(UPDATED_SUBMITTED_AMT)
            .refundAmt(UPDATED_REFUND_AMT)
            .fineAmt(UPDATED_FINE_AMT);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(updatedAssetInventory);

        restAssetInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetInventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
        AssetInventory testAssetInventory = assetInventoryList.get(assetInventoryList.size() - 1);
        assertThat(testAssetInventory.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAssetInventory.getAssetype()).isEqualTo(UPDATED_ASSETYPE);
        assertThat(testAssetInventory.getAssetId()).isEqualTo(UPDATED_ASSET_ID);
        assertThat(testAssetInventory.getPurchaseFrom()).isEqualTo(UPDATED_PURCHASE_FROM);
        assertThat(testAssetInventory.getPurchaseTo()).isEqualTo(UPDATED_PURCHASE_TO);
        assertThat(testAssetInventory.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testAssetInventory.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAssetInventory.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testAssetInventory.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testAssetInventory.getWarrantyInMonths()).isEqualTo(UPDATED_WARRANTY_IN_MONTHS);
        assertThat(testAssetInventory.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testAssetInventory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAssetInventory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetInventory.getAssetStatus()).isEqualTo(UPDATED_ASSET_STATUS);
        assertThat(testAssetInventory.getAssetUserId()).isEqualTo(UPDATED_ASSET_USER_ID);
        assertThat(testAssetInventory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetInventory.getSubmittedAmt()).isEqualTo(UPDATED_SUBMITTED_AMT);
        assertThat(testAssetInventory.getRefundAmt()).isEqualTo(UPDATED_REFUND_AMT);
        assertThat(testAssetInventory.getFineAmt()).isEqualTo(UPDATED_FINE_AMT);
//        assertThat(testAssetInventory.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testAssetInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testAssetInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAssetInventory() throws Exception {
        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();
        assetInventory.setId(count.incrementAndGet());

        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetInventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetInventory() throws Exception {
        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();
        assetInventory.setId(count.incrementAndGet());

        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetInventory() throws Exception {
        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();
        assetInventory.setId(count.incrementAndGet());

        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetInventoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetInventoryWithPatch() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();

        // Update the assetInventory using partial update
        AssetInventory partialUpdatedAssetInventory = new AssetInventory();
        partialUpdatedAssetInventory.setId(assetInventory.getId());

        partialUpdatedAssetInventory
            .assetName(UPDATED_ASSET_NAME)
            .model(UPDATED_MODEL)
            .assetUserId(UPDATED_ASSET_USER_ID)
            .refundAmt(UPDATED_REFUND_AMT)
            .fineAmt(UPDATED_FINE_AMT);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAssetInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetInventory))
            )
            .andExpect(status().isOk());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
        AssetInventory testAssetInventory = assetInventoryList.get(assetInventoryList.size() - 1);
        assertThat(testAssetInventory.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAssetInventory.getAssetype()).isEqualTo(DEFAULT_ASSETYPE);
        assertThat(testAssetInventory.getAssetId()).isEqualTo(DEFAULT_ASSET_ID);
        assertThat(testAssetInventory.getPurchaseFrom()).isEqualTo(DEFAULT_PURCHASE_FROM);
        assertThat(testAssetInventory.getPurchaseTo()).isEqualTo(DEFAULT_PURCHASE_TO);
        assertThat(testAssetInventory.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testAssetInventory.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAssetInventory.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testAssetInventory.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testAssetInventory.getWarrantyInMonths()).isEqualTo(DEFAULT_WARRANTY_IN_MONTHS);
        assertThat(testAssetInventory.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testAssetInventory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAssetInventory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetInventory.getAssetStatus()).isEqualTo(DEFAULT_ASSET_STATUS);
        assertThat(testAssetInventory.getAssetUserId()).isEqualTo(UPDATED_ASSET_USER_ID);
        assertThat(testAssetInventory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssetInventory.getSubmittedAmt()).isEqualTo(DEFAULT_SUBMITTED_AMT);
        assertThat(testAssetInventory.getRefundAmt()).isEqualTo(UPDATED_REFUND_AMT);
        assertThat(testAssetInventory.getFineAmt()).isEqualTo(UPDATED_FINE_AMT);
//        assertThat(testAssetInventory.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testAssetInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testAssetInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAssetInventoryWithPatch() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();

        // Update the assetInventory using partial update
        AssetInventory partialUpdatedAssetInventory = new AssetInventory();
        partialUpdatedAssetInventory.setId(assetInventory.getId());

        partialUpdatedAssetInventory
            .assetName(UPDATED_ASSET_NAME)
            .assetype(UPDATED_ASSETYPE)
            .assetId(UPDATED_ASSET_ID)
            .purchaseFrom(UPDATED_PURCHASE_FROM)
            .purchaseTo(UPDATED_PURCHASE_TO)
            .manufacturer(UPDATED_MANUFACTURER)
            .model(UPDATED_MODEL)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .supplier(UPDATED_SUPPLIER)
            .warrantyInMonths(UPDATED_WARRANTY_IN_MONTHS)
            .condition(UPDATED_CONDITION)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .assetStatus(UPDATED_ASSET_STATUS)
            .assetUserId(UPDATED_ASSET_USER_ID)
            .status(UPDATED_STATUS)
            .submittedAmt(UPDATED_SUBMITTED_AMT)
            .refundAmt(UPDATED_REFUND_AMT)
            .fineAmt(UPDATED_FINE_AMT);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAssetInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssetInventory))
            )
            .andExpect(status().isOk());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
        AssetInventory testAssetInventory = assetInventoryList.get(assetInventoryList.size() - 1);
        assertThat(testAssetInventory.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAssetInventory.getAssetype()).isEqualTo(UPDATED_ASSETYPE);
        assertThat(testAssetInventory.getAssetId()).isEqualTo(UPDATED_ASSET_ID);
        assertThat(testAssetInventory.getPurchaseFrom()).isEqualTo(UPDATED_PURCHASE_FROM);
        assertThat(testAssetInventory.getPurchaseTo()).isEqualTo(UPDATED_PURCHASE_TO);
        assertThat(testAssetInventory.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testAssetInventory.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAssetInventory.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testAssetInventory.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testAssetInventory.getWarrantyInMonths()).isEqualTo(UPDATED_WARRANTY_IN_MONTHS);
        assertThat(testAssetInventory.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testAssetInventory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAssetInventory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetInventory.getAssetStatus()).isEqualTo(UPDATED_ASSET_STATUS);
        assertThat(testAssetInventory.getAssetUserId()).isEqualTo(UPDATED_ASSET_USER_ID);
        assertThat(testAssetInventory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssetInventory.getSubmittedAmt()).isEqualTo(UPDATED_SUBMITTED_AMT);
        assertThat(testAssetInventory.getRefundAmt()).isEqualTo(UPDATED_REFUND_AMT);
        assertThat(testAssetInventory.getFineAmt()).isEqualTo(UPDATED_FINE_AMT);
//        assertThat(testAssetInventory.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testAssetInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testAssetInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAssetInventory() throws Exception {
        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();
        assetInventory.setId(count.incrementAndGet());

        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetInventoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetInventory() throws Exception {
        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();
        assetInventory.setId(count.incrementAndGet());

        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetInventory() throws Exception {
        int databaseSizeBeforeUpdate = assetInventoryRepository.findAll().size();
        assetInventory.setId(count.incrementAndGet());

        // Create the AssetInventory
        AssetInventoryDTO assetInventoryDTO = assetInventoryMapper.toDto(assetInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assetInventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetInventory in the database
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetInventory() throws Exception {
        // Initialize the database
        assetInventoryRepository.saveAndFlush(assetInventory);

        int databaseSizeBeforeDelete = assetInventoryRepository.findAll().size();

        // Delete the assetInventory
        restAssetInventoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetInventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetInventory> assetInventoryList = assetInventoryRepository.findAll();
        assertThat(assetInventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
