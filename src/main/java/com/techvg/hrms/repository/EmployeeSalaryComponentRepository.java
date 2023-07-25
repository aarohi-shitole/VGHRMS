package com.techvg.hrms.repository;

import com.techvg.hrms.domain.EmployeeSalaryComponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeSalaryComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSalaryComponentRepository
    extends JpaRepository<EmployeeSalaryComponent, Long>, JpaSpecificationExecutor<EmployeeSalaryComponent> {}
