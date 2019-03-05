package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.CustSchedule;
import com.yash.javalastyear.repository.CustScheduleRepository;
import com.yash.javalastyear.service.CustScheduleService;
import com.yash.javalastyear.service.dto.CustScheduleDTO;
import com.yash.javalastyear.service.mapper.CustScheduleMapper;
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
 * Test class for the CustScheduleResource REST controller.
 *
 * @see CustScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class CustScheduleResourceIntTest {

    private static final Long DEFAULT_CUST_SCHEDULE_ID = 1L;
    private static final Long UPDATED_CUST_SCHEDULE_ID = 2L;

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_REQUIREMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACTUAL_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DOC_FILE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_FILE = "BBBBBBBBBB";

    @Autowired
    private CustScheduleRepository custScheduleRepository;

    @Autowired
    private CustScheduleMapper custScheduleMapper;

    @Autowired
    private CustScheduleService custScheduleService;

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

    private MockMvc restCustScheduleMockMvc;

    private CustSchedule custSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustScheduleResource custScheduleResource = new CustScheduleResource(custScheduleService);
        this.restCustScheduleMockMvc = MockMvcBuilders.standaloneSetup(custScheduleResource)
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
    public static CustSchedule createEntity(EntityManager em) {
        CustSchedule custSchedule = new CustSchedule()
            .custScheduleID(DEFAULT_CUST_SCHEDULE_ID)
            .dueDate(DEFAULT_DUE_DATE)
            .requirements(DEFAULT_REQUIREMENTS)
            .status(DEFAULT_STATUS)
            .actualDate(DEFAULT_ACTUAL_DATE)
            .docFile(DEFAULT_DOC_FILE);
        return custSchedule;
    }

    @Before
    public void initTest() {
        custSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustSchedule() throws Exception {
        int databaseSizeBeforeCreate = custScheduleRepository.findAll().size();

        // Create the CustSchedule
        CustScheduleDTO custScheduleDTO = custScheduleMapper.toDto(custSchedule);
        restCustScheduleMockMvc.perform(post("/api/cust-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the CustSchedule in the database
        List<CustSchedule> custScheduleList = custScheduleRepository.findAll();
        assertThat(custScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        CustSchedule testCustSchedule = custScheduleList.get(custScheduleList.size() - 1);
        assertThat(testCustSchedule.getCustScheduleID()).isEqualTo(DEFAULT_CUST_SCHEDULE_ID);
        assertThat(testCustSchedule.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCustSchedule.getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
        assertThat(testCustSchedule.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustSchedule.getActualDate()).isEqualTo(DEFAULT_ACTUAL_DATE);
        assertThat(testCustSchedule.getDocFile()).isEqualTo(DEFAULT_DOC_FILE);
    }

    @Test
    @Transactional
    public void createCustScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = custScheduleRepository.findAll().size();

        // Create the CustSchedule with an existing ID
        custSchedule.setId(1L);
        CustScheduleDTO custScheduleDTO = custScheduleMapper.toDto(custSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustScheduleMockMvc.perform(post("/api/cust-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustSchedule in the database
        List<CustSchedule> custScheduleList = custScheduleRepository.findAll();
        assertThat(custScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustSchedules() throws Exception {
        // Initialize the database
        custScheduleRepository.saveAndFlush(custSchedule);

        // Get all the custScheduleList
        restCustScheduleMockMvc.perform(get("/api/cust-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].custScheduleID").value(hasItem(DEFAULT_CUST_SCHEDULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].requirements").value(hasItem(DEFAULT_REQUIREMENTS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].actualDate").value(hasItem(sameInstant(DEFAULT_ACTUAL_DATE))))
            .andExpect(jsonPath("$.[*].docFile").value(hasItem(DEFAULT_DOC_FILE.toString())));
    }
    
    @Test
    @Transactional
    public void getCustSchedule() throws Exception {
        // Initialize the database
        custScheduleRepository.saveAndFlush(custSchedule);

        // Get the custSchedule
        restCustScheduleMockMvc.perform(get("/api/cust-schedules/{id}", custSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(custSchedule.getId().intValue()))
            .andExpect(jsonPath("$.custScheduleID").value(DEFAULT_CUST_SCHEDULE_ID.intValue()))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)))
            .andExpect(jsonPath("$.requirements").value(DEFAULT_REQUIREMENTS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.actualDate").value(sameInstant(DEFAULT_ACTUAL_DATE)))
            .andExpect(jsonPath("$.docFile").value(DEFAULT_DOC_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustSchedule() throws Exception {
        // Get the custSchedule
        restCustScheduleMockMvc.perform(get("/api/cust-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustSchedule() throws Exception {
        // Initialize the database
        custScheduleRepository.saveAndFlush(custSchedule);

        int databaseSizeBeforeUpdate = custScheduleRepository.findAll().size();

        // Update the custSchedule
        CustSchedule updatedCustSchedule = custScheduleRepository.findById(custSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedCustSchedule are not directly saved in db
        em.detach(updatedCustSchedule);
        updatedCustSchedule
            .custScheduleID(UPDATED_CUST_SCHEDULE_ID)
            .dueDate(UPDATED_DUE_DATE)
            .requirements(UPDATED_REQUIREMENTS)
            .status(UPDATED_STATUS)
            .actualDate(UPDATED_ACTUAL_DATE)
            .docFile(UPDATED_DOC_FILE);
        CustScheduleDTO custScheduleDTO = custScheduleMapper.toDto(updatedCustSchedule);

        restCustScheduleMockMvc.perform(put("/api/cust-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the CustSchedule in the database
        List<CustSchedule> custScheduleList = custScheduleRepository.findAll();
        assertThat(custScheduleList).hasSize(databaseSizeBeforeUpdate);
        CustSchedule testCustSchedule = custScheduleList.get(custScheduleList.size() - 1);
        assertThat(testCustSchedule.getCustScheduleID()).isEqualTo(UPDATED_CUST_SCHEDULE_ID);
        assertThat(testCustSchedule.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCustSchedule.getRequirements()).isEqualTo(UPDATED_REQUIREMENTS);
        assertThat(testCustSchedule.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustSchedule.getActualDate()).isEqualTo(UPDATED_ACTUAL_DATE);
        assertThat(testCustSchedule.getDocFile()).isEqualTo(UPDATED_DOC_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustSchedule() throws Exception {
        int databaseSizeBeforeUpdate = custScheduleRepository.findAll().size();

        // Create the CustSchedule
        CustScheduleDTO custScheduleDTO = custScheduleMapper.toDto(custSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustScheduleMockMvc.perform(put("/api/cust-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(custScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustSchedule in the database
        List<CustSchedule> custScheduleList = custScheduleRepository.findAll();
        assertThat(custScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustSchedule() throws Exception {
        // Initialize the database
        custScheduleRepository.saveAndFlush(custSchedule);

        int databaseSizeBeforeDelete = custScheduleRepository.findAll().size();

        // Delete the custSchedule
        restCustScheduleMockMvc.perform(delete("/api/cust-schedules/{id}", custSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustSchedule> custScheduleList = custScheduleRepository.findAll();
        assertThat(custScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustSchedule.class);
        CustSchedule custSchedule1 = new CustSchedule();
        custSchedule1.setId(1L);
        CustSchedule custSchedule2 = new CustSchedule();
        custSchedule2.setId(custSchedule1.getId());
        assertThat(custSchedule1).isEqualTo(custSchedule2);
        custSchedule2.setId(2L);
        assertThat(custSchedule1).isNotEqualTo(custSchedule2);
        custSchedule1.setId(null);
        assertThat(custSchedule1).isNotEqualTo(custSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustScheduleDTO.class);
        CustScheduleDTO custScheduleDTO1 = new CustScheduleDTO();
        custScheduleDTO1.setId(1L);
        CustScheduleDTO custScheduleDTO2 = new CustScheduleDTO();
        assertThat(custScheduleDTO1).isNotEqualTo(custScheduleDTO2);
        custScheduleDTO2.setId(custScheduleDTO1.getId());
        assertThat(custScheduleDTO1).isEqualTo(custScheduleDTO2);
        custScheduleDTO2.setId(2L);
        assertThat(custScheduleDTO1).isNotEqualTo(custScheduleDTO2);
        custScheduleDTO1.setId(null);
        assertThat(custScheduleDTO1).isNotEqualTo(custScheduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(custScheduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(custScheduleMapper.fromId(null)).isNull();
    }
}
