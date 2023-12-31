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
import com.techvg.hrms.domain.MasterLookup;
import com.techvg.hrms.domain.MasterLookup_;
import com.techvg.hrms.repository.MasterLookupRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.MasterLookupCriteria;
import com.techvg.hrms.service.dto.MasterLookupDTO;
import com.techvg.hrms.service.mapper.MasterLookupMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MasterLookup} entities in the database.
 * The main input is a {@link MasterLookupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MasterLookupDTO} or a {@link Page} of {@link MasterLookupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MasterLookupQueryService extends QueryService<MasterLookup> {

    private final Logger log = LoggerFactory.getLogger(MasterLookupQueryService.class);

    private final MasterLookupRepository masterLookupRepository;

    private final MasterLookupMapper masterLookupMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public MasterLookupQueryService(MasterLookupRepository masterLookupRepository, MasterLookupMapper masterLookupMapper) {
        this.masterLookupRepository = masterLookupRepository;
        this.masterLookupMapper = masterLookupMapper;
    }

    /**
     * Return a {@link List} of {@link MasterLookupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MasterLookupDTO> findByCriteria(MasterLookupCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<MasterLookup> specification = createSpecification(criteria);
        return masterLookupMapper.toDto(masterLookupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MasterLookupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterLookupDTO> findByCriteria(MasterLookupCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MasterLookup> specification = createSpecification(criteria);
        return masterLookupRepository.findAll(specification, page).map(masterLookupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MasterLookupCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<MasterLookup> specification = createSpecification(criteria);
        return masterLookupRepository.count(specification);
    }

    /**
     * Function to convert {@link MasterLookupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MasterLookup> createSpecification(MasterLookupCriteria criteria) {
        Specification<MasterLookup> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MasterLookup_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MasterLookup_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), MasterLookup_.value));
            }
            if (criteria.getValueTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueTwo(), MasterLookup_.valueTwo));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MasterLookup_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MasterLookup_.type));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), MasterLookup_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MasterLookup_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MasterLookup_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MasterLookup_.lastModifiedBy));
            }
        }
        return specification;
    }
}
