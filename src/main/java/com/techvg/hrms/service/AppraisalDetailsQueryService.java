package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.AppraisalDetails;
import com.techvg.hrms.repository.AppraisalDetailsRepository;
import com.techvg.hrms.service.criteria.AppraisalDetailsCriteria;
import com.techvg.hrms.service.dto.AppraisalDetailsDTO;
import com.techvg.hrms.service.mapper.AppraisalDetailsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AppraisalDetails} entities in the database.
 * The main input is a {@link AppraisalDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppraisalDetailsDTO} or a {@link Page} of {@link AppraisalDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppraisalDetailsQueryService extends QueryService<AppraisalDetails> {

    private final Logger log = LoggerFactory.getLogger(AppraisalDetailsQueryService.class);

    private final AppraisalDetailsRepository appraisalDetailsRepository;

    private final AppraisalDetailsMapper appraisalDetailsMapper;

    public AppraisalDetailsQueryService(
        AppraisalDetailsRepository appraisalDetailsRepository,
        AppraisalDetailsMapper appraisalDetailsMapper
    ) {
        this.appraisalDetailsRepository = appraisalDetailsRepository;
        this.appraisalDetailsMapper = appraisalDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link AppraisalDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppraisalDetailsDTO> findByCriteria(AppraisalDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AppraisalDetails> specification = createSpecification(criteria);
        return appraisalDetailsMapper.toDto(appraisalDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppraisalDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalDetailsDTO> findByCriteria(AppraisalDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppraisalDetails> specification = createSpecification(criteria);
        return appraisalDetailsRepository.findAll(specification, page).map(appraisalDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppraisalDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AppraisalDetails> specification = createSpecification(criteria);
        return appraisalDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link AppraisalDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppraisalDetails> createSpecification(AppraisalDetailsCriteria criteria) {
        Specification<AppraisalDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AppraisalDetails_.id));
            }
            if (criteria.getPerformanceAppraisalId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPerformanceAppraisalId(), AppraisalDetails_.performanceAppraisalId)
                    );
            }
            if (criteria.getExpectedValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpectedValue(), AppraisalDetails_.expectedValue));
            }
            if (criteria.getSetValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSetValue(), AppraisalDetails_.setValue));
            }

            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AppraisalDetails_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), AppraisalDetails_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AppraisalDetails_.lastModified));
            }

           
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AppraisalDetails_.lastModifiedBy));
            }
            if (criteria.getPerformanceIndicatorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformanceIndicatorId(),
                            root -> root.join(AppraisalDetails_.performanceIndicator, JoinType.LEFT).get(PerformanceIndicator_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
