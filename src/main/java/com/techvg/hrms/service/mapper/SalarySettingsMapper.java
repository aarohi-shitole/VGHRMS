package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.SalarySettings;
import com.techvg.hrms.service.dto.SalarySettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalarySettings} and its DTO {@link SalarySettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalarySettingsMapper extends EntityMapper<SalarySettingsDTO, SalarySettings> {}
