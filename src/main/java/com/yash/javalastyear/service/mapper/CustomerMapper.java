package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.CustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(source = "employee.id", target = "employeeId")
    CustomerDTO toDto(Customer customer);

    @Mapping(source = "employeeId", target = "employee")
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
