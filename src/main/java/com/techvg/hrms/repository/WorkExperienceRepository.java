package com.techvg.hrms.repository;

import com.techvg.hrms.domain.WorkExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long>, JpaSpecificationExecutor<WorkExperience> {}
