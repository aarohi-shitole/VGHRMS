package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Termination;
import com.techvg.hrms.service.dto.TerminationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Termination} and its DTO {@link TerminationDTO}.
 */
@Mapper(componentModel = "spring")
public interface TerminationMapper extends EntityMapper<TerminationDTO, Termination> {}
