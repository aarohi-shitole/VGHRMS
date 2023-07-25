package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Designation;
import com.techvg.hrms.repository.DesignationRepository;
import com.techvg.hrms.service.criteria.DesignationCriteria;
import com.techvg.hrms.service.dto.DesignationDTO;
import com.techvg.hrms.service.mapper.DesignationMapper;
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
 * Integration tests for the {@link DesignationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesignationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/designations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationMapper designationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesignationMockMvc;

    private Designation designation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createEntity(EntityManager em) {
        Designation designation = new Designation()
            .name(DEFAULT_NAME)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return designation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createUpdatedEntity(EntityManager em) {
        Designation designation = new Designation()
            .name(UPDATED_NAME)
            .departmentId(UPDATED_DEPARTMENT_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return designation;
    }

    @BeforeEach
    public void initTest() {
        designation = createEntity(em);
    }

    @Test
    @Transactional
    void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();
        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);
        restDesignationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDesignation.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testDesignation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testDesignation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDesignation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createDesignationWithExistingId() throws Exception {
        // Create the Designation with an existing ID
        designation.setId(1L);
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get the designation
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL_ID, designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(designation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getDesignationsByIdFiltering() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        Long id = designation.getId();

        defaultDesignationShouldBeFound("id.equals=" + id);
        defaultDesignationShouldNotBeFound("id.notEquals=" + id);

        defaultDesignationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDesignationShouldNotBeFound("id.greaterThan=" + id);

        defaultDesignationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDesignationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name equals to DEFAULT_NAME
        defaultDesignationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the designationList where name equals to UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDesignationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the designationList where name equals to UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name is not null
        defaultDesignationShouldBeFound("name.specified=true");

        // Get all the designationList where name is null
        defaultDesignationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByNameContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name contains DEFAULT_NAME
        defaultDesignationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the designationList where name contains UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name does not contain DEFAULT_NAME
        defaultDesignationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the designationList where name does not contain UPDATED_NAME
        defaultDesignationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultDesignationShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the designationList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultDesignationShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultDesignationShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the designationList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultDesignationShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId is not null
        defaultDesignationShouldBeFound("departmentId.specified=true");

        // Get all the designationList where departmentId is null
        defaultDesignationShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultDesignationShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the designationList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultDesignationShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultDesignationShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the designationList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultDesignationShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultDesignationShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the designationList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultDesignationShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultDesignationShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the designationList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultDesignationShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId equals to DEFAULT_COMPANY_ID
        defaultDesignationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the designationList where companyId equals to UPDATED_COMPANY_ID
        defaultDesignationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultDesignationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the designationList where companyId equals to UPDATED_COMPANY_ID
        defaultDesignationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId is not null
        defaultDesignationShouldBeFound("companyId.specified=true");

        // Get all the designationList where companyId is null
        defaultDesignationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultDesignationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the designationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultDesignationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultDesignationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the designationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultDesignationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId is less than DEFAULT_COMPANY_ID
        defaultDesignationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the designationList where companyId is less than UPDATED_COMPANY_ID
        defaultDesignationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultDesignationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the designationList where companyId is greater than SMALLER_COMPANY_ID
        defaultDesignationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDesignationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where status equals to DEFAULT_STATUS
        defaultDesignationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the designationList where status equals to UPDATED_STATUS
        defaultDesignationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDesignationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDesignationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the designationList where status equals to UPDATED_STATUS
        defaultDesignationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDesignationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where status is not null
        defaultDesignationShouldBeFound("status.specified=true");

        // Get all the designationList where status is null
        defaultDesignationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where status contains DEFAULT_STATUS
        defaultDesignationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the designationList where status contains UPDATED_STATUS
        defaultDesignationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDesignationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where status does not contain DEFAULT_STATUS
        defaultDesignationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the designationList where status does not contain UPDATED_STATUS
        defaultDesignationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDesignationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the designationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDesignationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDesignationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the designationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDesignationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModified is not null
        defaultDesignationShouldBeFound("lastModified.specified=true");

        // Get all the designationList where lastModified is null
        defaultDesignationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy is not null
        defaultDesignationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the designationList where lastModifiedBy is null
        defaultDesignationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDesignationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDesignationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the designationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDesignationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDesignationShouldBeFound(String filter) throws Exception {
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDesignationShouldNotBeFound(String filter) throws Exception {
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        Designation updatedDesignation = designationRepository.findById(designation.getId()).get();
        // Disconnect from session so that the updates on updatedDesignation are not directly saved in db
        em.detach(updatedDesignation);
        updatedDesignation
            .name(UPDATED_NAME)
            .departmentId(UPDATED_DEPARTMENT_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        DesignationDTO designationDTO = designationMapper.toDto(updatedDesignation);

        restDesignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignation.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testDesignation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDesignation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDesignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, designationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesignationWithPatch() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation using partial update
        Designation partialUpdatedDesignation = new Designation();
        partialUpdatedDesignation.setId(designation.getId());

        partialUpdatedDesignation
            .name(UPDATED_NAME)
            .departmentId(UPDATED_DEPARTMENT_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignation))
            )
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignation.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testDesignation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDesignation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDesignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDesignationWithPatch() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation using partial update
        Designation partialUpdatedDesignation = new Designation();
        partialUpdatedDesignation.setId(designation.getId());

        partialUpdatedDesignation
            .name(UPDATED_NAME)
            .departmentId(UPDATED_DEPARTMENT_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesignation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesignation))
            )
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignation.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testDesignation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDesignation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDesignation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDesignation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, designationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();
        designation.setId(count.incrementAndGet());

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesignationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(designationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Delete the designation
        restDesignationMockMvc
            .perform(delete(ENTITY_API_URL_ID, designation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
