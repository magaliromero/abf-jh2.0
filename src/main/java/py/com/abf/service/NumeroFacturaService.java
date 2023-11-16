package py.com.abf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.abf.domain.Facturas;
import py.com.abf.repository.FacturasRepository;
import py.com.abf.web.rest.ProductosResource;

@Service
public class NumeroFacturaService {

    private final Logger LOG = LoggerFactory.getLogger(NumeroFacturaService.class);

    @Autowired
    private FacturasRepository facturasRepository;

    public Integer obtenerSiguienteNumeroFactura(Integer pe, Integer sucursal, Integer timbrado) {
        LOG.info(timbrado + "-" + pe + "-" + sucursal);
        Integer fac = this.facturasRepository.findMaxFacturaNroByPuntoExpedicionAndTimbradoAndSucursal(pe, timbrado, sucursal);
        LOG.info("data {}", fac);

        return fac + 1;
    }
}
