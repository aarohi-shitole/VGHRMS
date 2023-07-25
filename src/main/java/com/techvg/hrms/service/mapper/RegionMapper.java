package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Region;
import com.techvg.hrms.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {}
