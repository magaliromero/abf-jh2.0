package py.com.abf.domain.enumeration;

/**
 * The Motivo enumeration.
 */
public enum Motivo {
    ANULACION("Anulacion"),
    DEVOLUCION("Devolucion"),
    DESCUENTO("Descuento"),
    OTRO("Otro");

    private final String value;

    Motivo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
