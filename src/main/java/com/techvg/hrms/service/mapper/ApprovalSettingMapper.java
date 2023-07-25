package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.ApprovalSetting;
import com.techvg.hrms.service.dto.ApprovalSettingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApprovalSetting} and its DTO {@link ApprovalSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApprovalSettingMapper extends EntityMapper<ApprovalSettingDTO, ApprovalSetting> {}
