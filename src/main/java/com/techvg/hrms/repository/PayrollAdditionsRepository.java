package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PayrollAdditions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PayrollAdditions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollAdditionsRepository extends JpaRepository<PayrollAdditions, Long>, JpaSpecificationExecutor<PayrollAdditions> {}
