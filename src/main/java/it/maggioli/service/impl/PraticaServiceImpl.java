package it.maggioli.service.impl;

import it.maggioli.service.PraticaService;
import it.maggioli.domain.Pratica;
import it.maggioli.repository.PraticaRepository;
import it.maggioli.repository.search.PraticaSearchRepository;
import it.maggioli.service.dto.PraticaDTO;
import it.maggioli.service.mapper.PraticaMapper;
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
 * Service Implementation for managing {@link Pratica}.
 */
@Service
@Transactional
public class PraticaServiceImpl implements PraticaService {

    private final Logger log = LoggerFactory.getLogger(PraticaServiceImpl.class);

    private final PraticaRepository praticaRepository;

    private final PraticaMapper praticaMapper;

    private final PraticaSearchRepository praticaSearchRepository;

    public PraticaServiceImpl(PraticaRepository praticaRepository, PraticaMapper praticaMapper, PraticaSearchRepository praticaSearchRepository) {
        this.praticaRepository = praticaRepository;
        this.praticaMapper = praticaMapper;
        this.praticaSearchRepository = praticaSearchRepository;
    }

    /**
     * Save a pratica.
     *
     * @param praticaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PraticaDTO save(PraticaDTO praticaDTO) {
        log.debug("Request to save Pratica : {}", praticaDTO);
        Pratica pratica = praticaMapper.toEntity(praticaDTO);
        pratica = praticaRepository.save(pratica);
        PraticaDTO result = praticaMapper.toDto(pratica);
        praticaSearchRepository.save(pratica);
        return result;
    }

    /**
     * Get all the praticas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PraticaDTO> findAll() {
        log.debug("Request to get all Praticas");
        return praticaRepository.findAll().stream()
            .map(praticaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pratica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PraticaDTO> findOne(Long id) {
        log.debug("Request to get Pratica : {}", id);
        return praticaRepository.findById(id)
            .map(praticaMapper::toDto);
    }

    /**
     * Delete the pratica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pratica : {}", id);
        praticaRepository.deleteById(id);
        praticaSearchRepository.deleteById(id);
    }

    /**
     * Search for the pratica corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PraticaDTO> search(String query) {
        log.debug("Request to search Praticas for query {}", query);
        return StreamSupport
            .stream(praticaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(praticaMapper::toDto)
            .collect(Collectors.toList());
    }
}
