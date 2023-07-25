package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.TechvgRole;
import com.techvg.hrms.repository.TechvgRoleRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TechvgRoleCriteria;
import com.techvg.hrms.service.dto.TechvgRoleDTO;
import com.techvg.hrms.service.mapper.TechvgRoleMapper;
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
 * Service for executing complex queries for {@link TechvgRole} entities in the database.
 * The main input is a {@link TechvgRoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TechvgRoleDTO} or a {@link Page} of {@link TechvgRoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TechvgRoleQueryService extends QueryService<TechvgRole> {

    private final Logger log = LoggerFactory.getLogger(TechvgRoleQueryService.class);

    private final TechvgRoleRepository techvgRoleRepository;

    private final TechvgRoleMapper techvgRoleMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TechvgRoleQueryService(TechvgRoleRepository techvgRoleRepository, TechvgRoleMapper techvgRoleMapper) {
        this.techvgRoleRepository = techvgRoleRepository;
        this.techvgRoleMapper = techvgRoleMapper;
    }

    /**
     * Return a {@link List} of {@link TechvgRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TechvgRoleDTO> findByCriteria(TechvgRoleCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TechvgRole> specification = createSpecification(criteria);
        return techvgRoleMapper.toDto(techvgRoleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TechvgRoleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TechvgRoleDTO> findByCriteria(TechvgRoleCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TechvgRole> specification = createSpecification(criteria);
        return techvgRoleRepository.findAll(specification, page).map(techvgRoleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TechvgRoleCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TechvgRole> specification = createSpecification(criteria);
        return techvgRoleRepository.count(specification);
    }

    /**
     * Function to convert {@link TechvgRoleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TechvgRole> createSpecification(TechvgRoleCriteria criteria) {
        Specification<TechvgRole> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TechvgRole_.id));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), TechvgRole_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TechvgRole_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TechvgRole_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TechvgRole_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TechvgRole_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TechvgRole_.lastModifiedBy));
            }
            if (criteria.getTechvgPermissionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTechvgPermissionId(),
                            root -> root.join(TechvgRole_.techvgPermissions, JoinType.LEFT).get(TechvgPermission_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
