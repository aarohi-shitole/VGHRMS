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
import com.techvg.hrms.domain.CustomApprovar;
import com.techvg.hrms.domain.CustomApprovar_;
import com.techvg.hrms.repository.CustomApprovarRepository;
import com.techvg.hrms.service.criteria.CustomApprovarCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.CustomApprovarDTO;
import com.techvg.hrms.service.mapper.CustomApprovarMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link CustomApprovar} entities in the database.
 * The main input is a {@link CustomApprovarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomApprovarDTO} or a {@link Page} of {@link CustomApprovarDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomApprovarQueryService extends QueryService<CustomApprovar> {

    private final Logger log = LoggerFactory.getLogger(CustomApprovarQueryService.class);

    private final CustomApprovarRepository customApprovarRepository;

    private final CustomApprovarMapper customApprovarMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public CustomApprovarQueryService(CustomApprovarRepository customApprovarRepository, CustomApprovarMapper customApprovarMapper) {
        this.customApprovarRepository = customApprovarRepository;
        this.customApprovarMapper = customApprovarMapper;
    }

    /**
     * Return a {@link List} of {@link CustomApprovarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomApprovarDTO> findByCriteria(CustomApprovarCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomApprovar> specification = createSpecification(criteria);
        return customApprovarMapper.toDto(customApprovarRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomApprovarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomApprovarDTO> findByCriteria(CustomApprovarCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomApprovar> specification = createSpecification(criteria);
        return customApprovarRepository.findAll(specification, page).map(customApprovarMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomApprovarCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<CustomApprovar> specification = createSpecification(criteria);
        return customApprovarRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomApprovarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomApprovar> createSpecification(CustomApprovarCriteria criteria) {
        Specification<CustomApprovar> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomApprovar_.id));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeId(), CustomApprovar_.employeId));
            }
            if (criteria.getApprovalSettingId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getApprovalSettingId(), CustomApprovar_.approvalSettingId));
            }
            if (criteria.getSquence() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSquence(), CustomApprovar_.squence));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CustomApprovar_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), CustomApprovar_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), CustomApprovar_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomApprovar_.lastModifiedBy));
            }
        }
        return specification;
    }
}
