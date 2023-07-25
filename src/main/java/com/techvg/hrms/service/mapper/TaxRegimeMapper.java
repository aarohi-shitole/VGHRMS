package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.TaxRegime;
import com.techvg.hrms.service.dto.TaxRegimeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaxRegime} and its DTO {@link TaxRegimeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaxRegimeMapper extends EntityMapper<TaxRegimeDTO, TaxRegime> {}
