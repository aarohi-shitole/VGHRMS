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
import com.techvg.hrms.domain.AssetApplication;
import com.techvg.hrms.domain.AssetApplication_;
import com.techvg.hrms.repository.AssetApplicationRepository;
import com.techvg.hrms.service.criteria.AssetApplicationCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.AssetApplicationDTO;
import com.techvg.hrms.service.mapper.AssetApplicationMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AssetApplication} entities in the database.
 * The main input is a {@link AssetApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetApplicationDTO} or a {@link Page} of {@link AssetApplicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetApplicationQueryService extends QueryService<AssetApplication> {

    private final Logger log = LoggerFactory.getLogger(AssetApplicationQueryService.class);

    private final AssetApplicationRepository assetApplicationRepository;

    private final AssetApplicationMapper assetApplicationMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public AssetApplicationQueryService(
        AssetApplicationRepository assetApplicationRepository,
        AssetApplicationMapper assetApplicationMapper
    ) {
        this.assetApplicationRepository = assetApplicationRepository;
        this.assetApplicationMapper = assetApplicationMapper;
    }

    /**
     * Return a {@link List} of {@link AssetApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssetApplicationDTO> findByCriteria(AssetApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<AssetApplication> specification = createSpecification(criteria);
        return assetApplicationMapper.toDto(assetApplicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssetApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetApplicationDTO> findByCriteria(AssetApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<AssetApplication> specification = createSpecification(criteria);
        return assetApplicationRepository.findAll(specification, page).map(assetApplicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<AssetApplication> specification = createSpecification(criteria);
        return assetApplicationRepository.count(specification);
    }

    /**
     * Function to convert {@link AssetApplicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AssetApplication> createSpecification(AssetApplicationCriteria criteria) {
        Specification<AssetApplication> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AssetApplication_.id));
            }
            if (criteria.getAssetId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetId(), AssetApplication_.assetId));
            }
            if (criteria.getAssetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetype(), AssetApplication_.assetype));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), AssetApplication_.quantity));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AssetApplication_.description));
            }
            if (criteria.getReqStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReqStatus(), AssetApplication_.reqStatus));
            }
            if (criteria.getApplyDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplyDate(), AssetApplication_.applyDate));
            }
            if (criteria.getAssginDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssginDate(), AssetApplication_.assginDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AssetApplication_.status));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), AssetApplication_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), AssetApplication_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AssetApplication_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AssetApplication_.lastModifiedBy));
            }
        }
        return specification;
    }
}
