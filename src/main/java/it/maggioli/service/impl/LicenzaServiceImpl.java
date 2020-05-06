package it.maggioli.service.impl;

import it.maggioli.service.LicenzaService;
import it.maggioli.domain.Licenza;
import it.maggioli.repository.LicenzaRepository;
import it.maggioli.repository.search.LicenzaSearchRepository;
import it.maggioli.service.dto.LicenzaDTO;
import it.maggioli.service.mapper.LicenzaMapper;
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
 * Service Implementation for managing {@link Licenza}.
 */
@Service
@Transactional
public class LicenzaServiceImpl implements LicenzaService {

    private final Logger log = LoggerFactory.getLogger(LicenzaServiceImpl.class);

    private final LicenzaRepository licenzaRepository;

    private final LicenzaMapper licenzaMapper;

    private final LicenzaSearchRepository licenzaSearchRepository;

    public LicenzaServiceImpl(LicenzaRepository licenzaRepository, LicenzaMapper licenzaMapper, LicenzaSearchRepository licenzaSearchRepository) {
        this.licenzaRepository = licenzaRepository;
        this.licenzaMapper = licenzaMapper;
        this.licenzaSearchRepository = licenzaSearchRepository;
    }

    /**
     * Save a licenza.
     *
     * @param licenzaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LicenzaDTO save(LicenzaDTO licenzaDTO) {
        log.debug("Request to save Licenza : {}", licenzaDTO);
        Licenza licenza = licenzaMapper.toEntity(licenzaDTO);
        licenza = licenzaRepository.save(licenza);
        LicenzaDTO result = licenzaMapper.toDto(licenza);
        licenzaSearchRepository.save(licenza);
        return result;
    }

    /**
     * Get all the licenzas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LicenzaDTO> findAll() {
        log.debug("Request to get all Licenzas");
        return licenzaRepository.findAll().stream()
            .map(licenzaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one licenza by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LicenzaDTO> findOne(Long id) {
        log.debug("Request to get Licenza : {}", id);
        return licenzaRepository.findById(id)
            .map(licenzaMapper::toDto);
    }

    /**
     * Delete the licenza by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Licenza : {}", id);
        licenzaRepository.deleteById(id);
        licenzaSearchRepository.deleteById(id);
    }

    /**
     * Search for the licenza corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LicenzaDTO> search(String query) {
        log.debug("Request to search Licenzas for query {}", query);
        return StreamSupport
            .stream(licenzaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(licenzaMapper::toDto)
            .collect(Collectors.toList());
    }
}
