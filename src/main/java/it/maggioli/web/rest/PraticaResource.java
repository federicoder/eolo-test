package it.maggioli.web.rest;

import it.maggioli.service.PraticaService;
import it.maggioli.web.rest.errors.BadRequestAlertException;
import it.maggioli.service.dto.PraticaDTO;

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
 * REST controller for managing {@link it.maggioli.domain.Pratica}.
 */
@RestController
@RequestMapping("/api")
public class PraticaResource {

    private final Logger log = LoggerFactory.getLogger(PraticaResource.class);

    private static final String ENTITY_NAME = "pratica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PraticaService praticaService;

    public PraticaResource(PraticaService praticaService) {
        this.praticaService = praticaService;
    }

    /**
     * {@code POST  /praticas} : Create a new pratica.
     *
     * @param praticaDTO the praticaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new praticaDTO, or with status {@code 400 (Bad Request)} if the pratica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/praticas")
    public ResponseEntity<PraticaDTO> createPratica(@Valid @RequestBody PraticaDTO praticaDTO) throws URISyntaxException {
        log.debug("REST request to save Pratica : {}", praticaDTO);
        if (praticaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pratica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PraticaDTO result = praticaService.save(praticaDTO);
        return ResponseEntity.created(new URI("/api/praticas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /praticas} : Updates an existing pratica.
     *
     * @param praticaDTO the praticaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated praticaDTO,
     * or with status {@code 400 (Bad Request)} if the praticaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the praticaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/praticas")
    public ResponseEntity<PraticaDTO> updatePratica(@Valid @RequestBody PraticaDTO praticaDTO) throws URISyntaxException {
        log.debug("REST request to update Pratica : {}", praticaDTO);
        if (praticaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PraticaDTO result = praticaService.save(praticaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, praticaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /praticas} : get all the praticas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of praticas in body.
     */
    @GetMapping("/praticas")
    public List<PraticaDTO> getAllPraticas() {
        log.debug("REST request to get all Praticas");
        return praticaService.findAll();
    }

    /**
     * {@code GET  /praticas/:id} : get the "id" pratica.
     *
     * @param id the id of the praticaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the praticaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/praticas/{id}")
    public ResponseEntity<PraticaDTO> getPratica(@PathVariable Long id) {
        log.debug("REST request to get Pratica : {}", id);
        Optional<PraticaDTO> praticaDTO = praticaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(praticaDTO);
    }

    /**
     * {@code DELETE  /praticas/:id} : delete the "id" pratica.
     *
     * @param id the id of the praticaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/praticas/{id}")
    public ResponseEntity<Void> deletePratica(@PathVariable Long id) {
        log.debug("REST request to delete Pratica : {}", id);
        praticaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/praticas?query=:query} : search for the pratica corresponding
     * to the query.
     *
     * @param query the query of the pratica search.
     * @return the result of the search.
     */
    @GetMapping("/_search/praticas")
    public List<PraticaDTO> searchPraticas(@RequestParam String query) {
        log.debug("REST request to search Praticas for query {}", query);
        return praticaService.search(query);
    }
}
