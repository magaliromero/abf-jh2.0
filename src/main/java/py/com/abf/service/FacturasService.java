package py.com.abf.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.Facturas;

/**
 * Service Interface for managing {@link py.com.abf.domain.Facturas}.
 */
public interface FacturasService {
    /**
     * Save a facturas.
     *
     * @param facturas the entity to save.
     * @return the persisted entity.
     */
    Facturas save(Facturas facturas);

    /**
     * Updates a facturas.
     *
     * @param facturas the entity to update.
     * @return the persisted entity.
     */
    Facturas update(Facturas facturas);

    /**
     * Partially updates a facturas.
     *
     * @param facturas the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Facturas> partialUpdate(Facturas facturas);

    /**
     * Get all the facturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Facturas> findAll(Pageable pageable);

    /**
     * Get all the Facturas where NotaCredito is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Facturas> findAllWhereNotaCreditoIsNull();

    /**
     * Get the "id" facturas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Facturas> findOne(Long id);

    /**
     * Delete the "id" facturas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
