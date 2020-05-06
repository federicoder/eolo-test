package it.maggioli.web.rest;

import it.maggioli.service.InvitoService;
import it.maggioli.web.rest.errors.BadRequestAlertException;
import it.maggioli.service.dto.InvitoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link it.maggioli.domain.Invito}.
 */
@RestController
@RequestMapping("/api")
public class InvitoResource {

    private final Logger log = LoggerFactory.getLogger(InvitoResource.class);

    private static final String ENTITY_NAME = "invito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvitoService invitoService;

    public InvitoResource(InvitoService invitoService) {
        this.invitoService = invitoService;
    }

    /**
     * {@code POST  /invitos} : Create a new invito.
     *
     * @param invitoDTO the invitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invitoDTO, or with status {@code 400 (Bad Request)} if the invito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invitos")
    public ResponseEntity<InvitoDTO> createInvito(@Valid @RequestBody InvitoDTO invitoDTO) throws URISyntaxException {
        log.debug("REST request to save Invito : {}", invitoDTO);
        if (invitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new invito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitoDTO result = invitoService.save(invitoDTO);
        return ResponseEntity.created(new URI("/api/invitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invitos} : Updates an existing invito.
     *
     * @param invitoDTO the invitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invitoDTO,
     * or with status {@code 400 (Bad Request)} if the invitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invitos")
    public ResponseEntity<InvitoDTO> updateInvito(@Valid @RequestBody InvitoDTO invitoDTO) throws URISyntaxException {
        log.debug("REST request to update Invito : {}", invitoDTO);
        if (invitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitoDTO result = invitoService.save(invitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invitos} : get all the invitos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invitos in body.
     */
    @GetMapping("/invitos")
    public List<InvitoDTO> getAllInvitos() {
        log.debug("REST request to get all Invitos");
        return invitoService.findAll();
    }

    /**
     * {@code GET  /invitos/:id} : get the "id" invito.
     *
     * @param id the id of the invitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invitos/{id}")
    public ResponseEntity<InvitoDTO> getInvito(@PathVariable Long id) {
        log.debug("REST request to get Invito : {}", id);
        Optional<InvitoDTO> invitoDTO = invitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invitoDTO);
    }

    /**
     * {@code DELETE  /invitos/:id} : delete the "id" invito.
     *
     * @param id the id of the invitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invitos/{id}")
    public ResponseEntity<Void> deleteInvito(@PathVariable Long id) {
        log.debug("REST request to delete Invito : {}", id);
        invitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/invitos?query=:query} : search for the invito corresponding
     * to the query.
     *
     * @param query the query of the invito search.
     * @return the result of the search.
     */
    @GetMapping("/_search/invitos")
    public List<InvitoDTO> searchInvitos(@RequestParam String query) {
        log.debug("REST request to search Invitos for query {}", query);
        return invitoService.search(query);
    }
}
