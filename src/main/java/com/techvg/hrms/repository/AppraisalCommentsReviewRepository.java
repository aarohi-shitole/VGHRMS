package com.techvg.hrms.repository;

import com.techvg.hrms.domain.AppraisalCommentsReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppraisalCommentsReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppraisalCommentsReviewRepository
    extends JpaRepository<AppraisalCommentsReview, Long>, JpaSpecificationExecutor<AppraisalCommentsReview> {}
