package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PayrollAdditions;
import com.techvg.hrms.service.dto.PayrollAdditionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PayrollAdditions} and its DTO {@link PayrollAdditionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PayrollAdditionsMapper extends EntityMapper<PayrollAdditionsDTO, PayrollAdditions> {}
