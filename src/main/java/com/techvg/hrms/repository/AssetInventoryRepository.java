package com.techvg.hrms.repository;

import com.techvg.hrms.domain.AssetInventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetInventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetInventoryRepository extends JpaRepository<AssetInventory, Long>, JpaSpecificationExecutor<AssetInventory> {}
