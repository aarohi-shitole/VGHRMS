package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Contacts;
import com.techvg.hrms.service.dto.ContactsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contacts} and its DTO {@link ContactsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactsMapper extends EntityMapper<ContactsDTO, Contacts> {}
