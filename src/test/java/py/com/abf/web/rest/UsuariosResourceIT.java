package py.com.abf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.IntegrationTest;
import py.com.abf.domain.Usuarios;
import py.com.abf.repository.UsuariosRepository;
import py.com.abf.service.criteria.UsuariosCriteria;

/**
 * Integration tests for the {@link UsuariosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsuariosResourceIT {

    private static final Integer DEFAULT_DOCUMENTO = 1;
    private static final Integer UPDATED_DOCUMENTO = 2;
    private static final Integer SMALLER_DOCUMENTO = 1 - 1;

    private static final Integer DEFAULT_ID_ROL = 1;
    private static final Integer UPDATED_ID_ROL = 2;
    private static final Integer SMALLER_ID_ROL = 1 - 1;

    private static final String ENTITY_API_URL = "/api/usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuariosMockMvc;

    private Usuarios usuarios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios().documento(DEFAULT_DOCUMENTO).idRol(DEFAULT_ID_ROL);
        return usuarios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createUpdatedEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios().documento(UPDATED_DOCUMENTO).idRol(UPDATED_ID_ROL);
        return usuarios;
    }

    @BeforeEach
    public void initTest() {
        usuarios = createEntity(em);
    }

    @Test
    @Transactional
    void createUsuarios() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();
        // Create the Usuarios
        restUsuariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isCreated());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate + 1);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testUsuarios.getIdRol()).isEqualTo(DEFAULT_ID_ROL);
    }

    @Test
    @Transactional
    void createUsuariosWithExistingId() throws Exception {
        // Create the Usuarios with an existing ID
        usuarios.setId(1L);

        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().size();
        // set the field null
        usuarios.setDocumento(null);

        // Create the Usuarios, which fails.

        restUsuariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().size();
        // set the field null
        usuarios.setIdRol(null);

        // Create the Usuarios, which fails.

        restUsuariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList
        restUsuariosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].idRol").value(hasItem(DEFAULT_ID_ROL)));
    }

    @Test
    @Transactional
    void getUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get the usuarios
        restUsuariosMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarios.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.idRol").value(DEFAULT_ID_ROL));
    }

    @Test
    @Transactional
    void getUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        Long id = usuarios.getId();

        defaultUsuariosShouldBeFound("id.equals=" + id);
        defaultUsuariosShouldNotBeFound("id.notEquals=" + id);

        defaultUsuariosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsuariosShouldNotBeFound("id.greaterThan=" + id);

        defaultUsuariosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsuariosShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento equals to DEFAULT_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.equals=" + DEFAULT_DOCUMENTO);

        // Get all the usuariosList where documento equals to UPDATED_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.equals=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento in DEFAULT_DOCUMENTO or UPDATED_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.in=" + DEFAULT_DOCUMENTO + "," + UPDATED_DOCUMENTO);

        // Get all the usuariosList where documento equals to UPDATED_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.in=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento is not null
        defaultUsuariosShouldBeFound("documento.specified=true");

        // Get all the usuariosList where documento is null
        defaultUsuariosShouldNotBeFound("documento.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento is greater than or equal to DEFAULT_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.greaterThanOrEqual=" + DEFAULT_DOCUMENTO);

        // Get all the usuariosList where documento is greater than or equal to UPDATED_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.greaterThanOrEqual=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento is less than or equal to DEFAULT_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.lessThanOrEqual=" + DEFAULT_DOCUMENTO);

        // Get all the usuariosList where documento is less than or equal to SMALLER_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.lessThanOrEqual=" + SMALLER_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsLessThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento is less than DEFAULT_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.lessThan=" + DEFAULT_DOCUMENTO);

        // Get all the usuariosList where documento is less than UPDATED_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.lessThan=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByDocumentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where documento is greater than DEFAULT_DOCUMENTO
        defaultUsuariosShouldNotBeFound("documento.greaterThan=" + DEFAULT_DOCUMENTO);

        // Get all the usuariosList where documento is greater than SMALLER_DOCUMENTO
        defaultUsuariosShouldBeFound("documento.greaterThan=" + SMALLER_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol equals to DEFAULT_ID_ROL
        defaultUsuariosShouldBeFound("idRol.equals=" + DEFAULT_ID_ROL);

        // Get all the usuariosList where idRol equals to UPDATED_ID_ROL
        defaultUsuariosShouldNotBeFound("idRol.equals=" + UPDATED_ID_ROL);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol in DEFAULT_ID_ROL or UPDATED_ID_ROL
        defaultUsuariosShouldBeFound("idRol.in=" + DEFAULT_ID_ROL + "," + UPDATED_ID_ROL);

        // Get all the usuariosList where idRol equals to UPDATED_ID_ROL
        defaultUsuariosShouldNotBeFound("idRol.in=" + UPDATED_ID_ROL);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol is not null
        defaultUsuariosShouldBeFound("idRol.specified=true");

        // Get all the usuariosList where idRol is null
        defaultUsuariosShouldNotBeFound("idRol.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol is greater than or equal to DEFAULT_ID_ROL
        defaultUsuariosShouldBeFound("idRol.greaterThanOrEqual=" + DEFAULT_ID_ROL);

        // Get all the usuariosList where idRol is greater than or equal to UPDATED_ID_ROL
        defaultUsuariosShouldNotBeFound("idRol.greaterThanOrEqual=" + UPDATED_ID_ROL);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol is less than or equal to DEFAULT_ID_ROL
        defaultUsuariosShouldBeFound("idRol.lessThanOrEqual=" + DEFAULT_ID_ROL);

        // Get all the usuariosList where idRol is less than or equal to SMALLER_ID_ROL
        defaultUsuariosShouldNotBeFound("idRol.lessThanOrEqual=" + SMALLER_ID_ROL);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsLessThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol is less than DEFAULT_ID_ROL
        defaultUsuariosShouldNotBeFound("idRol.lessThan=" + DEFAULT_ID_ROL);

        // Get all the usuariosList where idRol is less than UPDATED_ID_ROL
        defaultUsuariosShouldBeFound("idRol.lessThan=" + UPDATED_ID_ROL);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdRolIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where idRol is greater than DEFAULT_ID_ROL
        defaultUsuariosShouldNotBeFound("idRol.greaterThan=" + DEFAULT_ID_ROL);

        // Get all the usuariosList where idRol is greater than SMALLER_ID_ROL
        defaultUsuariosShouldBeFound("idRol.greaterThan=" + SMALLER_ID_ROL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuariosShouldBeFound(String filter) throws Exception {
        restUsuariosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].idRol").value(hasItem(DEFAULT_ID_ROL)));

        // Check, that the count call also returns 1
        restUsuariosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuariosShouldNotBeFound(String filter) throws Exception {
        restUsuariosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuariosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsuarios() throws Exception {
        // Get the usuarios
        restUsuariosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Update the usuarios
        Usuarios updatedUsuarios = usuariosRepository.findById(usuarios.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarios are not directly saved in db
        em.detach(updatedUsuarios);
        updatedUsuarios.documento(UPDATED_DOCUMENTO).idRol(UPDATED_ID_ROL);

        restUsuariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUsuarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUsuarios))
            )
            .andExpect(status().isOk());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testUsuarios.getIdRol()).isEqualTo(UPDATED_ID_ROL);
    }

    @Test
    @Transactional
    void putNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();
        usuarios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuariosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuariosWithPatch() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Update the usuarios using partial update
        Usuarios partialUpdatedUsuarios = new Usuarios();
        partialUpdatedUsuarios.setId(usuarios.getId());

        restUsuariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarios))
            )
            .andExpect(status().isOk());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testUsuarios.getIdRol()).isEqualTo(DEFAULT_ID_ROL);
    }

    @Test
    @Transactional
    void fullUpdateUsuariosWithPatch() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Update the usuarios using partial update
        Usuarios partialUpdatedUsuarios = new Usuarios();
        partialUpdatedUsuarios.setId(usuarios.getId());

        partialUpdatedUsuarios.documento(UPDATED_DOCUMENTO).idRol(UPDATED_ID_ROL);

        restUsuariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarios))
            )
            .andExpect(status().isOk());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testUsuarios.getIdRol()).isEqualTo(UPDATED_ID_ROL);
    }

    @Test
    @Transactional
    void patchNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();
        usuarios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();
        usuarios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuariosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        int databaseSizeBeforeDelete = usuariosRepository.findAll().size();

        // Delete the usuarios
        restUsuariosMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
