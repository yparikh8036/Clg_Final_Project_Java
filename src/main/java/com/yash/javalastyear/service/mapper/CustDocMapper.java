package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.CustDocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustDoc and its DTO CustDocDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustDocMapper extends EntityMapper<CustDocDTO, CustDoc> {

    @Mapping(source = "customer.id", target = "customerId")
    CustDocDTO toDto(CustDoc custDoc);

    @Mapping(source = "customerId", target = "customer")
    CustDoc toEntity(CustDocDTO custDocDTO);

    default CustDoc fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustDoc custDoc = new CustDoc();
        custDoc.setId(id);
        return custDoc;
    }
}
