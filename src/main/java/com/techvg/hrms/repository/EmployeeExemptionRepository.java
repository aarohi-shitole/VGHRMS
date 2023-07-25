package com.techvg.hrms.repository;

import com.techvg.hrms.domain.EmployeeExemption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeExemption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeExemptionRepository extends JpaRepository<EmployeeExemption, Long>, JpaSpecificationExecutor<EmployeeExemption> {}
