//package com.techvg.hrms.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.techvg.hrms.IntegrationTest;
//import com.techvg.hrms.domain.Contacts;
//import com.techvg.hrms.repository.ContactsRepository;
//import com.techvg.hrms.service.criteria.ContactsCriteria;
//import com.techvg.hrms.service.dto.ContactsDTO;
//import com.techvg.hrms.service.mapper.ContactsMapper;
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
// * Integration tests for the {@link ContactsResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ContactsResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CONTACT_PREF = "AAAAAAAAAA";
//    private static final String UPDATED_CONTACT_PREF = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CONTACT_TYPE = "AAAAAAAAAA";
//    private static final String UPDATED_CONTACT_TYPE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
//    private static final String UPDATED_CONTACT = "BBBBBBBBBB";
//
//    private static final String DEFAULT_REF_TABLE = "AAAAAAAAAA";
//    private static final String UPDATED_REF_TABLE = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_REF_TABLE_ID = 1L;
//    private static final Long UPDATED_REF_TABLE_ID = 2L;
//    private static final Long SMALLER_REF_TABLE_ID = 1L - 1L;
//
//    private static final Long DEFAULT_COMPANY_ID = 1L;
//    private static final Long UPDATED_COMPANY_ID = 2L;
//    private static final Long SMALLER_COMPANY_ID = 1L - 1L;
//
//    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_STATUS = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CONTACT_REFERENCE = "AAAAAAAAAA";
//    private static final String UPDATED_CONTACT_REFERENCE = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/contacts";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private ContactsRepository contactsRepository;
//
//    @Autowired
//    private ContactsMapper contactsMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restContactsMockMvc;
//
//    private Contacts contacts;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Contacts createEntity(EntityManager em) {
//        Contacts contacts = new Contacts()
//            .name(DEFAULT_NAME)
//            .contactPref(DEFAULT_CONTACT_PREF)
//            .contactType(DEFAULT_CONTACT_TYPE)
//            .contact(DEFAULT_CONTACT)
//            .refTable(DEFAULT_REF_TABLE)
//            .refTableId(DEFAULT_REF_TABLE_ID)
////            .companyId(DEFAULT_COMPANY_ID)
//            .status(DEFAULT_STATUS)
////            .lastModified(DEFAULT_LAST_MODIFIED)
////            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
//            .contactReference(DEFAULT_CONTACT_REFERENCE);
//        return contacts;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Contacts createUpdatedEntity(EntityManager em) {
//        Contacts contacts = new Contacts()
//            .name(UPDATED_NAME)
//            .contactPref(UPDATED_CONTACT_PREF)
//            .contactType(UPDATED_CONTACT_TYPE)
//            .contact(UPDATED_CONTACT)
//            .refTable(UPDATED_REF_TABLE)
//            .refTableId(UPDATED_REF_TABLE_ID)
////            .companyId(UPDATED_COMPANY_ID)
//            .status(UPDATED_STATUS)
////            .lastModified(UPDATED_LAST_MODIFIED)
////            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
//            .contactReference(UPDATED_CONTACT_REFERENCE);
//        return contacts;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        contacts = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createContacts() throws Exception {
//        int databaseSizeBeforeCreate = contactsRepository.findAll().size();
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//        restContactsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeCreate + 1);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testContacts.getContactPref()).isEqualTo(DEFAULT_CONTACT_PREF);
//        assertThat(testContacts.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
//        assertThat(testContacts.getContact()).isEqualTo(DEFAULT_CONTACT);
//        assertThat(testContacts.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
//        assertThat(testContacts.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
//        assertThat(testContacts.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
//        assertThat(testContacts.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testContacts.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
//        assertThat(testContacts.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testContacts.getContactReference()).isEqualTo(DEFAULT_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void createContactsWithExistingId() throws Exception {
//        // Create the Contacts with an existing ID
//        contacts.setId(1L);
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        int databaseSizeBeforeCreate = contactsRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restContactsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].contactPref").value(hasItem(DEFAULT_CONTACT_PREF)))
//            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE)))
//            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
//            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
//            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
//            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
//            .andExpect(jsonPath("$.[*].contactReference").value(hasItem(DEFAULT_CONTACT_REFERENCE)));
//    }
//
//    @Test
//    @Transactional
//    void getContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get the contacts
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL_ID, contacts.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(contacts.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.contactPref").value(DEFAULT_CONTACT_PREF))
//            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE))
//            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
//            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
//            .andExpect(jsonPath("$.refTableId").value(DEFAULT_REF_TABLE_ID.intValue()))
//            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
//            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
//            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
//            .andExpect(jsonPath("$.contactReference").value(DEFAULT_CONTACT_REFERENCE));
//    }
//
//    @Test
//    @Transactional
//    void getContactsByIdFiltering() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        Long id = contacts.getId();
//
//        defaultContactsShouldBeFound("id.equals=" + id);
//        defaultContactsShouldNotBeFound("id.notEquals=" + id);
//
//        defaultContactsShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultContactsShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultContactsShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultContactsShouldNotBeFound("id.lessThan=" + id);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where name equals to DEFAULT_NAME
//        defaultContactsShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the contactsList where name equals to UPDATED_NAME
//        defaultContactsShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultContactsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the contactsList where name equals to UPDATED_NAME
//        defaultContactsShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where name is not null
//        defaultContactsShouldBeFound("name.specified=true");
//
//        // Get all the contactsList where name is null
//        defaultContactsShouldNotBeFound("name.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByNameContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where name contains DEFAULT_NAME
//        defaultContactsShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the contactsList where name contains UPDATED_NAME
//        defaultContactsShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where name does not contain DEFAULT_NAME
//        defaultContactsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the contactsList where name does not contain UPDATED_NAME
//        defaultContactsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactPrefIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactPref equals to DEFAULT_CONTACT_PREF
//        defaultContactsShouldBeFound("contactPref.equals=" + DEFAULT_CONTACT_PREF);
//
//        // Get all the contactsList where contactPref equals to UPDATED_CONTACT_PREF
//        defaultContactsShouldNotBeFound("contactPref.equals=" + UPDATED_CONTACT_PREF);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactPrefIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactPref in DEFAULT_CONTACT_PREF or UPDATED_CONTACT_PREF
//        defaultContactsShouldBeFound("contactPref.in=" + DEFAULT_CONTACT_PREF + "," + UPDATED_CONTACT_PREF);
//
//        // Get all the contactsList where contactPref equals to UPDATED_CONTACT_PREF
//        defaultContactsShouldNotBeFound("contactPref.in=" + UPDATED_CONTACT_PREF);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactPrefIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactPref is not null
//        defaultContactsShouldBeFound("contactPref.specified=true");
//
//        // Get all the contactsList where contactPref is null
//        defaultContactsShouldNotBeFound("contactPref.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactPrefContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactPref contains DEFAULT_CONTACT_PREF
//        defaultContactsShouldBeFound("contactPref.contains=" + DEFAULT_CONTACT_PREF);
//
//        // Get all the contactsList where contactPref contains UPDATED_CONTACT_PREF
//        defaultContactsShouldNotBeFound("contactPref.contains=" + UPDATED_CONTACT_PREF);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactPrefNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactPref does not contain DEFAULT_CONTACT_PREF
//        defaultContactsShouldNotBeFound("contactPref.doesNotContain=" + DEFAULT_CONTACT_PREF);
//
//        // Get all the contactsList where contactPref does not contain UPDATED_CONTACT_PREF
//        defaultContactsShouldBeFound("contactPref.doesNotContain=" + UPDATED_CONTACT_PREF);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactTypeIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactType equals to DEFAULT_CONTACT_TYPE
//        defaultContactsShouldBeFound("contactType.equals=" + DEFAULT_CONTACT_TYPE);
//
//        // Get all the contactsList where contactType equals to UPDATED_CONTACT_TYPE
//        defaultContactsShouldNotBeFound("contactType.equals=" + UPDATED_CONTACT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactTypeIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactType in DEFAULT_CONTACT_TYPE or UPDATED_CONTACT_TYPE
//        defaultContactsShouldBeFound("contactType.in=" + DEFAULT_CONTACT_TYPE + "," + UPDATED_CONTACT_TYPE);
//
//        // Get all the contactsList where contactType equals to UPDATED_CONTACT_TYPE
//        defaultContactsShouldNotBeFound("contactType.in=" + UPDATED_CONTACT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactTypeIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactType is not null
//        defaultContactsShouldBeFound("contactType.specified=true");
//
//        // Get all the contactsList where contactType is null
//        defaultContactsShouldNotBeFound("contactType.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactTypeContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactType contains DEFAULT_CONTACT_TYPE
//        defaultContactsShouldBeFound("contactType.contains=" + DEFAULT_CONTACT_TYPE);
//
//        // Get all the contactsList where contactType contains UPDATED_CONTACT_TYPE
//        defaultContactsShouldNotBeFound("contactType.contains=" + UPDATED_CONTACT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactTypeNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactType does not contain DEFAULT_CONTACT_TYPE
//        defaultContactsShouldNotBeFound("contactType.doesNotContain=" + DEFAULT_CONTACT_TYPE);
//
//        // Get all the contactsList where contactType does not contain UPDATED_CONTACT_TYPE
//        defaultContactsShouldBeFound("contactType.doesNotContain=" + UPDATED_CONTACT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contact equals to DEFAULT_CONTACT
//        defaultContactsShouldBeFound("contact.equals=" + DEFAULT_CONTACT);
//
//        // Get all the contactsList where contact equals to UPDATED_CONTACT
//        defaultContactsShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
//        defaultContactsShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);
//
//        // Get all the contactsList where contact equals to UPDATED_CONTACT
//        defaultContactsShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contact is not null
//        defaultContactsShouldBeFound("contact.specified=true");
//
//        // Get all the contactsList where contact is null
//        defaultContactsShouldNotBeFound("contact.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contact contains DEFAULT_CONTACT
//        defaultContactsShouldBeFound("contact.contains=" + DEFAULT_CONTACT);
//
//        // Get all the contactsList where contact contains UPDATED_CONTACT
//        defaultContactsShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contact does not contain DEFAULT_CONTACT
//        defaultContactsShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);
//
//        // Get all the contactsList where contact does not contain UPDATED_CONTACT
//        defaultContactsShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTable equals to DEFAULT_REF_TABLE
//        defaultContactsShouldBeFound("refTable.equals=" + DEFAULT_REF_TABLE);
//
//        // Get all the contactsList where refTable equals to UPDATED_REF_TABLE
//        defaultContactsShouldNotBeFound("refTable.equals=" + UPDATED_REF_TABLE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTable in DEFAULT_REF_TABLE or UPDATED_REF_TABLE
//        defaultContactsShouldBeFound("refTable.in=" + DEFAULT_REF_TABLE + "," + UPDATED_REF_TABLE);
//
//        // Get all the contactsList where refTable equals to UPDATED_REF_TABLE
//        defaultContactsShouldNotBeFound("refTable.in=" + UPDATED_REF_TABLE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTable is not null
//        defaultContactsShouldBeFound("refTable.specified=true");
//
//        // Get all the contactsList where refTable is null
//        defaultContactsShouldNotBeFound("refTable.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTable contains DEFAULT_REF_TABLE
//        defaultContactsShouldBeFound("refTable.contains=" + DEFAULT_REF_TABLE);
//
//        // Get all the contactsList where refTable contains UPDATED_REF_TABLE
//        defaultContactsShouldNotBeFound("refTable.contains=" + UPDATED_REF_TABLE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTable does not contain DEFAULT_REF_TABLE
//        defaultContactsShouldNotBeFound("refTable.doesNotContain=" + DEFAULT_REF_TABLE);
//
//        // Get all the contactsList where refTable does not contain UPDATED_REF_TABLE
//        defaultContactsShouldBeFound("refTable.doesNotContain=" + UPDATED_REF_TABLE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId equals to DEFAULT_REF_TABLE_ID
//        defaultContactsShouldBeFound("refTableId.equals=" + DEFAULT_REF_TABLE_ID);
//
//        // Get all the contactsList where refTableId equals to UPDATED_REF_TABLE_ID
//        defaultContactsShouldNotBeFound("refTableId.equals=" + UPDATED_REF_TABLE_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId in DEFAULT_REF_TABLE_ID or UPDATED_REF_TABLE_ID
//        defaultContactsShouldBeFound("refTableId.in=" + DEFAULT_REF_TABLE_ID + "," + UPDATED_REF_TABLE_ID);
//
//        // Get all the contactsList where refTableId equals to UPDATED_REF_TABLE_ID
//        defaultContactsShouldNotBeFound("refTableId.in=" + UPDATED_REF_TABLE_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId is not null
//        defaultContactsShouldBeFound("refTableId.specified=true");
//
//        // Get all the contactsList where refTableId is null
//        defaultContactsShouldNotBeFound("refTableId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId is greater than or equal to DEFAULT_REF_TABLE_ID
//        defaultContactsShouldBeFound("refTableId.greaterThanOrEqual=" + DEFAULT_REF_TABLE_ID);
//
//        // Get all the contactsList where refTableId is greater than or equal to UPDATED_REF_TABLE_ID
//        defaultContactsShouldNotBeFound("refTableId.greaterThanOrEqual=" + UPDATED_REF_TABLE_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId is less than or equal to DEFAULT_REF_TABLE_ID
//        defaultContactsShouldBeFound("refTableId.lessThanOrEqual=" + DEFAULT_REF_TABLE_ID);
//
//        // Get all the contactsList where refTableId is less than or equal to SMALLER_REF_TABLE_ID
//        defaultContactsShouldNotBeFound("refTableId.lessThanOrEqual=" + SMALLER_REF_TABLE_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId is less than DEFAULT_REF_TABLE_ID
//        defaultContactsShouldNotBeFound("refTableId.lessThan=" + DEFAULT_REF_TABLE_ID);
//
//        // Get all the contactsList where refTableId is less than UPDATED_REF_TABLE_ID
//        defaultContactsShouldBeFound("refTableId.lessThan=" + UPDATED_REF_TABLE_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByRefTableIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where refTableId is greater than DEFAULT_REF_TABLE_ID
//        defaultContactsShouldNotBeFound("refTableId.greaterThan=" + DEFAULT_REF_TABLE_ID);
//
//        // Get all the contactsList where refTableId is greater than SMALLER_REF_TABLE_ID
//        defaultContactsShouldBeFound("refTableId.greaterThan=" + SMALLER_REF_TABLE_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId equals to DEFAULT_COMPANY_ID
//        defaultContactsShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);
//
//        // Get all the contactsList where companyId equals to UPDATED_COMPANY_ID
//        defaultContactsShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
//        defaultContactsShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);
//
//        // Get all the contactsList where companyId equals to UPDATED_COMPANY_ID
//        defaultContactsShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId is not null
//        defaultContactsShouldBeFound("companyId.specified=true");
//
//        // Get all the contactsList where companyId is null
//        defaultContactsShouldNotBeFound("companyId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId is greater than or equal to DEFAULT_COMPANY_ID
//        defaultContactsShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the contactsList where companyId is greater than or equal to UPDATED_COMPANY_ID
//        defaultContactsShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId is less than or equal to DEFAULT_COMPANY_ID
//        defaultContactsShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);
//
//        // Get all the contactsList where companyId is less than or equal to SMALLER_COMPANY_ID
//        defaultContactsShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsLessThanSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId is less than DEFAULT_COMPANY_ID
//        defaultContactsShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the contactsList where companyId is less than UPDATED_COMPANY_ID
//        defaultContactsShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByCompanyIdIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where companyId is greater than DEFAULT_COMPANY_ID
//        defaultContactsShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);
//
//        // Get all the contactsList where companyId is greater than SMALLER_COMPANY_ID
//        defaultContactsShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByStatusIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where status equals to DEFAULT_STATUS
//        defaultContactsShouldBeFound("status.equals=" + DEFAULT_STATUS);
//
//        // Get all the contactsList where status equals to UPDATED_STATUS
//        defaultContactsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByStatusIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where status in DEFAULT_STATUS or UPDATED_STATUS
//        defaultContactsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);
//
//        // Get all the contactsList where status equals to UPDATED_STATUS
//        defaultContactsShouldNotBeFound("status.in=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByStatusIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where status is not null
//        defaultContactsShouldBeFound("status.specified=true");
//
//        // Get all the contactsList where status is null
//        defaultContactsShouldNotBeFound("status.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByStatusContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where status contains DEFAULT_STATUS
//        defaultContactsShouldBeFound("status.contains=" + DEFAULT_STATUS);
//
//        // Get all the contactsList where status contains UPDATED_STATUS
//        defaultContactsShouldNotBeFound("status.contains=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByStatusNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where status does not contain DEFAULT_STATUS
//        defaultContactsShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);
//
//        // Get all the contactsList where status does not contain UPDATED_STATUS
//        defaultContactsShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModified equals to DEFAULT_LAST_MODIFIED
//        defaultContactsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);
//
//        // Get all the contactsList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultContactsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
//        defaultContactsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);
//
//        // Get all the contactsList where lastModified equals to UPDATED_LAST_MODIFIED
//        defaultContactsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModified is not null
//        defaultContactsShouldBeFound("lastModified.specified=true");
//
//        // Get all the contactsList where lastModified is null
//        defaultContactsShouldNotBeFound("lastModified.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedByIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
//        defaultContactsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the contactsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultContactsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedByIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
//        defaultContactsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);
//
//        // Get all the contactsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultContactsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedByIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModifiedBy is not null
//        defaultContactsShouldBeFound("lastModifiedBy.specified=true");
//
//        // Get all the contactsList where lastModifiedBy is null
//        defaultContactsShouldNotBeFound("lastModifiedBy.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedByContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
//        defaultContactsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the contactsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
//        defaultContactsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastModifiedByNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
//        defaultContactsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the contactsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
//        defaultContactsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactReferenceIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactReference equals to DEFAULT_CONTACT_REFERENCE
//        defaultContactsShouldBeFound("contactReference.equals=" + DEFAULT_CONTACT_REFERENCE);
//
//        // Get all the contactsList where contactReference equals to UPDATED_CONTACT_REFERENCE
//        defaultContactsShouldNotBeFound("contactReference.equals=" + UPDATED_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactReferenceIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactReference in DEFAULT_CONTACT_REFERENCE or UPDATED_CONTACT_REFERENCE
//        defaultContactsShouldBeFound("contactReference.in=" + DEFAULT_CONTACT_REFERENCE + "," + UPDATED_CONTACT_REFERENCE);
//
//        // Get all the contactsList where contactReference equals to UPDATED_CONTACT_REFERENCE
//        defaultContactsShouldNotBeFound("contactReference.in=" + UPDATED_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactReferenceIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactReference is not null
//        defaultContactsShouldBeFound("contactReference.specified=true");
//
//        // Get all the contactsList where contactReference is null
//        defaultContactsShouldNotBeFound("contactReference.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactReferenceContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactReference contains DEFAULT_CONTACT_REFERENCE
//        defaultContactsShouldBeFound("contactReference.contains=" + DEFAULT_CONTACT_REFERENCE);
//
//        // Get all the contactsList where contactReference contains UPDATED_CONTACT_REFERENCE
//        defaultContactsShouldNotBeFound("contactReference.contains=" + UPDATED_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByContactReferenceNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where contactReference does not contain DEFAULT_CONTACT_REFERENCE
//        defaultContactsShouldNotBeFound("contactReference.doesNotContain=" + DEFAULT_CONTACT_REFERENCE);
//
//        // Get all the contactsList where contactReference does not contain UPDATED_CONTACT_REFERENCE
//        defaultContactsShouldBeFound("contactReference.doesNotContain=" + UPDATED_CONTACT_REFERENCE);
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultContactsShouldBeFound(String filter) throws Exception {
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].contactPref").value(hasItem(DEFAULT_CONTACT_PREF)))
//            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE)))
//            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
//            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
//            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
//            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
//            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
//            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
//            .andExpect(jsonPath("$.[*].contactReference").value(hasItem(DEFAULT_CONTACT_REFERENCE)));
//
//        // Check, that the count call also returns 1
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultContactsShouldNotBeFound(String filter) throws Exception {
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingContacts() throws Exception {
//        // Get the contacts
//        restContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//
//        // Update the contacts
//        Contacts updatedContacts = contactsRepository.findById(contacts.getId()).get();
//        // Disconnect from session so that the updates on updatedContacts are not directly saved in db
//        em.detach(updatedContacts);
//        updatedContacts
//            .name(UPDATED_NAME)
//            .contactPref(UPDATED_CONTACT_PREF)
//            .contactType(UPDATED_CONTACT_TYPE)
//            .contact(UPDATED_CONTACT)
//            .refTable(UPDATED_REF_TABLE)
//            .refTableId(UPDATED_REF_TABLE_ID)
////            .companyId(UPDATED_COMPANY_ID)
//            .status(UPDATED_STATUS)
////            .lastModified(UPDATED_LAST_MODIFIED)
////            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
//            .contactReference(UPDATED_CONTACT_REFERENCE);
//        ContactsDTO contactsDTO = contactsMapper.toDto(updatedContacts);
//
//        restContactsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, contactsDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testContacts.getContactPref()).isEqualTo(UPDATED_CONTACT_PREF);
//        assertThat(testContacts.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
//        assertThat(testContacts.getContact()).isEqualTo(UPDATED_CONTACT);
//        assertThat(testContacts.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
//        assertThat(testContacts.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
//        assertThat(testContacts.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testContacts.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testContacts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testContacts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testContacts.getContactReference()).isEqualTo(UPDATED_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, contactsDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactsDTO)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateContactsWithPatch() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//
//        // Update the contacts using partial update
//        Contacts partialUpdatedContacts = new Contacts();
//        partialUpdatedContacts.setId(contacts.getId());
//
//        partialUpdatedContacts
//            .name(UPDATED_NAME)
//            .contactType(UPDATED_CONTACT_TYPE)
//            .contact(UPDATED_CONTACT)
//            .refTableId(UPDATED_REF_TABLE_ID);
//
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testContacts.getContactPref()).isEqualTo(DEFAULT_CONTACT_PREF);
//        assertThat(testContacts.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
//        assertThat(testContacts.getContact()).isEqualTo(UPDATED_CONTACT);
//        assertThat(testContacts.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
//        assertThat(testContacts.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
//        assertThat(testContacts.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
//        assertThat(testContacts.getStatus()).isEqualTo(DEFAULT_STATUS);
//        assertThat(testContacts.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
//        assertThat(testContacts.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testContacts.getContactReference()).isEqualTo(DEFAULT_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateContactsWithPatch() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//
//        // Update the contacts using partial update
//        Contacts partialUpdatedContacts = new Contacts();
//        partialUpdatedContacts.setId(contacts.getId());
//
//        partialUpdatedContacts
//            .name(UPDATED_NAME)
//            .contactPref(UPDATED_CONTACT_PREF)
//            .contactType(UPDATED_CONTACT_TYPE)
//            .contact(UPDATED_CONTACT)
//            .refTable(UPDATED_REF_TABLE)
//            .refTableId(UPDATED_REF_TABLE_ID)
//            .companyId(UPDATED_COMPANY_ID)
//            .status(UPDATED_STATUS)
//            .lastModified(UPDATED_LAST_MODIFIED)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
//            .contactReference(UPDATED_CONTACT_REFERENCE);
//
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testContacts.getContactPref()).isEqualTo(UPDATED_CONTACT_PREF);
//        assertThat(testContacts.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
//        assertThat(testContacts.getContact()).isEqualTo(UPDATED_CONTACT);
//        assertThat(testContacts.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
//        assertThat(testContacts.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
//        assertThat(testContacts.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
//        assertThat(testContacts.getStatus()).isEqualTo(UPDATED_STATUS);
//        assertThat(testContacts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
//        assertThat(testContacts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testContacts.getContactReference()).isEqualTo(UPDATED_CONTACT_REFERENCE);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, contactsDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(contactsDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // Create the Contacts
//        ContactsDTO contactsDTO = contactsMapper.toDto(contacts);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactsDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeDelete = contactsRepository.findAll().size();
//
//        // Delete the contacts
//        restContactsMockMvc
//            .perform(delete(ENTITY_API_URL_ID, contacts.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
