package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.Usuarios;

/**
 * Service Interface for managing {@link Usuarios}.
 */
public interface UsuariosService {
    /**
     * Save a usuarios.
     *
     * @param usuarios the entity to save.
     * @return the persisted entity.
     */
    Usuarios save(Usuarios usuarios);

    /**
     * Updates a usuarios.
     *
     * @param usuarios the entity to update.
     * @return the persisted entity.
     */
    Usuarios update(Usuarios usuarios);

    /**
     * Partially updates a usuarios.
     *
     * @param usuarios the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Usuarios> partialUpdate(Usuarios usuarios);

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Usuarios> findAll(Pageable pageable);

    /**
     * Get the "id" usuarios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Usuarios> findOne(Long id);

    /**
     * Delete the "id" usuarios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
