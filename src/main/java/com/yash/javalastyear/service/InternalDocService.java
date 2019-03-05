package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.InternalDoc;
import com.yash.javalastyear.repository.InternalDocRepository;
import com.yash.javalastyear.service.dto.InternalDocDTO;
import com.yash.javalastyear.service.mapper.InternalDocMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InternalDoc.
 */
@Service
@Transactional
public class InternalDocService {

    private final Logger log = LoggerFactory.getLogger(InternalDocService.class);

    private final InternalDocRepository internalDocRepository;

    private final InternalDocMapper internalDocMapper;

    public InternalDocService(InternalDocRepository internalDocRepository, InternalDocMapper internalDocMapper) {
        this.internalDocRepository = internalDocRepository;
        this.internalDocMapper = internalDocMapper;
    }

    /**
     * Save a internalDoc.
     *
     * @param internalDocDTO the entity to save
     * @return the persisted entity
     */
    public InternalDocDTO save(InternalDocDTO internalDocDTO) {
        log.debug("Request to save InternalDoc : {}", internalDocDTO);
        InternalDoc internalDoc = internalDocMapper.toEntity(internalDocDTO);
        internalDoc = internalDocRepository.save(internalDoc);
        return internalDocMapper.toDto(internalDoc);
    }

    /**
     * Get all the internalDocs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InternalDocDTO> findAll() {
        log.debug("Request to get all InternalDocs");
        return internalDocRepository.findAll().stream()
            .map(internalDocMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one internalDoc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InternalDocDTO> findOne(Long id) {
        log.debug("Request to get InternalDoc : {}", id);
        return internalDocRepository.findById(id)
            .map(internalDocMapper::toDto);
    }

    /**
     * Delete the internalDoc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InternalDoc : {}", id);
        internalDocRepository.deleteById(id);
    }
}
