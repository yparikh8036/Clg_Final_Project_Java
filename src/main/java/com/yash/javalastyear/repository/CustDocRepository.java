package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.CustDoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustDocRepository extends JpaRepository<CustDoc, Long> {

}
