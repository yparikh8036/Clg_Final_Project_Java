package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.CustAccDoc;
import com.yash.javalastyear.repository.CustAccDocRepository;
import com.yash.javalastyear.service.CustAccDocService;
import com.yash.javalastyear.service.dto.CustAccDocDTO;
import com.yash.javalastyear.service.mapper.CustAccDocMapper;
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
 * Test class for the CustAccDocResource REST controller.
 *
 * @see CustAccDocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class CustAccDocResourceIntTest {

    private static final Long DEFAULT_CUST_ACC_DOC_ID = 1L;
    private static final Long UPDATED_CUST_ACC_DOC_ID = 2L;

    private static final String DEFAULT_DOC_FILE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_FILE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DOC_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOC_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CustAccDocRepository custAccDocRepository;

    @Autowired
    private CustAccDocMapper custAccDocMapper;

    @Autowired
    private CustAccDocService custAccDocService;

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

    private MockMvc restCustAccDocMockMvc;

    private CustAccDoc custAccDoc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustAccDocResource custAccDocResource = new CustAccDocResource(custAccDocService);
        this.restCustAccDocMockMvc = MockMvcBuilders.standaloneSetup(custAccDocResource)
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
    public static CustAccDoc createEntity(EntityManager em) {
        CustAccDoc custAccDoc = new CustAccDoc()
            .custAccDocID(DEFAULT_CUST_ACC_DOC_ID)
            .docFile(DEFAULT_DOC_FILE)
            .docDate(DEFAULT_DOC_DATE);
        return custAccDoc;
    }

    @Before
    public void initTest() {
        custAccDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustAccDoc() throws Exception {
        int databaseSizeBeforeCreate = custAccDocRepository.findAll().size();

        // Create the CustAccDoc
        CustAccDocDTO custAccDocDTO = custAccDocMapper.toDto(custAccDoc);
        restCustAccDocMockMvc.perform(post("/api/cust-acc-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custAccDocDTO)))
            .andExpect(status().isCreated());

        // Validate the CustAccDoc in the database
        List<CustAccDoc> custAccDocList = custAccDocRepository.findAll();
        assertThat(custAccDocList).hasSize(databaseSizeBeforeCreate + 1);
        CustAccDoc testCustAccDoc = custAccDocList.get(custAccDocList.size() - 1);
        assertThat(testCustAccDoc.getCustAccDocID()).isEqualTo(DEFAULT_CUST_ACC_DOC_ID);
        assertThat(testCustAccDoc.getDocFile()).isEqualTo(DEFAULT_DOC_FILE);
        assertThat(testCustAccDoc.getDocDate()).isEqualTo(DEFAULT_DOC_DATE);
    }

    @Test
    @Transactional
    public void createCustAccDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custAccDocRepository.findAll().size();

        // Create the CustAccDoc with an existing ID
        custAccDoc.setId(1L);
        CustAccDocDTO custAccDocDTO = custAccDocMapper.toDto(custAccDoc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustAccDocMockMvc.perform(post("/api/cust-acc-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custAccDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustAccDoc in the database
        List<CustAccDoc> custAccDocList = custAccDocRepository.findAll();
        assertThat(custAccDocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustAccDocs() throws Exception {
        // Initialize the database
        custAccDocRepository.saveAndFlush(custAccDoc);

        // Get all the custAccDocList
        restCustAccDocMockMvc.perform(get("/api/cust-acc-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custAccDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].custAccDocID").value(hasItem(DEFAULT_CUST_ACC_DOC_ID.intValue())))
            .andExpect(jsonPath("$.[*].docFile").value(hasItem(DEFAULT_DOC_FILE.toString())))
            .andExpect(jsonPath("$.[*].docDate").value(hasItem(sameInstant(DEFAULT_DOC_DATE))));
    }
    
    @Test
    @Transactional
    public void getCustAccDoc() throws Exception {
        // Initialize the database
        custAccDocRepository.saveAndFlush(custAccDoc);

        // Get the custAccDoc
        restCustAccDocMockMvc.perform(get("/api/cust-acc-docs/{id}", custAccDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(custAccDoc.getId().intValue()))
            .andExpect(jsonPath("$.custAccDocID").value(DEFAULT_CUST_ACC_DOC_ID.intValue()))
            .andExpect(jsonPath("$.docFile").value(DEFAULT_DOC_FILE.toString()))
            .andExpect(jsonPath("$.docDate").value(sameInstant(DEFAULT_DOC_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingCustAccDoc() throws Exception {
        // Get the custAccDoc
        restCustAccDocMockMvc.perform(get("/api/cust-acc-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustAccDoc() throws Exception {
        // Initialize the database
        custAccDocRepository.saveAndFlush(custAccDoc);

        int databaseSizeBeforeUpdate = custAccDocRepository.findAll().size();

        // Update the custAccDoc
        CustAccDoc updatedCustAccDoc = custAccDocRepository.findById(custAccDoc.getId()).get();
        // Disconnect from session so that the updates on updatedCustAccDoc are not directly saved in db
        em.detach(updatedCustAccDoc);
        updatedCustAccDoc
            .custAccDocID(UPDATED_CUST_ACC_DOC_ID)
            .docFile(UPDATED_DOC_FILE)
            .docDate(UPDATED_DOC_DATE);
        CustAccDocDTO custAccDocDTO = custAccDocMapper.toDto(updatedCustAccDoc);

        restCustAccDocMockMvc.perform(put("/api/cust-acc-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custAccDocDTO)))
            .andExpect(status().isOk());

        // Validate the CustAccDoc in the database
        List<CustAccDoc> custAccDocList = custAccDocRepository.findAll();
        assertThat(custAccDocList).hasSize(databaseSizeBeforeUpdate);
        CustAccDoc testCustAccDoc = custAccDocList.get(custAccDocList.size() - 1);
        assertThat(testCustAccDoc.getCustAccDocID()).isEqualTo(UPDATED_CUST_ACC_DOC_ID);
        assertThat(testCustAccDoc.getDocFile()).isEqualTo(UPDATED_DOC_FILE);
        assertThat(testCustAccDoc.getDocDate()).isEqualTo(UPDATED_DOC_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustAccDoc() throws Exception {
        int databaseSizeBeforeUpdate = custAccDocRepository.findAll().size();

        // Create the CustAccDoc
        CustAccDocDTO custAccDocDTO = custAccDocMapper.toDto(custAccDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustAccDocMockMvc.perform(put("/api/cust-acc-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custAccDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustAccDoc in the database
        List<CustAccDoc> custAccDocList = custAccDocRepository.findAll();
        assertThat(custAccDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustAccDoc() throws Exception {
        // Initialize the database
        custAccDocRepository.saveAndFlush(custAccDoc);

        int databaseSizeBeforeDelete = custAccDocRepository.findAll().size();

        // Delete the custAccDoc
        restCustAccDocMockMvc.perform(delete("/api/cust-acc-docs/{id}", custAccDoc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustAccDoc> custAccDocList = custAccDocRepository.findAll();
        assertThat(custAccDocList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustAccDoc.class);
        CustAccDoc custAccDoc1 = new CustAccDoc();
        custAccDoc1.setId(1L);
        CustAccDoc custAccDoc2 = new CustAccDoc();
        custAccDoc2.setId(custAccDoc1.getId());
        assertThat(custAccDoc1).isEqualTo(custAccDoc2);
        custAccDoc2.setId(2L);
        assertThat(custAccDoc1).isNotEqualTo(custAccDoc2);
        custAccDoc1.setId(null);
        assertThat(custAccDoc1).isNotEqualTo(custAccDoc2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustAccDocDTO.class);
        CustAccDocDTO custAccDocDTO1 = new CustAccDocDTO();
        custAccDocDTO1.setId(1L);
        CustAccDocDTO custAccDocDTO2 = new CustAccDocDTO();
        assertThat(custAccDocDTO1).isNotEqualTo(custAccDocDTO2);
        custAccDocDTO2.setId(custAccDocDTO1.getId());
        assertThat(custAccDocDTO1).isEqualTo(custAccDocDTO2);
        custAccDocDTO2.setId(2L);
        assertThat(custAccDocDTO1).isNotEqualTo(custAccDocDTO2);
        custAccDocDTO1.setId(null);
        assertThat(custAccDocDTO1).isNotEqualTo(custAccDocDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(custAccDocMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(custAccDocMapper.fromId(null)).isNull();
    }
}
