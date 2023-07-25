package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TaxExempSection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TaxExempSection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxExempSectionRepository extends JpaRepository<TaxExempSection, Long>, JpaSpecificationExecutor<TaxExempSection> {}
