package com.techvg.hrms.repository;

import com.techvg.hrms.domain.CustomApprovar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomApprovar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomApprovarRepository extends JpaRepository<CustomApprovar, Long>, JpaSpecificationExecutor<CustomApprovar> {}
