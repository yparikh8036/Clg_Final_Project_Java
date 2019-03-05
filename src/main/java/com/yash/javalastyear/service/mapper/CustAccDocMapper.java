package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.CustAccDocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustAccDoc and its DTO CustAccDocDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface CustAccDocMapper extends EntityMapper<CustAccDocDTO, CustAccDoc> {

    @Mapping(source = "customer.id", target = "customerId")
    CustAccDocDTO toDto(CustAccDoc custAccDoc);

    @Mapping(source = "customerId", target = "customer")
    CustAccDoc toEntity(CustAccDocDTO custAccDocDTO);

    default CustAccDoc fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustAccDoc custAccDoc = new CustAccDoc();
        custAccDoc.setId(id);
        return custAccDoc;
    }
}
