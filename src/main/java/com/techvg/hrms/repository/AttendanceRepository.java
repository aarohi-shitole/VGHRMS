package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Attendance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {}
