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
import py.com.abf.domain.MallaCurricular;
import py.com.abf.repository.MallaCurricularRepository;
import py.com.abf.service.MallaCurricularQueryService;
import py.com.abf.service.MallaCurricularService;
import py.com.abf.service.criteria.MallaCurricularCriteria;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.MallaCurricular}.
 */
@RestController
@RequestMapping("/api")
public class MallaCurricularResource {

    private final Logger log = LoggerFactory.getLogger(MallaCurricularResource.class);

    private static final String ENTITY_NAME = "mallaCurricular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MallaCurricularService mallaCurricularService;

    private final MallaCurricularRepository mallaCurricularRepository;

    private final MallaCurricularQueryService mallaCurricularQueryService;

    public MallaCurricularResource(
        MallaCurricularService mallaCurricularService,
        MallaCurricularRepository mallaCurricularRepository,
        MallaCurricularQueryService mallaCurricularQueryService
    ) {
        this.mallaCurricularService = mallaCurricularService;
        this.mallaCurricularRepository = mallaCurricularRepository;
        this.mallaCurricularQueryService = mallaCurricularQueryService;
    }

    /**
     * {@code POST  /malla-curriculars} : Create a new mallaCurricular.
     *
     * @param mallaCurricular the mallaCurricular to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mallaCurricular, or with status {@code 400 (Bad Request)} if the mallaCurricular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/malla-curriculars")
    public ResponseEntity<MallaCurricular> createMallaCurricular(@Valid @RequestBody MallaCurricular mallaCurricular)
        throws URISyntaxException {
        log.debug("REST request to save MallaCurricular : {}", mallaCurricular);
        if (mallaCurricular.getId() != null) {
            throw new BadRequestAlertException("A new mallaCurricular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MallaCurricular result = mallaCurricularService.save(mallaCurricular);
        return ResponseEntity
            .created(new URI("/api/malla-curriculars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /malla-curriculars/:id} : Updates an existing mallaCurricular.
     *
     * @param id the id of the mallaCurricular to save.
     * @param mallaCurricular the mallaCurricular to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mallaCurricular,
     * or with status {@code 400 (Bad Request)} if the mallaCurricular is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mallaCurricular couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/malla-curriculars/{id}")
    public ResponseEntity<MallaCurricular> updateMallaCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MallaCurricular mallaCurricular
    ) throws URISyntaxException {
        log.debug("REST request to update MallaCurricular : {}, {}", id, mallaCurricular);
        if (mallaCurricular.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mallaCurricular.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mallaCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MallaCurricular result = mallaCurricularService.update(mallaCurricular);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mallaCurricular.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /malla-curriculars/:id} : Partial updates given fields of an existing mallaCurricular, field will ignore if it is null
     *
     * @param id the id of the mallaCurricular to save.
     * @param mallaCurricular the mallaCurricular to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mallaCurricular,
     * or with status {@code 400 (Bad Request)} if the mallaCurricular is not valid,
     * or with status {@code 404 (Not Found)} if the mallaCurricular is not found,
     * or with status {@code 500 (Internal Server Error)} if the mallaCurricular couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/malla-curriculars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MallaCurricular> partialUpdateMallaCurricular(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MallaCurricular mallaCurricular
    ) throws URISyntaxException {
        log.debug("REST request to partial update MallaCurricular partially : {}, {}", id, mallaCurricular);
        if (mallaCurricular.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mallaCurricular.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mallaCurricularRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MallaCurricular> result = mallaCurricularService.partialUpdate(mallaCurricular);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mallaCurricular.getId().toString())
        );
    }

    /**
     * {@code GET  /malla-curriculars} : get all the mallaCurriculars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mallaCurriculars in body.
     */
    @GetMapping("/malla-curriculars")
    public ResponseEntity<List<MallaCurricular>> getAllMallaCurriculars(
        MallaCurricularCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MallaCurriculars by criteria: {}", criteria);
        Page<MallaCurricular> page = mallaCurricularQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /malla-curriculars/count} : count all the mallaCurriculars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/malla-curriculars/count")
    public ResponseEntity<Long> countMallaCurriculars(MallaCurricularCriteria criteria) {
        log.debug("REST request to count MallaCurriculars by criteria: {}", criteria);
        return ResponseEntity.ok().body(mallaCurricularQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /malla-curriculars/:id} : get the "id" mallaCurricular.
     *
     * @param id the id of the mallaCurricular to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mallaCurricular, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/malla-curriculars/{id}")
    public ResponseEntity<MallaCurricular> getMallaCurricular(@PathVariable Long id) {
        log.debug("REST request to get MallaCurricular : {}", id);
        Optional<MallaCurricular> mallaCurricular = mallaCurricularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mallaCurricular);
    }

    /**
     * {@code DELETE  /malla-curriculars/:id} : delete the "id" mallaCurricular.
     *
     * @param id the id of the mallaCurricular to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/malla-curriculars/{id}")
    public ResponseEntity<Void> deleteMallaCurricular(@PathVariable Long id) {
        log.debug("REST request to delete MallaCurricular : {}", id);
        mallaCurricularService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
