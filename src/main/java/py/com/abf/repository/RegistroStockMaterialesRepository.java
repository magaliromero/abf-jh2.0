package py.com.abf.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.RegistroStockMateriales;

/**
 * Spring Data JPA repository for the RegistroStockMateriales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroStockMaterialesRepository
    extends JpaRepository<RegistroStockMateriales, Long>, JpaSpecificationExecutor<RegistroStockMateriales> {}
