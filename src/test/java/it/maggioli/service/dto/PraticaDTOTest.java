package it.maggioli.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class PraticaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PraticaDTO.class);
        PraticaDTO praticaDTO1 = new PraticaDTO();
        praticaDTO1.setId(1L);
        PraticaDTO praticaDTO2 = new PraticaDTO();
        assertThat(praticaDTO1).isNotEqualTo(praticaDTO2);
        praticaDTO2.setId(praticaDTO1.getId());
        assertThat(praticaDTO1).isEqualTo(praticaDTO2);
        praticaDTO2.setId(2L);
        assertThat(praticaDTO1).isNotEqualTo(praticaDTO2);
        praticaDTO1.setId(null);
        assertThat(praticaDTO1).isNotEqualTo(praticaDTO2);
    }
}
