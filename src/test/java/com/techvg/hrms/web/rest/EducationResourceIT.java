package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Education;
import com.techvg.hrms.repository.EducationRepository;
import com.techvg.hrms.service.criteria.EducationCriteria;
import com.techvg.hrms.service.dto.EducationDTO;
import com.techvg.hrms.service.mapper.EducationMapper;
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
 * Integration tests for the {@link EducationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationResourceIT {

    private static final String DEFAULT_INSTITUTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_YEAR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_YEAR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EDUCATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;
    private static final Long SMALLER_EMPLOYEE_ID = 1L - 1L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/educations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .institution(DEFAULT_INSTITUTION)
            .subject(DEFAULT_SUBJECT)
            .startYear(DEFAULT_START_YEAR)
            .endDate(DEFAULT_END_DATE)
            .educationType(DEFAULT_EDUCATION_TYPE)
            .grade(DEFAULT_GRADE)
            .description(DEFAULT_DESCRIPTION)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            //            .companyId(DEFAULT_COMPANY_ID)
            .status(DEFAULT_STATUS)//            .lastModified(DEFAULT_LAST_MODIFIED)
        //            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
        ;
        return education;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity(EntityManager em) {
        Education education = new Education()
            .institution(UPDATED_INSTITUTION)
            .subject(UPDATED_SUBJECT)
            .startYear(UPDATED_START_YEAR)
            .endDate(UPDATED_END_DATE)
            .educationType(UPDATED_EDUCATION_TYPE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        return education;
    }

    @BeforeEach
    public void initTest() {
        education = createEntity(em);
    }

    @Test
    @Transactional
    void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEducation.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.getEducationType()).isEqualTo(DEFAULT_EDUCATION_TYPE);
        assertThat(testEducation.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEducation.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEducation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEducation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEducation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEducationWithExistingId() throws Exception {
        // Create the Education with an existing ID
        education.setId(1L);
        EducationDTO educationDTO = educationMapper.toDto(education);

        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].educationType").value(hasItem(DEFAULT_EDUCATION_TYPE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc
            .perform(get(ENTITY_API_URL_ID, education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.institution").value(DEFAULT_INSTITUTION))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.educationType").value(DEFAULT_EDUCATION_TYPE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getEducationsByIdFiltering() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        Long id = education.getId();

        defaultEducationShouldBeFound("id.equals=" + id);
        defaultEducationShouldNotBeFound("id.notEquals=" + id);

        defaultEducationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.greaterThan=" + id);

        defaultEducationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution equals to DEFAULT_INSTITUTION
        defaultEducationShouldBeFound("institution.equals=" + DEFAULT_INSTITUTION);

        // Get all the educationList where institution equals to UPDATED_INSTITUTION
        defaultEducationShouldNotBeFound("institution.equals=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution in DEFAULT_INSTITUTION or UPDATED_INSTITUTION
        defaultEducationShouldBeFound("institution.in=" + DEFAULT_INSTITUTION + "," + UPDATED_INSTITUTION);

        // Get all the educationList where institution equals to UPDATED_INSTITUTION
        defaultEducationShouldNotBeFound("institution.in=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution is not null
        defaultEducationShouldBeFound("institution.specified=true");

        // Get all the educationList where institution is null
        defaultEducationShouldNotBeFound("institution.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution contains DEFAULT_INSTITUTION
        defaultEducationShouldBeFound("institution.contains=" + DEFAULT_INSTITUTION);

        // Get all the educationList where institution contains UPDATED_INSTITUTION
        defaultEducationShouldNotBeFound("institution.contains=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsByInstitutionNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where institution does not contain DEFAULT_INSTITUTION
        defaultEducationShouldNotBeFound("institution.doesNotContain=" + DEFAULT_INSTITUTION);

        // Get all the educationList where institution does not contain UPDATED_INSTITUTION
        defaultEducationShouldBeFound("institution.doesNotContain=" + UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject equals to DEFAULT_SUBJECT
        defaultEducationShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the educationList where subject equals to UPDATED_SUBJECT
        defaultEducationShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultEducationShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the educationList where subject equals to UPDATED_SUBJECT
        defaultEducationShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject is not null
        defaultEducationShouldBeFound("subject.specified=true");

        // Get all the educationList where subject is null
        defaultEducationShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject contains DEFAULT_SUBJECT
        defaultEducationShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the educationList where subject contains UPDATED_SUBJECT
        defaultEducationShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where subject does not contain DEFAULT_SUBJECT
        defaultEducationShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the educationList where subject does not contain UPDATED_SUBJECT
        defaultEducationShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEducationsByStartYearIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startYear equals to DEFAULT_START_YEAR
        defaultEducationShouldBeFound("startYear.equals=" + DEFAULT_START_YEAR);

        // Get all the educationList where startYear equals to UPDATED_START_YEAR
        defaultEducationShouldNotBeFound("startYear.equals=" + UPDATED_START_YEAR);
    }

    @Test
    @Transactional
    void getAllEducationsByStartYearIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startYear in DEFAULT_START_YEAR or UPDATED_START_YEAR
        defaultEducationShouldBeFound("startYear.in=" + DEFAULT_START_YEAR + "," + UPDATED_START_YEAR);

        // Get all the educationList where startYear equals to UPDATED_START_YEAR
        defaultEducationShouldNotBeFound("startYear.in=" + UPDATED_START_YEAR);
    }

    @Test
    @Transactional
    void getAllEducationsByStartYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startYear is not null
        defaultEducationShouldBeFound("startYear.specified=true");

        // Get all the educationList where startYear is null
        defaultEducationShouldNotBeFound("startYear.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate equals to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is not null
        defaultEducationShouldBeFound("endDate.specified=true");

        // Get all the educationList where endDate is null
        defaultEducationShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEducationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where educationType equals to DEFAULT_EDUCATION_TYPE
        defaultEducationShouldBeFound("educationType.equals=" + DEFAULT_EDUCATION_TYPE);

        // Get all the educationList where educationType equals to UPDATED_EDUCATION_TYPE
        defaultEducationShouldNotBeFound("educationType.equals=" + UPDATED_EDUCATION_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByEducationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where educationType in DEFAULT_EDUCATION_TYPE or UPDATED_EDUCATION_TYPE
        defaultEducationShouldBeFound("educationType.in=" + DEFAULT_EDUCATION_TYPE + "," + UPDATED_EDUCATION_TYPE);

        // Get all the educationList where educationType equals to UPDATED_EDUCATION_TYPE
        defaultEducationShouldNotBeFound("educationType.in=" + UPDATED_EDUCATION_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByEducationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where educationType is not null
        defaultEducationShouldBeFound("educationType.specified=true");

        // Get all the educationList where educationType is null
        defaultEducationShouldNotBeFound("educationType.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEducationTypeContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where educationType contains DEFAULT_EDUCATION_TYPE
        defaultEducationShouldBeFound("educationType.contains=" + DEFAULT_EDUCATION_TYPE);

        // Get all the educationList where educationType contains UPDATED_EDUCATION_TYPE
        defaultEducationShouldNotBeFound("educationType.contains=" + UPDATED_EDUCATION_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByEducationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where educationType does not contain DEFAULT_EDUCATION_TYPE
        defaultEducationShouldNotBeFound("educationType.doesNotContain=" + DEFAULT_EDUCATION_TYPE);

        // Get all the educationList where educationType does not contain UPDATED_EDUCATION_TYPE
        defaultEducationShouldBeFound("educationType.doesNotContain=" + UPDATED_EDUCATION_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade equals to DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the educationList where grade equals to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEducationShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the educationList where grade equals to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is not null
        defaultEducationShouldBeFound("grade.specified=true");

        // Get all the educationList where grade is null
        defaultEducationShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByGradeContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade contains DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the educationList where grade contains UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade does not contain DEFAULT_GRADE
        defaultEducationShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the educationList where grade does not contain UPDATED_GRADE
        defaultEducationShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description equals to DEFAULT_DESCRIPTION
        defaultEducationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description equals to UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEducationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the educationList where description equals to UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description is not null
        defaultEducationShouldBeFound("description.specified=true");

        // Get all the educationList where description is null
        defaultEducationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description contains DEFAULT_DESCRIPTION
        defaultEducationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description contains UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description does not contain DEFAULT_DESCRIPTION
        defaultEducationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description does not contain UPDATED_DESCRIPTION
        defaultEducationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the educationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is not null
        defaultEducationShouldBeFound("employeeId.specified=true");

        // Get all the educationList where employeeId is null
        defaultEducationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is greater than or equal to DEFAULT_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is greater than or equal to UPDATED_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is less than or equal to DEFAULT_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is less than or equal to SMALLER_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.lessThanOrEqual=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is less than DEFAULT_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.lessThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is less than UPDATED_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.lessThan=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where employeeId is greater than DEFAULT_EMPLOYEE_ID
        defaultEducationShouldNotBeFound("employeeId.greaterThan=" + DEFAULT_EMPLOYEE_ID);

        // Get all the educationList where employeeId is greater than SMALLER_EMPLOYEE_ID
        defaultEducationShouldBeFound("employeeId.greaterThan=" + SMALLER_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId equals to DEFAULT_COMPANY_ID
        defaultEducationShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the educationList where companyId equals to UPDATED_COMPANY_ID
        defaultEducationShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultEducationShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the educationList where companyId equals to UPDATED_COMPANY_ID
        defaultEducationShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId is not null
        defaultEducationShouldBeFound("companyId.specified=true");

        // Get all the educationList where companyId is null
        defaultEducationShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultEducationShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the educationList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultEducationShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultEducationShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the educationList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultEducationShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId is less than DEFAULT_COMPANY_ID
        defaultEducationShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the educationList where companyId is less than UPDATED_COMPANY_ID
        defaultEducationShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where companyId is greater than DEFAULT_COMPANY_ID
        defaultEducationShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the educationList where companyId is greater than SMALLER_COMPANY_ID
        defaultEducationShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllEducationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where status equals to DEFAULT_STATUS
        defaultEducationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the educationList where status equals to UPDATED_STATUS
        defaultEducationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEducationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEducationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the educationList where status equals to UPDATED_STATUS
        defaultEducationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEducationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where status is not null
        defaultEducationShouldBeFound("status.specified=true");

        // Get all the educationList where status is null
        defaultEducationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByStatusContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where status contains DEFAULT_STATUS
        defaultEducationShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the educationList where status contains UPDATED_STATUS
        defaultEducationShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEducationsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where status does not contain DEFAULT_STATUS
        defaultEducationShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the educationList where status does not contain UPDATED_STATUS
        defaultEducationShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEducationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the educationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEducationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEducationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the educationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEducationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified is not null
        defaultEducationShouldBeFound("lastModified.specified=true");

        // Get all the educationList where lastModified is null
        defaultEducationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy is not null
        defaultEducationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the educationList where lastModifiedBy is null
        defaultEducationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEducationShouldBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].educationType").value(hasItem(DEFAULT_EDUCATION_TYPE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEducationShouldNotBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .institution(UPDATED_INSTITUTION)
            .subject(UPDATED_SUBJECT)
            .startYear(UPDATED_START_YEAR)
            .endDate(UPDATED_END_DATE)
            .educationType(UPDATED_EDUCATION_TYPE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEducation.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getEducationType()).isEqualTo(UPDATED_EDUCATION_TYPE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEducation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEducation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .endDate(UPDATED_END_DATE)
            .educationType(UPDATED_EDUCATION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        ;

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEducation.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getEducationType()).isEqualTo(UPDATED_EDUCATION_TYPE);
        assertThat(testEducation.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEducation.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testEducation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .institution(UPDATED_INSTITUTION)
            .subject(UPDATED_SUBJECT)
            .startYear(UPDATED_START_YEAR)
            .endDate(UPDATED_END_DATE)
            .educationType(UPDATED_EDUCATION_TYPE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            //            .companyId(UPDATED_COMPANY_ID)
            .status(UPDATED_STATUS)//            .lastModified(UPDATED_LAST_MODIFIED)
        //            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
        ;

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testEducation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEducation.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getEducationType()).isEqualTo(UPDATED_EDUCATION_TYPE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEducation.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testEducation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc
            .perform(delete(ENTITY_API_URL_ID, education.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
