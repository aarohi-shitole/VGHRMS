package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Address;
import com.techvg.hrms.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {}
