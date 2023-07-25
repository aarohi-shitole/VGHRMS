package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.EsiDetails;
import com.techvg.hrms.repository.EsiDetailsRepository;
import com.techvg.hrms.service.criteria.EsiDetailsCriteria;
import com.techvg.hrms.service.dto.EsiDetailsDTO;
import com.techvg.hrms.service.mapper.EsiDetailsMapper;
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
 * Integration tests for the {@link EsiDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EsiDetailsResourceIT {

    private static final Boolean DEFAULT_IS_ESI_CONTRIBUTION = false;
    private static final Boolean UPDATED_IS_ESI_CONTRIBUTION = true;

    private static final String DEFAULT_ESI_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ESI_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_ESI_RATE = 1D;
    private static final Double UPDATED_ESI_RATE = 2D;
    private static final Double SMALLER_ESI_RATE = 1D - 1D;

    private static final String DEFAULT_ADDITIONAL_ESI_RATE = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_ESI_RATE = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_ESI_RATE = 1D;
    private static final Double UPDATED_TOTAL_ESI_RATE = 2D;
    private static final Double SMALLER_TOTAL_ESI_RATE = 1D - 1D;

    private static final Long DEFAULT_EMPLOYE_ID = 1L;
    private static final Long UPDATED_EMPLOYE_ID = 2L;
    private static final Long SMALLER_EMPLOYE_ID = 1L - 1L;

    private static final Long DEFAULT_RE_ENUMERATION_ID = 1L;
    private static final Long UPDATED_RE_ENUMERATION_ID = 2L;
    private static final Long SMALLER_RE_ENUMERATION_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/esi-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EsiDetailsRepository esiDetailsRepository;

    @Autowired
    private EsiDetailsMapper esiDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEsiDetailsMockMvc;

    private EsiDetails esiDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EsiDetails createEntity(EntityManager em) {
        EsiDetails esiDetails = new EsiDetails()
            .isEsiContribution(DEFAULT_IS_ESI_CONTRIBUTION)
            .esiNumber(DEFAULT_ESI_NUMBER)
            .esiRate(DEFAULT_ESI_RATE)
            .additionalEsiRate(DEFAULT_ADDITIONAL_ESI_RATE)
            .totalEsiRate(DEFAULT_TOTAL_ESI_RATE)
            .employeeId(DEFAULT_EMPLOYE_ID)
            .reEnumerationId(DEFAULT_RE_ENUMERATION_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return esiDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EsiDetails createUpdatedEntity(EntityManager em) {
        EsiDetails esiDetails = new EsiDetails()
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .esiNumber(UPDATED_ESI_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)
            .totalEsiRate(UPDATED_TOTAL_ESI_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return esiDetails;
    }

    @BeforeEach
    public void initTest() {
        esiDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEsiDetails() throws Exception {
        int databaseSizeBeforeCreate = esiDetailsRepository.findAll().size();
        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);
        restEsiDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EsiDetails testEsiDetails = esiDetailsList.get(esiDetailsList.size() - 1);
        assertThat(testEsiDetails.getIsEsiContribution()).isEqualTo(DEFAULT_IS_ESI_CONTRIBUTION);
        assertThat(testEsiDetails.getEsiNumber()).isEqualTo(DEFAULT_ESI_NUMBER);
        assertThat(testEsiDetails.getEsiRate()).isEqualTo(DEFAULT_ESI_RATE);
        assertThat(testEsiDetails.getAdditionalEsiRate()).isEqualTo(DEFAULT_ADDITIONAL_ESI_RATE);
        assertThat(testEsiDetails.getTotalEsiRate()).isEqualTo(DEFAULT_TOTAL_ESI_RATE);
        assertThat(testEsiDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testEsiDetails.getReEnumerationId()).isEqualTo(DEFAULT_RE_ENUMERATION_ID);
        assertThat(testEsiDetails.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEsiDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEsiDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEsiDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEsiDetailsWithExistingId() throws Exception {
        // Create the EsiDetails with an existing ID
        esiDetails.setId(1L);
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        int databaseSizeBeforeCreate = esiDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsiDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEsiDetails() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList
        restEsiDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esiDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].isEsiContribution").value(hasItem(DEFAULT_IS_ESI_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].esiNumber").value(hasItem(DEFAULT_ESI_NUMBER)))
            .andExpect(jsonPath("$.[*].esiRate").value(hasItem(DEFAULT_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalEsiRate").value(hasItem(DEFAULT_ADDITIONAL_ESI_RATE)))
            .andExpect(jsonPath("$.[*].totalEsiRate").value(hasItem(DEFAULT_TOTAL_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reEnumerationId").value(hasItem(DEFAULT_RE_ENUMERATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getEsiDetails() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get the esiDetails
        restEsiDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, esiDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(esiDetails.getId().intValue()))
            .andExpect(jsonPath("$.isEsiContribution").value(DEFAULT_IS_ESI_CONTRIBUTION.booleanValue()))
            .andExpect(jsonPath("$.esiNumber").value(DEFAULT_ESI_NUMBER))
            .andExpect(jsonPath("$.esiRate").value(DEFAULT_ESI_RATE.doubleValue()))
            .andExpect(jsonPath("$.additionalEsiRate").value(DEFAULT_ADDITIONAL_ESI_RATE))
            .andExpect(jsonPath("$.totalEsiRate").value(DEFAULT_TOTAL_ESI_RATE.doubleValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.reEnumerationId").value(DEFAULT_RE_ENUMERATION_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getEsiDetailsByIdFiltering() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        Long id = esiDetails.getId();

        defaultEsiDetailsShouldBeFound("id.equals=" + id);
        defaultEsiDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEsiDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEsiDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEsiDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEsiDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByIsEsiContributionIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where isEsiContribution equals to DEFAULT_IS_ESI_CONTRIBUTION
        defaultEsiDetailsShouldBeFound("isEsiContribution.equals=" + DEFAULT_IS_ESI_CONTRIBUTION);

        // Get all the esiDetailsList where isEsiContribution equals to UPDATED_IS_ESI_CONTRIBUTION
        defaultEsiDetailsShouldNotBeFound("isEsiContribution.equals=" + UPDATED_IS_ESI_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByIsEsiContributionIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where isEsiContribution in DEFAULT_IS_ESI_CONTRIBUTION or UPDATED_IS_ESI_CONTRIBUTION
        defaultEsiDetailsShouldBeFound("isEsiContribution.in=" + DEFAULT_IS_ESI_CONTRIBUTION + "," + UPDATED_IS_ESI_CONTRIBUTION);

        // Get all the esiDetailsList where isEsiContribution equals to UPDATED_IS_ESI_CONTRIBUTION
        defaultEsiDetailsShouldNotBeFound("isEsiContribution.in=" + UPDATED_IS_ESI_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByIsEsiContributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where isEsiContribution is not null
        defaultEsiDetailsShouldBeFound("isEsiContribution.specified=true");

        // Get all the esiDetailsList where isEsiContribution is null
        defaultEsiDetailsShouldNotBeFound("isEsiContribution.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiNumber equals to DEFAULT_ESI_NUMBER
        defaultEsiDetailsShouldBeFound("esiNumber.equals=" + DEFAULT_ESI_NUMBER);

        // Get all the esiDetailsList where esiNumber equals to UPDATED_ESI_NUMBER
        defaultEsiDetailsShouldNotBeFound("esiNumber.equals=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiNumberIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiNumber in DEFAULT_ESI_NUMBER or UPDATED_ESI_NUMBER
        defaultEsiDetailsShouldBeFound("esiNumber.in=" + DEFAULT_ESI_NUMBER + "," + UPDATED_ESI_NUMBER);

        // Get all the esiDetailsList where esiNumber equals to UPDATED_ESI_NUMBER
        defaultEsiDetailsShouldNotBeFound("esiNumber.in=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiNumber is not null
        defaultEsiDetailsShouldBeFound("esiNumber.specified=true");

        // Get all the esiDetailsList where esiNumber is null
        defaultEsiDetailsShouldNotBeFound("esiNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiNumberContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiNumber contains DEFAULT_ESI_NUMBER
        defaultEsiDetailsShouldBeFound("esiNumber.contains=" + DEFAULT_ESI_NUMBER);

        // Get all the esiDetailsList where esiNumber contains UPDATED_ESI_NUMBER
        defaultEsiDetailsShouldNotBeFound("esiNumber.contains=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiNumberNotContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiNumber does not contain DEFAULT_ESI_NUMBER
        defaultEsiDetailsShouldNotBeFound("esiNumber.doesNotContain=" + DEFAULT_ESI_NUMBER);

        // Get all the esiDetailsList where esiNumber does not contain UPDATED_ESI_NUMBER
        defaultEsiDetailsShouldBeFound("esiNumber.doesNotContain=" + UPDATED_ESI_NUMBER);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate equals to DEFAULT_ESI_RATE
        defaultEsiDetailsShouldBeFound("esiRate.equals=" + DEFAULT_ESI_RATE);

        // Get all the esiDetailsList where esiRate equals to UPDATED_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("esiRate.equals=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate in DEFAULT_ESI_RATE or UPDATED_ESI_RATE
        defaultEsiDetailsShouldBeFound("esiRate.in=" + DEFAULT_ESI_RATE + "," + UPDATED_ESI_RATE);

        // Get all the esiDetailsList where esiRate equals to UPDATED_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("esiRate.in=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate is not null
        defaultEsiDetailsShouldBeFound("esiRate.specified=true");

        // Get all the esiDetailsList where esiRate is null
        defaultEsiDetailsShouldNotBeFound("esiRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate is greater than or equal to DEFAULT_ESI_RATE
        defaultEsiDetailsShouldBeFound("esiRate.greaterThanOrEqual=" + DEFAULT_ESI_RATE);

        // Get all the esiDetailsList where esiRate is greater than or equal to UPDATED_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("esiRate.greaterThanOrEqual=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate is less than or equal to DEFAULT_ESI_RATE
        defaultEsiDetailsShouldBeFound("esiRate.lessThanOrEqual=" + DEFAULT_ESI_RATE);

        // Get all the esiDetailsList where esiRate is less than or equal to SMALLER_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("esiRate.lessThanOrEqual=" + SMALLER_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsLessThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate is less than DEFAULT_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("esiRate.lessThan=" + DEFAULT_ESI_RATE);

        // Get all the esiDetailsList where esiRate is less than UPDATED_ESI_RATE
        defaultEsiDetailsShouldBeFound("esiRate.lessThan=" + UPDATED_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByEsiRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where esiRate is greater than DEFAULT_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("esiRate.greaterThan=" + DEFAULT_ESI_RATE);

        // Get all the esiDetailsList where esiRate is greater than SMALLER_ESI_RATE
        defaultEsiDetailsShouldBeFound("esiRate.greaterThan=" + SMALLER_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByAdditionalEsiRateIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where additionalEsiRate equals to DEFAULT_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("additionalEsiRate.equals=" + DEFAULT_ADDITIONAL_ESI_RATE);

        // Get all the esiDetailsList where additionalEsiRate equals to UPDATED_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("additionalEsiRate.equals=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByAdditionalEsiRateIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where additionalEsiRate in DEFAULT_ADDITIONAL_ESI_RATE or UPDATED_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("additionalEsiRate.in=" + DEFAULT_ADDITIONAL_ESI_RATE + "," + UPDATED_ADDITIONAL_ESI_RATE);

        // Get all the esiDetailsList where additionalEsiRate equals to UPDATED_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("additionalEsiRate.in=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByAdditionalEsiRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where additionalEsiRate is not null
        defaultEsiDetailsShouldBeFound("additionalEsiRate.specified=true");

        // Get all the esiDetailsList where additionalEsiRate is null
        defaultEsiDetailsShouldNotBeFound("additionalEsiRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByAdditionalEsiRateContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where additionalEsiRate contains DEFAULT_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("additionalEsiRate.contains=" + DEFAULT_ADDITIONAL_ESI_RATE);

        // Get all the esiDetailsList where additionalEsiRate contains UPDATED_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("additionalEsiRate.contains=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByAdditionalEsiRateNotContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where additionalEsiRate does not contain DEFAULT_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("additionalEsiRate.doesNotContain=" + DEFAULT_ADDITIONAL_ESI_RATE);

        // Get all the esiDetailsList where additionalEsiRate does not contain UPDATED_ADDITIONAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("additionalEsiRate.doesNotContain=" + UPDATED_ADDITIONAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate equals to DEFAULT_TOTAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("totalEsiRate.equals=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the esiDetailsList where totalEsiRate equals to UPDATED_TOTAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.equals=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate in DEFAULT_TOTAL_ESI_RATE or UPDATED_TOTAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("totalEsiRate.in=" + DEFAULT_TOTAL_ESI_RATE + "," + UPDATED_TOTAL_ESI_RATE);

        // Get all the esiDetailsList where totalEsiRate equals to UPDATED_TOTAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.in=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate is not null
        defaultEsiDetailsShouldBeFound("totalEsiRate.specified=true");

        // Get all the esiDetailsList where totalEsiRate is null
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate is greater than or equal to DEFAULT_TOTAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("totalEsiRate.greaterThanOrEqual=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the esiDetailsList where totalEsiRate is greater than or equal to UPDATED_TOTAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.greaterThanOrEqual=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate is less than or equal to DEFAULT_TOTAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("totalEsiRate.lessThanOrEqual=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the esiDetailsList where totalEsiRate is less than or equal to SMALLER_TOTAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.lessThanOrEqual=" + SMALLER_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsLessThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate is less than DEFAULT_TOTAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.lessThan=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the esiDetailsList where totalEsiRate is less than UPDATED_TOTAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("totalEsiRate.lessThan=" + UPDATED_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByTotalEsiRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where totalEsiRate is greater than DEFAULT_TOTAL_ESI_RATE
        defaultEsiDetailsShouldNotBeFound("totalEsiRate.greaterThan=" + DEFAULT_TOTAL_ESI_RATE);

        // Get all the esiDetailsList where totalEsiRate is greater than SMALLER_TOTAL_ESI_RATE
        defaultEsiDetailsShouldBeFound("totalEsiRate.greaterThan=" + SMALLER_TOTAL_ESI_RATE);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId equals to DEFAULT_EMPLOYE_ID
        defaultEsiDetailsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the esiDetailsList where employeeId equals to UPDATED_EMPLOYE_ID
        defaultEsiDetailsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultEsiDetailsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the esiDetailsList where employeeId equals to UPDATED_EMPLOYE_ID
        defaultEsiDetailsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId is not null
        defaultEsiDetailsShouldBeFound("employeeId.specified=true");

        // Get all the esiDetailsList where employeeId is null
        defaultEsiDetailsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultEsiDetailsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the esiDetailsList where employeeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultEsiDetailsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultEsiDetailsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the esiDetailsList where employeeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultEsiDetailsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId is less than DEFAULT_EMPLOYE_ID
        defaultEsiDetailsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the esiDetailsList where employeeId is less than UPDATED_EMPLOYE_ID
        defaultEsiDetailsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByemployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where employeeId is greater than DEFAULT_EMPLOYE_ID
        defaultEsiDetailsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the esiDetailsList where employeeId is greater than SMALLER_EMPLOYE_ID
        defaultEsiDetailsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId equals to DEFAULT_RE_ENUMERATION_ID
        defaultEsiDetailsShouldBeFound("reEnumerationId.equals=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the esiDetailsList where reEnumerationId equals to UPDATED_RE_ENUMERATION_ID
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.equals=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId in DEFAULT_RE_ENUMERATION_ID or UPDATED_RE_ENUMERATION_ID
        defaultEsiDetailsShouldBeFound("reEnumerationId.in=" + DEFAULT_RE_ENUMERATION_ID + "," + UPDATED_RE_ENUMERATION_ID);

        // Get all the esiDetailsList where reEnumerationId equals to UPDATED_RE_ENUMERATION_ID
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.in=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId is not null
        defaultEsiDetailsShouldBeFound("reEnumerationId.specified=true");

        // Get all the esiDetailsList where reEnumerationId is null
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId is greater than or equal to DEFAULT_RE_ENUMERATION_ID
        defaultEsiDetailsShouldBeFound("reEnumerationId.greaterThanOrEqual=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the esiDetailsList where reEnumerationId is greater than or equal to UPDATED_RE_ENUMERATION_ID
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.greaterThanOrEqual=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId is less than or equal to DEFAULT_RE_ENUMERATION_ID
        defaultEsiDetailsShouldBeFound("reEnumerationId.lessThanOrEqual=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the esiDetailsList where reEnumerationId is less than or equal to SMALLER_RE_ENUMERATION_ID
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.lessThanOrEqual=" + SMALLER_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId is less than DEFAULT_RE_ENUMERATION_ID
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.lessThan=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the esiDetailsList where reEnumerationId is less than UPDATED_RE_ENUMERATION_ID
        defaultEsiDetailsShouldBeFound("reEnumerationId.lessThan=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByReEnumerationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where reEnumerationId is greater than DEFAULT_RE_ENUMERATION_ID
        defaultEsiDetailsShouldNotBeFound("reEnumerationId.greaterThan=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the esiDetailsList where reEnumerationId is greater than SMALLER_RE_ENUMERATION_ID
        defaultEsiDetailsShouldBeFound("reEnumerationId.greaterThan=" + SMALLER_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId equals to DEFAULT_COMPANY_ID
        defaultEsiDetailsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the esiDetailsList where companyId equals to UPDATED_COMPANY_ID
        defaultEsiDetailsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultEsiDetailsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the esiDetailsList where companyId equals to UPDATED_COMPANY_ID
        defaultEsiDetailsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId is not null
        defaultEsiDetailsShouldBeFound("companyId.specified=true");

        // Get all the esiDetailsList where companyId is null
        defaultEsiDetailsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultEsiDetailsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the esiDetailsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultEsiDetailsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultEsiDetailsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the esiDetailsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultEsiDetailsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId is less than DEFAULT_COMPANY_ID
        defaultEsiDetailsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the esiDetailsList where companyId is less than UPDATED_COMPANY_ID
        defaultEsiDetailsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultEsiDetailsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the esiDetailsList where companyId is greater than SMALLER_COMPANY_ID
        defaultEsiDetailsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where status equals to DEFAULT_STATUS
        defaultEsiDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the esiDetailsList where status equals to UPDATED_STATUS
        defaultEsiDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEsiDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the esiDetailsList where status equals to UPDATED_STATUS
        defaultEsiDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where status is not null
        defaultEsiDetailsShouldBeFound("status.specified=true");

        // Get all the esiDetailsList where status is null
        defaultEsiDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByStatusContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where status contains DEFAULT_STATUS
        defaultEsiDetailsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the esiDetailsList where status contains UPDATED_STATUS
        defaultEsiDetailsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where status does not contain DEFAULT_STATUS
        defaultEsiDetailsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the esiDetailsList where status does not contain UPDATED_STATUS
        defaultEsiDetailsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEsiDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the esiDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEsiDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEsiDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the esiDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEsiDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModified is not null
        defaultEsiDetailsShouldBeFound("lastModified.specified=true");

        // Get all the esiDetailsList where lastModified is null
        defaultEsiDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEsiDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the esiDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEsiDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEsiDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the esiDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEsiDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModifiedBy is not null
        defaultEsiDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the esiDetailsList where lastModifiedBy is null
        defaultEsiDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEsiDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the esiDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEsiDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEsiDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        // Get all the esiDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEsiDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the esiDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEsiDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEsiDetailsShouldBeFound(String filter) throws Exception {
        restEsiDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esiDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].isEsiContribution").value(hasItem(DEFAULT_IS_ESI_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].esiNumber").value(hasItem(DEFAULT_ESI_NUMBER)))
            .andExpect(jsonPath("$.[*].esiRate").value(hasItem(DEFAULT_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalEsiRate").value(hasItem(DEFAULT_ADDITIONAL_ESI_RATE)))
            .andExpect(jsonPath("$.[*].totalEsiRate").value(hasItem(DEFAULT_TOTAL_ESI_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reEnumerationId").value(hasItem(DEFAULT_RE_ENUMERATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restEsiDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEsiDetailsShouldNotBeFound(String filter) throws Exception {
        restEsiDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEsiDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEsiDetails() throws Exception {
        // Get the esiDetails
        restEsiDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEsiDetails() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();

        // Update the esiDetails
        EsiDetails updatedEsiDetails = esiDetailsRepository.findById(esiDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEsiDetails are not directly saved in db
        em.detach(updatedEsiDetails);
        updatedEsiDetails
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .esiNumber(UPDATED_ESI_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)
            .totalEsiRate(UPDATED_TOTAL_ESI_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(updatedEsiDetails);

        restEsiDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, esiDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
        EsiDetails testEsiDetails = esiDetailsList.get(esiDetailsList.size() - 1);
        assertThat(testEsiDetails.getIsEsiContribution()).isEqualTo(UPDATED_IS_ESI_CONTRIBUTION);
        assertThat(testEsiDetails.getEsiNumber()).isEqualTo(UPDATED_ESI_NUMBER);
        assertThat(testEsiDetails.getEsiRate()).isEqualTo(UPDATED_ESI_RATE);
        assertThat(testEsiDetails.getAdditionalEsiRate()).isEqualTo(UPDATED_ADDITIONAL_ESI_RATE);
        assertThat(testEsiDetails.getTotalEsiRate()).isEqualTo(UPDATED_TOTAL_ESI_RATE);
        assertThat(testEsiDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testEsiDetails.getReEnumerationId()).isEqualTo(UPDATED_RE_ENUMERATION_ID);
        assertThat(testEsiDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEsiDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEsiDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEsiDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEsiDetails() throws Exception {
        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();
        esiDetails.setId(count.incrementAndGet());

        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsiDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, esiDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEsiDetails() throws Exception {
        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();
        esiDetails.setId(count.incrementAndGet());

        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsiDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEsiDetails() throws Exception {
        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();
        esiDetails.setId(count.incrementAndGet());

        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsiDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEsiDetailsWithPatch() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();

        // Update the esiDetails using partial update
        EsiDetails partialUpdatedEsiDetails = new EsiDetails();
        partialUpdatedEsiDetails.setId(esiDetails.getId());

        partialUpdatedEsiDetails
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restEsiDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEsiDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEsiDetails))
            )
            .andExpect(status().isOk());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
        EsiDetails testEsiDetails = esiDetailsList.get(esiDetailsList.size() - 1);
        assertThat(testEsiDetails.getIsEsiContribution()).isEqualTo(UPDATED_IS_ESI_CONTRIBUTION);
        assertThat(testEsiDetails.getEsiNumber()).isEqualTo(DEFAULT_ESI_NUMBER);
        assertThat(testEsiDetails.getEsiRate()).isEqualTo(DEFAULT_ESI_RATE);
        assertThat(testEsiDetails.getAdditionalEsiRate()).isEqualTo(UPDATED_ADDITIONAL_ESI_RATE);
        assertThat(testEsiDetails.getTotalEsiRate()).isEqualTo(DEFAULT_TOTAL_ESI_RATE);
        assertThat(testEsiDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testEsiDetails.getReEnumerationId()).isEqualTo(DEFAULT_RE_ENUMERATION_ID);
        assertThat(testEsiDetails.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEsiDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEsiDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEsiDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEsiDetailsWithPatch() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();

        // Update the esiDetails using partial update
        EsiDetails partialUpdatedEsiDetails = new EsiDetails();
        partialUpdatedEsiDetails.setId(esiDetails.getId());

        partialUpdatedEsiDetails
            .isEsiContribution(UPDATED_IS_ESI_CONTRIBUTION)
            .esiNumber(UPDATED_ESI_NUMBER)
            .esiRate(UPDATED_ESI_RATE)
            .additionalEsiRate(UPDATED_ADDITIONAL_ESI_RATE)
            .totalEsiRate(UPDATED_TOTAL_ESI_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restEsiDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEsiDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEsiDetails))
            )
            .andExpect(status().isOk());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
        EsiDetails testEsiDetails = esiDetailsList.get(esiDetailsList.size() - 1);
        assertThat(testEsiDetails.getIsEsiContribution()).isEqualTo(UPDATED_IS_ESI_CONTRIBUTION);
        assertThat(testEsiDetails.getEsiNumber()).isEqualTo(UPDATED_ESI_NUMBER);
        assertThat(testEsiDetails.getEsiRate()).isEqualTo(UPDATED_ESI_RATE);
        assertThat(testEsiDetails.getAdditionalEsiRate()).isEqualTo(UPDATED_ADDITIONAL_ESI_RATE);
        assertThat(testEsiDetails.getTotalEsiRate()).isEqualTo(UPDATED_TOTAL_ESI_RATE);
        assertThat(testEsiDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testEsiDetails.getReEnumerationId()).isEqualTo(UPDATED_RE_ENUMERATION_ID);
        assertThat(testEsiDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEsiDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEsiDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEsiDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEsiDetails() throws Exception {
        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();
        esiDetails.setId(count.incrementAndGet());

        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsiDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, esiDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEsiDetails() throws Exception {
        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();
        esiDetails.setId(count.incrementAndGet());

        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsiDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEsiDetails() throws Exception {
        int databaseSizeBeforeUpdate = esiDetailsRepository.findAll().size();
        esiDetails.setId(count.incrementAndGet());

        // Create the EsiDetails
        EsiDetailsDTO esiDetailsDTO = esiDetailsMapper.toDto(esiDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsiDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(esiDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EsiDetails in the database
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEsiDetails() throws Exception {
        // Initialize the database
        esiDetailsRepository.saveAndFlush(esiDetails);

        int databaseSizeBeforeDelete = esiDetailsRepository.findAll().size();

        // Delete the esiDetails
        restEsiDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, esiDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EsiDetails> esiDetailsList = esiDetailsRepository.findAll();
        assertThat(esiDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
