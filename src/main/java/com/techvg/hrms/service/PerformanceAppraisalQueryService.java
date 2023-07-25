package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.repository.PerformanceAppraisalRepository;
import com.techvg.hrms.service.criteria.PerformanceAppraisalCriteria;
import com.techvg.hrms.service.dto.PerformanceAppraisalDTO;
import com.techvg.hrms.service.mapper.PerformanceAppraisalMapper;
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
 * Service for executing complex queries for {@link PerformanceAppraisal} entities in the database.
 * The main input is a {@link PerformanceAppraisalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerformanceAppraisalDTO} or a {@link Page} of {@link PerformanceAppraisalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceAppraisalQueryService extends QueryService<PerformanceAppraisal> {

    private final Logger log = LoggerFactory.getLogger(PerformanceAppraisalQueryService.class);

    private final PerformanceAppraisalRepository performanceAppraisalRepository;

    private final PerformanceAppraisalMapper performanceAppraisalMapper;

    public PerformanceAppraisalQueryService(
        PerformanceAppraisalRepository performanceAppraisalRepository,
        PerformanceAppraisalMapper performanceAppraisalMapper
    ) {
        this.performanceAppraisalRepository = performanceAppraisalRepository;
        this.performanceAppraisalMapper = performanceAppraisalMapper;
    }

    /**
     * Return a {@link List} of {@link PerformanceAppraisalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerformanceAppraisalDTO> findByCriteria(PerformanceAppraisalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerformanceAppraisal> specification = createSpecification(criteria);
        return performanceAppraisalMapper.toDto(performanceAppraisalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PerformanceAppraisalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceAppraisalDTO> findByCriteria(PerformanceAppraisalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceAppraisal> specification = createSpecification(criteria);
        return performanceAppraisalRepository.findAll(specification, page).map(performanceAppraisalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceAppraisalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceAppraisal> specification = createSpecification(criteria);
        return performanceAppraisalRepository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceAppraisalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceAppraisal> createSpecification(PerformanceAppraisalCriteria criteria) {
        Specification<PerformanceAppraisal> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceAppraisal_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), PerformanceAppraisal_.employeeId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PerformanceAppraisal_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), PerformanceAppraisal_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PerformanceAppraisal_.lastModified));
            }
            if (criteria.getAppraisalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppraisalDate(), PerformanceAppraisal_.appraisalDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PerformanceAppraisal_.lastModifiedBy));
            }
            if (criteria.getAppraisalReviewId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAppraisalReviewId(),
                            root -> root.join(PerformanceAppraisal_.appraisalReview, JoinType.LEFT).get(AppraisalReview_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
