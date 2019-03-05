package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.TaxationUpdate;
import com.yash.javalastyear.repository.TaxationUpdateRepository;
import com.yash.javalastyear.service.dto.TaxationUpdateDTO;
import com.yash.javalastyear.service.mapper.TaxationUpdateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TaxationUpdate.
 */
@Service
@Transactional
public class TaxationUpdateService {

    private final Logger log = LoggerFactory.getLogger(TaxationUpdateService.class);

    private final TaxationUpdateRepository taxationUpdateRepository;

    private final TaxationUpdateMapper taxationUpdateMapper;

    public TaxationUpdateService(TaxationUpdateRepository taxationUpdateRepository, TaxationUpdateMapper taxationUpdateMapper) {
        this.taxationUpdateRepository = taxationUpdateRepository;
        this.taxationUpdateMapper = taxationUpdateMapper;
    }

    /**
     * Save a taxationUpdate.
     *
     * @param taxationUpdateDTO the entity to save
     * @return the persisted entity
     */
    public TaxationUpdateDTO save(TaxationUpdateDTO taxationUpdateDTO) {
        log.debug("Request to save TaxationUpdate : {}", taxationUpdateDTO);
        TaxationUpdate taxationUpdate = taxationUpdateMapper.toEntity(taxationUpdateDTO);
        taxationUpdate = taxationUpdateRepository.save(taxationUpdate);
        return taxationUpdateMapper.toDto(taxationUpdate);
    }

    /**
     * Get all the taxationUpdates.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TaxationUpdateDTO> findAll() {
        log.debug("Request to get all TaxationUpdates");
        return taxationUpdateRepository.findAll().stream()
            .map(taxationUpdateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one taxationUpdate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TaxationUpdateDTO> findOne(Long id) {
        log.debug("Request to get TaxationUpdate : {}", id);
        return taxationUpdateRepository.findById(id)
            .map(taxationUpdateMapper::toDto);
    }

    /**
     * Delete the taxationUpdate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TaxationUpdate : {}", id);
        taxationUpdateRepository.deleteById(id);
    }
}
