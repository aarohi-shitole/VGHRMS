package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Company;
import com.techvg.hrms.repository.CompanyRepository;
import com.techvg.hrms.service.criteria.CompanyCriteria;
import com.techvg.hrms.service.dto.CompanyDTO;
import com.techvg.hrms.service.mapper.CompanyMapper;
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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_REG_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REG_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LEAVE_SETTING_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEAVE_SETTING_LEVEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyName(DEFAULT_COMPANY_NAME)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .postalCode(DEFAULT_POSTAL_CODE)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .websiteUrl(DEFAULT_WEBSITE_URL)
            .fax(DEFAULT_FAX)
            .regNumber(DEFAULT_REG_NUMBER)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .leaveSettingLevel(DEFAULT_LEAVE_SETTING_LEVEL);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .companyName(UPDATED_COMPANY_NAME)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .postalCode(UPDATED_POSTAL_CODE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .fax(UPDATED_FAX)
            .regNumber(UPDATED_REG_NUMBER)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .leaveSettingLevel(UPDATED_LEAVE_SETTING_LEVEL);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testCompany.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCompany.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testCompany.getWebsiteUrl()).isEqualTo(DEFAULT_WEBSITE_URL);
        assertThat(testCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCompany.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testCompany.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompany.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCompany.getLeaveSettingLevel()).isEqualTo(DEFAULT_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void createCompanyWithExistingId() throws Exception {
        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteUrl").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].leaveSettingLevel").value(hasItem(DEFAULT_LEAVE_SETTING_LEVEL)));
    }

    @Test
    @Transactional
    void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.websiteUrl").value(DEFAULT_WEBSITE_URL))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.regNumber").value(DEFAULT_REG_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.leaveSettingLevel").value(DEFAULT_LEAVE_SETTING_LEVEL));
    }

    @Test
    @Transactional
    void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the companyList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName is not null
        defaultCompanyShouldBeFound("companyName.specified=true");

        // Get all the companyList where companyName is null
        defaultCompanyShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName contains DEFAULT_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName contains UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName does not contain UPDATED_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactPerson equals to DEFAULT_CONTACT_PERSON
        defaultCompanyShouldBeFound("contactPerson.equals=" + DEFAULT_CONTACT_PERSON);

        // Get all the companyList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultCompanyShouldNotBeFound("contactPerson.equals=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactPersonIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactPerson in DEFAULT_CONTACT_PERSON or UPDATED_CONTACT_PERSON
        defaultCompanyShouldBeFound("contactPerson.in=" + DEFAULT_CONTACT_PERSON + "," + UPDATED_CONTACT_PERSON);

        // Get all the companyList where contactPerson equals to UPDATED_CONTACT_PERSON
        defaultCompanyShouldNotBeFound("contactPerson.in=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactPerson is not null
        defaultCompanyShouldBeFound("contactPerson.specified=true");

        // Get all the companyList where contactPerson is null
        defaultCompanyShouldNotBeFound("contactPerson.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByContactPersonContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactPerson contains DEFAULT_CONTACT_PERSON
        defaultCompanyShouldBeFound("contactPerson.contains=" + DEFAULT_CONTACT_PERSON);

        // Get all the companyList where contactPerson contains UPDATED_CONTACT_PERSON
        defaultCompanyShouldNotBeFound("contactPerson.contains=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactPersonNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactPerson does not contain DEFAULT_CONTACT_PERSON
        defaultCompanyShouldNotBeFound("contactPerson.doesNotContain=" + DEFAULT_CONTACT_PERSON);

        // Get all the companyList where contactPerson does not contain UPDATED_CONTACT_PERSON
        defaultCompanyShouldBeFound("contactPerson.doesNotContain=" + UPDATED_CONTACT_PERSON);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultCompanyShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the companyList where postalCode equals to UPDATED_POSTAL_CODE
        defaultCompanyShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultCompanyShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the companyList where postalCode equals to UPDATED_POSTAL_CODE
        defaultCompanyShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where postalCode is not null
        defaultCompanyShouldBeFound("postalCode.specified=true");

        // Get all the companyList where postalCode is null
        defaultCompanyShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where postalCode contains DEFAULT_POSTAL_CODE
        defaultCompanyShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the companyList where postalCode contains UPDATED_POSTAL_CODE
        defaultCompanyShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultCompanyShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the companyList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultCompanyShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email equals to DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email is not null
        defaultCompanyShouldBeFound("email.specified=true");

        // Get all the companyList where email is null
        defaultCompanyShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email contains DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyList where email contains UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email does not contain DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyList where email does not contain UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultCompanyShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCompanyShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultCompanyShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the companyList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCompanyShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phoneNumber is not null
        defaultCompanyShouldBeFound("phoneNumber.specified=true");

        // Get all the companyList where phoneNumber is null
        defaultCompanyShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultCompanyShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultCompanyShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultCompanyShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the companyList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultCompanyShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByMobileNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobileNumber equals to DEFAULT_MOBILE_NUMBER
        defaultCompanyShouldBeFound("mobileNumber.equals=" + DEFAULT_MOBILE_NUMBER);

        // Get all the companyList where mobileNumber equals to UPDATED_MOBILE_NUMBER
        defaultCompanyShouldNotBeFound("mobileNumber.equals=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByMobileNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobileNumber in DEFAULT_MOBILE_NUMBER or UPDATED_MOBILE_NUMBER
        defaultCompanyShouldBeFound("mobileNumber.in=" + DEFAULT_MOBILE_NUMBER + "," + UPDATED_MOBILE_NUMBER);

        // Get all the companyList where mobileNumber equals to UPDATED_MOBILE_NUMBER
        defaultCompanyShouldNotBeFound("mobileNumber.in=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByMobileNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobileNumber is not null
        defaultCompanyShouldBeFound("mobileNumber.specified=true");

        // Get all the companyList where mobileNumber is null
        defaultCompanyShouldNotBeFound("mobileNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByMobileNumberContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobileNumber contains DEFAULT_MOBILE_NUMBER
        defaultCompanyShouldBeFound("mobileNumber.contains=" + DEFAULT_MOBILE_NUMBER);

        // Get all the companyList where mobileNumber contains UPDATED_MOBILE_NUMBER
        defaultCompanyShouldNotBeFound("mobileNumber.contains=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByMobileNumberNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobileNumber does not contain DEFAULT_MOBILE_NUMBER
        defaultCompanyShouldNotBeFound("mobileNumber.doesNotContain=" + DEFAULT_MOBILE_NUMBER);

        // Get all the companyList where mobileNumber does not contain UPDATED_MOBILE_NUMBER
        defaultCompanyShouldBeFound("mobileNumber.doesNotContain=" + UPDATED_MOBILE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where websiteUrl equals to DEFAULT_WEBSITE_URL
        defaultCompanyShouldBeFound("websiteUrl.equals=" + DEFAULT_WEBSITE_URL);

        // Get all the companyList where websiteUrl equals to UPDATED_WEBSITE_URL
        defaultCompanyShouldNotBeFound("websiteUrl.equals=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteUrlIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where websiteUrl in DEFAULT_WEBSITE_URL or UPDATED_WEBSITE_URL
        defaultCompanyShouldBeFound("websiteUrl.in=" + DEFAULT_WEBSITE_URL + "," + UPDATED_WEBSITE_URL);

        // Get all the companyList where websiteUrl equals to UPDATED_WEBSITE_URL
        defaultCompanyShouldNotBeFound("websiteUrl.in=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where websiteUrl is not null
        defaultCompanyShouldBeFound("websiteUrl.specified=true");

        // Get all the companyList where websiteUrl is null
        defaultCompanyShouldNotBeFound("websiteUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteUrlContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where websiteUrl contains DEFAULT_WEBSITE_URL
        defaultCompanyShouldBeFound("websiteUrl.contains=" + DEFAULT_WEBSITE_URL);

        // Get all the companyList where websiteUrl contains UPDATED_WEBSITE_URL
        defaultCompanyShouldNotBeFound("websiteUrl.contains=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteUrlNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where websiteUrl does not contain DEFAULT_WEBSITE_URL
        defaultCompanyShouldNotBeFound("websiteUrl.doesNotContain=" + DEFAULT_WEBSITE_URL);

        // Get all the companyList where websiteUrl does not contain UPDATED_WEBSITE_URL
        defaultCompanyShouldBeFound("websiteUrl.doesNotContain=" + UPDATED_WEBSITE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where fax equals to DEFAULT_FAX
        defaultCompanyShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the companyList where fax equals to UPDATED_FAX
        defaultCompanyShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllCompaniesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultCompanyShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the companyList where fax equals to UPDATED_FAX
        defaultCompanyShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllCompaniesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where fax is not null
        defaultCompanyShouldBeFound("fax.specified=true");

        // Get all the companyList where fax is null
        defaultCompanyShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByFaxContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where fax contains DEFAULT_FAX
        defaultCompanyShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the companyList where fax contains UPDATED_FAX
        defaultCompanyShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllCompaniesByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where fax does not contain DEFAULT_FAX
        defaultCompanyShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the companyList where fax does not contain UPDATED_FAX
        defaultCompanyShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllCompaniesByRegNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where regNumber equals to DEFAULT_REG_NUMBER
        defaultCompanyShouldBeFound("regNumber.equals=" + DEFAULT_REG_NUMBER);

        // Get all the companyList where regNumber equals to UPDATED_REG_NUMBER
        defaultCompanyShouldNotBeFound("regNumber.equals=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByRegNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where regNumber in DEFAULT_REG_NUMBER or UPDATED_REG_NUMBER
        defaultCompanyShouldBeFound("regNumber.in=" + DEFAULT_REG_NUMBER + "," + UPDATED_REG_NUMBER);

        // Get all the companyList where regNumber equals to UPDATED_REG_NUMBER
        defaultCompanyShouldNotBeFound("regNumber.in=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByRegNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where regNumber is not null
        defaultCompanyShouldBeFound("regNumber.specified=true");

        // Get all the companyList where regNumber is null
        defaultCompanyShouldNotBeFound("regNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByRegNumberContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where regNumber contains DEFAULT_REG_NUMBER
        defaultCompanyShouldBeFound("regNumber.contains=" + DEFAULT_REG_NUMBER);

        // Get all the companyList where regNumber contains UPDATED_REG_NUMBER
        defaultCompanyShouldNotBeFound("regNumber.contains=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByRegNumberNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where regNumber does not contain DEFAULT_REG_NUMBER
        defaultCompanyShouldNotBeFound("regNumber.doesNotContain=" + DEFAULT_REG_NUMBER);

        // Get all the companyList where regNumber does not contain UPDATED_REG_NUMBER
        defaultCompanyShouldBeFound("regNumber.doesNotContain=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where status equals to DEFAULT_STATUS
        defaultCompanyShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the companyList where status equals to UPDATED_STATUS
        defaultCompanyShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCompaniesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCompanyShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the companyList where status equals to UPDATED_STATUS
        defaultCompanyShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCompaniesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where status is not null
        defaultCompanyShouldBeFound("status.specified=true");

        // Get all the companyList where status is null
        defaultCompanyShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByStatusContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where status contains DEFAULT_STATUS
        defaultCompanyShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the companyList where status contains UPDATED_STATUS
        defaultCompanyShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCompaniesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where status does not contain DEFAULT_STATUS
        defaultCompanyShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the companyList where status does not contain UPDATED_STATUS
        defaultCompanyShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the companyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified is not null
        defaultCompanyShouldBeFound("lastModified.specified=true");

        // Get all the companyList where lastModified is null
        defaultCompanyShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy is not null
        defaultCompanyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the companyList where lastModifiedBy is null
        defaultCompanyShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLeaveSettingLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where leaveSettingLevel equals to DEFAULT_LEAVE_SETTING_LEVEL
        defaultCompanyShouldBeFound("leaveSettingLevel.equals=" + DEFAULT_LEAVE_SETTING_LEVEL);

        // Get all the companyList where leaveSettingLevel equals to UPDATED_LEAVE_SETTING_LEVEL
        defaultCompanyShouldNotBeFound("leaveSettingLevel.equals=" + UPDATED_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void getAllCompaniesByLeaveSettingLevelIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where leaveSettingLevel in DEFAULT_LEAVE_SETTING_LEVEL or UPDATED_LEAVE_SETTING_LEVEL
        defaultCompanyShouldBeFound("leaveSettingLevel.in=" + DEFAULT_LEAVE_SETTING_LEVEL + "," + UPDATED_LEAVE_SETTING_LEVEL);

        // Get all the companyList where leaveSettingLevel equals to UPDATED_LEAVE_SETTING_LEVEL
        defaultCompanyShouldNotBeFound("leaveSettingLevel.in=" + UPDATED_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void getAllCompaniesByLeaveSettingLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where leaveSettingLevel is not null
        defaultCompanyShouldBeFound("leaveSettingLevel.specified=true");

        // Get all the companyList where leaveSettingLevel is null
        defaultCompanyShouldNotBeFound("leaveSettingLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByLeaveSettingLevelContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where leaveSettingLevel contains DEFAULT_LEAVE_SETTING_LEVEL
        defaultCompanyShouldBeFound("leaveSettingLevel.contains=" + DEFAULT_LEAVE_SETTING_LEVEL);

        // Get all the companyList where leaveSettingLevel contains UPDATED_LEAVE_SETTING_LEVEL
        defaultCompanyShouldNotBeFound("leaveSettingLevel.contains=" + UPDATED_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void getAllCompaniesByLeaveSettingLevelNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where leaveSettingLevel does not contain DEFAULT_LEAVE_SETTING_LEVEL
        defaultCompanyShouldNotBeFound("leaveSettingLevel.doesNotContain=" + DEFAULT_LEAVE_SETTING_LEVEL);

        // Get all the companyList where leaveSettingLevel does not contain UPDATED_LEAVE_SETTING_LEVEL
        defaultCompanyShouldBeFound("leaveSettingLevel.doesNotContain=" + UPDATED_LEAVE_SETTING_LEVEL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].websiteUrl").value(hasItem(DEFAULT_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].leaveSettingLevel").value(hasItem(DEFAULT_LEAVE_SETTING_LEVEL)));

        // Check, that the count call also returns 1
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .postalCode(UPDATED_POSTAL_CODE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .fax(UPDATED_FAX)
            .regNumber(UPDATED_REG_NUMBER)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .leaveSettingLevel(UPDATED_LEAVE_SETTING_LEVEL);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testCompany.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCompany.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testCompany.getWebsiteUrl()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCompany.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testCompany.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompany.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCompany.getLeaveSettingLevel()).isEqualTo(UPDATED_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void putNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .contactPerson(UPDATED_CONTACT_PERSON)
            .postalCode(UPDATED_POSTAL_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .leaveSettingLevel(UPDATED_LEAVE_SETTING_LEVEL);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testCompany.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCompany.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testCompany.getWebsiteUrl()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testCompany.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCompany.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testCompany.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompany.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCompany.getLeaveSettingLevel()).isEqualTo(UPDATED_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void fullUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .postalCode(UPDATED_POSTAL_CODE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .websiteUrl(UPDATED_WEBSITE_URL)
            .fax(UPDATED_FAX)
            .regNumber(UPDATED_REG_NUMBER)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .leaveSettingLevel(UPDATED_LEAVE_SETTING_LEVEL);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testCompany.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCompany.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testCompany.getWebsiteUrl()).isEqualTo(UPDATED_WEBSITE_URL);
        assertThat(testCompany.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCompany.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testCompany.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompany.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCompany.getLeaveSettingLevel()).isEqualTo(UPDATED_LEAVE_SETTING_LEVEL);
    }

    @Test
    @Transactional
    void patchNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
