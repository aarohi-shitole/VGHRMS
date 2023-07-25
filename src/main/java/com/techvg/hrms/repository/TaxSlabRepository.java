package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TaxSlab;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxSlab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxSlabRepository extends JpaRepository<TaxSlab, Long>, JpaSpecificationExecutor<TaxSlab> {}
