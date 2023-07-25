package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.service.dto.WorkExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkExperience} and its DTO {@link WorkExperienceDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkExperienceMapper extends EntityMapper<WorkExperienceDTO, WorkExperience> {}
