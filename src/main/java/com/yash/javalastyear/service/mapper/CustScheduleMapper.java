package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.CustScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustSchedule and its DTO CustScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerServiceMapper.class})
public interface CustScheduleMapper extends EntityMapper<CustScheduleDTO, CustSchedule> {

    @Mapping(source = "customerService.id", target = "customerServiceId")
    CustScheduleDTO toDto(CustSchedule custSchedule);

    @Mapping(source = "customerServiceId", target = "customerService")
    CustSchedule toEntity(CustScheduleDTO custScheduleDTO);

    default CustSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustSchedule custSchedule = new CustSchedule();
        custSchedule.setId(id);
        return custSchedule;
    }
}
