package com.techvg.hrms.service;

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

// for static metamodels
import com.techvg.hrms.domain.Remuneration;
import com.techvg.hrms.domain.Remuneration_;
import com.techvg.hrms.domain.TaxRegime_;
import com.techvg.hrms.repository.RemunerationRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.RemunerationCriteria;
import com.techvg.hrms.service.dto.RemunerationDTO;
import com.techvg.hrms.service.mapper.RemunerationMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Remuneration} entities in the database.
 * The main input is a {@link RemunerationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RemunerationDTO} or a {@link Page} of {@link RemunerationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RemunerationQueryService extends QueryService<Remuneration> {

    private final Logger log = LoggerFactory.getLogger(RemunerationQueryService.class);

    private final RemunerationRepository remunerationRepository;

    private final RemunerationMapper remunerationMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public RemunerationQueryService(RemunerationRepository remunerationRepository, RemunerationMapper remunerationMapper) {
        this.remunerationRepository = remunerationRepository;
        this.remunerationMapper = remunerationMapper;
    }

    /**
     * Return a {@link List} of {@link RemunerationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RemunerationDTO> findByCriteria(RemunerationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<Remuneration> specification = createSpecification(criteria);
        return remunerationMapper.toDto(remunerationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RemunerationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RemunerationDTO> findByCriteria(RemunerationCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Remuneration> specification = createSpecification(criteria);
        return remunerationRepository.findAll(specification, page).map(remunerationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RemunerationCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Remuneration> specification = createSpecification(criteria);
        return remunerationRepository.count(specification);
    }

    /**
     * Function to convert {@link RemunerationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Remuneration> createSpecification(RemunerationCriteria criteria) {
        Specification<Remuneration> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Remuneration_.id));
            }
            if (criteria.getSalaryType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalaryType(), Remuneration_.salaryType));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Remuneration_.amount));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentType(), Remuneration_.paymentType));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Remuneration_.employeeId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Remuneration_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Remuneration_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Remuneration_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Remuneration_.lastModifiedBy));
            }
//            if (criteria.getTaxRegimeId() != null) {
//                specification = specification.and(build(criteria.getTaxRegimeId(), Remuneration_.lastModifiedBy));
//            }
            if (criteria.getTaxRegimeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTaxRegimeId(),
                            root -> root.join(Remuneration_.taxRegime, JoinType.LEFT).get(TaxRegime_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
