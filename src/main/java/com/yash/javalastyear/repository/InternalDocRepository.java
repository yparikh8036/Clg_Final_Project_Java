package com.yash.javalastyear.repository;

import com.yash.javalastyear.domain.InternalDoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InternalDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InternalDocRepository extends JpaRepository<InternalDoc, Long> {

}
