package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PfDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PfDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PfDetailsRepository extends JpaRepository<PfDetails, Long>, JpaSpecificationExecutor<PfDetails> {
	boolean existsByEmployeeId(Long employeeId);
}
