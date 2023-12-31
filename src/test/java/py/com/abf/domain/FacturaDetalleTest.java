package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class FacturaDetalleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaDetalle.class);
        FacturaDetalle facturaDetalle1 = new FacturaDetalle();
        facturaDetalle1.setId(1L);
        FacturaDetalle facturaDetalle2 = new FacturaDetalle();
        facturaDetalle2.setId(facturaDetalle1.getId());
        assertThat(facturaDetalle1).isEqualTo(facturaDetalle2);
        facturaDetalle2.setId(2L);
        assertThat(facturaDetalle1).isNotEqualTo(facturaDetalle2);
        facturaDetalle1.setId(null);
        assertThat(facturaDetalle1).isNotEqualTo(facturaDetalle2);
    }
}
