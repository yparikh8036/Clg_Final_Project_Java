package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.InternalDoc;
import com.yash.javalastyear.repository.InternalDocRepository;
import com.yash.javalastyear.service.InternalDocService;
import com.yash.javalastyear.service.dto.InternalDocDTO;
import com.yash.javalastyear.service.mapper.InternalDocMapper;
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
 * Test class for the InternalDocResource REST controller.
 *
 * @see InternalDocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class InternalDocResourceIntTest {

    private static final Long DEFAULT_INTERNAL_DOC_ID = 1L;
    private static final Long UPDATED_INTERNAL_DOC_ID = 2L;

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
    private InternalDocRepository internalDocRepository;

    @Autowired
    private InternalDocMapper internalDocMapper;

    @Autowired
    private InternalDocService internalDocService;

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

    private MockMvc restInternalDocMockMvc;

    private InternalDoc internalDoc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternalDocResource internalDocResource = new InternalDocResource(internalDocService);
        this.restInternalDocMockMvc = MockMvcBuilders.standaloneSetup(internalDocResource)
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
    public static InternalDoc createEntity(EntityManager em) {
        InternalDoc internalDoc = new InternalDoc()
            .internalDocID(DEFAULT_INTERNAL_DOC_ID)
            .docTitle(DEFAULT_DOC_TITLE)
            .docFile(DEFAULT_DOC_FILE)
            .docRemarks(DEFAULT_DOC_REMARKS)
            .issueDate(DEFAULT_ISSUE_DATE)
            .dueDate(DEFAULT_DUE_DATE);
        return internalDoc;
    }

    @Before
    public void initTest() {
        internalDoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternalDoc() throws Exception {
        int databaseSizeBeforeCreate = internalDocRepository.findAll().size();

        // Create the InternalDoc
        InternalDocDTO internalDocDTO = internalDocMapper.toDto(internalDoc);
        restInternalDocMockMvc.perform(post("/api/internal-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalDocDTO)))
            .andExpect(status().isCreated());

        // Validate the InternalDoc in the database
        List<InternalDoc> internalDocList = internalDocRepository.findAll();
        assertThat(internalDocList).hasSize(databaseSizeBeforeCreate + 1);
        InternalDoc testInternalDoc = internalDocList.get(internalDocList.size() - 1);
        assertThat(testInternalDoc.getInternalDocID()).isEqualTo(DEFAULT_INTERNAL_DOC_ID);
        assertThat(testInternalDoc.getDocTitle()).isEqualTo(DEFAULT_DOC_TITLE);
        assertThat(testInternalDoc.getDocFile()).isEqualTo(DEFAULT_DOC_FILE);
        assertThat(testInternalDoc.getDocRemarks()).isEqualTo(DEFAULT_DOC_REMARKS);
        assertThat(testInternalDoc.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testInternalDoc.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    public void createInternalDocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internalDocRepository.findAll().size();

        // Create the InternalDoc with an existing ID
        internalDoc.setId(1L);
        InternalDocDTO internalDocDTO = internalDocMapper.toDto(internalDoc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternalDocMockMvc.perform(post("/api/internal-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InternalDoc in the database
        List<InternalDoc> internalDocList = internalDocRepository.findAll();
        assertThat(internalDocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInternalDocs() throws Exception {
        // Initialize the database
        internalDocRepository.saveAndFlush(internalDoc);

        // Get all the internalDocList
        restInternalDocMockMvc.perform(get("/api/internal-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].internalDocID").value(hasItem(DEFAULT_INTERNAL_DOC_ID.intValue())))
            .andExpect(jsonPath("$.[*].docTitle").value(hasItem(DEFAULT_DOC_TITLE.toString())))
            .andExpect(jsonPath("$.[*].docFile").value(hasItem(DEFAULT_DOC_FILE.toString())))
            .andExpect(jsonPath("$.[*].docRemarks").value(hasItem(DEFAULT_DOC_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(sameInstant(DEFAULT_ISSUE_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))));
    }
    
    @Test
    @Transactional
    public void getInternalDoc() throws Exception {
        // Initialize the database
        internalDocRepository.saveAndFlush(internalDoc);

        // Get the internalDoc
        restInternalDocMockMvc.perform(get("/api/internal-docs/{id}", internalDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internalDoc.getId().intValue()))
            .andExpect(jsonPath("$.internalDocID").value(DEFAULT_INTERNAL_DOC_ID.intValue()))
            .andExpect(jsonPath("$.docTitle").value(DEFAULT_DOC_TITLE.toString()))
            .andExpect(jsonPath("$.docFile").value(DEFAULT_DOC_FILE.toString()))
            .andExpect(jsonPath("$.docRemarks").value(DEFAULT_DOC_REMARKS.toString()))
            .andExpect(jsonPath("$.issueDate").value(sameInstant(DEFAULT_ISSUE_DATE)))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingInternalDoc() throws Exception {
        // Get the internalDoc
        restInternalDocMockMvc.perform(get("/api/internal-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternalDoc() throws Exception {
        // Initialize the database
        internalDocRepository.saveAndFlush(internalDoc);

        int databaseSizeBeforeUpdate = internalDocRepository.findAll().size();

        // Update the internalDoc
        InternalDoc updatedInternalDoc = internalDocRepository.findById(internalDoc.getId()).get();
        // Disconnect from session so that the updates on updatedInternalDoc are not directly saved in db
        em.detach(updatedInternalDoc);
        updatedInternalDoc
            .internalDocID(UPDATED_INTERNAL_DOC_ID)
            .docTitle(UPDATED_DOC_TITLE)
            .docFile(UPDATED_DOC_FILE)
            .docRemarks(UPDATED_DOC_REMARKS)
            .issueDate(UPDATED_ISSUE_DATE)
            .dueDate(UPDATED_DUE_DATE);
        InternalDocDTO internalDocDTO = internalDocMapper.toDto(updatedInternalDoc);

        restInternalDocMockMvc.perform(put("/api/internal-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalDocDTO)))
            .andExpect(status().isOk());

        // Validate the InternalDoc in the database
        List<InternalDoc> internalDocList = internalDocRepository.findAll();
        assertThat(internalDocList).hasSize(databaseSizeBeforeUpdate);
        InternalDoc testInternalDoc = internalDocList.get(internalDocList.size() - 1);
        assertThat(testInternalDoc.getInternalDocID()).isEqualTo(UPDATED_INTERNAL_DOC_ID);
        assertThat(testInternalDoc.getDocTitle()).isEqualTo(UPDATED_DOC_TITLE);
        assertThat(testInternalDoc.getDocFile()).isEqualTo(UPDATED_DOC_FILE);
        assertThat(testInternalDoc.getDocRemarks()).isEqualTo(UPDATED_DOC_REMARKS);
        assertThat(testInternalDoc.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testInternalDoc.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInternalDoc() throws Exception {
        int databaseSizeBeforeUpdate = internalDocRepository.findAll().size();

        // Create the InternalDoc
        InternalDocDTO internalDocDTO = internalDocMapper.toDto(internalDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalDocMockMvc.perform(put("/api/internal-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalDocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InternalDoc in the database
        List<InternalDoc> internalDocList = internalDocRepository.findAll();
        assertThat(internalDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInternalDoc() throws Exception {
        // Initialize the database
        internalDocRepository.saveAndFlush(internalDoc);

        int databaseSizeBeforeDelete = internalDocRepository.findAll().size();

        // Delete the internalDoc
        restInternalDocMockMvc.perform(delete("/api/internal-docs/{id}", internalDoc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InternalDoc> internalDocList = internalDocRepository.findAll();
        assertThat(internalDocList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalDoc.class);
        InternalDoc internalDoc1 = new InternalDoc();
        internalDoc1.setId(1L);
        InternalDoc internalDoc2 = new InternalDoc();
        internalDoc2.setId(internalDoc1.getId());
        assertThat(internalDoc1).isEqualTo(internalDoc2);
        internalDoc2.setId(2L);
        assertThat(internalDoc1).isNotEqualTo(internalDoc2);
        internalDoc1.setId(null);
        assertThat(internalDoc1).isNotEqualTo(internalDoc2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalDocDTO.class);
        InternalDocDTO internalDocDTO1 = new InternalDocDTO();
        internalDocDTO1.setId(1L);
        InternalDocDTO internalDocDTO2 = new InternalDocDTO();
        assertThat(internalDocDTO1).isNotEqualTo(internalDocDTO2);
        internalDocDTO2.setId(internalDocDTO1.getId());
        assertThat(internalDocDTO1).isEqualTo(internalDocDTO2);
        internalDocDTO2.setId(2L);
        assertThat(internalDocDTO1).isNotEqualTo(internalDocDTO2);
        internalDocDTO1.setId(null);
        assertThat(internalDocDTO1).isNotEqualTo(internalDocDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(internalDocMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(internalDocMapper.fromId(null)).isNull();
    }
}
