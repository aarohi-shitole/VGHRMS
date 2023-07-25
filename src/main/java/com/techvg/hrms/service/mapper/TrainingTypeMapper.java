package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.TrainingType;
import com.techvg.hrms.service.dto.TrainingTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingType} and its DTO {@link TrainingTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingTypeMapper extends EntityMapper<TrainingTypeDTO, TrainingType> {}
