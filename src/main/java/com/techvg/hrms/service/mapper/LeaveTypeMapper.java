package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.LeaveType;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveType} and its DTO {@link LeaveTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeaveTypeMapper extends EntityMapper<LeaveTypeDTO, LeaveType> {}
