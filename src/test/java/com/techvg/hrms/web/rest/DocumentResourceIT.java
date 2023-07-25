package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Document;
import com.techvg.hrms.repository.DocumentRepository;
import com.techvg.hrms.service.criteria.DocumentCriteria;
import com.techvg.hrms.service.dto.DocumentDTO;
import com.techvg.hrms.service.mapper.DocumentMapper;
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
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentResourceIT {

    private static final String DEFAULT_DOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_DOC_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_UUID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_FOLDER_UUID = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER_UUID = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .docType(DEFAULT_DOC_TYPE)
            .docCategory(DEFAULT_DOC_CATEGORY)
            .fileName(DEFAULT_FILE_NAME)
            .contentType(DEFAULT_CONTENT_TYPE)
            .fileUuid(DEFAULT_FILE_UUID)
            .folderUuid(DEFAULT_FOLDER_UUID)
            .refTable(DEFAULT_REF_TABLE)
            .refTableId(DEFAULT_REF_TABLE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .docType(UPDATED_DOC_TYPE)
            .docCategory(UPDATED_DOC_CATEGORY)
            .fileName(UPDATED_FILE_NAME)
            .contentType(UPDATED_CONTENT_TYPE)
            .fileUuid(UPDATED_FILE_UUID)
            .folderUuid(UPDATED_FOLDER_UUID)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();
        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testDocument.getDocCategory()).isEqualTo(DEFAULT_DOC_CATEGORY);
        assertThat(testDocument.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDocument.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testDocument.getFileUuid()).isEqualTo(DEFAULT_FILE_UUID);
        assertThat(testDocument.getFolderUuid()).isEqualTo(DEFAULT_FOLDER_UUID);
        assertThat(testDocument.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testDocument.getRefTableId()).isEqualTo(DEFAULT_REF_TABLE_ID);
        assertThat(testDocument.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testDocument.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDocument.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDocument.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createDocumentWithExistingId() throws Exception {
        // Create the Document with an existing ID
        document.setId(1L);
        DocumentDTO documentDTO = documentMapper.toDto(document);

        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].docCategory").value(hasItem(DEFAULT_DOC_CATEGORY)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileUuid").value(hasItem(DEFAULT_FILE_UUID)))
            .andExpect(jsonPath("$.[*].folderUuid").value(hasItem(DEFAULT_FOLDER_UUID)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.docType").value(DEFAULT_DOC_TYPE))
            .andExpect(jsonPath("$.docCategory").value(DEFAULT_DOC_CATEGORY))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileUuid").value(DEFAULT_FILE_UUID))
            .andExpect(jsonPath("$.folderUuid").value(DEFAULT_FOLDER_UUID))
            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
            .andExpect(jsonPath("$.refTableId").value(DEFAULT_REF_TABLE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        Long id = document.getId();

        defaultDocumentShouldBeFound("id.equals=" + id);
        defaultDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docType equals to DEFAULT_DOC_TYPE
        defaultDocumentShouldBeFound("docType.equals=" + DEFAULT_DOC_TYPE);

        // Get all the documentList where docType equals to UPDATED_DOC_TYPE
        defaultDocumentShouldNotBeFound("docType.equals=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docType in DEFAULT_DOC_TYPE or UPDATED_DOC_TYPE
        defaultDocumentShouldBeFound("docType.in=" + DEFAULT_DOC_TYPE + "," + UPDATED_DOC_TYPE);

        // Get all the documentList where docType equals to UPDATED_DOC_TYPE
        defaultDocumentShouldNotBeFound("docType.in=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docType is not null
        defaultDocumentShouldBeFound("docType.specified=true");

        // Get all the documentList where docType is null
        defaultDocumentShouldNotBeFound("docType.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByDocTypeContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docType contains DEFAULT_DOC_TYPE
        defaultDocumentShouldBeFound("docType.contains=" + DEFAULT_DOC_TYPE);

        // Get all the documentList where docType contains UPDATED_DOC_TYPE
        defaultDocumentShouldNotBeFound("docType.contains=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocTypeNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docType does not contain DEFAULT_DOC_TYPE
        defaultDocumentShouldNotBeFound("docType.doesNotContain=" + DEFAULT_DOC_TYPE);

        // Get all the documentList where docType does not contain UPDATED_DOC_TYPE
        defaultDocumentShouldBeFound("docType.doesNotContain=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docCategory equals to DEFAULT_DOC_CATEGORY
        defaultDocumentShouldBeFound("docCategory.equals=" + DEFAULT_DOC_CATEGORY);

        // Get all the documentList where docCategory equals to UPDATED_DOC_CATEGORY
        defaultDocumentShouldNotBeFound("docCategory.equals=" + UPDATED_DOC_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docCategory in DEFAULT_DOC_CATEGORY or UPDATED_DOC_CATEGORY
        defaultDocumentShouldBeFound("docCategory.in=" + DEFAULT_DOC_CATEGORY + "," + UPDATED_DOC_CATEGORY);

        // Get all the documentList where docCategory equals to UPDATED_DOC_CATEGORY
        defaultDocumentShouldNotBeFound("docCategory.in=" + UPDATED_DOC_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docCategory is not null
        defaultDocumentShouldBeFound("docCategory.specified=true");

        // Get all the documentList where docCategory is null
        defaultDocumentShouldNotBeFound("docCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByDocCategoryContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docCategory contains DEFAULT_DOC_CATEGORY
        defaultDocumentShouldBeFound("docCategory.contains=" + DEFAULT_DOC_CATEGORY);

        // Get all the documentList where docCategory contains UPDATED_DOC_CATEGORY
        defaultDocumentShouldNotBeFound("docCategory.contains=" + UPDATED_DOC_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDocumentsByDocCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where docCategory does not contain DEFAULT_DOC_CATEGORY
        defaultDocumentShouldNotBeFound("docCategory.doesNotContain=" + DEFAULT_DOC_CATEGORY);

        // Get all the documentList where docCategory does not contain UPDATED_DOC_CATEGORY
        defaultDocumentShouldBeFound("docCategory.doesNotContain=" + UPDATED_DOC_CATEGORY);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileName equals to DEFAULT_FILE_NAME
        defaultDocumentShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the documentList where fileName equals to UPDATED_FILE_NAME
        defaultDocumentShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultDocumentShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the documentList where fileName equals to UPDATED_FILE_NAME
        defaultDocumentShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileName is not null
        defaultDocumentShouldBeFound("fileName.specified=true");

        // Get all the documentList where fileName is null
        defaultDocumentShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileName contains DEFAULT_FILE_NAME
        defaultDocumentShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the documentList where fileName contains UPDATED_FILE_NAME
        defaultDocumentShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileName does not contain DEFAULT_FILE_NAME
        defaultDocumentShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the documentList where fileName does not contain UPDATED_FILE_NAME
        defaultDocumentShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where contentType equals to DEFAULT_CONTENT_TYPE
        defaultDocumentShouldBeFound("contentType.equals=" + DEFAULT_CONTENT_TYPE);

        // Get all the documentList where contentType equals to UPDATED_CONTENT_TYPE
        defaultDocumentShouldNotBeFound("contentType.equals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where contentType in DEFAULT_CONTENT_TYPE or UPDATED_CONTENT_TYPE
        defaultDocumentShouldBeFound("contentType.in=" + DEFAULT_CONTENT_TYPE + "," + UPDATED_CONTENT_TYPE);

        // Get all the documentList where contentType equals to UPDATED_CONTENT_TYPE
        defaultDocumentShouldNotBeFound("contentType.in=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where contentType is not null
        defaultDocumentShouldBeFound("contentType.specified=true");

        // Get all the documentList where contentType is null
        defaultDocumentShouldNotBeFound("contentType.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByContentTypeContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where contentType contains DEFAULT_CONTENT_TYPE
        defaultDocumentShouldBeFound("contentType.contains=" + DEFAULT_CONTENT_TYPE);

        // Get all the documentList where contentType contains UPDATED_CONTENT_TYPE
        defaultDocumentShouldNotBeFound("contentType.contains=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByContentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where contentType does not contain DEFAULT_CONTENT_TYPE
        defaultDocumentShouldNotBeFound("contentType.doesNotContain=" + DEFAULT_CONTENT_TYPE);

        // Get all the documentList where contentType does not contain UPDATED_CONTENT_TYPE
        defaultDocumentShouldBeFound("contentType.doesNotContain=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileUuid equals to DEFAULT_FILE_UUID
        defaultDocumentShouldBeFound("fileUuid.equals=" + DEFAULT_FILE_UUID);

        // Get all the documentList where fileUuid equals to UPDATED_FILE_UUID
        defaultDocumentShouldNotBeFound("fileUuid.equals=" + UPDATED_FILE_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUuidIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileUuid in DEFAULT_FILE_UUID or UPDATED_FILE_UUID
        defaultDocumentShouldBeFound("fileUuid.in=" + DEFAULT_FILE_UUID + "," + UPDATED_FILE_UUID);

        // Get all the documentList where fileUuid equals to UPDATED_FILE_UUID
        defaultDocumentShouldNotBeFound("fileUuid.in=" + UPDATED_FILE_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileUuid is not null
        defaultDocumentShouldBeFound("fileUuid.specified=true");

        // Get all the documentList where fileUuid is null
        defaultDocumentShouldNotBeFound("fileUuid.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUuidContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileUuid contains DEFAULT_FILE_UUID
        defaultDocumentShouldBeFound("fileUuid.contains=" + DEFAULT_FILE_UUID);

        // Get all the documentList where fileUuid contains UPDATED_FILE_UUID
        defaultDocumentShouldNotBeFound("fileUuid.contains=" + UPDATED_FILE_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUuidNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where fileUuid does not contain DEFAULT_FILE_UUID
        defaultDocumentShouldNotBeFound("fileUuid.doesNotContain=" + DEFAULT_FILE_UUID);

        // Get all the documentList where fileUuid does not contain UPDATED_FILE_UUID
        defaultDocumentShouldBeFound("fileUuid.doesNotContain=" + UPDATED_FILE_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFolderUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where folderUuid equals to DEFAULT_FOLDER_UUID
        defaultDocumentShouldBeFound("folderUuid.equals=" + DEFAULT_FOLDER_UUID);

        // Get all the documentList where folderUuid equals to UPDATED_FOLDER_UUID
        defaultDocumentShouldNotBeFound("folderUuid.equals=" + UPDATED_FOLDER_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFolderUuidIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where folderUuid in DEFAULT_FOLDER_UUID or UPDATED_FOLDER_UUID
        defaultDocumentShouldBeFound("folderUuid.in=" + DEFAULT_FOLDER_UUID + "," + UPDATED_FOLDER_UUID);

        // Get all the documentList where folderUuid equals to UPDATED_FOLDER_UUID
        defaultDocumentShouldNotBeFound("folderUuid.in=" + UPDATED_FOLDER_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFolderUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where folderUuid is not null
        defaultDocumentShouldBeFound("folderUuid.specified=true");

        // Get all the documentList where folderUuid is null
        defaultDocumentShouldNotBeFound("folderUuid.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFolderUuidContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where folderUuid contains DEFAULT_FOLDER_UUID
        defaultDocumentShouldBeFound("folderUuid.contains=" + DEFAULT_FOLDER_UUID);

        // Get all the documentList where folderUuid contains UPDATED_FOLDER_UUID
        defaultDocumentShouldNotBeFound("folderUuid.contains=" + UPDATED_FOLDER_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByFolderUuidNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where folderUuid does not contain DEFAULT_FOLDER_UUID
        defaultDocumentShouldNotBeFound("folderUuid.doesNotContain=" + DEFAULT_FOLDER_UUID);

        // Get all the documentList where folderUuid does not contain UPDATED_FOLDER_UUID
        defaultDocumentShouldBeFound("folderUuid.doesNotContain=" + UPDATED_FOLDER_UUID);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTable equals to DEFAULT_REF_TABLE
        defaultDocumentShouldBeFound("refTable.equals=" + DEFAULT_REF_TABLE);

        // Get all the documentList where refTable equals to UPDATED_REF_TABLE
        defaultDocumentShouldNotBeFound("refTable.equals=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTable in DEFAULT_REF_TABLE or UPDATED_REF_TABLE
        defaultDocumentShouldBeFound("refTable.in=" + DEFAULT_REF_TABLE + "," + UPDATED_REF_TABLE);

        // Get all the documentList where refTable equals to UPDATED_REF_TABLE
        defaultDocumentShouldNotBeFound("refTable.in=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTable is not null
        defaultDocumentShouldBeFound("refTable.specified=true");

        // Get all the documentList where refTable is null
        defaultDocumentShouldNotBeFound("refTable.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTable contains DEFAULT_REF_TABLE
        defaultDocumentShouldBeFound("refTable.contains=" + DEFAULT_REF_TABLE);

        // Get all the documentList where refTable contains UPDATED_REF_TABLE
        defaultDocumentShouldNotBeFound("refTable.contains=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTable does not contain DEFAULT_REF_TABLE
        defaultDocumentShouldNotBeFound("refTable.doesNotContain=" + DEFAULT_REF_TABLE);

        // Get all the documentList where refTable does not contain UPDATED_REF_TABLE
        defaultDocumentShouldBeFound("refTable.doesNotContain=" + UPDATED_REF_TABLE);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId equals to DEFAULT_REF_TABLE_ID
        defaultDocumentShouldBeFound("refTableId.equals=" + DEFAULT_REF_TABLE_ID);

        // Get all the documentList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultDocumentShouldNotBeFound("refTableId.equals=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId in DEFAULT_REF_TABLE_ID or UPDATED_REF_TABLE_ID
        defaultDocumentShouldBeFound("refTableId.in=" + DEFAULT_REF_TABLE_ID + "," + UPDATED_REF_TABLE_ID);

        // Get all the documentList where refTableId equals to UPDATED_REF_TABLE_ID
        defaultDocumentShouldNotBeFound("refTableId.in=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId is not null
        defaultDocumentShouldBeFound("refTableId.specified=true");

        // Get all the documentList where refTableId is null
        defaultDocumentShouldNotBeFound("refTableId.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId is greater than or equal to DEFAULT_REF_TABLE_ID
        defaultDocumentShouldBeFound("refTableId.greaterThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the documentList where refTableId is greater than or equal to UPDATED_REF_TABLE_ID
        defaultDocumentShouldNotBeFound("refTableId.greaterThanOrEqual=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId is less than or equal to DEFAULT_REF_TABLE_ID
        defaultDocumentShouldBeFound("refTableId.lessThanOrEqual=" + DEFAULT_REF_TABLE_ID);

        // Get all the documentList where refTableId is less than or equal to SMALLER_REF_TABLE_ID
        defaultDocumentShouldNotBeFound("refTableId.lessThanOrEqual=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId is less than DEFAULT_REF_TABLE_ID
        defaultDocumentShouldNotBeFound("refTableId.lessThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the documentList where refTableId is less than UPDATED_REF_TABLE_ID
        defaultDocumentShouldBeFound("refTableId.lessThan=" + UPDATED_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByRefTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where refTableId is greater than DEFAULT_REF_TABLE_ID
        defaultDocumentShouldNotBeFound("refTableId.greaterThan=" + DEFAULT_REF_TABLE_ID);

        // Get all the documentList where refTableId is greater than SMALLER_REF_TABLE_ID
        defaultDocumentShouldBeFound("refTableId.greaterThan=" + SMALLER_REF_TABLE_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId equals to DEFAULT_COMPANY_ID
        defaultDocumentShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the documentList where companyId equals to UPDATED_COMPANY_ID
        defaultDocumentShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultDocumentShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the documentList where companyId equals to UPDATED_COMPANY_ID
        defaultDocumentShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId is not null
        defaultDocumentShouldBeFound("companyId.specified=true");

        // Get all the documentList where companyId is null
        defaultDocumentShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultDocumentShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the documentList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultDocumentShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultDocumentShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the documentList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultDocumentShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId is less than DEFAULT_COMPANY_ID
        defaultDocumentShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the documentList where companyId is less than UPDATED_COMPANY_ID
        defaultDocumentShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where companyId is greater than DEFAULT_COMPANY_ID
        defaultDocumentShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the documentList where companyId is greater than SMALLER_COMPANY_ID
        defaultDocumentShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllDocumentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where status equals to DEFAULT_STATUS
        defaultDocumentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the documentList where status equals to UPDATED_STATUS
        defaultDocumentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDocumentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDocumentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the documentList where status equals to UPDATED_STATUS
        defaultDocumentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDocumentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where status is not null
        defaultDocumentShouldBeFound("status.specified=true");

        // Get all the documentList where status is null
        defaultDocumentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByStatusContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where status contains DEFAULT_STATUS
        defaultDocumentShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the documentList where status contains UPDATED_STATUS
        defaultDocumentShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDocumentsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where status does not contain DEFAULT_STATUS
        defaultDocumentShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the documentList where status does not contain UPDATED_STATUS
        defaultDocumentShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDocumentShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the documentList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDocumentShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDocumentShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the documentList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDocumentShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModified is not null
        defaultDocumentShouldBeFound("lastModified.specified=true");

        // Get all the documentList where lastModified is null
        defaultDocumentShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDocumentShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the documentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDocumentShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDocumentShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the documentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDocumentShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModifiedBy is not null
        defaultDocumentShouldBeFound("lastModifiedBy.specified=true");

        // Get all the documentList where lastModifiedBy is null
        defaultDocumentShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDocumentShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the documentList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDocumentShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDocumentShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the documentList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDocumentShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentShouldBeFound(String filter) throws Exception {
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].docCategory").value(hasItem(DEFAULT_DOC_CATEGORY)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileUuid").value(hasItem(DEFAULT_FILE_UUID)))
            .andExpect(jsonPath("$.[*].folderUuid").value(hasItem(DEFAULT_FOLDER_UUID)))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].refTableId").value(hasItem(DEFAULT_REF_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentShouldNotBeFound(String filter) throws Exception {
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .docType(UPDATED_DOC_TYPE)
            .docCategory(UPDATED_DOC_CATEGORY)
            .fileName(UPDATED_FILE_NAME)
            .contentType(UPDATED_CONTENT_TYPE)
            .fileUuid(UPDATED_FILE_UUID)
            .folderUuid(UPDATED_FOLDER_UUID)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        DocumentDTO documentDTO = documentMapper.toDto(updatedDocument);

        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testDocument.getDocCategory()).isEqualTo(UPDATED_DOC_CATEGORY);
        assertThat(testDocument.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDocument.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testDocument.getFileUuid()).isEqualTo(UPDATED_FILE_UUID);
        assertThat(testDocument.getFolderUuid()).isEqualTo(UPDATED_FOLDER_UUID);
        assertThat(testDocument.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testDocument.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testDocument.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocument.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDocument.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .docType(UPDATED_DOC_TYPE)
            .docCategory(UPDATED_DOC_CATEGORY)
            .refTableId(UPDATED_REF_TABLE_ID)
            .status(UPDATED_STATUS)//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testDocument.getDocCategory()).isEqualTo(UPDATED_DOC_CATEGORY);
        assertThat(testDocument.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDocument.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testDocument.getFileUuid()).isEqualTo(DEFAULT_FILE_UUID);
        assertThat(testDocument.getFolderUuid()).isEqualTo(DEFAULT_FOLDER_UUID);
        assertThat(testDocument.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testDocument.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testDocument.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocument.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDocument.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .docType(UPDATED_DOC_TYPE)
            .docCategory(UPDATED_DOC_CATEGORY)
            .fileName(UPDATED_FILE_NAME)
            .contentType(UPDATED_CONTENT_TYPE)
            .fileUuid(UPDATED_FILE_UUID)
            .folderUuid(UPDATED_FOLDER_UUID)
            .refTable(UPDATED_REF_TABLE)
            .refTableId(UPDATED_REF_TABLE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testDocument.getDocCategory()).isEqualTo(UPDATED_DOC_CATEGORY);
        assertThat(testDocument.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDocument.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testDocument.getFileUuid()).isEqualTo(UPDATED_FILE_UUID);
        assertThat(testDocument.getFolderUuid()).isEqualTo(UPDATED_FOLDER_UUID);
        assertThat(testDocument.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testDocument.getRefTableId()).isEqualTo(UPDATED_REF_TABLE_ID);
        assertThat(testDocument.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testDocument.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocument.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDocument.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
