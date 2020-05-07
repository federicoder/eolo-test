package it.maggioli.web.rest;

import it.maggioli.service.StorageCloudService;
import it.maggioli.web.rest.errors.BadRequestAlertException;
import it.maggioli.service.dto.StorageCloudDTO;

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
 * REST controller for managing {@link it.maggioli.domain.StorageCloud}.
 */
@RestController
@RequestMapping("/api")
public class StorageCloudResource {

    private final Logger log = LoggerFactory.getLogger(StorageCloudResource.class);

    private static final String ENTITY_NAME = "storageCloud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageCloudService storageCloudService;

    public StorageCloudResource(StorageCloudService storageCloudService) {
        this.storageCloudService = storageCloudService;
    }

    /**
     * {@code POST  /storage-clouds} : Create a new storageCloud.
     *
     * @param storageCloudDTO the storageCloudDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storageCloudDTO, or with status {@code 400 (Bad Request)} if the storageCloud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/storage-clouds")
    public ResponseEntity<StorageCloudDTO> createStorageCloud(@Valid @RequestBody StorageCloudDTO storageCloudDTO) throws URISyntaxException {
        log.debug("REST request to save StorageCloud : {}", storageCloudDTO);
        if (storageCloudDTO.getId() != null) {
            throw new BadRequestAlertException("A new storageCloud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorageCloudDTO result = storageCloudService.save(storageCloudDTO);
        return ResponseEntity.created(new URI("/api/storage-clouds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /storage-clouds} : Updates an existing storageCloud.
     *
     * @param storageCloudDTO the storageCloudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storageCloudDTO,
     * or with status {@code 400 (Bad Request)} if the storageCloudDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storageCloudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/storage-clouds")
    public ResponseEntity<StorageCloudDTO> updateStorageCloud(@Valid @RequestBody StorageCloudDTO storageCloudDTO) throws URISyntaxException {
        log.debug("REST request to update StorageCloud : {}", storageCloudDTO);
        if (storageCloudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StorageCloudDTO result = storageCloudService.save(storageCloudDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storageCloudDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /storage-clouds} : get all the storageClouds.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storageClouds in body.
     */
    @GetMapping("/storage-clouds")
    public List<StorageCloudDTO> getAllStorageClouds(@RequestParam(required = false) String filter) {
        if ("licenza-is-null".equals(filter)) {
            log.debug("REST request to get all StorageClouds where licenza is null");
            return storageCloudService.findAllWhereLicenzaIsNull();
        }
        if ("professionista-is-null".equals(filter)) {
            log.debug("REST request to get all StorageClouds where professionista is null");
            return storageCloudService.findAllWhereProfessionistaIsNull();
        }
        log.debug("REST request to get all StorageClouds");
        return storageCloudService.findAll();
    }

    /**
     * {@code GET  /storage-clouds/:id} : get the "id" storageCloud.
     *
     * @param id the id of the storageCloudDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storageCloudDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/storage-clouds/{id}")
    public ResponseEntity<StorageCloudDTO> getStorageCloud(@PathVariable Long id) {
        log.debug("REST request to get StorageCloud : {}", id);
        Optional<StorageCloudDTO> storageCloudDTO = storageCloudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storageCloudDTO);
    }

    /**
     * {@code DELETE  /storage-clouds/:id} : delete the "id" storageCloud.
     *
     * @param id the id of the storageCloudDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/storage-clouds/{id}")
    public ResponseEntity<Void> deleteStorageCloud(@PathVariable Long id) {
        log.debug("REST request to delete StorageCloud : {}", id);
        storageCloudService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/storage-clouds?query=:query} : search for the storageCloud corresponding
     * to the query.
     *
     * @param query the query of the storageCloud search.
     * @return the result of the search.
     */
    @GetMapping("/_search/storage-clouds")
    public List<StorageCloudDTO> searchStorageClouds(@RequestParam String query) {
        log.debug("REST request to search StorageClouds for query {}", query);
        return storageCloudService.search(query);
    }
}
