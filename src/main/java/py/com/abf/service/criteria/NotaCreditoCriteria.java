package py.com.abf.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import py.com.abf.domain.enumeration.Motivo;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link py.com.abf.domain.NotaCredito} entity. This class is used
 * in {@link py.com.abf.web.rest.NotaCreditoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nota-creditos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotaCreditoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Motivo
     */
    public static class MotivoFilter extends Filter<Motivo> {

        public MotivoFilter() {}

        public MotivoFilter(MotivoFilter filter) {
            super(filter);
        }

        @Override
        public MotivoFilter copy() {
            return new MotivoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter fecha;

    private StringFilter notaNro;

    private IntegerFilter puntoExpedicion;

    private IntegerFilter sucursal;

    private StringFilter razonSocial;

    private StringFilter ruc;

    private StringFilter direccion;

    private MotivoFilter motivoEmision;

    private IntegerFilter total;

    private LongFilter facturasId;

    private LongFilter notaCreditoDetalleId;

    private Boolean distinct;

    public NotaCreditoCriteria() {}

    public NotaCreditoCriteria(NotaCreditoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.notaNro = other.notaNro == null ? null : other.notaNro.copy();
        this.puntoExpedicion = other.puntoExpedicion == null ? null : other.puntoExpedicion.copy();
        this.sucursal = other.sucursal == null ? null : other.sucursal.copy();
        this.razonSocial = other.razonSocial == null ? null : other.razonSocial.copy();
        this.ruc = other.ruc == null ? null : other.ruc.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.motivoEmision = other.motivoEmision == null ? null : other.motivoEmision.copy();
        this.total = other.total == null ? null : other.total.copy();
        this.facturasId = other.facturasId == null ? null : other.facturasId.copy();
        this.notaCreditoDetalleId = other.notaCreditoDetalleId == null ? null : other.notaCreditoDetalleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotaCreditoCriteria copy() {
        return new NotaCreditoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFecha() {
        return fecha;
    }

    public LocalDateFilter fecha() {
        if (fecha == null) {
            fecha = new LocalDateFilter();
        }
        return fecha;
    }

    public void setFecha(LocalDateFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getNotaNro() {
        return notaNro;
    }

    public StringFilter notaNro() {
        if (notaNro == null) {
            notaNro = new StringFilter();
        }
        return notaNro;
    }

    public void setNotaNro(StringFilter notaNro) {
        this.notaNro = notaNro;
    }

    public IntegerFilter getPuntoExpedicion() {
        return puntoExpedicion;
    }

    public IntegerFilter puntoExpedicion() {
        if (puntoExpedicion == null) {
            puntoExpedicion = new IntegerFilter();
        }
        return puntoExpedicion;
    }

    public void setPuntoExpedicion(IntegerFilter puntoExpedicion) {
        this.puntoExpedicion = puntoExpedicion;
    }

    public IntegerFilter getSucursal() {
        return sucursal;
    }

    public IntegerFilter sucursal() {
        if (sucursal == null) {
            sucursal = new IntegerFilter();
        }
        return sucursal;
    }

    public void setSucursal(IntegerFilter sucursal) {
        this.sucursal = sucursal;
    }

    public StringFilter getRazonSocial() {
        return razonSocial;
    }

    public StringFilter razonSocial() {
        if (razonSocial == null) {
            razonSocial = new StringFilter();
        }
        return razonSocial;
    }

    public void setRazonSocial(StringFilter razonSocial) {
        this.razonSocial = razonSocial;
    }

    public StringFilter getRuc() {
        return ruc;
    }

    public StringFilter ruc() {
        if (ruc == null) {
            ruc = new StringFilter();
        }
        return ruc;
    }

    public void setRuc(StringFilter ruc) {
        this.ruc = ruc;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public StringFilter direccion() {
        if (direccion == null) {
            direccion = new StringFilter();
        }
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public MotivoFilter getMotivoEmision() {
        return motivoEmision;
    }

    public MotivoFilter motivoEmision() {
        if (motivoEmision == null) {
            motivoEmision = new MotivoFilter();
        }
        return motivoEmision;
    }

    public void setMotivoEmision(MotivoFilter motivoEmision) {
        this.motivoEmision = motivoEmision;
    }

    public IntegerFilter getTotal() {
        return total;
    }

    public IntegerFilter total() {
        if (total == null) {
            total = new IntegerFilter();
        }
        return total;
    }

    public void setTotal(IntegerFilter total) {
        this.total = total;
    }

    public LongFilter getFacturasId() {
        return facturasId;
    }

    public LongFilter facturasId() {
        if (facturasId == null) {
            facturasId = new LongFilter();
        }
        return facturasId;
    }

    public void setFacturasId(LongFilter facturasId) {
        this.facturasId = facturasId;
    }

    public LongFilter getNotaCreditoDetalleId() {
        return notaCreditoDetalleId;
    }

    public LongFilter notaCreditoDetalleId() {
        if (notaCreditoDetalleId == null) {
            notaCreditoDetalleId = new LongFilter();
        }
        return notaCreditoDetalleId;
    }

    public void setNotaCreditoDetalleId(LongFilter notaCreditoDetalleId) {
        this.notaCreditoDetalleId = notaCreditoDetalleId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotaCreditoCriteria that = (NotaCreditoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(notaNro, that.notaNro) &&
            Objects.equals(puntoExpedicion, that.puntoExpedicion) &&
            Objects.equals(sucursal, that.sucursal) &&
            Objects.equals(razonSocial, that.razonSocial) &&
            Objects.equals(ruc, that.ruc) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(motivoEmision, that.motivoEmision) &&
            Objects.equals(total, that.total) &&
            Objects.equals(facturasId, that.facturasId) &&
            Objects.equals(notaCreditoDetalleId, that.notaCreditoDetalleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fecha,
            notaNro,
            puntoExpedicion,
            sucursal,
            razonSocial,
            ruc,
            direccion,
            motivoEmision,
            total,
            facturasId,
            notaCreditoDetalleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotaCreditoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fecha != null ? "fecha=" + fecha + ", " : "") +
            (notaNro != null ? "notaNro=" + notaNro + ", " : "") +
            (puntoExpedicion != null ? "puntoExpedicion=" + puntoExpedicion + ", " : "") +
            (sucursal != null ? "sucursal=" + sucursal + ", " : "") +
            (razonSocial != null ? "razonSocial=" + razonSocial + ", " : "") +
            (ruc != null ? "ruc=" + ruc + ", " : "") +
            (direccion != null ? "direccion=" + direccion + ", " : "") +
            (motivoEmision != null ? "motivoEmision=" + motivoEmision + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (facturasId != null ? "facturasId=" + facturasId + ", " : "") +
            (notaCreditoDetalleId != null ? "notaCreditoDetalleId=" + notaCreditoDetalleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
