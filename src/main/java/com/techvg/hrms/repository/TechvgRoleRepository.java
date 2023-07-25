package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TechvgRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TechvgRole entity.
 *
 * When extending this class, extend TechvgRoleRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TechvgRoleRepository
    extends TechvgRoleRepositoryWithBagRelationships, JpaRepository<TechvgRole, Long>, JpaSpecificationExecutor<TechvgRole> {
    default Optional<TechvgRole> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<TechvgRole> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<TechvgRole> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
