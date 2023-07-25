package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Holiday;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Holiday entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {}
