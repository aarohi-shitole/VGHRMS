package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Tds;
import com.techvg.hrms.service.dto.TdsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tds} and its DTO {@link TdsDTO}.
 */
@Mapper(componentModel = "spring")
public interface TdsMapper extends EntityMapper<TdsDTO, Tds> {}
