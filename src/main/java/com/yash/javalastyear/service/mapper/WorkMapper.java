package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.WorkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Work and its DTO WorkDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface WorkMapper extends EntityMapper<WorkDTO, Work> {

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "assigned.id", target = "assignedId")
    WorkDTO toDto(Work work);

    @Mapping(source = "creatorId", target = "creator")
    @Mapping(source = "assignedId", target = "assigned")
    Work toEntity(WorkDTO workDTO);

    default Work fromId(Long id) {
        if (id == null) {
            return null;
        }
        Work work = new Work();
        work.setId(id);
        return work;
    }
}
