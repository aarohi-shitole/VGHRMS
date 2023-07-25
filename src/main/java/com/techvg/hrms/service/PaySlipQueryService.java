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
import com.techvg.hrms.domain.PaySlip;
import com.techvg.hrms.domain.PaySlip_;
import com.techvg.hrms.repository.PaySlipRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.PaySlipCriteria;
import com.techvg.hrms.service.dto.PaySlipDTO;
import com.techvg.hrms.service.mapper.PaySlipMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PaySlip} entities in the database.
 * The main input is a {@link PaySlipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaySlipDTO} or a {@link Page} of {@link PaySlipDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaySlipQueryService extends QueryService<PaySlip> {

    private final Logger log = LoggerFactory.getLogger(PaySlipQueryService.class);

    private final PaySlipRepository paySlipRepository;

    private final PaySlipMapper paySlipMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public PaySlipQueryService(PaySlipRepository paySlipRepository, PaySlipMapper paySlipMapper) {
        this.paySlipRepository = paySlipRepository;
        this.paySlipMapper = paySlipMapper;
    }

    /**
     * Return a {@link List} of {@link PaySlipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaySlipDTO> findByCriteria(PaySlipCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<PaySlip> specification = createSpecification(criteria);
        return paySlipMapper.toDto(paySlipRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaySlipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaySlipDTO> findByCriteria(PaySlipCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaySlip> specification = createSpecification(criteria);
        return paySlipRepository.findAll(specification, page).map(paySlipMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaySlipCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<PaySlip> specification = createSpecification(criteria);
        return paySlipRepository.count(specification);
    }

    /**
     * Function to convert {@link PaySlipCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaySlip> createSpecification(PaySlipCriteria criteria) {
        Specification<PaySlip> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaySlip_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMonth(), PaySlip_.month));
            }
            if (criteria.getSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalary(), PaySlip_.salary));
            }
            if (criteria.getBranchId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBranchId(), PaySlip_.branchId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PaySlip_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), PaySlip_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), PaySlip_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PaySlip_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PaySlip_.lastModifiedBy));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), PaySlip_.year));
            }
            if (criteria.getSalaryStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalaryStatus(), PaySlip_.salaryStatus));
            }
            if (criteria.getActualGrossPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualGrossPay(), PaySlip_.actualGrossPay));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PaySlip_.description));
            }
            if (criteria.getTotalEarning() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalEarning(), PaySlip_.totalEarning));
            }
            if (criteria.getTotalDeduction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDeduction(), PaySlip_.totalDeduction));
            }
            if (criteria.getLossOfPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLossOfPay(), PaySlip_.lossOfPay));
            }
            if (criteria.getPresentDays()!= null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresentDays(), PaySlip_.presentDays));
            }
            if (criteria.getSalaryInWords() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalaryInWords(), PaySlip_.salaryInWords));
            }
            if (criteria.getSalaryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryDate(), PaySlip_.salaryDate));
            }
        }
        return specification;
    }
}
