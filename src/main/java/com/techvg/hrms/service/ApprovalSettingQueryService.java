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
import com.techvg.hrms.domain.ApprovalSetting;
import com.techvg.hrms.domain.ApprovalSetting_;
import com.techvg.hrms.repository.ApprovalSettingRepository;
import com.techvg.hrms.service.criteria.ApprovalSettingCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.ApprovalSettingDTO;
import com.techvg.hrms.service.mapper.ApprovalSettingMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ApprovalSetting} entities in the database.
 * The main input is a {@link ApprovalSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApprovalSettingDTO} or a {@link Page} of {@link ApprovalSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApprovalSettingQueryService extends QueryService<ApprovalSetting> {

    private final Logger log = LoggerFactory.getLogger(ApprovalSettingQueryService.class);

    private final ApprovalSettingRepository approvalSettingRepository;

    private final ApprovalSettingMapper approvalSettingMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public ApprovalSettingQueryService(ApprovalSettingRepository approvalSettingRepository, ApprovalSettingMapper approvalSettingMapper) {
        this.approvalSettingRepository = approvalSettingRepository;
        this.approvalSettingMapper = approvalSettingMapper;
    }

    /**
     * Return a {@link List} of {@link ApprovalSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApprovalSettingDTO> findByCriteria(ApprovalSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<ApprovalSetting> specification = createSpecification(criteria);
        return approvalSettingMapper.toDto(approvalSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApprovalSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApprovalSettingDTO> findByCriteria(ApprovalSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<ApprovalSetting> specification = createSpecification(criteria);
        return approvalSettingRepository.findAll(specification, page).map(approvalSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApprovalSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<ApprovalSetting> specification = createSpecification(criteria);
        return approvalSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link ApprovalSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApprovalSetting> createSpecification(ApprovalSettingCriteria criteria) {
        Specification<ApprovalSetting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApprovalSetting_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ApprovalSetting_.type));
            }
            if (criteria.getApprovalCategory() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getApprovalCategory(), ApprovalSetting_.approvalCategory));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), ApprovalSetting_.departmentId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ApprovalSetting_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), ApprovalSetting_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ApprovalSetting_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ApprovalSetting_.lastModifiedBy));
            }
        }
        return specification;
    }
}
