package py.com.abf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import py.com.abf.domain.NotaCredito;
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.domain.Productos;
import py.com.abf.repository.NotaCreditoDetalleRepository;
import py.com.abf.service.NotaCreditoDetalleService;
import py.com.abf.service.criteria.NotaCreditoDetalleCriteria;

/**
 * Integration tests for the {@link NotaCreditoDetalleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NotaCreditoDetalleResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;
    private static final Integer SMALLER_CANTIDAD = 1 - 1;

    private static final Integer DEFAULT_PRECIO_UNITARIO = 1;
    private static final Integer UPDATED_PRECIO_UNITARIO = 2;
    private static final Integer SMALLER_PRECIO_UNITARIO = 1 - 1;

    private static final Integer DEFAULT_SUBTOTAL = 1;
    private static final Integer UPDATED_SUBTOTAL = 2;
    private static final Integer SMALLER_SUBTOTAL = 1 - 1;

    private static final Integer DEFAULT_PORCENTAJE_IVA = 1;
    private static final Integer UPDATED_PORCENTAJE_IVA = 2;
    private static final Integer SMALLER_PORCENTAJE_IVA = 1 - 1;

    private static final Integer DEFAULT_VALOR_PORCENTAJE = 1;
    private static final Integer UPDATED_VALOR_PORCENTAJE = 2;
    private static final Integer SMALLER_VALOR_PORCENTAJE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/nota-credito-detalles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotaCreditoDetalleRepository notaCreditoDetalleRepository;

    @Mock
    private NotaCreditoDetalleRepository notaCreditoDetalleRepositoryMock;

    @Mock
    private NotaCreditoDetalleService notaCreditoDetalleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotaCreditoDetalleMockMvc;

    private NotaCreditoDetalle notaCreditoDetalle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaCreditoDetalle createEntity(EntityManager em) {
        NotaCreditoDetalle notaCreditoDetalle = new NotaCreditoDetalle()
            .cantidad(DEFAULT_CANTIDAD)
            .precioUnitario(DEFAULT_PRECIO_UNITARIO)
            .subtotal(DEFAULT_SUBTOTAL)
            .porcentajeIva(DEFAULT_PORCENTAJE_IVA)
            .valorPorcentaje(DEFAULT_VALOR_PORCENTAJE);
        // Add required entity
        NotaCredito notaCredito;
        if (TestUtil.findAll(em, NotaCredito.class).isEmpty()) {
            notaCredito = NotaCreditoResourceIT.createEntity(em);
            em.persist(notaCredito);
            em.flush();
        } else {
            notaCredito = TestUtil.findAll(em, NotaCredito.class).get(0);
        }
        notaCreditoDetalle.setNotaCredito(notaCredito);
        // Add required entity
        Productos productos;
        if (TestUtil.findAll(em, Productos.class).isEmpty()) {
            productos = ProductosResourceIT.createEntity(em);
            em.persist(productos);
            em.flush();
        } else {
            productos = TestUtil.findAll(em, Productos.class).get(0);
        }
        notaCreditoDetalle.setProducto(productos);
        return notaCreditoDetalle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotaCreditoDetalle createUpdatedEntity(EntityManager em) {
        NotaCreditoDetalle notaCreditoDetalle = new NotaCreditoDetalle()
            .cantidad(UPDATED_CANTIDAD)
            .precioUnitario(UPDATED_PRECIO_UNITARIO)
            .subtotal(UPDATED_SUBTOTAL)
            .porcentajeIva(UPDATED_PORCENTAJE_IVA)
            .valorPorcentaje(UPDATED_VALOR_PORCENTAJE);
        // Add required entity
        NotaCredito notaCredito;
        if (TestUtil.findAll(em, NotaCredito.class).isEmpty()) {
            notaCredito = NotaCreditoResourceIT.createUpdatedEntity(em);
            em.persist(notaCredito);
            em.flush();
        } else {
            notaCredito = TestUtil.findAll(em, NotaCredito.class).get(0);
        }
        notaCreditoDetalle.setNotaCredito(notaCredito);
        // Add required entity
        Productos productos;
        if (TestUtil.findAll(em, Productos.class).isEmpty()) {
            productos = ProductosResourceIT.createUpdatedEntity(em);
            em.persist(productos);
            em.flush();
        } else {
            productos = TestUtil.findAll(em, Productos.class).get(0);
        }
        notaCreditoDetalle.setProducto(productos);
        return notaCreditoDetalle;
    }

    @BeforeEach
    public void initTest() {
        notaCreditoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    void createNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeCreate = notaCreditoDetalleRepository.findAll().size();
        // Create the NotaCreditoDetalle
        restNotaCreditoDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isCreated());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        NotaCreditoDetalle testNotaCreditoDetalle = notaCreditoDetalleList.get(notaCreditoDetalleList.size() - 1);
        assertThat(testNotaCreditoDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testNotaCreditoDetalle.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
        assertThat(testNotaCreditoDetalle.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testNotaCreditoDetalle.getPorcentajeIva()).isEqualTo(DEFAULT_PORCENTAJE_IVA);
        assertThat(testNotaCreditoDetalle.getValorPorcentaje()).isEqualTo(DEFAULT_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void createNotaCreditoDetalleWithExistingId() throws Exception {
        // Create the NotaCreditoDetalle with an existing ID
        notaCreditoDetalle.setId(1L);

        int databaseSizeBeforeCreate = notaCreditoDetalleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotaCreditoDetalleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetalles() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList
        restNotaCreditoDetalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notaCreditoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL)))
            .andExpect(jsonPath("$.[*].porcentajeIva").value(hasItem(DEFAULT_PORCENTAJE_IVA)))
            .andExpect(jsonPath("$.[*].valorPorcentaje").value(hasItem(DEFAULT_VALOR_PORCENTAJE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotaCreditoDetallesWithEagerRelationshipsIsEnabled() throws Exception {
        when(notaCreditoDetalleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotaCreditoDetalleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(notaCreditoDetalleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNotaCreditoDetallesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(notaCreditoDetalleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNotaCreditoDetalleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(notaCreditoDetalleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNotaCreditoDetalle() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get the notaCreditoDetalle
        restNotaCreditoDetalleMockMvc
            .perform(get(ENTITY_API_URL_ID, notaCreditoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notaCreditoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precioUnitario").value(DEFAULT_PRECIO_UNITARIO))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL))
            .andExpect(jsonPath("$.porcentajeIva").value(DEFAULT_PORCENTAJE_IVA))
            .andExpect(jsonPath("$.valorPorcentaje").value(DEFAULT_VALOR_PORCENTAJE));
    }

    @Test
    @Transactional
    void getNotaCreditoDetallesByIdFiltering() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        Long id = notaCreditoDetalle.getId();

        defaultNotaCreditoDetalleShouldBeFound("id.equals=" + id);
        defaultNotaCreditoDetalleShouldNotBeFound("id.notEquals=" + id);

        defaultNotaCreditoDetalleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotaCreditoDetalleShouldNotBeFound("id.greaterThan=" + id);

        defaultNotaCreditoDetalleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotaCreditoDetalleShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad equals to DEFAULT_CANTIDAD
        defaultNotaCreditoDetalleShouldBeFound("cantidad.equals=" + DEFAULT_CANTIDAD);

        // Get all the notaCreditoDetalleList where cantidad equals to UPDATED_CANTIDAD
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.equals=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad in DEFAULT_CANTIDAD or UPDATED_CANTIDAD
        defaultNotaCreditoDetalleShouldBeFound("cantidad.in=" + DEFAULT_CANTIDAD + "," + UPDATED_CANTIDAD);

        // Get all the notaCreditoDetalleList where cantidad equals to UPDATED_CANTIDAD
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.in=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad is not null
        defaultNotaCreditoDetalleShouldBeFound("cantidad.specified=true");

        // Get all the notaCreditoDetalleList where cantidad is null
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad is greater than or equal to DEFAULT_CANTIDAD
        defaultNotaCreditoDetalleShouldBeFound("cantidad.greaterThanOrEqual=" + DEFAULT_CANTIDAD);

        // Get all the notaCreditoDetalleList where cantidad is greater than or equal to UPDATED_CANTIDAD
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.greaterThanOrEqual=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad is less than or equal to DEFAULT_CANTIDAD
        defaultNotaCreditoDetalleShouldBeFound("cantidad.lessThanOrEqual=" + DEFAULT_CANTIDAD);

        // Get all the notaCreditoDetalleList where cantidad is less than or equal to SMALLER_CANTIDAD
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.lessThanOrEqual=" + SMALLER_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad is less than DEFAULT_CANTIDAD
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.lessThan=" + DEFAULT_CANTIDAD);

        // Get all the notaCreditoDetalleList where cantidad is less than UPDATED_CANTIDAD
        defaultNotaCreditoDetalleShouldBeFound("cantidad.lessThan=" + UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByCantidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where cantidad is greater than DEFAULT_CANTIDAD
        defaultNotaCreditoDetalleShouldNotBeFound("cantidad.greaterThan=" + DEFAULT_CANTIDAD);

        // Get all the notaCreditoDetalleList where cantidad is greater than SMALLER_CANTIDAD
        defaultNotaCreditoDetalleShouldBeFound("cantidad.greaterThan=" + SMALLER_CANTIDAD);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario equals to DEFAULT_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.equals=" + DEFAULT_PRECIO_UNITARIO);

        // Get all the notaCreditoDetalleList where precioUnitario equals to UPDATED_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.equals=" + UPDATED_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario in DEFAULT_PRECIO_UNITARIO or UPDATED_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.in=" + DEFAULT_PRECIO_UNITARIO + "," + UPDATED_PRECIO_UNITARIO);

        // Get all the notaCreditoDetalleList where precioUnitario equals to UPDATED_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.in=" + UPDATED_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario is not null
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.specified=true");

        // Get all the notaCreditoDetalleList where precioUnitario is null
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario is greater than or equal to DEFAULT_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.greaterThanOrEqual=" + DEFAULT_PRECIO_UNITARIO);

        // Get all the notaCreditoDetalleList where precioUnitario is greater than or equal to UPDATED_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.greaterThanOrEqual=" + UPDATED_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario is less than or equal to DEFAULT_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.lessThanOrEqual=" + DEFAULT_PRECIO_UNITARIO);

        // Get all the notaCreditoDetalleList where precioUnitario is less than or equal to SMALLER_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.lessThanOrEqual=" + SMALLER_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario is less than DEFAULT_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.lessThan=" + DEFAULT_PRECIO_UNITARIO);

        // Get all the notaCreditoDetalleList where precioUnitario is less than UPDATED_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.lessThan=" + UPDATED_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPrecioUnitarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where precioUnitario is greater than DEFAULT_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldNotBeFound("precioUnitario.greaterThan=" + DEFAULT_PRECIO_UNITARIO);

        // Get all the notaCreditoDetalleList where precioUnitario is greater than SMALLER_PRECIO_UNITARIO
        defaultNotaCreditoDetalleShouldBeFound("precioUnitario.greaterThan=" + SMALLER_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal equals to DEFAULT_SUBTOTAL
        defaultNotaCreditoDetalleShouldBeFound("subtotal.equals=" + DEFAULT_SUBTOTAL);

        // Get all the notaCreditoDetalleList where subtotal equals to UPDATED_SUBTOTAL
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.equals=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal in DEFAULT_SUBTOTAL or UPDATED_SUBTOTAL
        defaultNotaCreditoDetalleShouldBeFound("subtotal.in=" + DEFAULT_SUBTOTAL + "," + UPDATED_SUBTOTAL);

        // Get all the notaCreditoDetalleList where subtotal equals to UPDATED_SUBTOTAL
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.in=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal is not null
        defaultNotaCreditoDetalleShouldBeFound("subtotal.specified=true");

        // Get all the notaCreditoDetalleList where subtotal is null
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal is greater than or equal to DEFAULT_SUBTOTAL
        defaultNotaCreditoDetalleShouldBeFound("subtotal.greaterThanOrEqual=" + DEFAULT_SUBTOTAL);

        // Get all the notaCreditoDetalleList where subtotal is greater than or equal to UPDATED_SUBTOTAL
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.greaterThanOrEqual=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal is less than or equal to DEFAULT_SUBTOTAL
        defaultNotaCreditoDetalleShouldBeFound("subtotal.lessThanOrEqual=" + DEFAULT_SUBTOTAL);

        // Get all the notaCreditoDetalleList where subtotal is less than or equal to SMALLER_SUBTOTAL
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.lessThanOrEqual=" + SMALLER_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal is less than DEFAULT_SUBTOTAL
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.lessThan=" + DEFAULT_SUBTOTAL);

        // Get all the notaCreditoDetalleList where subtotal is less than UPDATED_SUBTOTAL
        defaultNotaCreditoDetalleShouldBeFound("subtotal.lessThan=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesBySubtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where subtotal is greater than DEFAULT_SUBTOTAL
        defaultNotaCreditoDetalleShouldNotBeFound("subtotal.greaterThan=" + DEFAULT_SUBTOTAL);

        // Get all the notaCreditoDetalleList where subtotal is greater than SMALLER_SUBTOTAL
        defaultNotaCreditoDetalleShouldBeFound("subtotal.greaterThan=" + SMALLER_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva equals to DEFAULT_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.equals=" + DEFAULT_PORCENTAJE_IVA);

        // Get all the notaCreditoDetalleList where porcentajeIva equals to UPDATED_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.equals=" + UPDATED_PORCENTAJE_IVA);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva in DEFAULT_PORCENTAJE_IVA or UPDATED_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.in=" + DEFAULT_PORCENTAJE_IVA + "," + UPDATED_PORCENTAJE_IVA);

        // Get all the notaCreditoDetalleList where porcentajeIva equals to UPDATED_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.in=" + UPDATED_PORCENTAJE_IVA);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva is not null
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.specified=true");

        // Get all the notaCreditoDetalleList where porcentajeIva is null
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva is greater than or equal to DEFAULT_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.greaterThanOrEqual=" + DEFAULT_PORCENTAJE_IVA);

        // Get all the notaCreditoDetalleList where porcentajeIva is greater than or equal to UPDATED_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.greaterThanOrEqual=" + UPDATED_PORCENTAJE_IVA);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva is less than or equal to DEFAULT_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.lessThanOrEqual=" + DEFAULT_PORCENTAJE_IVA);

        // Get all the notaCreditoDetalleList where porcentajeIva is less than or equal to SMALLER_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.lessThanOrEqual=" + SMALLER_PORCENTAJE_IVA);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva is less than DEFAULT_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.lessThan=" + DEFAULT_PORCENTAJE_IVA);

        // Get all the notaCreditoDetalleList where porcentajeIva is less than UPDATED_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.lessThan=" + UPDATED_PORCENTAJE_IVA);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByPorcentajeIvaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where porcentajeIva is greater than DEFAULT_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldNotBeFound("porcentajeIva.greaterThan=" + DEFAULT_PORCENTAJE_IVA);

        // Get all the notaCreditoDetalleList where porcentajeIva is greater than SMALLER_PORCENTAJE_IVA
        defaultNotaCreditoDetalleShouldBeFound("porcentajeIva.greaterThan=" + SMALLER_PORCENTAJE_IVA);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje equals to DEFAULT_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.equals=" + DEFAULT_VALOR_PORCENTAJE);

        // Get all the notaCreditoDetalleList where valorPorcentaje equals to UPDATED_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.equals=" + UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsInShouldWork() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje in DEFAULT_VALOR_PORCENTAJE or UPDATED_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.in=" + DEFAULT_VALOR_PORCENTAJE + "," + UPDATED_VALOR_PORCENTAJE);

        // Get all the notaCreditoDetalleList where valorPorcentaje equals to UPDATED_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.in=" + UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje is not null
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.specified=true");

        // Get all the notaCreditoDetalleList where valorPorcentaje is null
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.specified=false");
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje is greater than or equal to DEFAULT_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.greaterThanOrEqual=" + DEFAULT_VALOR_PORCENTAJE);

        // Get all the notaCreditoDetalleList where valorPorcentaje is greater than or equal to UPDATED_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.greaterThanOrEqual=" + UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje is less than or equal to DEFAULT_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.lessThanOrEqual=" + DEFAULT_VALOR_PORCENTAJE);

        // Get all the notaCreditoDetalleList where valorPorcentaje is less than or equal to SMALLER_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.lessThanOrEqual=" + SMALLER_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsLessThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje is less than DEFAULT_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.lessThan=" + DEFAULT_VALOR_PORCENTAJE);

        // Get all the notaCreditoDetalleList where valorPorcentaje is less than UPDATED_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.lessThan=" + UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByValorPorcentajeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        // Get all the notaCreditoDetalleList where valorPorcentaje is greater than DEFAULT_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldNotBeFound("valorPorcentaje.greaterThan=" + DEFAULT_VALOR_PORCENTAJE);

        // Get all the notaCreditoDetalleList where valorPorcentaje is greater than SMALLER_VALOR_PORCENTAJE
        defaultNotaCreditoDetalleShouldBeFound("valorPorcentaje.greaterThan=" + SMALLER_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByNotaCreditoIsEqualToSomething() throws Exception {
        NotaCredito notaCredito;
        if (TestUtil.findAll(em, NotaCredito.class).isEmpty()) {
            notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);
            notaCredito = NotaCreditoResourceIT.createEntity(em);
        } else {
            notaCredito = TestUtil.findAll(em, NotaCredito.class).get(0);
        }
        em.persist(notaCredito);
        em.flush();
        notaCreditoDetalle.setNotaCredito(notaCredito);
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);
        Long notaCreditoId = notaCredito.getId();

        // Get all the notaCreditoDetalleList where notaCredito equals to notaCreditoId
        defaultNotaCreditoDetalleShouldBeFound("notaCreditoId.equals=" + notaCreditoId);

        // Get all the notaCreditoDetalleList where notaCredito equals to (notaCreditoId + 1)
        defaultNotaCreditoDetalleShouldNotBeFound("notaCreditoId.equals=" + (notaCreditoId + 1));
    }

    @Test
    @Transactional
    void getAllNotaCreditoDetallesByProductoIsEqualToSomething() throws Exception {
        Productos producto;
        if (TestUtil.findAll(em, Productos.class).isEmpty()) {
            notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);
            producto = ProductosResourceIT.createEntity(em);
        } else {
            producto = TestUtil.findAll(em, Productos.class).get(0);
        }
        em.persist(producto);
        em.flush();
        notaCreditoDetalle.setProducto(producto);
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);
        Long productoId = producto.getId();

        // Get all the notaCreditoDetalleList where producto equals to productoId
        defaultNotaCreditoDetalleShouldBeFound("productoId.equals=" + productoId);

        // Get all the notaCreditoDetalleList where producto equals to (productoId + 1)
        defaultNotaCreditoDetalleShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotaCreditoDetalleShouldBeFound(String filter) throws Exception {
        restNotaCreditoDetalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notaCreditoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL)))
            .andExpect(jsonPath("$.[*].porcentajeIva").value(hasItem(DEFAULT_PORCENTAJE_IVA)))
            .andExpect(jsonPath("$.[*].valorPorcentaje").value(hasItem(DEFAULT_VALOR_PORCENTAJE)));

        // Check, that the count call also returns 1
        restNotaCreditoDetalleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotaCreditoDetalleShouldNotBeFound(String filter) throws Exception {
        restNotaCreditoDetalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotaCreditoDetalleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotaCreditoDetalle() throws Exception {
        // Get the notaCreditoDetalle
        restNotaCreditoDetalleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotaCreditoDetalle() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();

        // Update the notaCreditoDetalle
        NotaCreditoDetalle updatedNotaCreditoDetalle = notaCreditoDetalleRepository.findById(notaCreditoDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedNotaCreditoDetalle are not directly saved in db
        em.detach(updatedNotaCreditoDetalle);
        updatedNotaCreditoDetalle
            .cantidad(UPDATED_CANTIDAD)
            .precioUnitario(UPDATED_PRECIO_UNITARIO)
            .subtotal(UPDATED_SUBTOTAL)
            .porcentajeIva(UPDATED_PORCENTAJE_IVA)
            .valorPorcentaje(UPDATED_VALOR_PORCENTAJE);

        restNotaCreditoDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotaCreditoDetalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotaCreditoDetalle))
            )
            .andExpect(status().isOk());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
        NotaCreditoDetalle testNotaCreditoDetalle = notaCreditoDetalleList.get(notaCreditoDetalleList.size() - 1);
        assertThat(testNotaCreditoDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testNotaCreditoDetalle.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
        assertThat(testNotaCreditoDetalle.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testNotaCreditoDetalle.getPorcentajeIva()).isEqualTo(UPDATED_PORCENTAJE_IVA);
        assertThat(testNotaCreditoDetalle.getValorPorcentaje()).isEqualTo(UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void putNonExistingNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();
        notaCreditoDetalle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaCreditoDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notaCreditoDetalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();
        notaCreditoDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoDetalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();
        notaCreditoDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoDetalleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotaCreditoDetalleWithPatch() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();

        // Update the notaCreditoDetalle using partial update
        NotaCreditoDetalle partialUpdatedNotaCreditoDetalle = new NotaCreditoDetalle();
        partialUpdatedNotaCreditoDetalle.setId(notaCreditoDetalle.getId());

        partialUpdatedNotaCreditoDetalle
            .subtotal(UPDATED_SUBTOTAL)
            .porcentajeIva(UPDATED_PORCENTAJE_IVA)
            .valorPorcentaje(UPDATED_VALOR_PORCENTAJE);

        restNotaCreditoDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotaCreditoDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotaCreditoDetalle))
            )
            .andExpect(status().isOk());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
        NotaCreditoDetalle testNotaCreditoDetalle = notaCreditoDetalleList.get(notaCreditoDetalleList.size() - 1);
        assertThat(testNotaCreditoDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testNotaCreditoDetalle.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
        assertThat(testNotaCreditoDetalle.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testNotaCreditoDetalle.getPorcentajeIva()).isEqualTo(UPDATED_PORCENTAJE_IVA);
        assertThat(testNotaCreditoDetalle.getValorPorcentaje()).isEqualTo(UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void fullUpdateNotaCreditoDetalleWithPatch() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();

        // Update the notaCreditoDetalle using partial update
        NotaCreditoDetalle partialUpdatedNotaCreditoDetalle = new NotaCreditoDetalle();
        partialUpdatedNotaCreditoDetalle.setId(notaCreditoDetalle.getId());

        partialUpdatedNotaCreditoDetalle
            .cantidad(UPDATED_CANTIDAD)
            .precioUnitario(UPDATED_PRECIO_UNITARIO)
            .subtotal(UPDATED_SUBTOTAL)
            .porcentajeIva(UPDATED_PORCENTAJE_IVA)
            .valorPorcentaje(UPDATED_VALOR_PORCENTAJE);

        restNotaCreditoDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotaCreditoDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotaCreditoDetalle))
            )
            .andExpect(status().isOk());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
        NotaCreditoDetalle testNotaCreditoDetalle = notaCreditoDetalleList.get(notaCreditoDetalleList.size() - 1);
        assertThat(testNotaCreditoDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testNotaCreditoDetalle.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
        assertThat(testNotaCreditoDetalle.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testNotaCreditoDetalle.getPorcentajeIva()).isEqualTo(UPDATED_PORCENTAJE_IVA);
        assertThat(testNotaCreditoDetalle.getValorPorcentaje()).isEqualTo(UPDATED_VALOR_PORCENTAJE);
    }

    @Test
    @Transactional
    void patchNonExistingNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();
        notaCreditoDetalle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotaCreditoDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notaCreditoDetalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();
        notaCreditoDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotaCreditoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = notaCreditoDetalleRepository.findAll().size();
        notaCreditoDetalle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotaCreditoDetalleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notaCreditoDetalle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotaCreditoDetalle in the database
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotaCreditoDetalle() throws Exception {
        // Initialize the database
        notaCreditoDetalleRepository.saveAndFlush(notaCreditoDetalle);

        int databaseSizeBeforeDelete = notaCreditoDetalleRepository.findAll().size();

        // Delete the notaCreditoDetalle
        restNotaCreditoDetalleMockMvc
            .perform(delete(ENTITY_API_URL_ID, notaCreditoDetalle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotaCreditoDetalle> notaCreditoDetalleList = notaCreditoDetalleRepository.findAll();
        assertThat(notaCreditoDetalleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
