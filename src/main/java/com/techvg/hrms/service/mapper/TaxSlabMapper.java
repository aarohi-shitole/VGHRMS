package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.TaxSlab;
import com.techvg.hrms.service.dto.TaxSlabDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaxSlab} and its DTO {@link TaxSlabDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaxSlabMapper extends EntityMapper<TaxSlabDTO, TaxSlab> {}
