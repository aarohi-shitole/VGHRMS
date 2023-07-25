package com.techvg.hrms.repository;

import com.techvg.hrms.domain.EmployeeLeaveAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeLeaveAccount entity.
 */
@Repository
public interface EmployeeLeaveAccountRepository
    extends JpaRepository<EmployeeLeaveAccount, Long>, JpaSpecificationExecutor<EmployeeLeaveAccount> {
    default Optional<EmployeeLeaveAccount> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EmployeeLeaveAccount> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EmployeeLeaveAccount> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct employeeLeaveAccount from EmployeeLeaveAccount employeeLeaveAccount left join fetch employeeLeaveAccount.leaveType",
        countQuery = "select count(distinct employeeLeaveAccount) from EmployeeLeaveAccount employeeLeaveAccount"
    )
    Page<EmployeeLeaveAccount> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct employeeLeaveAccount from EmployeeLeaveAccount employeeLeaveAccount left join fetch employeeLeaveAccount.leaveType"
    )
    List<EmployeeLeaveAccount> findAllWithToOneRelationships();

    @Query(
        "select employeeLeaveAccount from EmployeeLeaveAccount employeeLeaveAccount left join fetch employeeLeaveAccount.leaveType where employeeLeaveAccount.id =:id"
    )
    Optional<EmployeeLeaveAccount> findOneWithToOneRelationships(@Param("id") Long id);
}
