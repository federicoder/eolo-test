package it.maggioli.service;

import it.maggioli.service.dto.StorageCloudDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.maggioli.domain.StorageCloud}.
 */
public interface StorageCloudService {

    /**
     * Save a storageCloud.
     *
     * @param storageCloudDTO the entity to save.
     * @return the persisted entity.
     */
    StorageCloudDTO save(StorageCloudDTO storageCloudDTO);

    /**
     * Get all the storageClouds.
     *
     * @return the list of entities.
     */
    List<StorageCloudDTO> findAll();

    /**
     * Get the "id" storageCloud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StorageCloudDTO> findOne(Long id);

    /**
     * Delete the "id" storageCloud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the storageCloud corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<StorageCloudDTO> search(String query);
}
