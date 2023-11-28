package py.com.abf.domain.param;

import java.util.List;
import py.com.abf.domain.Facturas;
import py.com.abf.domain.NotaCredito;

public class BalanceResult {

    List<Facturas> listaFactura;
    List<NotaCredito> listaNC;

    public List<Facturas> getListaFactura() {
        return listaFactura;
    }

    public void setListaFactura(List<Facturas> listaFactura) {
        this.listaFactura = listaFactura;
    }

    public List<NotaCredito> getListaNC() {
        return listaNC;
    }

    public void setListaNC(List<NotaCredito> listaNC) {
        this.listaNC = listaNC;
    }
}
