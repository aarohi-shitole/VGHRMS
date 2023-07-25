package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import com.techvg.hrms.repository.AppraisalEvaluationParameterRepository;
import com.techvg.hrms.service.criteria.AppraisalEvaluationParameterCriteria;
import com.techvg.hrms.service.dto.AppraisalEvaluationParameterDTO;
import com.techvg.hrms.service.mapper.AppraisalEvaluationParameterMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AppraisalEvaluationParameter} entities in the database.
 * The main input is a {@link AppraisalEvaluationParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppraisalEvaluationParameterDTO} or a {@link Page} of {@link AppraisalEvaluationParameterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppraisalEvaluationParameterQueryService extends QueryService<AppraisalEvaluationParameter> {

    private final Logger log = LoggerFactory.getLogger(AppraisalEvaluationParameterQueryService.class);

    private final AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository;

    private final AppraisalEvaluationParameterMapper appraisalEvaluationParameterMapper;

    public AppraisalEvaluationParameterQueryService(
        AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository,
        AppraisalEvaluationParameterMapper appraisalEvaluationParameterMapper
    ) {
        this.appraisalEvaluationParameterRepository = appraisalEvaluationParameterRepository;
        this.appraisalEvaluationParameterMapper = appraisalEvaluationParameterMapper;
    }

    /**
     * Return a {@link List} of {@link AppraisalEvaluationParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppraisalEvaluationParameterDTO> findByCriteria(AppraisalEvaluationParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AppraisalEvaluationParameter> specification = createSpecification(criteria);
        return appraisalEvaluationParameterMapper.toDto(appraisalEvaluationParameterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppraisalEvaluationParameterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalEvaluationParameterDTO> findByCriteria(AppraisalEvaluationParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppraisalEvaluationParameter> specification = createSpecification(criteria);
        return appraisalEvaluationParameterRepository.findAll(specification, page).map(appraisalEvaluationParameterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppraisalEvaluationParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AppraisalEvaluationParameter> specification = createSpecification(criteria);
        return appraisalEvaluationParameterRepository.count(specification);
    }

    /**
     * Function to convert {@link AppraisalEvaluationParameterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppraisalEvaluationParameter> createSpecification(AppraisalEvaluationParameterCriteria criteria) {
        Specification<AppraisalEvaluationParameter> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AppraisalEvaluationParameter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AppraisalEvaluationParameter_.name));
            }
            if (criteria.getDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescription(), AppraisalEvaluationParameter_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), AppraisalEvaluationParameter_.type));
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCompanyId(), AppraisalEvaluationParameter_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AppraisalEvaluationParameter_.status));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), AppraisalEvaluationParameter_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AppraisalEvaluationParameter_.lastModifiedBy));
            }
        }
        return specification;
    }
}
