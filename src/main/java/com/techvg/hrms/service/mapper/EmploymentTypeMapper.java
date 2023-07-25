package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.EmploymentType;
import com.techvg.hrms.service.dto.EmploymentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmploymentType} and its DTO {@link EmploymentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmploymentTypeMapper extends EntityMapper<EmploymentTypeDTO, EmploymentType> {}
