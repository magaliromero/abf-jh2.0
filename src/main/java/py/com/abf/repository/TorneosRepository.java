package py.com.abf.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.Torneos;

/**
 * Spring Data JPA repository for the Torneos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TorneosRepository extends JpaRepository<Torneos, Long>, JpaSpecificationExecutor<Torneos> {}
