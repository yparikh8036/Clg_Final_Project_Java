package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.CustDocService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.CustDocDTO;
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
 * REST controller for managing CustDoc.
 */
@RestController
@RequestMapping("/api")
public class CustDocResource {

    private final Logger log = LoggerFactory.getLogger(CustDocResource.class);

    private static final String ENTITY_NAME = "custDoc";

    private final CustDocService custDocService;

    public CustDocResource(CustDocService custDocService) {
        this.custDocService = custDocService;
    }

    /**
     * POST  /cust-docs : Create a new custDoc.
     *
     * @param custDocDTO the custDocDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new custDocDTO, or with status 400 (Bad Request) if the custDoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cust-docs")
    public ResponseEntity<CustDocDTO> createCustDoc(@RequestBody CustDocDTO custDocDTO) throws URISyntaxException {
        log.debug("REST request to save CustDoc : {}", custDocDTO);
        if (custDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new custDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustDocDTO result = custDocService.save(custDocDTO);
        return ResponseEntity.created(new URI("/api/cust-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cust-docs : Updates an existing custDoc.
     *
     * @param custDocDTO the custDocDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated custDocDTO,
     * or with status 400 (Bad Request) if the custDocDTO is not valid,
     * or with status 500 (Internal Server Error) if the custDocDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cust-docs")
    public ResponseEntity<CustDocDTO> updateCustDoc(@RequestBody CustDocDTO custDocDTO) throws URISyntaxException {
        log.debug("REST request to update CustDoc : {}", custDocDTO);
        if (custDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustDocDTO result = custDocService.save(custDocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, custDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cust-docs : get all the custDocs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of custDocs in body
     */
    @GetMapping("/cust-docs")
    public List<CustDocDTO> getAllCustDocs() {
        log.debug("REST request to get all CustDocs");
        return custDocService.findAll();
    }

    /**
     * GET  /cust-docs/:id : get the "id" custDoc.
     *
     * @param id the id of the custDocDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the custDocDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cust-docs/{id}")
    public ResponseEntity<CustDocDTO> getCustDoc(@PathVariable Long id) {
        log.debug("REST request to get CustDoc : {}", id);
        Optional<CustDocDTO> custDocDTO = custDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custDocDTO);
    }

    /**
     * DELETE  /cust-docs/:id : delete the "id" custDoc.
     *
     * @param id the id of the custDocDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cust-docs/{id}")
    public ResponseEntity<Void> deleteCustDoc(@PathVariable Long id) {
        log.debug("REST request to delete CustDoc : {}", id);
        custDocService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
