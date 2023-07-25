package com.techvg.hrms.service.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.techvg.hrms.domain.Approval;
import com.techvg.hrms.domain.LeaveApplication;
import com.techvg.hrms.repository.ApprovalRepository;
import com.techvg.hrms.service.dto.LeaveApplicationDTO;

/**
 * Mapper for the entity {@link LeaveApplication} and its DTO {@link LeaveApplicationDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class LeaveApplicationMapper implements EntityMapper<LeaveApplicationDTO, LeaveApplication> {
	
    public abstract LeaveApplicationDTO toDto(LeaveApplication entity);
    
    public abstract  LeaveApplication toEntity(LeaveApplicationDTO dto);
	
	@Autowired
	ApprovalRepository approvalRepository;
	
	@Autowired
	ApprovalMapper approvalMapper;
	
    @AfterMapping
    public void  populateRelations(LeaveApplication entity, @MappingTarget LeaveApplicationDTO dto) {
    	
    	List<Approval> approvals=approvalRepository.findByRefTableIdAndRefTable(entity.getId(), "LeaveApplication");
    	dto.setApprovals(approvalMapper.toDto(approvals));
    }
}
