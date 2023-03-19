package py.com.abf.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.abf.domain.Usuarios;
import py.com.abf.repository.UsuariosRepository;
import py.com.abf.service.UsuariosService;

/**
 * Service Implementation for managing {@link Usuarios}.
 */
@Service
@Transactional
public class UsuariosServiceImpl implements UsuariosService {

    private final Logger log = LoggerFactory.getLogger(UsuariosServiceImpl.class);

    private final UsuariosRepository usuariosRepository;

    public UsuariosServiceImpl(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public Usuarios save(Usuarios usuarios) {
        log.debug("Request to save Usuarios : {}", usuarios);
        return usuariosRepository.save(usuarios);
    }

    @Override
    public Usuarios update(Usuarios usuarios) {
        log.debug("Request to update Usuarios : {}", usuarios);
        return usuariosRepository.save(usuarios);
    }

    @Override
    public Optional<Usuarios> partialUpdate(Usuarios usuarios) {
        log.debug("Request to partially update Usuarios : {}", usuarios);

        return usuariosRepository
            .findById(usuarios.getId())
            .map(existingUsuarios -> {
                if (usuarios.getDocumento() != null) {
                    existingUsuarios.setDocumento(usuarios.getDocumento());
                }
                if (usuarios.getIdRol() != null) {
                    existingUsuarios.setIdRol(usuarios.getIdRol());
                }

                return existingUsuarios;
            })
            .map(usuariosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuarios> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuariosRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuarios> findOne(Long id) {
        log.debug("Request to get Usuarios : {}", id);
        return usuariosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Usuarios : {}", id);
        usuariosRepository.deleteById(id);
    }
}
