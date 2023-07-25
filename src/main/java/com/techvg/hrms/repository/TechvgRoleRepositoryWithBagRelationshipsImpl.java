package com.techvg.hrms.repository;

import com.techvg.hrms.domain.TechvgRole;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TechvgRoleRepositoryWithBagRelationshipsImpl implements TechvgRoleRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TechvgRole> fetchBagRelationships(Optional<TechvgRole> techvgRole) {
        return techvgRole.map(this::fetchTechvgPermissions);
    }

    @Override
    public Page<TechvgRole> fetchBagRelationships(Page<TechvgRole> techvgRoles) {
        return new PageImpl<>(fetchBagRelationships(techvgRoles.getContent()), techvgRoles.getPageable(), techvgRoles.getTotalElements());
    }

    @Override
    public List<TechvgRole> fetchBagRelationships(List<TechvgRole> techvgRoles) {
        return Optional.of(techvgRoles).map(this::fetchTechvgPermissions).orElse(Collections.emptyList());
    }

    TechvgRole fetchTechvgPermissions(TechvgRole result) {
        return entityManager
            .createQuery(
                "select techvgRole from TechvgRole techvgRole left join fetch techvgRole.techvgPermissions where techvgRole is :techvgRole",
                TechvgRole.class
            )
            .setParameter("techvgRole", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<TechvgRole> fetchTechvgPermissions(List<TechvgRole> techvgRoles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, techvgRoles.size()).forEach(index -> order.put(techvgRoles.get(index).getId(), index));
        List<TechvgRole> result = entityManager
            .createQuery(
                "select distinct techvgRole from TechvgRole techvgRole left join fetch techvgRole.techvgPermissions where techvgRole in :techvgRoles",
                TechvgRole.class
            )
            .setParameter("techvgRoles", techvgRoles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
