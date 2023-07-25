package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Branch;
import com.techvg.hrms.service.dto.BranchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {}
