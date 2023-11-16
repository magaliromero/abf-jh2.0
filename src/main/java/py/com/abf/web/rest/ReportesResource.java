package py.com.abf.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import py.com.abf.service.ReporteService;

@RestController
@RequestMapping(value = "/reportes/")
@CrossOrigin(origins = "*")
public class ReportesResource {

    @Autowired
    private ReporteService report;

    @GetMapping("{facturaId}")
    public void reporteMultiParam(HttpServletResponse response, @PathVariable("facturaId") Long facturaId) {
        try {
            System.out.println("DATA ");
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");

            OutputStream out = response.getOutputStream();

            HashMap<String, Object> param = new HashMap<>();
            // param.put("clienteId", 1L);
            param.put("facturaId", facturaId);

            report.exportReport(out, "factura", "pdf", param);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
