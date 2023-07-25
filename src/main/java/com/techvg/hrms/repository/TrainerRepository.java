package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Trainer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Trainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long>, JpaSpecificationExecutor<Trainer> {}
