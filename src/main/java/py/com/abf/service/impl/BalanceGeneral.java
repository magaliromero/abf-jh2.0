package py.com.abf.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import py.com.abf.domain.Facturas;
import py.com.abf.domain.NotaCredito;
import py.com.abf.domain.param.BalanceParam;
import py.com.abf.domain.param.BalanceResult;
import py.com.abf.service.FacturasService;

@Service
public class BalanceGeneral {

    private FacturasServiceImpl facturaService;
    private NotaCreditoServiceImpl notaCreditoService;

    public BalanceGeneral(FacturasServiceImpl facturaService, NotaCreditoServiceImpl notaCreditoService) {
        this.facturaService = facturaService;
        this.notaCreditoService = notaCreditoService;
    }

    public BalanceResult obtenerBalanceGeneral(BalanceParam param) {
        LocalDate p1 = param.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate p2 = param.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Facturas> listaFactura = this.facturaService.obtenerFacturasPorFecha(p1, p2);
        List<NotaCredito> listaNotaCredito = this.notaCreditoService.obtenerNotasPorFecha(p1, p2);

        BalanceResult data = new BalanceResult();
        data.setListaFactura(listaFactura);
        data.setListaNC(listaNotaCredito);
        return data;
    }
}
