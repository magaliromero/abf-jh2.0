package py.com.abf.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link py.com.abf.domain.RegistroStockMateriales} entity. This class is used
 * in {@link py.com.abf.web.rest.RegistroStockMaterialesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /registro-stock-materiales?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistroStockMaterialesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comentario;

    private IntegerFilter cantidadInicial;

    private IntegerFilter cantidadModificada;

    private LongFilter materialesId;

    private Boolean distinct;

    public RegistroStockMaterialesCriteria() {}

    public RegistroStockMaterialesCriteria(RegistroStockMaterialesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.cantidadInicial = other.cantidadInicial == null ? null : other.cantidadInicial.copy();
        this.cantidadModificada = other.cantidadModificada == null ? null : other.cantidadModificada.copy();
        this.materialesId = other.materialesId == null ? null : other.materialesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RegistroStockMaterialesCriteria copy() {
        return new RegistroStockMaterialesCriteria(this);
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

    public StringFilter getComentario() {
        return comentario;
    }

    public StringFilter comentario() {
        if (comentario == null) {
            comentario = new StringFilter();
        }
        return comentario;
    }

    public void setComentario(StringFilter comentario) {
        this.comentario = comentario;
    }

    public IntegerFilter getCantidadInicial() {
        return cantidadInicial;
    }

    public IntegerFilter cantidadInicial() {
        if (cantidadInicial == null) {
            cantidadInicial = new IntegerFilter();
        }
        return cantidadInicial;
    }

    public void setCantidadInicial(IntegerFilter cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public IntegerFilter getCantidadModificada() {
        return cantidadModificada;
    }

    public IntegerFilter cantidadModificada() {
        if (cantidadModificada == null) {
            cantidadModificada = new IntegerFilter();
        }
        return cantidadModificada;
    }

    public void setCantidadModificada(IntegerFilter cantidadModificada) {
        this.cantidadModificada = cantidadModificada;
    }

    public LongFilter getMaterialesId() {
        return materialesId;
    }

    public LongFilter materialesId() {
        if (materialesId == null) {
            materialesId = new LongFilter();
        }
        return materialesId;
    }

    public void setMaterialesId(LongFilter materialesId) {
        this.materialesId = materialesId;
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
        final RegistroStockMaterialesCriteria that = (RegistroStockMaterialesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(cantidadInicial, that.cantidadInicial) &&
            Objects.equals(cantidadModificada, that.cantidadModificada) &&
            Objects.equals(materialesId, that.materialesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comentario, cantidadInicial, cantidadModificada, materialesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroStockMaterialesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comentario != null ? "comentario=" + comentario + ", " : "") +
            (cantidadInicial != null ? "cantidadInicial=" + cantidadInicial + ", " : "") +
            (cantidadModificada != null ? "cantidadModificada=" + cantidadModificada + ", " : "") +
            (materialesId != null ? "materialesId=" + materialesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
