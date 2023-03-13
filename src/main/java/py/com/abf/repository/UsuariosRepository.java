package py.com.abf.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.Usuarios;

/**
 * Spring Data JPA repository for the Usuarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>, JpaSpecificationExecutor<Usuarios> {}
