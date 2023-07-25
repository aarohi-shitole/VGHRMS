package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Region;
import com.techvg.hrms.repository.RegionRepository;
import com.techvg.hrms.service.criteria.RegionCriteria;
import com.techvg.hrms.service.dto.RegionDTO;
import com.techvg.hrms.service.mapper.RegionMapper;
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
 * Integration tests for the {@link RegionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegionResourceIT {

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_REGION_ID = 1L;
    private static final Long UPDATED_REGION_ID = 2L;
    private static final Long SMALLER_REGION_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegionMockMvc;

    private Region region;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createEntity(EntityManager em) {
        Region region = new Region()
            .regionName(DEFAULT_REGION_NAME)
            .description(DEFAULT_DESCRIPTION)
            .regionId(DEFAULT_REGION_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return region;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createUpdatedEntity(EntityManager em) {
        Region region = new Region()
            .regionName(UPDATED_REGION_NAME)
            .description(UPDATED_DESCRIPTION)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return region;
    }

    @BeforeEach
    public void initTest() {
        region = createEntity(em);
    }

    @Test
    @Transactional
    void createRegion() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();
        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);
        restRegionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isCreated());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate + 1);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRegion.getRegionId()).isEqualTo(DEFAULT_REGION_ID);
        assertThat(testRegion.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testRegion.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRegion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createRegionWithExistingId() throws Exception {
        // Create the Region with an existing ID
        region.setId(1L);
        RegionDTO regionDTO = regionMapper.toDto(region);

        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setRegionName(null);

        // Create the Region, which fails.
        RegionDTO regionDTO = regionMapper.toDto(region);

        restRegionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegions() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].regionId").value(hasItem(DEFAULT_REGION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get the region
        restRegionMockMvc
            .perform(get(ENTITY_API_URL_ID, region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(region.getId().intValue()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.regionId").value(DEFAULT_REGION_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getRegionsByIdFiltering() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        Long id = region.getId();

        defaultRegionShouldBeFound("id.equals=" + id);
        defaultRegionShouldNotBeFound("id.notEquals=" + id);

        defaultRegionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegionShouldNotBeFound("id.greaterThan=" + id);

        defaultRegionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName equals to DEFAULT_REGION_NAME
        defaultRegionShouldBeFound("regionName.equals=" + DEFAULT_REGION_NAME);

        // Get all the regionList where regionName equals to UPDATED_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.equals=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName in DEFAULT_REGION_NAME or UPDATED_REGION_NAME
        defaultRegionShouldBeFound("regionName.in=" + DEFAULT_REGION_NAME + "," + UPDATED_REGION_NAME);

        // Get all the regionList where regionName equals to UPDATED_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.in=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName is not null
        defaultRegionShouldBeFound("regionName.specified=true");

        // Get all the regionList where regionName is null
        defaultRegionShouldNotBeFound("regionName.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName contains DEFAULT_REGION_NAME
        defaultRegionShouldBeFound("regionName.contains=" + DEFAULT_REGION_NAME);

        // Get all the regionList where regionName contains UPDATED_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.contains=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionNameNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionName does not contain DEFAULT_REGION_NAME
        defaultRegionShouldNotBeFound("regionName.doesNotContain=" + DEFAULT_REGION_NAME);

        // Get all the regionList where regionName does not contain UPDATED_REGION_NAME
        defaultRegionShouldBeFound("regionName.doesNotContain=" + UPDATED_REGION_NAME);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description equals to DEFAULT_DESCRIPTION
        defaultRegionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the regionList where description equals to UPDATED_DESCRIPTION
        defaultRegionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRegionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the regionList where description equals to UPDATED_DESCRIPTION
        defaultRegionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description is not null
        defaultRegionShouldBeFound("description.specified=true");

        // Get all the regionList where description is null
        defaultRegionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description contains DEFAULT_DESCRIPTION
        defaultRegionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the regionList where description contains UPDATED_DESCRIPTION
        defaultRegionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where description does not contain DEFAULT_DESCRIPTION
        defaultRegionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the regionList where description does not contain UPDATED_DESCRIPTION
        defaultRegionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId equals to DEFAULT_REGION_ID
        defaultRegionShouldBeFound("regionId.equals=" + DEFAULT_REGION_ID);

        // Get all the regionList where regionId equals to UPDATED_REGION_ID
        defaultRegionShouldNotBeFound("regionId.equals=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId in DEFAULT_REGION_ID or UPDATED_REGION_ID
        defaultRegionShouldBeFound("regionId.in=" + DEFAULT_REGION_ID + "," + UPDATED_REGION_ID);

        // Get all the regionList where regionId equals to UPDATED_REGION_ID
        defaultRegionShouldNotBeFound("regionId.in=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId is not null
        defaultRegionShouldBeFound("regionId.specified=true");

        // Get all the regionList where regionId is null
        defaultRegionShouldNotBeFound("regionId.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId is greater than or equal to DEFAULT_REGION_ID
        defaultRegionShouldBeFound("regionId.greaterThanOrEqual=" + DEFAULT_REGION_ID);

        // Get all the regionList where regionId is greater than or equal to UPDATED_REGION_ID
        defaultRegionShouldNotBeFound("regionId.greaterThanOrEqual=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId is less than or equal to DEFAULT_REGION_ID
        defaultRegionShouldBeFound("regionId.lessThanOrEqual=" + DEFAULT_REGION_ID);

        // Get all the regionList where regionId is less than or equal to SMALLER_REGION_ID
        defaultRegionShouldNotBeFound("regionId.lessThanOrEqual=" + SMALLER_REGION_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId is less than DEFAULT_REGION_ID
        defaultRegionShouldNotBeFound("regionId.lessThan=" + DEFAULT_REGION_ID);

        // Get all the regionList where regionId is less than UPDATED_REGION_ID
        defaultRegionShouldBeFound("regionId.lessThan=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByRegionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where regionId is greater than DEFAULT_REGION_ID
        defaultRegionShouldNotBeFound("regionId.greaterThan=" + DEFAULT_REGION_ID);

        // Get all the regionList where regionId is greater than SMALLER_REGION_ID
        defaultRegionShouldBeFound("regionId.greaterThan=" + SMALLER_REGION_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId equals to DEFAULT_COMPANY_ID
        defaultRegionShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the regionList where companyId equals to UPDATED_COMPANY_ID
        defaultRegionShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultRegionShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the regionList where companyId equals to UPDATED_COMPANY_ID
        defaultRegionShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId is not null
        defaultRegionShouldBeFound("companyId.specified=true");

        // Get all the regionList where companyId is null
        defaultRegionShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultRegionShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the regionList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultRegionShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultRegionShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the regionList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultRegionShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId is less than DEFAULT_COMPANY_ID
        defaultRegionShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the regionList where companyId is less than UPDATED_COMPANY_ID
        defaultRegionShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where companyId is greater than DEFAULT_COMPANY_ID
        defaultRegionShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the regionList where companyId is greater than SMALLER_COMPANY_ID
        defaultRegionShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllRegionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where status equals to DEFAULT_STATUS
        defaultRegionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the regionList where status equals to UPDATED_STATUS
        defaultRegionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRegionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRegionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the regionList where status equals to UPDATED_STATUS
        defaultRegionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRegionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where status is not null
        defaultRegionShouldBeFound("status.specified=true");

        // Get all the regionList where status is null
        defaultRegionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where status contains DEFAULT_STATUS
        defaultRegionShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the regionList where status contains UPDATED_STATUS
        defaultRegionShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRegionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where status does not contain DEFAULT_STATUS
        defaultRegionShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the regionList where status does not contain UPDATED_STATUS
        defaultRegionShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRegionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the regionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRegionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRegionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the regionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRegionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModified is not null
        defaultRegionShouldBeFound("lastModified.specified=true");

        // Get all the regionList where lastModified is null
        defaultRegionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy is not null
        defaultRegionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the regionList where lastModifiedBy is null
        defaultRegionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRegionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRegionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the regionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRegionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionShouldBeFound(String filter) throws Exception {
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].regionId").value(hasItem(DEFAULT_REGION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionShouldNotBeFound(String filter) throws Exception {
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRegion() throws Exception {
        // Get the region
        restRegionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region
        Region updatedRegion = regionRepository.findById(region.getId()).get();
        // Disconnect from session so that the updates on updatedRegion are not directly saved in db
        em.detach(updatedRegion);
        updatedRegion
            .regionName(UPDATED_REGION_NAME)
            .description(UPDATED_DESCRIPTION)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        RegionDTO regionDTO = regionMapper.toDto(updatedRegion);

        restRegionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRegion.getRegionId()).isEqualTo(UPDATED_REGION_ID);
        assertThat(testRegion.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testRegion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRegion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegionWithPatch() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region using partial update
        Region partialUpdatedRegion = new Region();
        partialUpdatedRegion.setId(region.getId());

        partialUpdatedRegion
            .regionName(UPDATED_REGION_NAME)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);

        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegion))
            )
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRegion.getRegionId()).isEqualTo(UPDATED_REGION_ID);
        assertThat(testRegion.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testRegion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRegion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRegionWithPatch() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region using partial update
        Region partialUpdatedRegion = new Region();
        partialUpdatedRegion.setId(region.getId());

        partialUpdatedRegion
            .regionName(UPDATED_REGION_NAME)
            .description(UPDATED_DESCRIPTION)
            .regionId(UPDATED_REGION_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegion))
            )
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testRegion.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRegion.getRegionId()).isEqualTo(UPDATED_REGION_ID);
        assertThat(testRegion.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testRegion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRegion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRegion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
        region.setId(count.incrementAndGet());

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(regionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeDelete = regionRepository.findAll().size();

        // Delete the region
        restRegionMockMvc
            .perform(delete(ENTITY_API_URL_ID, region.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
