package it.maggioli.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class CollaboratoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollaboratoreDTO.class);
        CollaboratoreDTO collaboratoreDTO1 = new CollaboratoreDTO();
        collaboratoreDTO1.setId(1L);
        CollaboratoreDTO collaboratoreDTO2 = new CollaboratoreDTO();
        assertThat(collaboratoreDTO1).isNotEqualTo(collaboratoreDTO2);
        collaboratoreDTO2.setId(collaboratoreDTO1.getId());
        assertThat(collaboratoreDTO1).isEqualTo(collaboratoreDTO2);
        collaboratoreDTO2.setId(2L);
        assertThat(collaboratoreDTO1).isNotEqualTo(collaboratoreDTO2);
        collaboratoreDTO1.setId(null);
        assertThat(collaboratoreDTO1).isNotEqualTo(collaboratoreDTO2);
    }
}
