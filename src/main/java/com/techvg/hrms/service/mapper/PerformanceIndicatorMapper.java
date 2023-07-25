package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PerformanceIndicator;
import com.techvg.hrms.service.dto.PerformanceIndicatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerformanceIndicator} and its DTO {@link PerformanceIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerformanceIndicatorMapper extends EntityMapper<PerformanceIndicatorDTO, PerformanceIndicator> {
    @Mapping(target = "masterPerformanceIndicator.id", source = "masterPerformanceIndicator.id")
    PerformanceIndicatorDTO toDto(PerformanceIndicator s);

  
    @Mapping(source = "masterPerformanceIndicator.id", target = "masterPerformanceIndicator.id")
    PerformanceIndicator toEntity(PerformanceIndicatorDTO performanceIndicatorDTO);
 
 
    
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract PerformanceIndicatorDTO toDtoId(PerformanceIndicatorDTO performanceIndicatorDTO);
}
