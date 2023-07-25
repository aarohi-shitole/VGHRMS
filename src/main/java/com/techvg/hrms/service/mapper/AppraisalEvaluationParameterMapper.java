package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import com.techvg.hrms.service.dto.AppraisalEvaluationParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppraisalEvaluationParameter} and its DTO {@link AppraisalEvaluationParameterDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppraisalEvaluationParameterMapper extends EntityMapper<AppraisalEvaluationParameterDTO, AppraisalEvaluationParameter> {}
