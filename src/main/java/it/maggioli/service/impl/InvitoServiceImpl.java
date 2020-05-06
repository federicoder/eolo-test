package it.maggioli.service.impl;

import it.maggioli.service.InvitoService;
import it.maggioli.domain.Invito;
import it.maggioli.repository.InvitoRepository;
import it.maggioli.repository.search.InvitoSearchRepository;
import it.maggioli.service.dto.InvitoDTO;
import it.maggioli.service.mapper.InvitoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Invito}.
 */
@Service
@Transactional
public class InvitoServiceImpl implements InvitoService {

    private final Logger log = LoggerFactory.getLogger(InvitoServiceImpl.class);

    private final InvitoRepository invitoRepository;

    private final InvitoMapper invitoMapper;

    private final InvitoSearchRepository invitoSearchRepository;

    public InvitoServiceImpl(InvitoRepository invitoRepository, InvitoMapper invitoMapper, InvitoSearchRepository invitoSearchRepository) {
        this.invitoRepository = invitoRepository;
        this.invitoMapper = invitoMapper;
        this.invitoSearchRepository = invitoSearchRepository;
    }

    /**
     * Save a invito.
     *
     * @param invitoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvitoDTO save(InvitoDTO invitoDTO) {
        log.debug("Request to save Invito : {}", invitoDTO);
        Invito invito = invitoMapper.toEntity(invitoDTO);
        invito = invitoRepository.save(invito);
        InvitoDTO result = invitoMapper.toDto(invito);
        invitoSearchRepository.save(invito);
        return result;
    }

    /**
     * Get all the invitos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvitoDTO> findAll() {
        log.debug("Request to get all Invitos");
        return invitoRepository.findAll().stream()
            .map(invitoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one invito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvitoDTO> findOne(Long id) {
        log.debug("Request to get Invito : {}", id);
        return invitoRepository.findById(id)
            .map(invitoMapper::toDto);
    }

    /**
     * Delete the invito by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invito : {}", id);
        invitoRepository.deleteById(id);
        invitoSearchRepository.deleteById(id);
    }

    /**
     * Search for the invito corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvitoDTO> search(String query) {
        log.debug("Request to search Invitos for query {}", query);
        return StreamSupport
            .stream(invitoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(invitoMapper::toDto)
            .collect(Collectors.toList());
    }
}
