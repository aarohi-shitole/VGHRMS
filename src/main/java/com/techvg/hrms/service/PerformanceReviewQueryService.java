package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.PerformanceReview;
import com.techvg.hrms.repository.PerformanceReviewRepository;
import com.techvg.hrms.service.criteria.PerformanceReviewCriteria;
import com.techvg.hrms.service.dto.PerformanceReviewDTO;
import com.techvg.hrms.service.mapper.PerformanceReviewMapper;
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
 * Service for executing complex queries for {@link PerformanceReview} entities in the database.
 * The main input is a {@link PerformanceReviewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerformanceReviewDTO} or a {@link Page} of {@link PerformanceReviewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceReviewQueryService extends QueryService<PerformanceReview> {

    private final Logger log = LoggerFactory.getLogger(PerformanceReviewQueryService.class);

    private final PerformanceReviewRepository performanceReviewRepository;

    private final PerformanceReviewMapper performanceReviewMapper;

    public PerformanceReviewQueryService(
        PerformanceReviewRepository performanceReviewRepository,
        PerformanceReviewMapper performanceReviewMapper
    ) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.performanceReviewMapper = performanceReviewMapper;
    }

    /**
     * Return a {@link List} of {@link PerformanceReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerformanceReviewDTO> findByCriteria(PerformanceReviewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerformanceReview> specification = createSpecification(criteria);
        return performanceReviewMapper.toDto(performanceReviewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PerformanceReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceReviewDTO> findByCriteria(PerformanceReviewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceReview> specification = createSpecification(criteria);
        return performanceReviewRepository.findAll(specification, page).map(performanceReviewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceReviewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceReview> specification = createSpecification(criteria);
        return performanceReviewRepository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceReviewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceReview> createSpecification(PerformanceReviewCriteria criteria) {
        Specification<PerformanceReview> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceReview_.id));
            }
            if (criteria.getAppraisalReviewId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAppraisalReviewId(), PerformanceReview_.appraisalReviewId));
            }
            if (criteria.getEmployeeRemark() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmployeeRemark(), PerformanceReview_.employeeRemark));
            }
            if (criteria.getAppraiserRemark() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAppraiserRemark(), PerformanceReview_.appraiserRemark));
            }
            if (criteria.getReviewerRemark() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReviewerRemark(), PerformanceReview_.reviewerRemark));
            }
            if (criteria.getSelfScored() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSelfScored(), PerformanceReview_.selfScored));
            }
            if (criteria.getScoredByAppraiser() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getScoredByAppraiser(), PerformanceReview_.scoredByAppraiser));
            }
            if (criteria.getScoredByReviewer() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getScoredByReviewer(), PerformanceReview_.scoredByReviewer));
            }
            if (criteria.getAppraisalStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAppraisalStatus(), PerformanceReview_.appraisalStatus));
            }
            if (criteria.getSubmissionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmissionDate(), PerformanceReview_.submissionDate));
            }
            if (criteria.getAppraisalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppraisalDate(), PerformanceReview_.appraisalDate));
            }
            if (criteria.getReviewDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReviewDate(), PerformanceReview_.reviewDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PerformanceReview_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), PerformanceReview_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PerformanceReview_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PerformanceReview_.lastModifiedBy));
            }
            if (criteria.getTarget() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarget(), PerformanceReview_.target));
            }
            if (criteria.getTargetAchived() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTargetAchived(), PerformanceReview_.targetAchived));
            }
            if (criteria.getPerformanceIndicatorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPerformanceIndicatorId(),
                            root -> root.join(PerformanceReview_.performanceIndicator, JoinType.LEFT).get(PerformanceIndicator_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
