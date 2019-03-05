package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.TaxationCategory;
import com.yash.javalastyear.repository.TaxationCategoryRepository;
import com.yash.javalastyear.service.dto.TaxationCategoryDTO;
import com.yash.javalastyear.service.mapper.TaxationCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TaxationCategory.
 */
@Service
@Transactional
public class TaxationCategoryService {

    private final Logger log = LoggerFactory.getLogger(TaxationCategoryService.class);

    private final TaxationCategoryRepository taxationCategoryRepository;

    private final TaxationCategoryMapper taxationCategoryMapper;

    public TaxationCategoryService(TaxationCategoryRepository taxationCategoryRepository, TaxationCategoryMapper taxationCategoryMapper) {
        this.taxationCategoryRepository = taxationCategoryRepository;
        this.taxationCategoryMapper = taxationCategoryMapper;
    }

    /**
     * Save a taxationCategory.
     *
     * @param taxationCategoryDTO the entity to save
     * @return the persisted entity
     */
    public TaxationCategoryDTO save(TaxationCategoryDTO taxationCategoryDTO) {
        log.debug("Request to save TaxationCategory : {}", taxationCategoryDTO);
        TaxationCategory taxationCategory = taxationCategoryMapper.toEntity(taxationCategoryDTO);
        taxationCategory = taxationCategoryRepository.save(taxationCategory);
        return taxationCategoryMapper.toDto(taxationCategory);
    }

    /**
     * Get all the taxationCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TaxationCategoryDTO> findAll() {
        log.debug("Request to get all TaxationCategories");
        return taxationCategoryRepository.findAll().stream()
            .map(taxationCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one taxationCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TaxationCategoryDTO> findOne(Long id) {
        log.debug("Request to get TaxationCategory : {}", id);
        return taxationCategoryRepository.findById(id)
            .map(taxationCategoryMapper::toDto);
    }

    /**
     * Delete the taxationCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TaxationCategory : {}", id);
        taxationCategoryRepository.deleteById(id);
    }
}
