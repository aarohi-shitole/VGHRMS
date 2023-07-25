package com.techvg.hrms.repository;

import com.techvg.hrms.domain.MasterPerformanceIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MasterPerformanceIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MasterPerformanceIndicatorRepository
    extends JpaRepository<MasterPerformanceIndicator, Long>, JpaSpecificationExecutor<MasterPerformanceIndicator> {}
