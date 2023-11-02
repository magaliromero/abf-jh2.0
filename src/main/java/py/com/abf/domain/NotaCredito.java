package py.com.abf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import py.com.abf.domain.enumeration.Motivo;

/**
 * A NotaCredito.
 */
@Entity
@Table(name = "nota_credito")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotaCredito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "nota_nro", nullable = false)
    private String notaNro;

    @NotNull
    @Column(name = "punto_expedicion", nullable = false)
    private Integer puntoExpedicion;

    @NotNull
    @Column(name = "sucursal", nullable = false)
    private Integer sucursal;

    @NotNull
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @NotNull
    @Column(name = "ruc", nullable = false)
    private String ruc;

    @Column(name = "direccion")
    private String direccion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_emision", nullable = false)
    private Motivo motivoEmision;

    @NotNull
    @Column(name = "total", nullable = false)
    private Integer total;

    @JsonIgnoreProperties(value = { "facturaDetalles", "notaCredito" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Facturas facturas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notaCredito")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notaCredito", "producto" }, allowSetters = true)
    private Set<NotaCreditoDetalle> notaCreditoDetalles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NotaCredito id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public NotaCredito fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNotaNro() {
        return this.notaNro;
    }

    public NotaCredito notaNro(String notaNro) {
        this.setNotaNro(notaNro);
        return this;
    }

    public void setNotaNro(String notaNro) {
        this.notaNro = notaNro;
    }

    public Integer getPuntoExpedicion() {
        return this.puntoExpedicion;
    }

    public NotaCredito puntoExpedicion(Integer puntoExpedicion) {
        this.setPuntoExpedicion(puntoExpedicion);
        return this;
    }

    public void setPuntoExpedicion(Integer puntoExpedicion) {
        this.puntoExpedicion = puntoExpedicion;
    }

    public Integer getSucursal() {
        return this.sucursal;
    }

    public NotaCredito sucursal(Integer sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    public void setSucursal(Integer sucursal) {
        this.sucursal = sucursal;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public NotaCredito razonSocial(String razonSocial) {
        this.setRazonSocial(razonSocial);
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return this.ruc;
    }

    public NotaCredito ruc(String ruc) {
        this.setRuc(ruc);
        return this;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public NotaCredito direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Motivo getMotivoEmision() {
        return this.motivoEmision;
    }

    public NotaCredito motivoEmision(Motivo motivoEmision) {
        this.setMotivoEmision(motivoEmision);
        return this;
    }

    public void setMotivoEmision(Motivo motivoEmision) {
        this.motivoEmision = motivoEmision;
    }

    public Integer getTotal() {
        return this.total;
    }

    public NotaCredito total(Integer total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Facturas getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Facturas facturas) {
        this.facturas = facturas;
    }

    public NotaCredito facturas(Facturas facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Set<NotaCreditoDetalle> getNotaCreditoDetalles() {
        return this.notaCreditoDetalles;
    }

    public void setNotaCreditoDetalles(Set<NotaCreditoDetalle> notaCreditoDetalles) {
        if (this.notaCreditoDetalles != null) {
            this.notaCreditoDetalles.forEach(i -> i.setNotaCredito(null));
        }
        if (notaCreditoDetalles != null) {
            notaCreditoDetalles.forEach(i -> i.setNotaCredito(this));
        }
        this.notaCreditoDetalles = notaCreditoDetalles;
    }

    public NotaCredito notaCreditoDetalles(Set<NotaCreditoDetalle> notaCreditoDetalles) {
        this.setNotaCreditoDetalles(notaCreditoDetalles);
        return this;
    }

    public NotaCredito addNotaCreditoDetalle(NotaCreditoDetalle notaCreditoDetalle) {
        this.notaCreditoDetalles.add(notaCreditoDetalle);
        notaCreditoDetalle.setNotaCredito(this);
        return this;
    }

    public NotaCredito removeNotaCreditoDetalle(NotaCreditoDetalle notaCreditoDetalle) {
        this.notaCreditoDetalles.remove(notaCreditoDetalle);
        notaCreditoDetalle.setNotaCredito(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotaCredito)) {
            return false;
        }
        return getId() != null && getId().equals(((NotaCredito) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotaCredito{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", notaNro='" + getNotaNro() + "'" +
            ", puntoExpedicion=" + getPuntoExpedicion() +
            ", sucursal=" + getSucursal() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", ruc='" + getRuc() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", motivoEmision='" + getMotivoEmision() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
