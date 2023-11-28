package py.com.abf.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.abf.domain.Facturas;

/**
 * Spring Data JPA repository for the Facturas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturasRepository extends JpaRepository<Facturas, Long>, JpaSpecificationExecutor<Facturas> {
    @Query(
        value = "SELECT MAX(CAST(factura_nro AS NUMERIC)) FROM factura WHERE punto_expedicion = :puntoExpedicion AND timbrado = :timbrado AND sucursal = :sucursal",
        nativeQuery = true
    )
    Integer findMaxFacturaNroByPuntoExpedicionAndTimbradoAndSucursal(
        @Param("puntoExpedicion") Integer puntoExpedicion,
        @Param("timbrado") Integer timbrado,
        @Param("sucursal") Integer sucursal
    );

    List<Facturas> findByFechaBetween(LocalDate p1, LocalDate p2);
}
