package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AppraisalCommentsReview;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.service.dto.AppraisalCommentsReviewDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppraisalCommentsReview} and its DTO {@link AppraisalCommentsReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppraisalCommentsReviewMapper extends EntityMapper<AppraisalCommentsReviewDTO, AppraisalCommentsReview> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    AppraisalCommentsReviewDTO toDto(AppraisalCommentsReview s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
