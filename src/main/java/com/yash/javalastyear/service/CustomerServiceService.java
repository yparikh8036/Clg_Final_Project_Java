package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.CustomerService;
import com.yash.javalastyear.repository.CustomerServiceRepository;
import com.yash.javalastyear.service.dto.CustomerServiceDTO;
import com.yash.javalastyear.service.mapper.CustomerServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CustomerService.
 */
@Service
@Transactional
public class CustomerServiceService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceService.class);

    private final CustomerServiceRepository customerServiceRepository;

    private final CustomerServiceMapper customerServiceMapper;

    public CustomerServiceService(CustomerServiceRepository customerServiceRepository, CustomerServiceMapper customerServiceMapper) {
        this.customerServiceRepository = customerServiceRepository;
        this.customerServiceMapper = customerServiceMapper;
    }

    /**
     * Save a customerService.
     *
     * @param customerServiceDTO the entity to save
     * @return the persisted entity
     */
    public CustomerServiceDTO save(CustomerServiceDTO customerServiceDTO) {
        log.debug("Request to save CustomerService : {}", customerServiceDTO);
        CustomerService customerService = customerServiceMapper.toEntity(customerServiceDTO);
        customerService = customerServiceRepository.save(customerService);
        return customerServiceMapper.toDto(customerService);
    }

    /**
     * Get all the customerServices.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustomerServiceDTO> findAll() {
        log.debug("Request to get all CustomerServices");
        return customerServiceRepository.findAll().stream()
            .map(customerServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CustomerServiceDTO> findOne(Long id) {
        log.debug("Request to get CustomerService : {}", id);
        return customerServiceRepository.findById(id)
            .map(customerServiceMapper::toDto);
    }

    /**
     * Delete the customerService by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerService : {}", id);
        customerServiceRepository.deleteById(id);
    }
}
