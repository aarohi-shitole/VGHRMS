package com.techvg.hrms.service;

import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.hrms.domain.Deduction_;
// for static metamodels
import com.techvg.hrms.domain.WorkingHours;
import com.techvg.hrms.domain.WorkingHours_;
import com.techvg.hrms.repository.WorkingHoursRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.WorkingHoursCriteria;
import com.techvg.hrms.service.dto.WorkingHoursDTO;
import com.techvg.hrms.service.mapper.WorkingHoursMapper;

import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.DoubleFilter;

/**
 * Service for executing complex queries for {@link WorkingHours} entities in the database.
 * The main input is a {@link WorkingHoursCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkingHoursDTO} or a {@link Page} of {@link WorkingHoursDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkingHoursQueryService extends QueryService<WorkingHours> {

    private final Logger log = LoggerFactory.getLogger(WorkingHoursQueryService.class);

    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursMapper workingHoursMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public WorkingHoursQueryService(WorkingHoursRepository workingHoursRepository, WorkingHoursMapper workingHoursMapper) {
        this.workingHoursRepository = workingHoursRepository;
        this.workingHoursMapper = workingHoursMapper;
    }

    /**
     * Return a {@link List} of {@link WorkingHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkingHoursDTO> findByCriteria(WorkingHoursCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<WorkingHours> specification = createSpecification(criteria);
        return workingHoursMapper.toDto(workingHoursRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkingHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkingHoursDTO> findByCriteria(WorkingHoursCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkingHours> specification = createSpecification(criteria);
        return workingHoursRepository.findAll(specification, page).map(workingHoursMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkingHoursCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<WorkingHours> specification = createSpecification(criteria);
        return workingHoursRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkingHoursCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkingHours> createSpecification(WorkingHoursCriteria criteria) {
        Specification<WorkingHours> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkingHours_.id));
            }
            if (criteria.getNoOfHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfHours(), WorkingHours_.noOfHours));
            }
            if (criteria.getEmploymentTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmploymentTypeId(), WorkingHours_.employmentTypeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), WorkingHours_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), WorkingHours_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), WorkingHours_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WorkingHours_.lastModifiedBy));
            }
            if (criteria.getRefTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefTable(), WorkingHours_.refTable));
            }
            if (criteria.getRefTableId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefTableId(), WorkingHours_.refTableId));
            }
        }
        return specification;
    }
}
