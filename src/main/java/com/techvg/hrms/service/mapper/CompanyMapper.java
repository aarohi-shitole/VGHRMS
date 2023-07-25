package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Company;
import com.techvg.hrms.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {}
