package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.WorkingHours;
import com.techvg.hrms.service.dto.WorkingHoursDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingHours} and its DTO {@link WorkingHoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkingHoursMapper extends EntityMapper<WorkingHoursDTO, WorkingHours> {}
