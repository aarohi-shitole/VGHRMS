package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.PersonalDetails;
import com.techvg.hrms.service.dto.PersonalDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalDetails} and its DTO {@link PersonalDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalDetailsMapper extends EntityMapper<PersonalDetailsDTO, PersonalDetails> {}
