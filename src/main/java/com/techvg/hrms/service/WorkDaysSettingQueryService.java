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
import com.techvg.hrms.domain.WorkDaysSetting;
import com.techvg.hrms.domain.WorkDaysSetting_;
import com.techvg.hrms.repository.WorkDaysSettingRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.WorkDaysSettingCriteria;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
import com.techvg.hrms.service.mapper.WorkDaysSettingMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link WorkDaysSetting} entities in the database.
 * The main input is a {@link WorkDaysSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkDaysSettingDTO} or a {@link Page} of {@link WorkDaysSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkDaysSettingQueryService extends QueryService<WorkDaysSetting> {

    private final Logger log = LoggerFactory.getLogger(WorkDaysSettingQueryService.class);

    private final WorkDaysSettingRepository workDaysSettingRepository;

    private final WorkDaysSettingMapper workDaysSettingMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public WorkDaysSettingQueryService(WorkDaysSettingRepository workDaysSettingRepository, WorkDaysSettingMapper workDaysSettingMapper) {
        this.workDaysSettingRepository = workDaysSettingRepository;
        this.workDaysSettingMapper = workDaysSettingMapper;
    }

    /**
     * Return a {@link List} of {@link WorkDaysSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkDaysSettingDTO> findByCriteria(WorkDaysSettingCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<WorkDaysSetting> specification = createSpecification(criteria);
        return workDaysSettingMapper.toDto(workDaysSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkDaysSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkDaysSettingDTO> findByCriteria(WorkDaysSettingCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkDaysSetting> specification = createSpecification(criteria);
        return workDaysSettingRepository.findAll(specification, page).map(workDaysSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkDaysSettingCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<WorkDaysSetting> specification = createSpecification(criteria);
        return workDaysSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkDaysSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkDaysSetting> createSpecification(WorkDaysSettingCriteria criteria) {
        Specification<WorkDaysSetting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkDaysSetting_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDay(), WorkDaysSetting_.day));
            }
            if (criteria.getHours() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHours(), WorkDaysSetting_.hours));
            }
            if (criteria.getDayOff() != null) {
                specification = specification.and(buildSpecification(criteria.getDayOff(), WorkDaysSetting_.dayOff));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), WorkDaysSetting_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), WorkDaysSetting_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), WorkDaysSetting_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WorkDaysSetting_.lastModifiedBy));
            }
        }
        return specification;
    }
}
