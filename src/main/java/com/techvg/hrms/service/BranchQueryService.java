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
import com.techvg.hrms.domain.Branch;
import com.techvg.hrms.domain.Branch_;
import com.techvg.hrms.repository.BranchRepository;
import com.techvg.hrms.service.criteria.BranchCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.BranchDTO;
import com.techvg.hrms.service.mapper.BranchMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Branch} entities in the database.
 * The main input is a {@link BranchCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BranchDTO} or a {@link Page} of {@link BranchDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BranchQueryService extends QueryService<Branch> {

    private final Logger log = LoggerFactory.getLogger(BranchQueryService.class);

    private final BranchRepository branchRepository;

    private final BranchMapper branchMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public BranchQueryService(BranchRepository branchRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
    }

    /**
     * Return a {@link List} of {@link BranchDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BranchDTO> findByCriteria(BranchCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("find by criteria : {}", criteria);
        final Specification<Branch> specification = createSpecification(criteria);
        return branchMapper.toDto(branchRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BranchDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BranchDTO> findByCriteria(BranchCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Branch> specification = createSpecification(criteria);
        return branchRepository.findAll(specification, page).map(branchMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BranchCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("count by criteria : {}", criteria);
        final Specification<Branch> specification = createSpecification(criteria);
        return branchRepository.count(specification);
    }

    /**
     * Function to convert {@link BranchCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Branch> createSpecification(BranchCriteria criteria) {
        Specification<Branch> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Branch_.id));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), Branch_.branchName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Branch_.description));
            }
            if (criteria.getBranchcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchcode(), Branch_.branchcode));
            }
            if (criteria.getBranchType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchType(), Branch_.branchType));
            }
            if (criteria.getWebSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSite(), Branch_.webSite));
            }
            if (criteria.getBranchId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBranchId(), Branch_.branchId));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegionId(), Branch_.regionId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Branch_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Branch_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Branch_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Branch_.lastModifiedBy));
            }
        }
        return specification;
    }
}
