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
import py.com.abf.domain.FichaPartidasTorneos;
import py.com.abf.repository.FichaPartidasTorneosRepository;
import py.com.abf.service.FichaPartidasTorneosQueryService;
import py.com.abf.service.FichaPartidasTorneosService;
import py.com.abf.service.criteria.FichaPartidasTorneosCriteria;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.FichaPartidasTorneos}.
 */
@RestController
@RequestMapping("/api")
public class FichaPartidasTorneosResource {

    private final Logger log = LoggerFactory.getLogger(FichaPartidasTorneosResource.class);

    private static final String ENTITY_NAME = "fichaPartidasTorneos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaPartidasTorneosService fichaPartidasTorneosService;

    private final FichaPartidasTorneosRepository fichaPartidasTorneosRepository;

    private final FichaPartidasTorneosQueryService fichaPartidasTorneosQueryService;

    public FichaPartidasTorneosResource(
        FichaPartidasTorneosService fichaPartidasTorneosService,
        FichaPartidasTorneosRepository fichaPartidasTorneosRepository,
        FichaPartidasTorneosQueryService fichaPartidasTorneosQueryService
    ) {
        this.fichaPartidasTorneosService = fichaPartidasTorneosService;
        this.fichaPartidasTorneosRepository = fichaPartidasTorneosRepository;
        this.fichaPartidasTorneosQueryService = fichaPartidasTorneosQueryService;
    }

    /**
     * {@code POST  /ficha-partidas-torneos} : Create a new fichaPartidasTorneos.
     *
     * @param fichaPartidasTorneos the fichaPartidasTorneos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaPartidasTorneos, or with status {@code 400 (Bad Request)} if the fichaPartidasTorneos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ficha-partidas-torneos")
    public ResponseEntity<FichaPartidasTorneos> createFichaPartidasTorneos(@Valid @RequestBody FichaPartidasTorneos fichaPartidasTorneos)
        throws URISyntaxException {
        log.debug("REST request to save FichaPartidasTorneos : {}", fichaPartidasTorneos);
        if (fichaPartidasTorneos.getId() != null) {
            throw new BadRequestAlertException("A new fichaPartidasTorneos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaPartidasTorneos result = fichaPartidasTorneosService.save(fichaPartidasTorneos);
        return ResponseEntity
            .created(new URI("/api/ficha-partidas-torneos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ficha-partidas-torneos/:id} : Updates an existing fichaPartidasTorneos.
     *
     * @param id the id of the fichaPartidasTorneos to save.
     * @param fichaPartidasTorneos the fichaPartidasTorneos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaPartidasTorneos,
     * or with status {@code 400 (Bad Request)} if the fichaPartidasTorneos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaPartidasTorneos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ficha-partidas-torneos/{id}")
    public ResponseEntity<FichaPartidasTorneos> updateFichaPartidasTorneos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FichaPartidasTorneos fichaPartidasTorneos
    ) throws URISyntaxException {
        log.debug("REST request to update FichaPartidasTorneos : {}, {}", id, fichaPartidasTorneos);
        if (fichaPartidasTorneos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fichaPartidasTorneos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fichaPartidasTorneosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FichaPartidasTorneos result = fichaPartidasTorneosService.update(fichaPartidasTorneos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaPartidasTorneos.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ficha-partidas-torneos/:id} : Partial updates given fields of an existing fichaPartidasTorneos, field will ignore if it is null
     *
     * @param id the id of the fichaPartidasTorneos to save.
     * @param fichaPartidasTorneos the fichaPartidasTorneos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaPartidasTorneos,
     * or with status {@code 400 (Bad Request)} if the fichaPartidasTorneos is not valid,
     * or with status {@code 404 (Not Found)} if the fichaPartidasTorneos is not found,
     * or with status {@code 500 (Internal Server Error)} if the fichaPartidasTorneos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ficha-partidas-torneos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FichaPartidasTorneos> partialUpdateFichaPartidasTorneos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FichaPartidasTorneos fichaPartidasTorneos
    ) throws URISyntaxException {
        log.debug("REST request to partial update FichaPartidasTorneos partially : {}, {}", id, fichaPartidasTorneos);
        if (fichaPartidasTorneos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fichaPartidasTorneos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fichaPartidasTorneosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FichaPartidasTorneos> result = fichaPartidasTorneosService.partialUpdate(fichaPartidasTorneos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaPartidasTorneos.getId().toString())
        );
    }

    /**
     * {@code GET  /ficha-partidas-torneos} : get all the fichaPartidasTorneos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichaPartidasTorneos in body.
     */
    @GetMapping("/ficha-partidas-torneos")
    public ResponseEntity<List<FichaPartidasTorneos>> getAllFichaPartidasTorneos(
        FichaPartidasTorneosCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FichaPartidasTorneos by criteria: {}", criteria);
        Page<FichaPartidasTorneos> page = fichaPartidasTorneosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ficha-partidas-torneos/count} : count all the fichaPartidasTorneos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ficha-partidas-torneos/count")
    public ResponseEntity<Long> countFichaPartidasTorneos(FichaPartidasTorneosCriteria criteria) {
        log.debug("REST request to count FichaPartidasTorneos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fichaPartidasTorneosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ficha-partidas-torneos/:id} : get the "id" fichaPartidasTorneos.
     *
     * @param id the id of the fichaPartidasTorneos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaPartidasTorneos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ficha-partidas-torneos/{id}")
    public ResponseEntity<FichaPartidasTorneos> getFichaPartidasTorneos(@PathVariable Long id) {
        log.debug("REST request to get FichaPartidasTorneos : {}", id);
        Optional<FichaPartidasTorneos> fichaPartidasTorneos = fichaPartidasTorneosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaPartidasTorneos);
    }

    /**
     * {@code DELETE  /ficha-partidas-torneos/:id} : delete the "id" fichaPartidasTorneos.
     *
     * @param id the id of the fichaPartidasTorneos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ficha-partidas-torneos/{id}")
    public ResponseEntity<Void> deleteFichaPartidasTorneos(@PathVariable Long id) {
        log.debug("REST request to delete FichaPartidasTorneos : {}", id);
        fichaPartidasTorneosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
