package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.CustAccDoc;
import com.yash.javalastyear.repository.CustAccDocRepository;
import com.yash.javalastyear.service.dto.CustAccDocDTO;
import com.yash.javalastyear.service.mapper.CustAccDocMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CustAccDoc.
 */
@Service
@Transactional
public class CustAccDocService {

    private final Logger log = LoggerFactory.getLogger(CustAccDocService.class);

    private final CustAccDocRepository custAccDocRepository;

    private final CustAccDocMapper custAccDocMapper;

    public CustAccDocService(CustAccDocRepository custAccDocRepository, CustAccDocMapper custAccDocMapper) {
        this.custAccDocRepository = custAccDocRepository;
        this.custAccDocMapper = custAccDocMapper;
    }

    /**
     * Save a custAccDoc.
     *
     * @param custAccDocDTO the entity to save
     * @return the persisted entity
     */
    public CustAccDocDTO save(CustAccDocDTO custAccDocDTO) {
        log.debug("Request to save CustAccDoc : {}", custAccDocDTO);
        CustAccDoc custAccDoc = custAccDocMapper.toEntity(custAccDocDTO);
        custAccDoc = custAccDocRepository.save(custAccDoc);
        return custAccDocMapper.toDto(custAccDoc);
    }

    /**
     * Get all the custAccDocs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustAccDocDTO> findAll() {
        log.debug("Request to get all CustAccDocs");
        return custAccDocRepository.findAll().stream()
            .map(custAccDocMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one custAccDoc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CustAccDocDTO> findOne(Long id) {
        log.debug("Request to get CustAccDoc : {}", id);
        return custAccDocRepository.findById(id)
            .map(custAccDocMapper::toDto);
    }

    /**
     * Delete the custAccDoc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustAccDoc : {}", id);
        custAccDocRepository.deleteById(id);
    }
}
