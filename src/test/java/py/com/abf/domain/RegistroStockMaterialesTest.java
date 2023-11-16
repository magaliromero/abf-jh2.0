package py.com.abf.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import py.com.abf.web.rest.TestUtil;

class RegistroStockMaterialesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroStockMateriales.class);
        RegistroStockMateriales registroStockMateriales1 = new RegistroStockMateriales();
        registroStockMateriales1.setId(1L);
        RegistroStockMateriales registroStockMateriales2 = new RegistroStockMateriales();
        registroStockMateriales2.setId(registroStockMateriales1.getId());
        assertThat(registroStockMateriales1).isEqualTo(registroStockMateriales2);
        registroStockMateriales2.setId(2L);
        assertThat(registroStockMateriales1).isNotEqualTo(registroStockMateriales2);
        registroStockMateriales1.setId(null);
        assertThat(registroStockMateriales1).isNotEqualTo(registroStockMateriales2);
    }
}
