package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.WorkDaysSetting;
import com.techvg.hrms.repository.WorkDaysSettingRepository;
import com.techvg.hrms.service.criteria.WorkDaysSettingCriteria;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
import com.techvg.hrms.service.mapper.WorkDaysSettingMapper;
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
 * Integration tests for the {@link WorkDaysSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkDaysSettingResourceIT {

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_HOURS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DAY_OFF = false;
    private static final Boolean UPDATED_DAY_OFF = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-days-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkDaysSettingRepository workDaysSettingRepository;

    @Autowired
    private WorkDaysSettingMapper workDaysSettingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkDaysSettingMockMvc;

    private WorkDaysSetting workDaysSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkDaysSetting createEntity(EntityManager em) {
        WorkDaysSetting workDaysSetting = new WorkDaysSetting()
            .day(DEFAULT_DAY)
            .hours(DEFAULT_HOURS)
            .dayOff(DEFAULT_DAY_OFF)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return workDaysSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkDaysSetting createUpdatedEntity(EntityManager em) {
        WorkDaysSetting workDaysSetting = new WorkDaysSetting()
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .dayOff(UPDATED_DAY_OFF)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return workDaysSetting;
    }

    @BeforeEach
    public void initTest() {
        workDaysSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkDaysSetting() throws Exception {
        int databaseSizeBeforeCreate = workDaysSettingRepository.findAll().size();
        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);
        restWorkDaysSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeCreate + 1);
        WorkDaysSetting testWorkDaysSetting = workDaysSettingList.get(workDaysSettingList.size() - 1);
        assertThat(testWorkDaysSetting.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testWorkDaysSetting.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testWorkDaysSetting.getDayOff()).isEqualTo(DEFAULT_DAY_OFF);
        assertThat(testWorkDaysSetting.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testWorkDaysSetting.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkDaysSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkDaysSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createWorkDaysSettingWithExistingId() throws Exception {
        // Create the WorkDaysSetting with an existing ID
        workDaysSetting.setId(1L);
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        int databaseSizeBeforeCreate = workDaysSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkDaysSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettings() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList
        restWorkDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workDaysSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].dayOff").value(hasItem(DEFAULT_DAY_OFF.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getWorkDaysSetting() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get the workDaysSetting
        restWorkDaysSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, workDaysSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workDaysSetting.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.dayOff").value(DEFAULT_DAY_OFF.booleanValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getWorkDaysSettingsByIdFiltering() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        Long id = workDaysSetting.getId();

        defaultWorkDaysSettingShouldBeFound("id.equals=" + id);
        defaultWorkDaysSettingShouldNotBeFound("id.notEquals=" + id);

        defaultWorkDaysSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkDaysSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkDaysSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkDaysSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where day equals to DEFAULT_DAY
        defaultWorkDaysSettingShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the workDaysSettingList where day equals to UPDATED_DAY
        defaultWorkDaysSettingShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where day in DEFAULT_DAY or UPDATED_DAY
        defaultWorkDaysSettingShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the workDaysSettingList where day equals to UPDATED_DAY
        defaultWorkDaysSettingShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where day is not null
        defaultWorkDaysSettingShouldBeFound("day.specified=true");

        // Get all the workDaysSettingList where day is null
        defaultWorkDaysSettingShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where day contains DEFAULT_DAY
        defaultWorkDaysSettingShouldBeFound("day.contains=" + DEFAULT_DAY);

        // Get all the workDaysSettingList where day contains UPDATED_DAY
        defaultWorkDaysSettingShouldNotBeFound("day.contains=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayNotContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where day does not contain DEFAULT_DAY
        defaultWorkDaysSettingShouldNotBeFound("day.doesNotContain=" + DEFAULT_DAY);

        // Get all the workDaysSettingList where day does not contain UPDATED_DAY
        defaultWorkDaysSettingShouldBeFound("day.doesNotContain=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where hours equals to DEFAULT_HOURS
        defaultWorkDaysSettingShouldBeFound("hours.equals=" + DEFAULT_HOURS);

        // Get all the workDaysSettingList where hours equals to UPDATED_HOURS
        defaultWorkDaysSettingShouldNotBeFound("hours.equals=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByHoursIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where hours in DEFAULT_HOURS or UPDATED_HOURS
        defaultWorkDaysSettingShouldBeFound("hours.in=" + DEFAULT_HOURS + "," + UPDATED_HOURS);

        // Get all the workDaysSettingList where hours equals to UPDATED_HOURS
        defaultWorkDaysSettingShouldNotBeFound("hours.in=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where hours is not null
        defaultWorkDaysSettingShouldBeFound("hours.specified=true");

        // Get all the workDaysSettingList where hours is null
        defaultWorkDaysSettingShouldNotBeFound("hours.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByHoursContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where hours contains DEFAULT_HOURS
        defaultWorkDaysSettingShouldBeFound("hours.contains=" + DEFAULT_HOURS);

        // Get all the workDaysSettingList where hours contains UPDATED_HOURS
        defaultWorkDaysSettingShouldNotBeFound("hours.contains=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByHoursNotContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where hours does not contain DEFAULT_HOURS
        defaultWorkDaysSettingShouldNotBeFound("hours.doesNotContain=" + DEFAULT_HOURS);

        // Get all the workDaysSettingList where hours does not contain UPDATED_HOURS
        defaultWorkDaysSettingShouldBeFound("hours.doesNotContain=" + UPDATED_HOURS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayOffIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where dayOff equals to DEFAULT_DAY_OFF
        defaultWorkDaysSettingShouldBeFound("dayOff.equals=" + DEFAULT_DAY_OFF);

        // Get all the workDaysSettingList where dayOff equals to UPDATED_DAY_OFF
        defaultWorkDaysSettingShouldNotBeFound("dayOff.equals=" + UPDATED_DAY_OFF);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayOffIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where dayOff in DEFAULT_DAY_OFF or UPDATED_DAY_OFF
        defaultWorkDaysSettingShouldBeFound("dayOff.in=" + DEFAULT_DAY_OFF + "," + UPDATED_DAY_OFF);

        // Get all the workDaysSettingList where dayOff equals to UPDATED_DAY_OFF
        defaultWorkDaysSettingShouldNotBeFound("dayOff.in=" + UPDATED_DAY_OFF);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByDayOffIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where dayOff is not null
        defaultWorkDaysSettingShouldBeFound("dayOff.specified=true");

        // Get all the workDaysSettingList where dayOff is null
        defaultWorkDaysSettingShouldNotBeFound("dayOff.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId equals to DEFAULT_COMPANY_ID
        defaultWorkDaysSettingShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the workDaysSettingList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkDaysSettingShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultWorkDaysSettingShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the workDaysSettingList where companyId equals to UPDATED_COMPANY_ID
        defaultWorkDaysSettingShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId is not null
        defaultWorkDaysSettingShouldBeFound("companyId.specified=true");

        // Get all the workDaysSettingList where companyId is null
        defaultWorkDaysSettingShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultWorkDaysSettingShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workDaysSettingList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultWorkDaysSettingShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultWorkDaysSettingShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the workDaysSettingList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultWorkDaysSettingShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId is less than DEFAULT_COMPANY_ID
        defaultWorkDaysSettingShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the workDaysSettingList where companyId is less than UPDATED_COMPANY_ID
        defaultWorkDaysSettingShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where companyId is greater than DEFAULT_COMPANY_ID
        defaultWorkDaysSettingShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the workDaysSettingList where companyId is greater than SMALLER_COMPANY_ID
        defaultWorkDaysSettingShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where status equals to DEFAULT_STATUS
        defaultWorkDaysSettingShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the workDaysSettingList where status equals to UPDATED_STATUS
        defaultWorkDaysSettingShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWorkDaysSettingShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the workDaysSettingList where status equals to UPDATED_STATUS
        defaultWorkDaysSettingShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where status is not null
        defaultWorkDaysSettingShouldBeFound("status.specified=true");

        // Get all the workDaysSettingList where status is null
        defaultWorkDaysSettingShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where status contains DEFAULT_STATUS
        defaultWorkDaysSettingShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the workDaysSettingList where status contains UPDATED_STATUS
        defaultWorkDaysSettingShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where status does not contain DEFAULT_STATUS
        defaultWorkDaysSettingShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the workDaysSettingList where status does not contain UPDATED_STATUS
        defaultWorkDaysSettingShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWorkDaysSettingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the workDaysSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkDaysSettingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWorkDaysSettingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the workDaysSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkDaysSettingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModified is not null
        defaultWorkDaysSettingShouldBeFound("lastModified.specified=true");

        // Get all the workDaysSettingList where lastModified is null
        defaultWorkDaysSettingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workDaysSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workDaysSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModifiedBy is not null
        defaultWorkDaysSettingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workDaysSettingList where lastModifiedBy is null
        defaultWorkDaysSettingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workDaysSettingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkDaysSettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        // Get all the workDaysSettingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workDaysSettingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkDaysSettingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkDaysSettingShouldBeFound(String filter) throws Exception {
        restWorkDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workDaysSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].dayOff").value(hasItem(DEFAULT_DAY_OFF.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restWorkDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkDaysSettingShouldNotBeFound(String filter) throws Exception {
        restWorkDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkDaysSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkDaysSetting() throws Exception {
        // Get the workDaysSetting
        restWorkDaysSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkDaysSetting() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();

        // Update the workDaysSetting
        WorkDaysSetting updatedWorkDaysSetting = workDaysSettingRepository.findById(workDaysSetting.getId()).get();
        // Disconnect from session so that the updates on updatedWorkDaysSetting are not directly saved in db
        em.detach(updatedWorkDaysSetting);
        updatedWorkDaysSetting
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .dayOff(UPDATED_DAY_OFF)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(updatedWorkDaysSetting);

        restWorkDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workDaysSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
        WorkDaysSetting testWorkDaysSetting = workDaysSettingList.get(workDaysSettingList.size() - 1);
        assertThat(testWorkDaysSetting.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testWorkDaysSetting.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkDaysSetting.getDayOff()).isEqualTo(UPDATED_DAY_OFF);
        assertThat(testWorkDaysSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkDaysSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkDaysSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkDaysSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingWorkDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();
        workDaysSetting.setId(count.incrementAndGet());

        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workDaysSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();
        workDaysSetting.setId(count.incrementAndGet());

        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();
        workDaysSetting.setId(count.incrementAndGet());

        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkDaysSettingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkDaysSettingWithPatch() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();

        // Update the workDaysSetting using partial update
        WorkDaysSetting partialUpdatedWorkDaysSetting = new WorkDaysSetting();
        partialUpdatedWorkDaysSetting.setId(workDaysSetting.getId());

        partialUpdatedWorkDaysSetting.hours(UPDATED_HOURS).dayOff(UPDATED_DAY_OFF).status(UPDATED_STATUS);

        restWorkDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkDaysSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkDaysSetting))
            )
            .andExpect(status().isOk());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
        WorkDaysSetting testWorkDaysSetting = workDaysSettingList.get(workDaysSettingList.size() - 1);
        assertThat(testWorkDaysSetting.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testWorkDaysSetting.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkDaysSetting.getDayOff()).isEqualTo(UPDATED_DAY_OFF);
        assertThat(testWorkDaysSetting.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testWorkDaysSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkDaysSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkDaysSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateWorkDaysSettingWithPatch() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();

        // Update the workDaysSetting using partial update
        WorkDaysSetting partialUpdatedWorkDaysSetting = new WorkDaysSetting();
        partialUpdatedWorkDaysSetting.setId(workDaysSetting.getId());

        partialUpdatedWorkDaysSetting
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .dayOff(UPDATED_DAY_OFF)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restWorkDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkDaysSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkDaysSetting))
            )
            .andExpect(status().isOk());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
        WorkDaysSetting testWorkDaysSetting = workDaysSettingList.get(workDaysSettingList.size() - 1);
        assertThat(testWorkDaysSetting.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testWorkDaysSetting.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkDaysSetting.getDayOff()).isEqualTo(UPDATED_DAY_OFF);
        assertThat(testWorkDaysSetting.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testWorkDaysSetting.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkDaysSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkDaysSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingWorkDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();
        workDaysSetting.setId(count.incrementAndGet());

        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workDaysSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();
        workDaysSetting.setId(count.incrementAndGet());

        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkDaysSetting() throws Exception {
        int databaseSizeBeforeUpdate = workDaysSettingRepository.findAll().size();
        workDaysSetting.setId(count.incrementAndGet());

        // Create the WorkDaysSetting
        WorkDaysSettingDTO workDaysSettingDTO = workDaysSettingMapper.toDto(workDaysSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkDaysSettingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workDaysSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkDaysSetting in the database
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkDaysSetting() throws Exception {
        // Initialize the database
        workDaysSettingRepository.saveAndFlush(workDaysSetting);

        int databaseSizeBeforeDelete = workDaysSettingRepository.findAll().size();

        // Delete the workDaysSetting
        restWorkDaysSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, workDaysSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkDaysSetting> workDaysSettingList = workDaysSettingRepository.findAll();
        assertThat(workDaysSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
