package it.maggioli.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class InvitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitoDTO.class);
        InvitoDTO invitoDTO1 = new InvitoDTO();
        invitoDTO1.setId(1L);
        InvitoDTO invitoDTO2 = new InvitoDTO();
        assertThat(invitoDTO1).isNotEqualTo(invitoDTO2);
        invitoDTO2.setId(invitoDTO1.getId());
        assertThat(invitoDTO1).isEqualTo(invitoDTO2);
        invitoDTO2.setId(2L);
        assertThat(invitoDTO1).isNotEqualTo(invitoDTO2);
        invitoDTO1.setId(null);
        assertThat(invitoDTO1).isNotEqualTo(invitoDTO2);
    }
}
