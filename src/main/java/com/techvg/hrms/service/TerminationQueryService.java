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
import com.techvg.hrms.domain.Termination;
import com.techvg.hrms.domain.Termination_;
import com.techvg.hrms.repository.TerminationRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TerminationCriteria;
import com.techvg.hrms.service.dto.TerminationDTO;
import com.techvg.hrms.service.mapper.TerminationMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Termination} entities in the database.
 * The main input is a {@link TerminationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TerminationDTO} or a {@link Page} of {@link TerminationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TerminationQueryService extends QueryService<Termination> {

    private final Logger log = LoggerFactory.getLogger(TerminationQueryService.class);

    private final TerminationRepository terminationRepository;

    private final TerminationMapper terminationMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TerminationQueryService(TerminationRepository terminationRepository, TerminationMapper terminationMapper) {
        this.terminationRepository = terminationRepository;
        this.terminationMapper = terminationMapper;
    }

    /**
     * Return a {@link List} of {@link TerminationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TerminationDTO> findByCriteria(TerminationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Termination> specification = createSpecification(criteria);
        return terminationMapper.toDto(terminationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TerminationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TerminationDTO> findByCriteria(TerminationCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Termination> specification = createSpecification(criteria);
        return terminationRepository.findAll(specification, page).map(terminationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TerminationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Termination> specification = createSpecification(criteria);
        return terminationRepository.count(specification);
    }

    /**
     * Function to convert {@link TerminationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Termination> createSpecification(TerminationCriteria criteria) {
        Specification<Termination> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Termination_.id));
            }
            if (criteria.getEmpName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpName(), Termination_.empName));
            }
            if (criteria.getTerminationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTerminationType(), Termination_.terminationType));
            }
            if (criteria.getTerminationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTerminationDate(), Termination_.terminationDate));
            }
            if (criteria.getNoticeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoticeDate(), Termination_.noticeDate));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), Termination_.reason));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Termination_.status));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), Termination_.departmentId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Termination_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Termination_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Termination_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Termination_.lastModifiedBy));
            }
        }
        return specification;
    }
}
