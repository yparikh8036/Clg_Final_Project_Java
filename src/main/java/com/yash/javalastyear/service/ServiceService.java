package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.Service;
import com.yash.javalastyear.repository.ServiceRepository;
import com.yash.javalastyear.service.dto.ServiceDTO;
import com.yash.javalastyear.service.mapper.ServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Service.
 */
@Service
@Transactional
public class ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    /**
     * Save a service.
     *
     * @param serviceDTO the entity to save
     * @return the persisted entity
     */
    public ServiceDTO save(ServiceDTO serviceDTO) {
        log.debug("Request to save Service : {}", serviceDTO);
        Service service = serviceMapper.toEntity(serviceDTO);
        service = serviceRepository.save(service);
        return serviceMapper.toDto(service);
    }

    /**
     * Get all the services.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ServiceDTO> findAll() {
        log.debug("Request to get all Services");
        return serviceRepository.findAll().stream()
            .map(serviceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one service by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ServiceDTO> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id)
            .map(serviceMapper::toDto);
    }

    /**
     * Delete the service by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}
