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
import com.techvg.hrms.domain.Salary;
import com.techvg.hrms.domain.Salary_;
import com.techvg.hrms.repository.SalaryRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.SalaryCriteria;
import com.techvg.hrms.service.dto.SalaryDTO;
import com.techvg.hrms.service.mapper.SalaryMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Salary} entities in the database.
 * The main input is a {@link SalaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalaryDTO} or a {@link Page} of {@link SalaryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalaryQueryService extends QueryService<Salary> {

    private final Logger log = LoggerFactory.getLogger(SalaryQueryService.class);

    private final SalaryRepository salaryRepository;

    private final SalaryMapper salaryMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public SalaryQueryService(SalaryRepository salaryRepository, SalaryMapper salaryMapper) {
        this.salaryRepository = salaryRepository;
        this.salaryMapper = salaryMapper;
    }

    /**
     * Return a {@link List} of {@link SalaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalaryDTO> findByCriteria(SalaryCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Salary> specification = createSpecification(criteria);
        return salaryMapper.toDto(salaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaryDTO> findByCriteria(SalaryCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Salary> specification = createSpecification(criteria);
        return salaryRepository.findAll(specification, page).map(salaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalaryCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Salary> specification = createSpecification(criteria);
        return salaryRepository.count(specification);
    }

    /**
     * Function to convert {@link SalaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Salary> createSpecification(SalaryCriteria criteria) {
        Specification<Salary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Salary_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Salary_.type));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Salary_.amount));
            }
            if (criteria.getIsdeduction() != null) {
                specification = specification.and(buildSpecification(criteria.getIsdeduction(), Salary_.isdeduction));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMonth(), Salary_.month));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), Salary_.year));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Salary_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Salary_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Salary_.companyId));
            }
            if (criteria.getEarningAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEarningAmount(), Salary_.earningAmount));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Salary_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Salary_.lastModifiedBy));
            }
        }
        return specification;
    }
}
