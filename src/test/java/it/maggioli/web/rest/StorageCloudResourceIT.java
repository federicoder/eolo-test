package it.maggioli.web.rest;

import it.maggioli.EoloTestApp;
import it.maggioli.domain.StorageCloud;
import it.maggioli.repository.StorageCloudRepository;
import it.maggioli.repository.search.StorageCloudSearchRepository;
import it.maggioli.service.StorageCloudService;
import it.maggioli.service.dto.StorageCloudDTO;
import it.maggioli.service.mapper.StorageCloudMapper;

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
 * Integration tests for the {@link StorageCloudResource} REST controller.
 */
@SpringBootTest(classes = EoloTestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StorageCloudResourceIT {

    private static final Integer DEFAULT_ID_UTENTE = 1;
    private static final Integer UPDATED_ID_UTENTE = 2;

    private static final Integer DEFAULT_ID_LICENZA = 1;
    private static final Integer UPDATED_ID_LICENZA = 2;

    private static final String DEFAULT_PIANO_BASE = "AAAAAAAAAA";
    private static final String UPDATED_PIANO_BASE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CESSIONE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CESSIONE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StorageCloudRepository storageCloudRepository;

    @Autowired
    private StorageCloudMapper storageCloudMapper;

    @Autowired
    private StorageCloudService storageCloudService;

    /**
     * This repository is mocked in the it.maggioli.repository.search test package.
     *
     * @see it.maggioli.repository.search.StorageCloudSearchRepositoryMockConfiguration
     */
    @Autowired
    private StorageCloudSearchRepository mockStorageCloudSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStorageCloudMockMvc;

    private StorageCloud storageCloud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StorageCloud createEntity(EntityManager em) {
        StorageCloud storageCloud = new StorageCloud()
            .idUtente(DEFAULT_ID_UTENTE)
            .idLicenza(DEFAULT_ID_LICENZA)
            .pianoBase(DEFAULT_PIANO_BASE)
            .dataCessione(DEFAULT_DATA_CESSIONE);
        return storageCloud;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StorageCloud createUpdatedEntity(EntityManager em) {
        StorageCloud storageCloud = new StorageCloud()
            .idUtente(UPDATED_ID_UTENTE)
            .idLicenza(UPDATED_ID_LICENZA)
            .pianoBase(UPDATED_PIANO_BASE)
            .dataCessione(UPDATED_DATA_CESSIONE);
        return storageCloud;
    }

    @BeforeEach
    public void initTest() {
        storageCloud = createEntity(em);
    }

    @Test
    @Transactional
    public void createStorageCloud() throws Exception {
        int databaseSizeBeforeCreate = storageCloudRepository.findAll().size();

        // Create the StorageCloud
        StorageCloudDTO storageCloudDTO = storageCloudMapper.toDto(storageCloud);
        restStorageCloudMockMvc.perform(post("/api/storage-clouds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storageCloudDTO)))
            .andExpect(status().isCreated());

        // Validate the StorageCloud in the database
        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeCreate + 1);
        StorageCloud testStorageCloud = storageCloudList.get(storageCloudList.size() - 1);
        assertThat(testStorageCloud.getIdUtente()).isEqualTo(DEFAULT_ID_UTENTE);
        assertThat(testStorageCloud.getIdLicenza()).isEqualTo(DEFAULT_ID_LICENZA);
        assertThat(testStorageCloud.getPianoBase()).isEqualTo(DEFAULT_PIANO_BASE);
        assertThat(testStorageCloud.getDataCessione()).isEqualTo(DEFAULT_DATA_CESSIONE);

        // Validate the StorageCloud in Elasticsearch
        verify(mockStorageCloudSearchRepository, times(1)).save(testStorageCloud);
    }

    @Test
    @Transactional
    public void createStorageCloudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storageCloudRepository.findAll().size();

        // Create the StorageCloud with an existing ID
        storageCloud.setId(1L);
        StorageCloudDTO storageCloudDTO = storageCloudMapper.toDto(storageCloud);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorageCloudMockMvc.perform(post("/api/storage-clouds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storageCloudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StorageCloud in the database
        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeCreate);

        // Validate the StorageCloud in Elasticsearch
        verify(mockStorageCloudSearchRepository, times(0)).save(storageCloud);
    }


    @Test
    @Transactional
    public void checkIdUtenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCloudRepository.findAll().size();
        // set the field null
        storageCloud.setIdUtente(null);

        // Create the StorageCloud, which fails.
        StorageCloudDTO storageCloudDTO = storageCloudMapper.toDto(storageCloud);

        restStorageCloudMockMvc.perform(post("/api/storage-clouds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storageCloudDTO)))
            .andExpect(status().isBadRequest());

        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdLicenzaIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCloudRepository.findAll().size();
        // set the field null
        storageCloud.setIdLicenza(null);

        // Create the StorageCloud, which fails.
        StorageCloudDTO storageCloudDTO = storageCloudMapper.toDto(storageCloud);

        restStorageCloudMockMvc.perform(post("/api/storage-clouds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storageCloudDTO)))
            .andExpect(status().isBadRequest());

        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStorageClouds() throws Exception {
        // Initialize the database
        storageCloudRepository.saveAndFlush(storageCloud);

        // Get all the storageCloudList
        restStorageCloudMockMvc.perform(get("/api/storage-clouds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageCloud.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUtente").value(hasItem(DEFAULT_ID_UTENTE)))
            .andExpect(jsonPath("$.[*].idLicenza").value(hasItem(DEFAULT_ID_LICENZA)))
            .andExpect(jsonPath("$.[*].pianoBase").value(hasItem(DEFAULT_PIANO_BASE)))
            .andExpect(jsonPath("$.[*].dataCessione").value(hasItem(DEFAULT_DATA_CESSIONE.toString())));
    }
    
    @Test
    @Transactional
    public void getStorageCloud() throws Exception {
        // Initialize the database
        storageCloudRepository.saveAndFlush(storageCloud);

        // Get the storageCloud
        restStorageCloudMockMvc.perform(get("/api/storage-clouds/{id}", storageCloud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storageCloud.getId().intValue()))
            .andExpect(jsonPath("$.idUtente").value(DEFAULT_ID_UTENTE))
            .andExpect(jsonPath("$.idLicenza").value(DEFAULT_ID_LICENZA))
            .andExpect(jsonPath("$.pianoBase").value(DEFAULT_PIANO_BASE))
            .andExpect(jsonPath("$.dataCessione").value(DEFAULT_DATA_CESSIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStorageCloud() throws Exception {
        // Get the storageCloud
        restStorageCloudMockMvc.perform(get("/api/storage-clouds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStorageCloud() throws Exception {
        // Initialize the database
        storageCloudRepository.saveAndFlush(storageCloud);

        int databaseSizeBeforeUpdate = storageCloudRepository.findAll().size();

        // Update the storageCloud
        StorageCloud updatedStorageCloud = storageCloudRepository.findById(storageCloud.getId()).get();
        // Disconnect from session so that the updates on updatedStorageCloud are not directly saved in db
        em.detach(updatedStorageCloud);
        updatedStorageCloud
            .idUtente(UPDATED_ID_UTENTE)
            .idLicenza(UPDATED_ID_LICENZA)
            .pianoBase(UPDATED_PIANO_BASE)
            .dataCessione(UPDATED_DATA_CESSIONE);
        StorageCloudDTO storageCloudDTO = storageCloudMapper.toDto(updatedStorageCloud);

        restStorageCloudMockMvc.perform(put("/api/storage-clouds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storageCloudDTO)))
            .andExpect(status().isOk());

        // Validate the StorageCloud in the database
        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeUpdate);
        StorageCloud testStorageCloud = storageCloudList.get(storageCloudList.size() - 1);
        assertThat(testStorageCloud.getIdUtente()).isEqualTo(UPDATED_ID_UTENTE);
        assertThat(testStorageCloud.getIdLicenza()).isEqualTo(UPDATED_ID_LICENZA);
        assertThat(testStorageCloud.getPianoBase()).isEqualTo(UPDATED_PIANO_BASE);
        assertThat(testStorageCloud.getDataCessione()).isEqualTo(UPDATED_DATA_CESSIONE);

        // Validate the StorageCloud in Elasticsearch
        verify(mockStorageCloudSearchRepository, times(1)).save(testStorageCloud);
    }

    @Test
    @Transactional
    public void updateNonExistingStorageCloud() throws Exception {
        int databaseSizeBeforeUpdate = storageCloudRepository.findAll().size();

        // Create the StorageCloud
        StorageCloudDTO storageCloudDTO = storageCloudMapper.toDto(storageCloud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageCloudMockMvc.perform(put("/api/storage-clouds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(storageCloudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StorageCloud in the database
        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StorageCloud in Elasticsearch
        verify(mockStorageCloudSearchRepository, times(0)).save(storageCloud);
    }

    @Test
    @Transactional
    public void deleteStorageCloud() throws Exception {
        // Initialize the database
        storageCloudRepository.saveAndFlush(storageCloud);

        int databaseSizeBeforeDelete = storageCloudRepository.findAll().size();

        // Delete the storageCloud
        restStorageCloudMockMvc.perform(delete("/api/storage-clouds/{id}", storageCloud.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StorageCloud> storageCloudList = storageCloudRepository.findAll();
        assertThat(storageCloudList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StorageCloud in Elasticsearch
        verify(mockStorageCloudSearchRepository, times(1)).deleteById(storageCloud.getId());
    }

    @Test
    @Transactional
    public void searchStorageCloud() throws Exception {
        // Initialize the database
        storageCloudRepository.saveAndFlush(storageCloud);
        when(mockStorageCloudSearchRepository.search(queryStringQuery("id:" + storageCloud.getId())))
            .thenReturn(Collections.singletonList(storageCloud));
        // Search the storageCloud
        restStorageCloudMockMvc.perform(get("/api/_search/storage-clouds?query=id:" + storageCloud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageCloud.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUtente").value(hasItem(DEFAULT_ID_UTENTE)))
            .andExpect(jsonPath("$.[*].idLicenza").value(hasItem(DEFAULT_ID_LICENZA)))
            .andExpect(jsonPath("$.[*].pianoBase").value(hasItem(DEFAULT_PIANO_BASE)))
            .andExpect(jsonPath("$.[*].dataCessione").value(hasItem(DEFAULT_DATA_CESSIONE.toString())));
    }
}
