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
import py.com.abf.domain.Prestamos;
import py.com.abf.repository.PrestamosRepository;
import py.com.abf.service.criteria.PrestamosCriteria;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Prestamos} entities in the database.
 * The main input is a {@link PrestamosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Prestamos} or a {@link Page} of {@link Prestamos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrestamosQueryService extends QueryService<Prestamos> {

    private final Logger log = LoggerFactory.getLogger(PrestamosQueryService.class);

    private final PrestamosRepository prestamosRepository;

    public PrestamosQueryService(PrestamosRepository prestamosRepository) {
        this.prestamosRepository = prestamosRepository;
    }

    /**
     * Return a {@link List} of {@link Prestamos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Prestamos> findByCriteria(PrestamosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Prestamos> specification = createSpecification(criteria);
        return prestamosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Prestamos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Prestamos> findByCriteria(PrestamosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Prestamos> specification = createSpecification(criteria);
        return prestamosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrestamosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Prestamos> specification = createSpecification(criteria);
        return prestamosRepository.count(specification);
    }

    /**
     * Function to convert {@link PrestamosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Prestamos> createSpecification(PrestamosCriteria criteria) {
        Specification<Prestamos> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Prestamos_.id));
            }
            if (criteria.getFechaPrestamo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaPrestamo(), Prestamos_.fechaPrestamo));
            }
            if (criteria.getFechaDevolucion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaDevolucion(), Prestamos_.fechaDevolucion));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Prestamos_.estado));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), Prestamos_.observaciones));
            }
            if (criteria.getMaterialesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterialesId(),
                            root -> root.join(Prestamos_.materiales, JoinType.LEFT).get(Materiales_.id)
                        )
                    );
            }
            if (criteria.getAlumnosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAlumnosId(), root -> root.join(Prestamos_.alumnos, JoinType.LEFT).get(Alumnos_.id))
                    );
            }
        }
        return specification;
    }
}
