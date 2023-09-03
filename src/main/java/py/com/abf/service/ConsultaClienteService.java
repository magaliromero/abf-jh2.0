package py.com.abf.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import py.com.abf.domain.Clientes;
import py.com.abf.repository.ClientesRepository;

@Service
public class ConsultaClienteService {

    private ClientesRepository clientesRepository;

    public ConsultaClienteService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public Clientes findOne(String ruc) {
        return clientesRepository.findByRuc(ruc);
    }
}
