package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.EmploymentType;
import com.techvg.hrms.repository.EmploymentTypeRepository;
import com.techvg.hrms.service.criteria.EmploymentTypeCriteria;
import com.techvg.hrms.service.dto.EmploymentTypeDTO;
import com.techvg.hrms.service.mapper.EmploymentTypeMapper;
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
 * Integration tests for the {@link EmploymentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmploymentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employment-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmploymentTypeRepository employmentTypeRepository;

    @Autowired
    private EmploymentTypeMapper employmentTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploymentTypeMockMvc;

    private EmploymentType employmentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentType createEntity(EntityManager em) {
        EmploymentType employmentType = new EmploymentType()
            .name(DEFAULT_NAME)
            .subtype(DEFAULT_SUBTYPE)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return employmentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmploymentType createUpdatedEntity(EntityManager em) {
        EmploymentType employmentType = new EmploymentType()
            .name(UPDATED_NAME)
            .subtype(UPDATED_SUBTYPE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return employmentType;
    }

    @BeforeEach
    public void initTest() {
        employmentType = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploymentType() throws Exception {
        int databaseSizeBeforeCreate = employmentTypeRepository.findAll().size();
        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);
        restEmploymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmploymentType testEmploymentType = employmentTypeList.get(employmentTypeList.size() - 1);
        assertThat(testEmploymentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmploymentType.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testEmploymentType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEmploymentType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmploymentType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEmploymentType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEmploymentTypeWithExistingId() throws Exception {
        // Create the EmploymentType with an existing ID
        employmentType.setId(1L);
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        int databaseSizeBeforeCreate = employmentTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmploymentTypes() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList
        restEmploymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getEmploymentType() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get the employmentType
        restEmploymentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, employmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employmentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getEmploymentTypesByIdFiltering() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        Long id = employmentType.getId();

        defaultEmploymentTypeShouldBeFound("id.equals=" + id);
        defaultEmploymentTypeShouldNotBeFound("id.notEquals=" + id);

        defaultEmploymentTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmploymentTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmploymentTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmploymentTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where name equals to DEFAULT_NAME
        defaultEmploymentTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the employmentTypeList where name equals to UPDATED_NAME
        defaultEmploymentTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEmploymentTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the employmentTypeList where name equals to UPDATED_NAME
        defaultEmploymentTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where name is not null
        defaultEmploymentTypeShouldBeFound("name.specified=true");

        // Get all the employmentTypeList where name is null
        defaultEmploymentTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where name contains DEFAULT_NAME
        defaultEmploymentTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the employmentTypeList where name contains UPDATED_NAME
        defaultEmploymentTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where name does not contain DEFAULT_NAME
        defaultEmploymentTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the employmentTypeList where name does not contain UPDATED_NAME
        defaultEmploymentTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesBySubtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where subtype equals to DEFAULT_SUBTYPE
        defaultEmploymentTypeShouldBeFound("subtype.equals=" + DEFAULT_SUBTYPE);

        // Get all the employmentTypeList where subtype equals to UPDATED_SUBTYPE
        defaultEmploymentTypeShouldNotBeFound("subtype.equals=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesBySubtypeIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where subtype in DEFAULT_SUBTYPE or UPDATED_SUBTYPE
        defaultEmploymentTypeShouldBeFound("subtype.in=" + DEFAULT_SUBTYPE + "," + UPDATED_SUBTYPE);

        // Get all the employmentTypeList where subtype equals to UPDATED_SUBTYPE
        defaultEmploymentTypeShouldNotBeFound("subtype.in=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesBySubtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where subtype is not null
        defaultEmploymentTypeShouldBeFound("subtype.specified=true");

        // Get all the employmentTypeList where subtype is null
        defaultEmploymentTypeShouldNotBeFound("subtype.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesBySubtypeContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where subtype contains DEFAULT_SUBTYPE
        defaultEmploymentTypeShouldBeFound("subtype.contains=" + DEFAULT_SUBTYPE);

        // Get all the employmentTypeList where subtype contains UPDATED_SUBTYPE
        defaultEmploymentTypeShouldNotBeFound("subtype.contains=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesBySubtypeNotContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where subtype does not contain DEFAULT_SUBTYPE
        defaultEmploymentTypeShouldNotBeFound("subtype.doesNotContain=" + DEFAULT_SUBTYPE);

        // Get all the employmentTypeList where subtype does not contain UPDATED_SUBTYPE
        defaultEmploymentTypeShouldBeFound("subtype.doesNotContain=" + UPDATED_SUBTYPE);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId equals to DEFAULT_COMPANY_ID
        defaultEmploymentTypeShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the employmentTypeList where companyId equals to UPDATED_COMPANY_ID
        defaultEmploymentTypeShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultEmploymentTypeShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the employmentTypeList where companyId equals to UPDATED_COMPANY_ID
        defaultEmploymentTypeShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId is not null
        defaultEmploymentTypeShouldBeFound("companyId.specified=true");

        // Get all the employmentTypeList where companyId is null
        defaultEmploymentTypeShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultEmploymentTypeShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the employmentTypeList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultEmploymentTypeShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultEmploymentTypeShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the employmentTypeList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultEmploymentTypeShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId is less than DEFAULT_COMPANY_ID
        defaultEmploymentTypeShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the employmentTypeList where companyId is less than UPDATED_COMPANY_ID
        defaultEmploymentTypeShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where companyId is greater than DEFAULT_COMPANY_ID
        defaultEmploymentTypeShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the employmentTypeList where companyId is greater than SMALLER_COMPANY_ID
        defaultEmploymentTypeShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where status equals to DEFAULT_STATUS
        defaultEmploymentTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employmentTypeList where status equals to UPDATED_STATUS
        defaultEmploymentTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmploymentTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employmentTypeList where status equals to UPDATED_STATUS
        defaultEmploymentTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where status is not null
        defaultEmploymentTypeShouldBeFound("status.specified=true");

        // Get all the employmentTypeList where status is null
        defaultEmploymentTypeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByStatusContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where status contains DEFAULT_STATUS
        defaultEmploymentTypeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the employmentTypeList where status contains UPDATED_STATUS
        defaultEmploymentTypeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where status does not contain DEFAULT_STATUS
        defaultEmploymentTypeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the employmentTypeList where status does not contain UPDATED_STATUS
        defaultEmploymentTypeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEmploymentTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the employmentTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmploymentTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEmploymentTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the employmentTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmploymentTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModified is not null
        defaultEmploymentTypeShouldBeFound("lastModified.specified=true");

        // Get all the employmentTypeList where lastModified is null
        defaultEmploymentTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employmentTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the employmentTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModifiedBy is not null
        defaultEmploymentTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the employmentTypeList where lastModifiedBy is null
        defaultEmploymentTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employmentTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmploymentTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        // Get all the employmentTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employmentTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmploymentTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmploymentTypeShouldBeFound(String filter) throws Exception {
        restEmploymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restEmploymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmploymentTypeShouldNotBeFound(String filter) throws Exception {
        restEmploymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmploymentTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmploymentType() throws Exception {
        // Get the employmentType
        restEmploymentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmploymentType() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();

        // Update the employmentType
        EmploymentType updatedEmploymentType = employmentTypeRepository.findById(employmentType.getId()).get();
        // Disconnect from session so that the updates on updatedEmploymentType are not directly saved in db
        em.detach(updatedEmploymentType);
        updatedEmploymentType
            .name(UPDATED_NAME)
            .subtype(UPDATED_SUBTYPE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(updatedEmploymentType);

        restEmploymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EmploymentType testEmploymentType = employmentTypeList.get(employmentTypeList.size() - 1);
        assertThat(testEmploymentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentType.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testEmploymentType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEmploymentType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmploymentType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmploymentType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEmploymentType() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();
        employmentType.setId(count.incrementAndGet());

        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploymentType() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();
        employmentType.setId(count.incrementAndGet());

        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploymentType() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();
        employmentType.setId(count.incrementAndGet());

        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmploymentTypeWithPatch() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();

        // Update the employmentType using partial update
        EmploymentType partialUpdatedEmploymentType = new EmploymentType();
        partialUpdatedEmploymentType.setId(employmentType.getId());

        partialUpdatedEmploymentType.subtype(UPDATED_SUBTYPE).status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restEmploymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentType))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EmploymentType testEmploymentType = employmentTypeList.get(employmentTypeList.size() - 1);
        assertThat(testEmploymentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmploymentType.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testEmploymentType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEmploymentType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmploymentType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmploymentType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEmploymentTypeWithPatch() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();

        // Update the employmentType using partial update
        EmploymentType partialUpdatedEmploymentType = new EmploymentType();
        partialUpdatedEmploymentType.setId(employmentType.getId());

        partialUpdatedEmploymentType
            .name(UPDATED_NAME)
            .subtype(UPDATED_SUBTYPE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restEmploymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploymentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploymentType))
            )
            .andExpect(status().isOk());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EmploymentType testEmploymentType = employmentTypeList.get(employmentTypeList.size() - 1);
        assertThat(testEmploymentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmploymentType.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testEmploymentType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEmploymentType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmploymentType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmploymentType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEmploymentType() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();
        employmentType.setId(count.incrementAndGet());

        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employmentTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploymentType() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();
        employmentType.setId(count.incrementAndGet());

        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploymentType() throws Exception {
        int databaseSizeBeforeUpdate = employmentTypeRepository.findAll().size();
        employmentType.setId(count.incrementAndGet());

        // Create the EmploymentType
        EmploymentTypeDTO employmentTypeDTO = employmentTypeMapper.toDto(employmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmploymentType in the database
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploymentType() throws Exception {
        // Initialize the database
        employmentTypeRepository.saveAndFlush(employmentType);

        int databaseSizeBeforeDelete = employmentTypeRepository.findAll().size();

        // Delete the employmentType
        restEmploymentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employmentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmploymentType> employmentTypeList = employmentTypeRepository.findAll();
        assertThat(employmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
