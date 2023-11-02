package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.Cursos;

/**
 * Service Interface for managing {@link py.com.abf.domain.Cursos}.
 */
public interface CursosService {
    /**
     * Save a cursos.
     *
     * @param cursos the entity to save.
     * @return the persisted entity.
     */
    Cursos save(Cursos cursos);

    /**
     * Updates a cursos.
     *
     * @param cursos the entity to update.
     * @return the persisted entity.
     */
    Cursos update(Cursos cursos);

    /**
     * Partially updates a cursos.
     *
     * @param cursos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cursos> partialUpdate(Cursos cursos);

    /**
     * Get all the cursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cursos> findAll(Pageable pageable);

    /**
     * Get all the cursos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cursos> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" cursos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cursos> findOne(Long id);

    /**
     * Delete the "id" cursos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
