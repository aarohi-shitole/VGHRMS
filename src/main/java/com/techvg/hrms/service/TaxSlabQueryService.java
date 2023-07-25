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
import com.techvg.hrms.domain.TaxSlab;
import com.techvg.hrms.domain.TaxSlab_;
import com.techvg.hrms.repository.TaxSlabRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TaxSlabCriteria;
import com.techvg.hrms.service.dto.TaxSlabDTO;
import com.techvg.hrms.service.mapper.TaxSlabMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TaxSlab} entities in the database.
 * The main input is a {@link TaxSlabCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaxSlabDTO} or a {@link Page} of {@link TaxSlabDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaxSlabQueryService extends QueryService<TaxSlab> {

    private final Logger log = LoggerFactory.getLogger(TaxSlabQueryService.class);

    private final TaxSlabRepository taxSlabRepository;

    private final TaxSlabMapper taxSlabMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TaxSlabQueryService(TaxSlabRepository taxSlabRepository, TaxSlabMapper taxSlabMapper) {
        this.taxSlabRepository = taxSlabRepository;
        this.taxSlabMapper = taxSlabMapper;
    }

    /**
     * Return a {@link List} of {@link TaxSlabDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaxSlabDTO> findByCriteria(TaxSlabCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TaxSlab> specification = createSpecification(criteria);
        return taxSlabMapper.toDto(taxSlabRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaxSlabDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxSlabDTO> findByCriteria(TaxSlabCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaxSlab> specification = createSpecification(criteria);
        return taxSlabRepository.findAll(specification, page).map(taxSlabMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaxSlabCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TaxSlab> specification = createSpecification(criteria);
        return taxSlabRepository.count(specification);
    }

    /**
     * Function to convert {@link TaxSlabCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaxSlab> createSpecification(TaxSlabCriteria criteria) {
        Specification<TaxSlab> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaxSlab_.id));
            }
            if (criteria.getSlab() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlab(), TaxSlab_.slab));
            }
            if (criteria.getAmtFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmtFrom(), TaxSlab_.amtFrom));
            }
            if (criteria.getAmtTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmtTo(), TaxSlab_.amtTo));
            }
            if (criteria.getTaxPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxPercentage(), TaxSlab_.taxPercentage));
            }
            if (criteria.getTaxRegimeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxRegimeId(), TaxSlab_.taxRegimeId));
            }
            if (criteria.getTaxSlabId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxSlabId(), TaxSlab_.taxSlabId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TaxSlab_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TaxSlab_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TaxSlab_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TaxSlab_.lastModifiedBy));
            }
        }
        return specification;
    }
}
