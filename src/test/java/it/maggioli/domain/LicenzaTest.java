package it.maggioli.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class LicenzaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licenza.class);
        Licenza licenza1 = new Licenza();
        licenza1.setId(1L);
        Licenza licenza2 = new Licenza();
        licenza2.setId(licenza1.getId());
        assertThat(licenza1).isEqualTo(licenza2);
        licenza2.setId(2L);
        assertThat(licenza1).isNotEqualTo(licenza2);
        licenza1.setId(null);
        assertThat(licenza1).isNotEqualTo(licenza2);
    }
}
