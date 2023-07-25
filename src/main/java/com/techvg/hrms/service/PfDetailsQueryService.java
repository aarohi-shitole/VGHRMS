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
import com.techvg.hrms.domain.PfDetails;
import com.techvg.hrms.domain.PfDetails_;
import com.techvg.hrms.repository.PfDetailsRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.PfDetailsCriteria;
import com.techvg.hrms.service.dto.PfDetailsDTO;
import com.techvg.hrms.service.mapper.PfDetailsMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PfDetails} entities in the database.
 * The main input is a {@link PfDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PfDetailsDTO} or a {@link Page} of {@link PfDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PfDetailsQueryService extends QueryService<PfDetails> {

    private final Logger log = LoggerFactory.getLogger(PfDetailsQueryService.class);

    private final PfDetailsRepository pfDetailsRepository;

    private final PfDetailsMapper pfDetailsMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public PfDetailsQueryService(PfDetailsRepository pfDetailsRepository, PfDetailsMapper pfDetailsMapper) {
        this.pfDetailsRepository = pfDetailsRepository;
        this.pfDetailsMapper = pfDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link PfDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PfDetailsDTO> findByCriteria(PfDetailsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<PfDetails> specification = createSpecification(criteria);
        return pfDetailsMapper.toDto(pfDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PfDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PfDetailsDTO> findByCriteria(PfDetailsCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PfDetails> specification = createSpecification(criteria);
        return pfDetailsRepository.findAll(specification, page).map(pfDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PfDetailsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<PfDetails> specification = createSpecification(criteria);
        return pfDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link PfDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PfDetails> createSpecification(PfDetailsCriteria criteria) {
        Specification<PfDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PfDetails_.id));
            }
            if (criteria.getIsPfContribution() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPfContribution(), PfDetails_.isPfContribution));
            }
            if (criteria.getPfNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPfNumber(), PfDetails_.pfNumber));
            }
            if (criteria.getPfRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPfRate(), PfDetails_.pfRate));
            }
            if (criteria.getAdditionalPfRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalPfRate(), PfDetails_.additionalPfRate));
            }
            if (criteria.getTotalPfRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPfRate(), PfDetails_.totalPfRate));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), PfDetails_.employeeId));
            }
            if (criteria.getReEnumerationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReEnumerationId(), PfDetails_.reEnumerationId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PfDetails_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), PfDetails_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PfDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PfDetails_.lastModifiedBy));
            }
        }
        return specification;
    }
}
