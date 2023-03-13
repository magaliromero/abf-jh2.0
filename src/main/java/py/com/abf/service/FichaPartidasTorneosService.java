package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.FichaPartidasTorneos;

/**
 * Service Interface for managing {@link FichaPartidasTorneos}.
 */
public interface FichaPartidasTorneosService {
    /**
     * Save a fichaPartidasTorneos.
     *
     * @param fichaPartidasTorneos the entity to save.
     * @return the persisted entity.
     */
    FichaPartidasTorneos save(FichaPartidasTorneos fichaPartidasTorneos);

    /**
     * Updates a fichaPartidasTorneos.
     *
     * @param fichaPartidasTorneos the entity to update.
     * @return the persisted entity.
     */
    FichaPartidasTorneos update(FichaPartidasTorneos fichaPartidasTorneos);

    /**
     * Partially updates a fichaPartidasTorneos.
     *
     * @param fichaPartidasTorneos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FichaPartidasTorneos> partialUpdate(FichaPartidasTorneos fichaPartidasTorneos);

    /**
     * Get all the fichaPartidasTorneos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FichaPartidasTorneos> findAll(Pageable pageable);

    /**
     * Get all the fichaPartidasTorneos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FichaPartidasTorneos> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fichaPartidasTorneos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FichaPartidasTorneos> findOne(Long id);

    /**
     * Delete the "id" fichaPartidasTorneos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
