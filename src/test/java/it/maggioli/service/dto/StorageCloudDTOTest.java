package it.maggioli.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class StorageCloudDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageCloudDTO.class);
        StorageCloudDTO storageCloudDTO1 = new StorageCloudDTO();
        storageCloudDTO1.setId(1L);
        StorageCloudDTO storageCloudDTO2 = new StorageCloudDTO();
        assertThat(storageCloudDTO1).isNotEqualTo(storageCloudDTO2);
        storageCloudDTO2.setId(storageCloudDTO1.getId());
        assertThat(storageCloudDTO1).isEqualTo(storageCloudDTO2);
        storageCloudDTO2.setId(2L);
        assertThat(storageCloudDTO1).isNotEqualTo(storageCloudDTO2);
        storageCloudDTO1.setId(null);
        assertThat(storageCloudDTO1).isNotEqualTo(storageCloudDTO2);
    }
}
