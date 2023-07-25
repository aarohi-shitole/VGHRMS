package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Promotion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Promotion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {}
