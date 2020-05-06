package it.maggioli.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StorageCloudMapperTest {

    private StorageCloudMapper storageCloudMapper;

    @BeforeEach
    public void setUp() {
        storageCloudMapper = new StorageCloudMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(storageCloudMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(storageCloudMapper.fromId(null)).isNull();
    }
}
