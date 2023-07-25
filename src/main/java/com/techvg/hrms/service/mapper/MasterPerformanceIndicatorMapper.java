package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.MasterPerformanceIndicator;
import com.techvg.hrms.service.dto.MasterPerformanceIndicatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MasterPerformanceIndicator} and its DTO {@link MasterPerformanceIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface MasterPerformanceIndicatorMapper extends EntityMapper<MasterPerformanceIndicatorDTO, MasterPerformanceIndicator> {}
