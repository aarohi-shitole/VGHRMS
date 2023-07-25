package com.techvg.hrms.repository;

import com.techvg.hrms.domain.AppraisalDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppraisalDetailsRepository extends JpaRepository<AppraisalDetails, Long>, JpaSpecificationExecutor<AppraisalDetails> {}
