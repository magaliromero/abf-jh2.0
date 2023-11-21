package py.com.abf.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.com.abf.service.CodigoVerificadorService;
import py.com.abf.service.NumeroFacturaService;

@RestController
@RequestMapping("api/numero-factura")
public class NumeroFacturaResource {

    private final NumeroFacturaService service;

    public NumeroFacturaResource(NumeroFacturaService service) {
        this.service = service;
    }

    @GetMapping("/{timbrado}/{pe}/{sucursal}")
    public ResponseEntity<Integer> getAllcodigo(@PathVariable Integer timbrado, @PathVariable Integer pe, @PathVariable Integer sucursal) {
        Integer codigo = service.obtenerSiguienteNumeroFactura(pe, sucursal, timbrado);
        return ResponseEntity.ok().body(codigo);
    }
}
