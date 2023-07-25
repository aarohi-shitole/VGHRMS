package com.techvg.hrms.repository;

import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppraisalEvaluationParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppraisalEvaluationParameterRepository
    extends JpaRepository<AppraisalEvaluationParameter, Long>, JpaSpecificationExecutor<AppraisalEvaluationParameter> {}
