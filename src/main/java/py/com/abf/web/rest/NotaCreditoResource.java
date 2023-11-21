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
import py.com.abf.domain.FacturaConDetalle;
import py.com.abf.domain.Facturas;
import py.com.abf.domain.NCDetalle;
import py.com.abf.domain.NotaCredito;
import py.com.abf.repository.NotaCreditoRepository;
import py.com.abf.service.NotaCreditoQueryService;
import py.com.abf.service.NotaCreditoService;
import py.com.abf.service.criteria.NotaCreditoCriteria;
import py.com.abf.service.impl.NotaCreditoServiceImpl;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.NotaCredito}.
 */
@RestController
@RequestMapping("/api")
public class NotaCreditoResource {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoResource.class);

    private static final String ENTITY_NAME = "notaCredito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotaCreditoService notaCreditoService;

    private final NotaCreditoRepository notaCreditoRepository;

    private final NotaCreditoQueryService notaCreditoQueryService;

    private final NotaCreditoServiceImpl notaCreditoServiceImpl;

    public NotaCreditoResource(
        NotaCreditoService notaCreditoService,
        NotaCreditoRepository notaCreditoRepository,
        NotaCreditoQueryService notaCreditoQueryService,
        NotaCreditoServiceImpl notaCreditoServiceImpl
    ) {
        this.notaCreditoService = notaCreditoService;
        this.notaCreditoRepository = notaCreditoRepository;
        this.notaCreditoQueryService = notaCreditoQueryService;
        this.notaCreditoServiceImpl = notaCreditoServiceImpl;
    }

    /**
     * {@code POST  /nota-creditos} : Create a new notaCredito.
     *
     * @param notaCredito the notaCredito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notaCredito, or with status {@code 400 (Bad Request)} if the notaCredito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nota-creditos")
    public ResponseEntity<NotaCredito> createNotaCredito(@Valid @RequestBody NotaCredito notaCredito) throws URISyntaxException {
        log.debug("REST request to save NotaCredito : {}", notaCredito);
        if (notaCredito.getId() != null) {
            throw new BadRequestAlertException("A new notaCredito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotaCredito result = notaCreditoService.save(notaCredito);
        return ResponseEntity
            .created(new URI("/api/nota-creditos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nota-creditos/:id} : Updates an existing notaCredito.
     *
     * @param id the id of the notaCredito to save.
     * @param notaCredito the notaCredito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notaCredito,
     * or with status {@code 400 (Bad Request)} if the notaCredito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notaCredito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nota-creditos/{id}")
    public ResponseEntity<NotaCredito> updateNotaCredito(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NotaCredito notaCredito
    ) throws URISyntaxException {
        log.debug("REST request to update NotaCredito : {}, {}", id, notaCredito);
        if (notaCredito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notaCredito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notaCreditoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotaCredito result = notaCreditoService.update(notaCredito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notaCredito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nota-creditos/:id} : Partial updates given fields of an existing notaCredito, field will ignore if it is null
     *
     * @param id the id of the notaCredito to save.
     * @param notaCredito the notaCredito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notaCredito,
     * or with status {@code 400 (Bad Request)} if the notaCredito is not valid,
     * or with status {@code 404 (Not Found)} if the notaCredito is not found,
     * or with status {@code 500 (Internal Server Error)} if the notaCredito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nota-creditos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotaCredito> partialUpdateNotaCredito(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NotaCredito notaCredito
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotaCredito partially : {}, {}", id, notaCredito);
        if (notaCredito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notaCredito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notaCreditoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotaCredito> result = notaCreditoService.partialUpdate(notaCredito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notaCredito.getId().toString())
        );
    }

    /**
     * {@code GET  /nota-creditos} : get all the notaCreditos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notaCreditos in body.
     */
    @GetMapping("/nota-creditos")
    public ResponseEntity<List<NotaCredito>> getAllNotaCreditos(
        NotaCreditoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NotaCreditos by criteria: {}", criteria);
        Page<NotaCredito> page = notaCreditoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nota-creditos/count} : count all the notaCreditos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nota-creditos/count")
    public ResponseEntity<Long> countNotaCreditos(NotaCreditoCriteria criteria) {
        log.debug("REST request to count NotaCreditos by criteria: {}", criteria);
        return ResponseEntity.ok().body(notaCreditoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nota-creditos/:id} : get the "id" notaCredito.
     *
     * @param id the id of the notaCredito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notaCredito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nota-creditos/{id}")
    public ResponseEntity<NotaCredito> getNotaCredito(@PathVariable Long id) {
        log.debug("REST request to get NotaCredito : {}", id);
        Optional<NotaCredito> notaCredito = notaCreditoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notaCredito);
    }

    /**
     * {@code DELETE  /nota-creditos/:id} : delete the "id" notaCredito.
     *
     * @param id the id of the notaCredito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nota-creditos/{id}")
    public ResponseEntity<Void> deleteNotaCredito(@PathVariable Long id) {
        log.debug("REST request to delete NotaCredito : {}", id);
        notaCreditoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/nota-creditos/detalle")
    public ResponseEntity<NotaCredito> nuevoFactura(@Valid @RequestBody NCDetalle data) throws URISyntaxException {
        log.debug("REST request to save Facturas : {}", data);
        NotaCredito result = notaCreditoServiceImpl.saveWithDetails(data);
        return ResponseEntity.ok(result);
    }
}
