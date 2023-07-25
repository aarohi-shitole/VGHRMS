package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Promotion;
import com.techvg.hrms.service.dto.PromotionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Promotion} and its DTO {@link PromotionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PromotionMapper extends EntityMapper<PromotionDTO, Promotion> {}
