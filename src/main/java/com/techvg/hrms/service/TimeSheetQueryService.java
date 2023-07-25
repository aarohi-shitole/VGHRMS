package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.TimeSheet;
import com.techvg.hrms.repository.TimeSheetRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.TimeSheetCriteria;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import com.techvg.hrms.service.mapper.TimeSheetMapper;
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
 * Service for executing complex queries for {@link TimeSheet} entities in the database.
 * The main input is a {@link TimeSheetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TimeSheetDTO} or a {@link Page} of {@link TimeSheetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TimeSheetQueryService extends QueryService<TimeSheet> {

    private final Logger log = LoggerFactory.getLogger(TimeSheetQueryService.class);

    private final TimeSheetRepository timeSheetRepository;

    private final TimeSheetMapper timeSheetMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public TimeSheetQueryService(TimeSheetRepository timeSheetRepository, TimeSheetMapper timeSheetMapper) {
        this.timeSheetRepository = timeSheetRepository;
        this.timeSheetMapper = timeSheetMapper;
    }

    /**
     * Return a {@link List} of {@link TimeSheetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TimeSheetDTO> findByCriteria(TimeSheetCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}", criteria);
        final Specification<TimeSheet> specification = createSpecification(criteria);
        return timeSheetMapper.toDto(timeSheetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TimeSheetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeSheetDTO> findByCriteria(TimeSheetCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TimeSheet> specification = createSpecification(criteria);
        return timeSheetRepository.findAll(specification, page).map(timeSheetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TimeSheetCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<TimeSheet> specification = createSpecification(criteria);
        return timeSheetRepository.count(specification);
    }

    /**
     * Function to convert {@link TimeSheetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TimeSheet> createSpecification(TimeSheetCriteria criteria) {
        Specification<TimeSheet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TimeSheet_.id));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTime(), TimeSheet_.time));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), TimeSheet_.date));
            }
            if (criteria.getHasCheckedIn() != null) {
                specification = specification.and(buildSpecification(criteria.getHasCheckedIn(), TimeSheet_.hasCheckedIn));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TimeSheet_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), TimeSheet_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TimeSheet_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TimeSheet_.lastModifiedBy));
            }
            if (criteria.getAttendanceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAttendanceId(),
                            root -> root.join(TimeSheet_.attendance, JoinType.LEFT).get(Attendance_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
