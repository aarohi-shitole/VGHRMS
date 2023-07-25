package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Deduction;
import com.techvg.hrms.service.dto.DeductionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deduction} and its DTO {@link DeductionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeductionMapper extends EntityMapper<DeductionDTO, Deduction> {}
