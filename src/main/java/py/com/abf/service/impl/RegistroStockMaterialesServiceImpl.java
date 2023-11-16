package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.RegistroStockMateriales;
import py.com.abf.repository.RegistroStockMaterialesRepository;
import py.com.abf.service.RegistroStockMaterialesService;

/**
 * Service Implementation for managing {@link RegistroStockMateriales}.
 */
@Service
@Transactional
public class RegistroStockMaterialesServiceImpl implements RegistroStockMaterialesService {

    private final Logger log = LoggerFactory.getLogger(RegistroStockMaterialesServiceImpl.class);

    private final RegistroStockMaterialesRepository registroStockMaterialesRepository;

    public RegistroStockMaterialesServiceImpl(RegistroStockMaterialesRepository registroStockMaterialesRepository) {
        this.registroStockMaterialesRepository = registroStockMaterialesRepository;
    }

    @Override
    public RegistroStockMateriales save(RegistroStockMateriales registroStockMateriales) {
        log.debug("Request to save RegistroStockMateriales : {}", registroStockMateriales);
        return registroStockMaterialesRepository.save(registroStockMateriales);
    }

    @Override
    public RegistroStockMateriales update(RegistroStockMateriales registroStockMateriales) {
        log.debug("Request to update RegistroStockMateriales : {}", registroStockMateriales);
        return registroStockMaterialesRepository.save(registroStockMateriales);
    }

    @Override
    public Optional<RegistroStockMateriales> partialUpdate(RegistroStockMateriales registroStockMateriales) {
        log.debug("Request to partially update RegistroStockMateriales : {}", registroStockMateriales);

        return registroStockMaterialesRepository
            .findById(registroStockMateriales.getId())
            .map(existingRegistroStockMateriales -> {
                if (registroStockMateriales.getComentario() != null) {
                    existingRegistroStockMateriales.setComentario(registroStockMateriales.getComentario());
                }
                if (registroStockMateriales.getCantidadInicial() != null) {
                    existingRegistroStockMateriales.setCantidadInicial(registroStockMateriales.getCantidadInicial());
                }
                if (registroStockMateriales.getCantidadModificada() != null) {
                    existingRegistroStockMateriales.setCantidadModificada(registroStockMateriales.getCantidadModificada());
                }
                if (registroStockMateriales.getFecha() != null) {
                    existingRegistroStockMateriales.setFecha(registroStockMateriales.getFecha());
                }

                return existingRegistroStockMateriales;
            })
            .map(registroStockMaterialesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegistroStockMateriales> findAll(Pageable pageable) {
        log.debug("Request to get all RegistroStockMateriales");
        return registroStockMaterialesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroStockMateriales> findOne(Long id) {
        log.debug("Request to get RegistroStockMateriales : {}", id);
        return registroStockMaterialesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegistroStockMateriales : {}", id);
        registroStockMaterialesRepository.deleteById(id);
    }
}
