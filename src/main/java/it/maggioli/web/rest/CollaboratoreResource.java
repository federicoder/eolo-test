package it.maggioli.web.rest;

import it.maggioli.service.CollaboratoreService;
import it.maggioli.web.rest.errors.BadRequestAlertException;
import it.maggioli.service.dto.CollaboratoreDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link it.maggioli.domain.Collaboratore}.
 */
@RestController
@RequestMapping("/api")
public class CollaboratoreResource {

    private final Logger log = LoggerFactory.getLogger(CollaboratoreResource.class);

    private static final String ENTITY_NAME = "collaboratore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaboratoreService collaboratoreService;

    public CollaboratoreResource(CollaboratoreService collaboratoreService) {
        this.collaboratoreService = collaboratoreService;
    }

    /**
     * {@code POST  /collaboratores} : Create a new collaboratore.
     *
     * @param collaboratoreDTO the collaboratoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaboratoreDTO, or with status {@code 400 (Bad Request)} if the collaboratore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collaboratores")
    public ResponseEntity<CollaboratoreDTO> createCollaboratore(@Valid @RequestBody CollaboratoreDTO collaboratoreDTO) throws URISyntaxException {
        log.debug("REST request to save Collaboratore : {}", collaboratoreDTO);
        if (collaboratoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new collaboratore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollaboratoreDTO result = collaboratoreService.save(collaboratoreDTO);
        return ResponseEntity.created(new URI("/api/collaboratores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collaboratores} : Updates an existing collaboratore.
     *
     * @param collaboratoreDTO the collaboratoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaboratoreDTO,
     * or with status {@code 400 (Bad Request)} if the collaboratoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaboratoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collaboratores")
    public ResponseEntity<CollaboratoreDTO> updateCollaboratore(@Valid @RequestBody CollaboratoreDTO collaboratoreDTO) throws URISyntaxException {
        log.debug("REST request to update Collaboratore : {}", collaboratoreDTO);
        if (collaboratoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollaboratoreDTO result = collaboratoreService.save(collaboratoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboratoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collaboratores} : get all the collaboratores.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaboratores in body.
     */
    @GetMapping("/collaboratores")
    public ResponseEntity<List<CollaboratoreDTO>> getAllCollaboratores(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("invito-is-null".equals(filter)) {
            log.debug("REST request to get all Collaboratores where invito is null");
            return new ResponseEntity<>(collaboratoreService.findAllWhereInvitoIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Collaboratores");
        Page<CollaboratoreDTO> page;
        if (eagerload) {
            page = collaboratoreService.findAllWithEagerRelationships(pageable);
        } else {
            page = collaboratoreService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collaboratores/:id} : get the "id" collaboratore.
     *
     * @param id the id of the collaboratoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaboratoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collaboratores/{id}")
    public ResponseEntity<CollaboratoreDTO> getCollaboratore(@PathVariable Long id) {
        log.debug("REST request to get Collaboratore : {}", id);
        Optional<CollaboratoreDTO> collaboratoreDTO = collaboratoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collaboratoreDTO);
    }

    /**
     * {@code DELETE  /collaboratores/:id} : delete the "id" collaboratore.
     *
     * @param id the id of the collaboratoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collaboratores/{id}")
    public ResponseEntity<Void> deleteCollaboratore(@PathVariable Long id) {
        log.debug("REST request to delete Collaboratore : {}", id);
        collaboratoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/collaboratores?query=:query} : search for the collaboratore corresponding
     * to the query.
     *
     * @param query the query of the collaboratore search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/collaboratores")
    public ResponseEntity<List<CollaboratoreDTO>> searchCollaboratores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Collaboratores for query {}", query);
        Page<CollaboratoreDTO> page = collaboratoreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
