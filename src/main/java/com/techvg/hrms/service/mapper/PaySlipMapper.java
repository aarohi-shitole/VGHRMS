package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PaySlip;
import com.techvg.hrms.service.dto.PaySlipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaySlip} and its DTO {@link PaySlipDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaySlipMapper extends EntityMapper<PaySlipDTO, PaySlip> {}
