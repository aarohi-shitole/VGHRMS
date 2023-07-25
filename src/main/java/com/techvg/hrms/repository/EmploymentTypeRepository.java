package com.techvg.hrms.repository;

import com.techvg.hrms.domain.EmploymentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmploymentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, Long>, JpaSpecificationExecutor<EmploymentType> {}
