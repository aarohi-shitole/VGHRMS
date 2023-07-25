package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PersonalId;
import com.techvg.hrms.service.dto.PersonalIdDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalId} and its DTO {@link PersonalIdDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalIdMapper extends EntityMapper<PersonalIdDTO, PersonalId> {}
