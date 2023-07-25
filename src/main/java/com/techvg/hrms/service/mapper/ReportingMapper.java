package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Reporting;
import com.techvg.hrms.service.dto.ReportingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reporting} and its DTO {@link ReportingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportingMapper extends EntityMapper<ReportingDTO, Reporting> {}
