package com.techvg.hrms.repository;

import com.techvg.hrms.domain.AppraisalEvaluation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppraisalEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppraisalEvaluationRepository
    extends JpaRepository<AppraisalEvaluation, Long>, JpaSpecificationExecutor<AppraisalEvaluation> {}
