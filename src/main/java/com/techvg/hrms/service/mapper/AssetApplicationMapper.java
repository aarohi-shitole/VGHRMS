package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AssetApplication;
import com.techvg.hrms.service.dto.AssetApplicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetApplication} and its DTO {@link AssetApplicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetApplicationMapper extends EntityMapper<AssetApplicationDTO, AssetApplication> {}
