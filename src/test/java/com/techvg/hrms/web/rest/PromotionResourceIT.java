package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Promotion;
import com.techvg.hrms.repository.PromotionRepository;
import com.techvg.hrms.service.criteria.PromotionCriteria;
import com.techvg.hrms.service.dto.PromotionDTO;
import com.techvg.hrms.service.mapper.PromotionMapper;
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
 * Integration tests for the {@link PromotionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PromotionResourceIT {

    private static final String DEFAULT_PROMOTION_FOR = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTION_FOR = "BBBBBBBBBB";

    private static final String DEFAULT_PROMOTED_FROM = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_PROMOTED_TO = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTED_TO = "BBBBBBBBBB";

    private static final Instant DEFAULT_PROMOTIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PROMOTIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BRANCH_ID = 1L;
    private static final Long UPDATED_BRANCH_ID = 2L;
    private static final Long SMALLER_BRANCH_ID = 1L - 1L;

    private static final Long DEFAULT_DEPARTMENT_ID = 1L;
    private static final Long UPDATED_DEPARTMENT_ID = 2L;
    private static final Long SMALLER_DEPARTMENT_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/promotions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPromotionMockMvc;

    private Promotion promotion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotion createEntity(EntityManager em) {
        Promotion promotion = new Promotion()
            .promotionFor(DEFAULT_PROMOTION_FOR)
            .promotedFrom(DEFAULT_PROMOTED_FROM)
            .promotedTo(DEFAULT_PROMOTED_TO)
            .promotiedDate(DEFAULT_PROMOTIED_DATE)
            .branchId(DEFAULT_BRANCH_ID)
            .departmentId(DEFAULT_DEPARTMENT_ID)
            .status(DEFAULT_STATUS)
            .employeeId(DEFAULT_EMPLOYEE_ID);
//            .companyId(DEFAULT_COMPANY_ID)
//            .lastModified(DEFAULT_LAST_MODIFIED)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return promotion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotion createUpdatedEntity(EntityManager em) {
        Promotion promotion = new Promotion()
            .promotionFor(UPDATED_PROMOTION_FOR)
            .promotedFrom(UPDATED_PROMOTED_FROM)
            .promotedTo(UPDATED_PROMOTED_TO)
            .promotiedDate(UPDATED_PROMOTIED_DATE)
            .branchId(UPDATED_BRANCH_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return promotion;
    }

    @BeforeEach
    public void initTest() {
        promotion = createEntity(em);
    }

    @Test
    @Transactional
    void createPromotion() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();
        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);
        restPromotionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isCreated());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeCreate + 1);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getPromotionFor()).isEqualTo(DEFAULT_PROMOTION_FOR);
        assertThat(testPromotion.getPromotedFrom()).isEqualTo(DEFAULT_PROMOTED_FROM);
        assertThat(testPromotion.getPromotedTo()).isEqualTo(DEFAULT_PROMOTED_TO);
        assertThat(testPromotion.getPromotiedDate()).isEqualTo(DEFAULT_PROMOTIED_DATE);
        assertThat(testPromotion.getBranchId()).isEqualTo(DEFAULT_BRANCH_ID);
        assertThat(testPromotion.getDepartmentId()).isEqualTo(DEFAULT_DEPARTMENT_ID);
        assertThat(testPromotion.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPromotion.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testPromotion.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPromotion.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPromotion.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPromotionWithExistingId() throws Exception {
        // Create the Promotion with an existing ID
        promotion.setId(1L);
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromotionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPromotions() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList
        restPromotionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
            .andExpect(jsonPath("$.[*].promotionFor").value(hasItem(DEFAULT_PROMOTION_FOR)))
            .andExpect(jsonPath("$.[*].promotedFrom").value(hasItem(DEFAULT_PROMOTED_FROM)))
            .andExpect(jsonPath("$.[*].promotedTo").value(hasItem(DEFAULT_PROMOTED_TO)))
            .andExpect(jsonPath("$.[*].promotiedDate").value(hasItem(DEFAULT_PROMOTIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get the promotion
        restPromotionMockMvc
            .perform(get(ENTITY_API_URL_ID, promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(promotion.getId().intValue()))
            .andExpect(jsonPath("$.promotionFor").value(DEFAULT_PROMOTION_FOR))
            .andExpect(jsonPath("$.promotedFrom").value(DEFAULT_PROMOTED_FROM))
            .andExpect(jsonPath("$.promotedTo").value(DEFAULT_PROMOTED_TO))
            .andExpect(jsonPath("$.promotiedDate").value(DEFAULT_PROMOTIED_DATE.toString()))
            .andExpect(jsonPath("$.branchId").value(DEFAULT_BRANCH_ID.intValue()))
            .andExpect(jsonPath("$.departmentId").value(DEFAULT_DEPARTMENT_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPromotionsByIdFiltering() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        Long id = promotion.getId();

        defaultPromotionShouldBeFound("id.equals=" + id);
        defaultPromotionShouldNotBeFound("id.notEquals=" + id);

        defaultPromotionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPromotionShouldNotBeFound("id.greaterThan=" + id);

        defaultPromotionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPromotionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotionForIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionFor equals to DEFAULT_PROMOTION_FOR
        defaultPromotionShouldBeFound("promotionFor.equals=" + DEFAULT_PROMOTION_FOR);

        // Get all the promotionList where promotionFor equals to UPDATED_PROMOTION_FOR
        defaultPromotionShouldNotBeFound("promotionFor.equals=" + UPDATED_PROMOTION_FOR);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotionForIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionFor in DEFAULT_PROMOTION_FOR or UPDATED_PROMOTION_FOR
        defaultPromotionShouldBeFound("promotionFor.in=" + DEFAULT_PROMOTION_FOR + "," + UPDATED_PROMOTION_FOR);

        // Get all the promotionList where promotionFor equals to UPDATED_PROMOTION_FOR
        defaultPromotionShouldNotBeFound("promotionFor.in=" + UPDATED_PROMOTION_FOR);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotionForIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionFor is not null
        defaultPromotionShouldBeFound("promotionFor.specified=true");

        // Get all the promotionList where promotionFor is null
        defaultPromotionShouldNotBeFound("promotionFor.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotionForContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionFor contains DEFAULT_PROMOTION_FOR
        defaultPromotionShouldBeFound("promotionFor.contains=" + DEFAULT_PROMOTION_FOR);

        // Get all the promotionList where promotionFor contains UPDATED_PROMOTION_FOR
        defaultPromotionShouldNotBeFound("promotionFor.contains=" + UPDATED_PROMOTION_FOR);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotionForNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotionFor does not contain DEFAULT_PROMOTION_FOR
        defaultPromotionShouldNotBeFound("promotionFor.doesNotContain=" + DEFAULT_PROMOTION_FOR);

        // Get all the promotionList where promotionFor does not contain UPDATED_PROMOTION_FOR
        defaultPromotionShouldBeFound("promotionFor.doesNotContain=" + UPDATED_PROMOTION_FOR);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedFromIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedFrom equals to DEFAULT_PROMOTED_FROM
        defaultPromotionShouldBeFound("promotedFrom.equals=" + DEFAULT_PROMOTED_FROM);

        // Get all the promotionList where promotedFrom equals to UPDATED_PROMOTED_FROM
        defaultPromotionShouldNotBeFound("promotedFrom.equals=" + UPDATED_PROMOTED_FROM);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedFromIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedFrom in DEFAULT_PROMOTED_FROM or UPDATED_PROMOTED_FROM
        defaultPromotionShouldBeFound("promotedFrom.in=" + DEFAULT_PROMOTED_FROM + "," + UPDATED_PROMOTED_FROM);

        // Get all the promotionList where promotedFrom equals to UPDATED_PROMOTED_FROM
        defaultPromotionShouldNotBeFound("promotedFrom.in=" + UPDATED_PROMOTED_FROM);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedFrom is not null
        defaultPromotionShouldBeFound("promotedFrom.specified=true");

        // Get all the promotionList where promotedFrom is null
        defaultPromotionShouldNotBeFound("promotedFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedFromContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedFrom contains DEFAULT_PROMOTED_FROM
        defaultPromotionShouldBeFound("promotedFrom.contains=" + DEFAULT_PROMOTED_FROM);

        // Get all the promotionList where promotedFrom contains UPDATED_PROMOTED_FROM
        defaultPromotionShouldNotBeFound("promotedFrom.contains=" + UPDATED_PROMOTED_FROM);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedFromNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedFrom does not contain DEFAULT_PROMOTED_FROM
        defaultPromotionShouldNotBeFound("promotedFrom.doesNotContain=" + DEFAULT_PROMOTED_FROM);

        // Get all the promotionList where promotedFrom does not contain UPDATED_PROMOTED_FROM
        defaultPromotionShouldBeFound("promotedFrom.doesNotContain=" + UPDATED_PROMOTED_FROM);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedToIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedTo equals to DEFAULT_PROMOTED_TO
        defaultPromotionShouldBeFound("promotedTo.equals=" + DEFAULT_PROMOTED_TO);

        // Get all the promotionList where promotedTo equals to UPDATED_PROMOTED_TO
        defaultPromotionShouldNotBeFound("promotedTo.equals=" + UPDATED_PROMOTED_TO);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedToIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedTo in DEFAULT_PROMOTED_TO or UPDATED_PROMOTED_TO
        defaultPromotionShouldBeFound("promotedTo.in=" + DEFAULT_PROMOTED_TO + "," + UPDATED_PROMOTED_TO);

        // Get all the promotionList where promotedTo equals to UPDATED_PROMOTED_TO
        defaultPromotionShouldNotBeFound("promotedTo.in=" + UPDATED_PROMOTED_TO);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedToIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedTo is not null
        defaultPromotionShouldBeFound("promotedTo.specified=true");

        // Get all the promotionList where promotedTo is null
        defaultPromotionShouldNotBeFound("promotedTo.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedToContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedTo contains DEFAULT_PROMOTED_TO
        defaultPromotionShouldBeFound("promotedTo.contains=" + DEFAULT_PROMOTED_TO);

        // Get all the promotionList where promotedTo contains UPDATED_PROMOTED_TO
        defaultPromotionShouldNotBeFound("promotedTo.contains=" + UPDATED_PROMOTED_TO);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotedToNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotedTo does not contain DEFAULT_PROMOTED_TO
        defaultPromotionShouldNotBeFound("promotedTo.doesNotContain=" + DEFAULT_PROMOTED_TO);

        // Get all the promotionList where promotedTo does not contain UPDATED_PROMOTED_TO
        defaultPromotionShouldBeFound("promotedTo.doesNotContain=" + UPDATED_PROMOTED_TO);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotiedDate equals to DEFAULT_PROMOTIED_DATE
        defaultPromotionShouldBeFound("promotiedDate.equals=" + DEFAULT_PROMOTIED_DATE);

        // Get all the promotionList where promotiedDate equals to UPDATED_PROMOTIED_DATE
        defaultPromotionShouldNotBeFound("promotiedDate.equals=" + UPDATED_PROMOTIED_DATE);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotiedDate in DEFAULT_PROMOTIED_DATE or UPDATED_PROMOTIED_DATE
        defaultPromotionShouldBeFound("promotiedDate.in=" + DEFAULT_PROMOTIED_DATE + "," + UPDATED_PROMOTIED_DATE);

        // Get all the promotionList where promotiedDate equals to UPDATED_PROMOTIED_DATE
        defaultPromotionShouldNotBeFound("promotiedDate.in=" + UPDATED_PROMOTIED_DATE);
    }

    @Test
    @Transactional
    void getAllPromotionsByPromotiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where promotiedDate is not null
        defaultPromotionShouldBeFound("promotiedDate.specified=true");

        // Get all the promotionList where promotiedDate is null
        defaultPromotionShouldNotBeFound("promotiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId equals to DEFAULT_BRANCH_ID
        defaultPromotionShouldBeFound("branchId.equals=" + DEFAULT_BRANCH_ID);

        // Get all the promotionList where branchId equals to UPDATED_BRANCH_ID
        defaultPromotionShouldNotBeFound("branchId.equals=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId in DEFAULT_BRANCH_ID or UPDATED_BRANCH_ID
        defaultPromotionShouldBeFound("branchId.in=" + DEFAULT_BRANCH_ID + "," + UPDATED_BRANCH_ID);

        // Get all the promotionList where branchId equals to UPDATED_BRANCH_ID
        defaultPromotionShouldNotBeFound("branchId.in=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId is not null
        defaultPromotionShouldBeFound("branchId.specified=true");

        // Get all the promotionList where branchId is null
        defaultPromotionShouldNotBeFound("branchId.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId is greater than or equal to DEFAULT_BRANCH_ID
        defaultPromotionShouldBeFound("branchId.greaterThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the promotionList where branchId is greater than or equal to UPDATED_BRANCH_ID
        defaultPromotionShouldNotBeFound("branchId.greaterThanOrEqual=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId is less than or equal to DEFAULT_BRANCH_ID
        defaultPromotionShouldBeFound("branchId.lessThanOrEqual=" + DEFAULT_BRANCH_ID);

        // Get all the promotionList where branchId is less than or equal to SMALLER_BRANCH_ID
        defaultPromotionShouldNotBeFound("branchId.lessThanOrEqual=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId is less than DEFAULT_BRANCH_ID
        defaultPromotionShouldNotBeFound("branchId.lessThan=" + DEFAULT_BRANCH_ID);

        // Get all the promotionList where branchId is less than UPDATED_BRANCH_ID
        defaultPromotionShouldBeFound("branchId.lessThan=" + UPDATED_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByBranchIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where branchId is greater than DEFAULT_BRANCH_ID
        defaultPromotionShouldNotBeFound("branchId.greaterThan=" + DEFAULT_BRANCH_ID);

        // Get all the promotionList where branchId is greater than SMALLER_BRANCH_ID
        defaultPromotionShouldBeFound("branchId.greaterThan=" + SMALLER_BRANCH_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId equals to DEFAULT_DEPARTMENT_ID
        defaultPromotionShouldBeFound("departmentId.equals=" + DEFAULT_DEPARTMENT_ID);

        // Get all the promotionList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultPromotionShouldNotBeFound("departmentId.equals=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId in DEFAULT_DEPARTMENT_ID or UPDATED_DEPARTMENT_ID
        defaultPromotionShouldBeFound("departmentId.in=" + DEFAULT_DEPARTMENT_ID + "," + UPDATED_DEPARTMENT_ID);

        // Get all the promotionList where departmentId equals to UPDATED_DEPARTMENT_ID
        defaultPromotionShouldNotBeFound("departmentId.in=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId is not null
        defaultPromotionShouldBeFound("departmentId.specified=true");

        // Get all the promotionList where departmentId is null
        defaultPromotionShouldNotBeFound("departmentId.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId is greater than or equal to DEFAULT_DEPARTMENT_ID
        defaultPromotionShouldBeFound("departmentId.greaterThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the promotionList where departmentId is greater than or equal to UPDATED_DEPARTMENT_ID
        defaultPromotionShouldNotBeFound("departmentId.greaterThanOrEqual=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId is less than or equal to DEFAULT_DEPARTMENT_ID
        defaultPromotionShouldBeFound("departmentId.lessThanOrEqual=" + DEFAULT_DEPARTMENT_ID);

        // Get all the promotionList where departmentId is less than or equal to SMALLER_DEPARTMENT_ID
        defaultPromotionShouldNotBeFound("departmentId.lessThanOrEqual=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId is less than DEFAULT_DEPARTMENT_ID
        defaultPromotionShouldNotBeFound("departmentId.lessThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the promotionList where departmentId is less than UPDATED_DEPARTMENT_ID
        defaultPromotionShouldBeFound("departmentId.lessThan=" + UPDATED_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByDepartmentIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where departmentId is greater than DEFAULT_DEPARTMENT_ID
        defaultPromotionShouldNotBeFound("departmentId.greaterThan=" + DEFAULT_DEPARTMENT_ID);

        // Get all the promotionList where departmentId is greater than SMALLER_DEPARTMENT_ID
        defaultPromotionShouldBeFound("departmentId.greaterThan=" + SMALLER_DEPARTMENT_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where status equals to DEFAULT_STATUS
        defaultPromotionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the promotionList where status equals to UPDATED_STATUS
        defaultPromotionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPromotionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPromotionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the promotionList where status equals to UPDATED_STATUS
        defaultPromotionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPromotionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where status is not null
        defaultPromotionShouldBeFound("status.specified=true");

        // Get all the promotionList where status is null
        defaultPromotionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where status contains DEFAULT_STATUS
        defaultPromotionShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the promotionList where status contains UPDATED_STATUS
        defaultPromotionShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPromotionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where status does not contain DEFAULT_STATUS
        defaultPromotionShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the promotionList where status does not contain UPDATED_STATUS
        defaultPromotionShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultPromotionShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the promotionList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPromotionShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultPromotionShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the promotionList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPromotionShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId is not null
        defaultPromotionShouldBeFound("employeeId.specified=true");

        // Get all the promotionList where employeeId is null
        defaultPromotionShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultPromotionShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the promotionList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultPromotionShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultPromotionShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the promotionList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultPromotionShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultPromotionShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the promotionList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultPromotionShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultPromotionShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the promotionList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultPromotionShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId equals to DEFAULT_COMPANY_ID
        defaultPromotionShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the promotionList where companyId equals to UPDATED_COMPANY_ID
        defaultPromotionShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultPromotionShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the promotionList where companyId equals to UPDATED_COMPANY_ID
        defaultPromotionShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId is not null
        defaultPromotionShouldBeFound("companyId.specified=true");

        // Get all the promotionList where companyId is null
        defaultPromotionShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultPromotionShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the promotionList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultPromotionShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultPromotionShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the promotionList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultPromotionShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId is less than DEFAULT_COMPANY_ID
        defaultPromotionShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the promotionList where companyId is less than UPDATED_COMPANY_ID
        defaultPromotionShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where companyId is greater than DEFAULT_COMPANY_ID
        defaultPromotionShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the promotionList where companyId is greater than SMALLER_COMPANY_ID
        defaultPromotionShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPromotionShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the promotionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPromotionShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPromotionShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the promotionList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPromotionShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModified is not null
        defaultPromotionShouldBeFound("lastModified.specified=true");

        // Get all the promotionList where lastModified is null
        defaultPromotionShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPromotionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the promotionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPromotionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPromotionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the promotionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPromotionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModifiedBy is not null
        defaultPromotionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the promotionList where lastModifiedBy is null
        defaultPromotionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPromotionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the promotionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPromotionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPromotionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPromotionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the promotionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPromotionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPromotionShouldBeFound(String filter) throws Exception {
        restPromotionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
            .andExpect(jsonPath("$.[*].promotionFor").value(hasItem(DEFAULT_PROMOTION_FOR)))
            .andExpect(jsonPath("$.[*].promotedFrom").value(hasItem(DEFAULT_PROMOTED_FROM)))
            .andExpect(jsonPath("$.[*].promotedTo").value(hasItem(DEFAULT_PROMOTED_TO)))
            .andExpect(jsonPath("$.[*].promotiedDate").value(hasItem(DEFAULT_PROMOTIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].branchId").value(hasItem(DEFAULT_BRANCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].departmentId").value(hasItem(DEFAULT_DEPARTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPromotionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPromotionShouldNotBeFound(String filter) throws Exception {
        restPromotionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPromotionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPromotion() throws Exception {
        // Get the promotion
        restPromotionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion
        Promotion updatedPromotion = promotionRepository.findById(promotion.getId()).get();
        // Disconnect from session so that the updates on updatedPromotion are not directly saved in db
        em.detach(updatedPromotion);
        updatedPromotion
            .promotionFor(UPDATED_PROMOTION_FOR)
            .promotedFrom(UPDATED_PROMOTED_FROM)
            .promotedTo(UPDATED_PROMOTED_TO)
            .promotiedDate(UPDATED_PROMOTIED_DATE)
            .branchId(UPDATED_BRANCH_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PromotionDTO promotionDTO = promotionMapper.toDto(updatedPromotion);

        restPromotionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, promotionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(promotionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getPromotionFor()).isEqualTo(UPDATED_PROMOTION_FOR);
        assertThat(testPromotion.getPromotedFrom()).isEqualTo(UPDATED_PROMOTED_FROM);
        assertThat(testPromotion.getPromotedTo()).isEqualTo(UPDATED_PROMOTED_TO);
        assertThat(testPromotion.getPromotiedDate()).isEqualTo(UPDATED_PROMOTIED_DATE);
        assertThat(testPromotion.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testPromotion.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testPromotion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPromotion.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPromotion.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPromotion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPromotion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();
        promotion.setId(count.incrementAndGet());

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromotionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, promotionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(promotionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();
        promotion.setId(count.incrementAndGet());

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromotionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(promotionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();
        promotion.setId(count.incrementAndGet());

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromotionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePromotionWithPatch() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion using partial update
        Promotion partialUpdatedPromotion = new Promotion();
        partialUpdatedPromotion.setId(promotion.getId());

        partialUpdatedPromotion
            .promotedFrom(UPDATED_PROMOTED_FROM)
            .promotedTo(UPDATED_PROMOTED_TO)
            .promotiedDate(UPDATED_PROMOTIED_DATE)
            .branchId(UPDATED_BRANCH_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPromotionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPromotion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPromotion))
            )
            .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getPromotionFor()).isEqualTo(DEFAULT_PROMOTION_FOR);
        assertThat(testPromotion.getPromotedFrom()).isEqualTo(UPDATED_PROMOTED_FROM);
        assertThat(testPromotion.getPromotedTo()).isEqualTo(UPDATED_PROMOTED_TO);
        assertThat(testPromotion.getPromotiedDate()).isEqualTo(UPDATED_PROMOTIED_DATE);
        assertThat(testPromotion.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testPromotion.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testPromotion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPromotion.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPromotion.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPromotion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPromotion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePromotionWithPatch() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion using partial update
        Promotion partialUpdatedPromotion = new Promotion();
        partialUpdatedPromotion.setId(promotion.getId());

        partialUpdatedPromotion
            .promotionFor(UPDATED_PROMOTION_FOR)
            .promotedFrom(UPDATED_PROMOTED_FROM)
            .promotedTo(UPDATED_PROMOTED_TO)
            .promotiedDate(UPDATED_PROMOTIED_DATE)
            .branchId(UPDATED_BRANCH_ID)
            .departmentId(UPDATED_DEPARTMENT_ID)
            .status(UPDATED_STATUS)
            .employeeId(UPDATED_EMPLOYEE_ID);
//            .companyId(UPDATED_COMPANY_ID)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPromotionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPromotion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPromotion))
            )
            .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getPromotionFor()).isEqualTo(UPDATED_PROMOTION_FOR);
        assertThat(testPromotion.getPromotedFrom()).isEqualTo(UPDATED_PROMOTED_FROM);
        assertThat(testPromotion.getPromotedTo()).isEqualTo(UPDATED_PROMOTED_TO);
        assertThat(testPromotion.getPromotiedDate()).isEqualTo(UPDATED_PROMOTIED_DATE);
        assertThat(testPromotion.getBranchId()).isEqualTo(UPDATED_BRANCH_ID);
        assertThat(testPromotion.getDepartmentId()).isEqualTo(UPDATED_DEPARTMENT_ID);
        assertThat(testPromotion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPromotion.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testPromotion.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPromotion.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPromotion.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();
        promotion.setId(count.incrementAndGet());

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromotionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, promotionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(promotionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();
        promotion.setId(count.incrementAndGet());

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromotionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(promotionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();
        promotion.setId(count.incrementAndGet());

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromotionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(promotionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        int databaseSizeBeforeDelete = promotionRepository.findAll().size();

        // Delete the promotion
        restPromotionMockMvc
            .perform(delete(ENTITY_API_URL_ID, promotion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
