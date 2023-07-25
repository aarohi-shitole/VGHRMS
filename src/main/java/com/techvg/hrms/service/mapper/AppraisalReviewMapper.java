package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AppraisalReview;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppraisalReview} and its DTO {@link AppraisalReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppraisalReviewMapper extends EntityMapper<AppraisalReviewDTO, AppraisalReview> {
   
    AppraisalReviewDTO toDto(AppraisalReview s);


    EmployeeDTO toDtoEmployeeId(Employee employee);
}
