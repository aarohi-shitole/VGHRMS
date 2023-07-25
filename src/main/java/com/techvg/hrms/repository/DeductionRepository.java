package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Deduction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long>, JpaSpecificationExecutor<Deduction> {}
