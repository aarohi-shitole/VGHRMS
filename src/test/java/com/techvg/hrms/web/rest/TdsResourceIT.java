package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Tds;
import com.techvg.hrms.repository.TdsRepository;
import com.techvg.hrms.service.criteria.TdsCriteria;
import com.techvg.hrms.service.dto.TdsDTO;
import com.techvg.hrms.service.mapper.TdsMapper;
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
 * Integration tests for the {@link TdsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TdsResourceIT {

    private static final Instant DEFAULT_SALARY_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALARY_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SALARY_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SALARY_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;
    private static final Double SMALLER_PERCENTAGE = 1D - 1D;

    private static final Long DEFAULT_SALARY_SETTING_ID = 1L;
    private static final Long UPDATED_SALARY_SETTING_ID = 2L;
    private static final Long SMALLER_SALARY_SETTING_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TdsRepository tdsRepository;

    @Autowired
    private TdsMapper tdsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTdsMockMvc;

    private Tds tds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tds createEntity(EntityManager em) {
        Tds tds = new Tds()
            .salaryFrom(DEFAULT_SALARY_FROM)
            .salaryTo(DEFAULT_SALARY_TO)
            .percentage(DEFAULT_PERCENTAGE)
            .salarySettingId(DEFAULT_SALARY_SETTING_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return tds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tds createUpdatedEntity(EntityManager em) {
        Tds tds = new Tds()
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .percentage(UPDATED_PERCENTAGE)
            .salarySettingId(UPDATED_SALARY_SETTING_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return tds;
    }

    @BeforeEach
    public void initTest() {
        tds = createEntity(em);
    }

    @Test
    @Transactional
    void createTds() throws Exception {
        int databaseSizeBeforeCreate = tdsRepository.findAll().size();
        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);
        restTdsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tdsDTO)))
            .andExpect(status().isCreated());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeCreate + 1);
        Tds testTds = tdsList.get(tdsList.size() - 1);
        assertThat(testTds.getSalaryFrom()).isEqualTo(DEFAULT_SALARY_FROM);
        assertThat(testTds.getSalaryTo()).isEqualTo(DEFAULT_SALARY_TO);
        assertThat(testTds.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testTds.getSalarySettingId()).isEqualTo(DEFAULT_SALARY_SETTING_ID);
        assertThat(testTds.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTds.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTds.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTds.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTdsWithExistingId() throws Exception {
        // Create the Tds with an existing ID
        tds.setId(1L);
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        int databaseSizeBeforeCreate = tdsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTdsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tdsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTds() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList
        restTdsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tds.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryFrom").value(hasItem(DEFAULT_SALARY_FROM.toString())))
            .andExpect(jsonPath("$.[*].salaryTo").value(hasItem(DEFAULT_SALARY_TO.toString())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].salarySettingId").value(hasItem(DEFAULT_SALARY_SETTING_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTds() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get the tds
        restTdsMockMvc
            .perform(get(ENTITY_API_URL_ID, tds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tds.getId().intValue()))
            .andExpect(jsonPath("$.salaryFrom").value(DEFAULT_SALARY_FROM.toString()))
            .andExpect(jsonPath("$.salaryTo").value(DEFAULT_SALARY_TO.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.salarySettingId").value(DEFAULT_SALARY_SETTING_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTdsByIdFiltering() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        Long id = tds.getId();

        defaultTdsShouldBeFound("id.equals=" + id);
        defaultTdsShouldNotBeFound("id.notEquals=" + id);

        defaultTdsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTdsShouldNotBeFound("id.greaterThan=" + id);

        defaultTdsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTdsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTdsBySalaryFromIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salaryFrom equals to DEFAULT_SALARY_FROM
        defaultTdsShouldBeFound("salaryFrom.equals=" + DEFAULT_SALARY_FROM);

        // Get all the tdsList where salaryFrom equals to UPDATED_SALARY_FROM
        defaultTdsShouldNotBeFound("salaryFrom.equals=" + UPDATED_SALARY_FROM);
    }

    @Test
    @Transactional
    void getAllTdsBySalaryFromIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salaryFrom in DEFAULT_SALARY_FROM or UPDATED_SALARY_FROM
        defaultTdsShouldBeFound("salaryFrom.in=" + DEFAULT_SALARY_FROM + "," + UPDATED_SALARY_FROM);

        // Get all the tdsList where salaryFrom equals to UPDATED_SALARY_FROM
        defaultTdsShouldNotBeFound("salaryFrom.in=" + UPDATED_SALARY_FROM);
    }

    @Test
    @Transactional
    void getAllTdsBySalaryFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salaryFrom is not null
        defaultTdsShouldBeFound("salaryFrom.specified=true");

        // Get all the tdsList where salaryFrom is null
        defaultTdsShouldNotBeFound("salaryFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsBySalaryToIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salaryTo equals to DEFAULT_SALARY_TO
        defaultTdsShouldBeFound("salaryTo.equals=" + DEFAULT_SALARY_TO);

        // Get all the tdsList where salaryTo equals to UPDATED_SALARY_TO
        defaultTdsShouldNotBeFound("salaryTo.equals=" + UPDATED_SALARY_TO);
    }

    @Test
    @Transactional
    void getAllTdsBySalaryToIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salaryTo in DEFAULT_SALARY_TO or UPDATED_SALARY_TO
        defaultTdsShouldBeFound("salaryTo.in=" + DEFAULT_SALARY_TO + "," + UPDATED_SALARY_TO);

        // Get all the tdsList where salaryTo equals to UPDATED_SALARY_TO
        defaultTdsShouldNotBeFound("salaryTo.in=" + UPDATED_SALARY_TO);
    }

    @Test
    @Transactional
    void getAllTdsBySalaryToIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salaryTo is not null
        defaultTdsShouldBeFound("salaryTo.specified=true");

        // Get all the tdsList where salaryTo is null
        defaultTdsShouldNotBeFound("salaryTo.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage equals to DEFAULT_PERCENTAGE
        defaultTdsShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the tdsList where percentage equals to UPDATED_PERCENTAGE
        defaultTdsShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultTdsShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the tdsList where percentage equals to UPDATED_PERCENTAGE
        defaultTdsShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage is not null
        defaultTdsShouldBeFound("percentage.specified=true");

        // Get all the tdsList where percentage is null
        defaultTdsShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultTdsShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the tdsList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultTdsShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultTdsShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the tdsList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultTdsShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage is less than DEFAULT_PERCENTAGE
        defaultTdsShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the tdsList where percentage is less than UPDATED_PERCENTAGE
        defaultTdsShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTdsByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where percentage is greater than DEFAULT_PERCENTAGE
        defaultTdsShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the tdsList where percentage is greater than SMALLER_PERCENTAGE
        defaultTdsShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId equals to DEFAULT_SALARY_SETTING_ID
        defaultTdsShouldBeFound("salarySettingId.equals=" + DEFAULT_SALARY_SETTING_ID);

        // Get all the tdsList where salarySettingId equals to UPDATED_SALARY_SETTING_ID
        defaultTdsShouldNotBeFound("salarySettingId.equals=" + UPDATED_SALARY_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId in DEFAULT_SALARY_SETTING_ID or UPDATED_SALARY_SETTING_ID
        defaultTdsShouldBeFound("salarySettingId.in=" + DEFAULT_SALARY_SETTING_ID + "," + UPDATED_SALARY_SETTING_ID);

        // Get all the tdsList where salarySettingId equals to UPDATED_SALARY_SETTING_ID
        defaultTdsShouldNotBeFound("salarySettingId.in=" + UPDATED_SALARY_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId is not null
        defaultTdsShouldBeFound("salarySettingId.specified=true");

        // Get all the tdsList where salarySettingId is null
        defaultTdsShouldNotBeFound("salarySettingId.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId is greater than or equal to DEFAULT_SALARY_SETTING_ID
        defaultTdsShouldBeFound("salarySettingId.greaterThanOrEqual=" + DEFAULT_SALARY_SETTING_ID);

        // Get all the tdsList where salarySettingId is greater than or equal to UPDATED_SALARY_SETTING_ID
        defaultTdsShouldNotBeFound("salarySettingId.greaterThanOrEqual=" + UPDATED_SALARY_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId is less than or equal to DEFAULT_SALARY_SETTING_ID
        defaultTdsShouldBeFound("salarySettingId.lessThanOrEqual=" + DEFAULT_SALARY_SETTING_ID);

        // Get all the tdsList where salarySettingId is less than or equal to SMALLER_SALARY_SETTING_ID
        defaultTdsShouldNotBeFound("salarySettingId.lessThanOrEqual=" + SMALLER_SALARY_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsLessThanSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId is less than DEFAULT_SALARY_SETTING_ID
        defaultTdsShouldNotBeFound("salarySettingId.lessThan=" + DEFAULT_SALARY_SETTING_ID);

        // Get all the tdsList where salarySettingId is less than UPDATED_SALARY_SETTING_ID
        defaultTdsShouldBeFound("salarySettingId.lessThan=" + UPDATED_SALARY_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllTdsBySalarySettingIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where salarySettingId is greater than DEFAULT_SALARY_SETTING_ID
        defaultTdsShouldNotBeFound("salarySettingId.greaterThan=" + DEFAULT_SALARY_SETTING_ID);

        // Get all the tdsList where salarySettingId is greater than SMALLER_SALARY_SETTING_ID
        defaultTdsShouldBeFound("salarySettingId.greaterThan=" + SMALLER_SALARY_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId equals to DEFAULT_COMPANY_ID
        defaultTdsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the tdsList where companyId equals to UPDATED_COMPANY_ID
        defaultTdsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultTdsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the tdsList where companyId equals to UPDATED_COMPANY_ID
        defaultTdsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId is not null
        defaultTdsShouldBeFound("companyId.specified=true");

        // Get all the tdsList where companyId is null
        defaultTdsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultTdsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the tdsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultTdsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultTdsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the tdsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultTdsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId is less than DEFAULT_COMPANY_ID
        defaultTdsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the tdsList where companyId is less than UPDATED_COMPANY_ID
        defaultTdsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTdsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultTdsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the tdsList where companyId is greater than SMALLER_COMPANY_ID
        defaultTdsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTdsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where status equals to DEFAULT_STATUS
        defaultTdsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the tdsList where status equals to UPDATED_STATUS
        defaultTdsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTdsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTdsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the tdsList where status equals to UPDATED_STATUS
        defaultTdsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTdsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where status is not null
        defaultTdsShouldBeFound("status.specified=true");

        // Get all the tdsList where status is null
        defaultTdsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsByStatusContainsSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where status contains DEFAULT_STATUS
        defaultTdsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the tdsList where status contains UPDATED_STATUS
        defaultTdsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTdsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where status does not contain DEFAULT_STATUS
        defaultTdsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the tdsList where status does not contain UPDATED_STATUS
        defaultTdsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTdsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tdsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTdsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTdsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the tdsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTdsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModified is not null
        defaultTdsShouldBeFound("lastModified.specified=true");

        // Get all the tdsList where lastModified is null
        defaultTdsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTdsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tdsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTdsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTdsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the tdsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTdsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModifiedBy is not null
        defaultTdsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the tdsList where lastModifiedBy is null
        defaultTdsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTdsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tdsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTdsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTdsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        // Get all the tdsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTdsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tdsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTdsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTdsShouldBeFound(String filter) throws Exception {
        restTdsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tds.getId().intValue())))
            .andExpect(jsonPath("$.[*].salaryFrom").value(hasItem(DEFAULT_SALARY_FROM.toString())))
            .andExpect(jsonPath("$.[*].salaryTo").value(hasItem(DEFAULT_SALARY_TO.toString())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].salarySettingId").value(hasItem(DEFAULT_SALARY_SETTING_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTdsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTdsShouldNotBeFound(String filter) throws Exception {
        restTdsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTdsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTds() throws Exception {
        // Get the tds
        restTdsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTds() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();

        // Update the tds
        Tds updatedTds = tdsRepository.findById(tds.getId()).get();
        // Disconnect from session so that the updates on updatedTds are not directly saved in db
        em.detach(updatedTds);
        updatedTds
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .percentage(UPDATED_PERCENTAGE)
            .salarySettingId(UPDATED_SALARY_SETTING_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        TdsDTO tdsDTO = tdsMapper.toDto(updatedTds);

        restTdsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tdsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tdsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
        Tds testTds = tdsList.get(tdsList.size() - 1);
        assertThat(testTds.getSalaryFrom()).isEqualTo(UPDATED_SALARY_FROM);
        assertThat(testTds.getSalaryTo()).isEqualTo(UPDATED_SALARY_TO);
        assertThat(testTds.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testTds.getSalarySettingId()).isEqualTo(UPDATED_SALARY_SETTING_ID);
        assertThat(testTds.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTds.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTds.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTds.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTds() throws Exception {
        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();
        tds.setId(count.incrementAndGet());

        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTdsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tdsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tdsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTds() throws Exception {
        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();
        tds.setId(count.incrementAndGet());

        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTdsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tdsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTds() throws Exception {
        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();
        tds.setId(count.incrementAndGet());

        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTdsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tdsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTdsWithPatch() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();

        // Update the tds using partial update
        Tds partialUpdatedTds = new Tds();
        partialUpdatedTds.setId(tds.getId());

        partialUpdatedTds.salaryFrom(UPDATED_SALARY_FROM).salaryTo(UPDATED_SALARY_TO)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restTdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTds))
            )
            .andExpect(status().isOk());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
        Tds testTds = tdsList.get(tdsList.size() - 1);
        assertThat(testTds.getSalaryFrom()).isEqualTo(UPDATED_SALARY_FROM);
        assertThat(testTds.getSalaryTo()).isEqualTo(UPDATED_SALARY_TO);
        assertThat(testTds.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testTds.getSalarySettingId()).isEqualTo(DEFAULT_SALARY_SETTING_ID);
        assertThat(testTds.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTds.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTds.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTds.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTdsWithPatch() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();

        // Update the tds using partial update
        Tds partialUpdatedTds = new Tds();
        partialUpdatedTds.setId(tds.getId());

        partialUpdatedTds
            .salaryFrom(UPDATED_SALARY_FROM)
            .salaryTo(UPDATED_SALARY_TO)
            .percentage(UPDATED_PERCENTAGE)
            .salarySettingId(UPDATED_SALARY_SETTING_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restTdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTds))
            )
            .andExpect(status().isOk());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
        Tds testTds = tdsList.get(tdsList.size() - 1);
        assertThat(testTds.getSalaryFrom()).isEqualTo(UPDATED_SALARY_FROM);
        assertThat(testTds.getSalaryTo()).isEqualTo(UPDATED_SALARY_TO);
        assertThat(testTds.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testTds.getSalarySettingId()).isEqualTo(UPDATED_SALARY_SETTING_ID);
        assertThat(testTds.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTds.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTds.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTds.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTds() throws Exception {
        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();
        tds.setId(count.incrementAndGet());

        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tdsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tdsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTds() throws Exception {
        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();
        tds.setId(count.incrementAndGet());

        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTdsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tdsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTds() throws Exception {
        int databaseSizeBeforeUpdate = tdsRepository.findAll().size();
        tds.setId(count.incrementAndGet());

        // Create the Tds
        TdsDTO tdsDTO = tdsMapper.toDto(tds);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTdsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tdsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tds in the database
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTds() throws Exception {
        // Initialize the database
        tdsRepository.saveAndFlush(tds);

        int databaseSizeBeforeDelete = tdsRepository.findAll().size();

        // Delete the tds
        restTdsMockMvc.perform(delete(ENTITY_API_URL_ID, tds.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tds> tdsList = tdsRepository.findAll();
        assertThat(tdsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
