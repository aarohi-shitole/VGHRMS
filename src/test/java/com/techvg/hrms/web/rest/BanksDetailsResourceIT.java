package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.BanksDetails;
import com.techvg.hrms.repository.BanksDetailsRepository;
import com.techvg.hrms.service.criteria.BanksDetailsCriteria;
import com.techvg.hrms.service.dto.BanksDetailsDTO;
import com.techvg.hrms.service.mapper.BanksDetailsMapper;
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
 * Integration tests for the {@link BanksDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanksDetailsResourceIT {

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;
    private static final Long SMALLER_ACCOUNT_NUMBER = 1L - 1L;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_TRANS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_TRANS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GSTIN = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN = "BBBBBBBBBB";

    private static final String DEFAULT_TAN = "AAAAAAAAAA";
    private static final String UPDATED_TAN = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REF_TABLE = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TABLE_ID = 1L;
    private static final Long UPDATED_REF_TABLE_ID = 2L;
    private static final Long SMALLER_REF_TABLE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banks-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BanksDetailsRepository banksDetailsRepository;

    @Autowired
    private BanksDetailsMapper banksDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBanksDetailsMockMvc;

    private BanksDetails banksDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BanksDetails createEntity(EntityManager em) {
        BanksDetails banksDetails = new BanksDetails()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .branchTransCode(DEFAULT_BRANCH_TRANS_CODE)
            .taxNumber(DEFAULT_TAX_NUMBER)
            .gstin(DEFAULT_GSTIN)
            .tan(DEFAULT_TAN)
            .branchName(DEFAULT_BRANCH_NAME)
            .refTable(DEFAULT_REF_TABLE)
            .refTableId(DEFAULT_REF_TABLE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS);
        //            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return banksDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BanksDetails createUpdatedEntity(EntityManager em) {
        BanksDetails banksDetails = new BanksDetails()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .branchTransCode(UPDATED_BRANCH_TRANS_CODE)
            .taxNumber(UPDATED_TAX_NUMBER)
            .gstin(UPDATED_GSTIN)
            .tan(UPDATED_TAN)
            .branchName(UPDATED_BRANCH_NAME)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return banksDetails;
    }

    @BeforeEach
    public void initTest() {
        banksDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBanksDetails() throws Exception {
        int databaseSizeBeforeCreate = banksDetailsRepository.findAll().size();
        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);
        restBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBanksDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBanksDetails.getBranchTransCode()).isEqualTo(DEFAULT_BRANCH_TRANS_CODE);
        assertThat(testBanksDetails.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
        assertThat(testBanksDetails.getGstin()).isEqualTo(DEFAULT_GSTIN);
        assertThat(testBanksDetails.getTan()).isEqualTo(DEFAULT_TAN);
        assertThat(testBanksDetails.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testBanksDetails.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testBanksDetails.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
        assertThat(testBanksDetails.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testBanksDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createBanksDetailsWithExistingId() throws Exception {
        // Create the BanksDetails with an existing ID
        banksDetails.setId(1L);
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        int databaseSizeBeforeCreate = banksDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banksDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchTransCode").value(hasItem(DEFAULT_BRANCH_TRANS_CODE)))
            .andExpect(jsonPath("$.[*].taxNumber").value(hasItem(DEFAULT_TAX_NUMBER)))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].tan").value(hasItem(DEFAULT_TAN)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get the banksDetails
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, banksDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banksDetails.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchTransCode").value(DEFAULT_BRANCH_TRANS_CODE))
            .andExpect(jsonPath("$.taxNumber").value(DEFAULT_TAX_NUMBER))
            .andExpect(jsonPath("$.gstin").value(DEFAULT_GSTIN))
            .andExpect(jsonPath("$.tan").value(DEFAULT_TAN))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
            .andExpect(jsonPath("$.refTableId").value(DEFAULT_REF_TABLE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getBanksDetailsByIdFiltering() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        Long id = banksDetails.getId();

        defaultBanksDetailsShouldBeFound("id.equals=" + id);
        defaultBanksDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultBanksDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBanksDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultBanksDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBanksDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultBanksDetailsShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the banksDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultBanksDetailsShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultBanksDetailsShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the banksDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultBanksDetailsShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber is not null
        defaultBanksDetailsShouldBeFound("accountNumber.specified=true");

        // Get all the banksDetailsList where accountNumber is null
        defaultBanksDetailsShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber is greater than or equal to DEFAULT_ACCOUNT_NUMBER
        defaultBanksDetailsShouldBeFound("accountNumber.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the banksDetailsList where accountNumber is greater than or equal to UPDATED_ACCOUNT_NUMBER
        defaultBanksDetailsShouldNotBeFound("accountNumber.greaterThanOrEqual=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber is less than or equal to DEFAULT_ACCOUNT_NUMBER
        defaultBanksDetailsShouldBeFound("accountNumber.lessThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the banksDetailsList where accountNumber is less than or equal to SMALLER_ACCOUNT_NUMBER
        defaultBanksDetailsShouldNotBeFound("accountNumber.lessThanOrEqual=" + SMALLER_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber is less than DEFAULT_ACCOUNT_NUMBER
        defaultBanksDetailsShouldNotBeFound("accountNumber.lessThan=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the banksDetailsList where accountNumber is less than UPDATED_ACCOUNT_NUMBER
        defaultBanksDetailsShouldBeFound("accountNumber.lessThan=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByAccountNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where accountNumber is greater than DEFAULT_ACCOUNT_NUMBER
        defaultBanksDetailsShouldNotBeFound("accountNumber.greaterThan=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the banksDetailsList where accountNumber is greater than SMALLER_ACCOUNT_NUMBER
        defaultBanksDetailsShouldBeFound("accountNumber.greaterThan=" + SMALLER_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName equals to DEFAULT_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the banksDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the banksDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName is not null
        defaultBanksDetailsShouldBeFound("bankName.specified=true");

        // Get all the banksDetailsList where bankName is null
        defaultBanksDetailsShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName contains DEFAULT_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the banksDetailsList where bankName contains UPDATED_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where bankName does not contain DEFAULT_BANK_NAME
        defaultBanksDetailsShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the banksDetailsList where bankName does not contain UPDATED_BANK_NAME
        defaultBanksDetailsShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchTransCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchTransCode equals to DEFAULT_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldBeFound("branchTransCode.equals=" + DEFAULT_BRANCH_TRANS_CODE);

        // Get all the banksDetailsList where branchTransCode equals to UPDATED_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldNotBeFound("branchTransCode.equals=" + UPDATED_BRANCH_TRANS_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchTransCodeIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchTransCode in DEFAULT_BRANCH_TRANS_CODE or UPDATED_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldBeFound("branchTransCode.in=" + DEFAULT_BRANCH_TRANS_CODE + "," + UPDATED_BRANCH_TRANS_CODE);

        // Get all the banksDetailsList where branchTransCode equals to UPDATED_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldNotBeFound("branchTransCode.in=" + UPDATED_BRANCH_TRANS_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchTransCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchTransCode is not null
        defaultBanksDetailsShouldBeFound("branchTransCode.specified=true");

        // Get all the banksDetailsList where branchTransCode is null
        defaultBanksDetailsShouldNotBeFound("branchTransCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchTransCodeContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchTransCode contains DEFAULT_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldBeFound("branchTransCode.contains=" + DEFAULT_BRANCH_TRANS_CODE);

        // Get all the banksDetailsList where branchTransCode contains UPDATED_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldNotBeFound("branchTransCode.contains=" + UPDATED_BRANCH_TRANS_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchTransCodeNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchTransCode does not contain DEFAULT_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldNotBeFound("branchTransCode.doesNotContain=" + DEFAULT_BRANCH_TRANS_CODE);

        // Get all the banksDetailsList where branchTransCode does not contain UPDATED_BRANCH_TRANS_CODE
        defaultBanksDetailsShouldBeFound("branchTransCode.doesNotContain=" + UPDATED_BRANCH_TRANS_CODE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where taxNumber equals to DEFAULT_TAX_NUMBER
        defaultBanksDetailsShouldBeFound("taxNumber.equals=" + DEFAULT_TAX_NUMBER);

        // Get all the banksDetailsList where taxNumber equals to UPDATED_TAX_NUMBER
        defaultBanksDetailsShouldNotBeFound("taxNumber.equals=" + UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where taxNumber in DEFAULT_TAX_NUMBER or UPDATED_TAX_NUMBER
        defaultBanksDetailsShouldBeFound("taxNumber.in=" + DEFAULT_TAX_NUMBER + "," + UPDATED_TAX_NUMBER);

        // Get all the banksDetailsList where taxNumber equals to UPDATED_TAX_NUMBER
        defaultBanksDetailsShouldNotBeFound("taxNumber.in=" + UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where taxNumber is not null
        defaultBanksDetailsShouldBeFound("taxNumber.specified=true");

        // Get all the banksDetailsList where taxNumber is null
        defaultBanksDetailsShouldNotBeFound("taxNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTaxNumberContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where taxNumber contains DEFAULT_TAX_NUMBER
        defaultBanksDetailsShouldBeFound("taxNumber.contains=" + DEFAULT_TAX_NUMBER);

        // Get all the banksDetailsList where taxNumber contains UPDATED_TAX_NUMBER
        defaultBanksDetailsShouldNotBeFound("taxNumber.contains=" + UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTaxNumberNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where taxNumber does not contain DEFAULT_TAX_NUMBER
        defaultBanksDetailsShouldNotBeFound("taxNumber.doesNotContain=" + DEFAULT_TAX_NUMBER);

        // Get all the banksDetailsList where taxNumber does not contain UPDATED_TAX_NUMBER
        defaultBanksDetailsShouldBeFound("taxNumber.doesNotContain=" + UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByGstinIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where gstin equals to DEFAULT_GSTIN
        defaultBanksDetailsShouldBeFound("gstin.equals=" + DEFAULT_GSTIN);

        // Get all the banksDetailsList where gstin equals to UPDATED_GSTIN
        defaultBanksDetailsShouldNotBeFound("gstin.equals=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByGstinIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where gstin in DEFAULT_GSTIN or UPDATED_GSTIN
        defaultBanksDetailsShouldBeFound("gstin.in=" + DEFAULT_GSTIN + "," + UPDATED_GSTIN);

        // Get all the banksDetailsList where gstin equals to UPDATED_GSTIN
        defaultBanksDetailsShouldNotBeFound("gstin.in=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByGstinIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where gstin is not null
        defaultBanksDetailsShouldBeFound("gstin.specified=true");

        // Get all the banksDetailsList where gstin is null
        defaultBanksDetailsShouldNotBeFound("gstin.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByGstinContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where gstin contains DEFAULT_GSTIN
        defaultBanksDetailsShouldBeFound("gstin.contains=" + DEFAULT_GSTIN);

        // Get all the banksDetailsList where gstin contains UPDATED_GSTIN
        defaultBanksDetailsShouldNotBeFound("gstin.contains=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByGstinNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where gstin does not contain DEFAULT_GSTIN
        defaultBanksDetailsShouldNotBeFound("gstin.doesNotContain=" + DEFAULT_GSTIN);

        // Get all the banksDetailsList where gstin does not contain UPDATED_GSTIN
        defaultBanksDetailsShouldBeFound("gstin.doesNotContain=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTanIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where tan equals to DEFAULT_TAN
        defaultBanksDetailsShouldBeFound("tan.equals=" + DEFAULT_TAN);

        // Get all the banksDetailsList where tan equals to UPDATED_TAN
        defaultBanksDetailsShouldNotBeFound("tan.equals=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTanIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where tan in DEFAULT_TAN or UPDATED_TAN
        defaultBanksDetailsShouldBeFound("tan.in=" + DEFAULT_TAN + "," + UPDATED_TAN);

        // Get all the banksDetailsList where tan equals to UPDATED_TAN
        defaultBanksDetailsShouldNotBeFound("tan.in=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTanIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where tan is not null
        defaultBanksDetailsShouldBeFound("tan.specified=true");

        // Get all the banksDetailsList where tan is null
        defaultBanksDetailsShouldNotBeFound("tan.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTanContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where tan contains DEFAULT_TAN
        defaultBanksDetailsShouldBeFound("tan.contains=" + DEFAULT_TAN);

        // Get all the banksDetailsList where tan contains UPDATED_TAN
        defaultBanksDetailsShouldNotBeFound("tan.contains=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByTanNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where tan does not contain DEFAULT_TAN
        defaultBanksDetailsShouldNotBeFound("tan.doesNotContain=" + DEFAULT_TAN);

        // Get all the banksDetailsList where tan does not contain UPDATED_TAN
        defaultBanksDetailsShouldBeFound("tan.doesNotContain=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchName equals to DEFAULT_BRANCH_NAME
        defaultBanksDetailsShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the banksDetailsList where branchName equals to UPDATED_BRANCH_NAME
        defaultBanksDetailsShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultBanksDetailsShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the banksDetailsList where branchName equals to UPDATED_BRANCH_NAME
        defaultBanksDetailsShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchName is not null
        defaultBanksDetailsShouldBeFound("branchName.specified=true");

        // Get all the banksDetailsList where branchName is null
        defaultBanksDetailsShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchName contains DEFAULT_BRANCH_NAME
        defaultBanksDetailsShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the banksDetailsList where branchName contains UPDATED_BRANCH_NAME
        defaultBanksDetailsShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultBanksDetailsShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the banksDetailsList where branchName does not contain UPDATED_BRANCH_NAME
        defaultBanksDetailsShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTable equals to DEFAULT_REF_TABLE
        defaultBanksDetailsShouldBeFound("refTable.equals=" + DEFAULT_REF_TABLE);

        // Get all the banksDetailsList where refTable equals to UPDATED_REF_TABLE
        defaultBanksDetailsShouldNotBeFound("refTable.equals=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTable in DEFAULT_REF_TABLE or UPDATED_REF_TABLE
        defaultBanksDetailsShouldBeFound("refTable.in=" + DEFAULT_REF_TABLE + "," + UPDATED_REF_TABLE);

        // Get all the banksDetailsList where refTable equals to UPDATED_REF_TABLE
        defaultBanksDetailsShouldNotBeFound("refTable.in=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTable is not null
        defaultBanksDetailsShouldBeFound("refTable.specified=true");

        // Get all the banksDetailsList where refTable is null
        defaultBanksDetailsShouldNotBeFound("refTable.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTable contains DEFAULT_REF_TABLE
        defaultBanksDetailsShouldBeFound("refTable.contains=" + DEFAULT_REF_TABLE);

        // Get all the banksDetailsList where refTable contains UPDATED_REF_TABLE
        defaultBanksDetailsShouldNotBeFound("refTable.contains=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTable does not contain DEFAULT_REF_TABLE
        defaultBanksDetailsShouldNotBeFound("refTable.doesNotContain=" + DEFAULT_REF_TABLE);

        // Get all the banksDetailsList where refTable does not contain UPDATED_REF_TABLE
        defaultBanksDetailsShouldBeFound("refTable.doesNotContain=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId equals to DEFAULT_REF_TABLE_ID
        defaultBanksDetailsShouldBeFound("refTableId.equals=" + DEFAULT_REF_TABLE_ID);

        // Get all the banksDetailsList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultBanksDetailsShouldNotBeFound("refTableId.equals=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId in DEFAULT_REF_TABLE_ID or UPDATED_REF_TABLE_ID
        defaultBanksDetailsShouldBeFound("refTableId.in=" + DEFAULT_REF_TABLE_ID + "," + UPDATED_REF_TABLE_ID);

        // Get all the banksDetailsList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultBanksDetailsShouldNotBeFound("refTableId.in=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId is not null
        defaultBanksDetailsShouldBeFound("refTableId.specified=true");

        // Get all the banksDetailsList where refTableId is null
        defaultBanksDetailsShouldNotBeFound("refTableId.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId is greater than or equal to DEFAULT_REF_TABLE_ID
        defaultBanksDetailsShouldBeFound("refTableId.greaterThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the banksDetailsList where refTableId is greater than or equal to UPDATED_REF_TABLE_ID
        defaultBanksDetailsShouldNotBeFound("refTableId.greaterThanOrEqual=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId is less than or equal to DEFAULT_REF_TABLE_ID
        defaultBanksDetailsShouldBeFound("refTableId.lessThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the banksDetailsList where refTableId is less than or equal to SMALLER_REF_TABLE_ID
        defaultBanksDetailsShouldNotBeFound("refTableId.lessThanOrEqual=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId is less than DEFAULT_REF_TABLE_ID
        defaultBanksDetailsShouldNotBeFound("refTableId.lessThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the banksDetailsList where refTableId is less than UPDATED_REF_TABLE_ID
        defaultBanksDetailsShouldBeFound("refTableId.lessThan=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByRefTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where refTableId is greater than DEFAULT_REF_TABLE_ID
        defaultBanksDetailsShouldNotBeFound("refTableId.greaterThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the banksDetailsList where refTableId is greater than SMALLER_REF_TABLE_ID
        defaultBanksDetailsShouldBeFound("refTableId.greaterThan=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId equals to DEFAULT_COMPANY_ID
        defaultBanksDetailsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the banksDetailsList where companyId equals to UPDATED_COMPANY_ID
        defaultBanksDetailsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultBanksDetailsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the banksDetailsList where companyId equals to UPDATED_COMPANY_ID
        defaultBanksDetailsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId is not null
        defaultBanksDetailsShouldBeFound("companyId.specified=true");

        // Get all the banksDetailsList where companyId is null
        defaultBanksDetailsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultBanksDetailsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the banksDetailsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultBanksDetailsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultBanksDetailsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the banksDetailsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultBanksDetailsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId is less than DEFAULT_COMPANY_ID
        defaultBanksDetailsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the banksDetailsList where companyId is less than UPDATED_COMPANY_ID
        defaultBanksDetailsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultBanksDetailsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the banksDetailsList where companyId is greater than SMALLER_COMPANY_ID
        defaultBanksDetailsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where status equals to DEFAULT_STATUS
        defaultBanksDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the banksDetailsList where status equals to UPDATED_STATUS
        defaultBanksDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBanksDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the banksDetailsList where status equals to UPDATED_STATUS
        defaultBanksDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where status is not null
        defaultBanksDetailsShouldBeFound("status.specified=true");

        // Get all the banksDetailsList where status is null
        defaultBanksDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByStatusContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where status contains DEFAULT_STATUS
        defaultBanksDetailsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the banksDetailsList where status contains UPDATED_STATUS
        defaultBanksDetailsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where status does not contain DEFAULT_STATUS
        defaultBanksDetailsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the banksDetailsList where status does not contain UPDATED_STATUS
        defaultBanksDetailsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBanksDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the banksDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBanksDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBanksDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the banksDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBanksDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModified is not null
        defaultBanksDetailsShouldBeFound("lastModified.specified=true");

        // Get all the banksDetailsList where lastModified is null
        defaultBanksDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy is not null
        defaultBanksDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the banksDetailsList where lastModifiedBy is null
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        // Get all the banksDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBanksDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the banksDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBanksDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBanksDetailsShouldBeFound(String filter) throws Exception {
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banksDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchTransCode").value(hasItem(DEFAULT_BRANCH_TRANS_CODE)))
            .andExpect(jsonPath("$.[*].taxNumber").value(hasItem(DEFAULT_TAX_NUMBER)))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].tan").value(hasItem(DEFAULT_TAN)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBanksDetailsShouldNotBeFound(String filter) throws Exception {
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBanksDetails() throws Exception {
        // Get the banksDetails
        restBanksDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();

        // Update the banksDetails
        BanksDetails updatedBanksDetails = banksDetailsRepository.findById(banksDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBanksDetails are not directly saved in db
        em.detach(updatedBanksDetails);
        updatedBanksDetails
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .branchTransCode(UPDATED_BRANCH_TRANS_CODE)
            .taxNumber(UPDATED_TAX_NUMBER)
            .gstin(UPDATED_GSTIN)
            .tan(UPDATED_TAN)
            .branchName(UPDATED_BRANCH_NAME)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(updatedBanksDetails);

        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banksDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBanksDetails.getBranchTransCode()).isEqualTo(UPDATED_BRANCH_TRANS_CODE);
        assertThat(testBanksDetails.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
        assertThat(testBanksDetails.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testBanksDetails.getTan()).isEqualTo(UPDATED_TAN);
        assertThat(testBanksDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBanksDetails.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testBanksDetails.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testBanksDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testBanksDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banksDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBanksDetailsWithPatch() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();

        // Update the banksDetails using partial update
        BanksDetails partialUpdatedBanksDetails = new BanksDetails();
        partialUpdatedBanksDetails.setId(banksDetails.getId());

        partialUpdatedBanksDetails
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .gstin(UPDATED_GSTIN)
            .branchName(UPDATED_BRANCH_NAME)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            .status(UPDATED_STATUS)//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanksDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanksDetails))
            )
            .andExpect(status().isOk());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBanksDetails.getBranchTransCode()).isEqualTo(DEFAULT_BRANCH_TRANS_CODE);
        assertThat(testBanksDetails.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
        assertThat(testBanksDetails.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testBanksDetails.getTan()).isEqualTo(DEFAULT_TAN);
        assertThat(testBanksDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBanksDetails.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testBanksDetails.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testBanksDetails.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testBanksDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBanksDetailsWithPatch() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();

        // Update the banksDetails using partial update
        BanksDetails partialUpdatedBanksDetails = new BanksDetails();
        partialUpdatedBanksDetails.setId(banksDetails.getId());

        partialUpdatedBanksDetails
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .branchTransCode(UPDATED_BRANCH_TRANS_CODE)
            .taxNumber(UPDATED_TAX_NUMBER)
            .gstin(UPDATED_GSTIN)
            .tan(UPDATED_TAN)
            .branchName(UPDATED_BRANCH_NAME)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanksDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanksDetails))
            )
            .andExpect(status().isOk());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
        BanksDetails testBanksDetails = banksDetailsList.get(banksDetailsList.size() - 1);
        assertThat(testBanksDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBanksDetails.getBranchTransCode()).isEqualTo(UPDATED_BRANCH_TRANS_CODE);
        assertThat(testBanksDetails.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
        assertThat(testBanksDetails.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testBanksDetails.getTan()).isEqualTo(UPDATED_TAN);
        assertThat(testBanksDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testBanksDetails.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testBanksDetails.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testBanksDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testBanksDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBanksDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banksDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = banksDetailsRepository.findAll().size();
        banksDetails.setId(count.incrementAndGet());

        // Create the BanksDetails
        BanksDetailsDTO banksDetailsDTO = banksDetailsMapper.toDto(banksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BanksDetails in the database
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanksDetails() throws Exception {
        // Initialize the database
        banksDetailsRepository.saveAndFlush(banksDetails);

        int databaseSizeBeforeDelete = banksDetailsRepository.findAll().size();

        // Delete the banksDetails
        restBanksDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, banksDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BanksDetails> banksDetailsList = banksDetailsRepository.findAll();
        assertThat(banksDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
