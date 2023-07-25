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
import com.techvg.hrms.domain.EmployeeExemption;
import com.techvg.hrms.domain.EmployeeExemption_;
import com.techvg.hrms.repository.EmployeeExemptionRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.EmployeeExemptionCriteria;
import com.techvg.hrms.service.dto.EmployeeExemptionDTO;
import com.techvg.hrms.service.mapper.EmployeeExemptionMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmployeeExemption} entities in the database.
 * The main input is a {@link EmployeeExemptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeExemptionDTO} or a {@link Page} of {@link EmployeeExemptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeExemptionQueryService extends QueryService<EmployeeExemption> {

    private final Logger log = LoggerFactory.getLogger(EmployeeExemptionQueryService.class);

    private final EmployeeExemptionRepository employeeExemptionRepository;

    private final EmployeeExemptionMapper employeeExemptionMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public EmployeeExemptionQueryService(
        EmployeeExemptionRepository employeeExemptionRepository,
        EmployeeExemptionMapper employeeExemptionMapper
    ) {
        this.employeeExemptionRepository = employeeExemptionRepository;
        this.employeeExemptionMapper = employeeExemptionMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeExemptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeExemptionDTO> findByCriteria(EmployeeExemptionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeExemption> specification = createSpecification(criteria);
        return employeeExemptionMapper.toDto(employeeExemptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeExemptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeExemptionDTO> findByCriteria(EmployeeExemptionCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeExemption> specification = createSpecification(criteria);
        return employeeExemptionRepository.findAll(specification, page).map(employeeExemptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeExemptionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeExemption> specification = createSpecification(criteria);
        return employeeExemptionRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeExemptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeExemption> createSpecification(EmployeeExemptionCriteria criteria) {
        Specification<EmployeeExemption> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeExemption_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), EmployeeExemption_.amount));
            }
            if (criteria.getTaxExempSectionId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTaxExempSectionId(), EmployeeExemption_.taxExempSectionId));
            }
            if (criteria.getExemptionStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExemptionStatus(), EmployeeExemption_.exemptionStatus));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), EmployeeExemption_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), EmployeeExemption_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), EmployeeExemption_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmployeeExemption_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmployeeExemption_.lastModifiedBy));
            }
        }
        return specification;
    }
}
