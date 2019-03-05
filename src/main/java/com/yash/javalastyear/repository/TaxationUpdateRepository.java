package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.TaxationUpdate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaxationUpdate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxationUpdateRepository extends JpaRepository<TaxationUpdate, Long> {

}
