package py.com.abf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.jasperreports.data.provider.DataSourceProviderDataAdapterService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import py.com.abf.domain.Clientes;
import py.com.abf.domain.FacturaDetalle;
import py.com.abf.domain.Facturas;
import py.com.abf.domain.NotaCredito;
import py.com.abf.domain.NotaCreditoDetalle;
import py.com.abf.domain.Timbrados;
import py.com.abf.report.SimpleReportExporter;
import py.com.abf.report.SimpleReportFiller;
import py.com.abf.service.impl.ClientesServiceImpl;
import py.com.abf.service.impl.TimbradosServiceImpl;

@Service
public class ReporteService {

    public ClientesServiceImpl clienteService;
    public FacturasService facturasService;
    public TimbradosServiceImpl timbradoService;
    public FacturaDetalleService facturasDetalleService;
    public NotaCreditoService notaCreditoService;
    private ConsultaClienteService consultaRucService;

    public ReporteService(
        NotaCreditoService notaCreditoService,
        ClientesServiceImpl clienteService,
        FacturasService facturasService,
        ConsultaClienteService consultaRucService,
        TimbradosServiceImpl timbradoService
    ) {
        this.timbradoService = timbradoService;
        this.clienteService = clienteService;
        this.facturasService = facturasService;
        this.consultaRucService = consultaRucService;
        this.notaCreditoService = notaCreditoService;
    }

    public void exportReport(OutputStream out, String reporte, String tipoReporte, HashMap<String, Object> parametrosPeticion) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            HashMap<String, Object> parameters = new HashMap<String, Object>();
            List<HashMap<String, Object>> lista = new ArrayList<>();
            Timbrados t = null;
            switch (reporte) {
                case "factura":
                    Long facutraId = (Long) parametrosPeticion.get("facturaId");
                    Facturas fact = this.facturasService.findOne(facutraId).orElse(null);
                    System.out.println("DATA RUC" + (fact.getRuc() == "5159789-6"));
                    Clientes cliente = this.consultaRucService.findOne(fact.getRuc());

                    System.out.println(cliente);
                    System.out.println(fact.getTimbrado());
                    //HashMap<String, Object> facParams = objectMapper.convertValue(cliente, HashMap.class);
                    t = this.timbradoService.obtenerPorNroTimbrado(fact.getTimbrado());

                    parameters.put("fechaInicio", t != null ? t.getFechaInicio().toString() : null);
                    parameters.put("fechaFin", t != null ? t.getFechaFin().toString() : null);

                    parameters.put("timbrado", fact.getTimbrado().toString());
                    parameters.put("fechaFactura", fact.getFecha().toString());
                    parameters.put("facturaNumero", fact.getSucursal() + "-" + fact.getPuntoExpedicion() + "-" + fact.getFacturaNro());
                    parameters.put("rucAbf", "1231231-1");
                    parameters.put("telefonoAbf", "0999 312 132");
                    parameters.put("direccionAbf", "Academia de Ajedrez Bobby Fischer.");
                    parameters.put("razonSocial", cliente.getRazonSocial());
                    parameters.put("documento", cliente.getDocumento());
                    parameters.put("importeTotal", fact.getTotal());
                    parameters.put("direccion", cliente.getDireccion());
                    if (fact.getPoseeNC() == null || !fact.getPoseeNC()) {
                        fact.poseeNC(true);
                        this.facturasService.save(fact);
                    }
                    ///lista = mapperReporte.preciosPromedioTop5(new Date(), new Date());
                    //facturasDetalleService.

                    Set<FacturaDetalle> listaDetalle = fact.getFacturaDetalles();
                    for (FacturaDetalle itemDet : listaDetalle) {
                        HashMap<String, Object> item = new HashMap<>();

                        item.put("cantidad", itemDet.getCantidad());
                        item.put("precio", itemDet.getPrecioUnitario());
                        item.put("exenta", 0L);
                        item.put("ivacinco", 0L);
                        item.put("ivadiez", 0L);
                        item.put("descripcion", itemDet.getProducto().getDescripcion());
                        item.put("subtotal", itemDet.getSubtotal());
                        lista.add(item);
                    }
                    break;
                case "notaCredito":
                    Long ncId = (Long) parametrosPeticion.get("notaCreditoId");
                    NotaCredito nc = this.notaCreditoService.findOne(ncId).orElse(null);
                    System.out.println("DATA RUC" + (nc.getRuc() == "5159789-6"));
                    Clientes clienteNC = this.consultaRucService.findOne(nc.getRuc());
                    t = this.timbradoService.obtenerPorNroTimbrado(nc.getTimbrado());
                    //HashMap<String, Object> facParams = objectMapper.convertValue(cliente, HashMap.class);
                    parameters.put("timbrado", nc.getTimbrado().toString());
                    parameters.put("fechaInicio", t.getFechaInicio() != null ? t.getFechaInicio().toString() : null);
                    parameters.put("fechaFin", t.getFechaFin() != null ? t.getFechaFin().toString() : null);
                    parameters.put("timbrado", nc.getTimbrado().toString());
                    parameters.put("fechaFactura", nc.getFecha().toString());
                    parameters.put("facturaNumero", nc.getSucursal() + "-" + nc.getPuntoExpedicion() + "-" + nc.getNotaNro());
                    parameters.put("rucAbf", "1231231-1");
                    parameters.put("telefonoAbf", "0999 312 132");
                    parameters.put("direccionAbf", "Academia de Ajedrez Bobby Fischer.");
                    parameters.put("razonSocial", clienteNC.getRazonSocial());
                    parameters.put("documento", clienteNC.getDocumento());
                    parameters.put("importeTotal", nc.getTotal());
                    parameters.put("direccion", clienteNC.getDireccion());
                    ///lista = mapperReporte.preciosPromedioTop5(new Date(), new Date());
                    //facturasDetalleService.

                    Set<NotaCreditoDetalle> listaDetalNC = nc.getNotaCreditoDetalles();
                    for (NotaCreditoDetalle itemDet : listaDetalNC) {
                        HashMap<String, Object> item = new HashMap<>();

                        item.put("cantidad", itemDet.getCantidad());
                        item.put("precio", itemDet.getPrecioUnitario());
                        item.put("exenta", 0L);
                        item.put("ivacinco", 0L);
                        item.put("ivadiez", 0L);
                        item.put("descripcion", itemDet.getProducto().getDescripcion());
                        item.put("subtotal", itemDet.getSubtotal());
                        lista.add(item);
                    }

                    break;
                default:
                    break;
            }

            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(lista);
            SimpleReportFiller fill = new SimpleReportFiller();
            fill.setParameters(parameters);
            fill.setDataSource(datasource);
            fill.setReportFileName(reporte);
            fill.prepareReport();
            SimpleReportExporter simpleExporter = new SimpleReportExporter(fill.getJasperPrint());

            simpleExporter.exportToPdf("employeeReport.pdf", "baeldung", out);
            // JasperExportManager.exportReportToPdfStream(fill.getJasperPrint(),data);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public String formatNumero(String inicio, Long idPedido) {
        String id = idPedido.toString();

        inicio = inicio.substring(0, inicio.length() - id.length());
        return inicio + id;
    }
}
