package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.NotaCredito;

/**
 * Service Interface for managing {@link py.com.abf.domain.NotaCredito}.
 */
public interface NotaCreditoService {
    /**
     * Save a notaCredito.
     *
     * @param notaCredito the entity to save.
     * @return the persisted entity.
     */
    NotaCredito save(NotaCredito notaCredito);

    /**
     * Updates a notaCredito.
     *
     * @param notaCredito the entity to update.
     * @return the persisted entity.
     */
    NotaCredito update(NotaCredito notaCredito);

    /**
     * Partially updates a notaCredito.
     *
     * @param notaCredito the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotaCredito> partialUpdate(NotaCredito notaCredito);

    /**
     * Get all the notaCreditos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotaCredito> findAll(Pageable pageable);

    /**
     * Get all the notaCreditos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotaCredito> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" notaCredito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotaCredito> findOne(Long id);

    /**
     * Delete the "id" notaCredito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
