package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.SalarySettings;
import com.techvg.hrms.repository.SalarySettingsRepository;
import com.techvg.hrms.service.criteria.SalarySettingsCriteria;
import com.techvg.hrms.service.dto.SalarySettingsDTO;
import com.techvg.hrms.service.mapper.SalarySettingsMapper;
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
 * Integration tests for the {@link SalarySettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalarySettingsResourceIT {

    private static final Double DEFAULT_DA = 1D;
    private static final Double UPDATED_DA = 2D;
    private static final Double SMALLER_DA = 1D - 1D;

    private static final Double DEFAULT_HRA = 1D;
    private static final Double UPDATED_HRA = 2D;
    private static final Double SMALLER_HRA = 1D - 1D;

    private static final Double DEFAULT_EMPLOYEE_SHARE = 1D;
    private static final Double UPDATED_EMPLOYEE_SHARE = 2D;
    private static final Double SMALLER_EMPLOYEE_SHARE = 1D - 1D;

    private static final Double DEFAULT_COMPANY_SHARE = 1D;
    private static final Double UPDATED_COMPANY_SHARE = 2D;
    private static final Double SMALLER_COMPANY_SHARE = 1D - 1D;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/salary-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalarySettingsRepository salarySettingsRepository;

    @Autowired
    private SalarySettingsMapper salarySettingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalarySettingsMockMvc;

    private SalarySettings salarySettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalarySettings createEntity(EntityManager em) {
        SalarySettings salarySettings = new SalarySettings()
            .da(DEFAULT_DA)
            .hra(DEFAULT_HRA)
            .employeeShare(DEFAULT_EMPLOYEE_SHARE)
            .companyShare(DEFAULT_COMPANY_SHARE)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return salarySettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalarySettings createUpdatedEntity(EntityManager em) {
        SalarySettings salarySettings = new SalarySettings()
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .employeeShare(UPDATED_EMPLOYEE_SHARE)
            .companyShare(UPDATED_COMPANY_SHARE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return salarySettings;
    }

    @BeforeEach
    public void initTest() {
        salarySettings = createEntity(em);
    }

    @Test
    @Transactional
    void createSalarySettings() throws Exception {
        int databaseSizeBeforeCreate = salarySettingsRepository.findAll().size();
        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);
        restSalarySettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeCreate + 1);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(DEFAULT_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(DEFAULT_HRA);
        assertThat(testSalarySettings.getEmployeeShare()).isEqualTo(DEFAULT_EMPLOYEE_SHARE);
        assertThat(testSalarySettings.getCompanyShare()).isEqualTo(DEFAULT_COMPANY_SHARE);
        assertThat(testSalarySettings.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testSalarySettings.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createSalarySettingsWithExistingId() throws Exception {
        // Create the SalarySettings with an existing ID
        salarySettings.setId(1L);
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        int databaseSizeBeforeCreate = salarySettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalarySettingsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salarySettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].da").value(hasItem(DEFAULT_DA.doubleValue())))
            .andExpect(jsonPath("$.[*].hra").value(hasItem(DEFAULT_HRA.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeShare").value(hasItem(DEFAULT_EMPLOYEE_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyShare").value(hasItem(DEFAULT_COMPANY_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get the salarySettings
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, salarySettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salarySettings.getId().intValue()))
            .andExpect(jsonPath("$.da").value(DEFAULT_DA.doubleValue()))
            .andExpect(jsonPath("$.hra").value(DEFAULT_HRA.doubleValue()))
            .andExpect(jsonPath("$.employeeShare").value(DEFAULT_EMPLOYEE_SHARE.doubleValue()))
            .andExpect(jsonPath("$.companyShare").value(DEFAULT_COMPANY_SHARE.doubleValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getSalarySettingsByIdFiltering() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        Long id = salarySettings.getId();

        defaultSalarySettingsShouldBeFound("id.equals=" + id);
        defaultSalarySettingsShouldNotBeFound("id.notEquals=" + id);

        defaultSalarySettingsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSalarySettingsShouldNotBeFound("id.greaterThan=" + id);

        defaultSalarySettingsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSalarySettingsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da equals to DEFAULT_DA
        defaultSalarySettingsShouldBeFound("da.equals=" + DEFAULT_DA);

        // Get all the salarySettingsList where da equals to UPDATED_DA
        defaultSalarySettingsShouldNotBeFound("da.equals=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da in DEFAULT_DA or UPDATED_DA
        defaultSalarySettingsShouldBeFound("da.in=" + DEFAULT_DA + "," + UPDATED_DA);

        // Get all the salarySettingsList where da equals to UPDATED_DA
        defaultSalarySettingsShouldNotBeFound("da.in=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is not null
        defaultSalarySettingsShouldBeFound("da.specified=true");

        // Get all the salarySettingsList where da is null
        defaultSalarySettingsShouldNotBeFound("da.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is greater than or equal to DEFAULT_DA
        defaultSalarySettingsShouldBeFound("da.greaterThanOrEqual=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is greater than or equal to UPDATED_DA
        defaultSalarySettingsShouldNotBeFound("da.greaterThanOrEqual=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is less than or equal to DEFAULT_DA
        defaultSalarySettingsShouldBeFound("da.lessThanOrEqual=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is less than or equal to SMALLER_DA
        defaultSalarySettingsShouldNotBeFound("da.lessThanOrEqual=" + SMALLER_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is less than DEFAULT_DA
        defaultSalarySettingsShouldNotBeFound("da.lessThan=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is less than UPDATED_DA
        defaultSalarySettingsShouldBeFound("da.lessThan=" + UPDATED_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByDaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where da is greater than DEFAULT_DA
        defaultSalarySettingsShouldNotBeFound("da.greaterThan=" + DEFAULT_DA);

        // Get all the salarySettingsList where da is greater than SMALLER_DA
        defaultSalarySettingsShouldBeFound("da.greaterThan=" + SMALLER_DA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra equals to DEFAULT_HRA
        defaultSalarySettingsShouldBeFound("hra.equals=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra equals to UPDATED_HRA
        defaultSalarySettingsShouldNotBeFound("hra.equals=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra in DEFAULT_HRA or UPDATED_HRA
        defaultSalarySettingsShouldBeFound("hra.in=" + DEFAULT_HRA + "," + UPDATED_HRA);

        // Get all the salarySettingsList where hra equals to UPDATED_HRA
        defaultSalarySettingsShouldNotBeFound("hra.in=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is not null
        defaultSalarySettingsShouldBeFound("hra.specified=true");

        // Get all the salarySettingsList where hra is null
        defaultSalarySettingsShouldNotBeFound("hra.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is greater than or equal to DEFAULT_HRA
        defaultSalarySettingsShouldBeFound("hra.greaterThanOrEqual=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is greater than or equal to UPDATED_HRA
        defaultSalarySettingsShouldNotBeFound("hra.greaterThanOrEqual=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is less than or equal to DEFAULT_HRA
        defaultSalarySettingsShouldBeFound("hra.lessThanOrEqual=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is less than or equal to SMALLER_HRA
        defaultSalarySettingsShouldNotBeFound("hra.lessThanOrEqual=" + SMALLER_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is less than DEFAULT_HRA
        defaultSalarySettingsShouldNotBeFound("hra.lessThan=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is less than UPDATED_HRA
        defaultSalarySettingsShouldBeFound("hra.lessThan=" + UPDATED_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByHraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where hra is greater than DEFAULT_HRA
        defaultSalarySettingsShouldNotBeFound("hra.greaterThan=" + DEFAULT_HRA);

        // Get all the salarySettingsList where hra is greater than SMALLER_HRA
        defaultSalarySettingsShouldBeFound("hra.greaterThan=" + SMALLER_HRA);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare equals to DEFAULT_EMPLOYEE_SHARE
        defaultSalarySettingsShouldBeFound("employeeShare.equals=" + DEFAULT_EMPLOYEE_SHARE);

        // Get all the salarySettingsList where employeeShare equals to UPDATED_EMPLOYEE_SHARE
        defaultSalarySettingsShouldNotBeFound("employeeShare.equals=" + UPDATED_EMPLOYEE_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare in DEFAULT_EMPLOYEE_SHARE or UPDATED_EMPLOYEE_SHARE
        defaultSalarySettingsShouldBeFound("employeeShare.in=" + DEFAULT_EMPLOYEE_SHARE + "," + UPDATED_EMPLOYEE_SHARE);

        // Get all the salarySettingsList where employeeShare equals to UPDATED_EMPLOYEE_SHARE
        defaultSalarySettingsShouldNotBeFound("employeeShare.in=" + UPDATED_EMPLOYEE_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare is not null
        defaultSalarySettingsShouldBeFound("employeeShare.specified=true");

        // Get all the salarySettingsList where employeeShare is null
        defaultSalarySettingsShouldNotBeFound("employeeShare.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare is greater than or equal to DEFAULT_EMPLOYEE_SHARE
        defaultSalarySettingsShouldBeFound("employeeShare.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_SHARE);

        // Get all the salarySettingsList where employeeShare is greater than or equal to UPDATED_EMPLOYEE_SHARE
        defaultSalarySettingsShouldNotBeFound("employeeShare.greaterThanOrEqual=" + UPDATED_EMPLOYEE_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare is less than or equal to DEFAULT_EMPLOYEE_SHARE
        defaultSalarySettingsShouldBeFound("employeeShare.lessThanOrEqual=" + DEFAULT_EMPLOYEE_SHARE);

        // Get all the salarySettingsList where employeeShare is less than or equal to SMALLER_EMPLOYEE_SHARE
        defaultSalarySettingsShouldNotBeFound("employeeShare.lessThanOrEqual=" + SMALLER_EMPLOYEE_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare is less than DEFAULT_EMPLOYEE_SHARE
        defaultSalarySettingsShouldNotBeFound("employeeShare.lessThan=" + DEFAULT_EMPLOYEE_SHARE);

        // Get all the salarySettingsList where employeeShare is less than UPDATED_EMPLOYEE_SHARE
        defaultSalarySettingsShouldBeFound("employeeShare.lessThan=" + UPDATED_EMPLOYEE_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByEmployeeShareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where employeeShare is greater than DEFAULT_EMPLOYEE_SHARE
        defaultSalarySettingsShouldNotBeFound("employeeShare.greaterThan=" + DEFAULT_EMPLOYEE_SHARE);

        // Get all the salarySettingsList where employeeShare is greater than SMALLER_EMPLOYEE_SHARE
        defaultSalarySettingsShouldBeFound("employeeShare.greaterThan=" + SMALLER_EMPLOYEE_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare equals to DEFAULT_COMPANY_SHARE
        defaultSalarySettingsShouldBeFound("companyShare.equals=" + DEFAULT_COMPANY_SHARE);

        // Get all the salarySettingsList where companyShare equals to UPDATED_COMPANY_SHARE
        defaultSalarySettingsShouldNotBeFound("companyShare.equals=" + UPDATED_COMPANY_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare in DEFAULT_COMPANY_SHARE or UPDATED_COMPANY_SHARE
        defaultSalarySettingsShouldBeFound("companyShare.in=" + DEFAULT_COMPANY_SHARE + "," + UPDATED_COMPANY_SHARE);

        // Get all the salarySettingsList where companyShare equals to UPDATED_COMPANY_SHARE
        defaultSalarySettingsShouldNotBeFound("companyShare.in=" + UPDATED_COMPANY_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare is not null
        defaultSalarySettingsShouldBeFound("companyShare.specified=true");

        // Get all the salarySettingsList where companyShare is null
        defaultSalarySettingsShouldNotBeFound("companyShare.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare is greater than or equal to DEFAULT_COMPANY_SHARE
        defaultSalarySettingsShouldBeFound("companyShare.greaterThanOrEqual=" + DEFAULT_COMPANY_SHARE);

        // Get all the salarySettingsList where companyShare is greater than or equal to UPDATED_COMPANY_SHARE
        defaultSalarySettingsShouldNotBeFound("companyShare.greaterThanOrEqual=" + UPDATED_COMPANY_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare is less than or equal to DEFAULT_COMPANY_SHARE
        defaultSalarySettingsShouldBeFound("companyShare.lessThanOrEqual=" + DEFAULT_COMPANY_SHARE);

        // Get all the salarySettingsList where companyShare is less than or equal to SMALLER_COMPANY_SHARE
        defaultSalarySettingsShouldNotBeFound("companyShare.lessThanOrEqual=" + SMALLER_COMPANY_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare is less than DEFAULT_COMPANY_SHARE
        defaultSalarySettingsShouldNotBeFound("companyShare.lessThan=" + DEFAULT_COMPANY_SHARE);

        // Get all the salarySettingsList where companyShare is less than UPDATED_COMPANY_SHARE
        defaultSalarySettingsShouldBeFound("companyShare.lessThan=" + UPDATED_COMPANY_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyShareIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyShare is greater than DEFAULT_COMPANY_SHARE
        defaultSalarySettingsShouldNotBeFound("companyShare.greaterThan=" + DEFAULT_COMPANY_SHARE);

        // Get all the salarySettingsList where companyShare is greater than SMALLER_COMPANY_SHARE
        defaultSalarySettingsShouldBeFound("companyShare.greaterThan=" + SMALLER_COMPANY_SHARE);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId equals to DEFAULT_COMPANY_ID
        defaultSalarySettingsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the salarySettingsList where companyId equals to UPDATED_COMPANY_ID
        defaultSalarySettingsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultSalarySettingsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the salarySettingsList where companyId equals to UPDATED_COMPANY_ID
        defaultSalarySettingsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId is not null
        defaultSalarySettingsShouldBeFound("companyId.specified=true");

        // Get all the salarySettingsList where companyId is null
        defaultSalarySettingsShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultSalarySettingsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the salarySettingsList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultSalarySettingsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultSalarySettingsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the salarySettingsList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultSalarySettingsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId is less than DEFAULT_COMPANY_ID
        defaultSalarySettingsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the salarySettingsList where companyId is less than UPDATED_COMPANY_ID
        defaultSalarySettingsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where companyId is greater than DEFAULT_COMPANY_ID
        defaultSalarySettingsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the salarySettingsList where companyId is greater than SMALLER_COMPANY_ID
        defaultSalarySettingsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where status equals to DEFAULT_STATUS
        defaultSalarySettingsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the salarySettingsList where status equals to UPDATED_STATUS
        defaultSalarySettingsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSalarySettingsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the salarySettingsList where status equals to UPDATED_STATUS
        defaultSalarySettingsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where status is not null
        defaultSalarySettingsShouldBeFound("status.specified=true");

        // Get all the salarySettingsList where status is null
        defaultSalarySettingsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByStatusContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where status contains DEFAULT_STATUS
        defaultSalarySettingsShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the salarySettingsList where status contains UPDATED_STATUS
        defaultSalarySettingsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where status does not contain DEFAULT_STATUS
        defaultSalarySettingsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the salarySettingsList where status does not contain UPDATED_STATUS
        defaultSalarySettingsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSalarySettingsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the salarySettingsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSalarySettingsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSalarySettingsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the salarySettingsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSalarySettingsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModified is not null
        defaultSalarySettingsShouldBeFound("lastModified.specified=true");

        // Get all the salarySettingsList where lastModified is null
        defaultSalarySettingsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy is not null
        defaultSalarySettingsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the salarySettingsList where lastModifiedBy is null
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSalarySettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        // Get all the salarySettingsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSalarySettingsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the salarySettingsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSalarySettingsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalarySettingsShouldBeFound(String filter) throws Exception {
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salarySettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].da").value(hasItem(DEFAULT_DA.doubleValue())))
            .andExpect(jsonPath("$.[*].hra").value(hasItem(DEFAULT_HRA.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeShare").value(hasItem(DEFAULT_EMPLOYEE_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyShare").value(hasItem(DEFAULT_COMPANY_SHARE.doubleValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalarySettingsShouldNotBeFound(String filter) throws Exception {
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalarySettingsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalarySettings() throws Exception {
        // Get the salarySettings
        restSalarySettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();

        // Update the salarySettings
        SalarySettings updatedSalarySettings = salarySettingsRepository.findById(salarySettings.getId()).get();
        // Disconnect from session so that the updates on updatedSalarySettings are not directly saved in db
        em.detach(updatedSalarySettings);
        updatedSalarySettings
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .employeeShare(UPDATED_EMPLOYEE_SHARE)
            .companyShare(UPDATED_COMPANY_SHARE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(updatedSalarySettings);

        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salarySettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(UPDATED_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testSalarySettings.getEmployeeShare()).isEqualTo(UPDATED_EMPLOYEE_SHARE);
        assertThat(testSalarySettings.getCompanyShare()).isEqualTo(UPDATED_COMPANY_SHARE);
        assertThat(testSalarySettings.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSalarySettings.getStatus()).isEqualTo(UPDATED_STATUS);
//    @Test
//    @Transactional
//        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
//        salarySettings.setId(count.incrementAndGet());
//
//        // Create the SalarySettings
//        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salarySettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
   //     List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalarySettingsWithPatch() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();

        // Update the salarySettings using partial update
        SalarySettings partialUpdatedSalarySettings = new SalarySettings();
        partialUpdatedSalarySettings.setId(salarySettings.getId());

        partialUpdatedSalarySettings
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalarySettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalarySettings))
            )
            .andExpect(status().isOk());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(UPDATED_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testSalarySettings.getEmployeeShare()).isEqualTo(DEFAULT_EMPLOYEE_SHARE);
        assertThat(testSalarySettings.getCompanyShare()).isEqualTo(DEFAULT_COMPANY_SHARE);
        assertThat(testSalarySettings.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSalarySettings.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateSalarySettingsWithPatch() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();

        // Update the salarySettings using partial update
        SalarySettings partialUpdatedSalarySettings = new SalarySettings();
        partialUpdatedSalarySettings.setId(salarySettings.getId());

        partialUpdatedSalarySettings
            .da(UPDATED_DA)
            .hra(UPDATED_HRA)
            .employeeShare(UPDATED_EMPLOYEE_SHARE)
            .companyShare(UPDATED_COMPANY_SHARE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalarySettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalarySettings))
            )
            .andExpect(status().isOk());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
        SalarySettings testSalarySettings = salarySettingsList.get(salarySettingsList.size() - 1);
        assertThat(testSalarySettings.getDa()).isEqualTo(UPDATED_DA);
        assertThat(testSalarySettings.getHra()).isEqualTo(UPDATED_HRA);
        assertThat(testSalarySettings.getEmployeeShare()).isEqualTo(UPDATED_EMPLOYEE_SHARE);
        assertThat(testSalarySettings.getCompanyShare()).isEqualTo(UPDATED_COMPANY_SHARE);
        assertThat(testSalarySettings.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testSalarySettings.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalarySettings.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalarySettings.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salarySettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalarySettings() throws Exception {
        int databaseSizeBeforeUpdate = salarySettingsRepository.findAll().size();
        salarySettings.setId(count.incrementAndGet());

        // Create the SalarySettings
        SalarySettingsDTO salarySettingsDTO = salarySettingsMapper.toDto(salarySettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalarySettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salarySettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalarySettings in the database
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalarySettings() throws Exception {
        // Initialize the database
        salarySettingsRepository.saveAndFlush(salarySettings);

        int databaseSizeBeforeDelete = salarySettingsRepository.findAll().size();

        // Delete the salarySettings
        restSalarySettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, salarySettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalarySettings> salarySettingsList = salarySettingsRepository.findAll();
        assertThat(salarySettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
