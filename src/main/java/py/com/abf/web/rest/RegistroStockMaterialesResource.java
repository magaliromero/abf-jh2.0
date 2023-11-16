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
import py.com.abf.domain.RegistroStockMateriales;
import py.com.abf.repository.RegistroStockMaterialesRepository;
import py.com.abf.service.RegistroStockMaterialesQueryService;
import py.com.abf.service.RegistroStockMaterialesService;
import py.com.abf.service.criteria.RegistroStockMaterialesCriteria;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.RegistroStockMateriales}.
 */
@RestController
@RequestMapping("/api")
public class RegistroStockMaterialesResource {

    private final Logger log = LoggerFactory.getLogger(RegistroStockMaterialesResource.class);

    private static final String ENTITY_NAME = "registroStockMateriales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistroStockMaterialesService registroStockMaterialesService;

    private final RegistroStockMaterialesRepository registroStockMaterialesRepository;

    private final RegistroStockMaterialesQueryService registroStockMaterialesQueryService;

    public RegistroStockMaterialesResource(
        RegistroStockMaterialesService registroStockMaterialesService,
        RegistroStockMaterialesRepository registroStockMaterialesRepository,
        RegistroStockMaterialesQueryService registroStockMaterialesQueryService
    ) {
        this.registroStockMaterialesService = registroStockMaterialesService;
        this.registroStockMaterialesRepository = registroStockMaterialesRepository;
        this.registroStockMaterialesQueryService = registroStockMaterialesQueryService;
    }

    /**
     * {@code POST  /registro-stock-materiales} : Create a new registroStockMateriales.
     *
     * @param registroStockMateriales the registroStockMateriales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registroStockMateriales, or with status {@code 400 (Bad Request)} if the registroStockMateriales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registro-stock-materiales")
    public ResponseEntity<RegistroStockMateriales> createRegistroStockMateriales(
        @Valid @RequestBody RegistroStockMateriales registroStockMateriales
    ) throws URISyntaxException {
        log.debug("REST request to save RegistroStockMateriales : {}", registroStockMateriales);
        if (registroStockMateriales.getId() != null) {
            throw new BadRequestAlertException("A new registroStockMateriales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistroStockMateriales result = registroStockMaterialesService.save(registroStockMateriales);
        return ResponseEntity
            .created(new URI("/api/registro-stock-materiales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registro-stock-materiales/:id} : Updates an existing registroStockMateriales.
     *
     * @param id the id of the registroStockMateriales to save.
     * @param registroStockMateriales the registroStockMateriales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroStockMateriales,
     * or with status {@code 400 (Bad Request)} if the registroStockMateriales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registroStockMateriales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registro-stock-materiales/{id}")
    public ResponseEntity<RegistroStockMateriales> updateRegistroStockMateriales(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegistroStockMateriales registroStockMateriales
    ) throws URISyntaxException {
        log.debug("REST request to update RegistroStockMateriales : {}, {}", id, registroStockMateriales);
        if (registroStockMateriales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroStockMateriales.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroStockMaterialesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegistroStockMateriales result = registroStockMaterialesService.update(registroStockMateriales);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroStockMateriales.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registro-stock-materiales/:id} : Partial updates given fields of an existing registroStockMateriales, field will ignore if it is null
     *
     * @param id the id of the registroStockMateriales to save.
     * @param registroStockMateriales the registroStockMateriales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroStockMateriales,
     * or with status {@code 400 (Bad Request)} if the registroStockMateriales is not valid,
     * or with status {@code 404 (Not Found)} if the registroStockMateriales is not found,
     * or with status {@code 500 (Internal Server Error)} if the registroStockMateriales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registro-stock-materiales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegistroStockMateriales> partialUpdateRegistroStockMateriales(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegistroStockMateriales registroStockMateriales
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegistroStockMateriales partially : {}, {}", id, registroStockMateriales);
        if (registroStockMateriales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroStockMateriales.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroStockMaterialesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegistroStockMateriales> result = registroStockMaterialesService.partialUpdate(registroStockMateriales);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroStockMateriales.getId().toString())
        );
    }

    /**
     * {@code GET  /registro-stock-materiales} : get all the registroStockMateriales.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registroStockMateriales in body.
     */
    @GetMapping("/registro-stock-materiales")
    public ResponseEntity<List<RegistroStockMateriales>> getAllRegistroStockMateriales(
        RegistroStockMaterialesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RegistroStockMateriales by criteria: {}", criteria);
        Page<RegistroStockMateriales> page = registroStockMaterialesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registro-stock-materiales/count} : count all the registroStockMateriales.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/registro-stock-materiales/count")
    public ResponseEntity<Long> countRegistroStockMateriales(RegistroStockMaterialesCriteria criteria) {
        log.debug("REST request to count RegistroStockMateriales by criteria: {}", criteria);
        return ResponseEntity.ok().body(registroStockMaterialesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /registro-stock-materiales/:id} : get the "id" registroStockMateriales.
     *
     * @param id the id of the registroStockMateriales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registroStockMateriales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registro-stock-materiales/{id}")
    public ResponseEntity<RegistroStockMateriales> getRegistroStockMateriales(@PathVariable Long id) {
        log.debug("REST request to get RegistroStockMateriales : {}", id);
        Optional<RegistroStockMateriales> registroStockMateriales = registroStockMaterialesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registroStockMateriales);
    }

    /**
     * {@code DELETE  /registro-stock-materiales/:id} : delete the "id" registroStockMateriales.
     *
     * @param id the id of the registroStockMateriales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registro-stock-materiales/{id}")
    public ResponseEntity<Void> deleteRegistroStockMateriales(@PathVariable Long id) {
        log.debug("REST request to delete RegistroStockMateriales : {}", id);
        registroStockMaterialesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
