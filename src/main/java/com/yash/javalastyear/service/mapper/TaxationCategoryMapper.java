package com.yash.javalastyear.service.mapper;

import com.yash.javalastyear.domain.*;
import com.yash.javalastyear.service.dto.TaxationCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaxationCategory and its DTO TaxationCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaxationCategoryMapper extends EntityMapper<TaxationCategoryDTO, TaxationCategory> {



    default TaxationCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaxationCategory taxationCategory = new TaxationCategory();
        taxationCategory.setId(id);
        return taxationCategory;
    }
}
