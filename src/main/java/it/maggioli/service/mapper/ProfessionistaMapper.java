package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.ProfessionistaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professionista} and its DTO {@link ProfessionistaDTO}.
 */
@Mapper(componentModel = "spring", uses = {StorageCloudMapper.class})
public interface ProfessionistaMapper extends EntityMapper<ProfessionistaDTO, Professionista> {

    @Mapping(source = "idProfessionista.id", target = "idProfessionistaId")
    ProfessionistaDTO toDto(Professionista professionista);

    @Mapping(source = "idProfessionistaId", target = "idProfessionista")
    @Mapping(target = "idProfessionistas", ignore = true)
    @Mapping(target = "removeIdProfessionista", ignore = true)
    Professionista toEntity(ProfessionistaDTO professionistaDTO);

    default Professionista fromId(Long id) {
        if (id == null) {
            return null;
        }
        Professionista professionista = new Professionista();
        professionista.setId(id);
        return professionista;
    }
}
