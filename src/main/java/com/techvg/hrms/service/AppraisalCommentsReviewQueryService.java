package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.AppraisalCommentsReview;
import com.techvg.hrms.repository.AppraisalCommentsReviewRepository;
import com.techvg.hrms.service.criteria.AppraisalCommentsReviewCriteria;
import com.techvg.hrms.service.dto.AppraisalCommentsReviewDTO;
import com.techvg.hrms.service.mapper.AppraisalCommentsReviewMapper;
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
 * Service for executing complex queries for {@link AppraisalCommentsReview} entities in the database.
 * The main input is a {@link AppraisalCommentsReviewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppraisalCommentsReviewDTO} or a {@link Page} of {@link AppraisalCommentsReviewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppraisalCommentsReviewQueryService extends QueryService<AppraisalCommentsReview> {

    private final Logger log = LoggerFactory.getLogger(AppraisalCommentsReviewQueryService.class);

    private final AppraisalCommentsReviewRepository appraisalCommentsReviewRepository;

    private final AppraisalCommentsReviewMapper appraisalCommentsReviewMapper;

    public AppraisalCommentsReviewQueryService(
        AppraisalCommentsReviewRepository appraisalCommentsReviewRepository,
        AppraisalCommentsReviewMapper appraisalCommentsReviewMapper
    ) {
        this.appraisalCommentsReviewRepository = appraisalCommentsReviewRepository;
        this.appraisalCommentsReviewMapper = appraisalCommentsReviewMapper;
    }

    /**
     * Return a {@link List} of {@link AppraisalCommentsReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppraisalCommentsReviewDTO> findByCriteria(AppraisalCommentsReviewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AppraisalCommentsReview> specification = createSpecification(criteria);
        return appraisalCommentsReviewMapper.toDto(appraisalCommentsReviewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppraisalCommentsReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalCommentsReviewDTO> findByCriteria(AppraisalCommentsReviewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppraisalCommentsReview> specification = createSpecification(criteria);
        return appraisalCommentsReviewRepository.findAll(specification, page).map(appraisalCommentsReviewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppraisalCommentsReviewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AppraisalCommentsReview> specification = createSpecification(criteria);
        return appraisalCommentsReviewRepository.count(specification);
    }

    /**
     * Function to convert {@link AppraisalCommentsReviewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppraisalCommentsReview> createSpecification(AppraisalCommentsReviewCriteria criteria) {
        Specification<AppraisalCommentsReview> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AppraisalCommentsReview_.id));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), AppraisalCommentsReview_.comment));
            }
            if (criteria.getCommentType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCommentType(), AppraisalCommentsReview_.commentType));
            }
            if (criteria.getAppraisalReviewId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAppraisalReviewId(), AppraisalCommentsReview_.appraisalReviewId));
            }
            if (criteria.getRefFormName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRefFormName(), AppraisalCommentsReview_.refFormName));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AppraisalCommentsReview_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), AppraisalCommentsReview_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), AppraisalCommentsReview_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AppraisalCommentsReview_.lastModifiedBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(AppraisalCommentsReview_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
