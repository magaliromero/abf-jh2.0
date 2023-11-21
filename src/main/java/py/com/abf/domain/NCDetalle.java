package py.com.abf.domain;

import java.util.List;

public class NCDetalle {

    public NotaCredito cabecera;
    public List<NCDetalleItem> detalle;

    public NotaCredito getCabecera() {
        return cabecera;
    }

    public void setCabecera(NotaCredito cabecera) {
        this.cabecera = cabecera;
    }

    public List<NCDetalleItem> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<NCDetalleItem> detalle) {
        this.detalle = detalle;
    }
}
