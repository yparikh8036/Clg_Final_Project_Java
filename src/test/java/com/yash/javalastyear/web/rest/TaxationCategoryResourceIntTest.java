package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.TaxationCategory;
import com.yash.javalastyear.repository.TaxationCategoryRepository;
import com.yash.javalastyear.service.TaxationCategoryService;
import com.yash.javalastyear.service.dto.TaxationCategoryDTO;
import com.yash.javalastyear.service.mapper.TaxationCategoryMapper;
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
 * Test class for the TaxationCategoryResource REST controller.
 *
 * @see TaxationCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class TaxationCategoryResourceIntTest {

    private static final Long DEFAULT_TAXATION_CATEGORY_ID = 1L;
    private static final Long UPDATED_TAXATION_CATEGORY_ID = 2L;

    private static final String DEFAULT_CAT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAT_NAME = "BBBBBBBBBB";

    @Autowired
    private TaxationCategoryRepository taxationCategoryRepository;

    @Autowired
    private TaxationCategoryMapper taxationCategoryMapper;

    @Autowired
    private TaxationCategoryService taxationCategoryService;

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

    private MockMvc restTaxationCategoryMockMvc;

    private TaxationCategory taxationCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxationCategoryResource taxationCategoryResource = new TaxationCategoryResource(taxationCategoryService);
        this.restTaxationCategoryMockMvc = MockMvcBuilders.standaloneSetup(taxationCategoryResource)
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
    public static TaxationCategory createEntity(EntityManager em) {
        TaxationCategory taxationCategory = new TaxationCategory()
            .taxationCategoryID(DEFAULT_TAXATION_CATEGORY_ID)
            .catName(DEFAULT_CAT_NAME);
        return taxationCategory;
    }

    @Before
    public void initTest() {
        taxationCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxationCategory() throws Exception {
        int databaseSizeBeforeCreate = taxationCategoryRepository.findAll().size();

        // Create the TaxationCategory
        TaxationCategoryDTO taxationCategoryDTO = taxationCategoryMapper.toDto(taxationCategory);
        restTaxationCategoryMockMvc.perform(post("/api/taxation-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TaxationCategory in the database
        List<TaxationCategory> taxationCategoryList = taxationCategoryRepository.findAll();
        assertThat(taxationCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        TaxationCategory testTaxationCategory = taxationCategoryList.get(taxationCategoryList.size() - 1);
        assertThat(testTaxationCategory.getTaxationCategoryID()).isEqualTo(DEFAULT_TAXATION_CATEGORY_ID);
        assertThat(testTaxationCategory.getCatName()).isEqualTo(DEFAULT_CAT_NAME);
    }

    @Test
    @Transactional
    public void createTaxationCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxationCategoryRepository.findAll().size();

        // Create the TaxationCategory with an existing ID
        taxationCategory.setId(1L);
        TaxationCategoryDTO taxationCategoryDTO = taxationCategoryMapper.toDto(taxationCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxationCategoryMockMvc.perform(post("/api/taxation-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxationCategory in the database
        List<TaxationCategory> taxationCategoryList = taxationCategoryRepository.findAll();
        assertThat(taxationCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaxationCategories() throws Exception {
        // Initialize the database
        taxationCategoryRepository.saveAndFlush(taxationCategory);

        // Get all the taxationCategoryList
        restTaxationCategoryMockMvc.perform(get("/api/taxation-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxationCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxationCategoryID").value(hasItem(DEFAULT_TAXATION_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].catName").value(hasItem(DEFAULT_CAT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTaxationCategory() throws Exception {
        // Initialize the database
        taxationCategoryRepository.saveAndFlush(taxationCategory);

        // Get the taxationCategory
        restTaxationCategoryMockMvc.perform(get("/api/taxation-categories/{id}", taxationCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxationCategory.getId().intValue()))
            .andExpect(jsonPath("$.taxationCategoryID").value(DEFAULT_TAXATION_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.catName").value(DEFAULT_CAT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxationCategory() throws Exception {
        // Get the taxationCategory
        restTaxationCategoryMockMvc.perform(get("/api/taxation-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxationCategory() throws Exception {
        // Initialize the database
        taxationCategoryRepository.saveAndFlush(taxationCategory);

        int databaseSizeBeforeUpdate = taxationCategoryRepository.findAll().size();

        // Update the taxationCategory
        TaxationCategory updatedTaxationCategory = taxationCategoryRepository.findById(taxationCategory.getId()).get();
        // Disconnect from session so that the updates on updatedTaxationCategory are not directly saved in db
        em.detach(updatedTaxationCategory);
        updatedTaxationCategory
            .taxationCategoryID(UPDATED_TAXATION_CATEGORY_ID)
            .catName(UPDATED_CAT_NAME);
        TaxationCategoryDTO taxationCategoryDTO = taxationCategoryMapper.toDto(updatedTaxationCategory);

        restTaxationCategoryMockMvc.perform(put("/api/taxation-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the TaxationCategory in the database
        List<TaxationCategory> taxationCategoryList = taxationCategoryRepository.findAll();
        assertThat(taxationCategoryList).hasSize(databaseSizeBeforeUpdate);
        TaxationCategory testTaxationCategory = taxationCategoryList.get(taxationCategoryList.size() - 1);
        assertThat(testTaxationCategory.getTaxationCategoryID()).isEqualTo(UPDATED_TAXATION_CATEGORY_ID);
        assertThat(testTaxationCategory.getCatName()).isEqualTo(UPDATED_CAT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxationCategory() throws Exception {
        int databaseSizeBeforeUpdate = taxationCategoryRepository.findAll().size();

        // Create the TaxationCategory
        TaxationCategoryDTO taxationCategoryDTO = taxationCategoryMapper.toDto(taxationCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxationCategoryMockMvc.perform(put("/api/taxation-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxationCategory in the database
        List<TaxationCategory> taxationCategoryList = taxationCategoryRepository.findAll();
        assertThat(taxationCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxationCategory() throws Exception {
        // Initialize the database
        taxationCategoryRepository.saveAndFlush(taxationCategory);

        int databaseSizeBeforeDelete = taxationCategoryRepository.findAll().size();

        // Delete the taxationCategory
        restTaxationCategoryMockMvc.perform(delete("/api/taxation-categories/{id}", taxationCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaxationCategory> taxationCategoryList = taxationCategoryRepository.findAll();
        assertThat(taxationCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxationCategory.class);
        TaxationCategory taxationCategory1 = new TaxationCategory();
        taxationCategory1.setId(1L);
        TaxationCategory taxationCategory2 = new TaxationCategory();
        taxationCategory2.setId(taxationCategory1.getId());
        assertThat(taxationCategory1).isEqualTo(taxationCategory2);
        taxationCategory2.setId(2L);
        assertThat(taxationCategory1).isNotEqualTo(taxationCategory2);
        taxationCategory1.setId(null);
        assertThat(taxationCategory1).isNotEqualTo(taxationCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxationCategoryDTO.class);
        TaxationCategoryDTO taxationCategoryDTO1 = new TaxationCategoryDTO();
        taxationCategoryDTO1.setId(1L);
        TaxationCategoryDTO taxationCategoryDTO2 = new TaxationCategoryDTO();
        assertThat(taxationCategoryDTO1).isNotEqualTo(taxationCategoryDTO2);
        taxationCategoryDTO2.setId(taxationCategoryDTO1.getId());
        assertThat(taxationCategoryDTO1).isEqualTo(taxationCategoryDTO2);
        taxationCategoryDTO2.setId(2L);
        assertThat(taxationCategoryDTO1).isNotEqualTo(taxationCategoryDTO2);
        taxationCategoryDTO1.setId(null);
        assertThat(taxationCategoryDTO1).isNotEqualTo(taxationCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taxationCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taxationCategoryMapper.fromId(null)).isNull();
    }
}
