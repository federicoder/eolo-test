package it.maggioli.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class ProfessionistaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionistaDTO.class);
        ProfessionistaDTO professionistaDTO1 = new ProfessionistaDTO();
        professionistaDTO1.setId(1L);
        ProfessionistaDTO professionistaDTO2 = new ProfessionistaDTO();
        assertThat(professionistaDTO1).isNotEqualTo(professionistaDTO2);
        professionistaDTO2.setId(professionistaDTO1.getId());
        assertThat(professionistaDTO1).isEqualTo(professionistaDTO2);
        professionistaDTO2.setId(2L);
        assertThat(professionistaDTO1).isNotEqualTo(professionistaDTO2);
        professionistaDTO1.setId(null);
        assertThat(professionistaDTO1).isNotEqualTo(professionistaDTO2);
    }
}
