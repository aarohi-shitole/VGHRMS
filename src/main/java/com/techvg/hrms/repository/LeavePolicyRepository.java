package com.techvg.hrms.repository;

import com.techvg.hrms.domain.LeavePolicy;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeavePolicy entity.
 */
@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long>, JpaSpecificationExecutor<LeavePolicy> {
    default Optional<LeavePolicy> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LeavePolicy> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LeavePolicy> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct leavePolicy from LeavePolicy leavePolicy left join fetch leavePolicy.leaveType left join fetch leavePolicy.employmentType",
        countQuery = "select count(distinct leavePolicy) from LeavePolicy leavePolicy"
    )
    Page<LeavePolicy> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct leavePolicy from LeavePolicy leavePolicy left join fetch leavePolicy.leaveType left join fetch leavePolicy.employmentType"
    )
    List<LeavePolicy> findAllWithToOneRelationships();

    @Query(
        "select leavePolicy from LeavePolicy leavePolicy left join fetch leavePolicy.leaveType left join fetch leavePolicy.employmentType where leavePolicy.id =:id"
    )
    Optional<LeavePolicy> findOneWithToOneRelationships(@Param("id") Long id);
}
