package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class TorneosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Torneos.class);
        Torneos torneos1 = new Torneos();
        torneos1.setId(1L);
        Torneos torneos2 = new Torneos();
        torneos2.setId(torneos1.getId());
        assertThat(torneos1).isEqualTo(torneos2);
        torneos2.setId(2L);
        assertThat(torneos1).isNotEqualTo(torneos2);
        torneos1.setId(null);
        assertThat(torneos1).isNotEqualTo(torneos2);
    }
}
