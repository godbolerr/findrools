package com.work.findrools.web.rest;

import com.work.findrools.FindroolsApp;

import com.work.findrools.domain.RiskProfile;
import com.work.findrools.repository.RiskProfileRepository;
import com.work.findrools.service.RiskProfileService;
import com.work.findrools.service.dto.RiskProfileDTO;
import com.work.findrools.service.mapper.RiskProfileMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.work.findrools.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RiskProfileResource REST controller.
 *
 * @see RiskProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FindroolsApp.class)
public class RiskProfileResourceIntTest {

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final String DEFAULT_RISK_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RISK_DATA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PROFILE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PROFILE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_FIN_SCORE = 1;
    private static final Integer UPDATED_FIN_SCORE = 2;

    private static final BigDecimal DEFAULT_WEALTH_PROJECTED = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEALTH_PROJECTED = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WEALTH_ACTUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEALTH_ACTUAL = new BigDecimal(2);

    private static final Double DEFAULT_SOCIAL_SCORE = 1D;
    private static final Double UPDATED_SOCIAL_SCORE = 2D;

    @Autowired
    private RiskProfileRepository riskProfileRepository;

    @Autowired
    private RiskProfileMapper riskProfileMapper;

    @Autowired
    private RiskProfileService riskProfileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRiskProfileMockMvc;

    private RiskProfile riskProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RiskProfileResource riskProfileResource = new RiskProfileResource(riskProfileService);
        this.restRiskProfileMockMvc = MockMvcBuilders.standaloneSetup(riskProfileResource)
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
    public static RiskProfile createEntity(EntityManager em) {
        RiskProfile riskProfile = new RiskProfile()
            .personId(DEFAULT_PERSON_ID)
            .riskData(DEFAULT_RISK_DATA)
            .profileDate(DEFAULT_PROFILE_DATE)
            .finScore(DEFAULT_FIN_SCORE)
            .wealthProjected(DEFAULT_WEALTH_PROJECTED)
            .wealthActual(DEFAULT_WEALTH_ACTUAL)
            .socialScore(DEFAULT_SOCIAL_SCORE);
        return riskProfile;
    }

    @Before
    public void initTest() {
        riskProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createRiskProfile() throws Exception {
        int databaseSizeBeforeCreate = riskProfileRepository.findAll().size();

        // Create the RiskProfile
        RiskProfileDTO riskProfileDTO = riskProfileMapper.toDto(riskProfile);
        restRiskProfileMockMvc.perform(post("/api/risk-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeCreate + 1);
        RiskProfile testRiskProfile = riskProfileList.get(riskProfileList.size() - 1);
        assertThat(testRiskProfile.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testRiskProfile.getRiskData()).isEqualTo(DEFAULT_RISK_DATA);
        assertThat(testRiskProfile.getProfileDate()).isEqualTo(DEFAULT_PROFILE_DATE);
        assertThat(testRiskProfile.getFinScore()).isEqualTo(DEFAULT_FIN_SCORE);
        assertThat(testRiskProfile.getWealthProjected()).isEqualTo(DEFAULT_WEALTH_PROJECTED);
        assertThat(testRiskProfile.getWealthActual()).isEqualTo(DEFAULT_WEALTH_ACTUAL);
        assertThat(testRiskProfile.getSocialScore()).isEqualTo(DEFAULT_SOCIAL_SCORE);
    }

    @Test
    @Transactional
    public void createRiskProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskProfileRepository.findAll().size();

        // Create the RiskProfile with an existing ID
        riskProfile.setId(1L);
        RiskProfileDTO riskProfileDTO = riskProfileMapper.toDto(riskProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskProfileMockMvc.perform(post("/api/risk-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRiskProfiles() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        // Get all the riskProfileList
        restRiskProfileMockMvc.perform(get("/api/risk-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].riskData").value(hasItem(DEFAULT_RISK_DATA.toString())))
            .andExpect(jsonPath("$.[*].profileDate").value(hasItem(sameInstant(DEFAULT_PROFILE_DATE))))
            .andExpect(jsonPath("$.[*].finScore").value(hasItem(DEFAULT_FIN_SCORE)))
            .andExpect(jsonPath("$.[*].wealthProjected").value(hasItem(DEFAULT_WEALTH_PROJECTED.intValue())))
            .andExpect(jsonPath("$.[*].wealthActual").value(hasItem(DEFAULT_WEALTH_ACTUAL.intValue())))
            .andExpect(jsonPath("$.[*].socialScore").value(hasItem(DEFAULT_SOCIAL_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    public void getRiskProfile() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        // Get the riskProfile
        restRiskProfileMockMvc.perform(get("/api/risk-profiles/{id}", riskProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(riskProfile.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.riskData").value(DEFAULT_RISK_DATA.toString()))
            .andExpect(jsonPath("$.profileDate").value(sameInstant(DEFAULT_PROFILE_DATE)))
            .andExpect(jsonPath("$.finScore").value(DEFAULT_FIN_SCORE))
            .andExpect(jsonPath("$.wealthProjected").value(DEFAULT_WEALTH_PROJECTED.intValue()))
            .andExpect(jsonPath("$.wealthActual").value(DEFAULT_WEALTH_ACTUAL.intValue()))
            .andExpect(jsonPath("$.socialScore").value(DEFAULT_SOCIAL_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRiskProfile() throws Exception {
        // Get the riskProfile
        restRiskProfileMockMvc.perform(get("/api/risk-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRiskProfile() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();

        // Update the riskProfile
        RiskProfile updatedRiskProfile = riskProfileRepository.findOne(riskProfile.getId());
        updatedRiskProfile
            .personId(UPDATED_PERSON_ID)
            .riskData(UPDATED_RISK_DATA)
            .profileDate(UPDATED_PROFILE_DATE)
            .finScore(UPDATED_FIN_SCORE)
            .wealthProjected(UPDATED_WEALTH_PROJECTED)
            .wealthActual(UPDATED_WEALTH_ACTUAL)
            .socialScore(UPDATED_SOCIAL_SCORE);
        RiskProfileDTO riskProfileDTO = riskProfileMapper.toDto(updatedRiskProfile);

        restRiskProfileMockMvc.perform(put("/api/risk-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskProfileDTO)))
            .andExpect(status().isOk());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
        RiskProfile testRiskProfile = riskProfileList.get(riskProfileList.size() - 1);
        assertThat(testRiskProfile.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testRiskProfile.getRiskData()).isEqualTo(UPDATED_RISK_DATA);
        assertThat(testRiskProfile.getProfileDate()).isEqualTo(UPDATED_PROFILE_DATE);
        assertThat(testRiskProfile.getFinScore()).isEqualTo(UPDATED_FIN_SCORE);
        assertThat(testRiskProfile.getWealthProjected()).isEqualTo(UPDATED_WEALTH_PROJECTED);
        assertThat(testRiskProfile.getWealthActual()).isEqualTo(UPDATED_WEALTH_ACTUAL);
        assertThat(testRiskProfile.getSocialScore()).isEqualTo(UPDATED_SOCIAL_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();

        // Create the RiskProfile
        RiskProfileDTO riskProfileDTO = riskProfileMapper.toDto(riskProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRiskProfileMockMvc.perform(put("/api/risk-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRiskProfile() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);
        int databaseSizeBeforeDelete = riskProfileRepository.findAll().size();

        // Get the riskProfile
        restRiskProfileMockMvc.perform(delete("/api/risk-profiles/{id}", riskProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskProfile.class);
        RiskProfile riskProfile1 = new RiskProfile();
        riskProfile1.setId(1L);
        RiskProfile riskProfile2 = new RiskProfile();
        riskProfile2.setId(riskProfile1.getId());
        assertThat(riskProfile1).isEqualTo(riskProfile2);
        riskProfile2.setId(2L);
        assertThat(riskProfile1).isNotEqualTo(riskProfile2);
        riskProfile1.setId(null);
        assertThat(riskProfile1).isNotEqualTo(riskProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskProfileDTO.class);
        RiskProfileDTO riskProfileDTO1 = new RiskProfileDTO();
        riskProfileDTO1.setId(1L);
        RiskProfileDTO riskProfileDTO2 = new RiskProfileDTO();
        assertThat(riskProfileDTO1).isNotEqualTo(riskProfileDTO2);
        riskProfileDTO2.setId(riskProfileDTO1.getId());
        assertThat(riskProfileDTO1).isEqualTo(riskProfileDTO2);
        riskProfileDTO2.setId(2L);
        assertThat(riskProfileDTO1).isNotEqualTo(riskProfileDTO2);
        riskProfileDTO1.setId(null);
        assertThat(riskProfileDTO1).isNotEqualTo(riskProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(riskProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(riskProfileMapper.fromId(null)).isNull();
    }
}
