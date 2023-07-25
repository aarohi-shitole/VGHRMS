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
import com.techvg.hrms.domain.TaxRegime;
import com.techvg.hrms.domain.TaxRegime_;
import com.techvg.hrms.repository.TaxRegimeRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TaxRegimeCriteria;
import com.techvg.hrms.service.dto.TaxRegimeDTO;
import com.techvg.hrms.service.mapper.TaxRegimeMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TaxRegime} entities in the database.
 * The main input is a {@link TaxRegimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaxRegimeDTO} or a {@link Page} of {@link TaxRegimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaxRegimeQueryService extends QueryService<TaxRegime> {

    private final Logger log = LoggerFactory.getLogger(TaxRegimeQueryService.class);

    private final TaxRegimeRepository taxRegimeRepository;

    private final TaxRegimeMapper taxRegimeMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TaxRegimeQueryService(TaxRegimeRepository taxRegimeRepository, TaxRegimeMapper taxRegimeMapper) {
        this.taxRegimeRepository = taxRegimeRepository;
        this.taxRegimeMapper = taxRegimeMapper;
    }

    /**
     * Return a {@link List} of {@link TaxRegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaxRegimeDTO> findByCriteria(TaxRegimeCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TaxRegime> specification = createSpecification(criteria);
        return taxRegimeMapper.toDto(taxRegimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaxRegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxRegimeDTO> findByCriteria(TaxRegimeCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaxRegime> specification = createSpecification(criteria);
        return taxRegimeRepository.findAll(specification, page).map(taxRegimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaxRegimeCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TaxRegime> specification = createSpecification(criteria);
        return taxRegimeRepository.count(specification);
    }

    /**
     * Function to convert {@link TaxRegimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaxRegime> createSpecification(TaxRegimeCriteria criteria) {
        Specification<TaxRegime> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaxRegime_.id));
            }
            if (criteria.getTaxRegimeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxRegimeName(), TaxRegime_.taxRegimeName));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TaxRegime_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TaxRegime_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TaxRegime_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TaxRegime_.lastModifiedBy));
            }
        }
        return specification;
    }
}
