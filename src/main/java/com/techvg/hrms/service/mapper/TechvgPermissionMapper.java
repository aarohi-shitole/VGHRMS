package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.TechvgPermission;
import com.techvg.hrms.service.dto.TechvgPermissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechvgPermission} and its DTO {@link TechvgPermissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TechvgPermissionMapper extends EntityMapper<TechvgPermissionDTO, TechvgPermission> {}
