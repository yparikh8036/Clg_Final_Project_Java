package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.WorkService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.WorkDTO;
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
 * REST controller for managing Work.
 */
@RestController
@RequestMapping("/api")
public class WorkResource {

    private final Logger log = LoggerFactory.getLogger(WorkResource.class);

    private static final String ENTITY_NAME = "work";

    private final WorkService workService;

    public WorkResource(WorkService workService) {
        this.workService = workService;
    }

    /**
     * POST  /works : Create a new work.
     *
     * @param workDTO the workDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workDTO, or with status 400 (Bad Request) if the work has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/works")
    public ResponseEntity<WorkDTO> createWork(@RequestBody WorkDTO workDTO) throws URISyntaxException {
        log.debug("REST request to save Work : {}", workDTO);
        if (workDTO.getId() != null) {
            throw new BadRequestAlertException("A new work cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkDTO result = workService.save(workDTO);
        return ResponseEntity.created(new URI("/api/works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /works : Updates an existing work.
     *
     * @param workDTO the workDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workDTO,
     * or with status 400 (Bad Request) if the workDTO is not valid,
     * or with status 500 (Internal Server Error) if the workDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/works")
    public ResponseEntity<WorkDTO> updateWork(@RequestBody WorkDTO workDTO) throws URISyntaxException {
        log.debug("REST request to update Work : {}", workDTO);
        if (workDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkDTO result = workService.save(workDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /works : get all the works.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of works in body
     */
    @GetMapping("/works")
    public List<WorkDTO> getAllWorks() {
        log.debug("REST request to get all Works");
        return workService.findAll();
    }

    /**
     * GET  /works/:id : get the "id" work.
     *
     * @param id the id of the workDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workDTO, or with status 404 (Not Found)
     */
    @GetMapping("/works/{id}")
    public ResponseEntity<WorkDTO> getWork(@PathVariable Long id) {
        log.debug("REST request to get Work : {}", id);
        Optional<WorkDTO> workDTO = workService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workDTO);
    }

    /**
     * DELETE  /works/:id : delete the "id" work.
     *
     * @param id the id of the workDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/works/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        log.debug("REST request to delete Work : {}", id);
        workService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
