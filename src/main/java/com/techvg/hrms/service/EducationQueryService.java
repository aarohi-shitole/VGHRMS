package com.techvg.hrms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.techvg.hrms.domain.Education;
import com.techvg.hrms.domain.Education_;
import com.techvg.hrms.repository.EducationRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.EducationCriteria;
import com.techvg.hrms.service.dto.EducationDTO;
import com.techvg.hrms.service.mapper.EducationMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Education} entities in the database.
 * The main input is a {@link EducationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EducationDTO} or a {@link Page} of {@link EducationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EducationQueryService extends QueryService<Education> {

    private final Logger log = LoggerFactory.getLogger(EducationQueryService.class);

    private final EducationRepository educationRepository;

    private final EducationMapper educationMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public EducationQueryService(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    /**
     * Return a {@link List} of {@link EducationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EducationDTO> findByCriteria(EducationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("find by criteria : {}", criteria);
        final Specification<Education> specification = createSpecification(criteria);
        return educationMapper.toDto(educationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EducationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationDTO> findByCriteria(EducationCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Education> specification = createSpecification(criteria);
        return educationRepository.findAll(specification, page).map(educationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EducationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("count by criteria : {}", criteria);
        final Specification<Education> specification = createSpecification(criteria);
        return educationRepository.count(specification);
    }

    /**
     * Function to convert {@link EducationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Education> createSpecification(EducationCriteria criteria) {
        Specification<Education> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Education_.id));
            }
            if (criteria.getInstitution() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstitution(), Education_.institution));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Education_.subject));
            }
            if (criteria.getStartYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartYear(), Education_.startYear));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Education_.endDate));
            }
            if (criteria.getEducationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEducationType(), Education_.educationType));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrade(), Education_.grade));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Education_.description));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Education_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Education_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Education_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Education_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Education_.lastModifiedBy));
            }
        }
        return specification;
    }
}
