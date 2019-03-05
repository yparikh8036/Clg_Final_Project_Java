package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.TaxationUpdateService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.TaxationUpdateDTO;
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
 * REST controller for managing TaxationUpdate.
 */
@RestController
@RequestMapping("/api")
public class TaxationUpdateResource {

    private final Logger log = LoggerFactory.getLogger(TaxationUpdateResource.class);

    private static final String ENTITY_NAME = "taxationUpdate";

    private final TaxationUpdateService taxationUpdateService;

    public TaxationUpdateResource(TaxationUpdateService taxationUpdateService) {
        this.taxationUpdateService = taxationUpdateService;
    }

    /**
     * POST  /taxation-updates : Create a new taxationUpdate.
     *
     * @param taxationUpdateDTO the taxationUpdateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxationUpdateDTO, or with status 400 (Bad Request) if the taxationUpdate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taxation-updates")
    public ResponseEntity<TaxationUpdateDTO> createTaxationUpdate(@RequestBody TaxationUpdateDTO taxationUpdateDTO) throws URISyntaxException {
        log.debug("REST request to save TaxationUpdate : {}", taxationUpdateDTO);
        if (taxationUpdateDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxationUpdate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxationUpdateDTO result = taxationUpdateService.save(taxationUpdateDTO);
        return ResponseEntity.created(new URI("/api/taxation-updates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxation-updates : Updates an existing taxationUpdate.
     *
     * @param taxationUpdateDTO the taxationUpdateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxationUpdateDTO,
     * or with status 400 (Bad Request) if the taxationUpdateDTO is not valid,
     * or with status 500 (Internal Server Error) if the taxationUpdateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taxation-updates")
    public ResponseEntity<TaxationUpdateDTO> updateTaxationUpdate(@RequestBody TaxationUpdateDTO taxationUpdateDTO) throws URISyntaxException {
        log.debug("REST request to update TaxationUpdate : {}", taxationUpdateDTO);
        if (taxationUpdateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxationUpdateDTO result = taxationUpdateService.save(taxationUpdateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taxationUpdateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxation-updates : get all the taxationUpdates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taxationUpdates in body
     */
    @GetMapping("/taxation-updates")
    public List<TaxationUpdateDTO> getAllTaxationUpdates() {
        log.debug("REST request to get all TaxationUpdates");
        return taxationUpdateService.findAll();
    }

    /**
     * GET  /taxation-updates/:id : get the "id" taxationUpdate.
     *
     * @param id the id of the taxationUpdateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxationUpdateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/taxation-updates/{id}")
    public ResponseEntity<TaxationUpdateDTO> getTaxationUpdate(@PathVariable Long id) {
        log.debug("REST request to get TaxationUpdate : {}", id);
        Optional<TaxationUpdateDTO> taxationUpdateDTO = taxationUpdateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxationUpdateDTO);
    }

    /**
     * DELETE  /taxation-updates/:id : delete the "id" taxationUpdate.
     *
     * @param id the id of the taxationUpdateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taxation-updates/{id}")
    public ResponseEntity<Void> deleteTaxationUpdate(@PathVariable Long id) {
        log.debug("REST request to delete TaxationUpdate : {}", id);
        taxationUpdateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
