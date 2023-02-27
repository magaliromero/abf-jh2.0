package py.com.abf.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import py.com.abf.domain.MallaCurricular;

/**
 * Service Interface for managing {@link MallaCurricular}.
 */
public interface MallaCurricularService {
    /**
     * Save a mallaCurricular.
     *
     * @param mallaCurricular the entity to save.
     * @return the persisted entity.
     */
    MallaCurricular save(MallaCurricular mallaCurricular);

    /**
     * Updates a mallaCurricular.
     *
     * @param mallaCurricular the entity to update.
     * @return the persisted entity.
     */
    MallaCurricular update(MallaCurricular mallaCurricular);

    /**
     * Partially updates a mallaCurricular.
     *
     * @param mallaCurricular the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MallaCurricular> partialUpdate(MallaCurricular mallaCurricular);

    /**
     * Get all the mallaCurriculars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MallaCurricular> findAll(Pageable pageable);

    /**
     * Get all the mallaCurriculars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MallaCurricular> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mallaCurricular.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MallaCurricular> findOne(Long id);

    /**
     * Delete the "id" mallaCurricular.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
