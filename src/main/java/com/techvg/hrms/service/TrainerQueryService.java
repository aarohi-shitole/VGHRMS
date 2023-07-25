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
import com.techvg.hrms.domain.Trainer;
import com.techvg.hrms.domain.Trainer_;
import com.techvg.hrms.repository.TrainerRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TrainerCriteria;
import com.techvg.hrms.service.dto.TrainerDTO;
import com.techvg.hrms.service.mapper.TrainerMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Trainer} entities in the database.
 * The main input is a {@link TrainerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainerDTO} or a {@link Page} of {@link TrainerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainerQueryService extends QueryService<Trainer> {

    private final Logger log = LoggerFactory.getLogger(TrainerQueryService.class);

    private final TrainerRepository trainerRepository;

    private final TrainerMapper trainerMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TrainerQueryService(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    /**
     * Return a {@link List} of {@link TrainerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainerDTO> findByCriteria(TrainerCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Trainer> specification = createSpecification(criteria);
        return trainerMapper.toDto(trainerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainerDTO> findByCriteria(TrainerCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Trainer> specification = createSpecification(criteria);
        return trainerRepository.findAll(specification, page).map(trainerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainerCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Trainer> specification = createSpecification(criteria);
        return trainerRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Trainer> createSpecification(TrainerCriteria criteria) {
        Specification<Trainer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Trainer_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Trainer_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Trainer_.lastName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Trainer_.description));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), Trainer_.role));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Trainer_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Trainer_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Trainer_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Trainer_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Trainer_.lastModifiedBy));
            }
        }
        return specification;
    }
}
