package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.OverTime;
import com.techvg.hrms.repository.OverTimeRepository;
import com.techvg.hrms.service.criteria.OverTimeCriteria;
import com.techvg.hrms.service.dto.OverTimeDTO;
import com.techvg.hrms.service.mapper.OverTimeMapper;
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
 * Integration tests for the {@link OverTimeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OverTimeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RATE_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;
    private static final Double SMALLER_RATE = 1D - 1D;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/over-times";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OverTimeRepository overTimeRepository;

    @Autowired
    private OverTimeMapper overTimeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOverTimeMockMvc;

    private OverTime overTime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OverTime createEntity(EntityManager em) {
        OverTime overTime = new OverTime()
            .name(DEFAULT_NAME)
            .rateType(DEFAULT_RATE_TYPE)
            .rate(DEFAULT_RATE)
            .status(DEFAULT_STATUS);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return overTime;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OverTime createUpdatedEntity(EntityManager em) {
        OverTime overTime = new OverTime()
            .name(UPDATED_NAME)
            .rateType(UPDATED_RATE_TYPE)
            .rate(UPDATED_RATE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return overTime;
    }

    @BeforeEach
    public void initTest() {
        overTime = createEntity(em);
    }

    @Test
    @Transactional
    void createOverTime() throws Exception {
        int databaseSizeBeforeCreate = overTimeRepository.findAll().size();
        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);
        restOverTimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeCreate + 1);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOverTime.getRateType()).isEqualTo(DEFAULT_RATE_TYPE);
        assertThat(testOverTime.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testOverTime.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOverTime.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testOverTime.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOverTime.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createOverTimeWithExistingId() throws Exception {
        // Create the OverTime with an existing ID
        overTime.setId(1L);
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        int databaseSizeBeforeCreate = overTimeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverTimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOverTimes() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList
        restOverTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rateType").value(hasItem(DEFAULT_RATE_TYPE)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get the overTime
        restOverTimeMockMvc
            .perform(get(ENTITY_API_URL_ID, overTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(overTime.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rateType").value(DEFAULT_RATE_TYPE))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getOverTimesByIdFiltering() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        Long id = overTime.getId();

        defaultOverTimeShouldBeFound("id.equals=" + id);
        defaultOverTimeShouldNotBeFound("id.notEquals=" + id);

        defaultOverTimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOverTimeShouldNotBeFound("id.greaterThan=" + id);

        defaultOverTimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOverTimeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOverTimesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where name equals to DEFAULT_NAME
        defaultOverTimeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the overTimeList where name equals to UPDATED_NAME
        defaultOverTimeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOverTimesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOverTimeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the overTimeList where name equals to UPDATED_NAME
        defaultOverTimeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOverTimesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where name is not null
        defaultOverTimeShouldBeFound("name.specified=true");

        // Get all the overTimeList where name is null
        defaultOverTimeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByNameContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where name contains DEFAULT_NAME
        defaultOverTimeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the overTimeList where name contains UPDATED_NAME
        defaultOverTimeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOverTimesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where name does not contain DEFAULT_NAME
        defaultOverTimeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the overTimeList where name does not contain UPDATED_NAME
        defaultOverTimeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rateType equals to DEFAULT_RATE_TYPE
        defaultOverTimeShouldBeFound("rateType.equals=" + DEFAULT_RATE_TYPE);

        // Get all the overTimeList where rateType equals to UPDATED_RATE_TYPE
        defaultOverTimeShouldNotBeFound("rateType.equals=" + UPDATED_RATE_TYPE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateTypeIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rateType in DEFAULT_RATE_TYPE or UPDATED_RATE_TYPE
        defaultOverTimeShouldBeFound("rateType.in=" + DEFAULT_RATE_TYPE + "," + UPDATED_RATE_TYPE);

        // Get all the overTimeList where rateType equals to UPDATED_RATE_TYPE
        defaultOverTimeShouldNotBeFound("rateType.in=" + UPDATED_RATE_TYPE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rateType is not null
        defaultOverTimeShouldBeFound("rateType.specified=true");

        // Get all the overTimeList where rateType is null
        defaultOverTimeShouldNotBeFound("rateType.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByRateTypeContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rateType contains DEFAULT_RATE_TYPE
        defaultOverTimeShouldBeFound("rateType.contains=" + DEFAULT_RATE_TYPE);

        // Get all the overTimeList where rateType contains UPDATED_RATE_TYPE
        defaultOverTimeShouldNotBeFound("rateType.contains=" + UPDATED_RATE_TYPE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateTypeNotContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rateType does not contain DEFAULT_RATE_TYPE
        defaultOverTimeShouldNotBeFound("rateType.doesNotContain=" + DEFAULT_RATE_TYPE);

        // Get all the overTimeList where rateType does not contain UPDATED_RATE_TYPE
        defaultOverTimeShouldBeFound("rateType.doesNotContain=" + UPDATED_RATE_TYPE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate equals to DEFAULT_RATE
        defaultOverTimeShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the overTimeList where rate equals to UPDATED_RATE
        defaultOverTimeShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultOverTimeShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the overTimeList where rate equals to UPDATED_RATE
        defaultOverTimeShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate is not null
        defaultOverTimeShouldBeFound("rate.specified=true");

        // Get all the overTimeList where rate is null
        defaultOverTimeShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate is greater than or equal to DEFAULT_RATE
        defaultOverTimeShouldBeFound("rate.greaterThanOrEqual=" + DEFAULT_RATE);

        // Get all the overTimeList where rate is greater than or equal to UPDATED_RATE
        defaultOverTimeShouldNotBeFound("rate.greaterThanOrEqual=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate is less than or equal to DEFAULT_RATE
        defaultOverTimeShouldBeFound("rate.lessThanOrEqual=" + DEFAULT_RATE);

        // Get all the overTimeList where rate is less than or equal to SMALLER_RATE
        defaultOverTimeShouldNotBeFound("rate.lessThanOrEqual=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate is less than DEFAULT_RATE
        defaultOverTimeShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the overTimeList where rate is less than UPDATED_RATE
        defaultOverTimeShouldBeFound("rate.lessThan=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    void getAllOverTimesByRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where rate is greater than DEFAULT_RATE
        defaultOverTimeShouldNotBeFound("rate.greaterThan=" + DEFAULT_RATE);

        // Get all the overTimeList where rate is greater than SMALLER_RATE
        defaultOverTimeShouldBeFound("rate.greaterThan=" + SMALLER_RATE);
    }

    @Test
    @Transactional
    void getAllOverTimesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where status equals to DEFAULT_STATUS
        defaultOverTimeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the overTimeList where status equals to UPDATED_STATUS
        defaultOverTimeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOverTimesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOverTimeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the overTimeList where status equals to UPDATED_STATUS
        defaultOverTimeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOverTimesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where status is not null
        defaultOverTimeShouldBeFound("status.specified=true");

        // Get all the overTimeList where status is null
        defaultOverTimeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByStatusContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where status contains DEFAULT_STATUS
        defaultOverTimeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the overTimeList where status contains UPDATED_STATUS
        defaultOverTimeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOverTimesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where status does not contain DEFAULT_STATUS
        defaultOverTimeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the overTimeList where status does not contain UPDATED_STATUS
        defaultOverTimeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId equals to DEFAULT_COMPANY_ID
        defaultOverTimeShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the overTimeList where companyId equals to UPDATED_COMPANY_ID
        defaultOverTimeShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultOverTimeShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the overTimeList where companyId equals to UPDATED_COMPANY_ID
        defaultOverTimeShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId is not null
        defaultOverTimeShouldBeFound("companyId.specified=true");

        // Get all the overTimeList where companyId is null
        defaultOverTimeShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultOverTimeShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the overTimeList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultOverTimeShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultOverTimeShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the overTimeList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultOverTimeShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId is less than DEFAULT_COMPANY_ID
        defaultOverTimeShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the overTimeList where companyId is less than UPDATED_COMPANY_ID
        defaultOverTimeShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllOverTimesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where companyId is greater than DEFAULT_COMPANY_ID
        defaultOverTimeShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the overTimeList where companyId is greater than SMALLER_COMPANY_ID
        defaultOverTimeShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultOverTimeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the overTimeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOverTimeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultOverTimeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the overTimeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOverTimeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModified is not null
        defaultOverTimeShouldBeFound("lastModified.specified=true");

        // Get all the overTimeList where lastModified is null
        defaultOverTimeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOverTimeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the overTimeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOverTimeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOverTimeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the overTimeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOverTimeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModifiedBy is not null
        defaultOverTimeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the overTimeList where lastModifiedBy is null
        defaultOverTimeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOverTimeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the overTimeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOverTimeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOverTimesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOverTimeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the overTimeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOverTimeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOverTimeShouldBeFound(String filter) throws Exception {
        restOverTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rateType").value(hasItem(DEFAULT_RATE_TYPE)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restOverTimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOverTimeShouldNotBeFound(String filter) throws Exception {
        restOverTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOverTimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOverTime() throws Exception {
        // Get the overTime
        restOverTimeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // Update the overTime
        OverTime updatedOverTime = overTimeRepository.findById(overTime.getId()).get();
        // Disconnect from session so that the updates on updatedOverTime are not directly saved in db
        em.detach(updatedOverTime);
        updatedOverTime
            .name(UPDATED_NAME)
            .rateType(UPDATED_RATE_TYPE)
            .rate(UPDATED_RATE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(updatedOverTime);

        restOverTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overTimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(overTimeDTO))
            )
            .andExpect(status().isOk());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOverTime.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testOverTime.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testOverTime.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOverTime.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOverTime.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOverTime.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();
        overTime.setId(count.incrementAndGet());

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, overTimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(overTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();
        overTime.setId(count.incrementAndGet());

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(overTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();
        overTime.setId(count.incrementAndGet());

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverTimeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOverTimeWithPatch() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // Update the overTime using partial update
        OverTime partialUpdatedOverTime = new OverTime();
        partialUpdatedOverTime.setId(overTime.getId());

        partialUpdatedOverTime
            .rateType(UPDATED_RATE_TYPE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restOverTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverTime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOverTime))
            )
            .andExpect(status().isOk());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOverTime.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testOverTime.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testOverTime.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOverTime.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOverTime.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOverTime.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateOverTimeWithPatch() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // Update the overTime using partial update
        OverTime partialUpdatedOverTime = new OverTime();
        partialUpdatedOverTime.setId(overTime.getId());

        partialUpdatedOverTime
            .name(UPDATED_NAME)
            .rateType(UPDATED_RATE_TYPE)
            .rate(UPDATED_RATE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restOverTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOverTime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOverTime))
            )
            .andExpect(status().isOk());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOverTime.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testOverTime.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testOverTime.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOverTime.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOverTime.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOverTime.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();
        overTime.setId(count.incrementAndGet());

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, overTimeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(overTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();
        overTime.setId(count.incrementAndGet());

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(overTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();
        overTime.setId(count.incrementAndGet());

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOverTimeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(overTimeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        int databaseSizeBeforeDelete = overTimeRepository.findAll().size();

        // Delete the overTime
        restOverTimeMockMvc
            .perform(delete(ENTITY_API_URL_ID, overTime.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
