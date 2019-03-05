package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.InternalDocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InternalDoc and its DTO InternalDocDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface InternalDocMapper extends EntityMapper<InternalDocDTO, InternalDoc> {

    @Mapping(source = "employee.id", target = "employeeId")
    InternalDocDTO toDto(InternalDoc internalDoc);

    @Mapping(source = "employeeId", target = "employee")
    InternalDoc toEntity(InternalDocDTO internalDocDTO);

    default InternalDoc fromId(Long id) {
        if (id == null) {
            return null;
        }
        InternalDoc internalDoc = new InternalDoc();
        internalDoc.setId(id);
        return internalDoc;
    }
}
