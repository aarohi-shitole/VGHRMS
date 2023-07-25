package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PerformanceAppraisal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceAppraisal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceAppraisalRepository
    extends JpaRepository<PerformanceAppraisal, Long>, JpaSpecificationExecutor<PerformanceAppraisal> {}
