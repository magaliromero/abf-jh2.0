package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.Cursos;

/**
 * Spring Data JPA repository for the Cursos entity.
 *
 * When extending this class, extend CursosRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CursosRepository
    extends CursosRepositoryWithBagRelationships, JpaRepository<Cursos, Long>, JpaSpecificationExecutor<Cursos> {
    default Optional<Cursos> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Cursos> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Cursos> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
