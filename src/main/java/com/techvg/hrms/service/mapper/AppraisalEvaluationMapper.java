package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AppraisalEvaluation;
import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import com.techvg.hrms.service.dto.AppraisalEvaluationDTO;
import com.techvg.hrms.service.dto.AppraisalEvaluationParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppraisalEvaluation} and its DTO {@link AppraisalEvaluationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppraisalEvaluationMapper extends EntityMapper<AppraisalEvaluationDTO, AppraisalEvaluation> {
    @Mapping(
        target = "appraisalEvaluationParameter",
        source = "appraisalEvaluationParameter",
        qualifiedByName = "appraisalEvaluationParameterId"
    )
    AppraisalEvaluationDTO toDto(AppraisalEvaluation s);

    @Named("appraisalEvaluationParameterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppraisalEvaluationParameterDTO toDtoAppraisalEvaluationParameterId(AppraisalEvaluationParameter appraisalEvaluationParameter);
}
