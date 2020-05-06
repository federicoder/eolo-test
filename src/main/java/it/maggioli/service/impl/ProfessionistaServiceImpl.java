package it.maggioli.service.impl;

import it.maggioli.service.ProfessionistaService;
import it.maggioli.domain.Professionista;
import it.maggioli.repository.ProfessionistaRepository;
import it.maggioli.repository.search.ProfessionistaSearchRepository;
import it.maggioli.service.dto.ProfessionistaDTO;
import it.maggioli.service.mapper.ProfessionistaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Professionista}.
 */
@Service
@Transactional
public class ProfessionistaServiceImpl implements ProfessionistaService {

    private final Logger log = LoggerFactory.getLogger(ProfessionistaServiceImpl.class);

    private final ProfessionistaRepository professionistaRepository;

    private final ProfessionistaMapper professionistaMapper;

    private final ProfessionistaSearchRepository professionistaSearchRepository;

    public ProfessionistaServiceImpl(ProfessionistaRepository professionistaRepository, ProfessionistaMapper professionistaMapper, ProfessionistaSearchRepository professionistaSearchRepository) {
        this.professionistaRepository = professionistaRepository;
        this.professionistaMapper = professionistaMapper;
        this.professionistaSearchRepository = professionistaSearchRepository;
    }

    /**
     * Save a professionista.
     *
     * @param professionistaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfessionistaDTO save(ProfessionistaDTO professionistaDTO) {
        log.debug("Request to save Professionista : {}", professionistaDTO);
        Professionista professionista = professionistaMapper.toEntity(professionistaDTO);
        professionista = professionistaRepository.save(professionista);
        ProfessionistaDTO result = professionistaMapper.toDto(professionista);
        professionistaSearchRepository.save(professionista);
        return result;
    }

    /**
     * Get all the professionistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfessionistaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Professionistas");
        return professionistaRepository.findAll(pageable)
            .map(professionistaMapper::toDto);
    }

    /**
     * Get one professionista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionistaDTO> findOne(Long id) {
        log.debug("Request to get Professionista : {}", id);
        return professionistaRepository.findById(id)
            .map(professionistaMapper::toDto);
    }

    /**
     * Delete the professionista by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Professionista : {}", id);
        professionistaRepository.deleteById(id);
        professionistaSearchRepository.deleteById(id);
    }

    /**
     * Search for the professionista corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfessionistaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Professionistas for query {}", query);
        return professionistaSearchRepository.search(queryStringQuery(query), pageable)
            .map(professionistaMapper::toDto);
    }
}
