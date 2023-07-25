package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Approval;
import com.techvg.hrms.repository.ApprovalRepository;
import com.techvg.hrms.service.criteria.ApprovalCriteria;
import com.techvg.hrms.service.dto.ApprovalDTO;
import com.techvg.hrms.service.mapper.ApprovalMapper;
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
 * Integration tests for the {@link ApprovalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprovalResourceIT {

    private static final String DEFAULT_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REF_TABLE = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TABLE_ID = 1L;
    private static final Long UPDATED_REF_TABLE_ID = 2L;
    private static final Long SMALLER_REF_TABLE_ID = 1L - 1L;

    private static final Long DEFAULT_APPROVER_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_APPROVER_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_APPROVER_EMPLOYEE_ID = 1L - 1L;

    private static final String DEFAULT_APPROVAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_APPROVAL_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_SEQUENCE = 1L;
    private static final Long UPDATED_SEQUENCE = 2L;
    private static final Long SMALLER_SEQUENCE = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ApprovalMapper approvalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprovalMockMvc;

    private Approval approval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approval createEntity(EntityManager em) {
        Approval approval = new Approval()
            .refTable(DEFAULT_REF_TABLE)
            .refTableId(DEFAULT_REF_TABLE_ID)
            .approverEmployeeId(DEFAULT_APPROVER_EMPLOYEE_ID)
            .approvalStatus(DEFAULT_APPROVAL_STATUS)
            .sequence(DEFAULT_SEQUENCE)
            .status(DEFAULT_STATUS);
//    //        .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return approval;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Approval createUpdatedEntity(EntityManager em) {
        Approval approval = new Approval()
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            .approverEmployeeId(UPDATED_APPROVER_EMPLOYEE_ID)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .sequence(UPDATED_SEQUENCE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return approval;
    }

    @BeforeEach
    public void initTest() {
        approval = createEntity(em);
    }

    @Test
    @Transactional
    void createApproval() throws Exception {
        int databaseSizeBeforeCreate = approvalRepository.findAll().size();
        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);
        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isCreated());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeCreate + 1);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testApproval.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
        assertThat(testApproval.getApproverEmployeeId()).isEqualTo(DEFAULT_APPROVER_EMPLOYEE_ID);
        assertThat(testApproval.getApprovalStatus()).isEqualTo(DEFAULT_APPROVAL_STATUS);
        assertThat(testApproval.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testApproval.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApproval.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testApproval.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testApproval.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createApprovalWithExistingId() throws Exception {
        // Create the Approval with an existing ID
        approval.setId(1L);
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        int databaseSizeBeforeCreate = approvalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApprovals() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approval.getId().intValue())))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].approverEmployeeId").value(hasItem(DEFAULT_APPROVER_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getApproval() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get the approval
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL_ID, approval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(approval.getId().intValue()))
            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
            .andExpect(jsonPath("$.refTableId").value(DEFAULT_REF_TABLE_ID.intValue()))
            .andExpect(jsonPath("$.approverEmployeeId").value(DEFAULT_APPROVER_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.approvalStatus").value(DEFAULT_APPROVAL_STATUS))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getApprovalsByIdFiltering() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        Long id = approval.getId();

        defaultApprovalShouldBeFound("id.equals=" + id);
        defaultApprovalShouldNotBeFound("id.notEquals=" + id);

        defaultApprovalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApprovalShouldNotBeFound("id.greaterThan=" + id);

        defaultApprovalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApprovalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTable equals to DEFAULT_REF_TABLE
        defaultApprovalShouldBeFound("refTable.equals=" + DEFAULT_REF_TABLE);

        // Get all the approvalList where refTable equals to UPDATED_REF_TABLE
        defaultApprovalShouldNotBeFound("refTable.equals=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTable in DEFAULT_REF_TABLE or UPDATED_REF_TABLE
        defaultApprovalShouldBeFound("refTable.in=" + DEFAULT_REF_TABLE + "," + UPDATED_REF_TABLE);

        // Get all the approvalList where refTable equals to UPDATED_REF_TABLE
        defaultApprovalShouldNotBeFound("refTable.in=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTable is not null
        defaultApprovalShouldBeFound("refTable.specified=true");

        // Get all the approvalList where refTable is null
        defaultApprovalShouldNotBeFound("refTable.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTable contains DEFAULT_REF_TABLE
        defaultApprovalShouldBeFound("refTable.contains=" + DEFAULT_REF_TABLE);

        // Get all the approvalList where refTable contains UPDATED_REF_TABLE
        defaultApprovalShouldNotBeFound("refTable.contains=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTable does not contain DEFAULT_REF_TABLE
        defaultApprovalShouldNotBeFound("refTable.doesNotContain=" + DEFAULT_REF_TABLE);

        // Get all the approvalList where refTable does not contain UPDATED_REF_TABLE
        defaultApprovalShouldBeFound("refTable.doesNotContain=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId equals to DEFAULT_REF_TABLE_ID
        defaultApprovalShouldBeFound("refTableId.equals=" + DEFAULT_REF_TABLE_ID);

        // Get all the approvalList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultApprovalShouldNotBeFound("refTableId.equals=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId in DEFAULT_REF_TABLE_ID or UPDATED_REF_TABLE_ID
        defaultApprovalShouldBeFound("refTableId.in=" + DEFAULT_REF_TABLE_ID + "," + UPDATED_REF_TABLE_ID);

        // Get all the approvalList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultApprovalShouldNotBeFound("refTableId.in=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId is not null
        defaultApprovalShouldBeFound("refTableId.specified=true");

        // Get all the approvalList where refTableId is null
        defaultApprovalShouldNotBeFound("refTableId.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId is greater than or equal to DEFAULT_REF_TABLE_ID
        defaultApprovalShouldBeFound("refTableId.greaterThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the approvalList where refTableId is greater than or equal to UPDATED_REF_TABLE_ID
        defaultApprovalShouldNotBeFound("refTableId.greaterThanOrEqual=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId is less than or equal to DEFAULT_REF_TABLE_ID
        defaultApprovalShouldBeFound("refTableId.lessThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the approvalList where refTableId is less than or equal to SMALLER_REF_TABLE_ID
        defaultApprovalShouldNotBeFound("refTableId.lessThanOrEqual=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId is less than DEFAULT_REF_TABLE_ID
        defaultApprovalShouldNotBeFound("refTableId.lessThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the approvalList where refTableId is less than UPDATED_REF_TABLE_ID
        defaultApprovalShouldBeFound("refTableId.lessThan=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByRefTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where refTableId is greater than DEFAULT_REF_TABLE_ID
        defaultApprovalShouldNotBeFound("refTableId.greaterThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the approvalList where refTableId is greater than SMALLER_REF_TABLE_ID
        defaultApprovalShouldBeFound("refTableId.greaterThan=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId equals to DEFAULT_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldBeFound("approverEmployeeId.equals=" + DEFAULT_APPROVER_EMPLOYEE_ID);

        // Get all the approvalList where approverEmployeeId equals to UPDATED_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldNotBeFound("approverEmployeeId.equals=" + UPDATED_APPROVER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId in DEFAULT_APPROVER_EMPLOYEE_ID or UPDATED_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldBeFound("approverEmployeeId.in=" + DEFAULT_APPROVER_EMPLOYEE_ID + "," + UPDATED_APPROVER_EMPLOYEE_ID);

        // Get all the approvalList where approverEmployeeId equals to UPDATED_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldNotBeFound("approverEmployeeId.in=" + UPDATED_APPROVER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId is not null
        defaultApprovalShouldBeFound("approverEmployeeId.specified=true");

        // Get all the approvalList where approverEmployeeId is null
        defaultApprovalShouldNotBeFound("approverEmployeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId is greater than or equal to DEFAULT_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldBeFound("approverEmployeeId.greaterThanOrEqual=" + DEFAULT_APPROVER_EMPLOYEE_ID);

        // Get all the approvalList where approverEmployeeId is greater than or equal to UPDATED_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldNotBeFound("approverEmployeeId.greaterThanOrEqual=" + UPDATED_APPROVER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId is less than or equal to DEFAULT_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldBeFound("approverEmployeeId.lessThanOrEqual=" + DEFAULT_APPROVER_EMPLOYEE_ID);

        // Get all the approvalList where approverEmployeeId is less than or equal to SMALLER_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldNotBeFound("approverEmployeeId.lessThanOrEqual=" + SMALLER_APPROVER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId is less than DEFAULT_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldNotBeFound("approverEmployeeId.lessThan=" + DEFAULT_APPROVER_EMPLOYEE_ID);

        // Get all the approvalList where approverEmployeeId is less than UPDATED_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldBeFound("approverEmployeeId.lessThan=" + UPDATED_APPROVER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApproverEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approverEmployeeId is greater than DEFAULT_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldNotBeFound("approverEmployeeId.greaterThan=" + DEFAULT_APPROVER_EMPLOYEE_ID);

        // Get all the approvalList where approverEmployeeId is greater than SMALLER_APPROVER_EMPLOYEE_ID
        defaultApprovalShouldBeFound("approverEmployeeId.greaterThan=" + SMALLER_APPROVER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByApprovalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approvalStatus equals to DEFAULT_APPROVAL_STATUS
        defaultApprovalShouldBeFound("approvalStatus.equals=" + DEFAULT_APPROVAL_STATUS);

        // Get all the approvalList where approvalStatus equals to UPDATED_APPROVAL_STATUS
        defaultApprovalShouldNotBeFound("approvalStatus.equals=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByApprovalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approvalStatus in DEFAULT_APPROVAL_STATUS or UPDATED_APPROVAL_STATUS
        defaultApprovalShouldBeFound("approvalStatus.in=" + DEFAULT_APPROVAL_STATUS + "," + UPDATED_APPROVAL_STATUS);

        // Get all the approvalList where approvalStatus equals to UPDATED_APPROVAL_STATUS
        defaultApprovalShouldNotBeFound("approvalStatus.in=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByApprovalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approvalStatus is not null
        defaultApprovalShouldBeFound("approvalStatus.specified=true");

        // Get all the approvalList where approvalStatus is null
        defaultApprovalShouldNotBeFound("approvalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByApprovalStatusContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approvalStatus contains DEFAULT_APPROVAL_STATUS
        defaultApprovalShouldBeFound("approvalStatus.contains=" + DEFAULT_APPROVAL_STATUS);

        // Get all the approvalList where approvalStatus contains UPDATED_APPROVAL_STATUS
        defaultApprovalShouldNotBeFound("approvalStatus.contains=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByApprovalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where approvalStatus does not contain DEFAULT_APPROVAL_STATUS
        defaultApprovalShouldNotBeFound("approvalStatus.doesNotContain=" + DEFAULT_APPROVAL_STATUS);

        // Get all the approvalList where approvalStatus does not contain UPDATED_APPROVAL_STATUS
        defaultApprovalShouldBeFound("approvalStatus.doesNotContain=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence equals to DEFAULT_SEQUENCE
        defaultApprovalShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the approvalList where sequence equals to UPDATED_SEQUENCE
        defaultApprovalShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultApprovalShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the approvalList where sequence equals to UPDATED_SEQUENCE
        defaultApprovalShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence is not null
        defaultApprovalShouldBeFound("sequence.specified=true");

        // Get all the approvalList where sequence is null
        defaultApprovalShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence is greater than or equal to DEFAULT_SEQUENCE
        defaultApprovalShouldBeFound("sequence.greaterThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the approvalList where sequence is greater than or equal to UPDATED_SEQUENCE
        defaultApprovalShouldNotBeFound("sequence.greaterThanOrEqual=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence is less than or equal to DEFAULT_SEQUENCE
        defaultApprovalShouldBeFound("sequence.lessThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the approvalList where sequence is less than or equal to SMALLER_SEQUENCE
        defaultApprovalShouldNotBeFound("sequence.lessThanOrEqual=" + SMALLER_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence is less than DEFAULT_SEQUENCE
        defaultApprovalShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the approvalList where sequence is less than UPDATED_SEQUENCE
        defaultApprovalShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalsBySequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where sequence is greater than DEFAULT_SEQUENCE
        defaultApprovalShouldNotBeFound("sequence.greaterThan=" + DEFAULT_SEQUENCE);

        // Get all the approvalList where sequence is greater than SMALLER_SEQUENCE
        defaultApprovalShouldBeFound("sequence.greaterThan=" + SMALLER_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllApprovalsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where status equals to DEFAULT_STATUS
        defaultApprovalShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the approvalList where status equals to UPDATED_STATUS
        defaultApprovalShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApprovalShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the approvalList where status equals to UPDATED_STATUS
        defaultApprovalShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where status is not null
        defaultApprovalShouldBeFound("status.specified=true");

        // Get all the approvalList where status is null
        defaultApprovalShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByStatusContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where status contains DEFAULT_STATUS
        defaultApprovalShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the approvalList where status contains UPDATED_STATUS
        defaultApprovalShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where status does not contain DEFAULT_STATUS
        defaultApprovalShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the approvalList where status does not contain UPDATED_STATUS
        defaultApprovalShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId equals to DEFAULT_COMPANY_ID
        defaultApprovalShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the approvalList where companyId equals to UPDATED_COMPANY_ID
        defaultApprovalShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultApprovalShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the approvalList where companyId equals to UPDATED_COMPANY_ID
        defaultApprovalShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId is not null
        defaultApprovalShouldBeFound("companyId.specified=true");

        // Get all the approvalList where companyId is null
        defaultApprovalShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultApprovalShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approvalList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultApprovalShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultApprovalShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the approvalList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultApprovalShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId is less than DEFAULT_COMPANY_ID
        defaultApprovalShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the approvalList where companyId is less than UPDATED_COMPANY_ID
        defaultApprovalShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where companyId is greater than DEFAULT_COMPANY_ID
        defaultApprovalShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the approvalList where companyId is greater than SMALLER_COMPANY_ID
        defaultApprovalShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultApprovalShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the approvalList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApprovalShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultApprovalShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the approvalList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultApprovalShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModified is not null
        defaultApprovalShouldBeFound("lastModified.specified=true");

        // Get all the approvalList where lastModified is null
        defaultApprovalShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApprovalShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApprovalShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApprovalShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the approvalList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApprovalShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModifiedBy is not null
        defaultApprovalShouldBeFound("lastModifiedBy.specified=true");

        // Get all the approvalList where lastModifiedBy is null
        defaultApprovalShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApprovalShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApprovalShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllApprovalsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        // Get all the approvalList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApprovalShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the approvalList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApprovalShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApprovalShouldBeFound(String filter) throws Exception {
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(approval.getId().intValue())))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].approverEmployeeId").value(hasItem(DEFAULT_APPROVER_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApprovalShouldNotBeFound(String filter) throws Exception {
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApprovalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingApproval() throws Exception {
        // Get the approval
        restApprovalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApproval() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval
        Approval updatedApproval = approvalRepository.findById(approval.getId()).get();
        // Disconnect from session so that the updates on updatedApproval are not directly saved in db
        em.detach(updatedApproval);
        updatedApproval
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            .approverEmployeeId(UPDATED_APPROVER_EMPLOYEE_ID)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .sequence(UPDATED_SEQUENCE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ApprovalDTO approvalDTO = approvalMapper.toDto(updatedApproval);

        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testApproval.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testApproval.getApproverEmployeeId()).isEqualTo(UPDATED_APPROVER_EMPLOYEE_ID);
        assertThat(testApproval.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
        assertThat(testApproval.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testApproval.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApproval.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testApproval.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApproval.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(count.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(count.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(count.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(approvalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprovalWithPatch() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval using partial update
        Approval partialUpdatedApproval = new Approval();
        partialUpdatedApproval.setId(approval.getId());

        partialUpdatedApproval
            .refTableId(UPDATED_REF_TABLE_ID)
            .approverEmployeeId(UPDATED_APPROVER_EMPLOYEE_ID)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .sequence(UPDATED_SEQUENCE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED);

        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproval))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testApproval.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testApproval.getApproverEmployeeId()).isEqualTo(UPDATED_APPROVER_EMPLOYEE_ID);
        assertThat(testApproval.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
        assertThat(testApproval.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testApproval.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApproval.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testApproval.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApproval.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateApprovalWithPatch() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();

        // Update the approval using partial update
        Approval partialUpdatedApproval = new Approval();
        partialUpdatedApproval.setId(approval.getId());

        partialUpdatedApproval
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            .approverEmployeeId(UPDATED_APPROVER_EMPLOYEE_ID)
            .approvalStatus(UPDATED_APPROVAL_STATUS)
            .sequence(UPDATED_SEQUENCE)
            .status(UPDATED_STATUS);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApproval))
            )
            .andExpect(status().isOk());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
        Approval testApproval = approvalList.get(approvalList.size() - 1);
        assertThat(testApproval.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testApproval.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testApproval.getApproverEmployeeId()).isEqualTo(UPDATED_APPROVER_EMPLOYEE_ID);
        assertThat(testApproval.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
        assertThat(testApproval.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testApproval.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApproval.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testApproval.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testApproval.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(count.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, approvalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(count.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApproval() throws Exception {
        int databaseSizeBeforeUpdate = approvalRepository.findAll().size();
        approval.setId(count.incrementAndGet());

        // Create the Approval
        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(approvalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Approval in the database
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApproval() throws Exception {
        // Initialize the database
        approvalRepository.saveAndFlush(approval);

        int databaseSizeBeforeDelete = approvalRepository.findAll().size();

        // Delete the approval
        restApprovalMockMvc
            .perform(delete(ENTITY_API_URL_ID, approval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Approval> approvalList = approvalRepository.findAll();
        assertThat(approvalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
