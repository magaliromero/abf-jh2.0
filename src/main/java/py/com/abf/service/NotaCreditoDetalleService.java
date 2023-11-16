package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.NotaCreditoDetalle;

/**
 * Service Interface for managing {@link NotaCreditoDetalle}.
 */
public interface NotaCreditoDetalleService {
    /**
     * Save a notaCreditoDetalle.
     *
     * @param notaCreditoDetalle the entity to save.
     * @return the persisted entity.
     */
    NotaCreditoDetalle save(NotaCreditoDetalle notaCreditoDetalle);

    /**
     * Updates a notaCreditoDetalle.
     *
     * @param notaCreditoDetalle the entity to update.
     * @return the persisted entity.
     */
    NotaCreditoDetalle update(NotaCreditoDetalle notaCreditoDetalle);

    /**
     * Partially updates a notaCreditoDetalle.
     *
     * @param notaCreditoDetalle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotaCreditoDetalle> partialUpdate(NotaCreditoDetalle notaCreditoDetalle);

    /**
     * Get all the notaCreditoDetalles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotaCreditoDetalle> findAll(Pageable pageable);

    /**
     * Get all the notaCreditoDetalles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotaCreditoDetalle> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" notaCreditoDetalle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotaCreditoDetalle> findOne(Long id);

    /**
     * Delete the "id" notaCreditoDetalle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
