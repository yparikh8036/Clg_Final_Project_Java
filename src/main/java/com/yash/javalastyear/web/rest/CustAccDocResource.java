package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.CustAccDocService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.CustAccDocDTO;
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
 * REST controller for managing CustAccDoc.
 */
@RestController
@RequestMapping("/api")
public class CustAccDocResource {

    private final Logger log = LoggerFactory.getLogger(CustAccDocResource.class);

    private static final String ENTITY_NAME = "custAccDoc";

    private final CustAccDocService custAccDocService;

    public CustAccDocResource(CustAccDocService custAccDocService) {
        this.custAccDocService = custAccDocService;
    }

    /**
     * POST  /cust-acc-docs : Create a new custAccDoc.
     *
     * @param custAccDocDTO the custAccDocDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new custAccDocDTO, or with status 400 (Bad Request) if the custAccDoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cust-acc-docs")
    public ResponseEntity<CustAccDocDTO> createCustAccDoc(@RequestBody CustAccDocDTO custAccDocDTO) throws URISyntaxException {
        log.debug("REST request to save CustAccDoc : {}", custAccDocDTO);
        if (custAccDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new custAccDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustAccDocDTO result = custAccDocService.save(custAccDocDTO);
        return ResponseEntity.created(new URI("/api/cust-acc-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cust-acc-docs : Updates an existing custAccDoc.
     *
     * @param custAccDocDTO the custAccDocDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated custAccDocDTO,
     * or with status 400 (Bad Request) if the custAccDocDTO is not valid,
     * or with status 500 (Internal Server Error) if the custAccDocDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cust-acc-docs")
    public ResponseEntity<CustAccDocDTO> updateCustAccDoc(@RequestBody CustAccDocDTO custAccDocDTO) throws URISyntaxException {
        log.debug("REST request to update CustAccDoc : {}", custAccDocDTO);
        if (custAccDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustAccDocDTO result = custAccDocService.save(custAccDocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, custAccDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cust-acc-docs : get all the custAccDocs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of custAccDocs in body
     */
    @GetMapping("/cust-acc-docs")
    public List<CustAccDocDTO> getAllCustAccDocs() {
        log.debug("REST request to get all CustAccDocs");
        return custAccDocService.findAll();
    }

    /**
     * GET  /cust-acc-docs/:id : get the "id" custAccDoc.
     *
     * @param id the id of the custAccDocDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the custAccDocDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cust-acc-docs/{id}")
    public ResponseEntity<CustAccDocDTO> getCustAccDoc(@PathVariable Long id) {
        log.debug("REST request to get CustAccDoc : {}", id);
        Optional<CustAccDocDTO> custAccDocDTO = custAccDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custAccDocDTO);
    }

    /**
     * DELETE  /cust-acc-docs/:id : delete the "id" custAccDoc.
     *
     * @param id the id of the custAccDocDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cust-acc-docs/{id}")
    public ResponseEntity<Void> deleteCustAccDoc(@PathVariable Long id) {
        log.debug("REST request to delete CustAccDoc : {}", id);
        custAccDocService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
