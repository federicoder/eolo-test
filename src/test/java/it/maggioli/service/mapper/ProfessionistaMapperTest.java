package it.maggioli.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProfessionistaMapperTest {

    private ProfessionistaMapper professionistaMapper;

    @BeforeEach
    public void setUp() {
        professionistaMapper = new ProfessionistaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(professionistaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(professionistaMapper.fromId(null)).isNull();
    }
}
