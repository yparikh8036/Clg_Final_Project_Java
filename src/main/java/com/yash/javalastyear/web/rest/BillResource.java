package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.BillService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.BillDTO;
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
 * REST controller for managing Bill.
 */
@RestController
@RequestMapping("/api")
public class BillResource {

    private final Logger log = LoggerFactory.getLogger(BillResource.class);

    private static final String ENTITY_NAME = "bill";

    private final BillService billService;

    public BillResource(BillService billService) {
        this.billService = billService;
    }

    /**
     * POST  /bills : Create a new bill.
     *
     * @param billDTO the billDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billDTO, or with status 400 (Bad Request) if the bill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bills")
    public ResponseEntity<BillDTO> createBill(@RequestBody BillDTO billDTO) throws URISyntaxException {
        log.debug("REST request to save Bill : {}", billDTO);
        if (billDTO.getId() != null) {
            throw new BadRequestAlertException("A new bill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillDTO result = billService.save(billDTO);
        return ResponseEntity.created(new URI("/api/bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bills : Updates an existing bill.
     *
     * @param billDTO the billDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billDTO,
     * or with status 400 (Bad Request) if the billDTO is not valid,
     * or with status 500 (Internal Server Error) if the billDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bills")
    public ResponseEntity<BillDTO> updateBill(@RequestBody BillDTO billDTO) throws URISyntaxException {
        log.debug("REST request to update Bill : {}", billDTO);
        if (billDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillDTO result = billService.save(billDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bills : get all the bills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bills in body
     */
    @GetMapping("/bills")
    public List<BillDTO> getAllBills() {
        log.debug("REST request to get all Bills");
        return billService.findAll();
    }

    /**
     * GET  /bills/:id : get the "id" bill.
     *
     * @param id the id of the billDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bills/{id}")
    public ResponseEntity<BillDTO> getBill(@PathVariable Long id) {
        log.debug("REST request to get Bill : {}", id);
        Optional<BillDTO> billDTO = billService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billDTO);
    }

    /**
     * DELETE  /bills/:id : delete the "id" bill.
     *
     * @param id the id of the billDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bills/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        log.debug("REST request to delete Bill : {}", id);
        billService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
