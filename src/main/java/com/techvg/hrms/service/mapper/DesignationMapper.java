package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Designation;
import com.techvg.hrms.service.dto.DesignationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Designation} and its DTO {@link DesignationDTO}.
 */
@Mapper(componentModel = "spring")
public interface DesignationMapper extends EntityMapper<DesignationDTO, Designation> {}
