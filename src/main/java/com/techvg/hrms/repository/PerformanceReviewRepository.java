package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PerformanceReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long>, JpaSpecificationExecutor<PerformanceReview> {}
