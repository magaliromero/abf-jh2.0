package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class NotaCreditoDetalleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotaCreditoDetalle.class);
        NotaCreditoDetalle notaCreditoDetalle1 = new NotaCreditoDetalle();
        notaCreditoDetalle1.setId(1L);
        NotaCreditoDetalle notaCreditoDetalle2 = new NotaCreditoDetalle();
        notaCreditoDetalle2.setId(notaCreditoDetalle1.getId());
        assertThat(notaCreditoDetalle1).isEqualTo(notaCreditoDetalle2);
        notaCreditoDetalle2.setId(2L);
        assertThat(notaCreditoDetalle1).isNotEqualTo(notaCreditoDetalle2);
        notaCreditoDetalle1.setId(null);
        assertThat(notaCreditoDetalle1).isNotEqualTo(notaCreditoDetalle2);
    }
}
