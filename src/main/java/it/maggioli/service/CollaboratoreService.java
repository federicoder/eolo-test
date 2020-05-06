package it.maggioli.service;

import it.maggioli.domain.Collaboratore;
import it.maggioli.repository.CollaboratoreRepository;
import it.maggioli.repository.search.CollaboratoreSearchRepository;
import it.maggioli.service.dto.CollaboratoreDTO;
import it.maggioli.service.mapper.CollaboratoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Collaboratore}.
 */
@Service
@Transactional
public class CollaboratoreService {

    private final Logger log = LoggerFactory.getLogger(CollaboratoreService.class);

    private final CollaboratoreRepository collaboratoreRepository;

    private final CollaboratoreMapper collaboratoreMapper;

    private final CollaboratoreSearchRepository collaboratoreSearchRepository;

    public CollaboratoreService(CollaboratoreRepository collaboratoreRepository, CollaboratoreMapper collaboratoreMapper, CollaboratoreSearchRepository collaboratoreSearchRepository) {
        this.collaboratoreRepository = collaboratoreRepository;
        this.collaboratoreMapper = collaboratoreMapper;
        this.collaboratoreSearchRepository = collaboratoreSearchRepository;
    }

    /**
     * Save a collaboratore.
     *
     * @param collaboratoreDTO the entity to save.
     * @return the persisted entity.
     */
    public CollaboratoreDTO save(CollaboratoreDTO collaboratoreDTO) {
        log.debug("Request to save Collaboratore : {}", collaboratoreDTO);
        Collaboratore collaboratore = collaboratoreMapper.toEntity(collaboratoreDTO);
        collaboratore = collaboratoreRepository.save(collaboratore);
        CollaboratoreDTO result = collaboratoreMapper.toDto(collaboratore);
        collaboratoreSearchRepository.save(collaboratore);
        return result;
    }

    /**
     * Get all the collaboratores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CollaboratoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Collaboratores");
        return collaboratoreRepository.findAll(pageable)
            .map(collaboratoreMapper::toDto);
    }

    /**
     * Get all the collaboratores with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CollaboratoreDTO> findAllWithEagerRelationships(Pageable pageable) {
        return collaboratoreRepository.findAllWithEagerRelationships(pageable).map(collaboratoreMapper::toDto);
    }


    /**
     *  Get all the collaboratores where IdCollaboratore is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CollaboratoreDTO> findAllWhereIdCollaboratoreIsNull() {
        log.debug("Request to get all collaboratores where IdCollaboratore is null");
        return StreamSupport
            .stream(collaboratoreRepository.findAll().spliterator(), false)
            .filter(collaboratore -> collaboratore.getIdCollaboratore() == null)
            .map(collaboratoreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one collaboratore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CollaboratoreDTO> findOne(Long id) {
        log.debug("Request to get Collaboratore : {}", id);
        return collaboratoreRepository.findOneWithEagerRelationships(id)
            .map(collaboratoreMapper::toDto);
    }

    /**
     * Delete the collaboratore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Collaboratore : {}", id);
        collaboratoreRepository.deleteById(id);
        collaboratoreSearchRepository.deleteById(id);
    }

    /**
     * Search for the collaboratore corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CollaboratoreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Collaboratores for query {}", query);
        return collaboratoreSearchRepository.search(queryStringQuery(query), pageable)
            .map(collaboratoreMapper::toDto);
    }
}
