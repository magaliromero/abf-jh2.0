package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.FichaPartidasTorneos;
import py.com.abf.repository.FichaPartidasTorneosRepository;
import py.com.abf.service.FichaPartidasTorneosService;

/**
 * Service Implementation for managing {@link FichaPartidasTorneos}.
 */
@Service
@Transactional
public class FichaPartidasTorneosServiceImpl implements FichaPartidasTorneosService {

    private final Logger log = LoggerFactory.getLogger(FichaPartidasTorneosServiceImpl.class);

    private final FichaPartidasTorneosRepository fichaPartidasTorneosRepository;

    public FichaPartidasTorneosServiceImpl(FichaPartidasTorneosRepository fichaPartidasTorneosRepository) {
        this.fichaPartidasTorneosRepository = fichaPartidasTorneosRepository;
    }

    @Override
    public FichaPartidasTorneos save(FichaPartidasTorneos fichaPartidasTorneos) {
        log.debug("Request to save FichaPartidasTorneos : {}", fichaPartidasTorneos);
        return fichaPartidasTorneosRepository.save(fichaPartidasTorneos);
    }

    @Override
    public FichaPartidasTorneos update(FichaPartidasTorneos fichaPartidasTorneos) {
        log.debug("Request to update FichaPartidasTorneos : {}", fichaPartidasTorneos);
        return fichaPartidasTorneosRepository.save(fichaPartidasTorneos);
    }

    @Override
    public Optional<FichaPartidasTorneos> partialUpdate(FichaPartidasTorneos fichaPartidasTorneos) {
        log.debug("Request to partially update FichaPartidasTorneos : {}", fichaPartidasTorneos);

        return fichaPartidasTorneosRepository
            .findById(fichaPartidasTorneos.getId())
            .map(existingFichaPartidasTorneos -> {
                if (fichaPartidasTorneos.getNombreContrincante() != null) {
                    existingFichaPartidasTorneos.setNombreContrincante(fichaPartidasTorneos.getNombreContrincante());
                }
                if (fichaPartidasTorneos.getDuracion() != null) {
                    existingFichaPartidasTorneos.setDuracion(fichaPartidasTorneos.getDuracion());
                }
                if (fichaPartidasTorneos.getWinner() != null) {
                    existingFichaPartidasTorneos.setWinner(fichaPartidasTorneos.getWinner());
                }
                if (fichaPartidasTorneos.getResultado() != null) {
                    existingFichaPartidasTorneos.setResultado(fichaPartidasTorneos.getResultado());
                }
                if (fichaPartidasTorneos.getComentarios() != null) {
                    existingFichaPartidasTorneos.setComentarios(fichaPartidasTorneos.getComentarios());
                }
                if (fichaPartidasTorneos.getNombreArbitro() != null) {
                    existingFichaPartidasTorneos.setNombreArbitro(fichaPartidasTorneos.getNombreArbitro());
                }

                return existingFichaPartidasTorneos;
            })
            .map(fichaPartidasTorneosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FichaPartidasTorneos> findAll(Pageable pageable) {
        log.debug("Request to get all FichaPartidasTorneos");
        return fichaPartidasTorneosRepository.findAll(pageable);
    }

    public Page<FichaPartidasTorneos> findAllWithEagerRelationships(Pageable pageable) {
        return fichaPartidasTorneosRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FichaPartidasTorneos> findOne(Long id) {
        log.debug("Request to get FichaPartidasTorneos : {}", id);
        return fichaPartidasTorneosRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FichaPartidasTorneos : {}", id);
        fichaPartidasTorneosRepository.deleteById(id);
    }
}
