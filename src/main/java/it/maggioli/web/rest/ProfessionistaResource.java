package it.maggioli.web.rest;

import it.maggioli.service.ProfessionistaService;
import it.maggioli.web.rest.errors.BadRequestAlertException;
import it.maggioli.service.dto.ProfessionistaDTO;

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
 * REST controller for managing {@link it.maggioli.domain.Professionista}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionistaResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionistaResource.class);

    private static final String ENTITY_NAME = "professionista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionistaService professionistaService;

    public ProfessionistaResource(ProfessionistaService professionistaService) {
        this.professionistaService = professionistaService;
    }

    /**
     * {@code POST  /professionistas} : Create a new professionista.
     *
     * @param professionistaDTO the professionistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionistaDTO, or with status {@code 400 (Bad Request)} if the professionista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professionistas")
    public ResponseEntity<ProfessionistaDTO> createProfessionista(@Valid @RequestBody ProfessionistaDTO professionistaDTO) throws URISyntaxException {
        log.debug("REST request to save Professionista : {}", professionistaDTO);
        if (professionistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new professionista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionistaDTO result = professionistaService.save(professionistaDTO);
        return ResponseEntity.created(new URI("/api/professionistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professionistas} : Updates an existing professionista.
     *
     * @param professionistaDTO the professionistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionistaDTO,
     * or with status {@code 400 (Bad Request)} if the professionistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professionistas")
    public ResponseEntity<ProfessionistaDTO> updateProfessionista(@Valid @RequestBody ProfessionistaDTO professionistaDTO) throws URISyntaxException {
        log.debug("REST request to update Professionista : {}", professionistaDTO);
        if (professionistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionistaDTO result = professionistaService.save(professionistaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professionistas} : get all the professionistas.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionistas in body.
     */
    @GetMapping("/professionistas")
    public ResponseEntity<List<ProfessionistaDTO>> getAllProfessionistas(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("licenza-is-null".equals(filter)) {
            log.debug("REST request to get all Professionistas where licenza is null");
            return new ResponseEntity<>(professionistaService.findAllWhereLicenzaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Professionistas");
        Page<ProfessionistaDTO> page = professionistaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /professionistas/:id} : get the "id" professionista.
     *
     * @param id the id of the professionistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professionistas/{id}")
    public ResponseEntity<ProfessionistaDTO> getProfessionista(@PathVariable Long id) {
        log.debug("REST request to get Professionista : {}", id);
        Optional<ProfessionistaDTO> professionistaDTO = professionistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionistaDTO);
    }

    /**
     * {@code DELETE  /professionistas/:id} : delete the "id" professionista.
     *
     * @param id the id of the professionistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professionistas/{id}")
    public ResponseEntity<Void> deleteProfessionista(@PathVariable Long id) {
        log.debug("REST request to delete Professionista : {}", id);
        professionistaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/professionistas?query=:query} : search for the professionista corresponding
     * to the query.
     *
     * @param query the query of the professionista search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/professionistas")
    public ResponseEntity<List<ProfessionistaDTO>> searchProfessionistas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Professionistas for query {}", query);
        Page<ProfessionistaDTO> page = professionistaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
