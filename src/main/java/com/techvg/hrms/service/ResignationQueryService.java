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
import com.techvg.hrms.domain.Resignation;
import com.techvg.hrms.domain.Resignation_;
import com.techvg.hrms.repository.ResignationRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.ResignationCriteria;
import com.techvg.hrms.service.dto.ResignationDTO;
import com.techvg.hrms.service.mapper.ResignationMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Resignation} entities in the database.
 * The main input is a {@link ResignationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResignationDTO} or a {@link Page} of {@link ResignationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResignationQueryService extends QueryService<Resignation> {

    private final Logger log = LoggerFactory.getLogger(ResignationQueryService.class);

    private final ResignationRepository resignationRepository;

    private final ResignationMapper resignationMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public ResignationQueryService(ResignationRepository resignationRepository, ResignationMapper resignationMapper) {
        this.resignationRepository = resignationRepository;
        this.resignationMapper = resignationMapper;
    }

    /**
     * Return a {@link List} of {@link ResignationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResignationDTO> findByCriteria(ResignationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Resignation> specification = createSpecification(criteria);
        return resignationMapper.toDto(resignationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResignationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResignationDTO> findByCriteria(ResignationCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resignation> specification = createSpecification(criteria);
        return resignationRepository.findAll(specification, page).map(resignationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResignationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Resignation> specification = createSpecification(criteria);
        return resignationRepository.count(specification);
    }

    /**
     * Function to convert {@link ResignationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Resignation> createSpecification(ResignationCriteria criteria) {
        Specification<Resignation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Resignation_.id));
            }
            if (criteria.getEmpName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpName(), Resignation_.empName));
            }
            if (criteria.getResignDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResignDate(), Resignation_.resignDate));
            }
            if (criteria.getNoticePeriodIndays() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNoticePeriodIndays(), Resignation_.noticePeriodIndays));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), Resignation_.reason));
            }
            if (criteria.getResignStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResignStatus(), Resignation_.resignStatus));
            }
            if (criteria.getLastWorkingDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastWorkingDay(), Resignation_.lastWorkingDay));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepartmentId(), Resignation_.departmentId));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Resignation_.employeeId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Resignation_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Resignation_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Resignation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Resignation_.lastModifiedBy));
            }
        }
        return specification;
    }
}
