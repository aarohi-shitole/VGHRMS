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
import com.techvg.hrms.domain.Deduction;
import com.techvg.hrms.domain.Deduction_;
import com.techvg.hrms.repository.DeductionRepository;
import com.techvg.hrms.service.criteria.DeductionCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.DeductionDTO;
import com.techvg.hrms.service.mapper.DeductionMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Deduction} entities in the database.
 * The main input is a {@link DeductionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeductionDTO} or a {@link Page} of {@link DeductionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeductionQueryService extends QueryService<Deduction> {

    private final Logger log = LoggerFactory.getLogger(DeductionQueryService.class);

    private final DeductionRepository deductionRepository;

    private final DeductionMapper deductionMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public DeductionQueryService(DeductionRepository deductionRepository, DeductionMapper deductionMapper) {
        this.deductionRepository = deductionRepository;
        this.deductionMapper = deductionMapper;
    }

    /**
     * Return a {@link List} of {@link DeductionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeductionDTO> findByCriteria(DeductionCriteria criteria) {
    	 criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Deduction> specification = createSpecification(criteria);
        return deductionMapper.toDto(deductionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeductionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeductionDTO> findByCriteria(DeductionCriteria criteria, Pageable page) {
    	 criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Deduction> specification = createSpecification(criteria);
        return deductionRepository.findAll(specification, page).map(deductionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeductionCriteria criteria) {
    	 criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Deduction> specification = createSpecification(criteria);
        return deductionRepository.count(specification);
    }

    /**
     * Function to convert {@link DeductionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Deduction> createSpecification(DeductionCriteria criteria) {
        Specification<Deduction> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Deduction_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Deduction_.name));
            }
            if (criteria.getHasUnitCal() != null) {
                specification = specification.and(buildSpecification(criteria.getHasUnitCal(), Deduction_.hasUnitCal));
            }
            if (criteria.getUnitAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitAmount(), Deduction_.unitAmount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Deduction_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Deduction_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Deduction_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Deduction_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Deduction_.lastModifiedBy));
            }
        }
        return specification;
    }
}
