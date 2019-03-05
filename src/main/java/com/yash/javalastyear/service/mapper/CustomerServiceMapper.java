package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.CustomerServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerService and its DTO CustomerServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ServiceMapper.class})
public interface CustomerServiceMapper extends EntityMapper<CustomerServiceDTO, CustomerService> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "service.id", target = "serviceId")
    CustomerServiceDTO toDto(CustomerService customerService);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "serviceId", target = "service")
    CustomerService toEntity(CustomerServiceDTO customerServiceDTO);

    default CustomerService fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerService customerService = new CustomerService();
        customerService.setId(id);
        return customerService;
    }
}
