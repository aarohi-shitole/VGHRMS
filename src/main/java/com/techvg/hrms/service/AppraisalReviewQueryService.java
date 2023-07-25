package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.repository.AppraisalReviewRepository;
import com.techvg.hrms.service.criteria.AppraisalReviewCriteria;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.mapper.AppraisalReviewMapper;
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
 * Service for executing complex queries for {@link AppraisalReview} entities in the database.
 * The main input is a {@link AppraisalReviewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppraisalReviewDTO} or a {@link Page} of {@link AppraisalReviewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppraisalReviewQueryService extends QueryService<AppraisalReview> {

    private final Logger log = LoggerFactory.getLogger(AppraisalReviewQueryService.class);

    private final AppraisalReviewRepository appraisalReviewRepository;

    private final AppraisalReviewMapper appraisalReviewMapper;

    public AppraisalReviewQueryService(AppraisalReviewRepository appraisalReviewRepository, AppraisalReviewMapper appraisalReviewMapper) {
        this.appraisalReviewRepository = appraisalReviewRepository;
        this.appraisalReviewMapper = appraisalReviewMapper;
    }

    /**
     * Return a {@link List} of {@link AppraisalReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppraisalReviewDTO> findByCriteria(AppraisalReviewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AppraisalReview> specification = createSpecification(criteria);
        return appraisalReviewMapper.toDto(appraisalReviewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppraisalReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalReviewDTO> findByCriteria(AppraisalReviewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppraisalReview> specification = createSpecification(criteria);
        return appraisalReviewRepository.findAll(specification, page).map(appraisalReviewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppraisalReviewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AppraisalReview> specification = createSpecification(criteria);
        return appraisalReviewRepository.count(specification);
    }

    /**
     * Function to convert {@link AppraisalReviewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppraisalReview> createSpecification(AppraisalReviewCriteria criteria) {
        Specification<AppraisalReview> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AppraisalReview_.id));
            }
            if (criteria.getReportingOfficer() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReportingOfficer(), AppraisalReview_.reportingOfficer));
            }
            if (criteria.getAppraisalStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAppraisalStatus(), AppraisalReview_.appraisalStatus));
            }
            if (criteria.getRoDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoDesignation(), AppraisalReview_.roDesignation));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AppraisalReview_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), AppraisalReview_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AppraisalReview_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AppraisalReview_.lastModifiedBy));
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeId(), AppraisalReview_.employeId));
            }
        }
        return specification;
    }
}
