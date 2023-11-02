package py.com.abf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.IntegrationTest;
import py.com.abf.domain.Facturas;
import py.com.abf.domain.NotaCredito;
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.domain.enumeration.Motivo;
import py.com.abf.repository.NotaCreditoRepository;
import py.com.abf.service.NotaCreditoService;

/**
 * Integration tests for the {@link NotaCreditoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NotaCreditoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NOTA_NRO = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_NRO = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTO_EXPEDICION = 1;
    private static final Integer UPDATED_PUNTO_EXPEDICION = 2;
    private static final Integer SMALLER_PUNTO_EXPEDICION = 1 - 1;

    private static final Integer DEFAULT_SUCURSAL = 1;
    private static final Integer UPDATED_SUCURSAL = 2;
    private static final Integer SMALLER_SUCURSAL = 1 - 1;

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_RUC = "AAAAAAAAAA";
    private static final String UPDATED_RUC = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Motivo DEFAULT_MOTIVO_EMISION = Motivo.ANULACION;
    private static final Motivo UPDATED_MOTIVO_EMISION = Motivo.DEVOLUCION;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;
    private static final Integer SMALLER_TOTAL = 1 - 1;

    private static final String ENTITY_API_URL = "/api/nota-creditos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotaCreditoRepository notaCreditoRepository;

    @Mock
    private NotaCreditoRepository notaCreditoRepositoryMock;

    @Mock
    private NotaCreditoService notaCreditoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotaCreditoMockMvc;

    private NotaCredito notaCredito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaCredito createEntity(EntityManager em) {
        NotaCredito notaCredito = new NotaCredito()
            .fecha(DEFAULT_FECHA)
            .notaNro(DEFAULT_NOTA_NRO)
            .puntoExpedicion(DEFAULT_PUNTO_EXPEDICION)
            .sucursal(DEFAULT_SUCURSAL)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .ruc(DEFAULT_RUC)
            .direccion(DEFAULT_DIRECCION)
            .motivoEmision(DEFAULT_MOTIVO_EMISION)
            .total(DEFAULT_TOTAL);
        // Add required entity
        Facturas facturas;
        if (TestUtil.findAll(em, Facturas.class).isEmpty()) {
            facturas = FacturasResourceIT.createEntity(em);
            em.persist(facturas);
            em.flush();
        } else {
            facturas = TestUtil.findAll(em, Facturas.class).get(0);
        }
        notaCredito.setFacturas(facturas);
        return notaCredito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaCredito createUpdatedEntity(EntityManager em) {
        NotaCredito notaCredito = new NotaCredito()
            .fecha(UPDATED_FECHA)
            .notaNro(UPDATED_NOTA_NRO)
            .puntoExpedicion(UPDATED_PUNTO_EXPEDICION)
            .sucursal(UPDATED_SUCURSAL)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .ruc(UPDATED_RUC)
            .direccion(UPDATED_DIRECCION)
            .motivoEmision(UPDATED_MOTIVO_EMISION)
            .total(UPDATED_TOTAL);
        // Add required entity
        Facturas facturas;
        if (TestUtil.findAll(em, Facturas.class).isEmpty()) {
            facturas = FacturasResourceIT.createUpdatedEntity(em);
            em.persist(facturas);
            em.flush();
        } else {
            facturas = TestUtil.findAll(em, Facturas.class).get(0);
        }
        notaCredito.setFacturas(facturas);
        return notaCredito;
    }

    @BeforeEach
    public void initTest() {
        notaCredito = createEntity(em);
    }

    @Test
    @Transactional
    void createNotaCredito() throws Exception {
        int databaseSizeBeforeCreate = notaCreditoRepository.findAll().size();
        // Create the NotaCredito
        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isCreated());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeCreate + 1);
        NotaCredito testNotaCredito = notaCreditoList.get(notaCreditoList.size() - 1);
        assertThat(testNotaCredito.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testNotaCredito.getNotaNro()).isEqualTo(DEFAULT_NOTA_NRO);
        assertThat(testNotaCredito.getPuntoExpedicion()).isEqualTo(DEFAULT_PUNTO_EXPEDICION);
        assertThat(testNotaCredito.getSucursal()).isEqualTo(DEFAULT_SUCURSAL);
        assertThat(testNotaCredito.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testNotaCredito.getRuc()).isEqualTo(DEFAULT_RUC);
        assertThat(testNotaCredito.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testNotaCredito.getMotivoEmision()).isEqualTo(DEFAULT_MOTIVO_EMISION);
        assertThat(testNotaCredito.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void createNotaCreditoWithExistingId() throws Exception {
        // Create the NotaCredito with an existing ID
        notaCredito.setId(1L);

        int databaseSizeBeforeCreate = notaCreditoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setFecha(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotaNroIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setNotaNro(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPuntoExpedicionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setPuntoExpedicion(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSucursalIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setSucursal(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setRazonSocial(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRucIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setRuc(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMotivoEmisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setMotivoEmision(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = notaCreditoRepository.findAll().size();
        // set the field null
        notaCredito.setTotal(null);

        // Create the NotaCredito, which fails.

        restNotaCreditoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isBadRequest());

        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotaCreditos() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList
        restNotaCreditoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notaCredito.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].notaNro").value(hasItem(DEFAULT_NOTA_NRO)))
            .andExpect(jsonPath("$.[*].puntoExpedicion").value(hasItem(DEFAULT_PUNTO_EXPEDICION)))
            .andExpect(jsonPath("$.[*].sucursal").value(hasItem(DEFAULT_SUCURSAL)))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].ruc").value(hasItem(DEFAULT_RUC)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].motivoEmision").value(hasItem(DEFAULT_MOTIVO_EMISION.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotaCreditosWithEagerRelationshipsIsEnabled() throws Exception {
        when(notaCreditoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotaCreditoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(notaCreditoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotaCreditosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(notaCreditoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotaCreditoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(notaCreditoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNotaCredito() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get the notaCredito
        restNotaCreditoMockMvc
            .perform(get(ENTITY_API_URL_ID, notaCredito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notaCredito.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.notaNro").value(DEFAULT_NOTA_NRO))
            .andExpect(jsonPath("$.puntoExpedicion").value(DEFAULT_PUNTO_EXPEDICION))
            .andExpect(jsonPath("$.sucursal").value(DEFAULT_SUCURSAL))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL))
            .andExpect(jsonPath("$.ruc").value(DEFAULT_RUC))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.motivoEmision").value(DEFAULT_MOTIVO_EMISION.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL));
    }

    @Test
    @Transactional
    void getNotaCreditosByIdFiltering() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        Long id = notaCredito.getId();

        defaultNotaCreditoShouldBeFound("id.equals=" + id);
        defaultNotaCreditoShouldNotBeFound("id.notEquals=" + id);

        defaultNotaCreditoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotaCreditoShouldNotBeFound("id.greaterThan=" + id);

        defaultNotaCreditoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotaCreditoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha equals to DEFAULT_FECHA
        defaultNotaCreditoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the notaCreditoList where fecha equals to UPDATED_FECHA
        defaultNotaCreditoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultNotaCreditoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the notaCreditoList where fecha equals to UPDATED_FECHA
        defaultNotaCreditoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha is not null
        defaultNotaCreditoShouldBeFound("fecha.specified=true");

        // Get all the notaCreditoList where fecha is null
        defaultNotaCreditoShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha is greater than or equal to DEFAULT_FECHA
        defaultNotaCreditoShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the notaCreditoList where fecha is greater than or equal to UPDATED_FECHA
        defaultNotaCreditoShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha is less than or equal to DEFAULT_FECHA
        defaultNotaCreditoShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the notaCreditoList where fecha is less than or equal to SMALLER_FECHA
        defaultNotaCreditoShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha is less than DEFAULT_FECHA
        defaultNotaCreditoShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the notaCreditoList where fecha is less than UPDATED_FECHA
        defaultNotaCreditoShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where fecha is greater than DEFAULT_FECHA
        defaultNotaCreditoShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the notaCreditoList where fecha is greater than SMALLER_FECHA
        defaultNotaCreditoShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByNotaNroIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where notaNro equals to DEFAULT_NOTA_NRO
        defaultNotaCreditoShouldBeFound("notaNro.equals=" + DEFAULT_NOTA_NRO);

        // Get all the notaCreditoList where notaNro equals to UPDATED_NOTA_NRO
        defaultNotaCreditoShouldNotBeFound("notaNro.equals=" + UPDATED_NOTA_NRO);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByNotaNroIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where notaNro in DEFAULT_NOTA_NRO or UPDATED_NOTA_NRO
        defaultNotaCreditoShouldBeFound("notaNro.in=" + DEFAULT_NOTA_NRO + "," + UPDATED_NOTA_NRO);

        // Get all the notaCreditoList where notaNro equals to UPDATED_NOTA_NRO
        defaultNotaCreditoShouldNotBeFound("notaNro.in=" + UPDATED_NOTA_NRO);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByNotaNroIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where notaNro is not null
        defaultNotaCreditoShouldBeFound("notaNro.specified=true");

        // Get all the notaCreditoList where notaNro is null
        defaultNotaCreditoShouldNotBeFound("notaNro.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByNotaNroContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where notaNro contains DEFAULT_NOTA_NRO
        defaultNotaCreditoShouldBeFound("notaNro.contains=" + DEFAULT_NOTA_NRO);

        // Get all the notaCreditoList where notaNro contains UPDATED_NOTA_NRO
        defaultNotaCreditoShouldNotBeFound("notaNro.contains=" + UPDATED_NOTA_NRO);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByNotaNroNotContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where notaNro does not contain DEFAULT_NOTA_NRO
        defaultNotaCreditoShouldNotBeFound("notaNro.doesNotContain=" + DEFAULT_NOTA_NRO);

        // Get all the notaCreditoList where notaNro does not contain UPDATED_NOTA_NRO
        defaultNotaCreditoShouldBeFound("notaNro.doesNotContain=" + UPDATED_NOTA_NRO);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion equals to DEFAULT_PUNTO_EXPEDICION
        defaultNotaCreditoShouldBeFound("puntoExpedicion.equals=" + DEFAULT_PUNTO_EXPEDICION);

        // Get all the notaCreditoList where puntoExpedicion equals to UPDATED_PUNTO_EXPEDICION
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.equals=" + UPDATED_PUNTO_EXPEDICION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion in DEFAULT_PUNTO_EXPEDICION or UPDATED_PUNTO_EXPEDICION
        defaultNotaCreditoShouldBeFound("puntoExpedicion.in=" + DEFAULT_PUNTO_EXPEDICION + "," + UPDATED_PUNTO_EXPEDICION);

        // Get all the notaCreditoList where puntoExpedicion equals to UPDATED_PUNTO_EXPEDICION
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.in=" + UPDATED_PUNTO_EXPEDICION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion is not null
        defaultNotaCreditoShouldBeFound("puntoExpedicion.specified=true");

        // Get all the notaCreditoList where puntoExpedicion is null
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion is greater than or equal to DEFAULT_PUNTO_EXPEDICION
        defaultNotaCreditoShouldBeFound("puntoExpedicion.greaterThanOrEqual=" + DEFAULT_PUNTO_EXPEDICION);

        // Get all the notaCreditoList where puntoExpedicion is greater than or equal to UPDATED_PUNTO_EXPEDICION
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.greaterThanOrEqual=" + UPDATED_PUNTO_EXPEDICION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion is less than or equal to DEFAULT_PUNTO_EXPEDICION
        defaultNotaCreditoShouldBeFound("puntoExpedicion.lessThanOrEqual=" + DEFAULT_PUNTO_EXPEDICION);

        // Get all the notaCreditoList where puntoExpedicion is less than or equal to SMALLER_PUNTO_EXPEDICION
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.lessThanOrEqual=" + SMALLER_PUNTO_EXPEDICION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion is less than DEFAULT_PUNTO_EXPEDICION
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.lessThan=" + DEFAULT_PUNTO_EXPEDICION);

        // Get all the notaCreditoList where puntoExpedicion is less than UPDATED_PUNTO_EXPEDICION
        defaultNotaCreditoShouldBeFound("puntoExpedicion.lessThan=" + UPDATED_PUNTO_EXPEDICION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByPuntoExpedicionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where puntoExpedicion is greater than DEFAULT_PUNTO_EXPEDICION
        defaultNotaCreditoShouldNotBeFound("puntoExpedicion.greaterThan=" + DEFAULT_PUNTO_EXPEDICION);

        // Get all the notaCreditoList where puntoExpedicion is greater than SMALLER_PUNTO_EXPEDICION
        defaultNotaCreditoShouldBeFound("puntoExpedicion.greaterThan=" + SMALLER_PUNTO_EXPEDICION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal equals to DEFAULT_SUCURSAL
        defaultNotaCreditoShouldBeFound("sucursal.equals=" + DEFAULT_SUCURSAL);

        // Get all the notaCreditoList where sucursal equals to UPDATED_SUCURSAL
        defaultNotaCreditoShouldNotBeFound("sucursal.equals=" + UPDATED_SUCURSAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal in DEFAULT_SUCURSAL or UPDATED_SUCURSAL
        defaultNotaCreditoShouldBeFound("sucursal.in=" + DEFAULT_SUCURSAL + "," + UPDATED_SUCURSAL);

        // Get all the notaCreditoList where sucursal equals to UPDATED_SUCURSAL
        defaultNotaCreditoShouldNotBeFound("sucursal.in=" + UPDATED_SUCURSAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal is not null
        defaultNotaCreditoShouldBeFound("sucursal.specified=true");

        // Get all the notaCreditoList where sucursal is null
        defaultNotaCreditoShouldNotBeFound("sucursal.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal is greater than or equal to DEFAULT_SUCURSAL
        defaultNotaCreditoShouldBeFound("sucursal.greaterThanOrEqual=" + DEFAULT_SUCURSAL);

        // Get all the notaCreditoList where sucursal is greater than or equal to UPDATED_SUCURSAL
        defaultNotaCreditoShouldNotBeFound("sucursal.greaterThanOrEqual=" + UPDATED_SUCURSAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal is less than or equal to DEFAULT_SUCURSAL
        defaultNotaCreditoShouldBeFound("sucursal.lessThanOrEqual=" + DEFAULT_SUCURSAL);

        // Get all the notaCreditoList where sucursal is less than or equal to SMALLER_SUCURSAL
        defaultNotaCreditoShouldNotBeFound("sucursal.lessThanOrEqual=" + SMALLER_SUCURSAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal is less than DEFAULT_SUCURSAL
        defaultNotaCreditoShouldNotBeFound("sucursal.lessThan=" + DEFAULT_SUCURSAL);

        // Get all the notaCreditoList where sucursal is less than UPDATED_SUCURSAL
        defaultNotaCreditoShouldBeFound("sucursal.lessThan=" + UPDATED_SUCURSAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosBySucursalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where sucursal is greater than DEFAULT_SUCURSAL
        defaultNotaCreditoShouldNotBeFound("sucursal.greaterThan=" + DEFAULT_SUCURSAL);

        // Get all the notaCreditoList where sucursal is greater than SMALLER_SUCURSAL
        defaultNotaCreditoShouldBeFound("sucursal.greaterThan=" + SMALLER_SUCURSAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRazonSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where razonSocial equals to DEFAULT_RAZON_SOCIAL
        defaultNotaCreditoShouldBeFound("razonSocial.equals=" + DEFAULT_RAZON_SOCIAL);

        // Get all the notaCreditoList where razonSocial equals to UPDATED_RAZON_SOCIAL
        defaultNotaCreditoShouldNotBeFound("razonSocial.equals=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRazonSocialIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where razonSocial in DEFAULT_RAZON_SOCIAL or UPDATED_RAZON_SOCIAL
        defaultNotaCreditoShouldBeFound("razonSocial.in=" + DEFAULT_RAZON_SOCIAL + "," + UPDATED_RAZON_SOCIAL);

        // Get all the notaCreditoList where razonSocial equals to UPDATED_RAZON_SOCIAL
        defaultNotaCreditoShouldNotBeFound("razonSocial.in=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRazonSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where razonSocial is not null
        defaultNotaCreditoShouldBeFound("razonSocial.specified=true");

        // Get all the notaCreditoList where razonSocial is null
        defaultNotaCreditoShouldNotBeFound("razonSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRazonSocialContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where razonSocial contains DEFAULT_RAZON_SOCIAL
        defaultNotaCreditoShouldBeFound("razonSocial.contains=" + DEFAULT_RAZON_SOCIAL);

        // Get all the notaCreditoList where razonSocial contains UPDATED_RAZON_SOCIAL
        defaultNotaCreditoShouldNotBeFound("razonSocial.contains=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRazonSocialNotContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where razonSocial does not contain DEFAULT_RAZON_SOCIAL
        defaultNotaCreditoShouldNotBeFound("razonSocial.doesNotContain=" + DEFAULT_RAZON_SOCIAL);

        // Get all the notaCreditoList where razonSocial does not contain UPDATED_RAZON_SOCIAL
        defaultNotaCreditoShouldBeFound("razonSocial.doesNotContain=" + UPDATED_RAZON_SOCIAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRucIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where ruc equals to DEFAULT_RUC
        defaultNotaCreditoShouldBeFound("ruc.equals=" + DEFAULT_RUC);

        // Get all the notaCreditoList where ruc equals to UPDATED_RUC
        defaultNotaCreditoShouldNotBeFound("ruc.equals=" + UPDATED_RUC);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRucIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where ruc in DEFAULT_RUC or UPDATED_RUC
        defaultNotaCreditoShouldBeFound("ruc.in=" + DEFAULT_RUC + "," + UPDATED_RUC);

        // Get all the notaCreditoList where ruc equals to UPDATED_RUC
        defaultNotaCreditoShouldNotBeFound("ruc.in=" + UPDATED_RUC);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRucIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where ruc is not null
        defaultNotaCreditoShouldBeFound("ruc.specified=true");

        // Get all the notaCreditoList where ruc is null
        defaultNotaCreditoShouldNotBeFound("ruc.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRucContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where ruc contains DEFAULT_RUC
        defaultNotaCreditoShouldBeFound("ruc.contains=" + DEFAULT_RUC);

        // Get all the notaCreditoList where ruc contains UPDATED_RUC
        defaultNotaCreditoShouldNotBeFound("ruc.contains=" + UPDATED_RUC);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByRucNotContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where ruc does not contain DEFAULT_RUC
        defaultNotaCreditoShouldNotBeFound("ruc.doesNotContain=" + DEFAULT_RUC);

        // Get all the notaCreditoList where ruc does not contain UPDATED_RUC
        defaultNotaCreditoShouldBeFound("ruc.doesNotContain=" + UPDATED_RUC);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where direccion equals to DEFAULT_DIRECCION
        defaultNotaCreditoShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the notaCreditoList where direccion equals to UPDATED_DIRECCION
        defaultNotaCreditoShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultNotaCreditoShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the notaCreditoList where direccion equals to UPDATED_DIRECCION
        defaultNotaCreditoShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where direccion is not null
        defaultNotaCreditoShouldBeFound("direccion.specified=true");

        // Get all the notaCreditoList where direccion is null
        defaultNotaCreditoShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByDireccionContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where direccion contains DEFAULT_DIRECCION
        defaultNotaCreditoShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the notaCreditoList where direccion contains UPDATED_DIRECCION
        defaultNotaCreditoShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where direccion does not contain DEFAULT_DIRECCION
        defaultNotaCreditoShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the notaCreditoList where direccion does not contain UPDATED_DIRECCION
        defaultNotaCreditoShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByMotivoEmisionIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where motivoEmision equals to DEFAULT_MOTIVO_EMISION
        defaultNotaCreditoShouldBeFound("motivoEmision.equals=" + DEFAULT_MOTIVO_EMISION);

        // Get all the notaCreditoList where motivoEmision equals to UPDATED_MOTIVO_EMISION
        defaultNotaCreditoShouldNotBeFound("motivoEmision.equals=" + UPDATED_MOTIVO_EMISION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByMotivoEmisionIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where motivoEmision in DEFAULT_MOTIVO_EMISION or UPDATED_MOTIVO_EMISION
        defaultNotaCreditoShouldBeFound("motivoEmision.in=" + DEFAULT_MOTIVO_EMISION + "," + UPDATED_MOTIVO_EMISION);

        // Get all the notaCreditoList where motivoEmision equals to UPDATED_MOTIVO_EMISION
        defaultNotaCreditoShouldNotBeFound("motivoEmision.in=" + UPDATED_MOTIVO_EMISION);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByMotivoEmisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where motivoEmision is not null
        defaultNotaCreditoShouldBeFound("motivoEmision.specified=true");

        // Get all the notaCreditoList where motivoEmision is null
        defaultNotaCreditoShouldNotBeFound("motivoEmision.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total equals to DEFAULT_TOTAL
        defaultNotaCreditoShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the notaCreditoList where total equals to UPDATED_TOTAL
        defaultNotaCreditoShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultNotaCreditoShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the notaCreditoList where total equals to UPDATED_TOTAL
        defaultNotaCreditoShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total is not null
        defaultNotaCreditoShouldBeFound("total.specified=true");

        // Get all the notaCreditoList where total is null
        defaultNotaCreditoShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total is greater than or equal to DEFAULT_TOTAL
        defaultNotaCreditoShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the notaCreditoList where total is greater than or equal to UPDATED_TOTAL
        defaultNotaCreditoShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total is less than or equal to DEFAULT_TOTAL
        defaultNotaCreditoShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the notaCreditoList where total is less than or equal to SMALLER_TOTAL
        defaultNotaCreditoShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total is less than DEFAULT_TOTAL
        defaultNotaCreditoShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the notaCreditoList where total is less than UPDATED_TOTAL
        defaultNotaCreditoShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        // Get all the notaCreditoList where total is greater than DEFAULT_TOTAL
        defaultNotaCreditoShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the notaCreditoList where total is greater than SMALLER_TOTAL
        defaultNotaCreditoShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditosByFacturasIsEqualToSomething() throws Exception {
        // Get already existing entity
        Facturas facturas = notaCredito.getFacturas();
        notaCreditoRepository.saveAndFlush(notaCredito);
        Long facturasId = facturas.getId();
        // Get all the notaCreditoList where facturas equals to facturasId
        defaultNotaCreditoShouldBeFound("facturasId.equals=" + facturasId);

        // Get all the notaCreditoList where facturas equals to (facturasId + 1)
        defaultNotaCreditoShouldNotBeFound("facturasId.equals=" + (facturasId + 1));
    }

    @Test
    @Transactional
    void getAllNotaCreditosByNotaCreditoDetalleIsEqualToSomething() throws Exception {
        NotaCreditoDetalle notaCreditoDetalle;
        if (TestUtil.findAll(em, NotaCreditoDetalle.class).isEmpty()) {
            notaCreditoRepository.saveAndFlush(notaCredito);
            notaCreditoDetalle = NotaCreditoDetalleResourceIT.createEntity(em);
        } else {
            notaCreditoDetalle = TestUtil.findAll(em, NotaCreditoDetalle.class).get(0);
        }
        em.persist(notaCreditoDetalle);
        em.flush();
        notaCredito.addNotaCreditoDetalle(notaCreditoDetalle);
        notaCreditoRepository.saveAndFlush(notaCredito);
        Long notaCreditoDetalleId = notaCreditoDetalle.getId();
        // Get all the notaCreditoList where notaCreditoDetalle equals to notaCreditoDetalleId
        defaultNotaCreditoShouldBeFound("notaCreditoDetalleId.equals=" + notaCreditoDetalleId);

        // Get all the notaCreditoList where notaCreditoDetalle equals to (notaCreditoDetalleId + 1)
        defaultNotaCreditoShouldNotBeFound("notaCreditoDetalleId.equals=" + (notaCreditoDetalleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotaCreditoShouldBeFound(String filter) throws Exception {
        restNotaCreditoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notaCredito.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].notaNro").value(hasItem(DEFAULT_NOTA_NRO)))
            .andExpect(jsonPath("$.[*].puntoExpedicion").value(hasItem(DEFAULT_PUNTO_EXPEDICION)))
            .andExpect(jsonPath("$.[*].sucursal").value(hasItem(DEFAULT_SUCURSAL)))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].ruc").value(hasItem(DEFAULT_RUC)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].motivoEmision").value(hasItem(DEFAULT_MOTIVO_EMISION.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)));

        // Check, that the count call also returns 1
        restNotaCreditoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotaCreditoShouldNotBeFound(String filter) throws Exception {
        restNotaCreditoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotaCreditoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotaCredito() throws Exception {
        // Get the notaCredito
        restNotaCreditoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotaCredito() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();

        // Update the notaCredito
        NotaCredito updatedNotaCredito = notaCreditoRepository.findById(notaCredito.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNotaCredito are not directly saved in db
        em.detach(updatedNotaCredito);
        updatedNotaCredito
            .fecha(UPDATED_FECHA)
            .notaNro(UPDATED_NOTA_NRO)
            .puntoExpedicion(UPDATED_PUNTO_EXPEDICION)
            .sucursal(UPDATED_SUCURSAL)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .ruc(UPDATED_RUC)
            .direccion(UPDATED_DIRECCION)
            .motivoEmision(UPDATED_MOTIVO_EMISION)
            .total(UPDATED_TOTAL);

        restNotaCreditoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotaCredito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotaCredito))
            )
            .andExpect(status().isOk());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
        NotaCredito testNotaCredito = notaCreditoList.get(notaCreditoList.size() - 1);
        assertThat(testNotaCredito.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotaCredito.getNotaNro()).isEqualTo(UPDATED_NOTA_NRO);
        assertThat(testNotaCredito.getPuntoExpedicion()).isEqualTo(UPDATED_PUNTO_EXPEDICION);
        assertThat(testNotaCredito.getSucursal()).isEqualTo(UPDATED_SUCURSAL);
        assertThat(testNotaCredito.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testNotaCredito.getRuc()).isEqualTo(UPDATED_RUC);
        assertThat(testNotaCredito.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testNotaCredito.getMotivoEmision()).isEqualTo(UPDATED_MOTIVO_EMISION);
        assertThat(testNotaCredito.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingNotaCredito() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();
        notaCredito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaCreditoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notaCredito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notaCredito))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotaCredito() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();
        notaCredito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notaCredito))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotaCredito() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();
        notaCredito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCredito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotaCreditoWithPatch() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();

        // Update the notaCredito using partial update
        NotaCredito partialUpdatedNotaCredito = new NotaCredito();
        partialUpdatedNotaCredito.setId(notaCredito.getId());

        partialUpdatedNotaCredito
            .puntoExpedicion(UPDATED_PUNTO_EXPEDICION)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .ruc(UPDATED_RUC)
            .total(UPDATED_TOTAL);

        restNotaCreditoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotaCredito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotaCredito))
            )
            .andExpect(status().isOk());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
        NotaCredito testNotaCredito = notaCreditoList.get(notaCreditoList.size() - 1);
        assertThat(testNotaCredito.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testNotaCredito.getNotaNro()).isEqualTo(DEFAULT_NOTA_NRO);
        assertThat(testNotaCredito.getPuntoExpedicion()).isEqualTo(UPDATED_PUNTO_EXPEDICION);
        assertThat(testNotaCredito.getSucursal()).isEqualTo(DEFAULT_SUCURSAL);
        assertThat(testNotaCredito.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testNotaCredito.getRuc()).isEqualTo(UPDATED_RUC);
        assertThat(testNotaCredito.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testNotaCredito.getMotivoEmision()).isEqualTo(DEFAULT_MOTIVO_EMISION);
        assertThat(testNotaCredito.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateNotaCreditoWithPatch() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();

        // Update the notaCredito using partial update
        NotaCredito partialUpdatedNotaCredito = new NotaCredito();
        partialUpdatedNotaCredito.setId(notaCredito.getId());

        partialUpdatedNotaCredito
            .fecha(UPDATED_FECHA)
            .notaNro(UPDATED_NOTA_NRO)
            .puntoExpedicion(UPDATED_PUNTO_EXPEDICION)
            .sucursal(UPDATED_SUCURSAL)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .ruc(UPDATED_RUC)
            .direccion(UPDATED_DIRECCION)
            .motivoEmision(UPDATED_MOTIVO_EMISION)
            .total(UPDATED_TOTAL);

        restNotaCreditoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotaCredito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotaCredito))
            )
            .andExpect(status().isOk());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
        NotaCredito testNotaCredito = notaCreditoList.get(notaCreditoList.size() - 1);
        assertThat(testNotaCredito.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotaCredito.getNotaNro()).isEqualTo(UPDATED_NOTA_NRO);
        assertThat(testNotaCredito.getPuntoExpedicion()).isEqualTo(UPDATED_PUNTO_EXPEDICION);
        assertThat(testNotaCredito.getSucursal()).isEqualTo(UPDATED_SUCURSAL);
        assertThat(testNotaCredito.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testNotaCredito.getRuc()).isEqualTo(UPDATED_RUC);
        assertThat(testNotaCredito.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testNotaCredito.getMotivoEmision()).isEqualTo(UPDATED_MOTIVO_EMISION);
        assertThat(testNotaCredito.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingNotaCredito() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();
        notaCredito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaCreditoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notaCredito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notaCredito))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotaCredito() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();
        notaCredito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notaCredito))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotaCredito() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoRepository.findAll().size();
        notaCredito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(notaCredito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotaCredito in the database
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotaCredito() throws Exception {
        // Initialize the database
        notaCreditoRepository.saveAndFlush(notaCredito);

        int databaseSizeBeforeDelete = notaCreditoRepository.findAll().size();

        // Delete the notaCredito
        restNotaCreditoMockMvc
            .perform(delete(ENTITY_API_URL_ID, notaCredito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotaCredito> notaCreditoList = notaCreditoRepository.findAll();
        assertThat(notaCreditoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
