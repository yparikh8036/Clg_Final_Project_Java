package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.CustDoc;
import com.yash.javalastyear.repository.CustDocRepository;
import com.yash.javalastyear.service.CustDocService;
import com.yash.javalastyear.service.dto.CustDocDTO;
import com.yash.javalastyear.service.mapper.CustDocMapper;
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
 * Test class for the CustDocResource REST controller.
 *
 * @see CustDocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class CustDocResourceIntTest {

    private static final Long DEFAULT_CUST_DOC_ID = 1L;
    private static final Long UPDATED_CUST_DOC_ID = 2L;

    private static final String DEFAULT_DOC_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_FILE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_DOC_REMARKS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ISSUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ISSUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CustDocRepository custDocRepository;

    @Autowired
    private CustDocMapper custDocMapper;

    @Autowired
    private CustDocService custDocService;

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

    private MockMvc restCustDocMockMvc;

    private CustDoc custDoc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustDocResource custDocResource = new CustDocResource(custDocService);
        this.restCustDocMockMvc = MockMvcBuilders.standaloneSetup(custDocResource)
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
    public static CustDoc createEntity(EntityManager em) {
        CustDoc custDoc = new CustDoc()
            .custDocID(DEFAULT_CUST_DOC_ID)
            .docTitle(DEFAULT_DOC_TITLE)
            .docFile(DEFAULT_DOC_FILE)
            .docRemarks(DEFAULT_DOC_REMARKS)
            .issueDate(DEFAULT_ISSUE_DATE)
            .dueDate(DEFAULT_DUE_DATE);
        return custDoc;
    }

    @Before
    public void initTest() {
        custDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustDoc() throws Exception {
        int databaseSizeBeforeCreate = custDocRepository.findAll().size();

        // Create the CustDoc
        CustDocDTO custDocDTO = custDocMapper.toDto(custDoc);
        restCustDocMockMvc.perform(post("/api/cust-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custDocDTO)))
            .andExpect(status().isCreated());

        // Validate the CustDoc in the database
        List<CustDoc> custDocList = custDocRepository.findAll();
        assertThat(custDocList).hasSize(databaseSizeBeforeCreate + 1);
        CustDoc testCustDoc = custDocList.get(custDocList.size() - 1);
        assertThat(testCustDoc.getCustDocID()).isEqualTo(DEFAULT_CUST_DOC_ID);
        assertThat(testCustDoc.getDocTitle()).isEqualTo(DEFAULT_DOC_TITLE);
        assertThat(testCustDoc.getDocFile()).isEqualTo(DEFAULT_DOC_FILE);
        assertThat(testCustDoc.getDocRemarks()).isEqualTo(DEFAULT_DOC_REMARKS);
        assertThat(testCustDoc.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testCustDoc.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    public void createCustDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custDocRepository.findAll().size();

        // Create the CustDoc with an existing ID
        custDoc.setId(1L);
        CustDocDTO custDocDTO = custDocMapper.toDto(custDoc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustDocMockMvc.perform(post("/api/cust-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustDoc in the database
        List<CustDoc> custDocList = custDocRepository.findAll();
        assertThat(custDocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustDocs() throws Exception {
        // Initialize the database
        custDocRepository.saveAndFlush(custDoc);

        // Get all the custDocList
        restCustDocMockMvc.perform(get("/api/cust-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].custDocID").value(hasItem(DEFAULT_CUST_DOC_ID.intValue())))
            .andExpect(jsonPath("$.[*].docTitle").value(hasItem(DEFAULT_DOC_TITLE.toString())))
            .andExpect(jsonPath("$.[*].docFile").value(hasItem(DEFAULT_DOC_FILE.toString())))
            .andExpect(jsonPath("$.[*].docRemarks").value(hasItem(DEFAULT_DOC_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(sameInstant(DEFAULT_ISSUE_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))));
    }
    
    @Test
    @Transactional
    public void getCustDoc() throws Exception {
        // Initialize the database
        custDocRepository.saveAndFlush(custDoc);

        // Get the custDoc
        restCustDocMockMvc.perform(get("/api/cust-docs/{id}", custDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(custDoc.getId().intValue()))
            .andExpect(jsonPath("$.custDocID").value(DEFAULT_CUST_DOC_ID.intValue()))
            .andExpect(jsonPath("$.docTitle").value(DEFAULT_DOC_TITLE.toString()))
            .andExpect(jsonPath("$.docFile").value(DEFAULT_DOC_FILE.toString()))
            .andExpect(jsonPath("$.docRemarks").value(DEFAULT_DOC_REMARKS.toString()))
            .andExpect(jsonPath("$.issueDate").value(sameInstant(DEFAULT_ISSUE_DATE)))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingCustDoc() throws Exception {
        // Get the custDoc
        restCustDocMockMvc.perform(get("/api/cust-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustDoc() throws Exception {
        // Initialize the database
        custDocRepository.saveAndFlush(custDoc);

        int databaseSizeBeforeUpdate = custDocRepository.findAll().size();

        // Update the custDoc
        CustDoc updatedCustDoc = custDocRepository.findById(custDoc.getId()).get();
        // Disconnect from session so that the updates on updatedCustDoc are not directly saved in db
        em.detach(updatedCustDoc);
        updatedCustDoc
            .custDocID(UPDATED_CUST_DOC_ID)
            .docTitle(UPDATED_DOC_TITLE)
            .docFile(UPDATED_DOC_FILE)
            .docRemarks(UPDATED_DOC_REMARKS)
            .issueDate(UPDATED_ISSUE_DATE)
            .dueDate(UPDATED_DUE_DATE);
        CustDocDTO custDocDTO = custDocMapper.toDto(updatedCustDoc);

        restCustDocMockMvc.perform(put("/api/cust-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custDocDTO)))
            .andExpect(status().isOk());

        // Validate the CustDoc in the database
        List<CustDoc> custDocList = custDocRepository.findAll();
        assertThat(custDocList).hasSize(databaseSizeBeforeUpdate);
        CustDoc testCustDoc = custDocList.get(custDocList.size() - 1);
        assertThat(testCustDoc.getCustDocID()).isEqualTo(UPDATED_CUST_DOC_ID);
        assertThat(testCustDoc.getDocTitle()).isEqualTo(UPDATED_DOC_TITLE);
        assertThat(testCustDoc.getDocFile()).isEqualTo(UPDATED_DOC_FILE);
        assertThat(testCustDoc.getDocRemarks()).isEqualTo(UPDATED_DOC_REMARKS);
        assertThat(testCustDoc.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testCustDoc.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustDoc() throws Exception {
        int databaseSizeBeforeUpdate = custDocRepository.findAll().size();

        // Create the CustDoc
        CustDocDTO custDocDTO = custDocMapper.toDto(custDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustDocMockMvc.perform(put("/api/cust-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustDoc in the database
        List<CustDoc> custDocList = custDocRepository.findAll();
        assertThat(custDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustDoc() throws Exception {
        // Initialize the database
        custDocRepository.saveAndFlush(custDoc);

        int databaseSizeBeforeDelete = custDocRepository.findAll().size();

        // Delete the custDoc
        restCustDocMockMvc.perform(delete("/api/cust-docs/{id}", custDoc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustDoc> custDocList = custDocRepository.findAll();
        assertThat(custDocList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustDoc.class);
        CustDoc custDoc1 = new CustDoc();
        custDoc1.setId(1L);
        CustDoc custDoc2 = new CustDoc();
        custDoc2.setId(custDoc1.getId());
        assertThat(custDoc1).isEqualTo(custDoc2);
        custDoc2.setId(2L);
        assertThat(custDoc1).isNotEqualTo(custDoc2);
        custDoc1.setId(null);
        assertThat(custDoc1).isNotEqualTo(custDoc2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustDocDTO.class);
        CustDocDTO custDocDTO1 = new CustDocDTO();
        custDocDTO1.setId(1L);
        CustDocDTO custDocDTO2 = new CustDocDTO();
        assertThat(custDocDTO1).isNotEqualTo(custDocDTO2);
        custDocDTO2.setId(custDocDTO1.getId());
        assertThat(custDocDTO1).isEqualTo(custDocDTO2);
        custDocDTO2.setId(2L);
        assertThat(custDocDTO1).isNotEqualTo(custDocDTO2);
        custDocDTO1.setId(null);
        assertThat(custDocDTO1).isNotEqualTo(custDocDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(custDocMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(custDocMapper.fromId(null)).isNull();
    }
}
