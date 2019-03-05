package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.CustDoc;
import com.yash.javalastyear.repository.CustDocRepository;
import com.yash.javalastyear.service.dto.CustDocDTO;
import com.yash.javalastyear.service.mapper.CustDocMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CustDoc.
 */
@Service
@Transactional
public class CustDocService {

    private final Logger log = LoggerFactory.getLogger(CustDocService.class);

    private final CustDocRepository custDocRepository;

    private final CustDocMapper custDocMapper;

    public CustDocService(CustDocRepository custDocRepository, CustDocMapper custDocMapper) {
        this.custDocRepository = custDocRepository;
        this.custDocMapper = custDocMapper;
    }

    /**
     * Save a custDoc.
     *
     * @param custDocDTO the entity to save
     * @return the persisted entity
     */
    public CustDocDTO save(CustDocDTO custDocDTO) {
        log.debug("Request to save CustDoc : {}", custDocDTO);
        CustDoc custDoc = custDocMapper.toEntity(custDocDTO);
        custDoc = custDocRepository.save(custDoc);
        return custDocMapper.toDto(custDoc);
    }

    /**
     * Get all the custDocs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustDocDTO> findAll() {
        log.debug("Request to get all CustDocs");
        return custDocRepository.findAll().stream()
            .map(custDocMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one custDoc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CustDocDTO> findOne(Long id) {
        log.debug("Request to get CustDoc : {}", id);
        return custDocRepository.findById(id)
            .map(custDocMapper::toDto);
    }

    /**
     * Delete the custDoc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustDoc : {}", id);
        custDocRepository.deleteById(id);
    }
}
