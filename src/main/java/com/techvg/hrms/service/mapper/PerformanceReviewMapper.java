package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PerformanceIndicator;
import com.techvg.hrms.domain.PerformanceReview;
import com.techvg.hrms.service.dto.PerformanceIndicatorDTO;
import com.techvg.hrms.service.dto.PerformanceReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerformanceReview} and its DTO {@link PerformanceReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerformanceReviewMapper extends EntityMapper<PerformanceReviewDTO, PerformanceReview> {
    @Mapping(target = "performanceIndicator.id", source = "performanceIndicator.id")
    PerformanceReviewDTO toDto(PerformanceReview s);

    @Mapping(target = "performanceIndicator.id", source = "performanceIndicator.id")
    PerformanceReview toEntity(PerformanceReviewDTO pr);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract PerformanceReviewDTO toDtoId(PerformanceReviewDTO performanceReviewDTO);
}
