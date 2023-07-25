package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.AppraisalEvaluation;
import com.techvg.hrms.repository.AppraisalEvaluationRepository;
import com.techvg.hrms.service.criteria.AppraisalEvaluationCriteria;
import com.techvg.hrms.service.dto.AppraisalEvaluationDTO;
import com.techvg.hrms.service.mapper.AppraisalEvaluationMapper;
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
 * Service for executing complex queries for {@link AppraisalEvaluation} entities in the database.
 * The main input is a {@link AppraisalEvaluationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppraisalEvaluationDTO} or a {@link Page} of {@link AppraisalEvaluationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppraisalEvaluationQueryService extends QueryService<AppraisalEvaluation> {

    private final Logger log = LoggerFactory.getLogger(AppraisalEvaluationQueryService.class);

    private final AppraisalEvaluationRepository appraisalEvaluationRepository;

    private final AppraisalEvaluationMapper appraisalEvaluationMapper;

    public AppraisalEvaluationQueryService(
        AppraisalEvaluationRepository appraisalEvaluationRepository,
        AppraisalEvaluationMapper appraisalEvaluationMapper
    ) {
        this.appraisalEvaluationRepository = appraisalEvaluationRepository;
        this.appraisalEvaluationMapper = appraisalEvaluationMapper;
    }

    /**
     * Return a {@link List} of {@link AppraisalEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppraisalEvaluationDTO> findByCriteria(AppraisalEvaluationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AppraisalEvaluation> specification = createSpecification(criteria);
        return appraisalEvaluationMapper.toDto(appraisalEvaluationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppraisalEvaluationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalEvaluationDTO> findByCriteria(AppraisalEvaluationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppraisalEvaluation> specification = createSpecification(criteria);
        return appraisalEvaluationRepository.findAll(specification, page).map(appraisalEvaluationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppraisalEvaluationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AppraisalEvaluation> specification = createSpecification(criteria);
        return appraisalEvaluationRepository.count(specification);
    }

    /**
     * Function to convert {@link AppraisalEvaluationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppraisalEvaluation> createSpecification(AppraisalEvaluationCriteria criteria) {
        Specification<AppraisalEvaluation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AppraisalEvaluation_.id));
            }
            if (criteria.getAnswerFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getAnswerFlag(), AppraisalEvaluation_.answerFlag));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AppraisalEvaluation_.description));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), AppraisalEvaluation_.employeeId));
            }
            if (criteria.getAppraisalReviewId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAppraisalReviewId(), AppraisalEvaluation_.appraisalReviewId));
            }
            if (criteria.getAvailablePoints() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAvailablePoints(), AppraisalEvaluation_.availablePoints));
            }
            if (criteria.getScoredPoints() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoredPoints(), AppraisalEvaluation_.scoredPoints));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), AppraisalEvaluation_.remark));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AppraisalEvaluation_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), AppraisalEvaluation_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AppraisalEvaluation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AppraisalEvaluation_.lastModifiedBy));
            }
            if (criteria.getAppraisalEvaluationParameterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAppraisalEvaluationParameterId(),
                            root ->
                                root
                                    .join(AppraisalEvaluation_.appraisalEvaluationParameter, JoinType.LEFT)
                                    .get(AppraisalEvaluationParameter_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
