package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Department;
import com.techvg.hrms.service.dto.DepartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {}
