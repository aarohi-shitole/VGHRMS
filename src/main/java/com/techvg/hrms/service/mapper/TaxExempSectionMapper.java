package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.TaxExempSection;
import com.techvg.hrms.service.dto.TaxExempSectionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaxExempSection} and its DTO {@link TaxExempSectionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaxExempSectionMapper extends EntityMapper<TaxExempSectionDTO, TaxExempSection> {}
