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
import py.com.abf.domain.RegistroStockMateriales;
import py.com.abf.repository.RegistroStockMaterialesRepository;
import py.com.abf.service.criteria.RegistroStockMaterialesCriteria;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RegistroStockMateriales} entities in the database.
 * The main input is a {@link RegistroStockMaterialesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegistroStockMateriales} or a {@link Page} of {@link RegistroStockMateriales} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegistroStockMaterialesQueryService extends QueryService<RegistroStockMateriales> {

    private final Logger log = LoggerFactory.getLogger(RegistroStockMaterialesQueryService.class);

    private final RegistroStockMaterialesRepository registroStockMaterialesRepository;

    public RegistroStockMaterialesQueryService(RegistroStockMaterialesRepository registroStockMaterialesRepository) {
        this.registroStockMaterialesRepository = registroStockMaterialesRepository;
    }

    /**
     * Return a {@link List} of {@link RegistroStockMateriales} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegistroStockMateriales> findByCriteria(RegistroStockMaterialesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegistroStockMateriales> specification = createSpecification(criteria);
        return registroStockMaterialesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RegistroStockMateriales} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistroStockMateriales> findByCriteria(RegistroStockMaterialesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegistroStockMateriales> specification = createSpecification(criteria);
        return registroStockMaterialesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegistroStockMaterialesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegistroStockMateriales> specification = createSpecification(criteria);
        return registroStockMaterialesRepository.count(specification);
    }

    /**
     * Function to convert {@link RegistroStockMaterialesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegistroStockMateriales> createSpecification(RegistroStockMaterialesCriteria criteria) {
        Specification<RegistroStockMateriales> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegistroStockMateriales_.id));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), RegistroStockMateriales_.comentario));
            }
            if (criteria.getCantidadInicial() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCantidadInicial(), RegistroStockMateriales_.cantidadInicial));
            }
            if (criteria.getCantidadModificada() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getCantidadModificada(), RegistroStockMateriales_.cantidadModificada)
                    );
            }
            if (criteria.getMaterialesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterialesId(),
                            root -> root.join(RegistroStockMateriales_.materiales, JoinType.LEFT).get(Materiales_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
