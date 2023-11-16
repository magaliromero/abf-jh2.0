package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.Materiales;
import py.com.abf.domain.Prestamos;
import py.com.abf.domain.enumeration.EstadosPrestamos;
import py.com.abf.repository.MaterialesRepository;
import py.com.abf.repository.PrestamosRepository;
import py.com.abf.service.PrestamosService;

/**
 * Service Implementation for managing {@link Prestamos}.
 */
@Service
@Transactional
public class PrestamosServiceImpl implements PrestamosService {

    private final Logger log = LoggerFactory.getLogger(PrestamosServiceImpl.class);

    private final PrestamosRepository prestamosRepository;
    private final MaterialesRepository materialesRepository;

    public PrestamosServiceImpl(PrestamosRepository prestamosRepository, MaterialesRepository materialesRepository) {
        this.prestamosRepository = prestamosRepository;
        this.materialesRepository = materialesRepository;
    }

    @Override
    public Prestamos save(Prestamos prestamos) {
        log.debug("Request to save Prestamos : {}", prestamos);

        Materiales mat = prestamos.getMateriales();
        mat.setCantidad(mat.getCantidad() - 1);
        mat.setCantidadEnPrestamo(mat.getCantidadEnPrestamo() + 1);
        this.materialesRepository.save(mat);

        return prestamosRepository.save(prestamos);
    }

    @Override
    public Prestamos update(Prestamos prestamos) {
        log.debug("Request to update Prestamos : {}", prestamos);
        if (prestamos.getEstado().equals(EstadosPrestamos.DEVUELTO)) {
            Materiales mat = prestamos.getMateriales();
            mat.setCantidad(mat.getCantidad() + 1);
            mat.setCantidadEnPrestamo(mat.getCantidadEnPrestamo() - 1);
            this.materialesRepository.save(mat);
        }
        return prestamosRepository.save(prestamos);
    }

    @Override
    public Optional<Prestamos> partialUpdate(Prestamos prestamos) {
        log.debug("Request to partially update Prestamos : {}", prestamos);

        return prestamosRepository
            .findById(prestamos.getId())
            .map(existingPrestamos -> {
                if (prestamos.getFechaPrestamo() != null) {
                    existingPrestamos.setFechaPrestamo(prestamos.getFechaPrestamo());
                }
                if (prestamos.getFechaDevolucion() != null) {
                    existingPrestamos.setFechaDevolucion(prestamos.getFechaDevolucion());
                }
                if (prestamos.getEstado() != null) {
                    existingPrestamos.setEstado(prestamos.getEstado());
                    if (prestamos.getEstado().equals(EstadosPrestamos.DEVUELTO)) {
                        Materiales mat = prestamos.getMateriales();
                        mat.setCantidad(mat.getCantidad() + 1);
                        mat.setCantidadEnPrestamo(mat.getCantidadEnPrestamo() - 1);
                        this.materialesRepository.save(mat);
                    }
                }
                if (prestamos.getObservaciones() != null) {
                    existingPrestamos.setObservaciones(prestamos.getObservaciones());
                }

                return existingPrestamos;
            })
            .map(prestamosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prestamos> findAll(Pageable pageable) {
        log.debug("Request to get all Prestamos");
        return prestamosRepository.findAll(pageable);
    }

    public Page<Prestamos> findAllWithEagerRelationships(Pageable pageable) {
        return prestamosRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prestamos> findOne(Long id) {
        log.debug("Request to get Prestamos : {}", id);
        return prestamosRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prestamos : {}", id);
        prestamosRepository.deleteById(id);
    }
}
