package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.Torneos;

/**
 * Service Interface for managing {@link Torneos}.
 */
public interface TorneosService {
    /**
     * Save a torneos.
     *
     * @param torneos the entity to save.
     * @return the persisted entity.
     */
    Torneos save(Torneos torneos);

    /**
     * Updates a torneos.
     *
     * @param torneos the entity to update.
     * @return the persisted entity.
     */
    Torneos update(Torneos torneos);

    /**
     * Partially updates a torneos.
     *
     * @param torneos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Torneos> partialUpdate(Torneos torneos);

    /**
     * Get all the torneos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Torneos> findAll(Pageable pageable);

    /**
     * Get the "id" torneos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Torneos> findOne(Long id);

    /**
     * Delete the "id" torneos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
