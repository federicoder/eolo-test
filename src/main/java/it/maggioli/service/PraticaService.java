package it.maggioli.service;

import it.maggioli.service.dto.PraticaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.maggioli.domain.Pratica}.
 */
public interface PraticaService {

    /**
     * Save a pratica.
     *
     * @param praticaDTO the entity to save.
     * @return the persisted entity.
     */
    PraticaDTO save(PraticaDTO praticaDTO);

    /**
     * Get all the praticas.
     *
     * @return the list of entities.
     */
    List<PraticaDTO> findAll();

    /**
     * Get the "id" pratica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PraticaDTO> findOne(Long id);

    /**
     * Delete the "id" pratica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the pratica corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PraticaDTO> search(String query);
}
