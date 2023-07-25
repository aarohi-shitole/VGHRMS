package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.MasterPerformanceIndicator;
import com.techvg.hrms.domain.PerformanceIndicator;
import com.techvg.hrms.repository.PerformanceIndicatorRepository;
import com.techvg.hrms.service.criteria.PerformanceIndicatorCriteria;
import com.techvg.hrms.service.dto.PerformanceIndicatorDTO;
import com.techvg.hrms.service.mapper.PerformanceIndicatorMapper;
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
 * Integration tests for the {@link PerformanceIndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerformanceIndicatorResourceIT {

    private static final Long DEFAULT_DESIGNATION_ID = 1L;
    private static final Long UPDATED_DESIGNATION_ID = 2L;
    private static final Long SMALLER_DESIGNATION_ID = 1L - 1L;

    private static final String DEFAULT_INDICATOR_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_INDICATOR_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/performance-indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerformanceIndicatorRepository performanceIndicatorRepository;

    @Autowired
    private PerformanceIndicatorMapper performanceIndicatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerformanceIndicatorMockMvc;

    private PerformanceIndicator performanceIndicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceIndicator createEntity(EntityManager em) {
        PerformanceIndicator performanceIndicator = new PerformanceIndicator()
            .designationId(DEFAULT_DESIGNATION_ID)
            .indicatorValue(DEFAULT_INDICATOR_VALUE)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return performanceIndicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceIndicator createUpdatedEntity(EntityManager em) {
        PerformanceIndicator performanceIndicator = new PerformanceIndicator()
            .designationId(UPDATED_DESIGNATION_ID)
            .indicatorValue(UPDATED_INDICATOR_VALUE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return performanceIndicator;
    }

    @BeforeEach
    public void initTest() {
        performanceIndicator = createEntity(em);
    }

    @Test
    @Transactional
    void createPerformanceIndicator() throws Exception {
        int databaseSizeBeforeCreate = performanceIndicatorRepository.findAll().size();
        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);
        restPerformanceIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        PerformanceIndicator testPerformanceIndicator = performanceIndicatorList.get(performanceIndicatorList.size() - 1);
        assertThat(testPerformanceIndicator.getDesignationId()).isEqualTo(DEFAULT_DESIGNATION_ID);
        assertThat(testPerformanceIndicator.getIndicatorValue()).isEqualTo(DEFAULT_INDICATOR_VALUE);
        assertThat(testPerformanceIndicator.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerformanceIndicator.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPerformanceIndicator.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPerformanceIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPerformanceIndicatorWithExistingId() throws Exception {
        // Create the PerformanceIndicator with an existing ID
        performanceIndicator.setId(1L);
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        int databaseSizeBeforeCreate = performanceIndicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicators() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList
        restPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].designationId").value(hasItem(DEFAULT_DESIGNATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].indicatorValue").value(hasItem(DEFAULT_INDICATOR_VALUE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPerformanceIndicator() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get the performanceIndicator
        restPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, performanceIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(performanceIndicator.getId().intValue()))
            .andExpect(jsonPath("$.designationId").value(DEFAULT_DESIGNATION_ID.intValue()))
            .andExpect(jsonPath("$.indicatorValue").value(DEFAULT_INDICATOR_VALUE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPerformanceIndicatorsByIdFiltering() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        Long id = performanceIndicator.getId();

        defaultPerformanceIndicatorShouldBeFound("id.equals=" + id);
        defaultPerformanceIndicatorShouldNotBeFound("id.notEquals=" + id);

        defaultPerformanceIndicatorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPerformanceIndicatorShouldNotBeFound("id.greaterThan=" + id);

        defaultPerformanceIndicatorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPerformanceIndicatorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId equals to DEFAULT_DESIGNATION_ID
        defaultPerformanceIndicatorShouldBeFound("designationId.equals=" + DEFAULT_DESIGNATION_ID);

        // Get all the performanceIndicatorList where designationId equals to UPDATED_DESIGNATION_ID
        defaultPerformanceIndicatorShouldNotBeFound("designationId.equals=" + UPDATED_DESIGNATION_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsInShouldWork() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId in DEFAULT_DESIGNATION_ID or UPDATED_DESIGNATION_ID
        defaultPerformanceIndicatorShouldBeFound("designationId.in=" + DEFAULT_DESIGNATION_ID + "," + UPDATED_DESIGNATION_ID);

        // Get all the performanceIndicatorList where designationId equals to UPDATED_DESIGNATION_ID
        defaultPerformanceIndicatorShouldNotBeFound("designationId.in=" + UPDATED_DESIGNATION_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId is not null
        defaultPerformanceIndicatorShouldBeFound("designationId.specified=true");

        // Get all the performanceIndicatorList where designationId is null
        defaultPerformanceIndicatorShouldNotBeFound("designationId.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId is greater than or equal to DEFAULT_DESIGNATION_ID
        defaultPerformanceIndicatorShouldBeFound("designationId.greaterThanOrEqual=" + DEFAULT_DESIGNATION_ID);

        // Get all the performanceIndicatorList where designationId is greater than or equal to UPDATED_DESIGNATION_ID
        defaultPerformanceIndicatorShouldNotBeFound("designationId.greaterThanOrEqual=" + UPDATED_DESIGNATION_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId is less than or equal to DEFAULT_DESIGNATION_ID
        defaultPerformanceIndicatorShouldBeFound("designationId.lessThanOrEqual=" + DEFAULT_DESIGNATION_ID);

        // Get all the performanceIndicatorList where designationId is less than or equal to SMALLER_DESIGNATION_ID
        defaultPerformanceIndicatorShouldNotBeFound("designationId.lessThanOrEqual=" + SMALLER_DESIGNATION_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId is less than DEFAULT_DESIGNATION_ID
        defaultPerformanceIndicatorShouldNotBeFound("designationId.lessThan=" + DEFAULT_DESIGNATION_ID);

        // Get all the performanceIndicatorList where designationId is less than UPDATED_DESIGNATION_ID
        defaultPerformanceIndicatorShouldBeFound("designationId.lessThan=" + UPDATED_DESIGNATION_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByDesignationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where designationId is greater than DEFAULT_DESIGNATION_ID
        defaultPerformanceIndicatorShouldNotBeFound("designationId.greaterThan=" + DEFAULT_DESIGNATION_ID);

        // Get all the performanceIndicatorList where designationId is greater than SMALLER_DESIGNATION_ID
        defaultPerformanceIndicatorShouldBeFound("designationId.greaterThan=" + SMALLER_DESIGNATION_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByIndicatorValueIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where indicatorValue equals to DEFAULT_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldBeFound("indicatorValue.equals=" + DEFAULT_INDICATOR_VALUE);

        // Get all the performanceIndicatorList where indicatorValue equals to UPDATED_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldNotBeFound("indicatorValue.equals=" + UPDATED_INDICATOR_VALUE);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByIndicatorValueIsInShouldWork() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where indicatorValue in DEFAULT_INDICATOR_VALUE or UPDATED_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldBeFound("indicatorValue.in=" + DEFAULT_INDICATOR_VALUE + "," + UPDATED_INDICATOR_VALUE);

        // Get all the performanceIndicatorList where indicatorValue equals to UPDATED_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldNotBeFound("indicatorValue.in=" + UPDATED_INDICATOR_VALUE);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByIndicatorValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where indicatorValue is not null
        defaultPerformanceIndicatorShouldBeFound("indicatorValue.specified=true");

        // Get all the performanceIndicatorList where indicatorValue is null
        defaultPerformanceIndicatorShouldNotBeFound("indicatorValue.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByIndicatorValueContainsSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where indicatorValue contains DEFAULT_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldBeFound("indicatorValue.contains=" + DEFAULT_INDICATOR_VALUE);

        // Get all the performanceIndicatorList where indicatorValue contains UPDATED_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldNotBeFound("indicatorValue.contains=" + UPDATED_INDICATOR_VALUE);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByIndicatorValueNotContainsSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where indicatorValue does not contain DEFAULT_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldNotBeFound("indicatorValue.doesNotContain=" + DEFAULT_INDICATOR_VALUE);

        // Get all the performanceIndicatorList where indicatorValue does not contain UPDATED_INDICATOR_VALUE
        defaultPerformanceIndicatorShouldBeFound("indicatorValue.doesNotContain=" + UPDATED_INDICATOR_VALUE);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where status equals to DEFAULT_STATUS
        defaultPerformanceIndicatorShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the performanceIndicatorList where status equals to UPDATED_STATUS
        defaultPerformanceIndicatorShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPerformanceIndicatorShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the performanceIndicatorList where status equals to UPDATED_STATUS
        defaultPerformanceIndicatorShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where status is not null
        defaultPerformanceIndicatorShouldBeFound("status.specified=true");

        // Get all the performanceIndicatorList where status is null
        defaultPerformanceIndicatorShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByStatusContainsSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where status contains DEFAULT_STATUS
        defaultPerformanceIndicatorShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the performanceIndicatorList where status contains UPDATED_STATUS
        defaultPerformanceIndicatorShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where status does not contain DEFAULT_STATUS
        defaultPerformanceIndicatorShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the performanceIndicatorList where status does not contain UPDATED_STATUS
        defaultPerformanceIndicatorShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId equals to DEFAULT_COMPANY_ID
        defaultPerformanceIndicatorShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the performanceIndicatorList where companyId equals to UPDATED_COMPANY_ID
        defaultPerformanceIndicatorShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultPerformanceIndicatorShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the performanceIndicatorList where companyId equals to UPDATED_COMPANY_ID
        defaultPerformanceIndicatorShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId is not null
        defaultPerformanceIndicatorShouldBeFound("companyId.specified=true");

        // Get all the performanceIndicatorList where companyId is null
        defaultPerformanceIndicatorShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultPerformanceIndicatorShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the performanceIndicatorList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultPerformanceIndicatorShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultPerformanceIndicatorShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the performanceIndicatorList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultPerformanceIndicatorShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId is less than DEFAULT_COMPANY_ID
        defaultPerformanceIndicatorShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the performanceIndicatorList where companyId is less than UPDATED_COMPANY_ID
        defaultPerformanceIndicatorShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where companyId is greater than DEFAULT_COMPANY_ID
        defaultPerformanceIndicatorShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the performanceIndicatorList where companyId is greater than SMALLER_COMPANY_ID
        defaultPerformanceIndicatorShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPerformanceIndicatorShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the performanceIndicatorList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPerformanceIndicatorShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPerformanceIndicatorShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the performanceIndicatorList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPerformanceIndicatorShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModified is not null
        defaultPerformanceIndicatorShouldBeFound("lastModified.specified=true");

        // Get all the performanceIndicatorList where lastModified is null
        defaultPerformanceIndicatorShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the performanceIndicatorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the performanceIndicatorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModifiedBy is not null
        defaultPerformanceIndicatorShouldBeFound("lastModifiedBy.specified=true");

        // Get all the performanceIndicatorList where lastModifiedBy is null
        defaultPerformanceIndicatorShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the performanceIndicatorList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        // Get all the performanceIndicatorList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the performanceIndicatorList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPerformanceIndicatorShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPerformanceIndicatorsByMasterPerformanceIndicatorIsEqualToSomething() throws Exception {
        MasterPerformanceIndicator masterPerformanceIndicator;
        if (TestUtil.findAll(em, MasterPerformanceIndicator.class).isEmpty()) {
            performanceIndicatorRepository.saveAndFlush(performanceIndicator);
            masterPerformanceIndicator = MasterPerformanceIndicatorResourceIT.createEntity(em);
        } else {
            masterPerformanceIndicator = TestUtil.findAll(em, MasterPerformanceIndicator.class).get(0);
        }
        em.persist(masterPerformanceIndicator);
        em.flush();
        performanceIndicator.setMasterPerformanceIndicator(masterPerformanceIndicator);
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);
        Long masterPerformanceIndicatorId = masterPerformanceIndicator.getId();

        // Get all the performanceIndicatorList where masterPerformanceIndicator equals to masterPerformanceIndicatorId
        defaultPerformanceIndicatorShouldBeFound("masterPerformanceIndicatorId.equals=" + masterPerformanceIndicatorId);

        // Get all the performanceIndicatorList where masterPerformanceIndicator equals to (masterPerformanceIndicatorId + 1)
        defaultPerformanceIndicatorShouldNotBeFound("masterPerformanceIndicatorId.equals=" + (masterPerformanceIndicatorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerformanceIndicatorShouldBeFound(String filter) throws Exception {
        restPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].designationId").value(hasItem(DEFAULT_DESIGNATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].indicatorValue").value(hasItem(DEFAULT_INDICATOR_VALUE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerformanceIndicatorShouldNotBeFound(String filter) throws Exception {
        restPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerformanceIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerformanceIndicator() throws Exception {
        // Get the performanceIndicator
        restPerformanceIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerformanceIndicator() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();

        // Update the performanceIndicator
        PerformanceIndicator updatedPerformanceIndicator = performanceIndicatorRepository.findById(performanceIndicator.getId()).get();
        // Disconnect from session so that the updates on updatedPerformanceIndicator are not directly saved in db
        em.detach(updatedPerformanceIndicator);
        updatedPerformanceIndicator
            .designationId(UPDATED_DESIGNATION_ID)
            .indicatorValue(UPDATED_INDICATOR_VALUE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(updatedPerformanceIndicator);

        restPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
        PerformanceIndicator testPerformanceIndicator = performanceIndicatorList.get(performanceIndicatorList.size() - 1);
        assertThat(testPerformanceIndicator.getDesignationId()).isEqualTo(UPDATED_DESIGNATION_ID);
        assertThat(testPerformanceIndicator.getIndicatorValue()).isEqualTo(UPDATED_INDICATOR_VALUE);
        assertThat(testPerformanceIndicator.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerformanceIndicator.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPerformanceIndicator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPerformanceIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();
        performanceIndicator.setId(count.incrementAndGet());

        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();
        performanceIndicator.setId(count.incrementAndGet());

        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();
        performanceIndicator.setId(count.incrementAndGet());

        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerformanceIndicatorWithPatch() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();

        // Update the performanceIndicator using partial update
        PerformanceIndicator partialUpdatedPerformanceIndicator = new PerformanceIndicator();
        partialUpdatedPerformanceIndicator.setId(performanceIndicator.getId());

        partialUpdatedPerformanceIndicator
            .designationId(UPDATED_DESIGNATION_ID)
            .indicatorValue(UPDATED_INDICATOR_VALUE);
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceIndicator))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
        PerformanceIndicator testPerformanceIndicator = performanceIndicatorList.get(performanceIndicatorList.size() - 1);
        assertThat(testPerformanceIndicator.getDesignationId()).isEqualTo(UPDATED_DESIGNATION_ID);
        assertThat(testPerformanceIndicator.getIndicatorValue()).isEqualTo(UPDATED_INDICATOR_VALUE);
        assertThat(testPerformanceIndicator.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerformanceIndicator.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPerformanceIndicator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPerformanceIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePerformanceIndicatorWithPatch() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();

        // Update the performanceIndicator using partial update
        PerformanceIndicator partialUpdatedPerformanceIndicator = new PerformanceIndicator();
        partialUpdatedPerformanceIndicator.setId(performanceIndicator.getId());

        partialUpdatedPerformanceIndicator
            .designationId(UPDATED_DESIGNATION_ID)
            .indicatorValue(UPDATED_INDICATOR_VALUE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerformanceIndicator))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
        PerformanceIndicator testPerformanceIndicator = performanceIndicatorList.get(performanceIndicatorList.size() - 1);
        assertThat(testPerformanceIndicator.getDesignationId()).isEqualTo(UPDATED_DESIGNATION_ID);
        assertThat(testPerformanceIndicator.getIndicatorValue()).isEqualTo(UPDATED_INDICATOR_VALUE);
        assertThat(testPerformanceIndicator.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerformanceIndicator.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPerformanceIndicator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPerformanceIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();
        performanceIndicator.setId(count.incrementAndGet());

        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, performanceIndicatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();
        performanceIndicator.setId(count.incrementAndGet());

        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerformanceIndicator() throws Exception {
        int databaseSizeBeforeUpdate = performanceIndicatorRepository.findAll().size();
        performanceIndicator.setId(count.incrementAndGet());

        // Create the PerformanceIndicator
        PerformanceIndicatorDTO performanceIndicatorDTO = performanceIndicatorMapper.toDto(performanceIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(performanceIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceIndicator in the database
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerformanceIndicator() throws Exception {
        // Initialize the database
        performanceIndicatorRepository.saveAndFlush(performanceIndicator);

        int databaseSizeBeforeDelete = performanceIndicatorRepository.findAll().size();

        // Delete the performanceIndicator
        restPerformanceIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, performanceIndicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerformanceIndicator> performanceIndicatorList = performanceIndicatorRepository.findAll();
        assertThat(performanceIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
