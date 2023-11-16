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
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.repository.NotaCreditoDetalleRepository;
import py.com.abf.service.NotaCreditoDetalleQueryService;
import py.com.abf.service.NotaCreditoDetalleService;
import py.com.abf.service.criteria.NotaCreditoDetalleCriteria;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.NotaCreditoDetalle}.
 */
@RestController
@RequestMapping("/api")
public class NotaCreditoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoDetalleResource.class);

    private static final String ENTITY_NAME = "notaCreditoDetalle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotaCreditoDetalleService notaCreditoDetalleService;

    private final NotaCreditoDetalleRepository notaCreditoDetalleRepository;

    private final NotaCreditoDetalleQueryService notaCreditoDetalleQueryService;

    public NotaCreditoDetalleResource(
        NotaCreditoDetalleService notaCreditoDetalleService,
        NotaCreditoDetalleRepository notaCreditoDetalleRepository,
        NotaCreditoDetalleQueryService notaCreditoDetalleQueryService
    ) {
        this.notaCreditoDetalleService = notaCreditoDetalleService;
        this.notaCreditoDetalleRepository = notaCreditoDetalleRepository;
        this.notaCreditoDetalleQueryService = notaCreditoDetalleQueryService;
    }

    /**
     * {@code POST  /nota-credito-detalles} : Create a new notaCreditoDetalle.
     *
     * @param notaCreditoDetalle the notaCreditoDetalle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notaCreditoDetalle, or with status {@code 400 (Bad Request)} if the notaCreditoDetalle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nota-credito-detalles")
    public ResponseEntity<NotaCreditoDetalle> createNotaCreditoDetalle(@Valid @RequestBody NotaCreditoDetalle notaCreditoDetalle)
        throws URISyntaxException {
        log.debug("REST request to save NotaCreditoDetalle : {}", notaCreditoDetalle);
        if (notaCreditoDetalle.getId() != null) {
            throw new BadRequestAlertException("A new notaCreditoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotaCreditoDetalle result = notaCreditoDetalleService.save(notaCreditoDetalle);
        return ResponseEntity
            .created(new URI("/api/nota-credito-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nota-credito-detalles/:id} : Updates an existing notaCreditoDetalle.
     *
     * @param id the id of the notaCreditoDetalle to save.
     * @param notaCreditoDetalle the notaCreditoDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notaCreditoDetalle,
     * or with status {@code 400 (Bad Request)} if the notaCreditoDetalle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notaCreditoDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nota-credito-detalles/{id}")
    public ResponseEntity<NotaCreditoDetalle> updateNotaCreditoDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotaCreditoDetalle notaCreditoDetalle
    ) throws URISyntaxException {
        log.debug("REST request to update NotaCreditoDetalle : {}, {}", id, notaCreditoDetalle);
        if (notaCreditoDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notaCreditoDetalle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notaCreditoDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotaCreditoDetalle result = notaCreditoDetalleService.update(notaCreditoDetalle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notaCreditoDetalle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nota-credito-detalles/:id} : Partial updates given fields of an existing notaCreditoDetalle, field will ignore if it is null
     *
     * @param id the id of the notaCreditoDetalle to save.
     * @param notaCreditoDetalle the notaCreditoDetalle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notaCreditoDetalle,
     * or with status {@code 400 (Bad Request)} if the notaCreditoDetalle is not valid,
     * or with status {@code 404 (Not Found)} if the notaCreditoDetalle is not found,
     * or with status {@code 500 (Internal Server Error)} if the notaCreditoDetalle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nota-credito-detalles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotaCreditoDetalle> partialUpdateNotaCreditoDetalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotaCreditoDetalle notaCreditoDetalle
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotaCreditoDetalle partially : {}, {}", id, notaCreditoDetalle);
        if (notaCreditoDetalle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notaCreditoDetalle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notaCreditoDetalleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotaCreditoDetalle> result = notaCreditoDetalleService.partialUpdate(notaCreditoDetalle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notaCreditoDetalle.getId().toString())
        );
    }

    /**
     * {@code GET  /nota-credito-detalles} : get all the notaCreditoDetalles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notaCreditoDetalles in body.
     */
    @GetMapping("/nota-credito-detalles")
    public ResponseEntity<List<NotaCreditoDetalle>> getAllNotaCreditoDetalles(
        NotaCreditoDetalleCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotaCreditoDetalles by criteria: {}", criteria);
        Page<NotaCreditoDetalle> page = notaCreditoDetalleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nota-credito-detalles/count} : count all the notaCreditoDetalles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nota-credito-detalles/count")
    public ResponseEntity<Long> countNotaCreditoDetalles(NotaCreditoDetalleCriteria criteria) {
        log.debug("REST request to count NotaCreditoDetalles by criteria: {}", criteria);
        return ResponseEntity.ok().body(notaCreditoDetalleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nota-credito-detalles/:id} : get the "id" notaCreditoDetalle.
     *
     * @param id the id of the notaCreditoDetalle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notaCreditoDetalle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nota-credito-detalles/{id}")
    public ResponseEntity<NotaCreditoDetalle> getNotaCreditoDetalle(@PathVariable Long id) {
        log.debug("REST request to get NotaCreditoDetalle : {}", id);
        Optional<NotaCreditoDetalle> notaCreditoDetalle = notaCreditoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notaCreditoDetalle);
    }

    /**
     * {@code DELETE  /nota-credito-detalles/:id} : delete the "id" notaCreditoDetalle.
     *
     * @param id the id of the notaCreditoDetalle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nota-credito-detalles/{id}")
    public ResponseEntity<Void> deleteNotaCreditoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete NotaCreditoDetalle : {}", id);
        notaCreditoDetalleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
