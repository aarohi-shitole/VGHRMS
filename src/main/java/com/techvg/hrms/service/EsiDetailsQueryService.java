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
import com.techvg.hrms.domain.EsiDetails;
import com.techvg.hrms.domain.EsiDetails_;
import com.techvg.hrms.repository.EsiDetailsRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.EsiDetailsCriteria;
import com.techvg.hrms.service.dto.EsiDetailsDTO;
import com.techvg.hrms.service.mapper.EsiDetailsMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EsiDetails} entities in the database.
 * The main input is a {@link EsiDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EsiDetailsDTO} or a {@link Page} of {@link EsiDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EsiDetailsQueryService extends QueryService<EsiDetails> {

    private final Logger log = LoggerFactory.getLogger(EsiDetailsQueryService.class);

    private final EsiDetailsRepository esiDetailsRepository;

    private final EsiDetailsMapper esiDetailsMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public EsiDetailsQueryService(EsiDetailsRepository esiDetailsRepository, EsiDetailsMapper esiDetailsMapper) {
        this.esiDetailsRepository = esiDetailsRepository;
        this.esiDetailsMapper = esiDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link EsiDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EsiDetailsDTO> findByCriteria(EsiDetailsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<EsiDetails> specification = createSpecification(criteria);
        return esiDetailsMapper.toDto(esiDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EsiDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EsiDetailsDTO> findByCriteria(EsiDetailsCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EsiDetails> specification = createSpecification(criteria);
        return esiDetailsRepository.findAll(specification, page).map(esiDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EsiDetailsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<EsiDetails> specification = createSpecification(criteria);
        return esiDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EsiDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EsiDetails> createSpecification(EsiDetailsCriteria criteria) {
        Specification<EsiDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EsiDetails_.id));
            }
            if (criteria.getIsEsiContribution() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEsiContribution(), EsiDetails_.isEsiContribution));
            }
            if (criteria.getEsiNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEsiNumber(), EsiDetails_.esiNumber));
            }
            if (criteria.getEsiRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEsiRate(), EsiDetails_.esiRate));
            }
            if (criteria.getAdditionalEsiRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalEsiRate(), EsiDetails_.additionalEsiRate));
            }
            if (criteria.getTotalEsiRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalEsiRate(), EsiDetails_.totalEsiRate));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), EsiDetails_.employeeId));
            }
            if (criteria.getReEnumerationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReEnumerationId(), EsiDetails_.reEnumerationId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), EsiDetails_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), EsiDetails_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EsiDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EsiDetails_.lastModifiedBy));
            }
        }
        return specification;
    }
}
