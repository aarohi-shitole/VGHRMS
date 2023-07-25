package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TechvgPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TechvgPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechvgPermissionRepository extends JpaRepository<TechvgPermission, Long>, JpaSpecificationExecutor<TechvgPermission> {}
