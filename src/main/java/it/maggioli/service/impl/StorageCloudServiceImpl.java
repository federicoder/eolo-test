package it.maggioli.service.impl;

import it.maggioli.service.StorageCloudService;
import it.maggioli.domain.StorageCloud;
import it.maggioli.repository.StorageCloudRepository;
import it.maggioli.repository.search.StorageCloudSearchRepository;
import it.maggioli.service.dto.StorageCloudDTO;
import it.maggioli.service.mapper.StorageCloudMapper;
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
 * Service Implementation for managing {@link StorageCloud}.
 */
@Service
@Transactional
public class StorageCloudServiceImpl implements StorageCloudService {

    private final Logger log = LoggerFactory.getLogger(StorageCloudServiceImpl.class);

    private final StorageCloudRepository storageCloudRepository;

    private final StorageCloudMapper storageCloudMapper;

    private final StorageCloudSearchRepository storageCloudSearchRepository;

    public StorageCloudServiceImpl(StorageCloudRepository storageCloudRepository, StorageCloudMapper storageCloudMapper, StorageCloudSearchRepository storageCloudSearchRepository) {
        this.storageCloudRepository = storageCloudRepository;
        this.storageCloudMapper = storageCloudMapper;
        this.storageCloudSearchRepository = storageCloudSearchRepository;
    }

    /**
     * Save a storageCloud.
     *
     * @param storageCloudDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StorageCloudDTO save(StorageCloudDTO storageCloudDTO) {
        log.debug("Request to save StorageCloud : {}", storageCloudDTO);
        StorageCloud storageCloud = storageCloudMapper.toEntity(storageCloudDTO);
        storageCloud = storageCloudRepository.save(storageCloud);
        StorageCloudDTO result = storageCloudMapper.toDto(storageCloud);
        storageCloudSearchRepository.save(storageCloud);
        return result;
    }

    /**
     * Get all the storageClouds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StorageCloudDTO> findAll() {
        log.debug("Request to get all StorageClouds");
        return storageCloudRepository.findAll().stream()
            .map(storageCloudMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one storageCloud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StorageCloudDTO> findOne(Long id) {
        log.debug("Request to get StorageCloud : {}", id);
        return storageCloudRepository.findById(id)
            .map(storageCloudMapper::toDto);
    }

    /**
     * Delete the storageCloud by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StorageCloud : {}", id);
        storageCloudRepository.deleteById(id);
        storageCloudSearchRepository.deleteById(id);
    }

    /**
     * Search for the storageCloud corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StorageCloudDTO> search(String query) {
        log.debug("Request to search StorageClouds for query {}", query);
        return StreamSupport
            .stream(storageCloudSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(storageCloudMapper::toDto)
            .collect(Collectors.toList());
    }
}
