package py.com.abf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import py.com.abf.domain.RegistroStockMateriales;
import py.com.abf.repository.RegistroStockMaterialesRepository;
import py.com.abf.service.criteria.RegistroStockMaterialesCriteria;

/**
 * Integration tests for the {@link RegistroStockMaterialesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistroStockMaterialesResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_INICIAL = 1;
    private static final Integer UPDATED_CANTIDAD_INICIAL = 2;
    private static final Integer SMALLER_CANTIDAD_INICIAL = 1 - 1;

    private static final Integer DEFAULT_CANTIDAD_MODIFICADA = 1;
    private static final Integer UPDATED_CANTIDAD_MODIFICADA = 2;
    private static final Integer SMALLER_CANTIDAD_MODIFICADA = 1 - 1;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/registro-stock-materiales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistroStockMaterialesRepository registroStockMaterialesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistroStockMaterialesMockMvc;

    private RegistroStockMateriales registroStockMateriales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroStockMateriales createEntity(EntityManager em) {
        RegistroStockMateriales registroStockMateriales = new RegistroStockMateriales()
            .comentario(DEFAULT_COMENTARIO)
            .cantidadInicial(DEFAULT_CANTIDAD_INICIAL)
            .cantidadModificada(DEFAULT_CANTIDAD_MODIFICADA)
            .fecha(DEFAULT_FECHA);
        return registroStockMateriales;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroStockMateriales createUpdatedEntity(EntityManager em) {
        RegistroStockMateriales registroStockMateriales = new RegistroStockMateriales()
            .comentario(UPDATED_COMENTARIO)
            .cantidadInicial(UPDATED_CANTIDAD_INICIAL)
            .cantidadModificada(UPDATED_CANTIDAD_MODIFICADA)
            .fecha(UPDATED_FECHA);
        return registroStockMateriales;
    }

    @BeforeEach
    public void initTest() {
        registroStockMateriales = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeCreate = registroStockMaterialesRepository.findAll().size();
        // Create the RegistroStockMateriales
        restRegistroStockMaterialesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isCreated());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeCreate + 1);
        RegistroStockMateriales testRegistroStockMateriales = registroStockMaterialesList.get(registroStockMaterialesList.size() - 1);
        assertThat(testRegistroStockMateriales.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testRegistroStockMateriales.getCantidadInicial()).isEqualTo(DEFAULT_CANTIDAD_INICIAL);
        assertThat(testRegistroStockMateriales.getCantidadModificada()).isEqualTo(DEFAULT_CANTIDAD_MODIFICADA);
        assertThat(testRegistroStockMateriales.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void createRegistroStockMaterialesWithExistingId() throws Exception {
        // Create the RegistroStockMateriales with an existing ID
        registroStockMateriales.setId(1L);

        int databaseSizeBeforeCreate = registroStockMaterialesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroStockMaterialesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroStockMaterialesRepository.findAll().size();
        // set the field null
        registroStockMateriales.setFecha(null);

        // Create the RegistroStockMateriales, which fails.

        restRegistroStockMaterialesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isBadRequest());

        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegistroStockMateriales() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList
        restRegistroStockMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroStockMateriales.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].cantidadInicial").value(hasItem(DEFAULT_CANTIDAD_INICIAL)))
            .andExpect(jsonPath("$.[*].cantidadModificada").value(hasItem(DEFAULT_CANTIDAD_MODIFICADA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    void getRegistroStockMateriales() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get the registroStockMateriales
        restRegistroStockMaterialesMockMvc
            .perform(get(ENTITY_API_URL_ID, registroStockMateriales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registroStockMateriales.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.cantidadInicial").value(DEFAULT_CANTIDAD_INICIAL))
            .andExpect(jsonPath("$.cantidadModificada").value(DEFAULT_CANTIDAD_MODIFICADA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getRegistroStockMaterialesByIdFiltering() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        Long id = registroStockMateriales.getId();

        defaultRegistroStockMaterialesShouldBeFound("id.equals=" + id);
        defaultRegistroStockMaterialesShouldNotBeFound("id.notEquals=" + id);

        defaultRegistroStockMaterialesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegistroStockMaterialesShouldNotBeFound("id.greaterThan=" + id);

        defaultRegistroStockMaterialesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegistroStockMaterialesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where comentario equals to DEFAULT_COMENTARIO
        defaultRegistroStockMaterialesShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the registroStockMaterialesList where comentario equals to UPDATED_COMENTARIO
        defaultRegistroStockMaterialesShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultRegistroStockMaterialesShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the registroStockMaterialesList where comentario equals to UPDATED_COMENTARIO
        defaultRegistroStockMaterialesShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where comentario is not null
        defaultRegistroStockMaterialesShouldBeFound("comentario.specified=true");

        // Get all the registroStockMaterialesList where comentario is null
        defaultRegistroStockMaterialesShouldNotBeFound("comentario.specified=false");
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByComentarioContainsSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where comentario contains DEFAULT_COMENTARIO
        defaultRegistroStockMaterialesShouldBeFound("comentario.contains=" + DEFAULT_COMENTARIO);

        // Get all the registroStockMaterialesList where comentario contains UPDATED_COMENTARIO
        defaultRegistroStockMaterialesShouldNotBeFound("comentario.contains=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByComentarioNotContainsSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where comentario does not contain DEFAULT_COMENTARIO
        defaultRegistroStockMaterialesShouldNotBeFound("comentario.doesNotContain=" + DEFAULT_COMENTARIO);

        // Get all the registroStockMaterialesList where comentario does not contain UPDATED_COMENTARIO
        defaultRegistroStockMaterialesShouldBeFound("comentario.doesNotContain=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial equals to DEFAULT_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.equals=" + DEFAULT_CANTIDAD_INICIAL);

        // Get all the registroStockMaterialesList where cantidadInicial equals to UPDATED_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.equals=" + UPDATED_CANTIDAD_INICIAL);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsInShouldWork() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial in DEFAULT_CANTIDAD_INICIAL or UPDATED_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.in=" + DEFAULT_CANTIDAD_INICIAL + "," + UPDATED_CANTIDAD_INICIAL);

        // Get all the registroStockMaterialesList where cantidadInicial equals to UPDATED_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.in=" + UPDATED_CANTIDAD_INICIAL);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial is not null
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.specified=true");

        // Get all the registroStockMaterialesList where cantidadInicial is null
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.specified=false");
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial is greater than or equal to DEFAULT_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.greaterThanOrEqual=" + DEFAULT_CANTIDAD_INICIAL);

        // Get all the registroStockMaterialesList where cantidadInicial is greater than or equal to UPDATED_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.greaterThanOrEqual=" + UPDATED_CANTIDAD_INICIAL);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial is less than or equal to DEFAULT_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.lessThanOrEqual=" + DEFAULT_CANTIDAD_INICIAL);

        // Get all the registroStockMaterialesList where cantidadInicial is less than or equal to SMALLER_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.lessThanOrEqual=" + SMALLER_CANTIDAD_INICIAL);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsLessThanSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial is less than DEFAULT_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.lessThan=" + DEFAULT_CANTIDAD_INICIAL);

        // Get all the registroStockMaterialesList where cantidadInicial is less than UPDATED_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.lessThan=" + UPDATED_CANTIDAD_INICIAL);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadInicialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadInicial is greater than DEFAULT_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadInicial.greaterThan=" + DEFAULT_CANTIDAD_INICIAL);

        // Get all the registroStockMaterialesList where cantidadInicial is greater than SMALLER_CANTIDAD_INICIAL
        defaultRegistroStockMaterialesShouldBeFound("cantidadInicial.greaterThan=" + SMALLER_CANTIDAD_INICIAL);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada equals to DEFAULT_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldBeFound("cantidadModificada.equals=" + DEFAULT_CANTIDAD_MODIFICADA);

        // Get all the registroStockMaterialesList where cantidadModificada equals to UPDATED_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.equals=" + UPDATED_CANTIDAD_MODIFICADA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsInShouldWork() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada in DEFAULT_CANTIDAD_MODIFICADA or UPDATED_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldBeFound(
            "cantidadModificada.in=" + DEFAULT_CANTIDAD_MODIFICADA + "," + UPDATED_CANTIDAD_MODIFICADA
        );

        // Get all the registroStockMaterialesList where cantidadModificada equals to UPDATED_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.in=" + UPDATED_CANTIDAD_MODIFICADA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada is not null
        defaultRegistroStockMaterialesShouldBeFound("cantidadModificada.specified=true");

        // Get all the registroStockMaterialesList where cantidadModificada is null
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.specified=false");
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada is greater than or equal to DEFAULT_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldBeFound("cantidadModificada.greaterThanOrEqual=" + DEFAULT_CANTIDAD_MODIFICADA);

        // Get all the registroStockMaterialesList where cantidadModificada is greater than or equal to UPDATED_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.greaterThanOrEqual=" + UPDATED_CANTIDAD_MODIFICADA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada is less than or equal to DEFAULT_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldBeFound("cantidadModificada.lessThanOrEqual=" + DEFAULT_CANTIDAD_MODIFICADA);

        // Get all the registroStockMaterialesList where cantidadModificada is less than or equal to SMALLER_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.lessThanOrEqual=" + SMALLER_CANTIDAD_MODIFICADA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsLessThanSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada is less than DEFAULT_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.lessThan=" + DEFAULT_CANTIDAD_MODIFICADA);

        // Get all the registroStockMaterialesList where cantidadModificada is less than UPDATED_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldBeFound("cantidadModificada.lessThan=" + UPDATED_CANTIDAD_MODIFICADA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByCantidadModificadaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where cantidadModificada is greater than DEFAULT_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldNotBeFound("cantidadModificada.greaterThan=" + DEFAULT_CANTIDAD_MODIFICADA);

        // Get all the registroStockMaterialesList where cantidadModificada is greater than SMALLER_CANTIDAD_MODIFICADA
        defaultRegistroStockMaterialesShouldBeFound("cantidadModificada.greaterThan=" + SMALLER_CANTIDAD_MODIFICADA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha equals to DEFAULT_FECHA
        defaultRegistroStockMaterialesShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the registroStockMaterialesList where fecha equals to UPDATED_FECHA
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultRegistroStockMaterialesShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the registroStockMaterialesList where fecha equals to UPDATED_FECHA
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha is not null
        defaultRegistroStockMaterialesShouldBeFound("fecha.specified=true");

        // Get all the registroStockMaterialesList where fecha is null
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha is greater than or equal to DEFAULT_FECHA
        defaultRegistroStockMaterialesShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the registroStockMaterialesList where fecha is greater than or equal to UPDATED_FECHA
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha is less than or equal to DEFAULT_FECHA
        defaultRegistroStockMaterialesShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the registroStockMaterialesList where fecha is less than or equal to SMALLER_FECHA
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha is less than DEFAULT_FECHA
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the registroStockMaterialesList where fecha is less than UPDATED_FECHA
        defaultRegistroStockMaterialesShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        // Get all the registroStockMaterialesList where fecha is greater than DEFAULT_FECHA
        defaultRegistroStockMaterialesShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the registroStockMaterialesList where fecha is greater than SMALLER_FECHA
        defaultRegistroStockMaterialesShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    void getAllRegistroStockMaterialesByMaterialesIsEqualToSomething() throws Exception {
        Materiales materiales;
        if (TestUtil.findAll(em, Materiales.class).isEmpty()) {
            registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);
            materiales = MaterialesResourceIT.createEntity(em);
        } else {
            materiales = TestUtil.findAll(em, Materiales.class).get(0);
        }
        em.persist(materiales);
        em.flush();
        registroStockMateriales.setMateriales(materiales);
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);
        Long materialesId = materiales.getId();

        // Get all the registroStockMaterialesList where materiales equals to materialesId
        defaultRegistroStockMaterialesShouldBeFound("materialesId.equals=" + materialesId);

        // Get all the registroStockMaterialesList where materiales equals to (materialesId + 1)
        defaultRegistroStockMaterialesShouldNotBeFound("materialesId.equals=" + (materialesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegistroStockMaterialesShouldBeFound(String filter) throws Exception {
        restRegistroStockMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroStockMateriales.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].cantidadInicial").value(hasItem(DEFAULT_CANTIDAD_INICIAL)))
            .andExpect(jsonPath("$.[*].cantidadModificada").value(hasItem(DEFAULT_CANTIDAD_MODIFICADA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restRegistroStockMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegistroStockMaterialesShouldNotBeFound(String filter) throws Exception {
        restRegistroStockMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegistroStockMaterialesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRegistroStockMateriales() throws Exception {
        // Get the registroStockMateriales
        restRegistroStockMaterialesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegistroStockMateriales() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();

        // Update the registroStockMateriales
        RegistroStockMateriales updatedRegistroStockMateriales = registroStockMaterialesRepository
            .findById(registroStockMateriales.getId())
            .get();
        // Disconnect from session so that the updates on updatedRegistroStockMateriales are not directly saved in db
        em.detach(updatedRegistroStockMateriales);
        updatedRegistroStockMateriales
            .comentario(UPDATED_COMENTARIO)
            .cantidadInicial(UPDATED_CANTIDAD_INICIAL)
            .cantidadModificada(UPDATED_CANTIDAD_MODIFICADA)
            .fecha(UPDATED_FECHA);

        restRegistroStockMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegistroStockMateriales.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegistroStockMateriales))
            )
            .andExpect(status().isOk());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
        RegistroStockMateriales testRegistroStockMateriales = registroStockMaterialesList.get(registroStockMaterialesList.size() - 1);
        assertThat(testRegistroStockMateriales.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testRegistroStockMateriales.getCantidadInicial()).isEqualTo(UPDATED_CANTIDAD_INICIAL);
        assertThat(testRegistroStockMateriales.getCantidadModificada()).isEqualTo(UPDATED_CANTIDAD_MODIFICADA);
        assertThat(testRegistroStockMateriales.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void putNonExistingRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();
        registroStockMateriales.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroStockMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registroStockMateriales.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();
        registroStockMateriales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroStockMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();
        registroStockMateriales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroStockMaterialesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistroStockMaterialesWithPatch() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();

        // Update the registroStockMateriales using partial update
        RegistroStockMateriales partialUpdatedRegistroStockMateriales = new RegistroStockMateriales();
        partialUpdatedRegistroStockMateriales.setId(registroStockMateriales.getId());

        partialUpdatedRegistroStockMateriales
            .comentario(UPDATED_COMENTARIO)
            .cantidadModificada(UPDATED_CANTIDAD_MODIFICADA)
            .fecha(UPDATED_FECHA);

        restRegistroStockMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistroStockMateriales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistroStockMateriales))
            )
            .andExpect(status().isOk());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
        RegistroStockMateriales testRegistroStockMateriales = registroStockMaterialesList.get(registroStockMaterialesList.size() - 1);
        assertThat(testRegistroStockMateriales.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testRegistroStockMateriales.getCantidadInicial()).isEqualTo(DEFAULT_CANTIDAD_INICIAL);
        assertThat(testRegistroStockMateriales.getCantidadModificada()).isEqualTo(UPDATED_CANTIDAD_MODIFICADA);
        assertThat(testRegistroStockMateriales.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void fullUpdateRegistroStockMaterialesWithPatch() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();

        // Update the registroStockMateriales using partial update
        RegistroStockMateriales partialUpdatedRegistroStockMateriales = new RegistroStockMateriales();
        partialUpdatedRegistroStockMateriales.setId(registroStockMateriales.getId());

        partialUpdatedRegistroStockMateriales
            .comentario(UPDATED_COMENTARIO)
            .cantidadInicial(UPDATED_CANTIDAD_INICIAL)
            .cantidadModificada(UPDATED_CANTIDAD_MODIFICADA)
            .fecha(UPDATED_FECHA);

        restRegistroStockMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistroStockMateriales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistroStockMateriales))
            )
            .andExpect(status().isOk());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
        RegistroStockMateriales testRegistroStockMateriales = registroStockMaterialesList.get(registroStockMaterialesList.size() - 1);
        assertThat(testRegistroStockMateriales.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testRegistroStockMateriales.getCantidadInicial()).isEqualTo(UPDATED_CANTIDAD_INICIAL);
        assertThat(testRegistroStockMateriales.getCantidadModificada()).isEqualTo(UPDATED_CANTIDAD_MODIFICADA);
        assertThat(testRegistroStockMateriales.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void patchNonExistingRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();
        registroStockMateriales.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroStockMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registroStockMateriales.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();
        registroStockMateriales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroStockMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistroStockMateriales() throws Exception {
        int databaseSizeBeforeUpdate = registroStockMaterialesRepository.findAll().size();
        registroStockMateriales.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroStockMaterialesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroStockMateriales))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistroStockMateriales in the database
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistroStockMateriales() throws Exception {
        // Initialize the database
        registroStockMaterialesRepository.saveAndFlush(registroStockMateriales);

        int databaseSizeBeforeDelete = registroStockMaterialesRepository.findAll().size();

        // Delete the registroStockMateriales
        restRegistroStockMaterialesMockMvc
            .perform(delete(ENTITY_API_URL_ID, registroStockMateriales.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistroStockMateriales> registroStockMaterialesList = registroStockMaterialesRepository.findAll();
        assertThat(registroStockMaterialesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
