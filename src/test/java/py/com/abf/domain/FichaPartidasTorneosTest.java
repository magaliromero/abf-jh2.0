package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class FichaPartidasTorneosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaPartidasTorneos.class);
        FichaPartidasTorneos fichaPartidasTorneos1 = new FichaPartidasTorneos();
        fichaPartidasTorneos1.setId(1L);
        FichaPartidasTorneos fichaPartidasTorneos2 = new FichaPartidasTorneos();
        fichaPartidasTorneos2.setId(fichaPartidasTorneos1.getId());
        assertThat(fichaPartidasTorneos1).isEqualTo(fichaPartidasTorneos2);
        fichaPartidasTorneos2.setId(2L);
        assertThat(fichaPartidasTorneos1).isNotEqualTo(fichaPartidasTorneos2);
        fichaPartidasTorneos1.setId(null);
        assertThat(fichaPartidasTorneos1).isNotEqualTo(fichaPartidasTorneos2);
    }
}
