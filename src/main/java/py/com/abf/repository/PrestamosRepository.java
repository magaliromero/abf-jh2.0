package py.com.abf.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.Prestamos;

/**
 * Spring Data JPA repository for the Prestamos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestamosRepository extends JpaRepository<Prestamos, Long>, JpaSpecificationExecutor<Prestamos> {}
