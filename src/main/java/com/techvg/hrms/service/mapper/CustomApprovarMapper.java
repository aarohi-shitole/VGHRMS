package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.CustomApprovar;
import com.techvg.hrms.service.dto.CustomApprovarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomApprovar} and its DTO {@link CustomApprovarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomApprovarMapper extends EntityMapper<CustomApprovarDTO, CustomApprovar> {}
