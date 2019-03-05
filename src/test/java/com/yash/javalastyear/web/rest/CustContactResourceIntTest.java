package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.CustContact;
import com.yash.javalastyear.repository.CustContactRepository;
import com.yash.javalastyear.service.CustContactService;
import com.yash.javalastyear.service.dto.CustContactDTO;
import com.yash.javalastyear.service.mapper.CustContactMapper;
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
import java.util.List;


import static com.yash.javalastyear.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustContactResource REST controller.
 *
 * @see CustContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class CustContactResourceIntTest {

    private static final Long DEFAULT_CUST_CONTACT_ID = 1L;
    private static final Long UPDATED_CUST_CONTACT_ID = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CustContactRepository custContactRepository;

    @Autowired
    private CustContactMapper custContactMapper;

    @Autowired
    private CustContactService custContactService;

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

    private MockMvc restCustContactMockMvc;

    private CustContact custContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustContactResource custContactResource = new CustContactResource(custContactService);
        this.restCustContactMockMvc = MockMvcBuilders.standaloneSetup(custContactResource)
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
    public static CustContact createEntity(EntityManager em) {
        CustContact custContact = new CustContact()
            .custContactID(DEFAULT_CUST_CONTACT_ID)
            .email(DEFAULT_EMAIL)
            .mobile(DEFAULT_MOBILE)
            .purpose(DEFAULT_PURPOSE)
            .name(DEFAULT_NAME);
        return custContact;
    }

    @Before
    public void initTest() {
        custContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustContact() throws Exception {
        int databaseSizeBeforeCreate = custContactRepository.findAll().size();

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);
        restCustContactMockMvc.perform(post("/api/cust-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeCreate + 1);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getCustContactID()).isEqualTo(DEFAULT_CUST_CONTACT_ID);
        assertThat(testCustContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustContact.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCustContact.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testCustContact.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCustContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custContactRepository.findAll().size();

        // Create the CustContact with an existing ID
        custContact.setId(1L);
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustContactMockMvc.perform(post("/api/cust-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustContacts() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList
        restCustContactMockMvc.perform(get("/api/cust-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].custContactID").value(hasItem(DEFAULT_CUST_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get the custContact
        restCustContactMockMvc.perform(get("/api/cust-contacts/{id}", custContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(custContact.getId().intValue()))
            .andExpect(jsonPath("$.custContactID").value(DEFAULT_CUST_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustContact() throws Exception {
        // Get the custContact
        restCustContactMockMvc.perform(get("/api/cust-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact
        CustContact updatedCustContact = custContactRepository.findById(custContact.getId()).get();
        // Disconnect from session so that the updates on updatedCustContact are not directly saved in db
        em.detach(updatedCustContact);
        updatedCustContact
            .custContactID(UPDATED_CUST_CONTACT_ID)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .purpose(UPDATED_PURPOSE)
            .name(UPDATED_NAME);
        CustContactDTO custContactDTO = custContactMapper.toDto(updatedCustContact);

        restCustContactMockMvc.perform(put("/api/cust-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custContactDTO)))
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getCustContactID()).isEqualTo(UPDATED_CUST_CONTACT_ID);
        assertThat(testCustContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustContact.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustContact.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactMockMvc.perform(put("/api/cust-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeDelete = custContactRepository.findAll().size();

        // Delete the custContact
        restCustContactMockMvc.perform(delete("/api/cust-contacts/{id}", custContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustContact.class);
        CustContact custContact1 = new CustContact();
        custContact1.setId(1L);
        CustContact custContact2 = new CustContact();
        custContact2.setId(custContact1.getId());
        assertThat(custContact1).isEqualTo(custContact2);
        custContact2.setId(2L);
        assertThat(custContact1).isNotEqualTo(custContact2);
        custContact1.setId(null);
        assertThat(custContact1).isNotEqualTo(custContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustContactDTO.class);
        CustContactDTO custContactDTO1 = new CustContactDTO();
        custContactDTO1.setId(1L);
        CustContactDTO custContactDTO2 = new CustContactDTO();
        assertThat(custContactDTO1).isNotEqualTo(custContactDTO2);
        custContactDTO2.setId(custContactDTO1.getId());
        assertThat(custContactDTO1).isEqualTo(custContactDTO2);
        custContactDTO2.setId(2L);
        assertThat(custContactDTO1).isNotEqualTo(custContactDTO2);
        custContactDTO1.setId(null);
        assertThat(custContactDTO1).isNotEqualTo(custContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(custContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(custContactMapper.fromId(null)).isNull();
    }
}
