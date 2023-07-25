package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Approval;
import com.techvg.hrms.service.dto.ApprovalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Approval} and its DTO {@link ApprovalDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalMapper extends EntityMapper<ApprovalDTO, Approval> {}
