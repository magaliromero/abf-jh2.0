package py.com.abf.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import py.com.abf.domain.Torneos;
import py.com.abf.repository.TorneosRepository;
import py.com.abf.service.TorneosQueryService;
import py.com.abf.service.TorneosService;
import py.com.abf.service.criteria.TorneosCriteria;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.Torneos}.
 */
@RestController
@RequestMapping("/api")
public class TorneosResource {

    private final Logger log = LoggerFactory.getLogger(TorneosResource.class);

    private static final String ENTITY_NAME = "torneos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TorneosService torneosService;

    private final TorneosRepository torneosRepository;

    private final TorneosQueryService torneosQueryService;

    public TorneosResource(TorneosService torneosService, TorneosRepository torneosRepository, TorneosQueryService torneosQueryService) {
        this.torneosService = torneosService;
        this.torneosRepository = torneosRepository;
        this.torneosQueryService = torneosQueryService;
    }

    /**
     * {@code POST  /torneos} : Create a new torneos.
     *
     * @param torneos the torneos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new torneos, or with status {@code 400 (Bad Request)} if the torneos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/torneos")
    public ResponseEntity<Torneos> createTorneos(@Valid @RequestBody Torneos torneos) throws URISyntaxException {
        log.debug("REST request to save Torneos : {}", torneos);
        if (torneos.getId() != null) {
            throw new BadRequestAlertException("A new torneos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Torneos result = torneosService.save(torneos);
        return ResponseEntity
            .created(new URI("/api/torneos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /torneos/:id} : Updates an existing torneos.
     *
     * @param id the id of the torneos to save.
     * @param torneos the torneos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated torneos,
     * or with status {@code 400 (Bad Request)} if the torneos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the torneos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/torneos/{id}")
    public ResponseEntity<Torneos> updateTorneos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Torneos torneos
    ) throws URISyntaxException {
        log.debug("REST request to update Torneos : {}, {}", id, torneos);
        if (torneos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, torneos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!torneosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Torneos result = torneosService.update(torneos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, torneos.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /torneos/:id} : Partial updates given fields of an existing torneos, field will ignore if it is null
     *
     * @param id the id of the torneos to save.
     * @param torneos the torneos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated torneos,
     * or with status {@code 400 (Bad Request)} if the torneos is not valid,
     * or with status {@code 404 (Not Found)} if the torneos is not found,
     * or with status {@code 500 (Internal Server Error)} if the torneos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/torneos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Torneos> partialUpdateTorneos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Torneos torneos
    ) throws URISyntaxException {
        log.debug("REST request to partial update Torneos partially : {}, {}", id, torneos);
        if (torneos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, torneos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!torneosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Torneos> result = torneosService.partialUpdate(torneos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, torneos.getId().toString())
        );
    }

    /**
     * {@code GET  /torneos} : get all the torneos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of torneos in body.
     */
    @GetMapping("/torneos")
    public ResponseEntity<List<Torneos>> getAllTorneos(
        TorneosCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Torneos by criteria: {}", criteria);
        Page<Torneos> page = torneosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /torneos/count} : count all the torneos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/torneos/count")
    public ResponseEntity<Long> countTorneos(TorneosCriteria criteria) {
        log.debug("REST request to count Torneos by criteria: {}", criteria);
        return ResponseEntity.ok().body(torneosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /torneos/:id} : get the "id" torneos.
     *
     * @param id the id of the torneos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the torneos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/torneos/{id}")
    public ResponseEntity<Torneos> getTorneos(@PathVariable Long id) {
        log.debug("REST request to get Torneos : {}", id);
        Optional<Torneos> torneos = torneosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(torneos);
    }

    /**
     * {@code DELETE  /torneos/:id} : delete the "id" torneos.
     *
     * @param id the id of the torneos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/torneos/{id}")
    public ResponseEntity<Void> deleteTorneos(@PathVariable Long id) {
        log.debug("REST request to delete Torneos : {}", id);
        torneosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
