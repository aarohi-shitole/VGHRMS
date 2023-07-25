package com.techvg.hrms.repository;

import com.techvg.hrms.domain.LeaveApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long>, JpaSpecificationExecutor<LeaveApplication> {}
