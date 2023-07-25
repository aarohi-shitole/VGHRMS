package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TechvgRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link TechvgRole} entity.
 */
public interface AuthorityRepository extends JpaRepository<TechvgRole, String> {}
