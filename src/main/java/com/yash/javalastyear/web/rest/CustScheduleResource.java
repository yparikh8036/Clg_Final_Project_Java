package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.CustScheduleService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.CustScheduleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustSchedule.
 */
@RestController
@RequestMapping("/api")
public class CustScheduleResource {

    private final Logger log = LoggerFactory.getLogger(CustScheduleResource.class);

    private static final String ENTITY_NAME = "custSchedule";

    private final CustScheduleService custScheduleService;

    public CustScheduleResource(CustScheduleService custScheduleService) {
        this.custScheduleService = custScheduleService;
    }

    /**
     * POST  /cust-schedules : Create a new custSchedule.
     *
     * @param custScheduleDTO the custScheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new custScheduleDTO, or with status 400 (Bad Request) if the custSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cust-schedules")
    public ResponseEntity<CustScheduleDTO> createCustSchedule(@RequestBody CustScheduleDTO custScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save CustSchedule : {}", custScheduleDTO);
        if (custScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new custSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustScheduleDTO result = custScheduleService.save(custScheduleDTO);
        return ResponseEntity.created(new URI("/api/cust-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cust-schedules : Updates an existing custSchedule.
     *
     * @param custScheduleDTO the custScheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated custScheduleDTO,
     * or with status 400 (Bad Request) if the custScheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the custScheduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cust-schedules")
    public ResponseEntity<CustScheduleDTO> updateCustSchedule(@RequestBody CustScheduleDTO custScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update CustSchedule : {}", custScheduleDTO);
        if (custScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustScheduleDTO result = custScheduleService.save(custScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, custScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cust-schedules : get all the custSchedules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of custSchedules in body
     */
    @GetMapping("/cust-schedules")
    public List<CustScheduleDTO> getAllCustSchedules() {
        log.debug("REST request to get all CustSchedules");
        return custScheduleService.findAll();
    }

    /**
     * GET  /cust-schedules/:id : get the "id" custSchedule.
     *
     * @param id the id of the custScheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the custScheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cust-schedules/{id}")
    public ResponseEntity<CustScheduleDTO> getCustSchedule(@PathVariable Long id) {
        log.debug("REST request to get CustSchedule : {}", id);
        Optional<CustScheduleDTO> custScheduleDTO = custScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custScheduleDTO);
    }

    /**
     * DELETE  /cust-schedules/:id : delete the "id" custSchedule.
     *
     * @param id the id of the custScheduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cust-schedules/{id}")
    public ResponseEntity<Void> deleteCustSchedule(@PathVariable Long id) {
        log.debug("REST request to delete CustSchedule : {}", id);
        custScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
