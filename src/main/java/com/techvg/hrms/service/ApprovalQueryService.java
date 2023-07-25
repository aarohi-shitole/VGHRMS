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
import com.techvg.hrms.domain.Approval;
import com.techvg.hrms.domain.Approval_;
import com.techvg.hrms.repository.ApprovalRepository;
import com.techvg.hrms.service.criteria.ApprovalCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.ApprovalDTO;
import com.techvg.hrms.service.mapper.ApprovalMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Approval} entities in the database.
 * The main input is a {@link ApprovalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApprovalDTO} or a {@link Page} of {@link ApprovalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApprovalQueryService extends QueryService<Approval> {

    private final Logger log = LoggerFactory.getLogger(ApprovalQueryService.class);

    private final ApprovalRepository approvalRepository;

    private final ApprovalMapper approvalMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public ApprovalQueryService(ApprovalRepository approvalRepository, ApprovalMapper approvalMapper) {
        this.approvalRepository = approvalRepository;
        this.approvalMapper = approvalMapper;
    }

    /**
     * Return a {@link List} of {@link ApprovalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApprovalDTO> findByCriteria(ApprovalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<Approval> specification = createSpecification(criteria);
        return approvalMapper.toDto(approvalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApprovalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApprovalDTO> findByCriteria(ApprovalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<Approval> specification = createSpecification(criteria);
        return approvalRepository.findAll(specification, page).map(approvalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApprovalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<Approval> specification = createSpecification(criteria);
        return approvalRepository.count(specification);
    }

    /**
     * Function to convert {@link ApprovalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Approval> createSpecification(ApprovalCriteria criteria) {
        Specification<Approval> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Approval_.id));
            }
            if (criteria.getApproverEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApproverEmployeeId(), Approval_.approverEmployeeId));
            }
            if (criteria.getApprovalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovalStatus(), Approval_.approvalStatus));
            }
            if (criteria.getRefTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefTable(), Approval_.refTable));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), Approval_.sequence));
            }

            if (criteria.getRefTableId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefTableId(), Approval_.refTableId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Approval_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Approval_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Approval_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Approval_.lastModifiedBy));
            }
        }
        return specification;
    }
}
