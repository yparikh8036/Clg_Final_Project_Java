package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.CustContactService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.CustContactDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing CustContact.
 */
@RestController
@RequestMapping("/api")
public class CustContactResource {

    private final Logger log = LoggerFactory.getLogger(CustContactResource.class);

    private static final String ENTITY_NAME = "custContact";

    private final CustContactService custContactService;

    public CustContactResource(CustContactService custContactService) {
        this.custContactService = custContactService;
    }

    /**
     * POST  /cust-contacts : Create a new custContact.
     *
     * @param custContactDTO the custContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new custContactDTO, or with status 400 (Bad Request) if the custContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cust-contacts")
    public ResponseEntity<CustContactDTO> createCustContact(@RequestBody CustContactDTO custContactDTO) throws URISyntaxException {
        log.debug("REST request to save CustContact : {}", custContactDTO);
        if (custContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new custContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustContactDTO result = custContactService.save(custContactDTO);
        return ResponseEntity.created(new URI("/api/cust-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cust-contacts : Updates an existing custContact.
     *
     * @param custContactDTO the custContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated custContactDTO,
     * or with status 400 (Bad Request) if the custContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the custContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cust-contacts")
    public ResponseEntity<CustContactDTO> updateCustContact(@RequestBody CustContactDTO custContactDTO) throws URISyntaxException {
        log.debug("REST request to update CustContact : {}", custContactDTO);
        if (custContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustContactDTO result = custContactService.save(custContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, custContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cust-contacts : get all the custContacts.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of custContacts in body
     */
    @GetMapping("/cust-contacts")
    public List<CustContactDTO> getAllCustContacts(@RequestParam(required = false) String filter) {
        if ("customer-is-null".equals(filter)) {
            log.debug("REST request to get all CustContacts where customer is null");
            return custContactService.findAllWhereCustomerIsNull();
        }
        log.debug("REST request to get all CustContacts");
        return custContactService.findAll();
    }

    /**
     * GET  /cust-contacts/:id : get the "id" custContact.
     *
     * @param id the id of the custContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the custContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cust-contacts/{id}")
    public ResponseEntity<CustContactDTO> getCustContact(@PathVariable Long id) {
        log.debug("REST request to get CustContact : {}", id);
        Optional<CustContactDTO> custContactDTO = custContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custContactDTO);
    }

    /**
     * DELETE  /cust-contacts/:id : delete the "id" custContact.
     *
     * @param id the id of the custContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cust-contacts/{id}")
    public ResponseEntity<Void> deleteCustContact(@PathVariable Long id) {
        log.debug("REST request to delete CustContact : {}", id);
        custContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
