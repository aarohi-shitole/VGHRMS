package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.TechvgPermission;
import com.techvg.hrms.domain.TechvgRole;
import com.techvg.hrms.service.dto.TechvgPermissionDTO;
import com.techvg.hrms.service.dto.TechvgRoleDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TechvgRole} and its DTO {@link TechvgRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface TechvgRoleMapper extends EntityMapper<TechvgRoleDTO, TechvgRole> {
    @Mapping(target = "techvgPermissions", source = "techvgPermissions", qualifiedByName = "techvgPermissionPermissionNameSet")
    TechvgRoleDTO toDto(TechvgRole s);

    @Mapping(target = "removeTechvgPermission", ignore = true)
    TechvgRole toEntity(TechvgRoleDTO techvgRoleDTO);

    @Named("techvgPermissionPermissionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "permissionName", source = "permissionName")
    TechvgPermissionDTO toDtoTechvgPermissionPermissionName(TechvgPermission techvgPermission);

    @Named("techvgPermissionPermissionNameSet")
    default Set<TechvgPermissionDTO> toDtoTechvgPermissionPermissionNameSet(Set<TechvgPermission> techvgPermission) {
        return techvgPermission.stream().map(this::toDtoTechvgPermissionPermissionName).collect(Collectors.toSet());
    }
}
