package py.com.abf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.IntegrationTest;
import py.com.abf.domain.Alumnos;
import py.com.abf.domain.Inscripciones;
import py.com.abf.repository.InscripcionesRepository;
import py.com.abf.service.InscripcionesService;
import py.com.abf.service.criteria.InscripcionesCriteria;

/**
 * Integration tests for the {@link InscripcionesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InscripcionesResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/inscripciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InscripcionesRepository inscripcionesRepository;

    @Mock
    private InscripcionesRepository inscripcionesRepositoryMock;

    @Mock
    private InscripcionesService inscripcionesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInscripcionesMockMvc;

    private Inscripciones inscripciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscripciones createEntity(EntityManager em) {
        Inscripciones inscripciones = new Inscripciones().fecha(DEFAULT_FECHA);
        // Add required entity
        Alumnos alumnos;
        if (TestUtil.findAll(em, Alumnos.class).isEmpty()) {
            alumnos = AlumnosResourceIT.createEntity(em);
            em.persist(alumnos);
            em.flush();
        } else {
            alumnos = TestUtil.findAll(em, Alumnos.class).get(0);
        }
        inscripciones.setAlumnos(alumnos);
        return inscripciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscripciones createUpdatedEntity(EntityManager em) {
        Inscripciones inscripciones = new Inscripciones().fecha(UPDATED_FECHA);
        // Add required entity
        Alumnos alumnos;
        if (TestUtil.findAll(em, Alumnos.class).isEmpty()) {
            alumnos = AlumnosResourceIT.createUpdatedEntity(em);
            em.persist(alumnos);
            em.flush();
        } else {
            alumnos = TestUtil.findAll(em, Alumnos.class).get(0);
        }
        inscripciones.setAlumnos(alumnos);
        return inscripciones;
    }

    @BeforeEach
    public void initTest() {
        inscripciones = createEntity(em);
    }

    @Test
    @Transactional
    void createInscripciones() throws Exception {
        int databaseSizeBeforeCreate = inscripcionesRepository.findAll().size();
        // Create the Inscripciones
        restInscripcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscripciones)))
            .andExpect(status().isCreated());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeCreate + 1);
        Inscripciones testInscripciones = inscripcionesList.get(inscripcionesList.size() - 1);
        assertThat(testInscripciones.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void createInscripcionesWithExistingId() throws Exception {
        // Create the Inscripciones with an existing ID
        inscripciones.setId(1L);

        int databaseSizeBeforeCreate = inscripcionesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscripcionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscripciones)))
            .andExpect(status().isBadRequest());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInscripciones() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList
        restInscripcionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscripciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInscripcionesWithEagerRelationshipsIsEnabled() throws Exception {
        when(inscripcionesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInscripcionesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inscripcionesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInscripcionesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inscripcionesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInscripcionesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(inscripcionesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInscripciones() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get the inscripciones
        restInscripcionesMockMvc
            .perform(get(ENTITY_API_URL_ID, inscripciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscripciones.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getInscripcionesByIdFiltering() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        Long id = inscripciones.getId();

        defaultInscripcionesShouldBeFound("id.equals=" + id);
        defaultInscripcionesShouldNotBeFound("id.notEquals=" + id);

        defaultInscripcionesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInscripcionesShouldNotBeFound("id.greaterThan=" + id);

        defaultInscripcionesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInscripcionesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha equals to DEFAULT_FECHA
        defaultInscripcionesShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the inscripcionesList where fecha equals to UPDATED_FECHA
        defaultInscripcionesShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultInscripcionesShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the inscripcionesList where fecha equals to UPDATED_FECHA
        defaultInscripcionesShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha is not null
        defaultInscripcionesShouldBeFound("fecha.specified=true");

        // Get all the inscripcionesList where fecha is null
        defaultInscripcionesShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha is greater than or equal to DEFAULT_FECHA
        defaultInscripcionesShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the inscripcionesList where fecha is greater than or equal to UPDATED_FECHA
        defaultInscripcionesShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha is less than or equal to DEFAULT_FECHA
        defaultInscripcionesShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the inscripcionesList where fecha is less than or equal to SMALLER_FECHA
        defaultInscripcionesShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha is less than DEFAULT_FECHA
        defaultInscripcionesShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the inscripcionesList where fecha is less than UPDATED_FECHA
        defaultInscripcionesShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllInscripcionesByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        // Get all the inscripcionesList where fecha is greater than DEFAULT_FECHA
        defaultInscripcionesShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the inscripcionesList where fecha is greater than SMALLER_FECHA
        defaultInscripcionesShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    void getAllInscripcionesByAlumnosIsEqualToSomething() throws Exception {
        Alumnos alumnos;
        if (TestUtil.findAll(em, Alumnos.class).isEmpty()) {
            inscripcionesRepository.saveAndFlush(inscripciones);
            alumnos = AlumnosResourceIT.createEntity(em);
        } else {
            alumnos = TestUtil.findAll(em, Alumnos.class).get(0);
        }
        em.persist(alumnos);
        em.flush();
        inscripciones.setAlumnos(alumnos);
        inscripcionesRepository.saveAndFlush(inscripciones);
        Long alumnosId = alumnos.getId();

        // Get all the inscripcionesList where alumnos equals to alumnosId
        defaultInscripcionesShouldBeFound("alumnosId.equals=" + alumnosId);

        // Get all the inscripcionesList where alumnos equals to (alumnosId + 1)
        defaultInscripcionesShouldNotBeFound("alumnosId.equals=" + (alumnosId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInscripcionesShouldBeFound(String filter) throws Exception {
        restInscripcionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscripciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restInscripcionesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInscripcionesShouldNotBeFound(String filter) throws Exception {
        restInscripcionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInscripcionesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInscripciones() throws Exception {
        // Get the inscripciones
        restInscripcionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInscripciones() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();

        // Update the inscripciones
        Inscripciones updatedInscripciones = inscripcionesRepository.findById(inscripciones.getId()).get();
        // Disconnect from session so that the updates on updatedInscripciones are not directly saved in db
        em.detach(updatedInscripciones);
        updatedInscripciones.fecha(UPDATED_FECHA);

        restInscripcionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInscripciones.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInscripciones))
            )
            .andExpect(status().isOk());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
        Inscripciones testInscripciones = inscripcionesList.get(inscripcionesList.size() - 1);
        assertThat(testInscripciones.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void putNonExistingInscripciones() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();
        inscripciones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscripcionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inscripciones.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscripciones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInscripciones() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();
        inscripciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inscripciones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInscripciones() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();
        inscripciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inscripciones)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInscripcionesWithPatch() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();

        // Update the inscripciones using partial update
        Inscripciones partialUpdatedInscripciones = new Inscripciones();
        partialUpdatedInscripciones.setId(inscripciones.getId());

        restInscripcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscripciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscripciones))
            )
            .andExpect(status().isOk());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
        Inscripciones testInscripciones = inscripcionesList.get(inscripcionesList.size() - 1);
        assertThat(testInscripciones.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void fullUpdateInscripcionesWithPatch() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();

        // Update the inscripciones using partial update
        Inscripciones partialUpdatedInscripciones = new Inscripciones();
        partialUpdatedInscripciones.setId(inscripciones.getId());

        partialUpdatedInscripciones.fecha(UPDATED_FECHA);

        restInscripcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInscripciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInscripciones))
            )
            .andExpect(status().isOk());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
        Inscripciones testInscripciones = inscripcionesList.get(inscripcionesList.size() - 1);
        assertThat(testInscripciones.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void patchNonExistingInscripciones() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();
        inscripciones.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscripcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inscripciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscripciones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInscripciones() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();
        inscripciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inscripciones))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInscripciones() throws Exception {
        int databaseSizeBeforeUpdate = inscripcionesRepository.findAll().size();
        inscripciones.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInscripcionesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inscripciones))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inscripciones in the database
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInscripciones() throws Exception {
        // Initialize the database
        inscripcionesRepository.saveAndFlush(inscripciones);

        int databaseSizeBeforeDelete = inscripcionesRepository.findAll().size();

        // Delete the inscripciones
        restInscripcionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, inscripciones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inscripciones> inscripcionesList = inscripcionesRepository.findAll();
        assertThat(inscripcionesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
