package py.com.abf.domain.param;

import java.time.LocalDate;

public class BalanceParam {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "BalanceParam{" + "fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + '}';
    }
}
