package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Holiday;
import com.techvg.hrms.repository.HolidayRepository;
import com.techvg.hrms.service.criteria.HolidayCriteria;
import com.techvg.hrms.service.dto.HolidayDTO;
import com.techvg.hrms.service.mapper.HolidayMapper;
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
 * Integration tests for the {@link HolidayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HolidayResourceIT {

    private static final String DEFAULT_HOLIDAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOLIDAY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_HOLIDAY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOLIDAY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final Instant DEFAULT_YEAR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_YEAR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/holidays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHolidayMockMvc;

    private Holiday holiday;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holiday createEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .holidayName(DEFAULT_HOLIDAY_NAME)
            .holidayDate(DEFAULT_HOLIDAY_DATE)
            .day(DEFAULT_DAY)
            .year(DEFAULT_YEAR)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return holiday;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Holiday createUpdatedEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .holidayName(UPDATED_HOLIDAY_NAME)
            .holidayDate(UPDATED_HOLIDAY_DATE)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return holiday;
    }

    @BeforeEach
    public void initTest() {
        holiday = createEntity(em);
    }

    @Test
    @Transactional
    void createHoliday() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();
        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);
        restHolidayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isCreated());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate + 1);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getHolidayName()).isEqualTo(DEFAULT_HOLIDAY_NAME);
        assertThat(testHoliday.getHolidayDate()).isEqualTo(DEFAULT_HOLIDAY_DATE);
        assertThat(testHoliday.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testHoliday.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testHoliday.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testHoliday.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHoliday.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testHoliday.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createHolidayWithExistingId() throws Exception {
        // Create the Holiday with an existing ID
        holiday.setId(1L);
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        int databaseSizeBeforeCreate = holidayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHolidays() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList
        restHolidayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].holidayName").value(hasItem(DEFAULT_HOLIDAY_NAME)))
            .andExpect(jsonPath("$.[*].holidayDate").value(hasItem(DEFAULT_HOLIDAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get the holiday
        restHolidayMockMvc
            .perform(get(ENTITY_API_URL_ID, holiday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(holiday.getId().intValue()))
            .andExpect(jsonPath("$.holidayName").value(DEFAULT_HOLIDAY_NAME))
            .andExpect(jsonPath("$.holidayDate").value(DEFAULT_HOLIDAY_DATE.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getHolidaysByIdFiltering() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        Long id = holiday.getId();

        defaultHolidayShouldBeFound("id.equals=" + id);
        defaultHolidayShouldNotBeFound("id.notEquals=" + id);

        defaultHolidayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHolidayShouldNotBeFound("id.greaterThan=" + id);

        defaultHolidayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHolidayShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayName equals to DEFAULT_HOLIDAY_NAME
        defaultHolidayShouldBeFound("holidayName.equals=" + DEFAULT_HOLIDAY_NAME);

        // Get all the holidayList where holidayName equals to UPDATED_HOLIDAY_NAME
        defaultHolidayShouldNotBeFound("holidayName.equals=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayName in DEFAULT_HOLIDAY_NAME or UPDATED_HOLIDAY_NAME
        defaultHolidayShouldBeFound("holidayName.in=" + DEFAULT_HOLIDAY_NAME + "," + UPDATED_HOLIDAY_NAME);

        // Get all the holidayList where holidayName equals to UPDATED_HOLIDAY_NAME
        defaultHolidayShouldNotBeFound("holidayName.in=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayName is not null
        defaultHolidayShouldBeFound("holidayName.specified=true");

        // Get all the holidayList where holidayName is null
        defaultHolidayShouldNotBeFound("holidayName.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayName contains DEFAULT_HOLIDAY_NAME
        defaultHolidayShouldBeFound("holidayName.contains=" + DEFAULT_HOLIDAY_NAME);

        // Get all the holidayList where holidayName contains UPDATED_HOLIDAY_NAME
        defaultHolidayShouldNotBeFound("holidayName.contains=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayNameNotContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayName does not contain DEFAULT_HOLIDAY_NAME
        defaultHolidayShouldNotBeFound("holidayName.doesNotContain=" + DEFAULT_HOLIDAY_NAME);

        // Get all the holidayList where holidayName does not contain UPDATED_HOLIDAY_NAME
        defaultHolidayShouldBeFound("holidayName.doesNotContain=" + UPDATED_HOLIDAY_NAME);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayDateIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayDate equals to DEFAULT_HOLIDAY_DATE
        defaultHolidayShouldBeFound("holidayDate.equals=" + DEFAULT_HOLIDAY_DATE);

        // Get all the holidayList where holidayDate equals to UPDATED_HOLIDAY_DATE
        defaultHolidayShouldNotBeFound("holidayDate.equals=" + UPDATED_HOLIDAY_DATE);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayDateIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayDate in DEFAULT_HOLIDAY_DATE or UPDATED_HOLIDAY_DATE
        defaultHolidayShouldBeFound("holidayDate.in=" + DEFAULT_HOLIDAY_DATE + "," + UPDATED_HOLIDAY_DATE);

        // Get all the holidayList where holidayDate equals to UPDATED_HOLIDAY_DATE
        defaultHolidayShouldNotBeFound("holidayDate.in=" + UPDATED_HOLIDAY_DATE);
    }

    @Test
    @Transactional
    void getAllHolidaysByHolidayDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayDate is not null
        defaultHolidayShouldBeFound("holidayDate.specified=true");

        // Get all the holidayList where holidayDate is null
        defaultHolidayShouldNotBeFound("holidayDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where day equals to DEFAULT_DAY
        defaultHolidayShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the holidayList where day equals to UPDATED_DAY
        defaultHolidayShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByDayIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where day in DEFAULT_DAY or UPDATED_DAY
        defaultHolidayShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the holidayList where day equals to UPDATED_DAY
        defaultHolidayShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where day is not null
        defaultHolidayShouldBeFound("day.specified=true");

        // Get all the holidayList where day is null
        defaultHolidayShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByDayContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where day contains DEFAULT_DAY
        defaultHolidayShouldBeFound("day.contains=" + DEFAULT_DAY);

        // Get all the holidayList where day contains UPDATED_DAY
        defaultHolidayShouldNotBeFound("day.contains=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByDayNotContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where day does not contain DEFAULT_DAY
        defaultHolidayShouldNotBeFound("day.doesNotContain=" + DEFAULT_DAY);

        // Get all the holidayList where day does not contain UPDATED_DAY
        defaultHolidayShouldBeFound("day.doesNotContain=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllHolidaysByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where year equals to DEFAULT_YEAR
        defaultHolidayShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the holidayList where year equals to UPDATED_YEAR
        defaultHolidayShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllHolidaysByYearIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultHolidayShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the holidayList where year equals to UPDATED_YEAR
        defaultHolidayShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllHolidaysByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where year is not null
        defaultHolidayShouldBeFound("year.specified=true");

        // Get all the holidayList where year is null
        defaultHolidayShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId equals to DEFAULT_COMPANY_ID
        defaultHolidayShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the holidayList where companyId equals to UPDATED_COMPANY_ID
        defaultHolidayShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultHolidayShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the holidayList where companyId equals to UPDATED_COMPANY_ID
        defaultHolidayShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId is not null
        defaultHolidayShouldBeFound("companyId.specified=true");

        // Get all the holidayList where companyId is null
        defaultHolidayShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultHolidayShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the holidayList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultHolidayShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultHolidayShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the holidayList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultHolidayShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId is less than DEFAULT_COMPANY_ID
        defaultHolidayShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the holidayList where companyId is less than UPDATED_COMPANY_ID
        defaultHolidayShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllHolidaysByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where companyId is greater than DEFAULT_COMPANY_ID
        defaultHolidayShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the holidayList where companyId is greater than SMALLER_COMPANY_ID
        defaultHolidayShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllHolidaysByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where status equals to DEFAULT_STATUS
        defaultHolidayShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the holidayList where status equals to UPDATED_STATUS
        defaultHolidayShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllHolidaysByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultHolidayShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the holidayList where status equals to UPDATED_STATUS
        defaultHolidayShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllHolidaysByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where status is not null
        defaultHolidayShouldBeFound("status.specified=true");

        // Get all the holidayList where status is null
        defaultHolidayShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByStatusContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where status contains DEFAULT_STATUS
        defaultHolidayShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the holidayList where status contains UPDATED_STATUS
        defaultHolidayShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllHolidaysByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where status does not contain DEFAULT_STATUS
        defaultHolidayShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the holidayList where status does not contain UPDATED_STATUS
        defaultHolidayShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultHolidayShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the holidayList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHolidayShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultHolidayShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the holidayList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHolidayShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModified is not null
        defaultHolidayShouldBeFound("lastModified.specified=true");

        // Get all the holidayList where lastModified is null
        defaultHolidayShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultHolidayShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the holidayList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHolidayShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultHolidayShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the holidayList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHolidayShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModifiedBy is not null
        defaultHolidayShouldBeFound("lastModifiedBy.specified=true");

        // Get all the holidayList where lastModifiedBy is null
        defaultHolidayShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultHolidayShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the holidayList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultHolidayShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHolidaysByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultHolidayShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the holidayList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultHolidayShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHolidayShouldBeFound(String filter) throws Exception {
        restHolidayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].holidayName").value(hasItem(DEFAULT_HOLIDAY_NAME)))
            .andExpect(jsonPath("$.[*].holidayDate").value(hasItem(DEFAULT_HOLIDAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restHolidayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHolidayShouldNotBeFound(String filter) throws Exception {
        restHolidayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHolidayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHoliday() throws Exception {
        // Get the holiday
        restHolidayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Update the holiday
        Holiday updatedHoliday = holidayRepository.findById(holiday.getId()).get();
        // Disconnect from session so that the updates on updatedHoliday are not directly saved in db
        em.detach(updatedHoliday);
        updatedHoliday
            .holidayName(UPDATED_HOLIDAY_NAME)
            .holidayDate(UPDATED_HOLIDAY_DATE)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        HolidayDTO holidayDTO = holidayMapper.toDto(updatedHoliday);

        restHolidayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holidayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holidayDTO))
            )
            .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getHolidayName()).isEqualTo(UPDATED_HOLIDAY_NAME);
        assertThat(testHoliday.getHolidayDate()).isEqualTo(UPDATED_HOLIDAY_DATE);
        assertThat(testHoliday.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHoliday.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHoliday.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testHoliday.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHoliday.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHoliday.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();
        holiday.setId(count.incrementAndGet());

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, holidayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holidayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();
        holiday.setId(count.incrementAndGet());

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(holidayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();
        holiday.setId(count.incrementAndGet());

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHolidayWithPatch() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Update the holiday using partial update
        Holiday partialUpdatedHoliday = new Holiday();
        partialUpdatedHoliday.setId(holiday.getId());

        partialUpdatedHoliday
            .holidayName(UPDATED_HOLIDAY_NAME)
            .year(UPDATED_YEAR)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restHolidayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoliday.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoliday))
            )
            .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getHolidayName()).isEqualTo(UPDATED_HOLIDAY_NAME);
        assertThat(testHoliday.getHolidayDate()).isEqualTo(DEFAULT_HOLIDAY_DATE);
        assertThat(testHoliday.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testHoliday.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHoliday.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testHoliday.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHoliday.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHoliday.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateHolidayWithPatch() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Update the holiday using partial update
        Holiday partialUpdatedHoliday = new Holiday();
        partialUpdatedHoliday.setId(holiday.getId());

        partialUpdatedHoliday
            .holidayName(UPDATED_HOLIDAY_NAME)
            .holidayDate(UPDATED_HOLIDAY_DATE)
            .day(UPDATED_DAY)
            .year(UPDATED_YEAR)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restHolidayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoliday.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoliday))
            )
            .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getHolidayName()).isEqualTo(UPDATED_HOLIDAY_NAME);
        assertThat(testHoliday.getHolidayDate()).isEqualTo(UPDATED_HOLIDAY_DATE);
        assertThat(testHoliday.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testHoliday.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHoliday.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testHoliday.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHoliday.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHoliday.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();
        holiday.setId(count.incrementAndGet());

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, holidayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holidayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();
        holiday.setId(count.incrementAndGet());

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(holidayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();
        holiday.setId(count.incrementAndGet());

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHolidayMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(holidayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        int databaseSizeBeforeDelete = holidayRepository.findAll().size();

        // Delete the holiday
        restHolidayMockMvc
            .perform(delete(ENTITY_API_URL_ID, holiday.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
