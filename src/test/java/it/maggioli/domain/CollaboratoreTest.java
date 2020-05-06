package it.maggioli.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class CollaboratoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaboratore.class);
        Collaboratore collaboratore1 = new Collaboratore();
        collaboratore1.setId(1L);
        Collaboratore collaboratore2 = new Collaboratore();
        collaboratore2.setId(collaboratore1.getId());
        assertThat(collaboratore1).isEqualTo(collaboratore2);
        collaboratore2.setId(2L);
        assertThat(collaboratore1).isNotEqualTo(collaboratore2);
        collaboratore1.setId(null);
        assertThat(collaboratore1).isNotEqualTo(collaboratore2);
    }
}
