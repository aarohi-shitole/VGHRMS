package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.TechvgPermission;
import com.techvg.hrms.repository.TechvgPermissionRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TechvgPermissionCriteria;
import com.techvg.hrms.service.dto.TechvgPermissionDTO;
import com.techvg.hrms.service.mapper.TechvgPermissionMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TechvgPermission} entities in the database.
 * The main input is a {@link TechvgPermissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TechvgPermissionDTO} or a {@link Page} of {@link TechvgPermissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TechvgPermissionQueryService extends QueryService<TechvgPermission> {

    private final Logger log = LoggerFactory.getLogger(TechvgPermissionQueryService.class);

    private final TechvgPermissionRepository techvgPermissionRepository;

    private final TechvgPermissionMapper techvgPermissionMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;
    

    public TechvgPermissionQueryService(
        TechvgPermissionRepository techvgPermissionRepository,
        TechvgPermissionMapper techvgPermissionMapper
    ) {
        this.techvgPermissionRepository = techvgPermissionRepository;
        this.techvgPermissionMapper = techvgPermissionMapper;
    }

    /**
     * Return a {@link List} of {@link TechvgPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TechvgPermissionDTO> findByCriteria(TechvgPermissionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TechvgPermission> specification = createSpecification(criteria);
        return techvgPermissionMapper.toDto(techvgPermissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TechvgPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TechvgPermissionDTO> findByCriteria(TechvgPermissionCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TechvgPermission> specification = createSpecification(criteria);
        return techvgPermissionRepository.findAll(specification, page).map(techvgPermissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TechvgPermissionCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TechvgPermission> specification = createSpecification(criteria);
        return techvgPermissionRepository.count(specification);
    }

    /**
     * Function to convert {@link TechvgPermissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TechvgPermission> createSpecification(TechvgPermissionCriteria criteria) {
        Specification<TechvgPermission> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TechvgPermission_.id));
            }
            if (criteria.getPermissionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermissionName(), TechvgPermission_.permissionName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TechvgPermission_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TechvgPermission_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TechvgPermission_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TechvgPermission_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TechvgPermission_.lastModifiedBy));
            }
            if (criteria.getTechvgRoleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTechvgRoleId(),
                            root -> root.join(TechvgPermission_.techvgRoles, JoinType.LEFT).get(TechvgRole_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
