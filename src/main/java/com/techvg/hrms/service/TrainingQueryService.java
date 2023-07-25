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
import com.techvg.hrms.domain.Training;
import com.techvg.hrms.domain.Training_;
import com.techvg.hrms.repository.TrainingRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TrainingCriteria;
import com.techvg.hrms.service.dto.TrainingDTO;
import com.techvg.hrms.service.mapper.TrainingMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Training} entities in the database.
 * The main input is a {@link TrainingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingDTO} or a {@link Page} of {@link TrainingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingQueryService extends QueryService<Training> {

    private final Logger log = LoggerFactory.getLogger(TrainingQueryService.class);

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TrainingQueryService(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    /**
     * Return a {@link List} of {@link TrainingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingDTO> findByCriteria(TrainingCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingMapper.toDto(trainingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingDTO> findByCriteria(TrainingCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.findAll(specification, page).map(trainingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Training> createSpecification(TrainingCriteria criteria) {
        Specification<Training> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Training_.id));
            }
            if (criteria.getTrainingCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrainingCost(), Training_.trainingCost));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Training_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Training_.endDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Training_.description));
            }
            if (criteria.getTrainingStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrainingStatus(), Training_.trainingStatus));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Training_.status));
            }
            if (criteria.getTrainerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrainerId(), Training_.trainerId));
            }
            if (criteria.getTrainingTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrainingTypeId(), Training_.trainingTypeId));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), Training_.departmentId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Training_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Training_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Training_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Training_.lastModifiedBy));
            }
        }
        return specification;
    }
}
