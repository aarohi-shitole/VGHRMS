package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Education;
import com.techvg.hrms.service.dto.EducationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Education} and its DTO {@link EducationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {}
