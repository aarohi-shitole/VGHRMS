package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.AssetInventory;
import com.techvg.hrms.service.dto.AssetInventoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetInventory} and its DTO {@link AssetInventoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetInventoryMapper extends EntityMapper<AssetInventoryDTO, AssetInventory> {}
