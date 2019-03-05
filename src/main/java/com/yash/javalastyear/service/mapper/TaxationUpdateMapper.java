package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.TaxationUpdateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaxationUpdate and its DTO TaxationUpdateDTO.
 */
@Mapper(componentModel = "spring", uses = {TaxationCategoryMapper.class, EmployeeMapper.class})
public interface TaxationUpdateMapper extends EntityMapper<TaxationUpdateDTO, TaxationUpdate> {

    @Mapping(source = "taxationCategory.id", target = "taxationCategoryId")
    @Mapping(source = "employee.id", target = "employeeId")
    TaxationUpdateDTO toDto(TaxationUpdate taxationUpdate);

    @Mapping(source = "taxationCategoryId", target = "taxationCategory")
    @Mapping(source = "employeeId", target = "employee")
    TaxationUpdate toEntity(TaxationUpdateDTO taxationUpdateDTO);

    default TaxationUpdate fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaxationUpdate taxationUpdate = new TaxationUpdate();
        taxationUpdate.setId(id);
        return taxationUpdate;
    }
}
