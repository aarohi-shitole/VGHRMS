//package com.techvg.hrms.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.techvg.hrms.IntegrationTest;
//import com.techvg.hrms.domain.Approval;
//import com.techvg.hrms.repository.ApprovalRepository;
//import com.techvg.hrms.service.criteria.ApprovalCriteria;
//import com.techvg.hrms.service.dto.ApprovalDTO;
//import com.techvg.hrms.service.mapper.ApprovalMapper;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link ApprovalResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ApprovalResourceIT {
//
//    private static final Long DEFAULT_LEAVE_APP_ID = 1L;
//    private static final Long UPDATED_LEAVE_APP_ID = 2L;
//    private static final Long SMALLER_LEAVE_APP_ID = 1L - 1L;
//
//    private static final Long DEFAULT_APPROVER_EMP_ID = 1L;
//    private static final Long UPDATED_APPROVER_EMP_ID = 2L;
//    private static final Long SMALLER_APPROVER_EMP_ID = 1L - 1L;
//
//    private static final String DEFAULT_LEAVE_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_LEAVE_STATUS = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_SEQUENCE = 1L;
//    private static final Long UPDATED_SEQUENCE = 2L;
//    private static final Long SMALLER_SEQUENCE = 1L - 1L;
//
//    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_COMPANY_ID = 1L;
//    private static final Long UPDATED_COMPANY_ID = 2L;
//    private static final Long SMALLER_COMPANY_ID = 1L - 1L;
//
//    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/leave-application-approvals";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private ApprovalRepository approvalRepository;
//
//    @Autowired
//    private ApprovalMapper approvalMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restApprovalMockMvc;
//
//    private Approval approval;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Approval createEntity(EntityManager em) {
//        Approval approval = new Approval()
//            .leaveApplicationId(DEFAULT_LEAVE_APP_ID)
//            .approverEmpId(DEFAULT_APPROVER_EMP_ID)
//            .leaveStatus(DEFAULT_LEAVE_STATUS)
//            .sequence(DEFAULT_SEQUENCE)
//            .status(DEFAULT_STATUS);
////            .companyId(DEFAULT_COMPANY_ID)
////            .lastModified(DEFAULT_LAST_MODIFIED)
////            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
//        return approval;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Approval createUpdatedEntity(EntityManager em) {
//        Approval approval = new Approval()
//            .leaveApplicationId(UPDATED_LEAVE_APP_ID)
//            .approverEmpId(UPDATED_APPROVER_EMP_ID)
//            .leaveStatus(UPDATED_LEAVE_STATUS)
//            .sequence(UPDATED_SEQUENCE)
//            .status(UPDATED_STATUS);
////            .companyId(UPDATED_COMPANY_ID)
////            .lastModified(UPDATED_LAST_MODIFIED)
////            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
//        return approval;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        approval = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createApproval() throws Exception {
//        int databaseSizeBeforeCreate = approvalRepository.findAll().size();
//        // Create the Approval
//        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);
//        restApprovalMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the Approval in the database
//        List<Approval> approvalList = approvalRepository.findAll();
//        assertThat(approvalList).hasSize(databaseSizeBeforeCreate + 1);
//        Approval testApproval = approvalList.get(approvalList.size() - 1);
//        assertThat(testApproval.getId()).isEqualTo(DEFAULT_LEAVE_APP_ID);
//        assertThat(testApproval.getApproverEmpId()).isEqualTo(DEFAULT_APPROVER_EMP_ID);
//        assertThat(testApproval.getLeaveStatus()).isEqualTo(DEFAULT_LEAVE_STATUS);
//        assertThat(testApproval.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
//        assertThat(testApproval.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testApproval.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
//        assertThat(testApproval.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
//        assertThat(testApproval.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void createApprovalWithExistingId() throws Exception {
//        // Create the Approval with an existing ID
//        approval.setId(1L);
//        ApprovalDTO approvalDTO = approvalMapper.toDto(approval);
//
//        int databaseSizeBeforeCreate = approvalRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restApprovalMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(approvalDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Approval in the database
//        List<Approval> approvalList = approvalRepository.findAll();
//        assertThat(approvalList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllApprovals() throws Exception {
//        // Initialize the database
//        approvalRepository.saveAndFlush(approval);
//
//        // Get all the ApprovalList
//        restApprovalMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(approval.getId().intValue())))
//            .andExpect(jsonPath("$.[*].leaveApplicationId").value(hasItem(DEFAULT_LEAVE_APP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].approverEmpId").value(hasItem(DEFAULT_APPROVER_EMP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].leaveStatus").value(hasItem(DEFAULT_LEAVE_STATUS)))
//            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.intValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
//    }
//
//    @Test
//    @Transactional
//    void getApproval() throws Exception {
//        // Initialize the database
//        approvalRepository.saveAndFlush(approval);
//
//        // Get the Approval
//        restApprovalMockMvc
//            .perform(get(ENTITY_API_URL_ID, approval.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(approval.getId().intValue()))
//            .andExpect(jsonPath("$.leaveApplicationId").value(DEFAULT_LEAVE_APP_ID.intValue()))
//            .andExpect(jsonPath("$.approverEmpId").value(DEFAULT_APPROVER_EMP_ID.intValue()))
//            .andExpect(jsonPath("$.leaveStatus").value(DEFAULT_LEAVE_STATUS))
//            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.intValue()))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
//            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
//            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
//    }
//
//    @Test
//    @Transactional
//    void getApprovalsByIdFiltering() throws Exception {
//        // Initialize the database
//        approvalRepository.saveAndFlush(approval);
//
//        Long id = approval.getId();
//
//        defaultApprovalShouldBeFound("id.equals=" + id);
//        defaultApprovalShouldNotBeFound("id.notEquals=" + id);
//
//        defaultApprovalShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultApprovalShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultApprovalShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultApprovalShouldNotBeFound("id.lessThan=" + id);
//    }
//
//    @Test
//    @Transactional
//    void getAllApprovalsByLeaveAppIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        approvalRepository.saveAndFlush(approval);
//
//        // Get all the ApprovalList where leaveApplicationId equals to DEFAULT_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.equals=" + DEFAULT_LEAVE_APP_ID);
//
//        // Get all the approvalList where leaveApplicationId equals to UPDATED_LEAVE_APP_ID
//        defaultApprovalShouldNotBeFound("leaveApplicationId.equals=" + UPDATED_LEAVE_APP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllApprovalsByLeaveAppIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        ApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId in DEFAULT_LEAVE_APP_ID or UPDATED_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.in=" + DEFAULT_LEAVE_APP_ID + "," + UPDATED_LEAVE_APP_ID);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId equals to UPDATED_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveApplicationId.in=" + UPDATED_LEAVE_APP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveAppIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is not null
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.specified=true");
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveApplicationId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveAppIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is greater than or equal to DEFAULT_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.greaterThanOrEqual=" + DEFAULT_LEAVE_APP_ID);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is greater than or equal to UPDATED_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveApplicationId.greaterThanOrEqual=" + UPDATED_LEAVE_APP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveAppIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is less than or equal to DEFAULT_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.lessThanOrEqual=" + DEFAULT_LEAVE_APP_ID);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is less than or equal to SMALLER_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveApplicationId.lessThanOrEqual=" + SMALLER_LEAVE_APP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveAppIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is less than DEFAULT_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveApplicationId.lessThan=" + DEFAULT_LEAVE_APP_ID);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is less than UPDATED_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.lessThan=" + UPDATED_LEAVE_APP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveAppIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is greater than DEFAULT_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveApplicationId.greaterThan=" + DEFAULT_LEAVE_APP_ID);
//
//        // Get all the leaveApplicationApprovalList where leaveApplicationId is greater than SMALLER_LEAVE_APP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("leaveApplicationId.greaterThan=" + SMALLER_LEAVE_APP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId equals to DEFAULT_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.equals=" + DEFAULT_APPROVER_EMP_ID);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId equals to UPDATED_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.equals=" + UPDATED_APPROVER_EMP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId in DEFAULT_APPROVER_EMP_ID or UPDATED_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.in=" + DEFAULT_APPROVER_EMP_ID + "," + UPDATED_APPROVER_EMP_ID);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId equals to UPDATED_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.in=" + UPDATED_APPROVER_EMP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is not null
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.specified=true");
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is greater than or equal to DEFAULT_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.greaterThanOrEqual=" + DEFAULT_APPROVER_EMP_ID);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is greater than or equal to UPDATED_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.greaterThanOrEqual=" + UPDATED_APPROVER_EMP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is less than or equal to DEFAULT_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.lessThanOrEqual=" + DEFAULT_APPROVER_EMP_ID);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is less than or equal to SMALLER_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.lessThanOrEqual=" + SMALLER_APPROVER_EMP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is less than DEFAULT_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.lessThan=" + DEFAULT_APPROVER_EMP_ID);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is less than UPDATED_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.lessThan=" + UPDATED_APPROVER_EMP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByApproverEmpIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is greater than DEFAULT_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("approverEmpId.greaterThan=" + DEFAULT_APPROVER_EMP_ID);
//
//        // Get all the leaveApplicationApprovalList where approverEmpId is greater than SMALLER_APPROVER_EMP_ID
//        defaultLeaveApplicationApprovalShouldBeFound("approverEmpId.greaterThan=" + SMALLER_APPROVER_EMP_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveStatusIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus equals to DEFAULT_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("leaveStatus.equals=" + DEFAULT_LEAVE_STATUS);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus equals to UPDATED_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveStatus.equals=" + UPDATED_LEAVE_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveStatusIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus in DEFAULT_LEAVE_STATUS or UPDATED_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("leaveStatus.in=" + DEFAULT_LEAVE_STATUS + "," + UPDATED_LEAVE_STATUS);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus equals to UPDATED_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveStatus.in=" + UPDATED_LEAVE_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveStatusIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus is not null
//        defaultLeaveApplicationApprovalShouldBeFound("leaveStatus.specified=true");
//
//        // Get all the leaveApplicationApprovalList where leaveStatus is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveStatus.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveStatusContainsSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus contains DEFAULT_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("leaveStatus.contains=" + DEFAULT_LEAVE_STATUS);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus contains UPDATED_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveStatus.contains=" + UPDATED_LEAVE_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLeaveStatusNotContainsSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus does not contain DEFAULT_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("leaveStatus.doesNotContain=" + DEFAULT_LEAVE_STATUS);
//
//        // Get all the leaveApplicationApprovalList where leaveStatus does not contain UPDATED_LEAVE_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("leaveStatus.doesNotContain=" + UPDATED_LEAVE_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence equals to DEFAULT_SEQUENCE
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);
//
//        // Get all the leaveApplicationApprovalList where sequence equals to UPDATED_SEQUENCE
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);
//
//        // Get all the leaveApplicationApprovalList where sequence equals to UPDATED_SEQUENCE
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence is not null
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.specified=true");
//
//        // Get all the leaveApplicationApprovalList where sequence is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence is greater than or equal to DEFAULT_SEQUENCE
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.greaterThanOrEqual=" + DEFAULT_SEQUENCE);
//
//        // Get all the leaveApplicationApprovalList where sequence is greater than or equal to UPDATED_SEQUENCE
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.greaterThanOrEqual=" + UPDATED_SEQUENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence is less than or equal to DEFAULT_SEQUENCE
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.lessThanOrEqual=" + DEFAULT_SEQUENCE);
//
//        // Get all the leaveApplicationApprovalList where sequence is less than or equal to SMALLER_SEQUENCE
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.lessThanOrEqual=" + SMALLER_SEQUENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsLessThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence is less than DEFAULT_SEQUENCE
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);
//
//        // Get all the leaveApplicationApprovalList where sequence is less than UPDATED_SEQUENCE
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsBySequenceIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where sequence is greater than DEFAULT_SEQUENCE
//        defaultLeaveApplicationApprovalShouldNotBeFound("sequence.greaterThan=" + DEFAULT_SEQUENCE);
//
//        // Get all the leaveApplicationApprovalList where sequence is greater than SMALLER_SEQUENCE
//        defaultLeaveApplicationApprovalShouldBeFound("sequence.greaterThan=" + SMALLER_SEQUENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByStatusIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where status equals to DEFAULT_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("status.equals=" + DEFAULT_STATUS);
//
//        // Get all the leaveApplicationApprovalList where status equals to UPDATED_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("status.equals=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByStatusIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where status in DEFAULT_STATUS or UPDATED_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);
//
//        // Get all the leaveApplicationApprovalList where status equals to UPDATED_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("status.in=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByStatusIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where status is not null
//        defaultLeaveApplicationApprovalShouldBeFound("status.specified=true");
//
//        // Get all the leaveApplicationApprovalList where status is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("status.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByStatusContainsSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where status contains DEFAULT_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("status.contains=" + DEFAULT_STATUS);
//
//        // Get all the leaveApplicationApprovalList where status contains UPDATED_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("status.contains=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByStatusNotContainsSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where status does not contain DEFAULT_STATUS
//        defaultLeaveApplicationApprovalShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);
//
//        // Get all the leaveApplicationApprovalList where status does not contain UPDATED_STATUS
//        defaultLeaveApplicationApprovalShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId equals to DEFAULT_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);
//
//        // Get all the leaveApplicationApprovalList where companyId equals to UPDATED_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);
//
//        // Get all the leaveApplicationApprovalList where companyId equals to UPDATED_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId is not null
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.specified=true");
//
//        // Get all the leaveApplicationApprovalList where companyId is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId is greater than or equal to DEFAULT_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the leaveApplicationApprovalList where companyId is greater than or equal to UPDATED_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId is less than or equal to DEFAULT_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the leaveApplicationApprovalList where companyId is less than or equal to SMALLER_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId is less than DEFAULT_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the leaveApplicationApprovalList where companyId is less than UPDATED_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByCompanyIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where companyId is greater than DEFAULT_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the leaveApplicationApprovalList where companyId is greater than SMALLER_COMPANY_ID
//        defaultLeaveApplicationApprovalShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModified equals to DEFAULT_LAST_MODIFIED
//        defaultLeaveApplicationApprovalShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);
//
//        // Get all the leaveApplicationApprovalList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
//        defaultLeaveApplicationApprovalShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);
//
//        // Get all the leaveApplicationApprovalList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModified is not null
//        defaultLeaveApplicationApprovalShouldBeFound("lastModified.specified=true");
//
//        // Get all the leaveApplicationApprovalList where lastModified is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModified.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedByIsEqualToSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedByIsInShouldWork() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedByIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy is not null
//        defaultLeaveApplicationApprovalShouldBeFound("lastModifiedBy.specified=true");
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy is null
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModifiedBy.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedByContainsSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllLeaveApplicationApprovalsByLastModifiedByNotContainsSomething() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the leaveApplicationApprovalList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
//        defaultLeaveApplicationApprovalShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultLeaveApplicationApprovalShouldBeFound(String filter) throws Exception {
//        restLeaveApplicationApprovalMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplicationApproval.getId().intValue())))
//            .andExpect(jsonPath("$.[*].leaveApplicationId").value(hasItem(DEFAULT_LEAVE_APP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].approverEmpId").value(hasItem(DEFAULT_APPROVER_EMP_ID.intValue())))
//            .andExpect(jsonPath("$.[*].leaveStatus").value(hasItem(DEFAULT_LEAVE_STATUS)))
//            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.intValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
//
//        // Check, that the count call also returns 1
//        restLeaveApplicationApprovalMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultLeaveApplicationApprovalShouldNotBeFound(String filter) throws Exception {
//        restLeaveApplicationApprovalMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restLeaveApplicationApprovalMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingLeaveApplicationApproval() throws Exception {
//        // Get the leaveApplicationApproval
//        restLeaveApplicationApprovalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingLeaveApplicationApproval() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//
//        // Update the leaveApplicationApproval
//        LeaveApplicationApproval updatedLeaveApplicationApproval = leaveApplicationApprovalRepository
//            .findById(leaveApplicationApproval.getId())
//            .get();
//        // Disconnect from session so that the updates on updatedLeaveApplicationApproval are not directly saved in db
//        em.detach(updatedLeaveApplicationApproval);
//        updatedLeaveApplicationApproval
//            .leaveApplicationId(UPDATED_LEAVE_APP_ID)
//            .approverEmpId(UPDATED_APPROVER_EMP_ID)
//            .leaveStatus(UPDATED_LEAVE_STATUS)
//            .sequence(UPDATED_SEQUENCE)
//            .status(UPDATED_STATUS);
////            .companyId(UPDATED_COMPANY_ID)
////            .lastModified(UPDATED_LAST_MODIFIED)
////            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(updatedLeaveApplicationApproval);
//
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, leaveApplicationApprovalDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//        LeaveApplicationApproval testLeaveApplicationApproval = leaveApplicationApprovalList.get(leaveApplicationApprovalList.size() - 1);
//        assertThat(testLeaveApplicationApproval.getLeaveApplicationId()).isEqualTo(UPDATED_LEAVE_APP_ID);
//        assertThat(testLeaveApplicationApproval.getApproverEmpId()).isEqualTo(UPDATED_APPROVER_EMP_ID);
//        assertThat(testLeaveApplicationApproval.getLeaveStatus()).isEqualTo(UPDATED_LEAVE_STATUS);
//        assertThat(testLeaveApplicationApproval.getSequence()).isEqualTo(UPDATED_SEQUENCE);
//        assertThat(testLeaveApplicationApproval.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testLeaveApplicationApproval.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testLeaveApplicationApproval.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testLeaveApplicationApproval.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingLeaveApplicationApproval() throws Exception {
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//        leaveApplicationApproval.setId(count.incrementAndGet());
//
//        // Create the LeaveApplicationApproval
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(leaveApplicationApproval);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, leaveApplicationApprovalDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchLeaveApplicationApproval() throws Exception {
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//        leaveApplicationApproval.setId(count.incrementAndGet());
//
//        // Create the LeaveApplicationApproval
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(leaveApplicationApproval);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamLeaveApplicationApproval() throws Exception {
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//        leaveApplicationApproval.setId(count.incrementAndGet());
//
//        // Create the LeaveApplicationApproval
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(leaveApplicationApproval);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateLeaveApplicationApprovalWithPatch() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//
//        // Update the leaveApplicationApproval using partial update
//        LeaveApplicationApproval partialUpdatedLeaveApplicationApproval = new LeaveApplicationApproval();
//        partialUpdatedLeaveApplicationApproval.setId(leaveApplicationApproval.getId());
//
//        partialUpdatedLeaveApplicationApproval
//            .leaveStatus(UPDATED_LEAVE_STATUS)
//            .sequence(UPDATED_SEQUENCE)
//            .status(UPDATED_STATUS);
//         //   .companyId(UPDATED_COMPANY_ID);
//
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApplicationApproval.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApplicationApproval))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//        LeaveApplicationApproval testLeaveApplicationApproval = leaveApplicationApprovalList.get(leaveApplicationApprovalList.size() - 1);
//        assertThat(testLeaveApplicationApproval.getLeaveApplicationId()).isEqualTo(DEFAULT_LEAVE_APP_ID);
//        assertThat(testLeaveApplicationApproval.getApproverEmpId()).isEqualTo(DEFAULT_APPROVER_EMP_ID);
//        assertThat(testLeaveApplicationApproval.getLeaveStatus()).isEqualTo(UPDATED_LEAVE_STATUS);
//        assertThat(testLeaveApplicationApproval.getSequence()).isEqualTo(UPDATED_SEQUENCE);
//        assertThat(testLeaveApplicationApproval.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testLeaveApplicationApproval.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testLeaveApplicationApproval.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
//        assertThat(testLeaveApplicationApproval.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateLeaveApplicationApprovalWithPatch() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//
//        // Update the leaveApplicationApproval using partial update
//        LeaveApplicationApproval partialUpdatedLeaveApplicationApproval = new LeaveApplicationApproval();
//        partialUpdatedLeaveApplicationApproval.setId(leaveApplicationApproval.getId());
//
//        partialUpdatedLeaveApplicationApproval
//            .leaveApplicationId(UPDATED_LEAVE_APP_ID)
//            .approverEmpId(UPDATED_APPROVER_EMP_ID)
//            .leaveStatus(UPDATED_LEAVE_STATUS)
//            .sequence(UPDATED_SEQUENCE)
//            .status(UPDATED_STATUS);
////            .companyId(UPDATED_COMPANY_ID)
////            .lastModified(UPDATED_LAST_MODIFIED)
////            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
//
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApplicationApproval.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApplicationApproval))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//        LeaveApplicationApproval testLeaveApplicationApproval = leaveApplicationApprovalList.get(leaveApplicationApprovalList.size() - 1);
//        assertThat(testLeaveApplicationApproval.getLeaveApplicationId()).isEqualTo(UPDATED_LEAVE_APP_ID);
//        assertThat(testLeaveApplicationApproval.getApproverEmpId()).isEqualTo(UPDATED_APPROVER_EMP_ID);
//        assertThat(testLeaveApplicationApproval.getLeaveStatus()).isEqualTo(UPDATED_LEAVE_STATUS);
//        assertThat(testLeaveApplicationApproval.getSequence()).isEqualTo(UPDATED_SEQUENCE);
//        assertThat(testLeaveApplicationApproval.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testLeaveApplicationApproval.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testLeaveApplicationApproval.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testLeaveApplicationApproval.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingLeaveApplicationApproval() throws Exception {
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//        leaveApplicationApproval.setId(count.incrementAndGet());
//
//        // Create the LeaveApplicationApproval
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(leaveApplicationApproval);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, leaveApplicationApprovalDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchLeaveApplicationApproval() throws Exception {
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//        leaveApplicationApproval.setId(count.incrementAndGet());
//
//        // Create the LeaveApplicationApproval
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(leaveApplicationApproval);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamLeaveApplicationApproval() throws Exception {
//        int databaseSizeBeforeUpdate = leaveApplicationApprovalRepository.findAll().size();
//        leaveApplicationApproval.setId(count.incrementAndGet());
//
//        // Create the LeaveApplicationApproval
//        LeaveApplicationApprovalDTO leaveApplicationApprovalDTO = leaveApplicationApprovalMapper.toDto(leaveApplicationApproval);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLeaveApplicationApprovalMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationApprovalDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the LeaveApplicationApproval in the database
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteLeaveApplicationApproval() throws Exception {
//        // Initialize the database
//        leaveApplicationApprovalRepository.saveAndFlush(leaveApplicationApproval);
//
//        int databaseSizeBeforeDelete = leaveApplicationApprovalRepository.findAll().size();
//
//        // Delete the leaveApplicationApproval
//        restLeaveApplicationApprovalMockMvc
//            .perform(delete(ENTITY_API_URL_ID, leaveApplicationApproval.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<LeaveApplicationApproval> leaveApplicationApprovalList = leaveApplicationApprovalRepository.findAll();
//        assertThat(leaveApplicationApprovalList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
//
