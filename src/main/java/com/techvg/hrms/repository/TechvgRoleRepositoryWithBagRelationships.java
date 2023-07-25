package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TechvgRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TechvgRoleRepositoryWithBagRelationships {
    Optional<TechvgRole> fetchBagRelationships(Optional<TechvgRole> techvgRole);

    List<TechvgRole> fetchBagRelationships(List<TechvgRole> techvgRoles);

    Page<TechvgRole> fetchBagRelationships(Page<TechvgRole> techvgRoles);
}
