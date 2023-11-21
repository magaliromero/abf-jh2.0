package py.com.abf.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.FacturaDetalle;
import py.com.abf.domain.NCDetalle;
import py.com.abf.domain.NCDetalleItem;
import py.com.abf.domain.NotaCredito;
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.domain.Productos;
import py.com.abf.domain.enumeration.EstadosFacturas;
import py.com.abf.repository.NotaCreditoDetalleRepository;
import py.com.abf.repository.NotaCreditoRepository;
import py.com.abf.service.NotaCreditoService;

/**
 * Service Implementation for managing {@link NotaCredito}.
 */
@Service
@Transactional
public class NotaCreditoServiceImpl implements NotaCreditoService {

    private final Logger log = LoggerFactory.getLogger(NotaCreditoServiceImpl.class);

    private final NotaCreditoRepository notaCreditoRepository;
    private final NotaCreditoDetalleRepository notaCreditoDetalleRepository;
    private final ProductosServiceImpl productoServiceImpl;

    public NotaCreditoServiceImpl(
        NotaCreditoRepository notaCreditoRepository,
        NotaCreditoDetalleRepository notaCreditoDetalleRepository,
        ProductosServiceImpl productosServiceImpl
    ) {
        this.notaCreditoRepository = notaCreditoRepository;
        this.notaCreditoDetalleRepository = notaCreditoDetalleRepository;
        this.productoServiceImpl = productosServiceImpl;
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
                if (notaCredito.getEstado() != null) {
                    existingNotaCredito.setEstado(notaCredito.getEstado());
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

    public NotaCredito saveWithDetails(NCDetalle data) {
        log.debug("Request to save data : {}", data);
        data.getCabecera().setEstado(EstadosFacturas.PAGADO);

        NotaCredito f = notaCreditoRepository.save(data.getCabecera());
        log.debug("NC guardada : {}", f);

        List<NCDetalleItem> items = data.getDetalle();
        for (NCDetalleItem temp : items) {
            Productos p = this.productoServiceImpl.findOne(temp.getProducto().longValue()).orElse(null);
            if (p != null) {
                NotaCreditoDetalle fd = new NotaCreditoDetalle();
                fd.setCantidad(temp.getCantidad());
                fd.setNotaCredito(f);
                fd.setPorcentajeIva(p.getPorcentajeIva());
                fd.setSubtotal(temp.getSubtotal());
                fd.setValorPorcentaje((temp.getSubtotal() * p.getPorcentajeIva()) / 100);
                fd.setPrecioUnitario(temp.getPrecioUnitario());
                fd.setProducto(p);
                log.debug("Request to save data-detalle : {}", fd);

                notaCreditoDetalleRepository.save(fd);
            }
        }
        return f;
    }
}
