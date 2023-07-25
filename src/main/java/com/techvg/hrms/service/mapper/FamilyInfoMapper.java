package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.FamilyInfo;
import com.techvg.hrms.service.dto.FamilyInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyInfo} and its DTO {@link FamilyInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FamilyInfoMapper extends EntityMapper<FamilyInfoDTO, FamilyInfo> {}
