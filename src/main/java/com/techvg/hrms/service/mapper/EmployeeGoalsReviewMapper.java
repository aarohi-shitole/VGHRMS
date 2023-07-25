package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.EmployeeGoalsReview;
import com.techvg.hrms.service.dto.EmployeeGoalsReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeGoalsReview} and its DTO {@link EmployeeGoalsReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeGoalsReviewMapper extends EntityMapper<EmployeeGoalsReviewDTO, EmployeeGoalsReview> {}
