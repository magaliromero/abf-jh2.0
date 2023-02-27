package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.MallaCurricular;

/**
 * Spring Data JPA repository for the MallaCurricular entity.
 *
 * When extending this class, extend MallaCurricularRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface MallaCurricularRepository
    extends MallaCurricularRepositoryWithBagRelationships, JpaRepository<MallaCurricular, Long>, JpaSpecificationExecutor<MallaCurricular> {
    default Optional<MallaCurricular> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<MallaCurricular> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<MallaCurricular> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
