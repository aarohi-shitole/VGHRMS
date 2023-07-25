package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.ApprovalLevel;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApprovalLevel} and its DTO {@link ApprovalLevelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalLevelMapper extends EntityMapper<ApprovalLevelDTO, ApprovalLevel> {}
