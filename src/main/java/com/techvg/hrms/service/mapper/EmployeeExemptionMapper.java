package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.EmployeeExemption;
import com.techvg.hrms.service.dto.EmployeeExemptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeExemption} and its DTO {@link EmployeeExemptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeExemptionMapper extends EntityMapper<EmployeeExemptionDTO, EmployeeExemption> {}
