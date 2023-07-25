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
import com.techvg.hrms.domain.PayrollAdditions;
import com.techvg.hrms.domain.PayrollAdditions_;
import com.techvg.hrms.repository.PayrollAdditionsRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.PayrollAdditionsCriteria;
import com.techvg.hrms.service.dto.PayrollAdditionsDTO;
import com.techvg.hrms.service.mapper.PayrollAdditionsMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PayrollAdditions} entities in the database.
 * The main input is a {@link PayrollAdditionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PayrollAdditionsDTO} or a {@link Page} of {@link PayrollAdditionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PayrollAdditionsQueryService extends QueryService<PayrollAdditions> {

    private final Logger log = LoggerFactory.getLogger(PayrollAdditionsQueryService.class);

    private final PayrollAdditionsRepository payrollAdditionsRepository;

    private final PayrollAdditionsMapper payrollAdditionsMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public PayrollAdditionsQueryService(
        PayrollAdditionsRepository payrollAdditionsRepository,
        PayrollAdditionsMapper payrollAdditionsMapper
    ) {
        this.payrollAdditionsRepository = payrollAdditionsRepository;
        this.payrollAdditionsMapper = payrollAdditionsMapper;
    }

    /**
     * Return a {@link List} of {@link PayrollAdditionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PayrollAdditionsDTO> findByCriteria(PayrollAdditionsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<PayrollAdditions> specification = createSpecification(criteria);
        return payrollAdditionsMapper.toDto(payrollAdditionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PayrollAdditionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PayrollAdditionsDTO> findByCriteria(PayrollAdditionsCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PayrollAdditions> specification = createSpecification(criteria);
        return payrollAdditionsRepository.findAll(specification, page).map(payrollAdditionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PayrollAdditionsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<PayrollAdditions> specification = createSpecification(criteria);
        return payrollAdditionsRepository.count(specification);
    }

    /**
     * Function to convert {@link PayrollAdditionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PayrollAdditions> createSpecification(PayrollAdditionsCriteria criteria) {
        Specification<PayrollAdditions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PayrollAdditions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PayrollAdditions_.name));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), PayrollAdditions_.category));
            }
            if (criteria.getHasUnitCal() != null) {
                specification = specification.and(buildSpecification(criteria.getHasUnitCal(), PayrollAdditions_.hasUnitCal));
            }
            if (criteria.getUnitAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitAmount(), PayrollAdditions_.unitAmount));
            }
            if (criteria.getAssignType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignType(), PayrollAdditions_.assignType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PayrollAdditions_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), PayrollAdditions_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), PayrollAdditions_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PayrollAdditions_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PayrollAdditions_.lastModifiedBy));
            }
        }
        return specification;
    }
}
