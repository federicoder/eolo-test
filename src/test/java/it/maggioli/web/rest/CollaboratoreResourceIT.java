package it.maggioli.web.rest;

import it.maggioli.EoloTestApp;
import it.maggioli.domain.Collaboratore;
import it.maggioli.repository.CollaboratoreRepository;
import it.maggioli.repository.search.CollaboratoreSearchRepository;
import it.maggioli.service.CollaboratoreService;
import it.maggioli.service.dto.CollaboratoreDTO;
import it.maggioli.service.mapper.CollaboratoreMapper;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CollaboratoreResource} REST controller.
 */
@SpringBootTest(classes = EoloTestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CollaboratoreResourceIT {

    private static final Integer DEFAULT_ID_COLLABORATORE = 1;
    private static final Integer UPDATED_ID_COLLABORATORE = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_TIPOLOGIA = "AAAAAAAAAA";
    private static final String UPDATED_TIPOLOGIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_PRATIC = 1;
    private static final Integer UPDATED_ID_PRATIC = 2;

    private static final Integer DEFAULT_ID_INVITO = 1;
    private static final Integer UPDATED_ID_INVITO = 2;

    @Autowired
    private CollaboratoreRepository collaboratoreRepository;

    @Mock
    private CollaboratoreRepository collaboratoreRepositoryMock;

    @Autowired
    private CollaboratoreMapper collaboratoreMapper;

    @Mock
    private CollaboratoreService collaboratoreServiceMock;

    @Autowired
    private CollaboratoreService collaboratoreService;

    /**
     * This repository is mocked in the it.maggioli.repository.search test package.
     *
     * @see it.maggioli.repository.search.CollaboratoreSearchRepositoryMockConfiguration
     */
    @Autowired
    private CollaboratoreSearchRepository mockCollaboratoreSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollaboratoreMockMvc;

    private Collaboratore collaboratore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaboratore createEntity(EntityManager em) {
        Collaboratore collaboratore = new Collaboratore()
            .idCollaboratore(DEFAULT_ID_COLLABORATORE)
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .tipologia(DEFAULT_TIPOLOGIA)
            .idPratic(DEFAULT_ID_PRATIC)
            .idInvito(DEFAULT_ID_INVITO);
        return collaboratore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collaboratore createUpdatedEntity(EntityManager em) {
        Collaboratore collaboratore = new Collaboratore()
            .idCollaboratore(UPDATED_ID_COLLABORATORE)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .tipologia(UPDATED_TIPOLOGIA)
            .idPratic(UPDATED_ID_PRATIC)
            .idInvito(UPDATED_ID_INVITO);
        return collaboratore;
    }

    @BeforeEach
    public void initTest() {
        collaboratore = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollaboratore() throws Exception {
        int databaseSizeBeforeCreate = collaboratoreRepository.findAll().size();

        // Create the Collaboratore
        CollaboratoreDTO collaboratoreDTO = collaboratoreMapper.toDto(collaboratore);
        restCollaboratoreMockMvc.perform(post("/api/collaboratores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collaboratoreDTO)))
            .andExpect(status().isCreated());

        // Validate the Collaboratore in the database
        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeCreate + 1);
        Collaboratore testCollaboratore = collaboratoreList.get(collaboratoreList.size() - 1);
        assertThat(testCollaboratore.getIdCollaboratore()).isEqualTo(DEFAULT_ID_COLLABORATORE);
        assertThat(testCollaboratore.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCollaboratore.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testCollaboratore.getTipologia()).isEqualTo(DEFAULT_TIPOLOGIA);
        assertThat(testCollaboratore.getIdPratic()).isEqualTo(DEFAULT_ID_PRATIC);
        assertThat(testCollaboratore.getIdInvito()).isEqualTo(DEFAULT_ID_INVITO);

        // Validate the Collaboratore in Elasticsearch
        verify(mockCollaboratoreSearchRepository, times(1)).save(testCollaboratore);
    }

    @Test
    @Transactional
    public void createCollaboratoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collaboratoreRepository.findAll().size();

        // Create the Collaboratore with an existing ID
        collaboratore.setId(1L);
        CollaboratoreDTO collaboratoreDTO = collaboratoreMapper.toDto(collaboratore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollaboratoreMockMvc.perform(post("/api/collaboratores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collaboratoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collaboratore in the database
        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Collaboratore in Elasticsearch
        verify(mockCollaboratoreSearchRepository, times(0)).save(collaboratore);
    }


    @Test
    @Transactional
    public void checkIdCollaboratoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaboratoreRepository.findAll().size();
        // set the field null
        collaboratore.setIdCollaboratore(null);

        // Create the Collaboratore, which fails.
        CollaboratoreDTO collaboratoreDTO = collaboratoreMapper.toDto(collaboratore);

        restCollaboratoreMockMvc.perform(post("/api/collaboratores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collaboratoreDTO)))
            .andExpect(status().isBadRequest());

        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdPraticIsRequired() throws Exception {
        int databaseSizeBeforeTest = collaboratoreRepository.findAll().size();
        // set the field null
        collaboratore.setIdPratic(null);

        // Create the Collaboratore, which fails.
        CollaboratoreDTO collaboratoreDTO = collaboratoreMapper.toDto(collaboratore);

        restCollaboratoreMockMvc.perform(post("/api/collaboratores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collaboratoreDTO)))
            .andExpect(status().isBadRequest());

        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCollaboratores() throws Exception {
        // Initialize the database
        collaboratoreRepository.saveAndFlush(collaboratore);

        // Get all the collaboratoreList
        restCollaboratoreMockMvc.perform(get("/api/collaboratores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaboratore.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCollaboratore").value(hasItem(DEFAULT_ID_COLLABORATORE)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME)))
            .andExpect(jsonPath("$.[*].tipologia").value(hasItem(DEFAULT_TIPOLOGIA)))
            .andExpect(jsonPath("$.[*].idPratic").value(hasItem(DEFAULT_ID_PRATIC)))
            .andExpect(jsonPath("$.[*].idInvito").value(hasItem(DEFAULT_ID_INVITO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCollaboratoresWithEagerRelationshipsIsEnabled() throws Exception {
        when(collaboratoreServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaboratoreMockMvc.perform(get("/api/collaboratores?eagerload=true"))
            .andExpect(status().isOk());

        verify(collaboratoreServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCollaboratoresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(collaboratoreServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollaboratoreMockMvc.perform(get("/api/collaboratores?eagerload=true"))
            .andExpect(status().isOk());

        verify(collaboratoreServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCollaboratore() throws Exception {
        // Initialize the database
        collaboratoreRepository.saveAndFlush(collaboratore);

        // Get the collaboratore
        restCollaboratoreMockMvc.perform(get("/api/collaboratores/{id}", collaboratore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collaboratore.getId().intValue()))
            .andExpect(jsonPath("$.idCollaboratore").value(DEFAULT_ID_COLLABORATORE))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME))
            .andExpect(jsonPath("$.tipologia").value(DEFAULT_TIPOLOGIA))
            .andExpect(jsonPath("$.idPratic").value(DEFAULT_ID_PRATIC))
            .andExpect(jsonPath("$.idInvito").value(DEFAULT_ID_INVITO));
    }

    @Test
    @Transactional
    public void getNonExistingCollaboratore() throws Exception {
        // Get the collaboratore
        restCollaboratoreMockMvc.perform(get("/api/collaboratores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollaboratore() throws Exception {
        // Initialize the database
        collaboratoreRepository.saveAndFlush(collaboratore);

        int databaseSizeBeforeUpdate = collaboratoreRepository.findAll().size();

        // Update the collaboratore
        Collaboratore updatedCollaboratore = collaboratoreRepository.findById(collaboratore.getId()).get();
        // Disconnect from session so that the updates on updatedCollaboratore are not directly saved in db
        em.detach(updatedCollaboratore);
        updatedCollaboratore
            .idCollaboratore(UPDATED_ID_COLLABORATORE)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .tipologia(UPDATED_TIPOLOGIA)
            .idPratic(UPDATED_ID_PRATIC)
            .idInvito(UPDATED_ID_INVITO);
        CollaboratoreDTO collaboratoreDTO = collaboratoreMapper.toDto(updatedCollaboratore);

        restCollaboratoreMockMvc.perform(put("/api/collaboratores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collaboratoreDTO)))
            .andExpect(status().isOk());

        // Validate the Collaboratore in the database
        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeUpdate);
        Collaboratore testCollaboratore = collaboratoreList.get(collaboratoreList.size() - 1);
        assertThat(testCollaboratore.getIdCollaboratore()).isEqualTo(UPDATED_ID_COLLABORATORE);
        assertThat(testCollaboratore.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCollaboratore.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testCollaboratore.getTipologia()).isEqualTo(UPDATED_TIPOLOGIA);
        assertThat(testCollaboratore.getIdPratic()).isEqualTo(UPDATED_ID_PRATIC);
        assertThat(testCollaboratore.getIdInvito()).isEqualTo(UPDATED_ID_INVITO);

        // Validate the Collaboratore in Elasticsearch
        verify(mockCollaboratoreSearchRepository, times(1)).save(testCollaboratore);
    }

    @Test
    @Transactional
    public void updateNonExistingCollaboratore() throws Exception {
        int databaseSizeBeforeUpdate = collaboratoreRepository.findAll().size();

        // Create the Collaboratore
        CollaboratoreDTO collaboratoreDTO = collaboratoreMapper.toDto(collaboratore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollaboratoreMockMvc.perform(put("/api/collaboratores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(collaboratoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collaboratore in the database
        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Collaboratore in Elasticsearch
        verify(mockCollaboratoreSearchRepository, times(0)).save(collaboratore);
    }

    @Test
    @Transactional
    public void deleteCollaboratore() throws Exception {
        // Initialize the database
        collaboratoreRepository.saveAndFlush(collaboratore);

        int databaseSizeBeforeDelete = collaboratoreRepository.findAll().size();

        // Delete the collaboratore
        restCollaboratoreMockMvc.perform(delete("/api/collaboratores/{id}", collaboratore.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collaboratore> collaboratoreList = collaboratoreRepository.findAll();
        assertThat(collaboratoreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Collaboratore in Elasticsearch
        verify(mockCollaboratoreSearchRepository, times(1)).deleteById(collaboratore.getId());
    }

    @Test
    @Transactional
    public void searchCollaboratore() throws Exception {
        // Initialize the database
        collaboratoreRepository.saveAndFlush(collaboratore);
        when(mockCollaboratoreSearchRepository.search(queryStringQuery("id:" + collaboratore.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(collaboratore), PageRequest.of(0, 1), 1));
        // Search the collaboratore
        restCollaboratoreMockMvc.perform(get("/api/_search/collaboratores?query=id:" + collaboratore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collaboratore.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCollaboratore").value(hasItem(DEFAULT_ID_COLLABORATORE)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME)))
            .andExpect(jsonPath("$.[*].tipologia").value(hasItem(DEFAULT_TIPOLOGIA)))
            .andExpect(jsonPath("$.[*].idPratic").value(hasItem(DEFAULT_ID_PRATIC)))
            .andExpect(jsonPath("$.[*].idInvito").value(hasItem(DEFAULT_ID_INVITO)));
    }
}
