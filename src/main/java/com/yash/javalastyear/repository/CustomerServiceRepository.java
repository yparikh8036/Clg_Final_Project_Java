package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.CustomerService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustomerService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long> {

}
