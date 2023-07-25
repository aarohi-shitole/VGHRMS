package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.EmployeeGoalsReview;
import com.techvg.hrms.repository.EmployeeGoalsReviewRepository;
import com.techvg.hrms.service.criteria.EmployeeGoalsReviewCriteria;
import com.techvg.hrms.service.dto.EmployeeGoalsReviewDTO;
import com.techvg.hrms.service.mapper.EmployeeGoalsReviewMapper;
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
 * Service for executing complex queries for {@link EmployeeGoalsReview} entities in the database.
 * The main input is a {@link EmployeeGoalsReviewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeGoalsReviewDTO} or a {@link Page} of {@link EmployeeGoalsReviewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeGoalsReviewQueryService extends QueryService<EmployeeGoalsReview> {

    private final Logger log = LoggerFactory.getLogger(EmployeeGoalsReviewQueryService.class);

    private final EmployeeGoalsReviewRepository employeeGoalsReviewRepository;

    private final EmployeeGoalsReviewMapper employeeGoalsReviewMapper;

    public EmployeeGoalsReviewQueryService(
        EmployeeGoalsReviewRepository employeeGoalsReviewRepository,
        EmployeeGoalsReviewMapper employeeGoalsReviewMapper
    ) {
        this.employeeGoalsReviewRepository = employeeGoalsReviewRepository;
        this.employeeGoalsReviewMapper = employeeGoalsReviewMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeGoalsReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeGoalsReviewDTO> findByCriteria(EmployeeGoalsReviewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeGoalsReview> specification = createSpecification(criteria);
        return employeeGoalsReviewMapper.toDto(employeeGoalsReviewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeGoalsReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeGoalsReviewDTO> findByCriteria(EmployeeGoalsReviewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeGoalsReview> specification = createSpecification(criteria);
        return employeeGoalsReviewRepository.findAll(specification, page).map(employeeGoalsReviewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeGoalsReviewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeGoalsReview> specification = createSpecification(criteria);
        return employeeGoalsReviewRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeGoalsReviewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeGoalsReview> createSpecification(EmployeeGoalsReviewCriteria criteria) {
        Specification<EmployeeGoalsReview> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeGoalsReview_.id));
            }
            if (criteria.getGoalDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getGoalDescription(), EmployeeGoalsReview_.goalDescription));
            }
            if (criteria.getGoalCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoalCategory(), EmployeeGoalsReview_.goalCategory));
            }
            if (criteria.getGoaltype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoaltype(), EmployeeGoalsReview_.goaltype));
            }
            if (criteria.getGoalSetBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoalSetBy(), EmployeeGoalsReview_.goalSetBy));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), EmployeeGoalsReview_.employeeId));
            }
            if (criteria.getAppraisalReviewId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAppraisalReviewId(), EmployeeGoalsReview_.appraisalReviewId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), EmployeeGoalsReview_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), EmployeeGoalsReview_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmployeeGoalsReview_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmployeeGoalsReview_.lastModifiedBy));
            }
        }
        return specification;
    }
}
