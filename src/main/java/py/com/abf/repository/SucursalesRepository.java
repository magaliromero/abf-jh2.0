package py.com.abf.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.Sucursales;

/**
 * Spring Data JPA repository for the Sucursales entity.
 */
@Repository
public interface SucursalesRepository extends JpaRepository<Sucursales, Long>, JpaSpecificationExecutor<Sucursales> {
    default Optional<Sucursales> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Sucursales> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Sucursales> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select sucursales from Sucursales sucursales left join fetch sucursales.timbrados",
        countQuery = "select count(sucursales) from Sucursales sucursales"
    )
    Page<Sucursales> findAllWithToOneRelationships(Pageable pageable);

    @Query("select sucursales from Sucursales sucursales left join fetch sucursales.timbrados")
    List<Sucursales> findAllWithToOneRelationships();

    @Query("select sucursales from Sucursales sucursales left join fetch sucursales.timbrados where sucursales.id =:id")
    Optional<Sucursales> findOneWithToOneRelationships(@Param("id") Long id);
}
