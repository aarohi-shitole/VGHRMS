package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.WorkDaysSetting;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkDaysSetting} and its DTO {@link WorkDaysSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkDaysSettingMapper extends EntityMapper<WorkDaysSettingDTO, WorkDaysSetting> {}
