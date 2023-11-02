package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.Sucursales;

/**
 * Service Interface for managing {@link py.com.abf.domain.Sucursales}.
 */
public interface SucursalesService {
    /**
     * Save a sucursales.
     *
     * @param sucursales the entity to save.
     * @return the persisted entity.
     */
    Sucursales save(Sucursales sucursales);

    /**
     * Updates a sucursales.
     *
     * @param sucursales the entity to update.
     * @return the persisted entity.
     */
    Sucursales update(Sucursales sucursales);

    /**
     * Partially updates a sucursales.
     *
     * @param sucursales the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sucursales> partialUpdate(Sucursales sucursales);

    /**
     * Get all the sucursales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sucursales> findAll(Pageable pageable);

    /**
     * Get all the sucursales with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sucursales> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" sucursales.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sucursales> findOne(Long id);

    /**
     * Delete the "id" sucursales.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
