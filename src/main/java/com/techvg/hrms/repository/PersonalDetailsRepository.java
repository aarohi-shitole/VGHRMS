package com.techvg.hrms.repository;

import com.techvg.hrms.domain.PersonalDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonalDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long>, JpaSpecificationExecutor<PersonalDetails> {}
