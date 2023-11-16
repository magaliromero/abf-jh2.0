package py.com.abf.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link py.com.abf.domain.NotaCreditoDetalle} entity. This class is used
 * in {@link py.com.abf.web.rest.NotaCreditoDetalleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nota-credito-detalles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotaCreditoDetalleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter cantidad;

    private IntegerFilter precioUnitario;

    private IntegerFilter subtotal;

    private IntegerFilter porcentajeIva;

    private IntegerFilter valorPorcentaje;

    private LongFilter notaCreditoId;

    private LongFilter productoId;

    private Boolean distinct;

    public NotaCreditoDetalleCriteria() {}

    public NotaCreditoDetalleCriteria(NotaCreditoDetalleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cantidad = other.cantidad == null ? null : other.cantidad.copy();
        this.precioUnitario = other.precioUnitario == null ? null : other.precioUnitario.copy();
        this.subtotal = other.subtotal == null ? null : other.subtotal.copy();
        this.porcentajeIva = other.porcentajeIva == null ? null : other.porcentajeIva.copy();
        this.valorPorcentaje = other.valorPorcentaje == null ? null : other.valorPorcentaje.copy();
        this.notaCreditoId = other.notaCreditoId == null ? null : other.notaCreditoId.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotaCreditoDetalleCriteria copy() {
        return new NotaCreditoDetalleCriteria(this);
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

    public IntegerFilter getCantidad() {
        return cantidad;
    }

    public IntegerFilter cantidad() {
        if (cantidad == null) {
            cantidad = new IntegerFilter();
        }
        return cantidad;
    }

    public void setCantidad(IntegerFilter cantidad) {
        this.cantidad = cantidad;
    }

    public IntegerFilter getPrecioUnitario() {
        return precioUnitario;
    }

    public IntegerFilter precioUnitario() {
        if (precioUnitario == null) {
            precioUnitario = new IntegerFilter();
        }
        return precioUnitario;
    }

    public void setPrecioUnitario(IntegerFilter precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public IntegerFilter getSubtotal() {
        return subtotal;
    }

    public IntegerFilter subtotal() {
        if (subtotal == null) {
            subtotal = new IntegerFilter();
        }
        return subtotal;
    }

    public void setSubtotal(IntegerFilter subtotal) {
        this.subtotal = subtotal;
    }

    public IntegerFilter getPorcentajeIva() {
        return porcentajeIva;
    }

    public IntegerFilter porcentajeIva() {
        if (porcentajeIva == null) {
            porcentajeIva = new IntegerFilter();
        }
        return porcentajeIva;
    }

    public void setPorcentajeIva(IntegerFilter porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public IntegerFilter getValorPorcentaje() {
        return valorPorcentaje;
    }

    public IntegerFilter valorPorcentaje() {
        if (valorPorcentaje == null) {
            valorPorcentaje = new IntegerFilter();
        }
        return valorPorcentaje;
    }

    public void setValorPorcentaje(IntegerFilter valorPorcentaje) {
        this.valorPorcentaje = valorPorcentaje;
    }

    public LongFilter getNotaCreditoId() {
        return notaCreditoId;
    }

    public LongFilter notaCreditoId() {
        if (notaCreditoId == null) {
            notaCreditoId = new LongFilter();
        }
        return notaCreditoId;
    }

    public void setNotaCreditoId(LongFilter notaCreditoId) {
        this.notaCreditoId = notaCreditoId;
    }

    public LongFilter getProductoId() {
        return productoId;
    }

    public LongFilter productoId() {
        if (productoId == null) {
            productoId = new LongFilter();
        }
        return productoId;
    }

    public void setProductoId(LongFilter productoId) {
        this.productoId = productoId;
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
        final NotaCreditoDetalleCriteria that = (NotaCreditoDetalleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cantidad, that.cantidad) &&
            Objects.equals(precioUnitario, that.precioUnitario) &&
            Objects.equals(subtotal, that.subtotal) &&
            Objects.equals(porcentajeIva, that.porcentajeIva) &&
            Objects.equals(valorPorcentaje, that.valorPorcentaje) &&
            Objects.equals(notaCreditoId, that.notaCreditoId) &&
            Objects.equals(productoId, that.productoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidad, precioUnitario, subtotal, porcentajeIva, valorPorcentaje, notaCreditoId, productoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotaCreditoDetalleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cantidad != null ? "cantidad=" + cantidad + ", " : "") +
            (precioUnitario != null ? "precioUnitario=" + precioUnitario + ", " : "") +
            (subtotal != null ? "subtotal=" + subtotal + ", " : "") +
            (porcentajeIva != null ? "porcentajeIva=" + porcentajeIva + ", " : "") +
            (valorPorcentaje != null ? "valorPorcentaje=" + valorPorcentaje + ", " : "") +
            (notaCreditoId != null ? "notaCreditoId=" + notaCreditoId + ", " : "") +
            (productoId != null ? "productoId=" + productoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
