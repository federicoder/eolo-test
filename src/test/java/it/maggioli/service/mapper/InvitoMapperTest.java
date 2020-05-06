package it.maggioli.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InvitoMapperTest {

    private InvitoMapper invitoMapper;

    @BeforeEach
    public void setUp() {
        invitoMapper = new InvitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(invitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(invitoMapper.fromId(null)).isNull();
    }
}
