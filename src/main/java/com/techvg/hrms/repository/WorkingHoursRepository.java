package com.techvg.hrms.repository;

import com.techvg.hrms.domain.WorkingHours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkingHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long>, JpaSpecificationExecutor<WorkingHours> {}
