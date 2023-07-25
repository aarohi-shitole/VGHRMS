package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.MasterPerformanceIndicator;
import com.techvg.hrms.repository.MasterPerformanceIndicatorRepository;
import com.techvg.hrms.service.criteria.MasterPerformanceIndicatorCriteria;
import com.techvg.hrms.service.dto.MasterPerformanceIndicatorDTO;
import com.techvg.hrms.service.mapper.MasterPerformanceIndicatorMapper;
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
 * Service for executing complex queries for {@link MasterPerformanceIndicator} entities in the database.
 * The main input is a {@link MasterPerformanceIndicatorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MasterPerformanceIndicatorDTO} or a {@link Page} of {@link MasterPerformanceIndicatorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MasterPerformanceIndicatorQueryService extends QueryService<MasterPerformanceIndicator> {

    private final Logger log = LoggerFactory.getLogger(MasterPerformanceIndicatorQueryService.class);

    private final MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository;

    private final MasterPerformanceIndicatorMapper masterPerformanceIndicatorMapper;

    public MasterPerformanceIndicatorQueryService(
        MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository,
        MasterPerformanceIndicatorMapper masterPerformanceIndicatorMapper
    ) {
        this.masterPerformanceIndicatorRepository = masterPerformanceIndicatorRepository;
        this.masterPerformanceIndicatorMapper = masterPerformanceIndicatorMapper;
    }

    /**
     * Return a {@link List} of {@link MasterPerformanceIndicatorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MasterPerformanceIndicatorDTO> findByCriteria(MasterPerformanceIndicatorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MasterPerformanceIndicator> specification = createSpecification(criteria);
        return masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicatorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MasterPerformanceIndicatorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterPerformanceIndicatorDTO> findByCriteria(MasterPerformanceIndicatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MasterPerformanceIndicator> specification = createSpecification(criteria);
        return masterPerformanceIndicatorRepository.findAll(specification, page).map(masterPerformanceIndicatorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MasterPerformanceIndicatorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MasterPerformanceIndicator> specification = createSpecification(criteria);
        return masterPerformanceIndicatorRepository.count(specification);
    }

    /**
     * Function to convert {@link MasterPerformanceIndicatorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MasterPerformanceIndicator> createSpecification(MasterPerformanceIndicatorCriteria criteria) {
        Specification<MasterPerformanceIndicator> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MasterPerformanceIndicator_.id));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), MasterPerformanceIndicator_.category));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MasterPerformanceIndicator_.type));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MasterPerformanceIndicator_.name));
            }
            if (criteria.getDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescription(), MasterPerformanceIndicator_.description));
            }
            if (criteria.getWeightage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeightage(), MasterPerformanceIndicator_.weightage));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MasterPerformanceIndicator_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), MasterPerformanceIndicator_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), MasterPerformanceIndicator_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MasterPerformanceIndicator_.lastModifiedBy));
            }
        }
        return specification;
    }
}
