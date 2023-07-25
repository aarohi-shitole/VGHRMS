package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.BanksDetails;
import com.techvg.hrms.service.dto.BanksDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BanksDetails} and its DTO {@link BanksDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface BanksDetailsMapper extends EntityMapper<BanksDetailsDTO, BanksDetails> {}
