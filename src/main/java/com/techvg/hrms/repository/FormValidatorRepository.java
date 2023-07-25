package com.techvg.hrms.repository;

import com.techvg.hrms.domain.FormValidator;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormValidator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormValidatorRepository extends JpaRepository<FormValidator, Long>, JpaSpecificationExecutor<FormValidator> {
    List<FormValidator> findByFormNameAndFieldNameAndStatus(String formName, String fieldName, String string);
}
