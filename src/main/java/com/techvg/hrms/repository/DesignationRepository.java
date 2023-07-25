package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Designation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Designation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long>, JpaSpecificationExecutor<Designation> {}
