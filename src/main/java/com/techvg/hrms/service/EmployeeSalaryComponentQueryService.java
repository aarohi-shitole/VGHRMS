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
import com.techvg.hrms.domain.EmployeeSalaryComponent;
import com.techvg.hrms.domain.EmployeeSalaryComponent_;
import com.techvg.hrms.repository.EmployeeSalaryComponentRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.EmployeeSalaryComponentCriteria;
import com.techvg.hrms.service.dto.EmployeeSalaryComponentDTO;
import com.techvg.hrms.service.mapper.EmployeeSalaryComponentMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmployeeSalaryComponent} entities in the database.
 * The main input is a {@link EmployeeSalaryComponentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSalaryComponentDTO} or a {@link Page} of {@link EmployeeSalaryComponentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSalaryComponentQueryService extends QueryService<EmployeeSalaryComponent> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryComponentQueryService.class);

    private final EmployeeSalaryComponentRepository employeeSalaryComponentRepository;

    private final EmployeeSalaryComponentMapper employeeSalaryComponentMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public EmployeeSalaryComponentQueryService(
        EmployeeSalaryComponentRepository employeeSalaryComponentRepository,
        EmployeeSalaryComponentMapper employeeSalaryComponentMapper
    ) {
        this.employeeSalaryComponentRepository = employeeSalaryComponentRepository;
        this.employeeSalaryComponentMapper = employeeSalaryComponentMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeSalaryComponentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSalaryComponentDTO> findByCriteria(EmployeeSalaryComponentCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSalaryComponent> specification = createSpecification(criteria);
        return employeeSalaryComponentMapper.toDto(employeeSalaryComponentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeSalaryComponentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSalaryComponentDTO> findByCriteria(EmployeeSalaryComponentCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSalaryComponent> specification = createSpecification(criteria);
        return employeeSalaryComponentRepository.findAll(specification, page).map(employeeSalaryComponentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeSalaryComponentCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeSalaryComponent> specification = createSpecification(criteria);
        return employeeSalaryComponentRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeSalaryComponentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeSalaryComponent> createSpecification(EmployeeSalaryComponentCriteria criteria) {
        Specification<EmployeeSalaryComponent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeSalaryComponent_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmployeeSalaryComponent_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), EmployeeSalaryComponent_.type));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), EmployeeSalaryComponent_.value));
            }
            if (criteria.getComponentTypeFlag() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getComponentTypeFlag(), EmployeeSalaryComponent_.componentTypeFlag));
            }
            if (criteria.getCatagory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCatagory(), EmployeeSalaryComponent_.catagory));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), EmployeeSalaryComponent_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), EmployeeSalaryComponent_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmployeeSalaryComponent_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmployeeSalaryComponent_.lastModifiedBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), EmployeeSalaryComponent_.employeeId));
            }
        }
        return specification;
    }
}
