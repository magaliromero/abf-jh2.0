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
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.repository.NotaCreditoDetalleRepository;
import py.com.abf.service.criteria.NotaCreditoDetalleCriteria;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link NotaCreditoDetalle} entities in the database.
 * The main input is a {@link NotaCreditoDetalleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotaCreditoDetalle} or a {@link Page} of {@link NotaCreditoDetalle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotaCreditoDetalleQueryService extends QueryService<NotaCreditoDetalle> {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoDetalleQueryService.class);

    private final NotaCreditoDetalleRepository notaCreditoDetalleRepository;

    public NotaCreditoDetalleQueryService(NotaCreditoDetalleRepository notaCreditoDetalleRepository) {
        this.notaCreditoDetalleRepository = notaCreditoDetalleRepository;
    }

    /**
     * Return a {@link List} of {@link NotaCreditoDetalle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotaCreditoDetalle> findByCriteria(NotaCreditoDetalleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotaCreditoDetalle> specification = createSpecification(criteria);
        return notaCreditoDetalleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NotaCreditoDetalle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotaCreditoDetalle> findByCriteria(NotaCreditoDetalleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotaCreditoDetalle> specification = createSpecification(criteria);
        return notaCreditoDetalleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotaCreditoDetalleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotaCreditoDetalle> specification = createSpecification(criteria);
        return notaCreditoDetalleRepository.count(specification);
    }

    /**
     * Function to convert {@link NotaCreditoDetalleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotaCreditoDetalle> createSpecification(NotaCreditoDetalleCriteria criteria) {
        Specification<NotaCreditoDetalle> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotaCreditoDetalle_.id));
            }
            if (criteria.getCantidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCantidad(), NotaCreditoDetalle_.cantidad));
            }
            if (criteria.getPrecioUnitario() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPrecioUnitario(), NotaCreditoDetalle_.precioUnitario));
            }
            if (criteria.getSubtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubtotal(), NotaCreditoDetalle_.subtotal));
            }
            if (criteria.getPorcentajeIva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPorcentajeIva(), NotaCreditoDetalle_.porcentajeIva));
            }
            if (criteria.getValorPorcentaje() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getValorPorcentaje(), NotaCreditoDetalle_.valorPorcentaje));
            }
            if (criteria.getNotaCreditoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotaCreditoId(),
                            root -> root.join(NotaCreditoDetalle_.notaCredito, JoinType.LEFT).get(NotaCredito_.id)
                        )
                    );
            }
            if (criteria.getProductoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductoId(),
                            root -> root.join(NotaCreditoDetalle_.producto, JoinType.LEFT).get(Productos_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
