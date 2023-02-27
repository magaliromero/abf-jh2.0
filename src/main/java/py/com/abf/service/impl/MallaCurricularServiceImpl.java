package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.MallaCurricular;
import py.com.abf.repository.MallaCurricularRepository;
import py.com.abf.service.MallaCurricularService;

/**
 * Service Implementation for managing {@link MallaCurricular}.
 */
@Service
@Transactional
public class MallaCurricularServiceImpl implements MallaCurricularService {

    private final Logger log = LoggerFactory.getLogger(MallaCurricularServiceImpl.class);

    private final MallaCurricularRepository mallaCurricularRepository;

    public MallaCurricularServiceImpl(MallaCurricularRepository mallaCurricularRepository) {
        this.mallaCurricularRepository = mallaCurricularRepository;
    }

    @Override
    public MallaCurricular save(MallaCurricular mallaCurricular) {
        log.debug("Request to save MallaCurricular : {}", mallaCurricular);
        return mallaCurricularRepository.save(mallaCurricular);
    }

    @Override
    public MallaCurricular update(MallaCurricular mallaCurricular) {
        log.debug("Request to update MallaCurricular : {}", mallaCurricular);
        return mallaCurricularRepository.save(mallaCurricular);
    }

    @Override
    public Optional<MallaCurricular> partialUpdate(MallaCurricular mallaCurricular) {
        log.debug("Request to partially update MallaCurricular : {}", mallaCurricular);

        return mallaCurricularRepository
            .findById(mallaCurricular.getId())
            .map(existingMallaCurricular -> {
                if (mallaCurricular.getTitulo() != null) {
                    existingMallaCurricular.setTitulo(mallaCurricular.getTitulo());
                }
                if (mallaCurricular.getNivel() != null) {
                    existingMallaCurricular.setNivel(mallaCurricular.getNivel());
                }

                return existingMallaCurricular;
            })
            .map(mallaCurricularRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MallaCurricular> findAll(Pageable pageable) {
        log.debug("Request to get all MallaCurriculars");
        return mallaCurricularRepository.findAll(pageable);
    }

    public Page<MallaCurricular> findAllWithEagerRelationships(Pageable pageable) {
        return mallaCurricularRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MallaCurricular> findOne(Long id) {
        log.debug("Request to get MallaCurricular : {}", id);
        return mallaCurricularRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MallaCurricular : {}", id);
        mallaCurricularRepository.deleteById(id);
    }
}
