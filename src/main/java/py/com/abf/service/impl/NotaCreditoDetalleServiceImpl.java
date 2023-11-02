package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.repository.NotaCreditoDetalleRepository;
import py.com.abf.service.NotaCreditoDetalleService;

/**
 * Service Implementation for managing {@link py.com.abf.domain.NotaCreditoDetalle}.
 */
@Service
@Transactional
public class NotaCreditoDetalleServiceImpl implements NotaCreditoDetalleService {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoDetalleServiceImpl.class);

    private final NotaCreditoDetalleRepository notaCreditoDetalleRepository;

    public NotaCreditoDetalleServiceImpl(NotaCreditoDetalleRepository notaCreditoDetalleRepository) {
        this.notaCreditoDetalleRepository = notaCreditoDetalleRepository;
    }

    @Override
    public NotaCreditoDetalle save(NotaCreditoDetalle notaCreditoDetalle) {
        log.debug("Request to save NotaCreditoDetalle : {}", notaCreditoDetalle);
        return notaCreditoDetalleRepository.save(notaCreditoDetalle);
    }

    @Override
    public NotaCreditoDetalle update(NotaCreditoDetalle notaCreditoDetalle) {
        log.debug("Request to update NotaCreditoDetalle : {}", notaCreditoDetalle);
        return notaCreditoDetalleRepository.save(notaCreditoDetalle);
    }

    @Override
    public Optional<NotaCreditoDetalle> partialUpdate(NotaCreditoDetalle notaCreditoDetalle) {
        log.debug("Request to partially update NotaCreditoDetalle : {}", notaCreditoDetalle);

        return notaCreditoDetalleRepository
            .findById(notaCreditoDetalle.getId())
            .map(existingNotaCreditoDetalle -> {
                if (notaCreditoDetalle.getCantidad() != null) {
                    existingNotaCreditoDetalle.setCantidad(notaCreditoDetalle.getCantidad());
                }
                if (notaCreditoDetalle.getPrecioUnitario() != null) {
                    existingNotaCreditoDetalle.setPrecioUnitario(notaCreditoDetalle.getPrecioUnitario());
                }
                if (notaCreditoDetalle.getSubtotal() != null) {
                    existingNotaCreditoDetalle.setSubtotal(notaCreditoDetalle.getSubtotal());
                }
                if (notaCreditoDetalle.getPorcentajeIva() != null) {
                    existingNotaCreditoDetalle.setPorcentajeIva(notaCreditoDetalle.getPorcentajeIva());
                }
                if (notaCreditoDetalle.getValorPorcentaje() != null) {
                    existingNotaCreditoDetalle.setValorPorcentaje(notaCreditoDetalle.getValorPorcentaje());
                }

                return existingNotaCreditoDetalle;
            })
            .map(notaCreditoDetalleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotaCreditoDetalle> findAll(Pageable pageable) {
        log.debug("Request to get all NotaCreditoDetalles");
        return notaCreditoDetalleRepository.findAll(pageable);
    }

    public Page<NotaCreditoDetalle> findAllWithEagerRelationships(Pageable pageable) {
        return notaCreditoDetalleRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotaCreditoDetalle> findOne(Long id) {
        log.debug("Request to get NotaCreditoDetalle : {}", id);
        return notaCreditoDetalleRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotaCreditoDetalle : {}", id);
        notaCreditoDetalleRepository.deleteById(id);
    }
}
