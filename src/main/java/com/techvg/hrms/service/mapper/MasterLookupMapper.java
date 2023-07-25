package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.MasterLookup;
import com.techvg.hrms.service.dto.MasterLookupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MasterLookup} and its DTO {@link MasterLookupDTO}.
 */
@Mapper(componentModel = "spring")
public interface MasterLookupMapper extends EntityMapper<MasterLookupDTO, MasterLookup> {}
