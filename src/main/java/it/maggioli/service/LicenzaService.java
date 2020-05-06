package it.maggioli.service;

import it.maggioli.service.dto.LicenzaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.maggioli.domain.Licenza}.
 */
public interface LicenzaService {

    /**
     * Save a licenza.
     *
     * @param licenzaDTO the entity to save.
     * @return the persisted entity.
     */
    LicenzaDTO save(LicenzaDTO licenzaDTO);

    /**
     * Get all the licenzas.
     *
     * @return the list of entities.
     */
    List<LicenzaDTO> findAll();

    /**
     * Get the "id" licenza.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LicenzaDTO> findOne(Long id);

    /**
     * Delete the "id" licenza.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the licenza corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LicenzaDTO> search(String query);
}
