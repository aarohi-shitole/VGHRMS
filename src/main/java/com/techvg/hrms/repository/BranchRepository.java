package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Branch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Branch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {}
