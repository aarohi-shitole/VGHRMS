package com.techvg.hrms.repository;

import com.techvg.hrms.domain.ApprovalLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApprovalLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalLevelRepository extends JpaRepository<ApprovalLevel, Long>, JpaSpecificationExecutor<ApprovalLevel> {}
