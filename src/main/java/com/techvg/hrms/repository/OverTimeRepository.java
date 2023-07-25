package com.techvg.hrms.repository;

import com.techvg.hrms.domain.OverTime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OverTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverTimeRepository extends JpaRepository<OverTime, Long>, JpaSpecificationExecutor<OverTime> {}
