package py.com.abf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Timbrados.
 */
@Entity
@Table(name = "timbrado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Timbrados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero_timbrado", nullable = false, unique = true)
    private Integer numeroTimbrado;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "timbrados")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "puntoDeExpedicions", "timbrados" }, allowSetters = true)
    private Set<Sucursales> sucursales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Timbrados id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroTimbrado() {
        return this.numeroTimbrado;
    }

    public Timbrados numeroTimbrado(Integer numeroTimbrado) {
        this.setNumeroTimbrado(numeroTimbrado);
        return this;
    }

    public void setNumeroTimbrado(Integer numeroTimbrado) {
        this.numeroTimbrado = numeroTimbrado;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Timbrados fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public Timbrados fechaFin(LocalDate fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<Sucursales> getSucursales() {
        return this.sucursales;
    }

    public void setSucursales(Set<Sucursales> sucursales) {
        if (this.sucursales != null) {
            this.sucursales.forEach(i -> i.setTimbrados(null));
        }
        if (sucursales != null) {
            sucursales.forEach(i -> i.setTimbrados(this));
        }
        this.sucursales = sucursales;
    }

    public Timbrados sucursales(Set<Sucursales> sucursales) {
        this.setSucursales(sucursales);
        return this;
    }

    public Timbrados addSucursales(Sucursales sucursales) {
        this.sucursales.add(sucursales);
        sucursales.setTimbrados(this);
        return this;
    }

    public Timbrados removeSucursales(Sucursales sucursales) {
        this.sucursales.remove(sucursales);
        sucursales.setTimbrados(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timbrados)) {
            return false;
        }
        return id != null && id.equals(((Timbrados) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Timbrados{" +
            "id=" + getId() +
            ", numeroTimbrado=" + getNumeroTimbrado() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
