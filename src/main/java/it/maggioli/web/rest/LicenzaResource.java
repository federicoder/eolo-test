package it.maggioli.web.rest;

import it.maggioli.service.LicenzaService;
import it.maggioli.web.rest.errors.BadRequestAlertException;
import it.maggioli.service.dto.LicenzaDTO;

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
 * REST controller for managing {@link it.maggioli.domain.Licenza}.
 */
@RestController
@RequestMapping("/api")
public class LicenzaResource {

    private final Logger log = LoggerFactory.getLogger(LicenzaResource.class);

    private static final String ENTITY_NAME = "licenza";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenzaService licenzaService;

    public LicenzaResource(LicenzaService licenzaService) {
        this.licenzaService = licenzaService;
    }

    /**
     * {@code POST  /licenzas} : Create a new licenza.
     *
     * @param licenzaDTO the licenzaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenzaDTO, or with status {@code 400 (Bad Request)} if the licenza has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licenzas")
    public ResponseEntity<LicenzaDTO> createLicenza(@Valid @RequestBody LicenzaDTO licenzaDTO) throws URISyntaxException {
        log.debug("REST request to save Licenza : {}", licenzaDTO);
        if (licenzaDTO.getId() != null) {
            throw new BadRequestAlertException("A new licenza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenzaDTO result = licenzaService.save(licenzaDTO);
        return ResponseEntity.created(new URI("/api/licenzas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licenzas} : Updates an existing licenza.
     *
     * @param licenzaDTO the licenzaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenzaDTO,
     * or with status {@code 400 (Bad Request)} if the licenzaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenzaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licenzas")
    public ResponseEntity<LicenzaDTO> updateLicenza(@Valid @RequestBody LicenzaDTO licenzaDTO) throws URISyntaxException {
        log.debug("REST request to update Licenza : {}", licenzaDTO);
        if (licenzaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LicenzaDTO result = licenzaService.save(licenzaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licenzaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /licenzas} : get all the licenzas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenzas in body.
     */
    @GetMapping("/licenzas")
    public List<LicenzaDTO> getAllLicenzas() {
        log.debug("REST request to get all Licenzas");
        return licenzaService.findAll();
    }

    /**
     * {@code GET  /licenzas/:id} : get the "id" licenza.
     *
     * @param id the id of the licenzaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenzaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licenzas/{id}")
    public ResponseEntity<LicenzaDTO> getLicenza(@PathVariable Long id) {
        log.debug("REST request to get Licenza : {}", id);
        Optional<LicenzaDTO> licenzaDTO = licenzaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenzaDTO);
    }

    /**
     * {@code DELETE  /licenzas/:id} : delete the "id" licenza.
     *
     * @param id the id of the licenzaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licenzas/{id}")
    public ResponseEntity<Void> deleteLicenza(@PathVariable Long id) {
        log.debug("REST request to delete Licenza : {}", id);
        licenzaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/licenzas?query=:query} : search for the licenza corresponding
     * to the query.
     *
     * @param query the query of the licenza search.
     * @return the result of the search.
     */
    @GetMapping("/_search/licenzas")
    public List<LicenzaDTO> searchLicenzas(@RequestParam String query) {
        log.debug("REST request to search Licenzas for query {}", query);
        return licenzaService.search(query);
    }
}
