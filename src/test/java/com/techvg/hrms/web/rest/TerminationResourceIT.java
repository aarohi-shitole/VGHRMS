package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Termination;
import com.techvg.hrms.repository.TerminationRepository;
import com.techvg.hrms.service.criteria.TerminationCriteria;
import com.techvg.hrms.service.dto.TerminationDTO;
import com.techvg.hrms.service.mapper.TerminationMapper;
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
 * Integration tests for the {@link TerminationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerminationResourceIT {

    private static final String DEFAULT_EMP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TERMINATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TERMINATION_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TERMINATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TERMINATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NOTICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NOTICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/terminations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerminationRepository terminationRepository;

    @Autowired
    private TerminationMapper terminationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerminationMockMvc;

    private Termination termination;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Termination createEntity(EntityManager em) {
        Termination termination = new Termination()
            .empName(DEFAULT_EMP_NAME)
            .terminationType(DEFAULT_TERMINATION_TYPE)
            .terminationDate(DEFAULT_TERMINATION_DATE)
            .noticeDate(DEFAULT_NOTICE_DATE)
            .reason(DEFAULT_REASON)
            .status(DEFAULT_STATUS)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return termination;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Termination createUpdatedEntity(EntityManager em) {
        Termination termination = new Termination()
            .empName(UPDATED_EMP_NAME)
            .terminationType(UPDATED_TERMINATION_TYPE)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .noticeDate(UPDATED_NOTICE_DATE)
            .reason(UPDATED_REASON)
            .status(UPDATED_STATUS)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return termination;
    }

    @BeforeEach
    public void initTest() {
        termination = createEntity(em);
    }

    @Test
    @Transactional
    void createTermination() throws Exception {
        int databaseSizeBeforeCreate = terminationRepository.findAll().size();
        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);
        restTerminationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeCreate + 1);
        Termination testTermination = terminationList.get(terminationList.size() - 1);
        assertThat(testTermination.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testTermination.getTerminationType()).isEqualTo(DEFAULT_TERMINATION_TYPE);
        assertThat(testTermination.getTerminationDate()).isEqualTo(DEFAULT_TERMINATION_DATE);
        assertThat(testTermination.getNoticeDate()).isEqualTo(DEFAULT_NOTICE_DATE);
        assertThat(testTermination.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testTermination.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTermination.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testTermination.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testTermination.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testTermination.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTermination.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTerminationWithExistingId() throws Exception {
        // Create the Termination with an existing ID
        termination.setId(1L);
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        int databaseSizeBeforeCreate = terminationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerminationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTerminations() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList
        restTerminationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termination.getId().intValue())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME)))
            .andExpect(jsonPath("$.[*].terminationType").value(hasItem(DEFAULT_TERMINATION_TYPE)))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].noticeDate").value(hasItem(DEFAULT_NOTICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTermination() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get the termination
        restTerminationMockMvc
            .perform(get(ENTITY_API_URL_ID, termination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termination.getId().intValue()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME))
            .andExpect(jsonPath("$.terminationType").value(DEFAULT_TERMINATION_TYPE))
            .andExpect(jsonPath("$.terminationDate").value(DEFAULT_TERMINATION_DATE.toString()))
            .andExpect(jsonPath("$.noticeDate").value(DEFAULT_NOTICE_DATE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTerminationsByIdFiltering() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        Long id = termination.getId();

        defaultTerminationShouldBeFound("id.equals=" + id);
        defaultTerminationShouldNotBeFound("id.notEquals=" + id);

        defaultTerminationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTerminationShouldNotBeFound("id.greaterThan=" + id);

        defaultTerminationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTerminationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmpNameIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where empName equals to DEFAULT_EMP_NAME
        defaultTerminationShouldBeFound("empName.equals=" + DEFAULT_EMP_NAME);

        // Get all the terminationList where empName equals to UPDATED_EMP_NAME
        defaultTerminationShouldNotBeFound("empName.equals=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmpNameIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where empName in DEFAULT_EMP_NAME or UPDATED_EMP_NAME
        defaultTerminationShouldBeFound("empName.in=" + DEFAULT_EMP_NAME + "," + UPDATED_EMP_NAME);

        // Get all the terminationList where empName equals to UPDATED_EMP_NAME
        defaultTerminationShouldNotBeFound("empName.in=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmpNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where empName is not null
        defaultTerminationShouldBeFound("empName.specified=true");

        // Get all the terminationList where empName is null
        defaultTerminationShouldNotBeFound("empName.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByEmpNameContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where empName contains DEFAULT_EMP_NAME
        defaultTerminationShouldBeFound("empName.contains=" + DEFAULT_EMP_NAME);

        // Get all the terminationList where empName contains UPDATED_EMP_NAME
        defaultTerminationShouldNotBeFound("empName.contains=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmpNameNotContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where empName does not contain DEFAULT_EMP_NAME
        defaultTerminationShouldNotBeFound("empName.doesNotContain=" + DEFAULT_EMP_NAME);

        // Get all the terminationList where empName does not contain UPDATED_EMP_NAME
        defaultTerminationShouldBeFound("empName.doesNotContain=" + UPDATED_EMP_NAME);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationType equals to DEFAULT_TERMINATION_TYPE
        defaultTerminationShouldBeFound("terminationType.equals=" + DEFAULT_TERMINATION_TYPE);

        // Get all the terminationList where terminationType equals to UPDATED_TERMINATION_TYPE
        defaultTerminationShouldNotBeFound("terminationType.equals=" + UPDATED_TERMINATION_TYPE);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationType in DEFAULT_TERMINATION_TYPE or UPDATED_TERMINATION_TYPE
        defaultTerminationShouldBeFound("terminationType.in=" + DEFAULT_TERMINATION_TYPE + "," + UPDATED_TERMINATION_TYPE);

        // Get all the terminationList where terminationType equals to UPDATED_TERMINATION_TYPE
        defaultTerminationShouldNotBeFound("terminationType.in=" + UPDATED_TERMINATION_TYPE);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationType is not null
        defaultTerminationShouldBeFound("terminationType.specified=true");

        // Get all the terminationList where terminationType is null
        defaultTerminationShouldNotBeFound("terminationType.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationTypeContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationType contains DEFAULT_TERMINATION_TYPE
        defaultTerminationShouldBeFound("terminationType.contains=" + DEFAULT_TERMINATION_TYPE);

        // Get all the terminationList where terminationType contains UPDATED_TERMINATION_TYPE
        defaultTerminationShouldNotBeFound("terminationType.contains=" + UPDATED_TERMINATION_TYPE);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationType does not contain DEFAULT_TERMINATION_TYPE
        defaultTerminationShouldNotBeFound("terminationType.doesNotContain=" + DEFAULT_TERMINATION_TYPE);

        // Get all the terminationList where terminationType does not contain UPDATED_TERMINATION_TYPE
        defaultTerminationShouldBeFound("terminationType.doesNotContain=" + UPDATED_TERMINATION_TYPE);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationDate equals to DEFAULT_TERMINATION_DATE
        defaultTerminationShouldBeFound("terminationDate.equals=" + DEFAULT_TERMINATION_DATE);

        // Get all the terminationList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultTerminationShouldNotBeFound("terminationDate.equals=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationDateIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationDate in DEFAULT_TERMINATION_DATE or UPDATED_TERMINATION_DATE
        defaultTerminationShouldBeFound("terminationDate.in=" + DEFAULT_TERMINATION_DATE + "," + UPDATED_TERMINATION_DATE);

        // Get all the terminationList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultTerminationShouldNotBeFound("terminationDate.in=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    void getAllTerminationsByTerminationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where terminationDate is not null
        defaultTerminationShouldBeFound("terminationDate.specified=true");

        // Get all the terminationList where terminationDate is null
        defaultTerminationShouldNotBeFound("terminationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByNoticeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where noticeDate equals to DEFAULT_NOTICE_DATE
        defaultTerminationShouldBeFound("noticeDate.equals=" + DEFAULT_NOTICE_DATE);

        // Get all the terminationList where noticeDate equals to UPDATED_NOTICE_DATE
        defaultTerminationShouldNotBeFound("noticeDate.equals=" + UPDATED_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllTerminationsByNoticeDateIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where noticeDate in DEFAULT_NOTICE_DATE or UPDATED_NOTICE_DATE
        defaultTerminationShouldBeFound("noticeDate.in=" + DEFAULT_NOTICE_DATE + "," + UPDATED_NOTICE_DATE);

        // Get all the terminationList where noticeDate equals to UPDATED_NOTICE_DATE
        defaultTerminationShouldNotBeFound("noticeDate.in=" + UPDATED_NOTICE_DATE);
    }

    @Test
    @Transactional
    void getAllTerminationsByNoticeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where noticeDate is not null
        defaultTerminationShouldBeFound("noticeDate.specified=true");

        // Get all the terminationList where noticeDate is null
        defaultTerminationShouldNotBeFound("noticeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where reason equals to DEFAULT_REASON
        defaultTerminationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the terminationList where reason equals to UPDATED_REASON
        defaultTerminationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllTerminationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultTerminationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the terminationList where reason equals to UPDATED_REASON
        defaultTerminationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllTerminationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where reason is not null
        defaultTerminationShouldBeFound("reason.specified=true");

        // Get all the terminationList where reason is null
        defaultTerminationShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where reason contains DEFAULT_REASON
        defaultTerminationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the terminationList where reason contains UPDATED_REASON
        defaultTerminationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllTerminationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where reason does not contain DEFAULT_REASON
        defaultTerminationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the terminationList where reason does not contain UPDATED_REASON
        defaultTerminationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllTerminationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where status equals to DEFAULT_STATUS
        defaultTerminationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the terminationList where status equals to UPDATED_STATUS
        defaultTerminationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTerminationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTerminationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the terminationList where status equals to UPDATED_STATUS
        defaultTerminationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTerminationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where status is not null
        defaultTerminationShouldBeFound("status.specified=true");

        // Get all the terminationList where status is null
        defaultTerminationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where status contains DEFAULT_STATUS
        defaultTerminationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the terminationList where status contains UPDATED_STATUS
        defaultTerminationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTerminationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where status does not contain DEFAULT_STATUS
        defaultTerminationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the terminationList where status does not contain UPDATED_STATUS
        defaultTerminationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultTerminationShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the terminationList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultTerminationShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultTerminationShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the terminationList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultTerminationShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId is not null
        defaultTerminationShouldBeFound("departmentId.specified=true");

        // Get all the terminationList where departmentId is null
        defaultTerminationShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultTerminationShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the terminationList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultTerminationShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultTerminationShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the terminationList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultTerminationShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultTerminationShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the terminationList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultTerminationShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultTerminationShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the terminationList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultTerminationShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultTerminationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the terminationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultTerminationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultTerminationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the terminationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultTerminationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId is not null
        defaultTerminationShouldBeFound("employeeId.specified=true");

        // Get all the terminationList where employeeId is null
        defaultTerminationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultTerminationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the terminationList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultTerminationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultTerminationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the terminationList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultTerminationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultTerminationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the terminationList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultTerminationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultTerminationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the terminationList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultTerminationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId equals to DEFAULT_COMPANY_ID
        defaultTerminationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the terminationList where companyId equals to UPDATED_COMPANY_ID
        defaultTerminationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultTerminationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the terminationList where companyId equals to UPDATED_COMPANY_ID
        defaultTerminationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId is not null
        defaultTerminationShouldBeFound("companyId.specified=true");

        // Get all the terminationList where companyId is null
        defaultTerminationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultTerminationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the terminationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultTerminationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultTerminationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the terminationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultTerminationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId is less than DEFAULT_COMPANY_ID
        defaultTerminationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the terminationList where companyId is less than UPDATED_COMPANY_ID
        defaultTerminationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultTerminationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the terminationList where companyId is greater than SMALLER_COMPANY_ID
        defaultTerminationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTerminationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the terminationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTerminationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTerminationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the terminationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTerminationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModified is not null
        defaultTerminationShouldBeFound("lastModified.specified=true");

        // Get all the terminationList where lastModified is null
        defaultTerminationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTerminationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the terminationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTerminationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTerminationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the terminationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTerminationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModifiedBy is not null
        defaultTerminationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the terminationList where lastModifiedBy is null
        defaultTerminationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTerminationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the terminationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTerminationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTerminationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        // Get all the terminationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTerminationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the terminationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTerminationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTerminationShouldBeFound(String filter) throws Exception {
        restTerminationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termination.getId().intValue())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME)))
            .andExpect(jsonPath("$.[*].terminationType").value(hasItem(DEFAULT_TERMINATION_TYPE)))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].noticeDate").value(hasItem(DEFAULT_NOTICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTerminationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTerminationShouldNotBeFound(String filter) throws Exception {
        restTerminationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTerminationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTermination() throws Exception {
        // Get the termination
        restTerminationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermination() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();

        // Update the termination
        Termination updatedTermination = terminationRepository.findById(termination.getId()).get();
        // Disconnect from session so that the updates on updatedTermination are not directly saved in db
        em.detach(updatedTermination);
        updatedTermination
            .empName(UPDATED_EMP_NAME)
            .terminationType(UPDATED_TERMINATION_TYPE)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .noticeDate(UPDATED_NOTICE_DATE)
            .reason(UPDATED_REASON)
            .status(UPDATED_STATUS)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TerminationDTO terminationDTO = terminationMapper.toDto(updatedTermination);

        restTerminationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terminationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
        Termination testTermination = terminationList.get(terminationList.size() - 1);
        assertThat(testTermination.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testTermination.getTerminationType()).isEqualTo(UPDATED_TERMINATION_TYPE);
        assertThat(testTermination.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testTermination.getNoticeDate()).isEqualTo(UPDATED_NOTICE_DATE);
        assertThat(testTermination.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testTermination.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTermination.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTermination.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTermination.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTermination.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTermination.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTermination() throws Exception {
        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();
        termination.setId(count.incrementAndGet());

        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terminationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermination() throws Exception {
        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();
        termination.setId(count.incrementAndGet());

        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermination() throws Exception {
        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();
        termination.setId(count.incrementAndGet());

        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terminationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerminationWithPatch() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();

        // Update the termination using partial update
        Termination partialUpdatedTermination = new Termination();
        partialUpdatedTermination.setId(termination.getId());

        partialUpdatedTermination
            .empName(UPDATED_EMP_NAME)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .departmentId(UPDATED_DEPARTMENT_ID);
//            .employeeId(UPDATED_EMPLOYEE_ID)
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED);

        restTerminationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermination.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTermination))
            )
            .andExpect(status().isOk());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
        Termination testTermination = terminationList.get(terminationList.size() - 1);
        assertThat(testTermination.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testTermination.getTerminationType()).isEqualTo(DEFAULT_TERMINATION_TYPE);
        assertThat(testTermination.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testTermination.getNoticeDate()).isEqualTo(DEFAULT_NOTICE_DATE);
        assertThat(testTermination.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testTermination.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTermination.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTermination.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTermination.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTermination.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTermination.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTerminationWithPatch() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();

        // Update the termination using partial update
        Termination partialUpdatedTermination = new Termination();
        partialUpdatedTermination.setId(termination.getId());

        partialUpdatedTermination
            .empName(UPDATED_EMP_NAME)
            .terminationType(UPDATED_TERMINATION_TYPE)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .noticeDate(UPDATED_NOTICE_DATE)
            .reason(UPDATED_REASON)
            .status(UPDATED_STATUS)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTerminationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermination.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTermination))
            )
            .andExpect(status().isOk());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
        Termination testTermination = terminationList.get(terminationList.size() - 1);
        assertThat(testTermination.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testTermination.getTerminationType()).isEqualTo(UPDATED_TERMINATION_TYPE);
        assertThat(testTermination.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testTermination.getNoticeDate()).isEqualTo(UPDATED_NOTICE_DATE);
        assertThat(testTermination.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testTermination.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTermination.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testTermination.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testTermination.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testTermination.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTermination.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTermination() throws Exception {
        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();
        termination.setId(count.incrementAndGet());

        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerminationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, terminationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermination() throws Exception {
        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();
        termination.setId(count.incrementAndGet());

        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermination() throws Exception {
        int databaseSizeBeforeUpdate = terminationRepository.findAll().size();
        termination.setId(count.incrementAndGet());

        // Create the Termination
        TerminationDTO terminationDTO = terminationMapper.toDto(termination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerminationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(terminationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Termination in the database
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermination() throws Exception {
        // Initialize the database
        terminationRepository.saveAndFlush(termination);

        int databaseSizeBeforeDelete = terminationRepository.findAll().size();

        // Delete the termination
        restTerminationMockMvc
            .perform(delete(ENTITY_API_URL_ID, termination.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Termination> terminationList = terminationRepository.findAll();
        assertThat(terminationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
