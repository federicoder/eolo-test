package it.maggioli.web.rest;

import it.maggioli.EoloTestApp;
import it.maggioli.domain.Pratica;
import it.maggioli.repository.PraticaRepository;
import it.maggioli.repository.search.PraticaSearchRepository;
import it.maggioli.service.PraticaService;
import it.maggioli.service.dto.PraticaDTO;
import it.maggioli.service.mapper.PraticaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PraticaResource} REST controller.
 */
@SpringBootTest(classes = EoloTestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PraticaResourceIT {

    private static final Integer DEFAULT_ID_PRATICA = 1;
    private static final Integer UPDATED_ID_PRATICA = 2;

    private static final Integer DEFAULT_ID_LIC = 1;
    private static final Integer UPDATED_ID_LIC = 2;

    private static final Integer DEFAULT_TDP = 1;
    private static final Integer UPDATED_TDP = 2;

    private static final Integer DEFAULT_ID_COLLAB = 1;
    private static final Integer UPDATED_ID_COLLAB = 2;

    private static final Integer DEFAULT_ID_CLIENT = 1;
    private static final Integer UPDATED_ID_CLIENT = 2;

    @Autowired
    private PraticaRepository praticaRepository;

    @Autowired
    private PraticaMapper praticaMapper;

    @Autowired
    private PraticaService praticaService;

    /**
     * This repository is mocked in the it.maggioli.repository.search test package.
     *
     * @see it.maggioli.repository.search.PraticaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PraticaSearchRepository mockPraticaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPraticaMockMvc;

    private Pratica pratica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pratica createEntity(EntityManager em) {
        Pratica pratica = new Pratica()
            .idPratica(DEFAULT_ID_PRATICA)
            .idLic(DEFAULT_ID_LIC)
            .tdp(DEFAULT_TDP)
            .idCollab(DEFAULT_ID_COLLAB)
            .idClient(DEFAULT_ID_CLIENT);
        return pratica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pratica createUpdatedEntity(EntityManager em) {
        Pratica pratica = new Pratica()
            .idPratica(UPDATED_ID_PRATICA)
            .idLic(UPDATED_ID_LIC)
            .tdp(UPDATED_TDP)
            .idCollab(UPDATED_ID_COLLAB)
            .idClient(UPDATED_ID_CLIENT);
        return pratica;
    }

    @BeforeEach
    public void initTest() {
        pratica = createEntity(em);
    }

    @Test
    @Transactional
    public void createPratica() throws Exception {
        int databaseSizeBeforeCreate = praticaRepository.findAll().size();

        // Create the Pratica
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);
        restPraticaMockMvc.perform(post("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pratica in the database
        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeCreate + 1);
        Pratica testPratica = praticaList.get(praticaList.size() - 1);
        assertThat(testPratica.getIdPratica()).isEqualTo(DEFAULT_ID_PRATICA);
        assertThat(testPratica.getIdLic()).isEqualTo(DEFAULT_ID_LIC);
        assertThat(testPratica.getTdp()).isEqualTo(DEFAULT_TDP);
        assertThat(testPratica.getIdCollab()).isEqualTo(DEFAULT_ID_COLLAB);
        assertThat(testPratica.getIdClient()).isEqualTo(DEFAULT_ID_CLIENT);

        // Validate the Pratica in Elasticsearch
        verify(mockPraticaSearchRepository, times(1)).save(testPratica);
    }

    @Test
    @Transactional
    public void createPraticaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = praticaRepository.findAll().size();

        // Create the Pratica with an existing ID
        pratica.setId(1L);
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPraticaMockMvc.perform(post("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pratica in the database
        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pratica in Elasticsearch
        verify(mockPraticaSearchRepository, times(0)).save(pratica);
    }


    @Test
    @Transactional
    public void checkIdPraticaIsRequired() throws Exception {
        int databaseSizeBeforeTest = praticaRepository.findAll().size();
        // set the field null
        pratica.setIdPratica(null);

        // Create the Pratica, which fails.
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);

        restPraticaMockMvc.perform(post("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isBadRequest());

        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdLicIsRequired() throws Exception {
        int databaseSizeBeforeTest = praticaRepository.findAll().size();
        // set the field null
        pratica.setIdLic(null);

        // Create the Pratica, which fails.
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);

        restPraticaMockMvc.perform(post("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isBadRequest());

        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTdpIsRequired() throws Exception {
        int databaseSizeBeforeTest = praticaRepository.findAll().size();
        // set the field null
        pratica.setTdp(null);

        // Create the Pratica, which fails.
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);

        restPraticaMockMvc.perform(post("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isBadRequest());

        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = praticaRepository.findAll().size();
        // set the field null
        pratica.setIdClient(null);

        // Create the Pratica, which fails.
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);

        restPraticaMockMvc.perform(post("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isBadRequest());

        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPraticas() throws Exception {
        // Initialize the database
        praticaRepository.saveAndFlush(pratica);

        // Get all the praticaList
        restPraticaMockMvc.perform(get("/api/praticas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pratica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPratica").value(hasItem(DEFAULT_ID_PRATICA)))
            .andExpect(jsonPath("$.[*].idLic").value(hasItem(DEFAULT_ID_LIC)))
            .andExpect(jsonPath("$.[*].tdp").value(hasItem(DEFAULT_TDP)))
            .andExpect(jsonPath("$.[*].idCollab").value(hasItem(DEFAULT_ID_COLLAB)))
            .andExpect(jsonPath("$.[*].idClient").value(hasItem(DEFAULT_ID_CLIENT)));
    }
    
    @Test
    @Transactional
    public void getPratica() throws Exception {
        // Initialize the database
        praticaRepository.saveAndFlush(pratica);

        // Get the pratica
        restPraticaMockMvc.perform(get("/api/praticas/{id}", pratica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pratica.getId().intValue()))
            .andExpect(jsonPath("$.idPratica").value(DEFAULT_ID_PRATICA))
            .andExpect(jsonPath("$.idLic").value(DEFAULT_ID_LIC))
            .andExpect(jsonPath("$.tdp").value(DEFAULT_TDP))
            .andExpect(jsonPath("$.idCollab").value(DEFAULT_ID_COLLAB))
            .andExpect(jsonPath("$.idClient").value(DEFAULT_ID_CLIENT));
    }

    @Test
    @Transactional
    public void getNonExistingPratica() throws Exception {
        // Get the pratica
        restPraticaMockMvc.perform(get("/api/praticas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePratica() throws Exception {
        // Initialize the database
        praticaRepository.saveAndFlush(pratica);

        int databaseSizeBeforeUpdate = praticaRepository.findAll().size();

        // Update the pratica
        Pratica updatedPratica = praticaRepository.findById(pratica.getId()).get();
        // Disconnect from session so that the updates on updatedPratica are not directly saved in db
        em.detach(updatedPratica);
        updatedPratica
            .idPratica(UPDATED_ID_PRATICA)
            .idLic(UPDATED_ID_LIC)
            .tdp(UPDATED_TDP)
            .idCollab(UPDATED_ID_COLLAB)
            .idClient(UPDATED_ID_CLIENT);
        PraticaDTO praticaDTO = praticaMapper.toDto(updatedPratica);

        restPraticaMockMvc.perform(put("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isOk());

        // Validate the Pratica in the database
        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeUpdate);
        Pratica testPratica = praticaList.get(praticaList.size() - 1);
        assertThat(testPratica.getIdPratica()).isEqualTo(UPDATED_ID_PRATICA);
        assertThat(testPratica.getIdLic()).isEqualTo(UPDATED_ID_LIC);
        assertThat(testPratica.getTdp()).isEqualTo(UPDATED_TDP);
        assertThat(testPratica.getIdCollab()).isEqualTo(UPDATED_ID_COLLAB);
        assertThat(testPratica.getIdClient()).isEqualTo(UPDATED_ID_CLIENT);

        // Validate the Pratica in Elasticsearch
        verify(mockPraticaSearchRepository, times(1)).save(testPratica);
    }

    @Test
    @Transactional
    public void updateNonExistingPratica() throws Exception {
        int databaseSizeBeforeUpdate = praticaRepository.findAll().size();

        // Create the Pratica
        PraticaDTO praticaDTO = praticaMapper.toDto(pratica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPraticaMockMvc.perform(put("/api/praticas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(praticaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pratica in the database
        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pratica in Elasticsearch
        verify(mockPraticaSearchRepository, times(0)).save(pratica);
    }

    @Test
    @Transactional
    public void deletePratica() throws Exception {
        // Initialize the database
        praticaRepository.saveAndFlush(pratica);

        int databaseSizeBeforeDelete = praticaRepository.findAll().size();

        // Delete the pratica
        restPraticaMockMvc.perform(delete("/api/praticas/{id}", pratica.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pratica> praticaList = praticaRepository.findAll();
        assertThat(praticaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pratica in Elasticsearch
        verify(mockPraticaSearchRepository, times(1)).deleteById(pratica.getId());
    }

    @Test
    @Transactional
    public void searchPratica() throws Exception {
        // Initialize the database
        praticaRepository.saveAndFlush(pratica);
        when(mockPraticaSearchRepository.search(queryStringQuery("id:" + pratica.getId())))
            .thenReturn(Collections.singletonList(pratica));
        // Search the pratica
        restPraticaMockMvc.perform(get("/api/_search/praticas?query=id:" + pratica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pratica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPratica").value(hasItem(DEFAULT_ID_PRATICA)))
            .andExpect(jsonPath("$.[*].idLic").value(hasItem(DEFAULT_ID_LIC)))
            .andExpect(jsonPath("$.[*].tdp").value(hasItem(DEFAULT_TDP)))
            .andExpect(jsonPath("$.[*].idCollab").value(hasItem(DEFAULT_ID_COLLAB)))
            .andExpect(jsonPath("$.[*].idClient").value(hasItem(DEFAULT_ID_CLIENT)));
    }
}
