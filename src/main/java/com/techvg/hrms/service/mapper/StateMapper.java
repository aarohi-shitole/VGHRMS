package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.State;
import com.techvg.hrms.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDTO, State> {}
