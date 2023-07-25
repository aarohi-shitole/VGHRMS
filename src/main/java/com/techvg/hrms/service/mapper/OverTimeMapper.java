package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.OverTime;
import com.techvg.hrms.service.dto.OverTimeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OverTime} and its DTO {@link OverTimeDTO}.
 */
@Mapper(componentModel = "spring")
public interface OverTimeMapper extends EntityMapper<OverTimeDTO, OverTime> {}
