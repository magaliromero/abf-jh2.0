package py.com.abf.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.Materiales;
import py.com.abf.domain.RegistrarModificacionStockParam;
import py.com.abf.domain.RegistroStockMateriales;
import py.com.abf.repository.MaterialesRepository;
import py.com.abf.repository.RegistroStockMaterialesRepository;
import py.com.abf.service.MaterialesService;

/**
 * Service Implementation for managing {@link Materiales}.
 */
@Service
@Transactional
public class MaterialesServiceImpl implements MaterialesService {

    private final Logger log = LoggerFactory.getLogger(MaterialesServiceImpl.class);

    private final MaterialesRepository materialesRepository;
    private final RegistroStockMaterialesRepository rsRepo;

    public MaterialesServiceImpl(MaterialesRepository materialesRepository, RegistroStockMaterialesRepository rsRepo) {
        this.materialesRepository = materialesRepository;
        this.rsRepo = rsRepo;
    }

    @Override
    public Materiales save(Materiales materiales) {
        log.debug("Request to save Materiales : {}", materiales);
        return materialesRepository.save(materiales);
    }

    @Override
    public Materiales update(Materiales materiales) {
        log.debug("Request to update Materiales : {}", materiales);
        return materialesRepository.save(materiales);
    }

    @Override
    public Optional<Materiales> partialUpdate(Materiales materiales) {
        log.debug("Request to partially update Materiales : {}", materiales);

        return materialesRepository
            .findById(materiales.getId())
            .map(existingMateriales -> {
                if (materiales.getDescripcion() != null) {
                    existingMateriales.setDescripcion(materiales.getDescripcion());
                }
                if (materiales.getCantidad() != null) {
                    existingMateriales.setCantidad(materiales.getCantidad());
                }
                if (materiales.getCantidadEnPrestamo() != null) {
                    existingMateriales.setCantidadEnPrestamo(materiales.getCantidadEnPrestamo());
                }

                return existingMateriales;
            })
            .map(materialesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Materiales> findAll(Pageable pageable) {
        log.debug("Request to get all Materiales");
        return materialesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materiales> findOne(Long id) {
        log.debug("Request to get Materiales : {}", id);
        return materialesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materiales : {}", id);
        materialesRepository.deleteById(id);
    }

    public Materiales actualizarStockMateriales(RegistrarModificacionStockParam param) {
        Materiales mat = param.getMaterial();
        if (mat.getId() != null) {
            Materiales prev = this.materialesRepository.findById(mat.getId()).orElse(null);
            System.out.println(prev.getCantidad() + "----" + mat.getCantidad());
            if (mat.getCantidad() != prev.getCantidad()) {
                RegistroStockMateriales rs = new RegistroStockMateriales();
                rs.setMateriales(mat);
                rs.setCantidadInicial(prev.getCantidad());
                rs.setCantidadModificada(mat.getCantidad());
                rs.setComentario(param.getObservacion());
                LocalDate currentDate = LocalDate.now();

                rs.setFecha(currentDate);
                // agregar seteo de fecha
                // rs.setFecha(new Date());
                this.rsRepo.save(rs);
            }
        }
        this.materialesRepository.save(mat);

        return mat;
    }
}
