package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.InternalDocService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.InternalDocDTO;
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
 * REST controller for managing InternalDoc.
 */
@RestController
@RequestMapping("/api")
public class InternalDocResource {

    private final Logger log = LoggerFactory.getLogger(InternalDocResource.class);

    private static final String ENTITY_NAME = "internalDoc";

    private final InternalDocService internalDocService;

    public InternalDocResource(InternalDocService internalDocService) {
        this.internalDocService = internalDocService;
    }

    /**
     * POST  /internal-docs : Create a new internalDoc.
     *
     * @param internalDocDTO the internalDocDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new internalDocDTO, or with status 400 (Bad Request) if the internalDoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/internal-docs")
    public ResponseEntity<InternalDocDTO> createInternalDoc(@RequestBody InternalDocDTO internalDocDTO) throws URISyntaxException {
        log.debug("REST request to save InternalDoc : {}", internalDocDTO);
        if (internalDocDTO.getId() != null) {
            throw new BadRequestAlertException("A new internalDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InternalDocDTO result = internalDocService.save(internalDocDTO);
        return ResponseEntity.created(new URI("/api/internal-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /internal-docs : Updates an existing internalDoc.
     *
     * @param internalDocDTO the internalDocDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated internalDocDTO,
     * or with status 400 (Bad Request) if the internalDocDTO is not valid,
     * or with status 500 (Internal Server Error) if the internalDocDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/internal-docs")
    public ResponseEntity<InternalDocDTO> updateInternalDoc(@RequestBody InternalDocDTO internalDocDTO) throws URISyntaxException {
        log.debug("REST request to update InternalDoc : {}", internalDocDTO);
        if (internalDocDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternalDocDTO result = internalDocService.save(internalDocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, internalDocDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /internal-docs : get all the internalDocs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of internalDocs in body
     */
    @GetMapping("/internal-docs")
    public List<InternalDocDTO> getAllInternalDocs() {
        log.debug("REST request to get all InternalDocs");
        return internalDocService.findAll();
    }

    /**
     * GET  /internal-docs/:id : get the "id" internalDoc.
     *
     * @param id the id of the internalDocDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the internalDocDTO, or with status 404 (Not Found)
     */
    @GetMapping("/internal-docs/{id}")
    public ResponseEntity<InternalDocDTO> getInternalDoc(@PathVariable Long id) {
        log.debug("REST request to get InternalDoc : {}", id);
        Optional<InternalDocDTO> internalDocDTO = internalDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internalDocDTO);
    }

    /**
     * DELETE  /internal-docs/:id : delete the "id" internalDoc.
     *
     * @param id the id of the internalDocDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/internal-docs/{id}")
    public ResponseEntity<Void> deleteInternalDoc(@PathVariable Long id) {
        log.debug("REST request to delete InternalDoc : {}", id);
        internalDocService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
