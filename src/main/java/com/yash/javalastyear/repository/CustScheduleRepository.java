package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.CustSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CustSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustScheduleRepository extends JpaRepository<CustSchedule, Long> {

}
