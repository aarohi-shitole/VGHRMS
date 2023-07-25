package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.FormValidator;
import com.techvg.hrms.service.dto.FormValidatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormValidator} and its DTO {@link FormValidatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FormValidatorMapper extends EntityMapper<FormValidatorDTO, FormValidator> {}
