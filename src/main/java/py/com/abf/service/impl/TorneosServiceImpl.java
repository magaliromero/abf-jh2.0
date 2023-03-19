package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.Torneos;
import py.com.abf.repository.TorneosRepository;
import py.com.abf.service.TorneosService;

/**
 * Service Implementation for managing {@link Torneos}.
 */
@Service
@Transactional
public class TorneosServiceImpl implements TorneosService {

    private final Logger log = LoggerFactory.getLogger(TorneosServiceImpl.class);

    private final TorneosRepository torneosRepository;

    public TorneosServiceImpl(TorneosRepository torneosRepository) {
        this.torneosRepository = torneosRepository;
    }

    @Override
    public Torneos save(Torneos torneos) {
        log.debug("Request to save Torneos : {}", torneos);
        return torneosRepository.save(torneos);
    }

    @Override
    public Torneos update(Torneos torneos) {
        log.debug("Request to update Torneos : {}", torneos);
        return torneosRepository.save(torneos);
    }

    @Override
    public Optional<Torneos> partialUpdate(Torneos torneos) {
        log.debug("Request to partially update Torneos : {}", torneos);

        return torneosRepository
            .findById(torneos.getId())
            .map(existingTorneos -> {
                if (torneos.getNombreTorneo() != null) {
                    existingTorneos.setNombreTorneo(torneos.getNombreTorneo());
                }
                if (torneos.getFechaInicio() != null) {
                    existingTorneos.setFechaInicio(torneos.getFechaInicio());
                }
                if (torneos.getFechaFin() != null) {
                    existingTorneos.setFechaFin(torneos.getFechaFin());
                }
                if (torneos.getLugar() != null) {
                    existingTorneos.setLugar(torneos.getLugar());
                }
                if (torneos.getTiempo() != null) {
                    existingTorneos.setTiempo(torneos.getTiempo());
                }
                if (torneos.getTipoTorneo() != null) {
                    existingTorneos.setTipoTorneo(torneos.getTipoTorneo());
                }
                if (torneos.getTorneoEvaluado() != null) {
                    existingTorneos.setTorneoEvaluado(torneos.getTorneoEvaluado());
                }
                if (torneos.getFederado() != null) {
                    existingTorneos.setFederado(torneos.getFederado());
                }

                return existingTorneos;
            })
            .map(torneosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Torneos> findAll(Pageable pageable) {
        log.debug("Request to get all Torneos");
        return torneosRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Torneos> findOne(Long id) {
        log.debug("Request to get Torneos : {}", id);
        return torneosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Torneos : {}", id);
        torneosRepository.deleteById(id);
    }
}
