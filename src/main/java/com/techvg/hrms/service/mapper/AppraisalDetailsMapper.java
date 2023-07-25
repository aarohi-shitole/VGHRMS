package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AppraisalDetails;
import com.techvg.hrms.service.dto.AppraisalDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppraisalDetails} and its DTO {@link AppraisalDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppraisalDetailsMapper extends EntityMapper<AppraisalDetailsDTO, AppraisalDetails> {
    @Mapping(target = "performanceIndicator.id", source = "performanceIndicator.id")
    AppraisalDetailsDTO toDto(AppraisalDetails s);

    @Mapping(source = "performanceIndicator.id", target = "performanceIndicator.id")
    AppraisalDetails toEntity(AppraisalDetailsDTO appraisalDetailsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract AppraisalDetailsDTO toDtoId(AppraisalDetailsDTO appraisalDetailsDTO);
}
