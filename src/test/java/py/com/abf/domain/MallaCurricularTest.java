package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class MallaCurricularTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MallaCurricular.class);
        MallaCurricular mallaCurricular1 = new MallaCurricular();
        mallaCurricular1.setId(1L);
        MallaCurricular mallaCurricular2 = new MallaCurricular();
        mallaCurricular2.setId(mallaCurricular1.getId());
        assertThat(mallaCurricular1).isEqualTo(mallaCurricular2);
        mallaCurricular2.setId(2L);
        assertThat(mallaCurricular1).isNotEqualTo(mallaCurricular2);
        mallaCurricular1.setId(null);
        assertThat(mallaCurricular1).isNotEqualTo(mallaCurricular2);
    }
}
