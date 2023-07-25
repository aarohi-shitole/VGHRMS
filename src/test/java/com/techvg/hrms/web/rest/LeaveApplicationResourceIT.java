package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.LeaveApplication;
import com.techvg.hrms.repository.LeaveApplicationRepository;
import com.techvg.hrms.service.criteria.LeaveApplicationCriteria;
import com.techvg.hrms.service.dto.LeaveApplicationDTO;
import com.techvg.hrms.service.mapper.LeaveApplicationMapper;
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
 * Integration tests for the {@link LeaveApplicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveApplicationResourceIT {

    private static final String DEFAULT_LEAVE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_NO_OF_DAYS = 1L;
    private static final Long UPDATED_NO_OF_DAYS = 2L;
    private static final Long SMALLER_NO_OF_DAYS = 1L - 1L;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_FORM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FORM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LEAVE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYE_ID = 1L;
    private static final Long UPDATED_EMPLOYE_ID = 2L;
    private static final Long SMALLER_EMPLOYE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leave-applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private LeaveApplicationMapper leaveApplicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveApplicationMockMvc;

    private LeaveApplication leaveApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApplication createEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .leaveType(DEFAULT_LEAVE_TYPE)
            .noOfDays(DEFAULT_NO_OF_DAYS)
            .reason(DEFAULT_REASON)
            .formDate(DEFAULT_FORM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .leaveStatus(DEFAULT_LEAVE_STATUS)
            .status(DEFAULT_STATUS)
            .employeId(DEFAULT_EMPLOYE_ID)//            .companyId(DEFAULT_COMPANY_ID)
        //            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return leaveApplication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApplication createUpdatedEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .reason(UPDATED_REASON)
            .formDate(UPDATED_FORM_DATE)
            .toDate(UPDATED_TO_DATE)
            .leaveStatus(UPDATED_LEAVE_STATUS)
            .status(UPDATED_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return leaveApplication;
    }

    @BeforeEach
    public void initTest() {
        leaveApplication = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveApplication() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();
        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);
        restLeaveApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getLeaveType()).isEqualTo(DEFAULT_LEAVE_TYPE);
        assertThat(testLeaveApplication.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testLeaveApplication.getFormDate()).isEqualTo(DEFAULT_FORM_DATE);
        assertThat(testLeaveApplication.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testLeaveApplication.getLeaveStatus()).isEqualTo(DEFAULT_LEAVE_STATUS);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveApplication.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testLeaveApplication.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testLeaveApplication.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeaveApplication.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createLeaveApplicationWithExistingId() throws Exception {
        // Create the LeaveApplication with an existing ID
        leaveApplication.setId(1L);
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeaveApplications() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList
        restLeaveApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].formDate").value(hasItem(DEFAULT_FORM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].leaveStatus").value(hasItem(DEFAULT_LEAVE_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get the leaveApplication
        restLeaveApplicationMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveApplication.getId().intValue()))
            .andExpect(jsonPath("$.leaveType").value(DEFAULT_LEAVE_TYPE))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.formDate").value(DEFAULT_FORM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.leaveStatus").value(DEFAULT_LEAVE_STATUS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getLeaveApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        Long id = leaveApplication.getId();

        defaultLeaveApplicationShouldBeFound("id.equals=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveType equals to DEFAULT_LEAVE_TYPE
        defaultLeaveApplicationShouldBeFound("leaveType.equals=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveApplicationList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveApplicationShouldNotBeFound("leaveType.equals=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveType in DEFAULT_LEAVE_TYPE or UPDATED_LEAVE_TYPE
        defaultLeaveApplicationShouldBeFound("leaveType.in=" + DEFAULT_LEAVE_TYPE + "," + UPDATED_LEAVE_TYPE);

        // Get all the leaveApplicationList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveApplicationShouldNotBeFound("leaveType.in=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveType is not null
        defaultLeaveApplicationShouldBeFound("leaveType.specified=true");

        // Get all the leaveApplicationList where leaveType is null
        defaultLeaveApplicationShouldNotBeFound("leaveType.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveTypeContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveType contains DEFAULT_LEAVE_TYPE
        defaultLeaveApplicationShouldBeFound("leaveType.contains=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveApplicationList where leaveType contains UPDATED_LEAVE_TYPE
        defaultLeaveApplicationShouldNotBeFound("leaveType.contains=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveTypeNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveType does not contain DEFAULT_LEAVE_TYPE
        defaultLeaveApplicationShouldNotBeFound("leaveType.doesNotContain=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveApplicationList where leaveType does not contain UPDATED_LEAVE_TYPE
        defaultLeaveApplicationShouldBeFound("leaveType.doesNotContain=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays equals to DEFAULT_NO_OF_DAYS
        defaultLeaveApplicationShouldBeFound("noOfDays.equals=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveApplicationList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("noOfDays.equals=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays in DEFAULT_NO_OF_DAYS or UPDATED_NO_OF_DAYS
        defaultLeaveApplicationShouldBeFound("noOfDays.in=" + DEFAULT_NO_OF_DAYS + "," + UPDATED_NO_OF_DAYS);

        // Get all the leaveApplicationList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("noOfDays.in=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays is not null
        defaultLeaveApplicationShouldBeFound("noOfDays.specified=true");

        // Get all the leaveApplicationList where noOfDays is null
        defaultLeaveApplicationShouldNotBeFound("noOfDays.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays is greater than or equal to DEFAULT_NO_OF_DAYS
        defaultLeaveApplicationShouldBeFound("noOfDays.greaterThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveApplicationList where noOfDays is greater than or equal to UPDATED_NO_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("noOfDays.greaterThanOrEqual=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays is less than or equal to DEFAULT_NO_OF_DAYS
        defaultLeaveApplicationShouldBeFound("noOfDays.lessThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveApplicationList where noOfDays is less than or equal to SMALLER_NO_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("noOfDays.lessThanOrEqual=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays is less than DEFAULT_NO_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("noOfDays.lessThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveApplicationList where noOfDays is less than UPDATED_NO_OF_DAYS
        defaultLeaveApplicationShouldBeFound("noOfDays.lessThan=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByNoOfDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where noOfDays is greater than DEFAULT_NO_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("noOfDays.greaterThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveApplicationList where noOfDays is greater than SMALLER_NO_OF_DAYS
        defaultLeaveApplicationShouldBeFound("noOfDays.greaterThan=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason equals to DEFAULT_REASON
        defaultLeaveApplicationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason equals to UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultLeaveApplicationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the leaveApplicationList where reason equals to UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason is not null
        defaultLeaveApplicationShouldBeFound("reason.specified=true");

        // Get all the leaveApplicationList where reason is null
        defaultLeaveApplicationShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason contains DEFAULT_REASON
        defaultLeaveApplicationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason contains UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason does not contain DEFAULT_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason does not contain UPDATED_REASON
        defaultLeaveApplicationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByFormDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where formDate equals to DEFAULT_FORM_DATE
        defaultLeaveApplicationShouldBeFound("formDate.equals=" + DEFAULT_FORM_DATE);

        // Get all the leaveApplicationList where formDate equals to UPDATED_FORM_DATE
        defaultLeaveApplicationShouldNotBeFound("formDate.equals=" + UPDATED_FORM_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByFormDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where formDate in DEFAULT_FORM_DATE or UPDATED_FORM_DATE
        defaultLeaveApplicationShouldBeFound("formDate.in=" + DEFAULT_FORM_DATE + "," + UPDATED_FORM_DATE);

        // Get all the leaveApplicationList where formDate equals to UPDATED_FORM_DATE
        defaultLeaveApplicationShouldNotBeFound("formDate.in=" + UPDATED_FORM_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByFormDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where formDate is not null
        defaultLeaveApplicationShouldBeFound("formDate.specified=true");

        // Get all the leaveApplicationList where formDate is null
        defaultLeaveApplicationShouldNotBeFound("formDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate equals to DEFAULT_TO_DATE
        defaultLeaveApplicationShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the leaveApplicationList where toDate equals to UPDATED_TO_DATE
        defaultLeaveApplicationShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultLeaveApplicationShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the leaveApplicationList where toDate equals to UPDATED_TO_DATE
        defaultLeaveApplicationShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate is not null
        defaultLeaveApplicationShouldBeFound("toDate.specified=true");

        // Get all the leaveApplicationList where toDate is null
        defaultLeaveApplicationShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveStatus equals to DEFAULT_LEAVE_STATUS
        defaultLeaveApplicationShouldBeFound("leaveStatus.equals=" + DEFAULT_LEAVE_STATUS);

        // Get all the leaveApplicationList where leaveStatus equals to UPDATED_LEAVE_STATUS
        defaultLeaveApplicationShouldNotBeFound("leaveStatus.equals=" + UPDATED_LEAVE_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveStatus in DEFAULT_LEAVE_STATUS or UPDATED_LEAVE_STATUS
        defaultLeaveApplicationShouldBeFound("leaveStatus.in=" + DEFAULT_LEAVE_STATUS + "," + UPDATED_LEAVE_STATUS);

        // Get all the leaveApplicationList where leaveStatus equals to UPDATED_LEAVE_STATUS
        defaultLeaveApplicationShouldNotBeFound("leaveStatus.in=" + UPDATED_LEAVE_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveStatus is not null
        defaultLeaveApplicationShouldBeFound("leaveStatus.specified=true");

        // Get all the leaveApplicationList where leaveStatus is null
        defaultLeaveApplicationShouldNotBeFound("leaveStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveStatusContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveStatus contains DEFAULT_LEAVE_STATUS
        defaultLeaveApplicationShouldBeFound("leaveStatus.contains=" + DEFAULT_LEAVE_STATUS);

        // Get all the leaveApplicationList where leaveStatus contains UPDATED_LEAVE_STATUS
        defaultLeaveApplicationShouldNotBeFound("leaveStatus.contains=" + UPDATED_LEAVE_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLeaveStatusNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where leaveStatus does not contain DEFAULT_LEAVE_STATUS
        defaultLeaveApplicationShouldNotBeFound("leaveStatus.doesNotContain=" + DEFAULT_LEAVE_STATUS);

        // Get all the leaveApplicationList where leaveStatus does not contain UPDATED_LEAVE_STATUS
        defaultLeaveApplicationShouldBeFound("leaveStatus.doesNotContain=" + UPDATED_LEAVE_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status equals to DEFAULT_STATUS
        defaultLeaveApplicationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status is not null
        defaultLeaveApplicationShouldBeFound("status.specified=true");

        // Get all the leaveApplicationList where status is null
        defaultLeaveApplicationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status contains DEFAULT_STATUS
        defaultLeaveApplicationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status contains UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status does not contain DEFAULT_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status does not contain UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId equals to DEFAULT_EMPLOYE_ID
        defaultLeaveApplicationShouldBeFound("employeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the leaveApplicationList where employeId equals to UPDATED_EMPLOYE_ID
        defaultLeaveApplicationShouldNotBeFound("employeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultLeaveApplicationShouldBeFound("employeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the leaveApplicationList where employeId equals to UPDATED_EMPLOYE_ID
        defaultLeaveApplicationShouldNotBeFound("employeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId is not null
        defaultLeaveApplicationShouldBeFound("employeId.specified=true");

        // Get all the leaveApplicationList where employeId is null
        defaultLeaveApplicationShouldNotBeFound("employeId.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultLeaveApplicationShouldBeFound("employeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the leaveApplicationList where employeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultLeaveApplicationShouldNotBeFound("employeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultLeaveApplicationShouldBeFound("employeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the leaveApplicationList where employeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultLeaveApplicationShouldNotBeFound("employeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId is less than DEFAULT_EMPLOYE_ID
        defaultLeaveApplicationShouldNotBeFound("employeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the leaveApplicationList where employeId is less than UPDATED_EMPLOYE_ID
        defaultLeaveApplicationShouldBeFound("employeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByEmployeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeId is greater than DEFAULT_EMPLOYE_ID
        defaultLeaveApplicationShouldNotBeFound("employeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the leaveApplicationList where employeId is greater than SMALLER_EMPLOYE_ID
        defaultLeaveApplicationShouldBeFound("employeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId equals to DEFAULT_COMPANY_ID
        defaultLeaveApplicationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the leaveApplicationList where companyId equals to UPDATED_COMPANY_ID
        defaultLeaveApplicationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultLeaveApplicationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the leaveApplicationList where companyId equals to UPDATED_COMPANY_ID
        defaultLeaveApplicationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId is not null
        defaultLeaveApplicationShouldBeFound("companyId.specified=true");

        // Get all the leaveApplicationList where companyId is null
        defaultLeaveApplicationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultLeaveApplicationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the leaveApplicationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultLeaveApplicationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultLeaveApplicationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the leaveApplicationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultLeaveApplicationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId is less than DEFAULT_COMPANY_ID
        defaultLeaveApplicationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the leaveApplicationList where companyId is less than UPDATED_COMPANY_ID
        defaultLeaveApplicationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultLeaveApplicationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the leaveApplicationList where companyId is greater than SMALLER_COMPANY_ID
        defaultLeaveApplicationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLeaveApplicationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the leaveApplicationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveApplicationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLeaveApplicationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the leaveApplicationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveApplicationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModified is not null
        defaultLeaveApplicationShouldBeFound("lastModified.specified=true");

        // Get all the leaveApplicationList where lastModified is null
        defaultLeaveApplicationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveApplicationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the leaveApplicationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModifiedBy is not null
        defaultLeaveApplicationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the leaveApplicationList where lastModifiedBy is null
        defaultLeaveApplicationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveApplicationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveApplicationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveApplicationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLeaveApplicationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveApplicationShouldBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].formDate").value(hasItem(DEFAULT_FORM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].leaveStatus").value(hasItem(DEFAULT_LEAVE_STATUS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restLeaveApplicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveApplicationShouldNotBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveApplicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveApplication() throws Exception {
        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Update the leaveApplication
        LeaveApplication updatedLeaveApplication = leaveApplicationRepository.findById(leaveApplication.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveApplication are not directly saved in db
        em.detach(updatedLeaveApplication);
        updatedLeaveApplication
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .reason(UPDATED_REASON)
            .formDate(UPDATED_FORM_DATE)
            .toDate(UPDATED_TO_DATE)
            .leaveStatus(UPDATED_LEAVE_STATUS)
            .status(UPDATED_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(updatedLeaveApplication);

        restLeaveApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveApplicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveApplication.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testLeaveApplication.getFormDate()).isEqualTo(UPDATED_FORM_DATE);
        assertThat(testLeaveApplication.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testLeaveApplication.getLeaveStatus()).isEqualTo(UPDATED_LEAVE_STATUS);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveApplication.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testLeaveApplication.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeaveApplication.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveApplication.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();
        leaveApplication.setId(count.incrementAndGet());

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveApplicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();
        leaveApplication.setId(count.incrementAndGet());

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();
        leaveApplication.setId(count.incrementAndGet());

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveApplicationWithPatch() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Update the leaveApplication using partial update
        LeaveApplication partialUpdatedLeaveApplication = new LeaveApplication();
        partialUpdatedLeaveApplication.setId(leaveApplication.getId());

        partialUpdatedLeaveApplication
            .leaveType(UPDATED_LEAVE_TYPE)
            .reason(UPDATED_REASON)
            .formDate(UPDATED_FORM_DATE)
            .leaveStatus(UPDATED_LEAVE_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restLeaveApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApplication))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveApplication.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testLeaveApplication.getFormDate()).isEqualTo(DEFAULT_FORM_DATE);
        assertThat(testLeaveApplication.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testLeaveApplication.getLeaveStatus()).isEqualTo(DEFAULT_LEAVE_STATUS);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveApplication.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testLeaveApplication.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeaveApplication.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeaveApplication.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateLeaveApplicationWithPatch() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Update the leaveApplication using partial update
        LeaveApplication partialUpdatedLeaveApplication = new LeaveApplication();
        partialUpdatedLeaveApplication.setId(leaveApplication.getId());

        partialUpdatedLeaveApplication
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .reason(UPDATED_REASON)
            .formDate(UPDATED_FORM_DATE)
            .toDate(UPDATED_TO_DATE)
            .leaveStatus(UPDATED_LEAVE_STATUS)
            .status(UPDATED_STATUS)
            .employeId(UPDATED_EMPLOYE_ID)//            .companyId(UPDATED_COMPANY_ID)
        //            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restLeaveApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveApplication))
            )
            .andExpect(status().isOk());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveApplication.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testLeaveApplication.getFormDate()).isEqualTo(UPDATED_FORM_DATE);
        assertThat(testLeaveApplication.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testLeaveApplication.getLeaveStatus()).isEqualTo(UPDATED_LEAVE_STATUS);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveApplication.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testLeaveApplication.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeaveApplication.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveApplication.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();
        leaveApplication.setId(count.incrementAndGet());

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveApplicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();
        leaveApplication.setId(count.incrementAndGet());

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();
        leaveApplication.setId(count.incrementAndGet());

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        int databaseSizeBeforeDelete = leaveApplicationRepository.findAll().size();

        // Delete the leaveApplication
        restLeaveApplicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveApplication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
