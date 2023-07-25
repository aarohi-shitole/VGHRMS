package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TrainingType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrainingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long>, JpaSpecificationExecutor<TrainingType> {}
