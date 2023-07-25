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
import com.techvg.hrms.domain.Promotion;
import com.techvg.hrms.domain.Promotion_;
import com.techvg.hrms.repository.PromotionRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.PromotionCriteria;
import com.techvg.hrms.service.dto.PromotionDTO;
import com.techvg.hrms.service.mapper.PromotionMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Promotion} entities in the database.
 * The main input is a {@link PromotionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PromotionDTO} or a {@link Page} of {@link PromotionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PromotionQueryService extends QueryService<Promotion> {

    private final Logger log = LoggerFactory.getLogger(PromotionQueryService.class);

    private final PromotionRepository promotionRepository;

    private final PromotionMapper promotionMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public PromotionQueryService(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    /**
     * Return a {@link List} of {@link PromotionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PromotionDTO> findByCriteria(PromotionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Promotion> specification = createSpecification(criteria);
        return promotionMapper.toDto(promotionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PromotionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PromotionDTO> findByCriteria(PromotionCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Promotion> specification = createSpecification(criteria);
        return promotionRepository.findAll(specification, page).map(promotionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PromotionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Promotion> specification = createSpecification(criteria);
        return promotionRepository.count(specification);
    }

    /**
     * Function to convert {@link PromotionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Promotion> createSpecification(PromotionCriteria criteria) {
        Specification<Promotion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Promotion_.id));
            }
            if (criteria.getPromotionFor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPromotionFor(), Promotion_.promotionFor));
            }
            if (criteria.getPromotedFrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPromotedFrom(), Promotion_.promotedFrom));
            }
            if (criteria.getPromotedTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPromotedTo(), Promotion_.promotedTo));
            }
            if (criteria.getPromotiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPromotiedDate(), Promotion_.promotiedDate));
            }
            if (criteria.getBranchId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBranchId(), Promotion_.branchId));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), Promotion_.departmentId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Promotion_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Promotion_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Promotion_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Promotion_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Promotion_.lastModifiedBy));
            }
        }
        return specification;
    }
}
