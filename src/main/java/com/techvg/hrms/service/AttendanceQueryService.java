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
import com.techvg.hrms.domain.Attendance;
import com.techvg.hrms.domain.Attendance_;
import com.techvg.hrms.repository.AttendanceRepository;
import com.techvg.hrms.service.criteria.AttendanceCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.mapper.AttendanceMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Attendance} entities in the database.
 * The main input is a {@link AttendanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendanceDTO} or a {@link Page} of {@link AttendanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceQueryService extends QueryService<Attendance> {

    private final Logger log = LoggerFactory.getLogger(AttendanceQueryService.class);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public AttendanceQueryService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    /**
     * Return a {@link List} of {@link AttendanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceDTO> findByCriteria(AttendanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceMapper.toDto(attendanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttendanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findByCriteria(AttendanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.findAll(specification, page).map(attendanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        final Specification<Attendance> specification = createSpecification(criteria);
        return attendanceRepository.count(specification);
    }

    /**
     * Function to convert {@link AttendanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attendance> createSpecification(AttendanceCriteria criteria) {
        Specification<Attendance> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Attendance_.id));
            }
            if (criteria.getDeviceInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeviceInfo(), Attendance_.deviceInfo));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), Attendance_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), Attendance_.longitude));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Attendance_.date));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDay(), Attendance_.day));
            }
            if (criteria.getHours() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHours(), Attendance_.hours));
            }
            if (criteria.getBreakTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBreakTime(), Attendance_.hours));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Attendance_.employeeId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Attendance_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Attendance_.status));
            }

            if (criteria.getHasCheckedIn() != null) {
                specification = specification.and(buildSpecification(criteria.getHasCheckedIn(), Attendance_.hasCheckedIn));
            }

            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Attendance_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Attendance_.lastModifiedBy));
            }
        }
        return specification;
    }
}
