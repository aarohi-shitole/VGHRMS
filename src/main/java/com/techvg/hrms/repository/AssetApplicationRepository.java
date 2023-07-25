package com.techvg.hrms.repository;

import com.techvg.hrms.domain.AssetApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetApplicationRepository extends JpaRepository<AssetApplication, Long>, JpaSpecificationExecutor<AssetApplication> {}
