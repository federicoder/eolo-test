package it.maggioli.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PraticaMapperTest {

    private PraticaMapper praticaMapper;

    @BeforeEach
    public void setUp() {
        praticaMapper = new PraticaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(praticaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(praticaMapper.fromId(null)).isNull();
    }
}
