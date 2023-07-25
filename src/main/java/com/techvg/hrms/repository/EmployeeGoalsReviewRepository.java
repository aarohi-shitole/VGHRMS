package com.techvg.hrms.repository;

import com.techvg.hrms.domain.EmployeeGoalsReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeGoalsReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeGoalsReviewRepository
    extends JpaRepository<EmployeeGoalsReview, Long>, JpaSpecificationExecutor<EmployeeGoalsReview> {}
