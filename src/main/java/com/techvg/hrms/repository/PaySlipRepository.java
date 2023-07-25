package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PaySlip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaySlip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaySlipRepository extends JpaRepository<PaySlip, Long>, JpaSpecificationExecutor<PaySlip> {}
