package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.NotaCredito;
import py.com.abf.repository.NotaCreditoRepository;
import py.com.abf.service.NotaCreditoService;

/**
 * Service Implementation for managing {@link py.com.abf.domain.NotaCredito}.
 */
@Service
@Transactional
public class NotaCreditoServiceImpl implements NotaCreditoService {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoServiceImpl.class);

    private final NotaCreditoRepository notaCreditoRepository;

    public NotaCreditoServiceImpl(NotaCreditoRepository notaCreditoRepository) {
        this.notaCreditoRepository = notaCreditoRepository;
    }

    @Override
    public NotaCredito save(NotaCredito notaCredito) {
        log.debug("Request to save NotaCredito : {}", notaCredito);
        return notaCreditoRepository.save(notaCredito);
    }

    @Override
    public NotaCredito update(NotaCredito notaCredito) {
        log.debug("Request to update NotaCredito : {}", notaCredito);
        return notaCreditoRepository.save(notaCredito);
    }

    @Override
    public Optional<NotaCredito> partialUpdate(NotaCredito notaCredito) {
        log.debug("Request to partially update NotaCredito : {}", notaCredito);

        return notaCreditoRepository
            .findById(notaCredito.getId())
            .map(existingNotaCredito -> {
                if (notaCredito.getFecha() != null) {
                    existingNotaCredito.setFecha(notaCredito.getFecha());
                }
                if (notaCredito.getNotaNro() != null) {
                    existingNotaCredito.setNotaNro(notaCredito.getNotaNro());
                }
                if (notaCredito.getPuntoExpedicion() != null) {
                    existingNotaCredito.setPuntoExpedicion(notaCredito.getPuntoExpedicion());
                }
                if (notaCredito.getSucursal() != null) {
                    existingNotaCredito.setSucursal(notaCredito.getSucursal());
                }
                if (notaCredito.getRazonSocial() != null) {
                    existingNotaCredito.setRazonSocial(notaCredito.getRazonSocial());
                }
                if (notaCredito.getRuc() != null) {
                    existingNotaCredito.setRuc(notaCredito.getRuc());
                }
                if (notaCredito.getDireccion() != null) {
                    existingNotaCredito.setDireccion(notaCredito.getDireccion());
                }
                if (notaCredito.getMotivoEmision() != null) {
                    existingNotaCredito.setMotivoEmision(notaCredito.getMotivoEmision());
                }
                if (notaCredito.getTotal() != null) {
                    existingNotaCredito.setTotal(notaCredito.getTotal());
                }

                return existingNotaCredito;
            })
            .map(notaCreditoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotaCredito> findAll(Pageable pageable) {
        log.debug("Request to get all NotaCreditos");
        return notaCreditoRepository.findAll(pageable);
    }

    public Page<NotaCredito> findAllWithEagerRelationships(Pageable pageable) {
        return notaCreditoRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotaCredito> findOne(Long id) {
        log.debug("Request to get NotaCredito : {}", id);
        return notaCreditoRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotaCredito : {}", id);
        notaCreditoRepository.deleteById(id);
    }
}
