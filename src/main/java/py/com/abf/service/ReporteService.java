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
import py.com.abf.report.SimpleReportExporter;
import py.com.abf.report.SimpleReportFiller;

@Service
public class ReporteService {

    public ClientesService clienteService;
    public FacturasService facturasService;

    public ReporteService(ClientesService clienteService, FacturasService facturasService) {
        this.clienteService = clienteService;
        this.facturasService = facturasService;
    }

    public void exportReport(OutputStream out, String reporte, String tipoReporte, HashMap<String, Object> parametrosPeticion) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            HashMap<String, Object> parameters = new HashMap<String, Object>();
            List<HashMap<String, Object>> lista = new ArrayList<>();
            switch (reporte) {
                case "factura":
                    Long id = (Long) parametrosPeticion.get("clienteId");
                    Long facutraId = (Long) parametrosPeticion.get("facturaId");
                    Clientes cliente = this.clienteService.findOne(id).orElse(null);
                    Facturas fact = this.facturasService.findOne(facutraId).orElse(null);

                    System.out.println(fact.getFacturaDetalles());
                    //HashMap<String, Object> facParams = objectMapper.convertValue(cliente, HashMap.class);
                    parameters.put("timbrado", fact.getTimbrado().toString());
                    parameters.put("facturaNumero", fact.getFacturaNro());
                    parameters.put("ruc", cliente);
                    parameters.put("razonSocial", cliente.getRazonSocial());
                    parameters.put("documento", cliente.getDocumento());
                    ///lista = mapperReporte.preciosPromedioTop5(new Date(), new Date());
                    HashMap<String, Object> item = new HashMap<>();

                    /* <field name="cantidad" class="java.lang.Integer"/>
	<field name="descripcion" class="java.lang.String"/>
	<field name="precio" class="java.lang.String"/>
	<field name="exenta" class="java.lang.Long"/>
	<field name="ivacinco" class="java.lang.Long"/>
	<field name="ivadiez" class="java.lang.Long"/> */

                    Set<FacturaDetalle> listaDetalle = fact.getFacturaDetalles();

                    for (FacturaDetalle detalle : listaDetalle) {
                        item.put("cantidad", detalle.getCantidad());
                        item.put("precio", detalle.getPrecioUnitario().toString());
                        item.put("exenta", 0L);
                        item.put("ivacinco", 0L);
                        item.put("ivadiez", 0L);
                        item.put("descripcion", detalle.getProducto().getDescripcion());
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
