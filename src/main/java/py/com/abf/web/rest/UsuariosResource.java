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
import py.com.abf.domain.Usuarios;
import py.com.abf.repository.UsuariosRepository;
import py.com.abf.service.UsuariosQueryService;
import py.com.abf.service.UsuariosService;
import py.com.abf.service.criteria.UsuariosCriteria;
import py.com.abf.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link py.com.abf.domain.Usuarios}.
 */
@RestController
@RequestMapping("/api")
public class UsuariosResource {

    private final Logger log = LoggerFactory.getLogger(UsuariosResource.class);

    private static final String ENTITY_NAME = "usuarios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuariosService usuariosService;

    private final UsuariosRepository usuariosRepository;

    private final UsuariosQueryService usuariosQueryService;

    public UsuariosResource(
        UsuariosService usuariosService,
        UsuariosRepository usuariosRepository,
        UsuariosQueryService usuariosQueryService
    ) {
        this.usuariosService = usuariosService;
        this.usuariosRepository = usuariosRepository;
        this.usuariosQueryService = usuariosQueryService;
    }

    /**
     * {@code POST  /usuarios} : Create a new usuarios.
     *
     * @param usuarios the usuarios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarios, or with status {@code 400 (Bad Request)} if the usuarios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuarios")
    public ResponseEntity<Usuarios> createUsuarios(@Valid @RequestBody Usuarios usuarios) throws URISyntaxException {
        log.debug("REST request to save Usuarios : {}", usuarios);
        if (usuarios.getId() != null) {
            throw new BadRequestAlertException("A new usuarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Usuarios result = usuariosService.save(usuarios);
        return ResponseEntity
            .created(new URI("/api/usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuarios/:id} : Updates an existing usuarios.
     *
     * @param id the id of the usuarios to save.
     * @param usuarios the usuarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarios,
     * or with status {@code 400 (Bad Request)} if the usuarios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> updateUsuarios(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Usuarios usuarios
    ) throws URISyntaxException {
        log.debug("REST request to update Usuarios : {}, {}", id, usuarios);
        if (usuarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Usuarios result = usuariosService.update(usuarios);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarios.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /usuarios/:id} : Partial updates given fields of an existing usuarios, field will ignore if it is null
     *
     * @param id the id of the usuarios to save.
     * @param usuarios the usuarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarios,
     * or with status {@code 400 (Bad Request)} if the usuarios is not valid,
     * or with status {@code 404 (Not Found)} if the usuarios is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/usuarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Usuarios> partialUpdateUsuarios(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Usuarios usuarios
    ) throws URISyntaxException {
        log.debug("REST request to partial update Usuarios partially : {}, {}", id, usuarios);
        if (usuarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Usuarios> result = usuariosService.partialUpdate(usuarios);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarios.getId().toString())
        );
    }

    /**
     * {@code GET  /usuarios} : get all the usuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarios in body.
     */
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuarios>> getAllUsuarios(
        UsuariosCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Usuarios by criteria: {}", criteria);
        Page<Usuarios> page = usuariosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuarios/count} : count all the usuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/usuarios/count")
    public ResponseEntity<Long> countUsuarios(UsuariosCriteria criteria) {
        log.debug("REST request to count Usuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(usuariosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /usuarios/:id} : get the "id" usuarios.
     *
     * @param id the id of the usuarios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuarios> getUsuarios(@PathVariable Long id) {
        log.debug("REST request to get Usuarios : {}", id);
        Optional<Usuarios> usuarios = usuariosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarios);
    }

    /**
     * {@code DELETE  /usuarios/:id} : delete the "id" usuarios.
     *
     * @param id the id of the usuarios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuarios(@PathVariable Long id) {
        log.debug("REST request to delete Usuarios : {}", id);
        usuariosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
