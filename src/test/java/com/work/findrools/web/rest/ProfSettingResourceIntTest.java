package com.work.findrools.web.rest;

import com.work.findrools.FindroolsApp;

import com.work.findrools.domain.ProfSetting;
import com.work.findrools.repository.ProfSettingRepository;
import com.work.findrools.service.ProfSettingService;
import com.work.findrools.service.dto.ProfSettingDTO;
import com.work.findrools.service.mapper.ProfSettingMapper;
import com.work.findrools.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfSettingResource REST controller.
 *
 * @see ProfSettingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FindroolsApp.class)
public class ProfSettingResourceIntTest {

    private static final String DEFAULT_PROF_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PROF_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PROF_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PROF_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private ProfSettingRepository profSettingRepository;

    @Autowired
    private ProfSettingMapper profSettingMapper;

    @Autowired
    private ProfSettingService profSettingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfSettingMockMvc;

    private ProfSetting profSetting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfSettingResource profSettingResource = new ProfSettingResource(profSettingService);
        this.restProfSettingMockMvc = MockMvcBuilders.standaloneSetup(profSettingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfSetting createEntity(EntityManager em) {
        ProfSetting profSetting = new ProfSetting()
            .profKey(DEFAULT_PROF_KEY)
            .profValue(DEFAULT_PROF_VALUE)
            .category(DEFAULT_CATEGORY);
        return profSetting;
    }

    @Before
    public void initTest() {
        profSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfSetting() throws Exception {
        int databaseSizeBeforeCreate = profSettingRepository.findAll().size();

        // Create the ProfSetting
        ProfSettingDTO profSettingDTO = profSettingMapper.toDto(profSetting);
        restProfSettingMockMvc.perform(post("/api/prof-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfSetting in the database
        List<ProfSetting> profSettingList = profSettingRepository.findAll();
        assertThat(profSettingList).hasSize(databaseSizeBeforeCreate + 1);
        ProfSetting testProfSetting = profSettingList.get(profSettingList.size() - 1);
        assertThat(testProfSetting.getProfKey()).isEqualTo(DEFAULT_PROF_KEY);
        assertThat(testProfSetting.getProfValue()).isEqualTo(DEFAULT_PROF_VALUE);
        assertThat(testProfSetting.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createProfSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profSettingRepository.findAll().size();

        // Create the ProfSetting with an existing ID
        profSetting.setId(1L);
        ProfSettingDTO profSettingDTO = profSettingMapper.toDto(profSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfSettingMockMvc.perform(post("/api/prof-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProfSetting> profSettingList = profSettingRepository.findAll();
        assertThat(profSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfSettings() throws Exception {
        // Initialize the database
        profSettingRepository.saveAndFlush(profSetting);

        // Get all the profSettingList
        restProfSettingMockMvc.perform(get("/api/prof-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].profKey").value(hasItem(DEFAULT_PROF_KEY.toString())))
            .andExpect(jsonPath("$.[*].profValue").value(hasItem(DEFAULT_PROF_VALUE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getProfSetting() throws Exception {
        // Initialize the database
        profSettingRepository.saveAndFlush(profSetting);

        // Get the profSetting
        restProfSettingMockMvc.perform(get("/api/prof-settings/{id}", profSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profSetting.getId().intValue()))
            .andExpect(jsonPath("$.profKey").value(DEFAULT_PROF_KEY.toString()))
            .andExpect(jsonPath("$.profValue").value(DEFAULT_PROF_VALUE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfSetting() throws Exception {
        // Get the profSetting
        restProfSettingMockMvc.perform(get("/api/prof-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfSetting() throws Exception {
        // Initialize the database
        profSettingRepository.saveAndFlush(profSetting);
        int databaseSizeBeforeUpdate = profSettingRepository.findAll().size();

        // Update the profSetting
        ProfSetting updatedProfSetting = profSettingRepository.findOne(profSetting.getId());
        updatedProfSetting
            .profKey(UPDATED_PROF_KEY)
            .profValue(UPDATED_PROF_VALUE)
            .category(UPDATED_CATEGORY);
        ProfSettingDTO profSettingDTO = profSettingMapper.toDto(updatedProfSetting);

        restProfSettingMockMvc.perform(put("/api/prof-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profSettingDTO)))
            .andExpect(status().isOk());

        // Validate the ProfSetting in the database
        List<ProfSetting> profSettingList = profSettingRepository.findAll();
        assertThat(profSettingList).hasSize(databaseSizeBeforeUpdate);
        ProfSetting testProfSetting = profSettingList.get(profSettingList.size() - 1);
        assertThat(testProfSetting.getProfKey()).isEqualTo(UPDATED_PROF_KEY);
        assertThat(testProfSetting.getProfValue()).isEqualTo(UPDATED_PROF_VALUE);
        assertThat(testProfSetting.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingProfSetting() throws Exception {
        int databaseSizeBeforeUpdate = profSettingRepository.findAll().size();

        // Create the ProfSetting
        ProfSettingDTO profSettingDTO = profSettingMapper.toDto(profSetting);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfSettingMockMvc.perform(put("/api/prof-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfSetting in the database
        List<ProfSetting> profSettingList = profSettingRepository.findAll();
        assertThat(profSettingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfSetting() throws Exception {
        // Initialize the database
        profSettingRepository.saveAndFlush(profSetting);
        int databaseSizeBeforeDelete = profSettingRepository.findAll().size();

        // Get the profSetting
        restProfSettingMockMvc.perform(delete("/api/prof-settings/{id}", profSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProfSetting> profSettingList = profSettingRepository.findAll();
        assertThat(profSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfSetting.class);
        ProfSetting profSetting1 = new ProfSetting();
        profSetting1.setId(1L);
        ProfSetting profSetting2 = new ProfSetting();
        profSetting2.setId(profSetting1.getId());
        assertThat(profSetting1).isEqualTo(profSetting2);
        profSetting2.setId(2L);
        assertThat(profSetting1).isNotEqualTo(profSetting2);
        profSetting1.setId(null);
        assertThat(profSetting1).isNotEqualTo(profSetting2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfSettingDTO.class);
        ProfSettingDTO profSettingDTO1 = new ProfSettingDTO();
        profSettingDTO1.setId(1L);
        ProfSettingDTO profSettingDTO2 = new ProfSettingDTO();
        assertThat(profSettingDTO1).isNotEqualTo(profSettingDTO2);
        profSettingDTO2.setId(profSettingDTO1.getId());
        assertThat(profSettingDTO1).isEqualTo(profSettingDTO2);
        profSettingDTO2.setId(2L);
        assertThat(profSettingDTO1).isNotEqualTo(profSettingDTO2);
        profSettingDTO1.setId(null);
        assertThat(profSettingDTO1).isNotEqualTo(profSettingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profSettingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profSettingMapper.fromId(null)).isNull();
    }
}
