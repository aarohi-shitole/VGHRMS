package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Training;
import com.techvg.hrms.service.dto.TrainingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Training} and its DTO {@link TrainingDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {}
