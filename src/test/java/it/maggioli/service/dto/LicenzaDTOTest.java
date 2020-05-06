package it.maggioli.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class LicenzaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenzaDTO.class);
        LicenzaDTO licenzaDTO1 = new LicenzaDTO();
        licenzaDTO1.setId(1L);
        LicenzaDTO licenzaDTO2 = new LicenzaDTO();
        assertThat(licenzaDTO1).isNotEqualTo(licenzaDTO2);
        licenzaDTO2.setId(licenzaDTO1.getId());
        assertThat(licenzaDTO1).isEqualTo(licenzaDTO2);
        licenzaDTO2.setId(2L);
        assertThat(licenzaDTO1).isNotEqualTo(licenzaDTO2);
        licenzaDTO1.setId(null);
        assertThat(licenzaDTO1).isNotEqualTo(licenzaDTO2);
    }
}
