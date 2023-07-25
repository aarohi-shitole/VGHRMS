package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Termination;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Termination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerminationRepository extends JpaRepository<Termination, Long>, JpaSpecificationExecutor<Termination> {}
