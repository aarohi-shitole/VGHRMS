package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.CustomApprovar;
import com.techvg.hrms.repository.CustomApprovarRepository;
import com.techvg.hrms.service.criteria.CustomApprovarCriteria;
import com.techvg.hrms.service.dto.CustomApprovarDTO;
import com.techvg.hrms.service.mapper.CustomApprovarMapper;
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
 * Integration tests for the {@link CustomApprovarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomApprovarResourceIT {

    private static final Long DEFAULT_EMPLOYE_ID = 1L;
    private static final Long UPDATED_EMPLOYE_ID = 2L;
    private static final Long SMALLER_EMPLOYE_ID = 1L - 1L;

    private static final String DEFAULT_APPROVAL_SETTING_ID = "AAAAAAAAAA";
    private static final String UPDATED_APPROVAL_SETTING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SQUENCE = "AAAAAAAAAA";
    private static final String UPDATED_SQUENCE = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/custom-approvars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomApprovarRepository customApprovarRepository;

    @Autowired
    private CustomApprovarMapper customApprovarMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomApprovarMockMvc;

    private CustomApprovar customApprovar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomApprovar createEntity(EntityManager em) {
        CustomApprovar customApprovar = new CustomApprovar()
            .employeId(DEFAULT_EMPLOYE_ID)
            .approvalSettingId(DEFAULT_APPROVAL_SETTING_ID)
            .squence(DEFAULT_SQUENCE)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return customApprovar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomApprovar createUpdatedEntity(EntityManager em) {
        CustomApprovar customApprovar = new CustomApprovar()
            .employeId(UPDATED_EMPLOYE_ID)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .squence(UPDATED_SQUENCE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return customApprovar;
    }

    @BeforeEach
    public void initTest() {
        customApprovar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomApprovar() throws Exception {
        int databaseSizeBeforeCreate = customApprovarRepository.findAll().size();
        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);
        restCustomApprovarMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeCreate + 1);
        CustomApprovar testCustomApprovar = customApprovarList.get(customApprovarList.size() - 1);
        assertThat(testCustomApprovar.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testCustomApprovar.getApprovalSettingId()).isEqualTo(DEFAULT_APPROVAL_SETTING_ID);
        assertThat(testCustomApprovar.getSquence()).isEqualTo(DEFAULT_SQUENCE);
        assertThat(testCustomApprovar.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCustomApprovar.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomApprovar.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCustomApprovar.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCustomApprovarWithExistingId() throws Exception {
        // Create the CustomApprovar with an existing ID
        customApprovar.setId(1L);
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        int databaseSizeBeforeCreate = customApprovarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomApprovarMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomApprovars() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList
        restCustomApprovarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customApprovar.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvalSettingId").value(hasItem(DEFAULT_APPROVAL_SETTING_ID)))
            .andExpect(jsonPath("$.[*].squence").value(hasItem(DEFAULT_SQUENCE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCustomApprovar() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get the customApprovar
        restCustomApprovarMockMvc
            .perform(get(ENTITY_API_URL_ID, customApprovar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customApprovar.getId().intValue()))
            .andExpect(jsonPath("$.employeId").value(DEFAULT_EMPLOYE_ID.intValue()))
            .andExpect(jsonPath("$.approvalSettingId").value(DEFAULT_APPROVAL_SETTING_ID))
            .andExpect(jsonPath("$.squence").value(DEFAULT_SQUENCE))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getCustomApprovarsByIdFiltering() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        Long id = customApprovar.getId();

        defaultCustomApprovarShouldBeFound("id.equals=" + id);
        defaultCustomApprovarShouldNotBeFound("id.notEquals=" + id);

        defaultCustomApprovarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomApprovarShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomApprovarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomApprovarShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId equals to DEFAULT_EMPLOYE_ID
        defaultCustomApprovarShouldBeFound("employeId.equals=" + DEFAULT_EMPLOYE_ID);

        // Get all the customApprovarList where employeId equals to UPDATED_EMPLOYE_ID
        defaultCustomApprovarShouldNotBeFound("employeId.equals=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId in DEFAULT_EMPLOYE_ID or UPDATED_EMPLOYE_ID
        defaultCustomApprovarShouldBeFound("employeId.in=" + DEFAULT_EMPLOYE_ID + "," + UPDATED_EMPLOYE_ID);

        // Get all the customApprovarList where employeId equals to UPDATED_EMPLOYE_ID
        defaultCustomApprovarShouldNotBeFound("employeId.in=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId is not null
        defaultCustomApprovarShouldBeFound("employeId.specified=true");

        // Get all the customApprovarList where employeId is null
        defaultCustomApprovarShouldNotBeFound("employeId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId is greater than or equal to DEFAULT_EMPLOYE_ID
        defaultCustomApprovarShouldBeFound("employeId.greaterThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the customApprovarList where employeId is greater than or equal to UPDATED_EMPLOYE_ID
        defaultCustomApprovarShouldNotBeFound("employeId.greaterThanOrEqual=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId is less than or equal to DEFAULT_EMPLOYE_ID
        defaultCustomApprovarShouldBeFound("employeId.lessThanOrEqual=" + DEFAULT_EMPLOYE_ID);

        // Get all the customApprovarList where employeId is less than or equal to SMALLER_EMPLOYE_ID
        defaultCustomApprovarShouldNotBeFound("employeId.lessThanOrEqual=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId is less than DEFAULT_EMPLOYE_ID
        defaultCustomApprovarShouldNotBeFound("employeId.lessThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the customApprovarList where employeId is less than UPDATED_EMPLOYE_ID
        defaultCustomApprovarShouldBeFound("employeId.lessThan=" + UPDATED_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByEmployeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where employeId is greater than DEFAULT_EMPLOYE_ID
        defaultCustomApprovarShouldNotBeFound("employeId.greaterThan=" + DEFAULT_EMPLOYE_ID);

        // Get all the customApprovarList where employeId is greater than SMALLER_EMPLOYE_ID
        defaultCustomApprovarShouldBeFound("employeId.greaterThan=" + SMALLER_EMPLOYE_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByApprovalSettingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where approvalSettingId equals to DEFAULT_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldBeFound("approvalSettingId.equals=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the customApprovarList where approvalSettingId equals to UPDATED_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldNotBeFound("approvalSettingId.equals=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByApprovalSettingIdIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where approvalSettingId in DEFAULT_APPROVAL_SETTING_ID or UPDATED_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldBeFound("approvalSettingId.in=" + DEFAULT_APPROVAL_SETTING_ID + "," + UPDATED_APPROVAL_SETTING_ID);

        // Get all the customApprovarList where approvalSettingId equals to UPDATED_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldNotBeFound("approvalSettingId.in=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByApprovalSettingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where approvalSettingId is not null
        defaultCustomApprovarShouldBeFound("approvalSettingId.specified=true");

        // Get all the customApprovarList where approvalSettingId is null
        defaultCustomApprovarShouldNotBeFound("approvalSettingId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByApprovalSettingIdContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where approvalSettingId contains DEFAULT_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldBeFound("approvalSettingId.contains=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the customApprovarList where approvalSettingId contains UPDATED_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldNotBeFound("approvalSettingId.contains=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByApprovalSettingIdNotContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where approvalSettingId does not contain DEFAULT_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldNotBeFound("approvalSettingId.doesNotContain=" + DEFAULT_APPROVAL_SETTING_ID);

        // Get all the customApprovarList where approvalSettingId does not contain UPDATED_APPROVAL_SETTING_ID
        defaultCustomApprovarShouldBeFound("approvalSettingId.doesNotContain=" + UPDATED_APPROVAL_SETTING_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsBySquenceIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where squence equals to DEFAULT_SQUENCE
        defaultCustomApprovarShouldBeFound("squence.equals=" + DEFAULT_SQUENCE);

        // Get all the customApprovarList where squence equals to UPDATED_SQUENCE
        defaultCustomApprovarShouldNotBeFound("squence.equals=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsBySquenceIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where squence in DEFAULT_SQUENCE or UPDATED_SQUENCE
        defaultCustomApprovarShouldBeFound("squence.in=" + DEFAULT_SQUENCE + "," + UPDATED_SQUENCE);

        // Get all the customApprovarList where squence equals to UPDATED_SQUENCE
        defaultCustomApprovarShouldNotBeFound("squence.in=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsBySquenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where squence is not null
        defaultCustomApprovarShouldBeFound("squence.specified=true");

        // Get all the customApprovarList where squence is null
        defaultCustomApprovarShouldNotBeFound("squence.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsBySquenceContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where squence contains DEFAULT_SQUENCE
        defaultCustomApprovarShouldBeFound("squence.contains=" + DEFAULT_SQUENCE);

        // Get all the customApprovarList where squence contains UPDATED_SQUENCE
        defaultCustomApprovarShouldNotBeFound("squence.contains=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsBySquenceNotContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where squence does not contain DEFAULT_SQUENCE
        defaultCustomApprovarShouldNotBeFound("squence.doesNotContain=" + DEFAULT_SQUENCE);

        // Get all the customApprovarList where squence does not contain UPDATED_SQUENCE
        defaultCustomApprovarShouldBeFound("squence.doesNotContain=" + UPDATED_SQUENCE);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId equals to DEFAULT_COMPANY_ID
        defaultCustomApprovarShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the customApprovarList where companyId equals to UPDATED_COMPANY_ID
        defaultCustomApprovarShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultCustomApprovarShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the customApprovarList where companyId equals to UPDATED_COMPANY_ID
        defaultCustomApprovarShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId is not null
        defaultCustomApprovarShouldBeFound("companyId.specified=true");

        // Get all the customApprovarList where companyId is null
        defaultCustomApprovarShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultCustomApprovarShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the customApprovarList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultCustomApprovarShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultCustomApprovarShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the customApprovarList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultCustomApprovarShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId is less than DEFAULT_COMPANY_ID
        defaultCustomApprovarShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the customApprovarList where companyId is less than UPDATED_COMPANY_ID
        defaultCustomApprovarShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where companyId is greater than DEFAULT_COMPANY_ID
        defaultCustomApprovarShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the customApprovarList where companyId is greater than SMALLER_COMPANY_ID
        defaultCustomApprovarShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where status equals to DEFAULT_STATUS
        defaultCustomApprovarShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the customApprovarList where status equals to UPDATED_STATUS
        defaultCustomApprovarShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustomApprovarShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the customApprovarList where status equals to UPDATED_STATUS
        defaultCustomApprovarShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where status is not null
        defaultCustomApprovarShouldBeFound("status.specified=true");

        // Get all the customApprovarList where status is null
        defaultCustomApprovarShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByStatusContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where status contains DEFAULT_STATUS
        defaultCustomApprovarShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the customApprovarList where status contains UPDATED_STATUS
        defaultCustomApprovarShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where status does not contain DEFAULT_STATUS
        defaultCustomApprovarShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the customApprovarList where status does not contain UPDATED_STATUS
        defaultCustomApprovarShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultCustomApprovarShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the customApprovarList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCustomApprovarShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultCustomApprovarShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the customApprovarList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCustomApprovarShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModified is not null
        defaultCustomApprovarShouldBeFound("lastModified.specified=true");

        // Get all the customApprovarList where lastModified is null
        defaultCustomApprovarShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCustomApprovarShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customApprovarList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomApprovarShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCustomApprovarShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the customApprovarList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomApprovarShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModifiedBy is not null
        defaultCustomApprovarShouldBeFound("lastModifiedBy.specified=true");

        // Get all the customApprovarList where lastModifiedBy is null
        defaultCustomApprovarShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultCustomApprovarShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customApprovarList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultCustomApprovarShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCustomApprovarsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        // Get all the customApprovarList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultCustomApprovarShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customApprovarList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultCustomApprovarShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomApprovarShouldBeFound(String filter) throws Exception {
        restCustomApprovarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customApprovar.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeId").value(hasItem(DEFAULT_EMPLOYE_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvalSettingId").value(hasItem(DEFAULT_APPROVAL_SETTING_ID)))
            .andExpect(jsonPath("$.[*].squence").value(hasItem(DEFAULT_SQUENCE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restCustomApprovarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomApprovarShouldNotBeFound(String filter) throws Exception {
        restCustomApprovarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomApprovarMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomApprovar() throws Exception {
        // Get the customApprovar
        restCustomApprovarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCustomApprovar() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();

        // Update the customApprovar
        CustomApprovar updatedCustomApprovar = customApprovarRepository.findById(customApprovar.getId()).get();
        // Disconnect from session so that the updates on updatedCustomApprovar are not directly saved in db
        em.detach(updatedCustomApprovar);
        updatedCustomApprovar
            .employeId(UPDATED_EMPLOYE_ID)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .squence(UPDATED_SQUENCE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(updatedCustomApprovar);

        restCustomApprovarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customApprovarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
        CustomApprovar testCustomApprovar = customApprovarList.get(customApprovarList.size() - 1);
        assertThat(testCustomApprovar.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testCustomApprovar.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testCustomApprovar.getSquence()).isEqualTo(UPDATED_SQUENCE);
        assertThat(testCustomApprovar.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomApprovar.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomApprovar.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCustomApprovar.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCustomApprovar() throws Exception {
        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();
        customApprovar.setId(count.incrementAndGet());

        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomApprovarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customApprovarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomApprovar() throws Exception {
        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();
        customApprovar.setId(count.incrementAndGet());

        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomApprovarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomApprovar() throws Exception {
        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();
        customApprovar.setId(count.incrementAndGet());

        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomApprovarMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomApprovarWithPatch() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();

        // Update the customApprovar using partial update
        CustomApprovar partialUpdatedCustomApprovar = new CustomApprovar();
        partialUpdatedCustomApprovar.setId(customApprovar.getId());

        partialUpdatedCustomApprovar
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .status(UPDATED_STATUS)//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restCustomApprovarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomApprovar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomApprovar))
            )
            .andExpect(status().isOk());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
        CustomApprovar testCustomApprovar = customApprovarList.get(customApprovarList.size() - 1);
        assertThat(testCustomApprovar.getEmployeId()).isEqualTo(DEFAULT_EMPLOYE_ID);
        assertThat(testCustomApprovar.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testCustomApprovar.getSquence()).isEqualTo(DEFAULT_SQUENCE);
        assertThat(testCustomApprovar.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCustomApprovar.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomApprovar.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCustomApprovar.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCustomApprovarWithPatch() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();

        // Update the customApprovar using partial update
        CustomApprovar partialUpdatedCustomApprovar = new CustomApprovar();
        partialUpdatedCustomApprovar.setId(customApprovar.getId());

        partialUpdatedCustomApprovar
            .employeId(UPDATED_EMPLOYE_ID)
            .approvalSettingId(UPDATED_APPROVAL_SETTING_ID)
            .squence(UPDATED_SQUENCE)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restCustomApprovarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomApprovar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomApprovar))
            )
            .andExpect(status().isOk());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
        CustomApprovar testCustomApprovar = customApprovarList.get(customApprovarList.size() - 1);
        assertThat(testCustomApprovar.getEmployeId()).isEqualTo(UPDATED_EMPLOYE_ID);
        assertThat(testCustomApprovar.getApprovalSettingId()).isEqualTo(UPDATED_APPROVAL_SETTING_ID);
        assertThat(testCustomApprovar.getSquence()).isEqualTo(UPDATED_SQUENCE);
        assertThat(testCustomApprovar.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomApprovar.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomApprovar.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCustomApprovar.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCustomApprovar() throws Exception {
        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();
        customApprovar.setId(count.incrementAndGet());

        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomApprovarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customApprovarDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomApprovar() throws Exception {
        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();
        customApprovar.setId(count.incrementAndGet());

        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomApprovarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomApprovar() throws Exception {
        int databaseSizeBeforeUpdate = customApprovarRepository.findAll().size();
        customApprovar.setId(count.incrementAndGet());

        // Create the CustomApprovar
        CustomApprovarDTO customApprovarDTO = customApprovarMapper.toDto(customApprovar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomApprovarMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customApprovarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomApprovar in the database
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomApprovar() throws Exception {
        // Initialize the database
        customApprovarRepository.saveAndFlush(customApprovar);

        int databaseSizeBeforeDelete = customApprovarRepository.findAll().size();

        // Delete the customApprovar
        restCustomApprovarMockMvc
            .perform(delete(ENTITY_API_URL_ID, customApprovar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomApprovar> customApprovarList = customApprovarRepository.findAll();
        assertThat(customApprovarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
