package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.NotaCreditoDetalle;

/**
 * Spring Data JPA repository for the NotaCreditoDetalle entity.
 */
@Repository
public interface NotaCreditoDetalleRepository
    extends JpaRepository<NotaCreditoDetalle, Long>, JpaSpecificationExecutor<NotaCreditoDetalle> {
    default Optional<NotaCreditoDetalle> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NotaCreditoDetalle> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NotaCreditoDetalle> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct notaCreditoDetalle from NotaCreditoDetalle notaCreditoDetalle left join fetch notaCreditoDetalle.notaCredito left join fetch notaCreditoDetalle.producto",
        countQuery = "select count(distinct notaCreditoDetalle) from NotaCreditoDetalle notaCreditoDetalle"
    )
    Page<NotaCreditoDetalle> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct notaCreditoDetalle from NotaCreditoDetalle notaCreditoDetalle left join fetch notaCreditoDetalle.notaCredito left join fetch notaCreditoDetalle.producto"
    )
    List<NotaCreditoDetalle> findAllWithToOneRelationships();

    @Query(
        "select notaCreditoDetalle from NotaCreditoDetalle notaCreditoDetalle left join fetch notaCreditoDetalle.notaCredito left join fetch notaCreditoDetalle.producto where notaCreditoDetalle.id =:id"
    )
    Optional<NotaCreditoDetalle> findOneWithToOneRelationships(@Param("id") Long id);
}
