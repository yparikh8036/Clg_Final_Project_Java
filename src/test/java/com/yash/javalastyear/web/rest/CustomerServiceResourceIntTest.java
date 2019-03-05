package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.CustomerService;
import com.yash.javalastyear.repository.CustomerServiceRepository;
import com.yash.javalastyear.service.CustomerServiceService;
import com.yash.javalastyear.service.dto.CustomerServiceDTO;
import com.yash.javalastyear.service.mapper.CustomerServiceMapper;
import com.yash.javalastyear.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.yash.javalastyear.web.rest.TestUtil.sameInstant;
import static com.yash.javalastyear.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerServiceResource REST controller.
 *
 * @see CustomerServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class CustomerServiceResourceIntTest {

    private static final Long DEFAULT_CUSTOMER_SERVICE_ID = 1L;
    private static final Long UPDATED_CUSTOMER_SERVICE_ID = 2L;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CustomerServiceRepository customerServiceRepository;

    @Autowired
    private CustomerServiceMapper customerServiceMapper;

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCustomerServiceMockMvc;

    private CustomerService customerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerServiceResource customerServiceResource = new CustomerServiceResource(customerServiceService);
        this.restCustomerServiceMockMvc = MockMvcBuilders.standaloneSetup(customerServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerService createEntity(EntityManager em) {
        CustomerService customerService = new CustomerService()
            .customerServiceID(DEFAULT_CUSTOMER_SERVICE_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return customerService;
    }

    @Before
    public void initTest() {
        customerService = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerService() throws Exception {
        int databaseSizeBeforeCreate = customerServiceRepository.findAll().size();

        // Create the CustomerService
        CustomerServiceDTO customerServiceDTO = customerServiceMapper.toDto(customerService);
        restCustomerServiceMockMvc.perform(post("/api/customer-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerService in the database
        List<CustomerService> customerServiceList = customerServiceRepository.findAll();
        assertThat(customerServiceList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerService testCustomerService = customerServiceList.get(customerServiceList.size() - 1);
        assertThat(testCustomerService.getCustomerServiceID()).isEqualTo(DEFAULT_CUSTOMER_SERVICE_ID);
        assertThat(testCustomerService.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCustomerService.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createCustomerServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerServiceRepository.findAll().size();

        // Create the CustomerService with an existing ID
        customerService.setId(1L);
        CustomerServiceDTO customerServiceDTO = customerServiceMapper.toDto(customerService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerServiceMockMvc.perform(post("/api/customer-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerService in the database
        List<CustomerService> customerServiceList = customerServiceRepository.findAll();
        assertThat(customerServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerServices() throws Exception {
        // Initialize the database
        customerServiceRepository.saveAndFlush(customerService);

        // Get all the customerServiceList
        restCustomerServiceMockMvc.perform(get("/api/customer-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerService.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerServiceID").value(hasItem(DEFAULT_CUSTOMER_SERVICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }
    
    @Test
    @Transactional
    public void getCustomerService() throws Exception {
        // Initialize the database
        customerServiceRepository.saveAndFlush(customerService);

        // Get the customerService
        restCustomerServiceMockMvc.perform(get("/api/customer-services/{id}", customerService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerService.getId().intValue()))
            .andExpect(jsonPath("$.customerServiceID").value(DEFAULT_CUSTOMER_SERVICE_ID.intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerService() throws Exception {
        // Get the customerService
        restCustomerServiceMockMvc.perform(get("/api/customer-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerService() throws Exception {
        // Initialize the database
        customerServiceRepository.saveAndFlush(customerService);

        int databaseSizeBeforeUpdate = customerServiceRepository.findAll().size();

        // Update the customerService
        CustomerService updatedCustomerService = customerServiceRepository.findById(customerService.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerService are not directly saved in db
        em.detach(updatedCustomerService);
        updatedCustomerService
            .customerServiceID(UPDATED_CUSTOMER_SERVICE_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        CustomerServiceDTO customerServiceDTO = customerServiceMapper.toDto(updatedCustomerService);

        restCustomerServiceMockMvc.perform(put("/api/customer-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerServiceDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerService in the database
        List<CustomerService> customerServiceList = customerServiceRepository.findAll();
        assertThat(customerServiceList).hasSize(databaseSizeBeforeUpdate);
        CustomerService testCustomerService = customerServiceList.get(customerServiceList.size() - 1);
        assertThat(testCustomerService.getCustomerServiceID()).isEqualTo(UPDATED_CUSTOMER_SERVICE_ID);
        assertThat(testCustomerService.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCustomerService.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerService() throws Exception {
        int databaseSizeBeforeUpdate = customerServiceRepository.findAll().size();

        // Create the CustomerService
        CustomerServiceDTO customerServiceDTO = customerServiceMapper.toDto(customerService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerServiceMockMvc.perform(put("/api/customer-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerService in the database
        List<CustomerService> customerServiceList = customerServiceRepository.findAll();
        assertThat(customerServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerService() throws Exception {
        // Initialize the database
        customerServiceRepository.saveAndFlush(customerService);

        int databaseSizeBeforeDelete = customerServiceRepository.findAll().size();

        // Delete the customerService
        restCustomerServiceMockMvc.perform(delete("/api/customer-services/{id}", customerService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerService> customerServiceList = customerServiceRepository.findAll();
        assertThat(customerServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerService.class);
        CustomerService customerService1 = new CustomerService();
        customerService1.setId(1L);
        CustomerService customerService2 = new CustomerService();
        customerService2.setId(customerService1.getId());
        assertThat(customerService1).isEqualTo(customerService2);
        customerService2.setId(2L);
        assertThat(customerService1).isNotEqualTo(customerService2);
        customerService1.setId(null);
        assertThat(customerService1).isNotEqualTo(customerService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerServiceDTO.class);
        CustomerServiceDTO customerServiceDTO1 = new CustomerServiceDTO();
        customerServiceDTO1.setId(1L);
        CustomerServiceDTO customerServiceDTO2 = new CustomerServiceDTO();
        assertThat(customerServiceDTO1).isNotEqualTo(customerServiceDTO2);
        customerServiceDTO2.setId(customerServiceDTO1.getId());
        assertThat(customerServiceDTO1).isEqualTo(customerServiceDTO2);
        customerServiceDTO2.setId(2L);
        assertThat(customerServiceDTO1).isNotEqualTo(customerServiceDTO2);
        customerServiceDTO1.setId(null);
        assertThat(customerServiceDTO1).isNotEqualTo(customerServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerServiceMapper.fromId(null)).isNull();
    }
}
