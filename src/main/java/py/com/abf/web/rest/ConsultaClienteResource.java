package py.com.abf.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.com.abf.domain.Clientes;
import py.com.abf.service.ConsultaClienteService;

@RestController
@RequestMapping("/consulta-ruc")
public class ConsultaClienteResource {

    private ConsultaClienteService service;

    public ConsultaClienteResource(ConsultaClienteService service) {
        this.service = service;
    }

    @GetMapping("/{ruc}")
    public ResponseEntity<Clientes> get(@PathVariable String ruc) {
        Clientes consulta = service.findOne(ruc);
        return ResponseEntity.ok().body(consulta);
    }
}
