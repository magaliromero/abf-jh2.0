package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.RegistroStockMateriales;

/**
 * Service Interface for managing {@link RegistroStockMateriales}.
 */
public interface RegistroStockMaterialesService {
    /**
     * Save a registroStockMateriales.
     *
     * @param registroStockMateriales the entity to save.
     * @return the persisted entity.
     */
    RegistroStockMateriales save(RegistroStockMateriales registroStockMateriales);

    /**
     * Updates a registroStockMateriales.
     *
     * @param registroStockMateriales the entity to update.
     * @return the persisted entity.
     */
    RegistroStockMateriales update(RegistroStockMateriales registroStockMateriales);

    /**
     * Partially updates a registroStockMateriales.
     *
     * @param registroStockMateriales the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegistroStockMateriales> partialUpdate(RegistroStockMateriales registroStockMateriales);

    /**
     * Get all the registroStockMateriales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegistroStockMateriales> findAll(Pageable pageable);

    /**
     * Get the "id" registroStockMateriales.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistroStockMateriales> findOne(Long id);

    /**
     * Delete the "id" registroStockMateriales.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
