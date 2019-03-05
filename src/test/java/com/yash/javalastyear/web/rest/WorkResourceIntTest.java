package com.yash.javalastyear.web.rest;

import com.yash.javalastyear.JavalastyearApp;

import com.yash.javalastyear.domain.Work;
import com.yash.javalastyear.repository.WorkRepository;
import com.yash.javalastyear.service.WorkService;
import com.yash.javalastyear.service.dto.WorkDTO;
import com.yash.javalastyear.service.mapper.WorkMapper;
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
 * Test class for the WorkResource REST controller.
 *
 * @see WorkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavalastyearApp.class)
public class WorkResourceIntTest {

    private static final Long DEFAULT_WORK_ID = 1L;
    private static final Long UPDATED_WORK_ID = 2L;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMPLETION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMPLETION_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETION_REMARKS = "BBBBBBBBBB";

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkService workService;

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

    private MockMvc restWorkMockMvc;

    private Work work;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkResource workResource = new WorkResource(workService);
        this.restWorkMockMvc = MockMvcBuilders.standaloneSetup(workResource)
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
    public static Work createEntity(EntityManager em) {
        Work work = new Work()
            .workID(DEFAULT_WORK_ID)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .createDate(DEFAULT_CREATE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .status(DEFAULT_STATUS)
            .completionDate(DEFAULT_COMPLETION_DATE)
            .completionRemarks(DEFAULT_COMPLETION_REMARKS);
        return work;
    }

    @Before
    public void initTest() {
        work = createEntity(em);
    }

    @Test
    @Transactional
    public void createWork() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isCreated());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate + 1);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getWorkID()).isEqualTo(DEFAULT_WORK_ID);
        assertThat(testWork.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testWork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWork.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testWork.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testWork.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWork.getCompletionDate()).isEqualTo(DEFAULT_COMPLETION_DATE);
        assertThat(testWork.getCompletionRemarks()).isEqualTo(DEFAULT_COMPLETION_REMARKS);
    }

    @Test
    @Transactional
    public void createWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work with an existing ID
        work.setId(1L);
        WorkDTO workDTO = workMapper.toDto(work);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorks() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get all the workList
        restWorkMockMvc.perform(get("/api/works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(work.getId().intValue())))
            .andExpect(jsonPath("$.[*].workID").value(hasItem(DEFAULT_WORK_ID.intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(sameInstant(DEFAULT_COMPLETION_DATE))))
            .andExpect(jsonPath("$.[*].completionRemarks").value(hasItem(DEFAULT_COMPLETION_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(work.getId().intValue()))
            .andExpect(jsonPath("$.workID").value(DEFAULT_WORK_ID.intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.completionDate").value(sameInstant(DEFAULT_COMPLETION_DATE)))
            .andExpect(jsonPath("$.completionRemarks").value(DEFAULT_COMPLETION_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWork() throws Exception {
        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Update the work
        Work updatedWork = workRepository.findById(work.getId()).get();
        // Disconnect from session so that the updates on updatedWork are not directly saved in db
        em.detach(updatedWork);
        updatedWork
            .workID(UPDATED_WORK_ID)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createDate(UPDATED_CREATE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .status(UPDATED_STATUS)
            .completionDate(UPDATED_COMPLETION_DATE)
            .completionRemarks(UPDATED_COMPLETION_REMARKS);
        WorkDTO workDTO = workMapper.toDto(updatedWork);

        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isOk());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getWorkID()).isEqualTo(UPDATED_WORK_ID);
        assertThat(testWork.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWork.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testWork.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testWork.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWork.getCompletionDate()).isEqualTo(UPDATED_COMPLETION_DATE);
        assertThat(testWork.getCompletionRemarks()).isEqualTo(UPDATED_COMPLETION_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingWork() throws Exception {
        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        int databaseSizeBeforeDelete = workRepository.findAll().size();

        // Delete the work
        restWorkMockMvc.perform(delete("/api/works/{id}", work.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Work.class);
        Work work1 = new Work();
        work1.setId(1L);
        Work work2 = new Work();
        work2.setId(work1.getId());
        assertThat(work1).isEqualTo(work2);
        work2.setId(2L);
        assertThat(work1).isNotEqualTo(work2);
        work1.setId(null);
        assertThat(work1).isNotEqualTo(work2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDTO.class);
        WorkDTO workDTO1 = new WorkDTO();
        workDTO1.setId(1L);
        WorkDTO workDTO2 = new WorkDTO();
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO2.setId(workDTO1.getId());
        assertThat(workDTO1).isEqualTo(workDTO2);
        workDTO2.setId(2L);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO1.setId(null);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workMapper.fromId(null)).isNull();
    }
}
