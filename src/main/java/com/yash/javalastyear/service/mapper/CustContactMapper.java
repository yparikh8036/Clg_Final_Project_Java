package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.CustContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustContact and its DTO CustContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustContactMapper extends EntityMapper<CustContactDTO, CustContact> {


    @Mapping(target = "customer", ignore = true)
    CustContact toEntity(CustContactDTO custContactDTO);

    default CustContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustContact custContact = new CustContact();
        custContact.setId(id);
        return custContact;
    }
}
