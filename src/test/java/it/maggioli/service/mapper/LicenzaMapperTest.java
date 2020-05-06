package it.maggioli.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LicenzaMapperTest {

    private LicenzaMapper licenzaMapper;

    @BeforeEach
    public void setUp() {
        licenzaMapper = new LicenzaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(licenzaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(licenzaMapper.fromId(null)).isNull();
    }
}
