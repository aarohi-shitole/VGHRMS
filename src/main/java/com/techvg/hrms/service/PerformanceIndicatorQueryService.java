package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.PerformanceIndicator;
import com.techvg.hrms.repository.PerformanceIndicatorRepository;
import com.techvg.hrms.service.criteria.PerformanceIndicatorCriteria;
import com.techvg.hrms.service.dto.PerformanceIndicatorDTO;
import com.techvg.hrms.service.mapper.PerformanceIndicatorMapper;
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
 * Service for executing complex queries for {@link PerformanceIndicator} entities in the database.
 * The main input is a {@link PerformanceIndicatorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PerformanceIndicatorDTO} or a {@link Page} of {@link PerformanceIndicatorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceIndicatorQueryService extends QueryService<PerformanceIndicator> {

    private final Logger log = LoggerFactory.getLogger(PerformanceIndicatorQueryService.class);

    private final PerformanceIndicatorRepository performanceIndicatorRepository;

    private final PerformanceIndicatorMapper performanceIndicatorMapper;

    public PerformanceIndicatorQueryService(
        PerformanceIndicatorRepository performanceIndicatorRepository,
        PerformanceIndicatorMapper performanceIndicatorMapper
    ) {
        this.performanceIndicatorRepository = performanceIndicatorRepository;
        this.performanceIndicatorMapper = performanceIndicatorMapper;
    }

    /**
     * Return a {@link List} of {@link PerformanceIndicatorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PerformanceIndicatorDTO> findByCriteria(PerformanceIndicatorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PerformanceIndicator> specification = createSpecification(criteria);
        return performanceIndicatorMapper.toDto(performanceIndicatorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PerformanceIndicatorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceIndicatorDTO> findByCriteria(PerformanceIndicatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceIndicator> specification = createSpecification(criteria);
        return performanceIndicatorRepository.findAll(specification, page).map(performanceIndicatorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceIndicatorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceIndicator> specification = createSpecification(criteria);
        return performanceIndicatorRepository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceIndicatorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceIndicator> createSpecification(PerformanceIndicatorCriteria criteria) {
        Specification<PerformanceIndicator> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceIndicator_.id));
            }
            if (criteria.getDesignationId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDesignationId(), PerformanceIndicator_.designationId));
            }
            if (criteria.getIndicatorValue() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIndicatorValue(), PerformanceIndicator_.indicatorValue));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PerformanceIndicator_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), PerformanceIndicator_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PerformanceIndicator_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PerformanceIndicator_.lastModifiedBy));
            }
            if (criteria.getMasterPerformanceIndicatorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMasterPerformanceIndicatorId(),
                            root ->
                                root
                                    .join(PerformanceIndicator_.masterPerformanceIndicator, JoinType.LEFT)
                                    .get(MasterPerformanceIndicator_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
