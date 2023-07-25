package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AppraisalReview;
import com.techvg.hrms.domain.PerformanceAppraisal;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.dto.PerformanceAppraisalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerformanceAppraisal} and its DTO {@link PerformanceAppraisalDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerformanceAppraisalMapper extends EntityMapper<PerformanceAppraisalDTO, PerformanceAppraisal> {
    @Mapping(target = "appraisalReview", source = "appraisalReview", qualifiedByName = "appraisalReviewId")
    PerformanceAppraisalDTO toDto(PerformanceAppraisal s);

    @Named("appraisalReviewId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppraisalReviewDTO toDtoAppraisalReviewId(AppraisalReview appraisalReview);
}
