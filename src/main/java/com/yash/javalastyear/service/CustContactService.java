package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.CustContact;
import com.yash.javalastyear.repository.CustContactRepository;
import com.yash.javalastyear.service.dto.CustContactDTO;
import com.yash.javalastyear.service.mapper.CustContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing CustContact.
 */
@Service
@Transactional
public class CustContactService {

    private final Logger log = LoggerFactory.getLogger(CustContactService.class);

    private final CustContactRepository custContactRepository;

    private final CustContactMapper custContactMapper;

    public CustContactService(CustContactRepository custContactRepository, CustContactMapper custContactMapper) {
        this.custContactRepository = custContactRepository;
        this.custContactMapper = custContactMapper;
    }

    /**
     * Save a custContact.
     *
     * @param custContactDTO the entity to save
     * @return the persisted entity
     */
    public CustContactDTO save(CustContactDTO custContactDTO) {
        log.debug("Request to save CustContact : {}", custContactDTO);
        CustContact custContact = custContactMapper.toEntity(custContactDTO);
        custContact = custContactRepository.save(custContact);
        return custContactMapper.toDto(custContact);
    }

    /**
     * Get all the custContacts.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustContactDTO> findAll() {
        log.debug("Request to get all CustContacts");
        return custContactRepository.findAll().stream()
            .map(custContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the custContacts where Customer is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CustContactDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custContacts where Customer is null");
        return StreamSupport
            .stream(custContactRepository.findAll().spliterator(), false)
            .filter(custContact -> custContact.getCustomer() == null)
            .map(custContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one custContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CustContactDTO> findOne(Long id) {
        log.debug("Request to get CustContact : {}", id);
        return custContactRepository.findById(id)
            .map(custContactMapper::toDto);
    }

    /**
     * Delete the custContact by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustContact : {}", id);
        custContactRepository.deleteById(id);
    }
}
