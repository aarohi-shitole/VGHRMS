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
import com.techvg.hrms.domain.ApprovalLevel;
import com.techvg.hrms.domain.ApprovalLevel_;
import com.techvg.hrms.repository.ApprovalLevelRepository;
import com.techvg.hrms.service.criteria.ApprovalLevelCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.service.mapper.ApprovalLevelMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ApprovalLevel} entities in the database.
 * The main input is a {@link ApprovalLevelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApprovalLevelDTO} or a {@link Page} of {@link ApprovalLevelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApprovalLevelQueryService extends QueryService<ApprovalLevel> {

    private final Logger log = LoggerFactory.getLogger(ApprovalLevelQueryService.class);

    private final ApprovalLevelRepository approvalLevelRepository;

    private final ApprovalLevelMapper approvalLevelMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public ApprovalLevelQueryService(ApprovalLevelRepository approvalLevelRepository, ApprovalLevelMapper approvalLevelMapper) {
        this.approvalLevelRepository = approvalLevelRepository;
        this.approvalLevelMapper = approvalLevelMapper;
    }

    /**
     * Return a {@link List} of {@link ApprovalLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApprovalLevelDTO> findByCriteria(ApprovalLevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<ApprovalLevel> specification = createSpecification(criteria);
        return approvalLevelMapper.toDto(approvalLevelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApprovalLevelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApprovalLevelDTO> findByCriteria(ApprovalLevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<ApprovalLevel> specification = createSpecification(criteria);
        return approvalLevelRepository.findAll(specification, page).map(approvalLevelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApprovalLevelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<ApprovalLevel> specification = createSpecification(criteria);
        return approvalLevelRepository.count(specification);
    }

    /**
     * Function to convert {@link ApprovalLevelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApprovalLevel> createSpecification(ApprovalLevelCriteria criteria) {
        Specification<ApprovalLevel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApprovalLevel_.id));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesignationId(), ApprovalLevel_.designationId));
            }
            if (criteria.getSquence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSquence(), ApprovalLevel_.squence));
            }
            if (criteria.getApprovalSettingId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getApprovalSettingId(), ApprovalLevel_.approvalSettingId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ApprovalLevel_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), ApprovalLevel_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ApprovalLevel_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ApprovalLevel_.lastModifiedBy));
            }
        }
        return specification;
    }
}
