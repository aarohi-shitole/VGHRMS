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
import com.techvg.hrms.domain.TrainingType;
import com.techvg.hrms.domain.TrainingType_;
import com.techvg.hrms.repository.TrainingTypeRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TrainingTypeCriteria;
import com.techvg.hrms.service.dto.TrainingTypeDTO;
import com.techvg.hrms.service.mapper.TrainingTypeMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TrainingType} entities in the database.
 * The main input is a {@link TrainingTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingTypeDTO} or a {@link Page} of {@link TrainingTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingTypeQueryService extends QueryService<TrainingType> {

    private final Logger log = LoggerFactory.getLogger(TrainingTypeQueryService.class);

    private final TrainingTypeRepository trainingTypeRepository;

    private final TrainingTypeMapper trainingTypeMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TrainingTypeQueryService(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    /**
     * Return a {@link List} of {@link TrainingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingTypeDTO> findByCriteria(TrainingTypeCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TrainingType> specification = createSpecification(criteria);
        return trainingTypeMapper.toDto(trainingTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingTypeDTO> findByCriteria(TrainingTypeCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrainingType> specification = createSpecification(criteria);
        return trainingTypeRepository.findAll(specification, page).map(trainingTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingTypeCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TrainingType> specification = createSpecification(criteria);
        return trainingTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TrainingType> createSpecification(TrainingTypeCriteria criteria) {
        Specification<TrainingType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TrainingType_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), TrainingType_.type));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TrainingType_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TrainingType_.status));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), TrainingType_.departmentId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TrainingType_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TrainingType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TrainingType_.lastModifiedBy));
            }
        }
        return specification;
    }
}
