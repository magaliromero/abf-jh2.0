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
import py.com.abf.domain.Usuarios;
import py.com.abf.repository.UsuariosRepository;
import py.com.abf.service.criteria.UsuariosCriteria;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Usuarios} entities in the database.
 * The main input is a {@link UsuariosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Usuarios} or a {@link Page} of {@link Usuarios} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuariosQueryService extends QueryService<Usuarios> {

    private final Logger log = LoggerFactory.getLogger(UsuariosQueryService.class);

    private final UsuariosRepository usuariosRepository;

    public UsuariosQueryService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    /**
     * Return a {@link List} of {@link Usuarios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Usuarios> findByCriteria(UsuariosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Usuarios> specification = createSpecification(criteria);
        return usuariosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Usuarios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Usuarios> findByCriteria(UsuariosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Usuarios> specification = createSpecification(criteria);
        return usuariosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuariosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Usuarios> specification = createSpecification(criteria);
        return usuariosRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuariosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Usuarios> createSpecification(UsuariosCriteria criteria) {
        Specification<Usuarios> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Usuarios_.id));
            }
            if (criteria.getDocumento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumento(), Usuarios_.documento));
            }
            if (criteria.getIdRol() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdRol(), Usuarios_.idRol));
            }
        }
        return specification;
    }
}
