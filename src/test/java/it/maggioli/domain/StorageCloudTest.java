package it.maggioli.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.maggioli.web.rest.TestUtil;

public class StorageCloudTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageCloud.class);
        StorageCloud storageCloud1 = new StorageCloud();
        storageCloud1.setId(1L);
        StorageCloud storageCloud2 = new StorageCloud();
        storageCloud2.setId(storageCloud1.getId());
        assertThat(storageCloud1).isEqualTo(storageCloud2);
        storageCloud2.setId(2L);
        assertThat(storageCloud1).isNotEqualTo(storageCloud2);
        storageCloud1.setId(null);
        assertThat(storageCloud1).isNotEqualTo(storageCloud2);
    }
}
