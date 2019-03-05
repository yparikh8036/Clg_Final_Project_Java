package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.TaxationCategoryService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.TaxationCategoryDTO;
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
 * REST controller for managing TaxationCategory.
 */
@RestController
@RequestMapping("/api")
public class TaxationCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TaxationCategoryResource.class);

    private static final String ENTITY_NAME = "taxationCategory";

    private final TaxationCategoryService taxationCategoryService;

    public TaxationCategoryResource(TaxationCategoryService taxationCategoryService) {
        this.taxationCategoryService = taxationCategoryService;
    }

    /**
     * POST  /taxation-categories : Create a new taxationCategory.
     *
     * @param taxationCategoryDTO the taxationCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxationCategoryDTO, or with status 400 (Bad Request) if the taxationCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taxation-categories")
    public ResponseEntity<TaxationCategoryDTO> createTaxationCategory(@RequestBody TaxationCategoryDTO taxationCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save TaxationCategory : {}", taxationCategoryDTO);
        if (taxationCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxationCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxationCategoryDTO result = taxationCategoryService.save(taxationCategoryDTO);
        return ResponseEntity.created(new URI("/api/taxation-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxation-categories : Updates an existing taxationCategory.
     *
     * @param taxationCategoryDTO the taxationCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxationCategoryDTO,
     * or with status 400 (Bad Request) if the taxationCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the taxationCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taxation-categories")
    public ResponseEntity<TaxationCategoryDTO> updateTaxationCategory(@RequestBody TaxationCategoryDTO taxationCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update TaxationCategory : {}", taxationCategoryDTO);
        if (taxationCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxationCategoryDTO result = taxationCategoryService.save(taxationCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taxationCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxation-categories : get all the taxationCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taxationCategories in body
     */
    @GetMapping("/taxation-categories")
    public List<TaxationCategoryDTO> getAllTaxationCategories() {
        log.debug("REST request to get all TaxationCategories");
        return taxationCategoryService.findAll();
    }

    /**
     * GET  /taxation-categories/:id : get the "id" taxationCategory.
     *
     * @param id the id of the taxationCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxationCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/taxation-categories/{id}")
    public ResponseEntity<TaxationCategoryDTO> getTaxationCategory(@PathVariable Long id) {
        log.debug("REST request to get TaxationCategory : {}", id);
        Optional<TaxationCategoryDTO> taxationCategoryDTO = taxationCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxationCategoryDTO);
    }

    /**
     * DELETE  /taxation-categories/:id : delete the "id" taxationCategory.
     *
     * @param id the id of the taxationCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taxation-categories/{id}")
    public ResponseEntity<Void> deleteTaxationCategory(@PathVariable Long id) {
        log.debug("REST request to delete TaxationCategory : {}", id);
        taxationCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
