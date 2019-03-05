package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.CustContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustContactRepository extends JpaRepository<CustContact, Long> {

}
