package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Education;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Education entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {}
