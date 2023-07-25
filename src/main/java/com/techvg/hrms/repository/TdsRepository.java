package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Tds;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tds entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TdsRepository extends JpaRepository<Tds, Long>, JpaSpecificationExecutor<Tds> {}
