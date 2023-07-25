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
import com.techvg.hrms.domain.Tds;
import com.techvg.hrms.domain.Tds_;
import com.techvg.hrms.repository.TdsRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TdsCriteria;
import com.techvg.hrms.service.dto.TdsDTO;
import com.techvg.hrms.service.mapper.TdsMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Tds} entities in the database.
 * The main input is a {@link TdsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TdsDTO} or a {@link Page} of {@link TdsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TdsQueryService extends QueryService<Tds> {

    private final Logger log = LoggerFactory.getLogger(TdsQueryService.class);

    private final TdsRepository tdsRepository;

    private final TdsMapper tdsMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TdsQueryService(TdsRepository tdsRepository, TdsMapper tdsMapper) {
        this.tdsRepository = tdsRepository;
        this.tdsMapper = tdsMapper;
    }

    /**
     * Return a {@link List} of {@link TdsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TdsDTO> findByCriteria(TdsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Tds> specification = createSpecification(criteria);
        return tdsMapper.toDto(tdsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TdsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TdsDTO> findByCriteria(TdsCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tds> specification = createSpecification(criteria);
        return tdsRepository.findAll(specification, page).map(tdsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TdsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Tds> specification = createSpecification(criteria);
        return tdsRepository.count(specification);
    }

    /**
     * Function to convert {@link TdsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tds> createSpecification(TdsCriteria criteria) {
        Specification<Tds> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tds_.id));
            }
            if (criteria.getSalaryFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryFrom(), Tds_.salaryFrom));
            }
            if (criteria.getSalaryTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalaryTo(), Tds_.salaryTo));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), Tds_.percentage));
            }
            if (criteria.getSalarySettingId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalarySettingId(), Tds_.salarySettingId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Tds_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Tds_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Tds_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Tds_.lastModifiedBy));
            }
        }
        return specification;
    }
}
