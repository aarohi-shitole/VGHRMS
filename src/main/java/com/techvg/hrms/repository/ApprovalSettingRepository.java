package com.techvg.hrms.repository;

import com.techvg.hrms.domain.ApprovalSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApprovalSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalSettingRepository extends JpaRepository<ApprovalSetting, Long>, JpaSpecificationExecutor<ApprovalSetting> {}
