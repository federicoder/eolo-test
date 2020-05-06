package it.maggioli.web.rest;

import it.maggioli.EoloTestApp;
import it.maggioli.domain.Licenza;
import it.maggioli.repository.LicenzaRepository;
import it.maggioli.repository.search.LicenzaSearchRepository;
import it.maggioli.service.LicenzaService;
import it.maggioli.service.dto.LicenzaDTO;
import it.maggioli.service.mapper.LicenzaMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LicenzaResource} REST controller.
 */
@SpringBootTest(classes = EoloTestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LicenzaResourceIT {

    private static final Integer DEFAULT_ID_LICENZA = 1;
    private static final Integer UPDATED_ID_LICENZA = 2;

    private static final String DEFAULT_TIPOLOGIA = "AAAAAAAAAA";
    private static final String UPDATED_TIPOLOGIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_SCADENZA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_SCADENZA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LicenzaRepository licenzaRepository;

    @Autowired
    private LicenzaMapper licenzaMapper;

    @Autowired
    private LicenzaService licenzaService;

    /**
     * This repository is mocked in the it.maggioli.repository.search test package.
     *
     * @see it.maggioli.repository.search.LicenzaSearchRepositoryMockConfiguration
     */
    @Autowired
    private LicenzaSearchRepository mockLicenzaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenzaMockMvc;

    private Licenza licenza;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licenza createEntity(EntityManager em) {
        Licenza licenza = new Licenza()
            .idLicenza(DEFAULT_ID_LICENZA)
            .tipologia(DEFAULT_TIPOLOGIA)
            .descrizione(DEFAULT_DESCRIZIONE)
            .dataScadenza(DEFAULT_DATA_SCADENZA);
        return licenza;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licenza createUpdatedEntity(EntityManager em) {
        Licenza licenza = new Licenza()
            .idLicenza(UPDATED_ID_LICENZA)
            .tipologia(UPDATED_TIPOLOGIA)
            .descrizione(UPDATED_DESCRIZIONE)
            .dataScadenza(UPDATED_DATA_SCADENZA);
        return licenza;
    }

    @BeforeEach
    public void initTest() {
        licenza = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicenza() throws Exception {
        int databaseSizeBeforeCreate = licenzaRepository.findAll().size();

        // Create the Licenza
        LicenzaDTO licenzaDTO = licenzaMapper.toDto(licenza);
        restLicenzaMockMvc.perform(post("/api/licenzas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenzaDTO)))
            .andExpect(status().isCreated());

        // Validate the Licenza in the database
        List<Licenza> licenzaList = licenzaRepository.findAll();
        assertThat(licenzaList).hasSize(databaseSizeBeforeCreate + 1);
        Licenza testLicenza = licenzaList.get(licenzaList.size() - 1);
        assertThat(testLicenza.getIdLicenza()).isEqualTo(DEFAULT_ID_LICENZA);
        assertThat(testLicenza.getTipologia()).isEqualTo(DEFAULT_TIPOLOGIA);
        assertThat(testLicenza.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testLicenza.getDataScadenza()).isEqualTo(DEFAULT_DATA_SCADENZA);

        // Validate the Licenza in Elasticsearch
        verify(mockLicenzaSearchRepository, times(1)).save(testLicenza);
    }

    @Test
    @Transactional
    public void createLicenzaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenzaRepository.findAll().size();

        // Create the Licenza with an existing ID
        licenza.setId(1L);
        LicenzaDTO licenzaDTO = licenzaMapper.toDto(licenza);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenzaMockMvc.perform(post("/api/licenzas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Licenza in the database
        List<Licenza> licenzaList = licenzaRepository.findAll();
        assertThat(licenzaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Licenza in Elasticsearch
        verify(mockLicenzaSearchRepository, times(0)).save(licenza);
    }


    @Test
    @Transactional
    public void checkIdLicenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenzaRepository.findAll().size();
        // set the field null
        licenza.setIdLicenza(null);

        // Create the Licenza, which fails.
        LicenzaDTO licenzaDTO = licenzaMapper.toDto(licenza);

        restLicenzaMockMvc.perform(post("/api/licenzas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenzaDTO)))
            .andExpect(status().isBadRequest());

        List<Licenza> licenzaList = licenzaRepository.findAll();
        assertThat(licenzaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLicenzas() throws Exception {
        // Initialize the database
        licenzaRepository.saveAndFlush(licenza);

        // Get all the licenzaList
        restLicenzaMockMvc.perform(get("/api/licenzas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenza.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLicenza").value(hasItem(DEFAULT_ID_LICENZA)))
            .andExpect(jsonPath("$.[*].tipologia").value(hasItem(DEFAULT_TIPOLOGIA)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].dataScadenza").value(hasItem(DEFAULT_DATA_SCADENZA.toString())));
    }
    
    @Test
    @Transactional
    public void getLicenza() throws Exception {
        // Initialize the database
        licenzaRepository.saveAndFlush(licenza);

        // Get the licenza
        restLicenzaMockMvc.perform(get("/api/licenzas/{id}", licenza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licenza.getId().intValue()))
            .andExpect(jsonPath("$.idLicenza").value(DEFAULT_ID_LICENZA))
            .andExpect(jsonPath("$.tipologia").value(DEFAULT_TIPOLOGIA))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE))
            .andExpect(jsonPath("$.dataScadenza").value(DEFAULT_DATA_SCADENZA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLicenza() throws Exception {
        // Get the licenza
        restLicenzaMockMvc.perform(get("/api/licenzas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicenza() throws Exception {
        // Initialize the database
        licenzaRepository.saveAndFlush(licenza);

        int databaseSizeBeforeUpdate = licenzaRepository.findAll().size();

        // Update the licenza
        Licenza updatedLicenza = licenzaRepository.findById(licenza.getId()).get();
        // Disconnect from session so that the updates on updatedLicenza are not directly saved in db
        em.detach(updatedLicenza);
        updatedLicenza
            .idLicenza(UPDATED_ID_LICENZA)
            .tipologia(UPDATED_TIPOLOGIA)
            .descrizione(UPDATED_DESCRIZIONE)
            .dataScadenza(UPDATED_DATA_SCADENZA);
        LicenzaDTO licenzaDTO = licenzaMapper.toDto(updatedLicenza);

        restLicenzaMockMvc.perform(put("/api/licenzas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenzaDTO)))
            .andExpect(status().isOk());

        // Validate the Licenza in the database
        List<Licenza> licenzaList = licenzaRepository.findAll();
        assertThat(licenzaList).hasSize(databaseSizeBeforeUpdate);
        Licenza testLicenza = licenzaList.get(licenzaList.size() - 1);
        assertThat(testLicenza.getIdLicenza()).isEqualTo(UPDATED_ID_LICENZA);
        assertThat(testLicenza.getTipologia()).isEqualTo(UPDATED_TIPOLOGIA);
        assertThat(testLicenza.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testLicenza.getDataScadenza()).isEqualTo(UPDATED_DATA_SCADENZA);

        // Validate the Licenza in Elasticsearch
        verify(mockLicenzaSearchRepository, times(1)).save(testLicenza);
    }

    @Test
    @Transactional
    public void updateNonExistingLicenza() throws Exception {
        int databaseSizeBeforeUpdate = licenzaRepository.findAll().size();

        // Create the Licenza
        LicenzaDTO licenzaDTO = licenzaMapper.toDto(licenza);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenzaMockMvc.perform(put("/api/licenzas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Licenza in the database
        List<Licenza> licenzaList = licenzaRepository.findAll();
        assertThat(licenzaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Licenza in Elasticsearch
        verify(mockLicenzaSearchRepository, times(0)).save(licenza);
    }

    @Test
    @Transactional
    public void deleteLicenza() throws Exception {
        // Initialize the database
        licenzaRepository.saveAndFlush(licenza);

        int databaseSizeBeforeDelete = licenzaRepository.findAll().size();

        // Delete the licenza
        restLicenzaMockMvc.perform(delete("/api/licenzas/{id}", licenza.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Licenza> licenzaList = licenzaRepository.findAll();
        assertThat(licenzaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Licenza in Elasticsearch
        verify(mockLicenzaSearchRepository, times(1)).deleteById(licenza.getId());
    }

    @Test
    @Transactional
    public void searchLicenza() throws Exception {
        // Initialize the database
        licenzaRepository.saveAndFlush(licenza);
        when(mockLicenzaSearchRepository.search(queryStringQuery("id:" + licenza.getId())))
            .thenReturn(Collections.singletonList(licenza));
        // Search the licenza
        restLicenzaMockMvc.perform(get("/api/_search/licenzas?query=id:" + licenza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenza.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLicenza").value(hasItem(DEFAULT_ID_LICENZA)))
            .andExpect(jsonPath("$.[*].tipologia").value(hasItem(DEFAULT_TIPOLOGIA)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].dataScadenza").value(hasItem(DEFAULT_DATA_SCADENZA.toString())));
    }
}
