package it.maggioli.web.rest;

import it.maggioli.EoloTestApp;
import it.maggioli.domain.Professionista;
import it.maggioli.repository.ProfessionistaRepository;
import it.maggioli.repository.search.ProfessionistaSearchRepository;
import it.maggioli.service.ProfessionistaService;
import it.maggioli.service.dto.ProfessionistaDTO;
import it.maggioli.service.mapper.ProfessionistaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
 * Integration tests for the {@link ProfessionistaResource} REST controller.
 */
@SpringBootTest(classes = EoloTestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfessionistaResourceIT {

    private static final Integer DEFAULT_ID_PROFESSIONISTA = 1;
    private static final Integer UPDATED_ID_PROFESSIONISTA = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_TIPOLOGIA = "AAAAAAAAAA";
    private static final String UPDATED_TIPOLOGIA = "BBBBBBBBBB";

    private static final String DEFAULT_CODICE_FISCALE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_FISCALE = "BBBBBBBBBB";

    private static final String DEFAULT_P_IVA = "AAAAAAAAAA";
    private static final String UPDATED_P_IVA = "BBBBBBBBBB";

    private static final String DEFAULT_STUDIO_ASSOCIATO = "AAAAAAAAAA";
    private static final String UPDATED_STUDIO_ASSOCIATO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_LICENZA = 1;
    private static final Integer UPDATED_ID_LICENZA = 2;

    @Autowired
    private ProfessionistaRepository professionistaRepository;

    @Autowired
    private ProfessionistaMapper professionistaMapper;

    @Autowired
    private ProfessionistaService professionistaService;

    /**
     * This repository is mocked in the it.maggioli.repository.search test package.
     *
     * @see it.maggioli.repository.search.ProfessionistaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProfessionistaSearchRepository mockProfessionistaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfessionistaMockMvc;

    private Professionista professionista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professionista createEntity(EntityManager em) {
        Professionista professionista = new Professionista()
            .idProfessionista(DEFAULT_ID_PROFESSIONISTA)
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .tipologia(DEFAULT_TIPOLOGIA)
            .codiceFiscale(DEFAULT_CODICE_FISCALE)
            .pIva(DEFAULT_P_IVA)
            .studioAssociato(DEFAULT_STUDIO_ASSOCIATO)
            .idLicenza(DEFAULT_ID_LICENZA);
        return professionista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professionista createUpdatedEntity(EntityManager em) {
        Professionista professionista = new Professionista()
            .idProfessionista(UPDATED_ID_PROFESSIONISTA)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .tipologia(UPDATED_TIPOLOGIA)
            .codiceFiscale(UPDATED_CODICE_FISCALE)
            .pIva(UPDATED_P_IVA)
            .studioAssociato(UPDATED_STUDIO_ASSOCIATO)
            .idLicenza(UPDATED_ID_LICENZA);
        return professionista;
    }

    @BeforeEach
    public void initTest() {
        professionista = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionista() throws Exception {
        int databaseSizeBeforeCreate = professionistaRepository.findAll().size();

        // Create the Professionista
        ProfessionistaDTO professionistaDTO = professionistaMapper.toDto(professionista);
        restProfessionistaMockMvc.perform(post("/api/professionistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionistaDTO)))
            .andExpect(status().isCreated());

        // Validate the Professionista in the database
        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeCreate + 1);
        Professionista testProfessionista = professionistaList.get(professionistaList.size() - 1);
        assertThat(testProfessionista.getIdProfessionista()).isEqualTo(DEFAULT_ID_PROFESSIONISTA);
        assertThat(testProfessionista.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProfessionista.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testProfessionista.getTipologia()).isEqualTo(DEFAULT_TIPOLOGIA);
        assertThat(testProfessionista.getCodiceFiscale()).isEqualTo(DEFAULT_CODICE_FISCALE);
        assertThat(testProfessionista.getpIva()).isEqualTo(DEFAULT_P_IVA);
        assertThat(testProfessionista.getStudioAssociato()).isEqualTo(DEFAULT_STUDIO_ASSOCIATO);
        assertThat(testProfessionista.getIdLicenza()).isEqualTo(DEFAULT_ID_LICENZA);

        // Validate the Professionista in Elasticsearch
        verify(mockProfessionistaSearchRepository, times(1)).save(testProfessionista);
    }

    @Test
    @Transactional
    public void createProfessionistaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionistaRepository.findAll().size();

        // Create the Professionista with an existing ID
        professionista.setId(1L);
        ProfessionistaDTO professionistaDTO = professionistaMapper.toDto(professionista);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionistaMockMvc.perform(post("/api/professionistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Professionista in the database
        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Professionista in Elasticsearch
        verify(mockProfessionistaSearchRepository, times(0)).save(professionista);
    }


    @Test
    @Transactional
    public void checkIdProfessionistaIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionistaRepository.findAll().size();
        // set the field null
        professionista.setIdProfessionista(null);

        // Create the Professionista, which fails.
        ProfessionistaDTO professionistaDTO = professionistaMapper.toDto(professionista);

        restProfessionistaMockMvc.perform(post("/api/professionistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionistaDTO)))
            .andExpect(status().isBadRequest());

        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodiceFiscaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionistaRepository.findAll().size();
        // set the field null
        professionista.setCodiceFiscale(null);

        // Create the Professionista, which fails.
        ProfessionistaDTO professionistaDTO = professionistaMapper.toDto(professionista);

        restProfessionistaMockMvc.perform(post("/api/professionistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionistaDTO)))
            .andExpect(status().isBadRequest());

        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfessionistas() throws Exception {
        // Initialize the database
        professionistaRepository.saveAndFlush(professionista);

        // Get all the professionistaList
        restProfessionistaMockMvc.perform(get("/api/professionistas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionista.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProfessionista").value(hasItem(DEFAULT_ID_PROFESSIONISTA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME)))
            .andExpect(jsonPath("$.[*].tipologia").value(hasItem(DEFAULT_TIPOLOGIA)))
            .andExpect(jsonPath("$.[*].codiceFiscale").value(hasItem(DEFAULT_CODICE_FISCALE)))
            .andExpect(jsonPath("$.[*].pIva").value(hasItem(DEFAULT_P_IVA)))
            .andExpect(jsonPath("$.[*].studioAssociato").value(hasItem(DEFAULT_STUDIO_ASSOCIATO)))
            .andExpect(jsonPath("$.[*].idLicenza").value(hasItem(DEFAULT_ID_LICENZA)));
    }
    
    @Test
    @Transactional
    public void getProfessionista() throws Exception {
        // Initialize the database
        professionistaRepository.saveAndFlush(professionista);

        // Get the professionista
        restProfessionistaMockMvc.perform(get("/api/professionistas/{id}", professionista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(professionista.getId().intValue()))
            .andExpect(jsonPath("$.idProfessionista").value(DEFAULT_ID_PROFESSIONISTA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME))
            .andExpect(jsonPath("$.tipologia").value(DEFAULT_TIPOLOGIA))
            .andExpect(jsonPath("$.codiceFiscale").value(DEFAULT_CODICE_FISCALE))
            .andExpect(jsonPath("$.pIva").value(DEFAULT_P_IVA))
            .andExpect(jsonPath("$.studioAssociato").value(DEFAULT_STUDIO_ASSOCIATO))
            .andExpect(jsonPath("$.idLicenza").value(DEFAULT_ID_LICENZA));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionista() throws Exception {
        // Get the professionista
        restProfessionistaMockMvc.perform(get("/api/professionistas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionista() throws Exception {
        // Initialize the database
        professionistaRepository.saveAndFlush(professionista);

        int databaseSizeBeforeUpdate = professionistaRepository.findAll().size();

        // Update the professionista
        Professionista updatedProfessionista = professionistaRepository.findById(professionista.getId()).get();
        // Disconnect from session so that the updates on updatedProfessionista are not directly saved in db
        em.detach(updatedProfessionista);
        updatedProfessionista
            .idProfessionista(UPDATED_ID_PROFESSIONISTA)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .tipologia(UPDATED_TIPOLOGIA)
            .codiceFiscale(UPDATED_CODICE_FISCALE)
            .pIva(UPDATED_P_IVA)
            .studioAssociato(UPDATED_STUDIO_ASSOCIATO)
            .idLicenza(UPDATED_ID_LICENZA);
        ProfessionistaDTO professionistaDTO = professionistaMapper.toDto(updatedProfessionista);

        restProfessionistaMockMvc.perform(put("/api/professionistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionistaDTO)))
            .andExpect(status().isOk());

        // Validate the Professionista in the database
        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeUpdate);
        Professionista testProfessionista = professionistaList.get(professionistaList.size() - 1);
        assertThat(testProfessionista.getIdProfessionista()).isEqualTo(UPDATED_ID_PROFESSIONISTA);
        assertThat(testProfessionista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProfessionista.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testProfessionista.getTipologia()).isEqualTo(UPDATED_TIPOLOGIA);
        assertThat(testProfessionista.getCodiceFiscale()).isEqualTo(UPDATED_CODICE_FISCALE);
        assertThat(testProfessionista.getpIva()).isEqualTo(UPDATED_P_IVA);
        assertThat(testProfessionista.getStudioAssociato()).isEqualTo(UPDATED_STUDIO_ASSOCIATO);
        assertThat(testProfessionista.getIdLicenza()).isEqualTo(UPDATED_ID_LICENZA);

        // Validate the Professionista in Elasticsearch
        verify(mockProfessionistaSearchRepository, times(1)).save(testProfessionista);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionista() throws Exception {
        int databaseSizeBeforeUpdate = professionistaRepository.findAll().size();

        // Create the Professionista
        ProfessionistaDTO professionistaDTO = professionistaMapper.toDto(professionista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionistaMockMvc.perform(put("/api/professionistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(professionistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Professionista in the database
        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Professionista in Elasticsearch
        verify(mockProfessionistaSearchRepository, times(0)).save(professionista);
    }

    @Test
    @Transactional
    public void deleteProfessionista() throws Exception {
        // Initialize the database
        professionistaRepository.saveAndFlush(professionista);

        int databaseSizeBeforeDelete = professionistaRepository.findAll().size();

        // Delete the professionista
        restProfessionistaMockMvc.perform(delete("/api/professionistas/{id}", professionista.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Professionista> professionistaList = professionistaRepository.findAll();
        assertThat(professionistaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Professionista in Elasticsearch
        verify(mockProfessionistaSearchRepository, times(1)).deleteById(professionista.getId());
    }

    @Test
    @Transactional
    public void searchProfessionista() throws Exception {
        // Initialize the database
        professionistaRepository.saveAndFlush(professionista);
        when(mockProfessionistaSearchRepository.search(queryStringQuery("id:" + professionista.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(professionista), PageRequest.of(0, 1), 1));
        // Search the professionista
        restProfessionistaMockMvc.perform(get("/api/_search/professionistas?query=id:" + professionista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionista.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProfessionista").value(hasItem(DEFAULT_ID_PROFESSIONISTA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME)))
            .andExpect(jsonPath("$.[*].tipologia").value(hasItem(DEFAULT_TIPOLOGIA)))
            .andExpect(jsonPath("$.[*].codiceFiscale").value(hasItem(DEFAULT_CODICE_FISCALE)))
            .andExpect(jsonPath("$.[*].pIva").value(hasItem(DEFAULT_P_IVA)))
            .andExpect(jsonPath("$.[*].studioAssociato").value(hasItem(DEFAULT_STUDIO_ASSOCIATO)))
            .andExpect(jsonPath("$.[*].idLicenza").value(hasItem(DEFAULT_ID_LICENZA)));
    }
}
