package it.maggioli.web.rest;

import it.maggioli.EoloTestApp;
import it.maggioli.domain.Invito;
import it.maggioli.repository.InvitoRepository;
import it.maggioli.repository.search.InvitoSearchRepository;
import it.maggioli.service.InvitoService;
import it.maggioli.service.dto.InvitoDTO;
import it.maggioli.service.mapper.InvitoMapper;

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
 * Integration tests for the {@link InvitoResource} REST controller.
 */
@SpringBootTest(classes = EoloTestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvitoResourceIT {

    private static final Boolean DEFAULT_UTENTE_ISCRITTO = false;
    private static final Boolean UPDATED_UTENTE_ISCRITTO = true;

    private static final Integer DEFAULT_ID_UTENTE = 1;
    private static final Integer UPDATED_ID_UTENTE = 2;

    private static final Integer DEFAULT_ID_PRATICA = 1;
    private static final Integer UPDATED_ID_PRATICA = 2;

    private static final Integer DEFAULT_ID_INVITO = 1;
    private static final Integer UPDATED_ID_INVITO = 2;

    @Autowired
    private InvitoRepository invitoRepository;

    @Autowired
    private InvitoMapper invitoMapper;

    @Autowired
    private InvitoService invitoService;

    /**
     * This repository is mocked in the it.maggioli.repository.search test package.
     *
     * @see it.maggioli.repository.search.InvitoSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvitoSearchRepository mockInvitoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitoMockMvc;

    private Invito invito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invito createEntity(EntityManager em) {
        Invito invito = new Invito()
            .utenteIscritto(DEFAULT_UTENTE_ISCRITTO)
            .idUtente(DEFAULT_ID_UTENTE)
            .idPratica(DEFAULT_ID_PRATICA)
            .idInvito(DEFAULT_ID_INVITO);
        return invito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invito createUpdatedEntity(EntityManager em) {
        Invito invito = new Invito()
            .utenteIscritto(UPDATED_UTENTE_ISCRITTO)
            .idUtente(UPDATED_ID_UTENTE)
            .idPratica(UPDATED_ID_PRATICA)
            .idInvito(UPDATED_ID_INVITO);
        return invito;
    }

    @BeforeEach
    public void initTest() {
        invito = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvito() throws Exception {
        int databaseSizeBeforeCreate = invitoRepository.findAll().size();

        // Create the Invito
        InvitoDTO invitoDTO = invitoMapper.toDto(invito);
        restInvitoMockMvc.perform(post("/api/invitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitoDTO)))
            .andExpect(status().isCreated());

        // Validate the Invito in the database
        List<Invito> invitoList = invitoRepository.findAll();
        assertThat(invitoList).hasSize(databaseSizeBeforeCreate + 1);
        Invito testInvito = invitoList.get(invitoList.size() - 1);
        assertThat(testInvito.isUtenteIscritto()).isEqualTo(DEFAULT_UTENTE_ISCRITTO);
        assertThat(testInvito.getIdUtente()).isEqualTo(DEFAULT_ID_UTENTE);
        assertThat(testInvito.getIdPratica()).isEqualTo(DEFAULT_ID_PRATICA);
        assertThat(testInvito.getIdInvito()).isEqualTo(DEFAULT_ID_INVITO);

        // Validate the Invito in Elasticsearch
        verify(mockInvitoSearchRepository, times(1)).save(testInvito);
    }

    @Test
    @Transactional
    public void createInvitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitoRepository.findAll().size();

        // Create the Invito with an existing ID
        invito.setId(1L);
        InvitoDTO invitoDTO = invitoMapper.toDto(invito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitoMockMvc.perform(post("/api/invitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invito in the database
        List<Invito> invitoList = invitoRepository.findAll();
        assertThat(invitoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Invito in Elasticsearch
        verify(mockInvitoSearchRepository, times(0)).save(invito);
    }


    @Test
    @Transactional
    public void checkIdInvitoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitoRepository.findAll().size();
        // set the field null
        invito.setIdInvito(null);

        // Create the Invito, which fails.
        InvitoDTO invitoDTO = invitoMapper.toDto(invito);

        restInvitoMockMvc.perform(post("/api/invitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitoDTO)))
            .andExpect(status().isBadRequest());

        List<Invito> invitoList = invitoRepository.findAll();
        assertThat(invitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitos() throws Exception {
        // Initialize the database
        invitoRepository.saveAndFlush(invito);

        // Get all the invitoList
        restInvitoMockMvc.perform(get("/api/invitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invito.getId().intValue())))
            .andExpect(jsonPath("$.[*].utenteIscritto").value(hasItem(DEFAULT_UTENTE_ISCRITTO.booleanValue())))
            .andExpect(jsonPath("$.[*].idUtente").value(hasItem(DEFAULT_ID_UTENTE)))
            .andExpect(jsonPath("$.[*].idPratica").value(hasItem(DEFAULT_ID_PRATICA)))
            .andExpect(jsonPath("$.[*].idInvito").value(hasItem(DEFAULT_ID_INVITO)));
    }
    
    @Test
    @Transactional
    public void getInvito() throws Exception {
        // Initialize the database
        invitoRepository.saveAndFlush(invito);

        // Get the invito
        restInvitoMockMvc.perform(get("/api/invitos/{id}", invito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invito.getId().intValue()))
            .andExpect(jsonPath("$.utenteIscritto").value(DEFAULT_UTENTE_ISCRITTO.booleanValue()))
            .andExpect(jsonPath("$.idUtente").value(DEFAULT_ID_UTENTE))
            .andExpect(jsonPath("$.idPratica").value(DEFAULT_ID_PRATICA))
            .andExpect(jsonPath("$.idInvito").value(DEFAULT_ID_INVITO));
    }

    @Test
    @Transactional
    public void getNonExistingInvito() throws Exception {
        // Get the invito
        restInvitoMockMvc.perform(get("/api/invitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvito() throws Exception {
        // Initialize the database
        invitoRepository.saveAndFlush(invito);

        int databaseSizeBeforeUpdate = invitoRepository.findAll().size();

        // Update the invito
        Invito updatedInvito = invitoRepository.findById(invito.getId()).get();
        // Disconnect from session so that the updates on updatedInvito are not directly saved in db
        em.detach(updatedInvito);
        updatedInvito
            .utenteIscritto(UPDATED_UTENTE_ISCRITTO)
            .idUtente(UPDATED_ID_UTENTE)
            .idPratica(UPDATED_ID_PRATICA)
            .idInvito(UPDATED_ID_INVITO);
        InvitoDTO invitoDTO = invitoMapper.toDto(updatedInvito);

        restInvitoMockMvc.perform(put("/api/invitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitoDTO)))
            .andExpect(status().isOk());

        // Validate the Invito in the database
        List<Invito> invitoList = invitoRepository.findAll();
        assertThat(invitoList).hasSize(databaseSizeBeforeUpdate);
        Invito testInvito = invitoList.get(invitoList.size() - 1);
        assertThat(testInvito.isUtenteIscritto()).isEqualTo(UPDATED_UTENTE_ISCRITTO);
        assertThat(testInvito.getIdUtente()).isEqualTo(UPDATED_ID_UTENTE);
        assertThat(testInvito.getIdPratica()).isEqualTo(UPDATED_ID_PRATICA);
        assertThat(testInvito.getIdInvito()).isEqualTo(UPDATED_ID_INVITO);

        // Validate the Invito in Elasticsearch
        verify(mockInvitoSearchRepository, times(1)).save(testInvito);
    }

    @Test
    @Transactional
    public void updateNonExistingInvito() throws Exception {
        int databaseSizeBeforeUpdate = invitoRepository.findAll().size();

        // Create the Invito
        InvitoDTO invitoDTO = invitoMapper.toDto(invito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitoMockMvc.perform(put("/api/invitos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invito in the database
        List<Invito> invitoList = invitoRepository.findAll();
        assertThat(invitoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Invito in Elasticsearch
        verify(mockInvitoSearchRepository, times(0)).save(invito);
    }

    @Test
    @Transactional
    public void deleteInvito() throws Exception {
        // Initialize the database
        invitoRepository.saveAndFlush(invito);

        int databaseSizeBeforeDelete = invitoRepository.findAll().size();

        // Delete the invito
        restInvitoMockMvc.perform(delete("/api/invitos/{id}", invito.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invito> invitoList = invitoRepository.findAll();
        assertThat(invitoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Invito in Elasticsearch
        verify(mockInvitoSearchRepository, times(1)).deleteById(invito.getId());
    }

    @Test
    @Transactional
    public void searchInvito() throws Exception {
        // Initialize the database
        invitoRepository.saveAndFlush(invito);
        when(mockInvitoSearchRepository.search(queryStringQuery("id:" + invito.getId())))
            .thenReturn(Collections.singletonList(invito));
        // Search the invito
        restInvitoMockMvc.perform(get("/api/_search/invitos?query=id:" + invito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invito.getId().intValue())))
            .andExpect(jsonPath("$.[*].utenteIscritto").value(hasItem(DEFAULT_UTENTE_ISCRITTO.booleanValue())))
            .andExpect(jsonPath("$.[*].idUtente").value(hasItem(DEFAULT_ID_UTENTE)))
            .andExpect(jsonPath("$.[*].idPratica").value(hasItem(DEFAULT_ID_PRATICA)))
            .andExpect(jsonPath("$.[*].idInvito").value(hasItem(DEFAULT_ID_INVITO)));
    }
}
