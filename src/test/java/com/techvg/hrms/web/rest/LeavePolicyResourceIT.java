package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.EmploymentType;
import com.techvg.hrms.domain.LeavePolicy;
import com.techvg.hrms.domain.LeaveType;
import com.techvg.hrms.repository.LeavePolicyRepository;
import com.techvg.hrms.service.LeavePolicyService;
import com.techvg.hrms.service.criteria.LeavePolicyCriteria;
import com.techvg.hrms.service.dto.LeavePolicyDTO;
import com.techvg.hrms.service.mapper.LeavePolicyMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LeavePolicyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LeavePolicyResourceIT {

    private static final Boolean DEFAULT_IS_CARRY_FORWORD = false;
    private static final Boolean UPDATED_IS_CARRY_FORWORD = true;

    private static final Long DEFAULT_GENDER_LEAVE = 1L;
    private static final Long UPDATED_GENDER_LEAVE = 2L;
    private static final Long SMALLER_GENDER_LEAVE = 1L - 1L;

    private static final Long DEFAULT_TOTAL_LEAVE = 1L;
    private static final Long UPDATED_TOTAL_LEAVE = 2L;
    private static final Long SMALLER_TOTAL_LEAVE = 1L - 1L;

    private static final Long DEFAULT_MAX_LEAVE = 1L;
    private static final Long UPDATED_MAX_LEAVE = 2L;
    private static final Long SMALLER_MAX_LEAVE = 1L - 1L;

    private static final Boolean DEFAULT_HASPRO_RATA_LEAVE = false;
    private static final Boolean UPDATED_HASPRO_RATA_LEAVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REF_TABLE = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TABLE_ID = 1L;
    private static final Long UPDATED_REF_TABLE_ID = 2L;
    private static final Long SMALLER_REF_TABLE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/leave-policies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeavePolicyRepository leavePolicyRepository;

    @Mock
    private LeavePolicyRepository leavePolicyRepositoryMock;

    @Autowired
    private LeavePolicyMapper leavePolicyMapper;

    @Mock
    private LeavePolicyService leavePolicyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeavePolicyMockMvc;

    private LeavePolicy leavePolicy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeavePolicy createEntity(EntityManager em) {
        LeavePolicy leavePolicy = new LeavePolicy()
            .isCarryForword(DEFAULT_IS_CARRY_FORWORD)
            .genderLeave(DEFAULT_GENDER_LEAVE)
            .totalLeave(DEFAULT_TOTAL_LEAVE)
            .maxLeave(DEFAULT_MAX_LEAVE)
            .hasproRataLeave(DEFAULT_HASPRO_RATA_LEAVE)
            .description(DEFAULT_DESCRIPTION)
            .refTable(DEFAULT_REF_TABLE)
            .refTableId(DEFAULT_REF_TABLE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return leavePolicy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeavePolicy createUpdatedEntity(EntityManager em) {
        LeavePolicy leavePolicy = new LeavePolicy()
            .isCarryForword(UPDATED_IS_CARRY_FORWORD)
            .genderLeave(UPDATED_GENDER_LEAVE)
            .totalLeave(UPDATED_TOTAL_LEAVE)
            .maxLeave(UPDATED_MAX_LEAVE)
            .hasproRataLeave(UPDATED_HASPRO_RATA_LEAVE)
            .description(UPDATED_DESCRIPTION)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return leavePolicy;
    }

    @BeforeEach
    public void initTest() {
        leavePolicy = createEntity(em);
    }

    @Test
    @Transactional
    void createLeavePolicy() throws Exception {
        int databaseSizeBeforeCreate = leavePolicyRepository.findAll().size();
        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);
        restLeavePolicyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeCreate + 1);
        LeavePolicy testLeavePolicy = leavePolicyList.get(leavePolicyList.size() - 1);
        assertThat(testLeavePolicy.getIsCarryForword()).isEqualTo(DEFAULT_IS_CARRY_FORWORD);
        assertThat(testLeavePolicy.getGenderLeave()).isEqualTo(DEFAULT_GENDER_LEAVE);
        assertThat(testLeavePolicy.getTotalLeave()).isEqualTo(DEFAULT_TOTAL_LEAVE);
        assertThat(testLeavePolicy.getMaxLeave()).isEqualTo(DEFAULT_MAX_LEAVE);
        assertThat(testLeavePolicy.getHasproRataLeave()).isEqualTo(DEFAULT_HASPRO_RATA_LEAVE);
        assertThat(testLeavePolicy.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLeavePolicy.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testLeavePolicy.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
        assertThat(testLeavePolicy.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testLeavePolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeavePolicy.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeavePolicy.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createLeavePolicyWithExistingId() throws Exception {
        // Create the LeavePolicy with an existing ID
        leavePolicy.setId(1L);
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        int databaseSizeBeforeCreate = leavePolicyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeavePolicyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeavePolicies() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList
        restLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leavePolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].isCarryForword").value(hasItem(DEFAULT_IS_CARRY_FORWORD.booleanValue())))
            .andExpect(jsonPath("$.[*].genderLeave").value(hasItem(DEFAULT_GENDER_LEAVE.intValue())))
            .andExpect(jsonPath("$.[*].totalLeave").value(hasItem(DEFAULT_TOTAL_LEAVE.intValue())))
            .andExpect(jsonPath("$.[*].maxLeave").value(hasItem(DEFAULT_MAX_LEAVE.intValue())))
            .andExpect(jsonPath("$.[*].hasproRataLeave").value(hasItem(DEFAULT_HASPRO_RATA_LEAVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLeavePoliciesWithEagerRelationshipsIsEnabled() throws Exception {
        when(leavePolicyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLeavePolicyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(leavePolicyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLeavePoliciesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(leavePolicyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLeavePolicyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(leavePolicyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLeavePolicy() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get the leavePolicy
        restLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL_ID, leavePolicy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leavePolicy.getId().intValue()))
            .andExpect(jsonPath("$.isCarryForword").value(DEFAULT_IS_CARRY_FORWORD.booleanValue()))
            .andExpect(jsonPath("$.genderLeave").value(DEFAULT_GENDER_LEAVE.intValue()))
            .andExpect(jsonPath("$.totalLeave").value(DEFAULT_TOTAL_LEAVE.intValue()))
            .andExpect(jsonPath("$.maxLeave").value(DEFAULT_MAX_LEAVE.intValue()))
            .andExpect(jsonPath("$.hasproRataLeave").value(DEFAULT_HASPRO_RATA_LEAVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
            .andExpect(jsonPath("$.refTableId").value(DEFAULT_REF_TABLE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getLeavePoliciesByIdFiltering() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        Long id = leavePolicy.getId();

        defaultLeavePolicyShouldBeFound("id.equals=" + id);
        defaultLeavePolicyShouldNotBeFound("id.notEquals=" + id);

        defaultLeavePolicyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeavePolicyShouldNotBeFound("id.greaterThan=" + id);

        defaultLeavePolicyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeavePolicyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByIsCarryForwordIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where isCarryForword equals to DEFAULT_IS_CARRY_FORWORD
        defaultLeavePolicyShouldBeFound("isCarryForword.equals=" + DEFAULT_IS_CARRY_FORWORD);

        // Get all the leavePolicyList where isCarryForword equals to UPDATED_IS_CARRY_FORWORD
        defaultLeavePolicyShouldNotBeFound("isCarryForword.equals=" + UPDATED_IS_CARRY_FORWORD);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByIsCarryForwordIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where isCarryForword in DEFAULT_IS_CARRY_FORWORD or UPDATED_IS_CARRY_FORWORD
        defaultLeavePolicyShouldBeFound("isCarryForword.in=" + DEFAULT_IS_CARRY_FORWORD + "," + UPDATED_IS_CARRY_FORWORD);

        // Get all the leavePolicyList where isCarryForword equals to UPDATED_IS_CARRY_FORWORD
        defaultLeavePolicyShouldNotBeFound("isCarryForword.in=" + UPDATED_IS_CARRY_FORWORD);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByIsCarryForwordIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where isCarryForword is not null
        defaultLeavePolicyShouldBeFound("isCarryForword.specified=true");

        // Get all the leavePolicyList where isCarryForword is null
        defaultLeavePolicyShouldNotBeFound("isCarryForword.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave equals to DEFAULT_GENDER_LEAVE
        defaultLeavePolicyShouldBeFound("genderLeave.equals=" + DEFAULT_GENDER_LEAVE);

        // Get all the leavePolicyList where genderLeave equals to UPDATED_GENDER_LEAVE
        defaultLeavePolicyShouldNotBeFound("genderLeave.equals=" + UPDATED_GENDER_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave in DEFAULT_GENDER_LEAVE or UPDATED_GENDER_LEAVE
        defaultLeavePolicyShouldBeFound("genderLeave.in=" + DEFAULT_GENDER_LEAVE + "," + UPDATED_GENDER_LEAVE);

        // Get all the leavePolicyList where genderLeave equals to UPDATED_GENDER_LEAVE
        defaultLeavePolicyShouldNotBeFound("genderLeave.in=" + UPDATED_GENDER_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave is not null
        defaultLeavePolicyShouldBeFound("genderLeave.specified=true");

        // Get all the leavePolicyList where genderLeave is null
        defaultLeavePolicyShouldNotBeFound("genderLeave.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave is greater than or equal to DEFAULT_GENDER_LEAVE
        defaultLeavePolicyShouldBeFound("genderLeave.greaterThanOrEqual=" + DEFAULT_GENDER_LEAVE);

        // Get all the leavePolicyList where genderLeave is greater than or equal to UPDATED_GENDER_LEAVE
        defaultLeavePolicyShouldNotBeFound("genderLeave.greaterThanOrEqual=" + UPDATED_GENDER_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave is less than or equal to DEFAULT_GENDER_LEAVE
        defaultLeavePolicyShouldBeFound("genderLeave.lessThanOrEqual=" + DEFAULT_GENDER_LEAVE);

        // Get all the leavePolicyList where genderLeave is less than or equal to SMALLER_GENDER_LEAVE
        defaultLeavePolicyShouldNotBeFound("genderLeave.lessThanOrEqual=" + SMALLER_GENDER_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave is less than DEFAULT_GENDER_LEAVE
        defaultLeavePolicyShouldNotBeFound("genderLeave.lessThan=" + DEFAULT_GENDER_LEAVE);

        // Get all the leavePolicyList where genderLeave is less than UPDATED_GENDER_LEAVE
        defaultLeavePolicyShouldBeFound("genderLeave.lessThan=" + UPDATED_GENDER_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByGenderLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where genderLeave is greater than DEFAULT_GENDER_LEAVE
        defaultLeavePolicyShouldNotBeFound("genderLeave.greaterThan=" + DEFAULT_GENDER_LEAVE);

        // Get all the leavePolicyList where genderLeave is greater than SMALLER_GENDER_LEAVE
        defaultLeavePolicyShouldBeFound("genderLeave.greaterThan=" + SMALLER_GENDER_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave equals to DEFAULT_TOTAL_LEAVE
        defaultLeavePolicyShouldBeFound("totalLeave.equals=" + DEFAULT_TOTAL_LEAVE);

        // Get all the leavePolicyList where totalLeave equals to UPDATED_TOTAL_LEAVE
        defaultLeavePolicyShouldNotBeFound("totalLeave.equals=" + UPDATED_TOTAL_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave in DEFAULT_TOTAL_LEAVE or UPDATED_TOTAL_LEAVE
        defaultLeavePolicyShouldBeFound("totalLeave.in=" + DEFAULT_TOTAL_LEAVE + "," + UPDATED_TOTAL_LEAVE);

        // Get all the leavePolicyList where totalLeave equals to UPDATED_TOTAL_LEAVE
        defaultLeavePolicyShouldNotBeFound("totalLeave.in=" + UPDATED_TOTAL_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave is not null
        defaultLeavePolicyShouldBeFound("totalLeave.specified=true");

        // Get all the leavePolicyList where totalLeave is null
        defaultLeavePolicyShouldNotBeFound("totalLeave.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave is greater than or equal to DEFAULT_TOTAL_LEAVE
        defaultLeavePolicyShouldBeFound("totalLeave.greaterThanOrEqual=" + DEFAULT_TOTAL_LEAVE);

        // Get all the leavePolicyList where totalLeave is greater than or equal to UPDATED_TOTAL_LEAVE
        defaultLeavePolicyShouldNotBeFound("totalLeave.greaterThanOrEqual=" + UPDATED_TOTAL_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave is less than or equal to DEFAULT_TOTAL_LEAVE
        defaultLeavePolicyShouldBeFound("totalLeave.lessThanOrEqual=" + DEFAULT_TOTAL_LEAVE);

        // Get all the leavePolicyList where totalLeave is less than or equal to SMALLER_TOTAL_LEAVE
        defaultLeavePolicyShouldNotBeFound("totalLeave.lessThanOrEqual=" + SMALLER_TOTAL_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave is less than DEFAULT_TOTAL_LEAVE
        defaultLeavePolicyShouldNotBeFound("totalLeave.lessThan=" + DEFAULT_TOTAL_LEAVE);

        // Get all the leavePolicyList where totalLeave is less than UPDATED_TOTAL_LEAVE
        defaultLeavePolicyShouldBeFound("totalLeave.lessThan=" + UPDATED_TOTAL_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByTotalLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where totalLeave is greater than DEFAULT_TOTAL_LEAVE
        defaultLeavePolicyShouldNotBeFound("totalLeave.greaterThan=" + DEFAULT_TOTAL_LEAVE);

        // Get all the leavePolicyList where totalLeave is greater than SMALLER_TOTAL_LEAVE
        defaultLeavePolicyShouldBeFound("totalLeave.greaterThan=" + SMALLER_TOTAL_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave equals to DEFAULT_MAX_LEAVE
        defaultLeavePolicyShouldBeFound("maxLeave.equals=" + DEFAULT_MAX_LEAVE);

        // Get all the leavePolicyList where maxLeave equals to UPDATED_MAX_LEAVE
        defaultLeavePolicyShouldNotBeFound("maxLeave.equals=" + UPDATED_MAX_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave in DEFAULT_MAX_LEAVE or UPDATED_MAX_LEAVE
        defaultLeavePolicyShouldBeFound("maxLeave.in=" + DEFAULT_MAX_LEAVE + "," + UPDATED_MAX_LEAVE);

        // Get all the leavePolicyList where maxLeave equals to UPDATED_MAX_LEAVE
        defaultLeavePolicyShouldNotBeFound("maxLeave.in=" + UPDATED_MAX_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave is not null
        defaultLeavePolicyShouldBeFound("maxLeave.specified=true");

        // Get all the leavePolicyList where maxLeave is null
        defaultLeavePolicyShouldNotBeFound("maxLeave.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave is greater than or equal to DEFAULT_MAX_LEAVE
        defaultLeavePolicyShouldBeFound("maxLeave.greaterThanOrEqual=" + DEFAULT_MAX_LEAVE);

        // Get all the leavePolicyList where maxLeave is greater than or equal to UPDATED_MAX_LEAVE
        defaultLeavePolicyShouldNotBeFound("maxLeave.greaterThanOrEqual=" + UPDATED_MAX_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave is less than or equal to DEFAULT_MAX_LEAVE
        defaultLeavePolicyShouldBeFound("maxLeave.lessThanOrEqual=" + DEFAULT_MAX_LEAVE);

        // Get all the leavePolicyList where maxLeave is less than or equal to SMALLER_MAX_LEAVE
        defaultLeavePolicyShouldNotBeFound("maxLeave.lessThanOrEqual=" + SMALLER_MAX_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave is less than DEFAULT_MAX_LEAVE
        defaultLeavePolicyShouldNotBeFound("maxLeave.lessThan=" + DEFAULT_MAX_LEAVE);

        // Get all the leavePolicyList where maxLeave is less than UPDATED_MAX_LEAVE
        defaultLeavePolicyShouldBeFound("maxLeave.lessThan=" + UPDATED_MAX_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByMaxLeaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where maxLeave is greater than DEFAULT_MAX_LEAVE
        defaultLeavePolicyShouldNotBeFound("maxLeave.greaterThan=" + DEFAULT_MAX_LEAVE);

        // Get all the leavePolicyList where maxLeave is greater than SMALLER_MAX_LEAVE
        defaultLeavePolicyShouldBeFound("maxLeave.greaterThan=" + SMALLER_MAX_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByHasproRataLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where hasproRataLeave equals to DEFAULT_HASPRO_RATA_LEAVE
        defaultLeavePolicyShouldBeFound("hasproRataLeave.equals=" + DEFAULT_HASPRO_RATA_LEAVE);

        // Get all the leavePolicyList where hasproRataLeave equals to UPDATED_HASPRO_RATA_LEAVE
        defaultLeavePolicyShouldNotBeFound("hasproRataLeave.equals=" + UPDATED_HASPRO_RATA_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByHasproRataLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where hasproRataLeave in DEFAULT_HASPRO_RATA_LEAVE or UPDATED_HASPRO_RATA_LEAVE
        defaultLeavePolicyShouldBeFound("hasproRataLeave.in=" + DEFAULT_HASPRO_RATA_LEAVE + "," + UPDATED_HASPRO_RATA_LEAVE);

        // Get all the leavePolicyList where hasproRataLeave equals to UPDATED_HASPRO_RATA_LEAVE
        defaultLeavePolicyShouldNotBeFound("hasproRataLeave.in=" + UPDATED_HASPRO_RATA_LEAVE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByHasproRataLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where hasproRataLeave is not null
        defaultLeavePolicyShouldBeFound("hasproRataLeave.specified=true");

        // Get all the leavePolicyList where hasproRataLeave is null
        defaultLeavePolicyShouldNotBeFound("hasproRataLeave.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where description equals to DEFAULT_DESCRIPTION
        defaultLeavePolicyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the leavePolicyList where description equals to UPDATED_DESCRIPTION
        defaultLeavePolicyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLeavePolicyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the leavePolicyList where description equals to UPDATED_DESCRIPTION
        defaultLeavePolicyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where description is not null
        defaultLeavePolicyShouldBeFound("description.specified=true");

        // Get all the leavePolicyList where description is null
        defaultLeavePolicyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where description contains DEFAULT_DESCRIPTION
        defaultLeavePolicyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the leavePolicyList where description contains UPDATED_DESCRIPTION
        defaultLeavePolicyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where description does not contain DEFAULT_DESCRIPTION
        defaultLeavePolicyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the leavePolicyList where description does not contain UPDATED_DESCRIPTION
        defaultLeavePolicyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTable equals to DEFAULT_REF_TABLE
        defaultLeavePolicyShouldBeFound("refTable.equals=" + DEFAULT_REF_TABLE);

        // Get all the leavePolicyList where refTable equals to UPDATED_REF_TABLE
        defaultLeavePolicyShouldNotBeFound("refTable.equals=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTable in DEFAULT_REF_TABLE or UPDATED_REF_TABLE
        defaultLeavePolicyShouldBeFound("refTable.in=" + DEFAULT_REF_TABLE + "," + UPDATED_REF_TABLE);

        // Get all the leavePolicyList where refTable equals to UPDATED_REF_TABLE
        defaultLeavePolicyShouldNotBeFound("refTable.in=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTable is not null
        defaultLeavePolicyShouldBeFound("refTable.specified=true");

        // Get all the leavePolicyList where refTable is null
        defaultLeavePolicyShouldNotBeFound("refTable.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTable contains DEFAULT_REF_TABLE
        defaultLeavePolicyShouldBeFound("refTable.contains=" + DEFAULT_REF_TABLE);

        // Get all the leavePolicyList where refTable contains UPDATED_REF_TABLE
        defaultLeavePolicyShouldNotBeFound("refTable.contains=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTable does not contain DEFAULT_REF_TABLE
        defaultLeavePolicyShouldNotBeFound("refTable.doesNotContain=" + DEFAULT_REF_TABLE);

        // Get all the leavePolicyList where refTable does not contain UPDATED_REF_TABLE
        defaultLeavePolicyShouldBeFound("refTable.doesNotContain=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId equals to DEFAULT_REF_TABLE_ID
        defaultLeavePolicyShouldBeFound("refTableId.equals=" + DEFAULT_REF_TABLE_ID);

        // Get all the leavePolicyList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultLeavePolicyShouldNotBeFound("refTableId.equals=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId in DEFAULT_REF_TABLE_ID or UPDATED_REF_TABLE_ID
        defaultLeavePolicyShouldBeFound("refTableId.in=" + DEFAULT_REF_TABLE_ID + "," + UPDATED_REF_TABLE_ID);

        // Get all the leavePolicyList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultLeavePolicyShouldNotBeFound("refTableId.in=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId is not null
        defaultLeavePolicyShouldBeFound("refTableId.specified=true");

        // Get all the leavePolicyList where refTableId is null
        defaultLeavePolicyShouldNotBeFound("refTableId.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId is greater than or equal to DEFAULT_REF_TABLE_ID
        defaultLeavePolicyShouldBeFound("refTableId.greaterThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the leavePolicyList where refTableId is greater than or equal to UPDATED_REF_TABLE_ID
        defaultLeavePolicyShouldNotBeFound("refTableId.greaterThanOrEqual=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId is less than or equal to DEFAULT_REF_TABLE_ID
        defaultLeavePolicyShouldBeFound("refTableId.lessThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the leavePolicyList where refTableId is less than or equal to SMALLER_REF_TABLE_ID
        defaultLeavePolicyShouldNotBeFound("refTableId.lessThanOrEqual=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId is less than DEFAULT_REF_TABLE_ID
        defaultLeavePolicyShouldNotBeFound("refTableId.lessThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the leavePolicyList where refTableId is less than UPDATED_REF_TABLE_ID
        defaultLeavePolicyShouldBeFound("refTableId.lessThan=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByRefTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where refTableId is greater than DEFAULT_REF_TABLE_ID
        defaultLeavePolicyShouldNotBeFound("refTableId.greaterThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the leavePolicyList where refTableId is greater than SMALLER_REF_TABLE_ID
        defaultLeavePolicyShouldBeFound("refTableId.greaterThan=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId equals to DEFAULT_COMPANY_ID
        defaultLeavePolicyShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the leavePolicyList where companyId equals to UPDATED_COMPANY_ID
        defaultLeavePolicyShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultLeavePolicyShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the leavePolicyList where companyId equals to UPDATED_COMPANY_ID
        defaultLeavePolicyShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId is not null
        defaultLeavePolicyShouldBeFound("companyId.specified=true");

        // Get all the leavePolicyList where companyId is null
        defaultLeavePolicyShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultLeavePolicyShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the leavePolicyList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultLeavePolicyShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultLeavePolicyShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the leavePolicyList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultLeavePolicyShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId is less than DEFAULT_COMPANY_ID
        defaultLeavePolicyShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the leavePolicyList where companyId is less than UPDATED_COMPANY_ID
        defaultLeavePolicyShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where companyId is greater than DEFAULT_COMPANY_ID
        defaultLeavePolicyShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the leavePolicyList where companyId is greater than SMALLER_COMPANY_ID
        defaultLeavePolicyShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where status equals to DEFAULT_STATUS
        defaultLeavePolicyShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leavePolicyList where status equals to UPDATED_STATUS
        defaultLeavePolicyShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeavePolicyShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leavePolicyList where status equals to UPDATED_STATUS
        defaultLeavePolicyShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where status is not null
        defaultLeavePolicyShouldBeFound("status.specified=true");

        // Get all the leavePolicyList where status is null
        defaultLeavePolicyShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByStatusContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where status contains DEFAULT_STATUS
        defaultLeavePolicyShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the leavePolicyList where status contains UPDATED_STATUS
        defaultLeavePolicyShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where status does not contain DEFAULT_STATUS
        defaultLeavePolicyShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the leavePolicyList where status does not contain UPDATED_STATUS
        defaultLeavePolicyShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLeavePolicyShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the leavePolicyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeavePolicyShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLeavePolicyShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the leavePolicyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLeavePolicyShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModified is not null
        defaultLeavePolicyShouldBeFound("lastModified.specified=true");

        // Get all the leavePolicyList where lastModified is null
        defaultLeavePolicyShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLeavePolicyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leavePolicyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeavePolicyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLeavePolicyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the leavePolicyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLeavePolicyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModifiedBy is not null
        defaultLeavePolicyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the leavePolicyList where lastModifiedBy is null
        defaultLeavePolicyShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLeavePolicyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leavePolicyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLeavePolicyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        // Get all the leavePolicyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLeavePolicyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the leavePolicyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLeavePolicyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByLeaveTypeIsEqualToSomething() throws Exception {
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leavePolicyRepository.saveAndFlush(leavePolicy);
            leaveType = LeaveTypeResourceIT.createEntity(em);
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        em.persist(leaveType);
        em.flush();
        leavePolicy.setLeaveType(leaveType);
        leavePolicyRepository.saveAndFlush(leavePolicy);
        Long leaveTypeId = leaveType.getId();

        // Get all the leavePolicyList where leaveType equals to leaveTypeId
        defaultLeavePolicyShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leavePolicyList where leaveType equals to (leaveTypeId + 1)
        defaultLeavePolicyShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }

    @Test
    @Transactional
    void getAllLeavePoliciesByEmploymentTypeIsEqualToSomething() throws Exception {
        EmploymentType employmentType;
        if (TestUtil.findAll(em, EmploymentType.class).isEmpty()) {
            leavePolicyRepository.saveAndFlush(leavePolicy);
            employmentType = EmploymentTypeResourceIT.createEntity(em);
        } else {
            employmentType = TestUtil.findAll(em, EmploymentType.class).get(0);
        }
        em.persist(employmentType);
        em.flush();
        leavePolicy.setEmploymentType(employmentType);
        leavePolicyRepository.saveAndFlush(leavePolicy);
        Long employmentTypeId = employmentType.getId();

        // Get all the leavePolicyList where employmentType equals to employmentTypeId
        defaultLeavePolicyShouldBeFound("employmentTypeId.equals=" + employmentTypeId);

        // Get all the leavePolicyList where employmentType equals to (employmentTypeId + 1)
        defaultLeavePolicyShouldNotBeFound("employmentTypeId.equals=" + (employmentTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeavePolicyShouldBeFound(String filter) throws Exception {
        restLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leavePolicy.getId().intValue())))
            .andExpect(jsonPath("$.[*].isCarryForword").value(hasItem(DEFAULT_IS_CARRY_FORWORD.booleanValue())))
            .andExpect(jsonPath("$.[*].genderLeave").value(hasItem(DEFAULT_GENDER_LEAVE.intValue())))
            .andExpect(jsonPath("$.[*].totalLeave").value(hasItem(DEFAULT_TOTAL_LEAVE.intValue())))
            .andExpect(jsonPath("$.[*].maxLeave").value(hasItem(DEFAULT_MAX_LEAVE.intValue())))
            .andExpect(jsonPath("$.[*].hasproRataLeave").value(hasItem(DEFAULT_HASPRO_RATA_LEAVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeavePolicyShouldNotBeFound(String filter) throws Exception {
        restLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeavePolicyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLeavePolicy() throws Exception {
        // Get the leavePolicy
        restLeavePolicyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeavePolicy() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();

        // Update the leavePolicy
        LeavePolicy updatedLeavePolicy = leavePolicyRepository.findById(leavePolicy.getId()).get();
        // Disconnect from session so that the updates on updatedLeavePolicy are not directly saved in db
        em.detach(updatedLeavePolicy);
        updatedLeavePolicy
            .isCarryForword(UPDATED_IS_CARRY_FORWORD)
            .genderLeave(UPDATED_GENDER_LEAVE)
            .totalLeave(UPDATED_TOTAL_LEAVE)
            .maxLeave(UPDATED_MAX_LEAVE)
            .hasproRataLeave(UPDATED_HASPRO_RATA_LEAVE)
            .description(UPDATED_DESCRIPTION)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(updatedLeavePolicy);

        restLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leavePolicyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
        LeavePolicy testLeavePolicy = leavePolicyList.get(leavePolicyList.size() - 1);
        assertThat(testLeavePolicy.getIsCarryForword()).isEqualTo(UPDATED_IS_CARRY_FORWORD);
        assertThat(testLeavePolicy.getGenderLeave()).isEqualTo(UPDATED_GENDER_LEAVE);
        assertThat(testLeavePolicy.getTotalLeave()).isEqualTo(UPDATED_TOTAL_LEAVE);
        assertThat(testLeavePolicy.getMaxLeave()).isEqualTo(UPDATED_MAX_LEAVE);
        assertThat(testLeavePolicy.getHasproRataLeave()).isEqualTo(UPDATED_HASPRO_RATA_LEAVE);
        assertThat(testLeavePolicy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeavePolicy.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testLeavePolicy.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testLeavePolicy.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeavePolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeavePolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeavePolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();
        leavePolicy.setId(count.incrementAndGet());

        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leavePolicyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();
        leavePolicy.setId(count.incrementAndGet());

        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavePolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();
        leavePolicy.setId(count.incrementAndGet());

        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavePolicyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeavePolicyWithPatch() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();

        // Update the leavePolicy using partial update
        LeavePolicy partialUpdatedLeavePolicy = new LeavePolicy();
        partialUpdatedLeavePolicy.setId(leavePolicy.getId());

        partialUpdatedLeavePolicy
            .totalLeave(UPDATED_TOTAL_LEAVE)
            .maxLeave(UPDATED_MAX_LEAVE)
            .hasproRataLeave(UPDATED_HASPRO_RATA_LEAVE)
            .description(UPDATED_DESCRIPTION)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeavePolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeavePolicy))
            )
            .andExpect(status().isOk());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
        LeavePolicy testLeavePolicy = leavePolicyList.get(leavePolicyList.size() - 1);
        assertThat(testLeavePolicy.getIsCarryForword()).isEqualTo(DEFAULT_IS_CARRY_FORWORD);
        assertThat(testLeavePolicy.getGenderLeave()).isEqualTo(DEFAULT_GENDER_LEAVE);
        assertThat(testLeavePolicy.getTotalLeave()).isEqualTo(UPDATED_TOTAL_LEAVE);
        assertThat(testLeavePolicy.getMaxLeave()).isEqualTo(UPDATED_MAX_LEAVE);
        assertThat(testLeavePolicy.getHasproRataLeave()).isEqualTo(UPDATED_HASPRO_RATA_LEAVE);
        assertThat(testLeavePolicy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeavePolicy.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testLeavePolicy.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testLeavePolicy.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testLeavePolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeavePolicy.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLeavePolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateLeavePolicyWithPatch() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();

        // Update the leavePolicy using partial update
        LeavePolicy partialUpdatedLeavePolicy = new LeavePolicy();
        partialUpdatedLeavePolicy.setId(leavePolicy.getId());

        partialUpdatedLeavePolicy
            .isCarryForword(UPDATED_IS_CARRY_FORWORD)
            .genderLeave(UPDATED_GENDER_LEAVE)
            .totalLeave(UPDATED_TOTAL_LEAVE)
            .maxLeave(UPDATED_MAX_LEAVE)
            .hasproRataLeave(UPDATED_HASPRO_RATA_LEAVE)
            .description(UPDATED_DESCRIPTION)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeavePolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeavePolicy))
            )
            .andExpect(status().isOk());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
        LeavePolicy testLeavePolicy = leavePolicyList.get(leavePolicyList.size() - 1);
        assertThat(testLeavePolicy.getIsCarryForword()).isEqualTo(UPDATED_IS_CARRY_FORWORD);
        assertThat(testLeavePolicy.getGenderLeave()).isEqualTo(UPDATED_GENDER_LEAVE);
        assertThat(testLeavePolicy.getTotalLeave()).isEqualTo(UPDATED_TOTAL_LEAVE);
        assertThat(testLeavePolicy.getMaxLeave()).isEqualTo(UPDATED_MAX_LEAVE);
        assertThat(testLeavePolicy.getHasproRataLeave()).isEqualTo(UPDATED_HASPRO_RATA_LEAVE);
        assertThat(testLeavePolicy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeavePolicy.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testLeavePolicy.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testLeavePolicy.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testLeavePolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeavePolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLeavePolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();
        leavePolicy.setId(count.incrementAndGet());

        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leavePolicyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();
        leavePolicy.setId(count.incrementAndGet());

        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeavePolicy() throws Exception {
        int databaseSizeBeforeUpdate = leavePolicyRepository.findAll().size();
        leavePolicy.setId(count.incrementAndGet());

        // Create the LeavePolicy
        LeavePolicyDTO leavePolicyDTO = leavePolicyMapper.toDto(leavePolicy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeavePolicyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(leavePolicyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeavePolicy in the database
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeavePolicy() throws Exception {
        // Initialize the database
        leavePolicyRepository.saveAndFlush(leavePolicy);

        int databaseSizeBeforeDelete = leavePolicyRepository.findAll().size();

        // Delete the leavePolicy
        restLeavePolicyMockMvc
            .perform(delete(ENTITY_API_URL_ID, leavePolicy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeavePolicy> leavePolicyList = leavePolicyRepository.findAll();
        assertThat(leavePolicyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
