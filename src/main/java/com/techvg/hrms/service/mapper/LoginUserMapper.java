package com.techvg.hrms.service.mapper;

import java.util.HashSet;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.hrms.domain.TechvgRole;
import com.techvg.hrms.domain.User;
import com.techvg.hrms.service.dto.AdminUserDTO;

@Mapper(componentModel = "spring", uses = {})
public interface LoginUserMapper extends EntityMapper<AdminUserDTO, User> {
    // @Mapping(source = "userAccess", target = "userAccess")
    AdminUserDTO toDto(User user);


    @Mapping(target = "removeTechvgRole", ignore = true)
    User toEntity(AdminUserDTO adminUserDTO);

    default User fromId(String id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    @AfterMapping
    default void populateAuthorities(User entity, @MappingTarget AdminUserDTO dto) {
        Set<String> authorities = new HashSet<>();
        Set<String> authorities1 = new HashSet<>();


        for (TechvgRole role : entity.getTechvgRoles()) {
            authorities1 = role.getTechvgPermissions().stream().map(permission->{return permission.getPermissionName();}).collect(Collectors.toSet());
            authorities.addAll(authorities1);
        }
        dto.setAuthorities(authorities);
    }
    
    @AfterMapping
    default void populateRoles(User entity, @MappingTarget AdminUserDTO dto) {
        Set<TechvgRole> roles = new HashSet<>();

        roles =
            entity
                .getTechvgRoles();
//                .stream()
//                .map(role -> {
//                    return role.getName();
//                })
////                .collect(Collectors.toSet());

        dto.setRoles(roles);
    }
}
