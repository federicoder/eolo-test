package it.maggioli.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CollaboratoreMapperTest {

    private CollaboratoreMapper collaboratoreMapper;

    @BeforeEach
    public void setUp() {
        collaboratoreMapper = new CollaboratoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(collaboratoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(collaboratoreMapper.fromId(null)).isNull();
    }
}
