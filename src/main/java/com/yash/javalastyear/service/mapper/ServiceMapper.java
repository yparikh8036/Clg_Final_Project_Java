package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.ServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Service and its DTO ServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ServiceMapper extends EntityMapper<ServiceDTO, Service> {

    @Mapping(source = "employee.id", target = "employeeId")
    ServiceDTO toDto(Service service);

    @Mapping(source = "employeeId", target = "employee")
    Service toEntity(ServiceDTO serviceDTO);

    default Service fromId(Long id) {
        if (id == null) {
            return null;
        }
        Service service = new Service();
        service.setId(id);
        return service;
    }
}
