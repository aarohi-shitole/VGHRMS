package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PerformanceIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceIndicatorRepository
    extends JpaRepository<PerformanceIndicator, Long>, JpaSpecificationExecutor<PerformanceIndicator> {}
