package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.EmployeeSalaryComponent;
import com.techvg.hrms.service.dto.EmployeeSalaryComponentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeSalaryComponent} and its DTO {@link EmployeeSalaryComponentDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeSalaryComponentMapper extends EntityMapper<EmployeeSalaryComponentDTO, EmployeeSalaryComponent> {}
