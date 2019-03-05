package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.CustSchedule;
import com.yash.javalastyear.repository.CustScheduleRepository;
import com.yash.javalastyear.service.dto.CustScheduleDTO;
import com.yash.javalastyear.service.mapper.CustScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CustSchedule.
 */
@Service
@Transactional
public class CustScheduleService {

    private final Logger log = LoggerFactory.getLogger(CustScheduleService.class);

    private final CustScheduleRepository custScheduleRepository;

    private final CustScheduleMapper custScheduleMapper;

    public CustScheduleService(CustScheduleRepository custScheduleRepository, CustScheduleMapper custScheduleMapper) {
        this.custScheduleRepository = custScheduleRepository;
        this.custScheduleMapper = custScheduleMapper;
    }

    /**
     * Save a custSchedule.
     *
     * @param custScheduleDTO the entity to save
     * @return the persisted entity
     */
    public CustScheduleDTO save(CustScheduleDTO custScheduleDTO) {
        log.debug("Request to save CustSchedule : {}", custScheduleDTO);
        CustSchedule custSchedule = custScheduleMapper.toEntity(custScheduleDTO);
        custSchedule = custScheduleRepository.save(custSchedule);
        return custScheduleMapper.toDto(custSchedule);
    }

    /**
     * Get all the custSchedules.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustScheduleDTO> findAll() {
        log.debug("Request to get all CustSchedules");
        return custScheduleRepository.findAll().stream()
            .map(custScheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one custSchedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CustScheduleDTO> findOne(Long id) {
        log.debug("Request to get CustSchedule : {}", id);
        return custScheduleRepository.findById(id)
            .map(custScheduleMapper::toDto);
    }

    /**
     * Delete the custSchedule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustSchedule : {}", id);
        custScheduleRepository.deleteById(id);
    }
}
