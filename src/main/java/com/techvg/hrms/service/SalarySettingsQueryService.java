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
import com.techvg.hrms.domain.SalarySettings;
import com.techvg.hrms.domain.SalarySettings_;
import com.techvg.hrms.repository.SalarySettingsRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.SalarySettingsCriteria;
import com.techvg.hrms.service.dto.SalarySettingsDTO;
import com.techvg.hrms.service.mapper.SalarySettingsMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalarySettings} entities in the database.
 * The main input is a {@link SalarySettingsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalarySettingsDTO} or a {@link Page} of {@link SalarySettingsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalarySettingsQueryService extends QueryService<SalarySettings> {

    private final Logger log = LoggerFactory.getLogger(SalarySettingsQueryService.class);

    private final SalarySettingsRepository salarySettingsRepository;

    private final SalarySettingsMapper salarySettingsMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public SalarySettingsQueryService(SalarySettingsRepository salarySettingsRepository, SalarySettingsMapper salarySettingsMapper) {
        this.salarySettingsRepository = salarySettingsRepository;
        this.salarySettingsMapper = salarySettingsMapper;
    }

    /**
     * Return a {@link List} of {@link SalarySettingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalarySettingsDTO> findByCriteria(SalarySettingsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<SalarySettings> specification = createSpecification(criteria);
        return salarySettingsMapper.toDto(salarySettingsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalarySettingsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalarySettingsDTO> findByCriteria(SalarySettingsCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalarySettings> specification = createSpecification(criteria);
        return salarySettingsRepository.findAll(specification, page).map(salarySettingsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalarySettingsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<SalarySettings> specification = createSpecification(criteria);
        return salarySettingsRepository.count(specification);
    }

    /**
     * Function to convert {@link SalarySettingsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalarySettings> createSpecification(SalarySettingsCriteria criteria) {
        Specification<SalarySettings> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalarySettings_.id));
            }
            if (criteria.getDa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDa(), SalarySettings_.da));
            }
            if (criteria.getHra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHra(), SalarySettings_.hra));
            }
            if (criteria.getEmployeeShare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeShare(), SalarySettings_.employeeShare));
            }
            if (criteria.getCompanyShare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyShare(), SalarySettings_.companyShare));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SalarySettings_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), SalarySettings_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SalarySettings_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SalarySettings_.lastModifiedBy));
            }
        }
        return specification;
    }
}
