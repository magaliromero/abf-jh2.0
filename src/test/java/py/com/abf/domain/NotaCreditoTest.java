package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class NotaCreditoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotaCredito.class);
        NotaCredito notaCredito1 = new NotaCredito();
        notaCredito1.setId(1L);
        NotaCredito notaCredito2 = new NotaCredito();
        notaCredito2.setId(notaCredito1.getId());
        assertThat(notaCredito1).isEqualTo(notaCredito2);
        notaCredito2.setId(2L);
        assertThat(notaCredito1).isNotEqualTo(notaCredito2);
        notaCredito1.setId(null);
        assertThat(notaCredito1).isNotEqualTo(notaCredito2);
    }
}
