package it.maggioli.service;

import it.maggioli.service.dto.ProfessionistaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.maggioli.domain.Professionista}.
 */
public interface ProfessionistaService {

    /**
     * Save a professionista.
     *
     * @param professionistaDTO the entity to save.
     * @return the persisted entity.
     */
    ProfessionistaDTO save(ProfessionistaDTO professionistaDTO);

    /**
     * Get all the professionistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfessionistaDTO> findAll(Pageable pageable);
    /**
     * Get all the ProfessionistaDTO where Licenza is {@code null}.
     *
     * @return the list of entities.
     */
    List<ProfessionistaDTO> findAllWhereLicenzaIsNull();

    /**
     * Get the "id" professionista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionistaDTO> findOne(Long id);

    /**
     * Delete the "id" professionista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the professionista corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfessionistaDTO> search(String query, Pageable pageable);
}
