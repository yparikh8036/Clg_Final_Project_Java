package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.BillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bill and its DTO BillDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, EmployeeMapper.class})
public interface BillMapper extends EntityMapper<BillDTO, Bill> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "employee.id", target = "employeeId")
    BillDTO toDto(Bill bill);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "employeeId", target = "employee")
    Bill toEntity(BillDTO billDTO);

    default Bill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
