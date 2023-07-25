package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.PfDetails;
import com.techvg.hrms.repository.PfDetailsRepository;
import com.techvg.hrms.service.criteria.PfDetailsCriteria;
import com.techvg.hrms.service.dto.PfDetailsDTO;
import com.techvg.hrms.service.mapper.PfDetailsMapper;
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
 * Integration tests for the {@link PfDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PfDetailsResourceIT {

    private static final Boolean DEFAULT_IS_PF_CONTRIBUTION = false;
    private static final Boolean UPDATED_IS_PF_CONTRIBUTION = true;

    private static final String DEFAULT_PF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PF_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_PF_RATE = 1D;
    private static final Double UPDATED_PF_RATE = 2D;
    private static final Double SMALLER_PF_RATE = 1D - 1D;

    private static final String DEFAULT_ADDITIONAL_PF_RATE = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_PF_RATE = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_PF_RATE = 1D;
    private static final Double UPDATED_TOTAL_PF_RATE = 2D;
    private static final Double SMALLER_TOTAL_PF_RATE = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/pf-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PfDetailsRepository pfDetailsRepository;

    @Autowired
    private PfDetailsMapper pfDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPfDetailsMockMvc;

    private PfDetails pfDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PfDetails createEntity(EntityManager em) {
        PfDetails pfDetails = new PfDetails()
            .isPfContribution(DEFAULT_IS_PF_CONTRIBUTION)
            .pfNumber(DEFAULT_PF_NUMBER)
            .pfRate(DEFAULT_PF_RATE)
            .additionalPfRate(DEFAULT_ADDITIONAL_PF_RATE)
            .totalPfRate(DEFAULT_TOTAL_PF_RATE)
            .employeeId(DEFAULT_EMPLOYE_ID)
            .reEnumerationId(DEFAULT_RE_ENUMERATION_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return pfDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PfDetails createUpdatedEntity(EntityManager em) {
        PfDetails pfDetails = new PfDetails()
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfNumber(UPDATED_PF_NUMBER)
            .pfRate(UPDATED_PF_RATE)
            .additionalPfRate(UPDATED_ADDITIONAL_PF_RATE)
            .totalPfRate(UPDATED_TOTAL_PF_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return pfDetails;
    }

    @BeforeEach
    public void initTest() {
        pfDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPfDetails() throws Exception {
        int databaseSizeBeforeCreate = pfDetailsRepository.findAll().size();
        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);
        restPfDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PfDetails testPfDetails = pfDetailsList.get(pfDetailsList.size() - 1);
        assertThat(testPfDetails.getIsPfContribution()).isEqualTo(DEFAULT_IS_PF_CONTRIBUTION);
        assertThat(testPfDetails.getPfNumber()).isEqualTo(DEFAULT_PF_NUMBER);
        assertThat(testPfDetails.getPfRate()).isEqualTo(DEFAULT_PF_RATE);
        assertThat(testPfDetails.getAdditionalPfRate()).isEqualTo(DEFAULT_ADDITIONAL_PF_RATE);
        assertThat(testPfDetails.getTotalPfRate()).isEqualTo(DEFAULT_TOTAL_PF_RATE);
        assertThat(testPfDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testPfDetails.getReEnumerationId()).isEqualTo(DEFAULT_RE_ENUMERATION_ID);
        assertThat(testPfDetails.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPfDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPfDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPfDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPfDetailsWithExistingId() throws Exception {
        // Create the PfDetails with an existing ID
        pfDetails.setId(1L);
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        int databaseSizeBeforeCreate = pfDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPfDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPfDetails() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList
        restPfDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pfDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPfContribution").value(hasItem(DEFAULT_IS_PF_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER)))
            .andExpect(jsonPath("$.[*].pfRate").value(hasItem(DEFAULT_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalPfRate").value(hasItem(DEFAULT_ADDITIONAL_PF_RATE)))
            .andExpect(jsonPath("$.[*].totalPfRate").value(hasItem(DEFAULT_TOTAL_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reEnumerationId").value(hasItem(DEFAULT_RE_ENUMERATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPfDetails() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get the pfDetails
        restPfDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, pfDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pfDetails.getId().intValue()))
            .andExpect(jsonPath("$.isPfContribution").value(DEFAULT_IS_PF_CONTRIBUTION.booleanValue()))
            .andExpect(jsonPath("$.pfNumber").value(DEFAULT_PF_NUMBER))
            .andExpect(jsonPath("$.pfRate").value(DEFAULT_PF_RATE.doubleValue()))
            .andExpect(jsonPath("$.additionalPfRate").value(DEFAULT_ADDITIONAL_PF_RATE))
            .andExpect(jsonPath("$.totalPfRate").value(DEFAULT_TOTAL_PF_RATE.doubleValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.reEnumerationId").value(DEFAULT_RE_ENUMERATION_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPfDetailsByIdFiltering() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        Long id = pfDetails.getId();

        defaultPfDetailsShouldBeFound("id.equals=" + id);
        defaultPfDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultPfDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPfDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultPfDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPfDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPfDetailsByIsPfContributionIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where isPfContribution equals to DEFAULT_IS_PF_CONTRIBUTION
        defaultPfDetailsShouldBeFound("isPfContribution.equals=" + DEFAULT_IS_PF_CONTRIBUTION);

        // Get all the pfDetailsList where isPfContribution equals to UPDATED_IS_PF_CONTRIBUTION
        defaultPfDetailsShouldNotBeFound("isPfContribution.equals=" + UPDATED_IS_PF_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllPfDetailsByIsPfContributionIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where isPfContribution in DEFAULT_IS_PF_CONTRIBUTION or UPDATED_IS_PF_CONTRIBUTION
        defaultPfDetailsShouldBeFound("isPfContribution.in=" + DEFAULT_IS_PF_CONTRIBUTION + "," + UPDATED_IS_PF_CONTRIBUTION);

        // Get all the pfDetailsList where isPfContribution equals to UPDATED_IS_PF_CONTRIBUTION
        defaultPfDetailsShouldNotBeFound("isPfContribution.in=" + UPDATED_IS_PF_CONTRIBUTION);
    }

    @Test
    @Transactional
    void getAllPfDetailsByIsPfContributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where isPfContribution is not null
        defaultPfDetailsShouldBeFound("isPfContribution.specified=true");

        // Get all the pfDetailsList where isPfContribution is null
        defaultPfDetailsShouldNotBeFound("isPfContribution.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfNumber equals to DEFAULT_PF_NUMBER
        defaultPfDetailsShouldBeFound("pfNumber.equals=" + DEFAULT_PF_NUMBER);

        // Get all the pfDetailsList where pfNumber equals to UPDATED_PF_NUMBER
        defaultPfDetailsShouldNotBeFound("pfNumber.equals=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfNumberIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfNumber in DEFAULT_PF_NUMBER or UPDATED_PF_NUMBER
        defaultPfDetailsShouldBeFound("pfNumber.in=" + DEFAULT_PF_NUMBER + "," + UPDATED_PF_NUMBER);

        // Get all the pfDetailsList where pfNumber equals to UPDATED_PF_NUMBER
        defaultPfDetailsShouldNotBeFound("pfNumber.in=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfNumber is not null
        defaultPfDetailsShouldBeFound("pfNumber.specified=true");

        // Get all the pfDetailsList where pfNumber is null
        defaultPfDetailsShouldNotBeFound("pfNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfNumberContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfNumber contains DEFAULT_PF_NUMBER
        defaultPfDetailsShouldBeFound("pfNumber.contains=" + DEFAULT_PF_NUMBER);

        // Get all the pfDetailsList where pfNumber contains UPDATED_PF_NUMBER
        defaultPfDetailsShouldNotBeFound("pfNumber.contains=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfNumberNotContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfNumber does not contain DEFAULT_PF_NUMBER
        defaultPfDetailsShouldNotBeFound("pfNumber.doesNotContain=" + DEFAULT_PF_NUMBER);

        // Get all the pfDetailsList where pfNumber does not contain UPDATED_PF_NUMBER
        defaultPfDetailsShouldBeFound("pfNumber.doesNotContain=" + UPDATED_PF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate equals to DEFAULT_PF_RATE
        defaultPfDetailsShouldBeFound("pfRate.equals=" + DEFAULT_PF_RATE);

        // Get all the pfDetailsList where pfRate equals to UPDATED_PF_RATE
        defaultPfDetailsShouldNotBeFound("pfRate.equals=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate in DEFAULT_PF_RATE or UPDATED_PF_RATE
        defaultPfDetailsShouldBeFound("pfRate.in=" + DEFAULT_PF_RATE + "," + UPDATED_PF_RATE);

        // Get all the pfDetailsList where pfRate equals to UPDATED_PF_RATE
        defaultPfDetailsShouldNotBeFound("pfRate.in=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate is not null
        defaultPfDetailsShouldBeFound("pfRate.specified=true");

        // Get all the pfDetailsList where pfRate is null
        defaultPfDetailsShouldNotBeFound("pfRate.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate is greater than or equal to DEFAULT_PF_RATE
        defaultPfDetailsShouldBeFound("pfRate.greaterThanOrEqual=" + DEFAULT_PF_RATE);

        // Get all the pfDetailsList where pfRate is greater than or equal to UPDATED_PF_RATE
        defaultPfDetailsShouldNotBeFound("pfRate.greaterThanOrEqual=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate is less than or equal to DEFAULT_PF_RATE
        defaultPfDetailsShouldBeFound("pfRate.lessThanOrEqual=" + DEFAULT_PF_RATE);

        // Get all the pfDetailsList where pfRate is less than or equal to SMALLER_PF_RATE
        defaultPfDetailsShouldNotBeFound("pfRate.lessThanOrEqual=" + SMALLER_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsLessThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate is less than DEFAULT_PF_RATE
        defaultPfDetailsShouldNotBeFound("pfRate.lessThan=" + DEFAULT_PF_RATE);

        // Get all the pfDetailsList where pfRate is less than UPDATED_PF_RATE
        defaultPfDetailsShouldBeFound("pfRate.lessThan=" + UPDATED_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByPfRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where pfRate is greater than DEFAULT_PF_RATE
        defaultPfDetailsShouldNotBeFound("pfRate.greaterThan=" + DEFAULT_PF_RATE);

        // Get all the pfDetailsList where pfRate is greater than SMALLER_PF_RATE
        defaultPfDetailsShouldBeFound("pfRate.greaterThan=" + SMALLER_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByAdditionalPfRateIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where additionalPfRate equals to DEFAULT_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldBeFound("additionalPfRate.equals=" + DEFAULT_ADDITIONAL_PF_RATE);

        // Get all the pfDetailsList where additionalPfRate equals to UPDATED_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("additionalPfRate.equals=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByAdditionalPfRateIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where additionalPfRate in DEFAULT_ADDITIONAL_PF_RATE or UPDATED_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldBeFound("additionalPfRate.in=" + DEFAULT_ADDITIONAL_PF_RATE + "," + UPDATED_ADDITIONAL_PF_RATE);

        // Get all the pfDetailsList where additionalPfRate equals to UPDATED_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("additionalPfRate.in=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByAdditionalPfRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where additionalPfRate is not null
        defaultPfDetailsShouldBeFound("additionalPfRate.specified=true");

        // Get all the pfDetailsList where additionalPfRate is null
        defaultPfDetailsShouldNotBeFound("additionalPfRate.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByAdditionalPfRateContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where additionalPfRate contains DEFAULT_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldBeFound("additionalPfRate.contains=" + DEFAULT_ADDITIONAL_PF_RATE);

        // Get all the pfDetailsList where additionalPfRate contains UPDATED_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("additionalPfRate.contains=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByAdditionalPfRateNotContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where additionalPfRate does not contain DEFAULT_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("additionalPfRate.doesNotContain=" + DEFAULT_ADDITIONAL_PF_RATE);

        // Get all the pfDetailsList where additionalPfRate does not contain UPDATED_ADDITIONAL_PF_RATE
        defaultPfDetailsShouldBeFound("additionalPfRate.doesNotContain=" + UPDATED_ADDITIONAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate equals to DEFAULT_TOTAL_PF_RATE
        defaultPfDetailsShouldBeFound("totalPfRate.equals=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the pfDetailsList where totalPfRate equals to UPDATED_TOTAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("totalPfRate.equals=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate in DEFAULT_TOTAL_PF_RATE or UPDATED_TOTAL_PF_RATE
        defaultPfDetailsShouldBeFound("totalPfRate.in=" + DEFAULT_TOTAL_PF_RATE + "," + UPDATED_TOTAL_PF_RATE);

        // Get all the pfDetailsList where totalPfRate equals to UPDATED_TOTAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("totalPfRate.in=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate is not null
        defaultPfDetailsShouldBeFound("totalPfRate.specified=true");

        // Get all the pfDetailsList where totalPfRate is null
        defaultPfDetailsShouldNotBeFound("totalPfRate.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate is greater than or equal to DEFAULT_TOTAL_PF_RATE
        defaultPfDetailsShouldBeFound("totalPfRate.greaterThanOrEqual=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the pfDetailsList where totalPfRate is greater than or equal to UPDATED_TOTAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("totalPfRate.greaterThanOrEqual=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate is less than or equal to DEFAULT_TOTAL_PF_RATE
        defaultPfDetailsShouldBeFound("totalPfRate.lessThanOrEqual=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the pfDetailsList where totalPfRate is less than or equal to SMALLER_TOTAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("totalPfRate.lessThanOrEqual=" + SMALLER_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsLessThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate is less than DEFAULT_TOTAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("totalPfRate.lessThan=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the pfDetailsList where totalPfRate is less than UPDATED_TOTAL_PF_RATE
        defaultPfDetailsShouldBeFound("totalPfRate.lessThan=" + UPDATED_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByTotalPfRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where totalPfRate is greater than DEFAULT_TOTAL_PF_RATE
        defaultPfDetailsShouldNotBeFound("totalPfRate.greaterThan=" + DEFAULT_TOTAL_PF_RATE);

        // Get all the pfDetailsList where totalPfRate is greater than SMALLER_TOTAL_PF_RATE
        defaultPfDetailsShouldBeFound("totalPfRate.greaterThan=" + SMALLER_TOTAL_PF_RATE);
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId equals to DEFAULT_EMPLOYE_ID
        defaultPfDetailsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the pfDetailsList where employeeId equals to UPDATED_EMPLOYE_ID
        defaultPfDetailsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultPfDetailsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the pfDetailsList where employeeId equals to UPDATED_EMPLOYE_ID
        defaultPfDetailsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId is not null
        defaultPfDetailsShouldBeFound("employeeId.specified=true");

        // Get all the pfDetailsList where employeeId is null
        defaultPfDetailsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultPfDetailsShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the pfDetailsList where employeeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultPfDetailsShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultPfDetailsShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the pfDetailsList where employeeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultPfDetailsShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId is less than DEFAULT_EMPLOYE_ID
        defaultPfDetailsShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the pfDetailsList where employeeId is less than UPDATED_EMPLOYE_ID
        defaultPfDetailsShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByemployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where employeeId is greater than DEFAULT_EMPLOYE_ID
        defaultPfDetailsShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the pfDetailsList where employeeId is greater than SMALLER_EMPLOYE_ID
        defaultPfDetailsShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId equals to DEFAULT_RE_ENUMERATION_ID
        defaultPfDetailsShouldBeFound("reEnumerationId.equals=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the pfDetailsList where reEnumerationId equals to UPDATED_RE_ENUMERATION_ID
        defaultPfDetailsShouldNotBeFound("reEnumerationId.equals=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId in DEFAULT_RE_ENUMERATION_ID or UPDATED_RE_ENUMERATION_ID
        defaultPfDetailsShouldBeFound("reEnumerationId.in=" + DEFAULT_RE_ENUMERATION_ID + "," + UPDATED_RE_ENUMERATION_ID);

        // Get all the pfDetailsList where reEnumerationId equals to UPDATED_RE_ENUMERATION_ID
        defaultPfDetailsShouldNotBeFound("reEnumerationId.in=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId is not null
        defaultPfDetailsShouldBeFound("reEnumerationId.specified=true");

        // Get all the pfDetailsList where reEnumerationId is null
        defaultPfDetailsShouldNotBeFound("reEnumerationId.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId is greater than or equal to DEFAULT_RE_ENUMERATION_ID
        defaultPfDetailsShouldBeFound("reEnumerationId.greaterThanOrEqual=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the pfDetailsList where reEnumerationId is greater than or equal to UPDATED_RE_ENUMERATION_ID
        defaultPfDetailsShouldNotBeFound("reEnumerationId.greaterThanOrEqual=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId is less than or equal to DEFAULT_RE_ENUMERATION_ID
        defaultPfDetailsShouldBeFound("reEnumerationId.lessThanOrEqual=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the pfDetailsList where reEnumerationId is less than or equal to SMALLER_RE_ENUMERATION_ID
        defaultPfDetailsShouldNotBeFound("reEnumerationId.lessThanOrEqual=" + SMALLER_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId is less than DEFAULT_RE_ENUMERATION_ID
        defaultPfDetailsShouldNotBeFound("reEnumerationId.lessThan=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the pfDetailsList where reEnumerationId is less than UPDATED_RE_ENUMERATION_ID
        defaultPfDetailsShouldBeFound("reEnumerationId.lessThan=" + UPDATED_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByReEnumerationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where reEnumerationId is greater than DEFAULT_RE_ENUMERATION_ID
        defaultPfDetailsShouldNotBeFound("reEnumerationId.greaterThan=" + DEFAULT_RE_ENUMERATION_ID);

        // Get all the pfDetailsList where reEnumerationId is greater than SMALLER_RE_ENUMERATION_ID
        defaultPfDetailsShouldBeFound("reEnumerationId.greaterThan=" + SMALLER_RE_ENUMERATION_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId equals to DEFAULT_COMPANY_ID
        defaultPfDetailsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the pfDetailsList where companyId equals to UPDATED_COMPANY_ID
        defaultPfDetailsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultPfDetailsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the pfDetailsList where companyId equals to UPDATED_COMPANY_ID
        defaultPfDetailsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId is not null
        defaultPfDetailsShouldBeFound("companyId.specified=true");

        // Get all the pfDetailsList where companyId is null
        defaultPfDetailsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultPfDetailsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the pfDetailsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultPfDetailsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultPfDetailsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the pfDetailsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultPfDetailsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId is less than DEFAULT_COMPANY_ID
        defaultPfDetailsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the pfDetailsList where companyId is less than UPDATED_COMPANY_ID
        defaultPfDetailsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultPfDetailsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the pfDetailsList where companyId is greater than SMALLER_COMPANY_ID
        defaultPfDetailsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPfDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where status equals to DEFAULT_STATUS
        defaultPfDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the pfDetailsList where status equals to UPDATED_STATUS
        defaultPfDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPfDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPfDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the pfDetailsList where status equals to UPDATED_STATUS
        defaultPfDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPfDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where status is not null
        defaultPfDetailsShouldBeFound("status.specified=true");

        // Get all the pfDetailsList where status is null
        defaultPfDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByStatusContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where status contains DEFAULT_STATUS
        defaultPfDetailsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the pfDetailsList where status contains UPDATED_STATUS
        defaultPfDetailsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPfDetailsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where status does not contain DEFAULT_STATUS
        defaultPfDetailsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the pfDetailsList where status does not contain UPDATED_STATUS
        defaultPfDetailsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPfDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the pfDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPfDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPfDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the pfDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPfDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModified is not null
        defaultPfDetailsShouldBeFound("lastModified.specified=true");

        // Get all the pfDetailsList where lastModified is null
        defaultPfDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPfDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the pfDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPfDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPfDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the pfDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPfDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModifiedBy is not null
        defaultPfDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the pfDetailsList where lastModifiedBy is null
        defaultPfDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPfDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the pfDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPfDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPfDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        // Get all the pfDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPfDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the pfDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPfDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPfDetailsShouldBeFound(String filter) throws Exception {
        restPfDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pfDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].isPfContribution").value(hasItem(DEFAULT_IS_PF_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER)))
            .andExpect(jsonPath("$.[*].pfRate").value(hasItem(DEFAULT_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalPfRate").value(hasItem(DEFAULT_ADDITIONAL_PF_RATE)))
            .andExpect(jsonPath("$.[*].totalPfRate").value(hasItem(DEFAULT_TOTAL_PF_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].reEnumerationId").value(hasItem(DEFAULT_RE_ENUMERATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPfDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPfDetailsShouldNotBeFound(String filter) throws Exception {
        restPfDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPfDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPfDetails() throws Exception {
        // Get the pfDetails
        restPfDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPfDetails() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();

        // Update the pfDetails
        PfDetails updatedPfDetails = pfDetailsRepository.findById(pfDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPfDetails are not directly saved in db
        em.detach(updatedPfDetails);
        updatedPfDetails
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfNumber(UPDATED_PF_NUMBER)
            .pfRate(UPDATED_PF_RATE)
            .additionalPfRate(UPDATED_ADDITIONAL_PF_RATE)
            .totalPfRate(UPDATED_TOTAL_PF_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(updatedPfDetails);

        restPfDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pfDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
        PfDetails testPfDetails = pfDetailsList.get(pfDetailsList.size() - 1);
        assertThat(testPfDetails.getIsPfContribution()).isEqualTo(UPDATED_IS_PF_CONTRIBUTION);
        assertThat(testPfDetails.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testPfDetails.getPfRate()).isEqualTo(UPDATED_PF_RATE);
        assertThat(testPfDetails.getAdditionalPfRate()).isEqualTo(UPDATED_ADDITIONAL_PF_RATE);
        assertThat(testPfDetails.getTotalPfRate()).isEqualTo(UPDATED_TOTAL_PF_RATE);
        assertThat(testPfDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testPfDetails.getReEnumerationId()).isEqualTo(UPDATED_RE_ENUMERATION_ID);
        assertThat(testPfDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPfDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPfDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPfDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPfDetails() throws Exception {
        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();
        pfDetails.setId(count.incrementAndGet());

        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPfDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pfDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPfDetails() throws Exception {
        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();
        pfDetails.setId(count.incrementAndGet());

        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPfDetails() throws Exception {
        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();
        pfDetails.setId(count.incrementAndGet());

        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePfDetailsWithPatch() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();

        // Update the pfDetails using partial update
        PfDetails partialUpdatedPfDetails = new PfDetails();
        partialUpdatedPfDetails.setId(pfDetails.getId());

        partialUpdatedPfDetails
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfRate(UPDATED_PF_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restPfDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPfDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPfDetails))
            )
            .andExpect(status().isOk());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
        PfDetails testPfDetails = pfDetailsList.get(pfDetailsList.size() - 1);
        assertThat(testPfDetails.getIsPfContribution()).isEqualTo(UPDATED_IS_PF_CONTRIBUTION);
        assertThat(testPfDetails.getPfNumber()).isEqualTo(DEFAULT_PF_NUMBER);
        assertThat(testPfDetails.getPfRate()).isEqualTo(UPDATED_PF_RATE);
        assertThat(testPfDetails.getAdditionalPfRate()).isEqualTo(DEFAULT_ADDITIONAL_PF_RATE);
        assertThat(testPfDetails.getTotalPfRate()).isEqualTo(DEFAULT_TOTAL_PF_RATE);
        assertThat(testPfDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testPfDetails.getReEnumerationId()).isEqualTo(UPDATED_RE_ENUMERATION_ID);
        assertThat(testPfDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPfDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPfDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPfDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePfDetailsWithPatch() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();

        // Update the pfDetails using partial update
        PfDetails partialUpdatedPfDetails = new PfDetails();
        partialUpdatedPfDetails.setId(pfDetails.getId());

        partialUpdatedPfDetails
            .isPfContribution(UPDATED_IS_PF_CONTRIBUTION)
            .pfNumber(UPDATED_PF_NUMBER)
            .pfRate(UPDATED_PF_RATE)
            .additionalPfRate(UPDATED_ADDITIONAL_PF_RATE)
            .totalPfRate(UPDATED_TOTAL_PF_RATE)
            .employeeId(UPDATED_EMPLOYE_ID)
            .reEnumerationId(UPDATED_RE_ENUMERATION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restPfDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPfDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPfDetails))
            )
            .andExpect(status().isOk());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
        PfDetails testPfDetails = pfDetailsList.get(pfDetailsList.size() - 1);
        assertThat(testPfDetails.getIsPfContribution()).isEqualTo(UPDATED_IS_PF_CONTRIBUTION);
        assertThat(testPfDetails.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testPfDetails.getPfRate()).isEqualTo(UPDATED_PF_RATE);
        assertThat(testPfDetails.getAdditionalPfRate()).isEqualTo(UPDATED_ADDITIONAL_PF_RATE);
        assertThat(testPfDetails.getTotalPfRate()).isEqualTo(UPDATED_TOTAL_PF_RATE);
        assertThat(testPfDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testPfDetails.getReEnumerationId()).isEqualTo(UPDATED_RE_ENUMERATION_ID);
        assertThat(testPfDetails.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPfDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPfDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPfDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPfDetails() throws Exception {
        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();
        pfDetails.setId(count.incrementAndGet());

        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPfDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pfDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPfDetails() throws Exception {
        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();
        pfDetails.setId(count.incrementAndGet());

        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPfDetails() throws Exception {
        int databaseSizeBeforeUpdate = pfDetailsRepository.findAll().size();
        pfDetails.setId(count.incrementAndGet());

        // Create the PfDetails
        PfDetailsDTO pfDetailsDTO = pfDetailsMapper.toDto(pfDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pfDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PfDetails in the database
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePfDetails() throws Exception {
        // Initialize the database
        pfDetailsRepository.saveAndFlush(pfDetails);

        int databaseSizeBeforeDelete = pfDetailsRepository.findAll().size();

        // Delete the pfDetails
        restPfDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, pfDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PfDetails> pfDetailsList = pfDetailsRepository.findAll();
        assertThat(pfDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
