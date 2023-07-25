package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PfDetails;
import com.techvg.hrms.service.dto.PfDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PfDetails} and its DTO {@link PfDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PfDetailsMapper extends EntityMapper<PfDetailsDTO, PfDetails> {}
