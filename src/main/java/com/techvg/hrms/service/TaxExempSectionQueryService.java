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
import com.techvg.hrms.domain.TaxExempSection;
import com.techvg.hrms.domain.TaxExempSection_;
import com.techvg.hrms.repository.TaxExempSectionRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TaxExempSectionCriteria;
import com.techvg.hrms.service.dto.TaxExempSectionDTO;
import com.techvg.hrms.service.mapper.TaxExempSectionMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TaxExempSection} entities in the database.
 * The main input is a {@link TaxExempSectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaxExempSectionDTO} or a {@link Page} of {@link TaxExempSectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaxExempSectionQueryService extends QueryService<TaxExempSection> {

    private final Logger log = LoggerFactory.getLogger(TaxExempSectionQueryService.class);

    private final TaxExempSectionRepository taxExempSectionRepository;

    private final TaxExempSectionMapper taxExempSectionMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TaxExempSectionQueryService(TaxExempSectionRepository taxExempSectionRepository, TaxExempSectionMapper taxExempSectionMapper) {
        this.taxExempSectionRepository = taxExempSectionRepository;
        this.taxExempSectionMapper = taxExempSectionMapper;
    }

    /**
     * Return a {@link List} of {@link TaxExempSectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaxExempSectionDTO> findByCriteria(TaxExempSectionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TaxExempSection> specification = createSpecification(criteria);
        return taxExempSectionMapper.toDto(taxExempSectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaxExempSectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxExempSectionDTO> findByCriteria(TaxExempSectionCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaxExempSection> specification = createSpecification(criteria);
        return taxExempSectionRepository.findAll(specification, page).map(taxExempSectionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaxExempSectionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TaxExempSection> specification = createSpecification(criteria);
        return taxExempSectionRepository.count(specification);
    }

    /**
     * Function to convert {@link TaxExempSectionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaxExempSection> createSpecification(TaxExempSectionCriteria criteria) {
        Specification<TaxExempSection> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaxExempSection_.id));
            }
            if (criteria.getTaxExempSection() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTaxExempSection(), TaxExempSection_.taxExempSection));
            }
            if (criteria.getTaxExempSectionId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTaxExempSectionId(), TaxExempSection_.taxExempSectionId));
            }
            if (criteria.getMaxlimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxlimit(), TaxExempSection_.maxlimit));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TaxExempSection_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TaxExempSection_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TaxExempSection_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TaxExempSection_.lastModifiedBy));
            }
        }
        return specification;
    }
}
