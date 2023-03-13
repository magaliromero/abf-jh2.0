package py.com.abf.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link py.com.abf.domain.Usuarios} entity. This class is used
 * in {@link py.com.abf.web.rest.UsuariosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuariosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter documento;

    private IntegerFilter idRol;

    private Boolean distinct;

    public UsuariosCriteria() {}

    public UsuariosCriteria(UsuariosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documento = other.documento == null ? null : other.documento.copy();
        this.idRol = other.idRol == null ? null : other.idRol.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UsuariosCriteria copy() {
        return new UsuariosCriteria(this);
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

    public IntegerFilter getDocumento() {
        return documento;
    }

    public IntegerFilter documento() {
        if (documento == null) {
            documento = new IntegerFilter();
        }
        return documento;
    }

    public void setDocumento(IntegerFilter documento) {
        this.documento = documento;
    }

    public IntegerFilter getIdRol() {
        return idRol;
    }

    public IntegerFilter idRol() {
        if (idRol == null) {
            idRol = new IntegerFilter();
        }
        return idRol;
    }

    public void setIdRol(IntegerFilter idRol) {
        this.idRol = idRol;
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
        final UsuariosCriteria that = (UsuariosCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(documento, that.documento) &&
            Objects.equals(idRol, that.idRol) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento, idRol, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuariosCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (documento != null ? "documento=" + documento + ", " : "") +
            (idRol != null ? "idRol=" + idRol + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
