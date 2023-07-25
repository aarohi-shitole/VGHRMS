package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.EsiDetails;
import com.techvg.hrms.service.dto.EsiDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EsiDetails} and its DTO {@link EsiDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EsiDetailsMapper extends EntityMapper<EsiDetailsDTO, EsiDetails> {}
