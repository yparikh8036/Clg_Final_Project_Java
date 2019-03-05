package com.yash.javalastyear.web.rest;
import com.yash.javalastyear.service.CustomerServiceService;
import com.yash.javalastyear.web.rest.errors.BadRequestAlertException;
import com.yash.javalastyear.web.rest.util.HeaderUtil;
import com.yash.javalastyear.service.dto.CustomerServiceDTO;
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
 * REST controller for managing CustomerService.
 */
@RestController
@RequestMapping("/api")
public class CustomerServiceResource {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceResource.class);

    private static final String ENTITY_NAME = "customerService";

    private final CustomerServiceService customerServiceService;

    public CustomerServiceResource(CustomerServiceService customerServiceService) {
        this.customerServiceService = customerServiceService;
    }

    /**
     * POST  /customer-services : Create a new customerService.
     *
     * @param customerServiceDTO the customerServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerServiceDTO, or with status 400 (Bad Request) if the customerService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-services")
    public ResponseEntity<CustomerServiceDTO> createCustomerService(@RequestBody CustomerServiceDTO customerServiceDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerService : {}", customerServiceDTO);
        if (customerServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerServiceDTO result = customerServiceService.save(customerServiceDTO);
        return ResponseEntity.created(new URI("/api/customer-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-services : Updates an existing customerService.
     *
     * @param customerServiceDTO the customerServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerServiceDTO,
     * or with status 400 (Bad Request) if the customerServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-services")
    public ResponseEntity<CustomerServiceDTO> updateCustomerService(@RequestBody CustomerServiceDTO customerServiceDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerService : {}", customerServiceDTO);
        if (customerServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerServiceDTO result = customerServiceService.save(customerServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-services : get all the customerServices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerServices in body
     */
    @GetMapping("/customer-services")
    public List<CustomerServiceDTO> getAllCustomerServices() {
        log.debug("REST request to get all CustomerServices");
        return customerServiceService.findAll();
    }

    /**
     * GET  /customer-services/:id : get the "id" customerService.
     *
     * @param id the id of the customerServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-services/{id}")
    public ResponseEntity<CustomerServiceDTO> getCustomerService(@PathVariable Long id) {
        log.debug("REST request to get CustomerService : {}", id);
        Optional<CustomerServiceDTO> customerServiceDTO = customerServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerServiceDTO);
    }

    /**
     * DELETE  /customer-services/:id : delete the "id" customerService.
     *
     * @param id the id of the customerServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-services/{id}")
    public ResponseEntity<Void> deleteCustomerService(@PathVariable Long id) {
        log.debug("REST request to delete CustomerService : {}", id);
        customerServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
