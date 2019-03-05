package com.yash.javalastyear.service;

import com.yash.javalastyear.domain.Bill;
import com.yash.javalastyear.repository.BillRepository;
import com.yash.javalastyear.service.dto.BillDTO;
import com.yash.javalastyear.service.mapper.BillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Bill.
 */
@Service
@Transactional
public class BillService {

    private final Logger log = LoggerFactory.getLogger(BillService.class);

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    public BillService(BillRepository billRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
    }

    /**
     * Save a bill.
     *
     * @param billDTO the entity to save
     * @return the persisted entity
     */
    public BillDTO save(BillDTO billDTO) {
        log.debug("Request to save Bill : {}", billDTO);
        Bill bill = billMapper.toEntity(billDTO);
        bill = billRepository.save(bill);
        return billMapper.toDto(bill);
    }

    /**
     * Get all the bills.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BillDTO> findAll() {
        log.debug("Request to get all Bills");
        return billRepository.findAll().stream()
            .map(billMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BillDTO> findOne(Long id) {
        log.debug("Request to get Bill : {}", id);
        return billRepository.findById(id)
            .map(billMapper::toDto);
    }

    /**
     * Delete the bill by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bill : {}", id);
        billRepository.deleteById(id);
    }
}
