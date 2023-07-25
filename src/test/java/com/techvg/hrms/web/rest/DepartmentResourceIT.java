package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Department;
import com.techvg.hrms.repository.DepartmentRepository;
import com.techvg.hrms.service.criteria.DepartmentCriteria;
import com.techvg.hrms.service.dto.DepartmentDTO;
import com.techvg.hrms.service.mapper.DepartmentMapper;
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
 * Integration tests for the {@link DepartmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepartmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/departments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartmentMockMvc;

    private Department department;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createEntity(EntityManager em) {
        Department department = new Department()
            .name(DEFAULT_NAME)
//            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS);
//            .lastModified(DEFAULT_LAST_MODIFIED)
//           .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return department;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createUpdatedEntity(EntityManager em) {
        Department department = new Department()
            .name(UPDATED_NAME)
        //    .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
       //     .lastModified(UPDATED_LAST_MODIFIED)
       //     .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return department;
    }

    @BeforeEach
    public void initTest() {
        department = createEntity(em);
    }

    @Test
    @Transactional
    void createDepartment() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();
        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);
        restDepartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate + 1);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartment.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testDepartment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDepartment.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDepartment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createDepartmentWithExistingId() throws Exception {
        // Create the Department with an existing ID
        department.setId(1L);
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartments() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get the department
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL_ID, department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(department.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getDepartmentsByIdFiltering() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        Long id = department.getId();

        defaultDepartmentShouldBeFound("id.equals=" + id);
        defaultDepartmentShouldNotBeFound("id.notEquals=" + id);

        defaultDepartmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepartmentShouldNotBeFound("id.greaterThan=" + id);

        defaultDepartmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepartmentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name equals to DEFAULT_NAME
        defaultDepartmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the departmentList where name equals to UPDATED_NAME
        defaultDepartmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDepartmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the departmentList where name equals to UPDATED_NAME
        defaultDepartmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name is not null
        defaultDepartmentShouldBeFound("name.specified=true");

        // Get all the departmentList where name is null
        defaultDepartmentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name contains DEFAULT_NAME
        defaultDepartmentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the departmentList where name contains UPDATED_NAME
        defaultDepartmentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where name does not contain DEFAULT_NAME
        defaultDepartmentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the departmentList where name does not contain UPDATED_NAME
        defaultDepartmentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId equals to DEFAULT_COMPANY_ID
        defaultDepartmentShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the departmentList where companyId equals to UPDATED_COMPANY_ID
        defaultDepartmentShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultDepartmentShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the departmentList where companyId equals to UPDATED_COMPANY_ID
        defaultDepartmentShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId is not null
        defaultDepartmentShouldBeFound("companyId.specified=true");

        // Get all the departmentList where companyId is null
        defaultDepartmentShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultDepartmentShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the departmentList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultDepartmentShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultDepartmentShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the departmentList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultDepartmentShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId is less than DEFAULT_COMPANY_ID
        defaultDepartmentShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the departmentList where companyId is less than UPDATED_COMPANY_ID
        defaultDepartmentShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDepartmentsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where companyId is greater than DEFAULT_COMPANY_ID
        defaultDepartmentShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the departmentList where companyId is greater than SMALLER_COMPANY_ID
        defaultDepartmentShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDepartmentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where status equals to DEFAULT_STATUS
        defaultDepartmentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the departmentList where status equals to UPDATED_STATUS
        defaultDepartmentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDepartmentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDepartmentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the departmentList where status equals to UPDATED_STATUS
        defaultDepartmentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDepartmentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where status is not null
        defaultDepartmentShouldBeFound("status.specified=true");

        // Get all the departmentList where status is null
        defaultDepartmentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByStatusContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where status contains DEFAULT_STATUS
        defaultDepartmentShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the departmentList where status contains UPDATED_STATUS
        defaultDepartmentShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDepartmentsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where status does not contain DEFAULT_STATUS
        defaultDepartmentShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the departmentList where status does not contain UPDATED_STATUS
        defaultDepartmentShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDepartmentShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the departmentList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDepartmentShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDepartmentShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the departmentList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDepartmentShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModified is not null
        defaultDepartmentShouldBeFound("lastModified.specified=true");

        // Get all the departmentList where lastModified is null
        defaultDepartmentShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDepartmentShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the departmentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDepartmentShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDepartmentShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the departmentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDepartmentShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModifiedBy is not null
        defaultDepartmentShouldBeFound("lastModifiedBy.specified=true");

        // Get all the departmentList where lastModifiedBy is null
        defaultDepartmentShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDepartmentShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the departmentList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDepartmentShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDepartmentsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDepartmentShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the departmentList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDepartmentShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartmentShouldBeFound(String filter) throws Exception {
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartmentShouldNotBeFound(String filter) throws Exception {
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartment() throws Exception {
        // Get the department
        restDepartmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department
        Department updatedDepartment = departmentRepository.findById(department.getId()).get();
        // Disconnect from session so that the updates on updatedDepartment are not directly saved in db
        em.detach(updatedDepartment);
        updatedDepartment
            .name(UPDATED_NAME)
            //.companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
           // .lastModified(UPDATED_LAST_MODIFIED)
           // .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        DepartmentDTO departmentDTO = departmentMapper.toDto(updatedDepartment);

        restDepartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartment.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDepartment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDepartment.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDepartment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();
        department.setId(count.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();
        department.setId(count.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();
        department.setId(count.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

    //    partialUpdatedDepartment.companyId(UPDATED_COMPANY_ID).lastModified(UPDATED_LAST_MODIFIED);

        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartment))
            )
            .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartment.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDepartment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDepartment.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDepartment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

        partialUpdatedDepartment
            .name(UPDATED_NAME)
     //       .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS);
    //        .lastModified(UPDATED_LAST_MODIFIED)
     //       .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDepartment))
            )
            .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartment.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDepartment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDepartment.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDepartment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();
        department.setId(count.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();
        department.setId(count.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(departmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();
        department.setId(count.incrementAndGet());

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(departmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeDelete = departmentRepository.findAll().size();

        // Delete the department
        restDepartmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, department.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
