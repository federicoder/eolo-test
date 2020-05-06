package it.maggioli.service;

import it.maggioli.service.dto.InvitoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.maggioli.domain.Invito}.
 */
public interface InvitoService {

    /**
     * Save a invito.
     *
     * @param invitoDTO the entity to save.
     * @return the persisted entity.
     */
    InvitoDTO save(InvitoDTO invitoDTO);

    /**
     * Get all the invitos.
     *
     * @return the list of entities.
     */
    List<InvitoDTO> findAll();

    /**
     * Get the "id" invito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvitoDTO> findOne(Long id);

    /**
     * Delete the "id" invito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the invito corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<InvitoDTO> search(String query);
}
