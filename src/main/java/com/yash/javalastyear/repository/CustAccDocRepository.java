package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.CustAccDoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustAccDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustAccDocRepository extends JpaRepository<CustAccDoc, Long> {

}
