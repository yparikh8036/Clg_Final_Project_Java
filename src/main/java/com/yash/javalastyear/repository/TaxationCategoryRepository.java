package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.TaxationCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaxationCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxationCategoryRepository extends JpaRepository<TaxationCategory, Long> {

}
