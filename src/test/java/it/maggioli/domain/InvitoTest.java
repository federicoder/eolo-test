package it.maggioli.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class InvitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invito.class);
        Invito invito1 = new Invito();
        invito1.setId(1L);
        Invito invito2 = new Invito();
        invito2.setId(invito1.getId());
        assertThat(invito1).isEqualTo(invito2);
        invito2.setId(2L);
        assertThat(invito1).isNotEqualTo(invito2);
        invito1.setId(null);
        assertThat(invito1).isNotEqualTo(invito2);
    }
}
