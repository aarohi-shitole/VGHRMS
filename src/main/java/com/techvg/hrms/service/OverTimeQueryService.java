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
import com.techvg.hrms.domain.OverTime;
import com.techvg.hrms.domain.OverTime_;
import com.techvg.hrms.repository.OverTimeRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.OverTimeCriteria;
import com.techvg.hrms.service.dto.OverTimeDTO;
import com.techvg.hrms.service.mapper.OverTimeMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link OverTime} entities in the database.
 * The main input is a {@link OverTimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OverTimeDTO} or a {@link Page} of {@link OverTimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OverTimeQueryService extends QueryService<OverTime> {

    private final Logger log = LoggerFactory.getLogger(OverTimeQueryService.class);

    private final OverTimeRepository overTimeRepository;

    private final OverTimeMapper overTimeMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public OverTimeQueryService(OverTimeRepository overTimeRepository, OverTimeMapper overTimeMapper) {
        this.overTimeRepository = overTimeRepository;
        this.overTimeMapper = overTimeMapper;
    }

    /**
     * Return a {@link List} of {@link OverTimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OverTimeDTO> findByCriteria(OverTimeCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeMapper.toDto(overTimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OverTimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OverTimeDTO> findByCriteria(OverTimeCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.findAll(specification, page).map(overTimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OverTimeCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.count(specification);
    }

    /**
     * Function to convert {@link OverTimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OverTime> createSpecification(OverTimeCriteria criteria) {
        Specification<OverTime> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OverTime_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OverTime_.name));
            }
            if (criteria.getRateType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRateType(), OverTime_.rateType));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), OverTime_.rate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OverTime_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), OverTime_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), OverTime_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), OverTime_.lastModifiedBy));
            }
        }
        return specification;
    }
}
