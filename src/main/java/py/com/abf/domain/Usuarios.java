package py.com.abf.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Usuarios.
 */
@Entity
@Table(name = "usuarios")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "documento", nullable = false)
    private Integer documento;

    @NotNull
    @Column(name = "id_rol", nullable = false)
    private Integer idRol;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuarios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDocumento() {
        return this.documento;
    }

    public Usuarios documento(Integer documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getIdRol() {
        return this.idRol;
    }

    public Usuarios idRol(Integer idRol) {
        this.setIdRol(idRol);
        return this;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuarios)) {
            return false;
        }
        return id != null && id.equals(((Usuarios) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuarios{" +
            "id=" + getId() +
            ", documento=" + getDocumento() +
            ", idRol=" + getIdRol() +
            "}";
    }
}
