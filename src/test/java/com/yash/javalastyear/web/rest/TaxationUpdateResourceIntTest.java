package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.TaxationUpdate;
import com.yash.javalastyear.repository.TaxationUpdateRepository;
import com.yash.javalastyear.service.TaxationUpdateService;
import com.yash.javalastyear.service.dto.TaxationUpdateDTO;
import com.yash.javalastyear.service.mapper.TaxationUpdateMapper;
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
 * Test class for the TaxationUpdateResource REST controller.
 *
 * @see TaxationUpdateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class TaxationUpdateResourceIntTest {

    private static final Long DEFAULT_TAXATION_UPDATE_ID = 1L;
    private static final Long UPDATED_TAXATION_UPDATE_ID = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private TaxationUpdateRepository taxationUpdateRepository;

    @Autowired
    private TaxationUpdateMapper taxationUpdateMapper;

    @Autowired
    private TaxationUpdateService taxationUpdateService;

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

    private MockMvc restTaxationUpdateMockMvc;

    private TaxationUpdate taxationUpdate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxationUpdateResource taxationUpdateResource = new TaxationUpdateResource(taxationUpdateService);
        this.restTaxationUpdateMockMvc = MockMvcBuilders.standaloneSetup(taxationUpdateResource)
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
    public static TaxationUpdate createEntity(EntityManager em) {
        TaxationUpdate taxationUpdate = new TaxationUpdate()
            .taxationUpdateID(DEFAULT_TAXATION_UPDATE_ID)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .photo(DEFAULT_PHOTO)
            .createDate(DEFAULT_CREATE_DATE)
            .isActive(DEFAULT_IS_ACTIVE);
        return taxationUpdate;
    }

    @Before
    public void initTest() {
        taxationUpdate = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxationUpdate() throws Exception {
        int databaseSizeBeforeCreate = taxationUpdateRepository.findAll().size();

        // Create the TaxationUpdate
        TaxationUpdateDTO taxationUpdateDTO = taxationUpdateMapper.toDto(taxationUpdate);
        restTaxationUpdateMockMvc.perform(post("/api/taxation-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationUpdateDTO)))
            .andExpect(status().isCreated());

        // Validate the TaxationUpdate in the database
        List<TaxationUpdate> taxationUpdateList = taxationUpdateRepository.findAll();
        assertThat(taxationUpdateList).hasSize(databaseSizeBeforeCreate + 1);
        TaxationUpdate testTaxationUpdate = taxationUpdateList.get(taxationUpdateList.size() - 1);
        assertThat(testTaxationUpdate.getTaxationUpdateID()).isEqualTo(DEFAULT_TAXATION_UPDATE_ID);
        assertThat(testTaxationUpdate.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTaxationUpdate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaxationUpdate.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testTaxationUpdate.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testTaxationUpdate.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createTaxationUpdateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxationUpdateRepository.findAll().size();

        // Create the TaxationUpdate with an existing ID
        taxationUpdate.setId(1L);
        TaxationUpdateDTO taxationUpdateDTO = taxationUpdateMapper.toDto(taxationUpdate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxationUpdateMockMvc.perform(post("/api/taxation-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationUpdateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxationUpdate in the database
        List<TaxationUpdate> taxationUpdateList = taxationUpdateRepository.findAll();
        assertThat(taxationUpdateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaxationUpdates() throws Exception {
        // Initialize the database
        taxationUpdateRepository.saveAndFlush(taxationUpdate);

        // Get all the taxationUpdateList
        restTaxationUpdateMockMvc.perform(get("/api/taxation-updates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxationUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxationUpdateID").value(hasItem(DEFAULT_TAXATION_UPDATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTaxationUpdate() throws Exception {
        // Initialize the database
        taxationUpdateRepository.saveAndFlush(taxationUpdate);

        // Get the taxationUpdate
        restTaxationUpdateMockMvc.perform(get("/api/taxation-updates/{id}", taxationUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxationUpdate.getId().intValue()))
            .andExpect(jsonPath("$.taxationUpdateID").value(DEFAULT_TAXATION_UPDATE_ID.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxationUpdate() throws Exception {
        // Get the taxationUpdate
        restTaxationUpdateMockMvc.perform(get("/api/taxation-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxationUpdate() throws Exception {
        // Initialize the database
        taxationUpdateRepository.saveAndFlush(taxationUpdate);

        int databaseSizeBeforeUpdate = taxationUpdateRepository.findAll().size();

        // Update the taxationUpdate
        TaxationUpdate updatedTaxationUpdate = taxationUpdateRepository.findById(taxationUpdate.getId()).get();
        // Disconnect from session so that the updates on updatedTaxationUpdate are not directly saved in db
        em.detach(updatedTaxationUpdate);
        updatedTaxationUpdate
            .taxationUpdateID(UPDATED_TAXATION_UPDATE_ID)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .photo(UPDATED_PHOTO)
            .createDate(UPDATED_CREATE_DATE)
            .isActive(UPDATED_IS_ACTIVE);
        TaxationUpdateDTO taxationUpdateDTO = taxationUpdateMapper.toDto(updatedTaxationUpdate);

        restTaxationUpdateMockMvc.perform(put("/api/taxation-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationUpdateDTO)))
            .andExpect(status().isOk());

        // Validate the TaxationUpdate in the database
        List<TaxationUpdate> taxationUpdateList = taxationUpdateRepository.findAll();
        assertThat(taxationUpdateList).hasSize(databaseSizeBeforeUpdate);
        TaxationUpdate testTaxationUpdate = taxationUpdateList.get(taxationUpdateList.size() - 1);
        assertThat(testTaxationUpdate.getTaxationUpdateID()).isEqualTo(UPDATED_TAXATION_UPDATE_ID);
        assertThat(testTaxationUpdate.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTaxationUpdate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaxationUpdate.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTaxationUpdate.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testTaxationUpdate.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxationUpdate() throws Exception {
        int databaseSizeBeforeUpdate = taxationUpdateRepository.findAll().size();

        // Create the TaxationUpdate
        TaxationUpdateDTO taxationUpdateDTO = taxationUpdateMapper.toDto(taxationUpdate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxationUpdateMockMvc.perform(put("/api/taxation-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxationUpdateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaxationUpdate in the database
        List<TaxationUpdate> taxationUpdateList = taxationUpdateRepository.findAll();
        assertThat(taxationUpdateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxationUpdate() throws Exception {
        // Initialize the database
        taxationUpdateRepository.saveAndFlush(taxationUpdate);

        int databaseSizeBeforeDelete = taxationUpdateRepository.findAll().size();

        // Delete the taxationUpdate
        restTaxationUpdateMockMvc.perform(delete("/api/taxation-updates/{id}", taxationUpdate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaxationUpdate> taxationUpdateList = taxationUpdateRepository.findAll();
        assertThat(taxationUpdateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxationUpdate.class);
        TaxationUpdate taxationUpdate1 = new TaxationUpdate();
        taxationUpdate1.setId(1L);
        TaxationUpdate taxationUpdate2 = new TaxationUpdate();
        taxationUpdate2.setId(taxationUpdate1.getId());
        assertThat(taxationUpdate1).isEqualTo(taxationUpdate2);
        taxationUpdate2.setId(2L);
        assertThat(taxationUpdate1).isNotEqualTo(taxationUpdate2);
        taxationUpdate1.setId(null);
        assertThat(taxationUpdate1).isNotEqualTo(taxationUpdate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxationUpdateDTO.class);
        TaxationUpdateDTO taxationUpdateDTO1 = new TaxationUpdateDTO();
        taxationUpdateDTO1.setId(1L);
        TaxationUpdateDTO taxationUpdateDTO2 = new TaxationUpdateDTO();
        assertThat(taxationUpdateDTO1).isNotEqualTo(taxationUpdateDTO2);
        taxationUpdateDTO2.setId(taxationUpdateDTO1.getId());
        assertThat(taxationUpdateDTO1).isEqualTo(taxationUpdateDTO2);
        taxationUpdateDTO2.setId(2L);
        assertThat(taxationUpdateDTO1).isNotEqualTo(taxationUpdateDTO2);
        taxationUpdateDTO1.setId(null);
        assertThat(taxationUpdateDTO1).isNotEqualTo(taxationUpdateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taxationUpdateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taxationUpdateMapper.fromId(null)).isNull();
    }
}
