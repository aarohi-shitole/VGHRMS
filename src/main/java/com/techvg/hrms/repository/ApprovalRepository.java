package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Approval;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeaveApplicationApproval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long>, JpaSpecificationExecutor<Approval> {
	
	List<Approval> findByRefTableIdAndRefTable(Long refTableId,String refTable);
}
