package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TaxRegime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxRegime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxRegimeRepository extends JpaRepository<TaxRegime, Long>, JpaSpecificationExecutor<TaxRegime> {}
