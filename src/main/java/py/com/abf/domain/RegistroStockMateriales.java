package py.com.abf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RegistroStockMateriales.
 */
@Entity
@Table(name = "registro_materiales")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistroStockMateriales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "cantidad_inicial")
    private Integer cantidadInicial;

    @Column(name = "cantidad_modificada")
    private Integer cantidadModificada;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties(value = { "registroStockMateriales", "prestamos" }, allowSetters = true)
    private Materiales materiales;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegistroStockMateriales id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return this.comentario;
    }

    public RegistroStockMateriales comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCantidadInicial() {
        return this.cantidadInicial;
    }

    public RegistroStockMateriales cantidadInicial(Integer cantidadInicial) {
        this.setCantidadInicial(cantidadInicial);
        return this;
    }

    public void setCantidadInicial(Integer cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public Integer getCantidadModificada() {
        return this.cantidadModificada;
    }

    public RegistroStockMateriales cantidadModificada(Integer cantidadModificada) {
        this.setCantidadModificada(cantidadModificada);
        return this;
    }

    public void setCantidadModificada(Integer cantidadModificada) {
        this.cantidadModificada = cantidadModificada;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public RegistroStockMateriales fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Materiales getMateriales() {
        return this.materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public RegistroStockMateriales materiales(Materiales materiales) {
        this.setMateriales(materiales);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroStockMateriales)) {
            return false;
        }
        return id != null && id.equals(((RegistroStockMateriales) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroStockMateriales{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", cantidadInicial=" + getCantidadInicial() +
            ", cantidadModificada=" + getCantidadModificada() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
