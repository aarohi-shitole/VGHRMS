package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Salary;
import com.techvg.hrms.service.dto.SalaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Salary} and its DTO {@link SalaryDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalaryMapper extends EntityMapper<SalaryDTO, Salary> {}
