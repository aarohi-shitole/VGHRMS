package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.LeaveType;
import com.techvg.hrms.repository.LeaveTypeRepository;
import com.techvg.hrms.service.criteria.LeaveTypeCriteria;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import com.techvg.hrms.service.mapper.LeaveTypeMapper;
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
 * Integration tests for the {@link LeaveTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeaveTypeResourceIT {

    private static final String DEFAULT_LEAVE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_NO_OF_DAYS = 1L;
    private static final Long UPDATED_NO_OF_DAYS = 2L;
    private static final Long SMALLER_NO_OF_DAYS = 1L - 1L;

    private static final Boolean DEFAULT_HAS_CARRY_FORWARD = false;
    private static final Boolean UPDATED_HAS_CARRY_FORWARD = true;

    private static final Boolean DEFAULT_HAS_EARNED = false;
    private static final Boolean UPDATED_HAS_EARNED = true;

    private static final Boolean DEFAULT_HAS_CUSTOM_POLICY = false;
    private static final Boolean UPDATED_HAS_CUSTOM_POLICY = true;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leave-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveTypeMapper leaveTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveTypeMockMvc;

    private LeaveType leaveType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveType createEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .leaveType(DEFAULT_LEAVE_TYPE)
            .noOfDays(DEFAULT_NO_OF_DAYS)
            .hasCarryForward(DEFAULT_HAS_CARRY_FORWARD)
            .hasEarned(DEFAULT_HAS_EARNED)
            .hasCustomPolicy(DEFAULT_HAS_CUSTOM_POLICY)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return leaveType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveType createUpdatedEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .hasCarryForward(UPDATED_HAS_CARRY_FORWARD)
            .hasEarned(UPDATED_HAS_EARNED)
            .hasCustomPolicy(UPDATED_HAS_CUSTOM_POLICY)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return leaveType;
    }

    @BeforeEach
    public void initTest() {
        leaveType = createEntity(em);
    }

    @Test
    @Transactional
    void createLeaveType() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();
        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);
        restLeaveTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(DEFAULT_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveType.getHasCarryForward()).isEqualTo(DEFAULT_HAS_CARRY_FORWARD);
        assertThat(testLeaveType.getHasEarned()).isEqualTo(DEFAULT_HAS_EARNED);
        assertThat(testLeaveType.getHasCustomPolicy()).isEqualTo(DEFAULT_HAS_CUSTOM_POLICY);
        assertThat(testLeaveType.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testLeaveType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createLeaveTypeWithExistingId() throws Exception {
        // Create the LeaveType with an existing ID
        leaveType.setId(1L);
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].hasCarryForward").value(hasItem(DEFAULT_HAS_CARRY_FORWARD.booleanValue())))
            .andExpect(jsonPath("$.[*].hasEarned").value(hasItem(DEFAULT_HAS_EARNED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasCustomPolicy").value(hasItem(DEFAULT_HAS_CUSTOM_POLICY.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get the leaveType
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, leaveType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveType.getId().intValue()))
            .andExpect(jsonPath("$.leaveType").value(DEFAULT_LEAVE_TYPE))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS.intValue()))
            .andExpect(jsonPath("$.hasCarryForward").value(DEFAULT_HAS_CARRY_FORWARD.booleanValue()))
            .andExpect(jsonPath("$.hasEarned").value(DEFAULT_HAS_EARNED.booleanValue()))
            .andExpect(jsonPath("$.hasCustomPolicy").value(DEFAULT_HAS_CUSTOM_POLICY.booleanValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getLeaveTypesByIdFiltering() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        Long id = leaveType.getId();

        defaultLeaveTypeShouldBeFound("id.equals=" + id);
        defaultLeaveTypeShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType equals to DEFAULT_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.equals=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.equals=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType in DEFAULT_LEAVE_TYPE or UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.in=" + DEFAULT_LEAVE_TYPE + "," + UPDATED_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType equals to UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.in=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType is not null
        defaultLeaveTypeShouldBeFound("leaveType.specified=true");

        // Get all the leaveTypeList where leaveType is null
        defaultLeaveTypeShouldNotBeFound("leaveType.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType contains DEFAULT_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.contains=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType contains UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.contains=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLeaveTypeNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where leaveType does not contain DEFAULT_LEAVE_TYPE
        defaultLeaveTypeShouldNotBeFound("leaveType.doesNotContain=" + DEFAULT_LEAVE_TYPE);

        // Get all the leaveTypeList where leaveType does not contain UPDATED_LEAVE_TYPE
        defaultLeaveTypeShouldBeFound("leaveType.doesNotContain=" + UPDATED_LEAVE_TYPE);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays equals to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.equals=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.equals=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays in DEFAULT_NO_OF_DAYS or UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.in=" + DEFAULT_NO_OF_DAYS + "," + UPDATED_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays equals to UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.in=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays is not null
        defaultLeaveTypeShouldBeFound("noOfDays.specified=true");

        // Get all the leaveTypeList where noOfDays is null
        defaultLeaveTypeShouldNotBeFound("noOfDays.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays is greater than or equal to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.greaterThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays is greater than or equal to UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.greaterThanOrEqual=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays is less than or equal to DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.lessThanOrEqual=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays is less than or equal to SMALLER_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.lessThanOrEqual=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays is less than DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.lessThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays is less than UPDATED_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.lessThan=" + UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByNoOfDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where noOfDays is greater than DEFAULT_NO_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("noOfDays.greaterThan=" + DEFAULT_NO_OF_DAYS);

        // Get all the leaveTypeList where noOfDays is greater than SMALLER_NO_OF_DAYS
        defaultLeaveTypeShouldBeFound("noOfDays.greaterThan=" + SMALLER_NO_OF_DAYS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasCarryForwardIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasCarryForward equals to DEFAULT_HAS_CARRY_FORWARD
        defaultLeaveTypeShouldBeFound("hasCarryForward.equals=" + DEFAULT_HAS_CARRY_FORWARD);

        // Get all the leaveTypeList where hasCarryForward equals to UPDATED_HAS_CARRY_FORWARD
        defaultLeaveTypeShouldNotBeFound("hasCarryForward.equals=" + UPDATED_HAS_CARRY_FORWARD);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasCarryForwardIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasCarryForward in DEFAULT_HAS_CARRY_FORWARD or UPDATED_HAS_CARRY_FORWARD
        defaultLeaveTypeShouldBeFound("hasCarryForward.in=" + DEFAULT_HAS_CARRY_FORWARD + "," + UPDATED_HAS_CARRY_FORWARD);

        // Get all the leaveTypeList where hasCarryForward equals to UPDATED_HAS_CARRY_FORWARD
        defaultLeaveTypeShouldNotBeFound("hasCarryForward.in=" + UPDATED_HAS_CARRY_FORWARD);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasCarryForwardIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasCarryForward is not null
        defaultLeaveTypeShouldBeFound("hasCarryForward.specified=true");

        // Get all the leaveTypeList where hasCarryForward is null
        defaultLeaveTypeShouldNotBeFound("hasCarryForward.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasEarnedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasEarned equals to DEFAULT_HAS_EARNED
        defaultLeaveTypeShouldBeFound("hasEarned.equals=" + DEFAULT_HAS_EARNED);

        // Get all the leaveTypeList where hasEarned equals to UPDATED_HAS_EARNED
        defaultLeaveTypeShouldNotBeFound("hasEarned.equals=" + UPDATED_HAS_EARNED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasEarnedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasEarned in DEFAULT_HAS_EARNED or UPDATED_HAS_EARNED
        defaultLeaveTypeShouldBeFound("hasEarned.in=" + DEFAULT_HAS_EARNED + "," + UPDATED_HAS_EARNED);

        // Get all the leaveTypeList where hasEarned equals to UPDATED_HAS_EARNED
        defaultLeaveTypeShouldNotBeFound("hasEarned.in=" + UPDATED_HAS_EARNED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasEarnedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasEarned is not null
        defaultLeaveTypeShouldBeFound("hasEarned.specified=true");

        // Get all the leaveTypeList where hasEarned is null
        defaultLeaveTypeShouldNotBeFound("hasEarned.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasCustomPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasCustomPolicy equals to DEFAULT_HAS_CUSTOM_POLICY
        defaultLeaveTypeShouldBeFound("hasCustomPolicy.equals=" + DEFAULT_HAS_CUSTOM_POLICY);

        // Get all the leaveTypeList where hasCustomPolicy equals to UPDATED_HAS_CUSTOM_POLICY
        defaultLeaveTypeShouldNotBeFound("hasCustomPolicy.equals=" + UPDATED_HAS_CUSTOM_POLICY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasCustomPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasCustomPolicy in DEFAULT_HAS_CUSTOM_POLICY or UPDATED_HAS_CUSTOM_POLICY
        defaultLeaveTypeShouldBeFound("hasCustomPolicy.in=" + DEFAULT_HAS_CUSTOM_POLICY + "," + UPDATED_HAS_CUSTOM_POLICY);

        // Get all the leaveTypeList where hasCustomPolicy equals to UPDATED_HAS_CUSTOM_POLICY
        defaultLeaveTypeShouldNotBeFound("hasCustomPolicy.in=" + UPDATED_HAS_CUSTOM_POLICY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByHasCustomPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where hasCustomPolicy is not null
        defaultLeaveTypeShouldBeFound("hasCustomPolicy.specified=true");

        // Get all the leaveTypeList where hasCustomPolicy is null
        defaultLeaveTypeShouldNotBeFound("hasCustomPolicy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId equals to DEFAULT_COMPANY_ID
        defaultLeaveTypeShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the leaveTypeList where companyId equals to UPDATED_COMPANY_ID
        defaultLeaveTypeShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultLeaveTypeShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the leaveTypeList where companyId equals to UPDATED_COMPANY_ID
        defaultLeaveTypeShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId is not null
        defaultLeaveTypeShouldBeFound("companyId.specified=true");

        // Get all the leaveTypeList where companyId is null
        defaultLeaveTypeShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultLeaveTypeShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the leaveTypeList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultLeaveTypeShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultLeaveTypeShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the leaveTypeList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultLeaveTypeShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId is less than DEFAULT_COMPANY_ID
        defaultLeaveTypeShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the leaveTypeList where companyId is less than UPDATED_COMPANY_ID
        defaultLeaveTypeShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where companyId is greater than DEFAULT_COMPANY_ID
        defaultLeaveTypeShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the leaveTypeList where companyId is greater than SMALLER_COMPANY_ID
        defaultLeaveTypeShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status equals to DEFAULT_STATUS
        defaultLeaveTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveTypeList where status equals to UPDATED_STATUS
        defaultLeaveTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveTypeList where status equals to UPDATED_STATUS
        defaultLeaveTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status is not null
        defaultLeaveTypeShouldBeFound("status.specified=true");

        // Get all the leaveTypeList where status is null
        defaultLeaveTypeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status contains DEFAULT_STATUS
        defaultLeaveTypeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the leaveTypeList where status contains UPDATED_STATUS
        defaultLeaveTypeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where status does not contain DEFAULT_STATUS
        defaultLeaveTypeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the leaveTypeList where status does not contain UPDATED_STATUS
        defaultLeaveTypeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLeaveTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the leaveTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLeaveTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the leaveTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeaveTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModified is not null
        defaultLeaveTypeShouldBeFound("lastModified.specified=true");

        // Get all the leaveTypeList where lastModified is null
        defaultLeaveTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy is not null
        defaultLeaveTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the leaveTypeList where lastModifiedBy is null
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeaveTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLeaveTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leaveTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLeaveTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveTypeShouldBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveType").value(hasItem(DEFAULT_LEAVE_TYPE)))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].hasCarryForward").value(hasItem(DEFAULT_HAS_CARRY_FORWARD.booleanValue())))
            .andExpect(jsonPath("$.[*].hasEarned").value(hasItem(DEFAULT_HAS_EARNED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasCustomPolicy").value(hasItem(DEFAULT_HAS_CUSTOM_POLICY.booleanValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveTypeShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeaveType() throws Exception {
        // Get the leaveType
        restLeaveTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType
        LeaveType updatedLeaveType = leaveTypeRepository.findById(leaveType.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveType are not directly saved in db
        em.detach(updatedLeaveType);
        updatedLeaveType
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .hasCarryForward(UPDATED_HAS_CARRY_FORWARD)
            .hasEarned(UPDATED_HAS_EARNED)
            .hasCustomPolicy(UPDATED_HAS_CUSTOM_POLICY)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(updatedLeaveType);

        restLeaveTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveType.getHasCarryForward()).isEqualTo(UPDATED_HAS_CARRY_FORWARD);
        assertThat(testLeaveType.getHasEarned()).isEqualTo(UPDATED_HAS_EARNED);
        assertThat(testLeaveType.getHasCustomPolicy()).isEqualTo(UPDATED_HAS_CUSTOM_POLICY);
        assertThat(testLeaveType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeaveType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leaveTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeaveTypeWithPatch() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType using partial update
        LeaveType partialUpdatedLeaveType = new LeaveType();
        partialUpdatedLeaveType.setId(leaveType.getId());

        partialUpdatedLeaveType
            .leaveType(UPDATED_LEAVE_TYPE)
            .hasEarned(UPDATED_HAS_EARNED)
            .hasCustomPolicy(UPDATED_HAS_CUSTOM_POLICY)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveType))
            )
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testLeaveType.getHasCarryForward()).isEqualTo(DEFAULT_HAS_CARRY_FORWARD);
        assertThat(testLeaveType.getHasEarned()).isEqualTo(UPDATED_HAS_EARNED);
        assertThat(testLeaveType.getHasCustomPolicy()).isEqualTo(UPDATED_HAS_CUSTOM_POLICY);
        assertThat(testLeaveType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeaveType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateLeaveTypeWithPatch() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType using partial update
        LeaveType partialUpdatedLeaveType = new LeaveType();
        partialUpdatedLeaveType.setId(leaveType.getId());

        partialUpdatedLeaveType
            .leaveType(UPDATED_LEAVE_TYPE)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .hasCarryForward(UPDATED_HAS_CARRY_FORWARD)
            .hasEarned(UPDATED_HAS_EARNED)
            .hasCustomPolicy(UPDATED_HAS_CUSTOM_POLICY)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeaveType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeaveType))
            )
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getLeaveType()).isEqualTo(UPDATED_LEAVE_TYPE);
        assertThat(testLeaveType.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testLeaveType.getHasCarryForward()).isEqualTo(UPDATED_HAS_CARRY_FORWARD);
        assertThat(testLeaveType.getHasEarned()).isEqualTo(UPDATED_HAS_EARNED);
        assertThat(testLeaveType.getHasCustomPolicy()).isEqualTo(UPDATED_HAS_CUSTOM_POLICY);
        assertThat(testLeaveType.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeaveType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeaveType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leaveTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();
        leaveType.setId(count.incrementAndGet());

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeDelete = leaveTypeRepository.findAll().size();

        // Delete the leaveType
        restLeaveTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, leaveType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
