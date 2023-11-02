package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.NotaCredito;

/**
 * Spring Data JPA repository for the NotaCredito entity.
 */
@Repository
public interface NotaCreditoRepository extends JpaRepository<NotaCredito, Long>, JpaSpecificationExecutor<NotaCredito> {
    default Optional<NotaCredito> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NotaCredito> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NotaCredito> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select notaCredito from NotaCredito notaCredito left join fetch notaCredito.facturas",
        countQuery = "select count(notaCredito) from NotaCredito notaCredito"
    )
    Page<NotaCredito> findAllWithToOneRelationships(Pageable pageable);

    @Query("select notaCredito from NotaCredito notaCredito left join fetch notaCredito.facturas")
    List<NotaCredito> findAllWithToOneRelationships();

    @Query("select notaCredito from NotaCredito notaCredito left join fetch notaCredito.facturas where notaCredito.id =:id")
    Optional<NotaCredito> findOneWithToOneRelationships(@Param("id") Long id);
}
