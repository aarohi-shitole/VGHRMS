package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Approval;
import com.techvg.hrms.domain.Resignation;
import com.techvg.hrms.repository.ApprovalRepository;
import com.techvg.hrms.service.dto.ResignationDTO;

import java.util.List;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Resignation} and its DTO {@link ResignationDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class ResignationMapper implements EntityMapper<ResignationDTO, Resignation> {

	public abstract ResignationDTO toDto(Resignation entity);

	public abstract Resignation toEntity(ResignationDTO dto);

	@Autowired
	public ApprovalRepository approvalRepository;

	@Autowired
	ApprovalMapper approvalMapper;

	@AfterMapping
	public void populateRelations(Resignation entity, @MappingTarget ResignationDTO dto) {

		List<Approval> approvals = approvalRepository.findByRefTableIdAndRefTable(entity.getId(), "Resignation");
		dto.setApprovals(approvalMapper.toDto(approvals));
	}

}
