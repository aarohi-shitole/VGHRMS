package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Salary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Salary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>, JpaSpecificationExecutor<Salary> {}
