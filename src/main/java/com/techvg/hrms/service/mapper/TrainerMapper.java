package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Trainer;
import com.techvg.hrms.service.dto.TrainerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trainer} and its DTO {@link TrainerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainerMapper extends EntityMapper<TrainerDTO, Trainer> {}
