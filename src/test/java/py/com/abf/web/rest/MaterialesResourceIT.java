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
import py.com.abf.domain.Materiales;
import py.com.abf.repository.MaterialesRepository;
import py.com.abf.service.criteria.MaterialesCriteria;

/**
 * Integration tests for the {@link MaterialesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;
    private static final Integer SMALLER_CANTIDAD = 1 - 1;

    private static final String ENTITY_API_URL = "/api/materiales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialesRepository materialesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialesMockMvc;

    private Materiales materiales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiales createEntity(EntityManager em) {
        Materiales materiales = new Materiales().descripcion(DEFAULT_DESCRIPCION).estado(DEFAULT_ESTADO).cantidad(DEFAULT_CANTIDAD);
        return materiales;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiales createUpdatedEntity(EntityManager em) {
        Materiales materiales = new Materiales().descripcion(UPDATED_DESCRIPCION).estado(UPDATED_ESTADO).cantidad(UPDATED_CANTIDAD);
        return materiales;
    }

    @BeforeEach
    public void initTest() {
        materiales = createEntity(em);
    }

    @Test
    @Transactional
    void createMateriales() throws Exception {
        int databaseSizeBeforeCreate = materialesRepository.findAll().size();
        // Create the Materiales
        restMaterialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiales)))
            .andExpect(status().isCreated());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeCreate + 1);
        Materiales testMateriales = materialesList.get(materialesList.size() - 1);
        assertThat(testMateriales.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testMateriales.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMateriales.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    void createMaterialesWithExistingId() throws Exception {
        // Create the Materiales with an existing ID
        materiales.setId(1L);

        int databaseSizeBeforeCreate = materialesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiales)))
            .andExpect(status().isBadRequest());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialesRepository.findAll().size();
        // set the field null
        materiales.setDescripcion(null);

        // Create the Materiales, which fails.

        restMaterialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiales)))
            .andExpect(status().isBadRequest());

        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialesRepository.findAll().size();
        // set the field null
        materiales.setEstado(null);

        // Create the Materiales, which fails.

        restMaterialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiales)))
            .andExpect(status().isBadRequest());

        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialesRepository.findAll().size();
        // set the field null
        materiales.setCantidad(null);

        // Create the Materiales, which fails.

        restMaterialesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiales)))
            .andExpect(status().isBadRequest());

        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMateriales() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList
        restMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiales.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    void getMateriales() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get the materiales
        restMaterialesMockMvc
            .perform(get(ENTITY_API_URL_ID, materiales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiales.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    void getMaterialesByIdFiltering() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        Long id = materiales.getId();

        defaultMaterialesShouldBeFound("id.equals=" + id);
        defaultMaterialesShouldNotBeFound("id.notEquals=" + id);

        defaultMaterialesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMaterialesShouldNotBeFound("id.greaterThan=" + id);

        defaultMaterialesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMaterialesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMaterialesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where descripcion equals to DEFAULT_DESCRIPCION
        defaultMaterialesShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the materialesList where descripcion equals to UPDATED_DESCRIPCION
        defaultMaterialesShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllMaterialesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultMaterialesShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the materialesList where descripcion equals to UPDATED_DESCRIPCION
        defaultMaterialesShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllMaterialesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where descripcion is not null
        defaultMaterialesShouldBeFound("descripcion.specified=true");

        // Get all the materialesList where descripcion is null
        defaultMaterialesShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where descripcion contains DEFAULT_DESCRIPCION
        defaultMaterialesShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the materialesList where descripcion contains UPDATED_DESCRIPCION
        defaultMaterialesShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllMaterialesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultMaterialesShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the materialesList where descripcion does not contain UPDATED_DESCRIPCION
        defaultMaterialesShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void getAllMaterialesByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where estado equals to DEFAULT_ESTADO
        defaultMaterialesShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the materialesList where estado equals to UPDATED_ESTADO
        defaultMaterialesShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllMaterialesByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultMaterialesShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the materialesList where estado equals to UPDATED_ESTADO
        defaultMaterialesShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllMaterialesByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where estado is not null
        defaultMaterialesShouldBeFound("estado.specified=true");

        // Get all the materialesList where estado is null
        defaultMaterialesShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialesByEstadoContainsSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where estado contains DEFAULT_ESTADO
        defaultMaterialesShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the materialesList where estado contains UPDATED_ESTADO
        defaultMaterialesShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllMaterialesByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where estado does not contain DEFAULT_ESTADO
        defaultMaterialesShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the materialesList where estado does not contain UPDATED_ESTADO
        defaultMaterialesShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsEqualToSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad equals to DEFAULT_CANTIDAD
        defaultMaterialesShouldBeFound("cantidad.equals=" + DEFAULT_CANTIDAD);

        // Get all the materialesList where cantidad equals to UPDATED_CANTIDAD
        defaultMaterialesShouldNotBeFound("cantidad.equals=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsInShouldWork() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad in DEFAULT_CANTIDAD or UPDATED_CANTIDAD
        defaultMaterialesShouldBeFound("cantidad.in=" + DEFAULT_CANTIDAD + "," + UPDATED_CANTIDAD);

        // Get all the materialesList where cantidad equals to UPDATED_CANTIDAD
        defaultMaterialesShouldNotBeFound("cantidad.in=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad is not null
        defaultMaterialesShouldBeFound("cantidad.specified=true");

        // Get all the materialesList where cantidad is null
        defaultMaterialesShouldNotBeFound("cantidad.specified=false");
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad is greater than or equal to DEFAULT_CANTIDAD
        defaultMaterialesShouldBeFound("cantidad.greaterThanOrEqual=" + DEFAULT_CANTIDAD);

        // Get all the materialesList where cantidad is greater than or equal to UPDATED_CANTIDAD
        defaultMaterialesShouldNotBeFound("cantidad.greaterThanOrEqual=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad is less than or equal to DEFAULT_CANTIDAD
        defaultMaterialesShouldBeFound("cantidad.lessThanOrEqual=" + DEFAULT_CANTIDAD);

        // Get all the materialesList where cantidad is less than or equal to SMALLER_CANTIDAD
        defaultMaterialesShouldNotBeFound("cantidad.lessThanOrEqual=" + SMALLER_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsLessThanSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad is less than DEFAULT_CANTIDAD
        defaultMaterialesShouldNotBeFound("cantidad.lessThan=" + DEFAULT_CANTIDAD);

        // Get all the materialesList where cantidad is less than UPDATED_CANTIDAD
        defaultMaterialesShouldBeFound("cantidad.lessThan=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllMaterialesByCantidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        // Get all the materialesList where cantidad is greater than DEFAULT_CANTIDAD
        defaultMaterialesShouldNotBeFound("cantidad.greaterThan=" + DEFAULT_CANTIDAD);

        // Get all the materialesList where cantidad is greater than SMALLER_CANTIDAD
        defaultMaterialesShouldBeFound("cantidad.greaterThan=" + SMALLER_CANTIDAD);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialesShouldBeFound(String filter) throws Exception {
        restMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiales.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));

        // Check, that the count call also returns 1
        restMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialesShouldNotBeFound(String filter) throws Exception {
        restMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMateriales() throws Exception {
        // Get the materiales
        restMaterialesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMateriales() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();

        // Update the materiales
        Materiales updatedMateriales = materialesRepository.findById(materiales.getId()).get();
        // Disconnect from session so that the updates on updatedMateriales are not directly saved in db
        em.detach(updatedMateriales);
        updatedMateriales.descripcion(UPDATED_DESCRIPCION).estado(UPDATED_ESTADO).cantidad(UPDATED_CANTIDAD);

        restMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMateriales.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMateriales))
            )
            .andExpect(status().isOk());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
        Materiales testMateriales = materialesList.get(materialesList.size() - 1);
        assertThat(testMateriales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testMateriales.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMateriales.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void putNonExistingMateriales() throws Exception {
        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();
        materiales.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiales.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiales))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMateriales() throws Exception {
        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();
        materiales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiales))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMateriales() throws Exception {
        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();
        materiales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiales)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialesWithPatch() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();

        // Update the materiales using partial update
        Materiales partialUpdatedMateriales = new Materiales();
        partialUpdatedMateriales.setId(materiales.getId());

        partialUpdatedMateriales.descripcion(UPDATED_DESCRIPCION).cantidad(UPDATED_CANTIDAD);

        restMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriales))
            )
            .andExpect(status().isOk());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
        Materiales testMateriales = materialesList.get(materialesList.size() - 1);
        assertThat(testMateriales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testMateriales.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMateriales.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void fullUpdateMaterialesWithPatch() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();

        // Update the materiales using partial update
        Materiales partialUpdatedMateriales = new Materiales();
        partialUpdatedMateriales.setId(materiales.getId());

        partialUpdatedMateriales.descripcion(UPDATED_DESCRIPCION).estado(UPDATED_ESTADO).cantidad(UPDATED_CANTIDAD);

        restMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriales))
            )
            .andExpect(status().isOk());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
        Materiales testMateriales = materialesList.get(materialesList.size() - 1);
        assertThat(testMateriales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testMateriales.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMateriales.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void patchNonExistingMateriales() throws Exception {
        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();
        materiales.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materiales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiales))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMateriales() throws Exception {
        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();
        materiales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiales))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMateriales() throws Exception {
        int databaseSizeBeforeUpdate = materialesRepository.findAll().size();
        materiales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(materiales))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materiales in the database
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMateriales() throws Exception {
        // Initialize the database
        materialesRepository.saveAndFlush(materiales);

        int databaseSizeBeforeDelete = materialesRepository.findAll().size();

        // Delete the materiales
        restMaterialesMockMvc
            .perform(delete(ENTITY_API_URL_ID, materiales.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materiales> materialesList = materialesRepository.findAll();
        assertThat(materialesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
