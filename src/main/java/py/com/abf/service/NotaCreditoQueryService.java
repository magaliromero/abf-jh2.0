package py.com.abf.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.*; // for static metamodels
import py.com.abf.domain.NotaCredito;
import py.com.abf.repository.NotaCreditoRepository;
import py.com.abf.service.criteria.NotaCreditoCriteria;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link NotaCredito} entities in the database.
 * The main input is a {@link NotaCreditoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotaCredito} or a {@link Page} of {@link NotaCredito} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotaCreditoQueryService extends QueryService<NotaCredito> {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoQueryService.class);

    private final NotaCreditoRepository notaCreditoRepository;

    public NotaCreditoQueryService(NotaCreditoRepository notaCreditoRepository) {
        this.notaCreditoRepository = notaCreditoRepository;
    }

    /**
     * Return a {@link List} of {@link NotaCredito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotaCredito> findByCriteria(NotaCreditoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotaCredito> specification = createSpecification(criteria);
        return notaCreditoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotaCredito} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotaCredito> findByCriteria(NotaCreditoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotaCredito> specification = createSpecification(criteria);
        return notaCreditoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotaCreditoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotaCredito> specification = createSpecification(criteria);
        return notaCreditoRepository.count(specification);
    }

    /**
     * Function to convert {@link NotaCreditoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotaCredito> createSpecification(NotaCreditoCriteria criteria) {
        Specification<NotaCredito> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotaCredito_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), NotaCredito_.fecha));
            }
            if (criteria.getNotaNro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotaNro(), NotaCredito_.notaNro));
            }
            if (criteria.getPuntoExpedicion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPuntoExpedicion(), NotaCredito_.puntoExpedicion));
            }
            if (criteria.getSucursal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSucursal(), NotaCredito_.sucursal));
            }
            if (criteria.getRazonSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazonSocial(), NotaCredito_.razonSocial));
            }
            if (criteria.getRuc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRuc(), NotaCredito_.ruc));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), NotaCredito_.direccion));
            }
            if (criteria.getMotivoEmision() != null) {
                specification = specification.and(buildSpecification(criteria.getMotivoEmision(), NotaCredito_.motivoEmision));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), NotaCredito_.estado));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), NotaCredito_.total));
            }
            if (criteria.getNotaCreditoDetalleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotaCreditoDetalleId(),
                            root -> root.join(NotaCredito_.notaCreditoDetalles, JoinType.LEFT).get(NotaCreditoDetalle_.id)
                        )
                    );
            }
            if (criteria.getFacturasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFacturasId(),
                            root -> root.join(NotaCredito_.facturas, JoinType.LEFT).get(Facturas_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
