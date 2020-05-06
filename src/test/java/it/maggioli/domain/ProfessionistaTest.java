package it.maggioli.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class ProfessionistaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Professionista.class);
        Professionista professionista1 = new Professionista();
        professionista1.setId(1L);
        Professionista professionista2 = new Professionista();
        professionista2.setId(professionista1.getId());
        assertThat(professionista1).isEqualTo(professionista2);
        professionista2.setId(2L);
        assertThat(professionista1).isNotEqualTo(professionista2);
        professionista1.setId(null);
        assertThat(professionista1).isNotEqualTo(professionista2);
    }
}
