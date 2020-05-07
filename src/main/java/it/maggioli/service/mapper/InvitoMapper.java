package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.InvitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invito} and its DTO {@link InvitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CollaboratoreMapper.class, ClienteMapper.class, PraticaMapper.class, ProfessionistaMapper.class})
public interface InvitoMapper extends EntityMapper<InvitoDTO, Invito> {

    @Mapping(source = "collaboratore.id", target = "collaboratoreId")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "pratica.id", target = "praticaId")
    @Mapping(source = "professionista.id", target = "professionistaId")
    InvitoDTO toDto(Invito invito);

    @Mapping(source = "collaboratoreId", target = "collaboratore")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(target = "collaboratores", ignore = true)
    @Mapping(target = "removeCollaboratore", ignore = true)
    @Mapping(source = "praticaId", target = "pratica")
    @Mapping(source = "professionistaId", target = "professionista")
    @Mapping(target = "idUtentes", ignore = true)
    @Mapping(target = "removeIdUtente", ignore = true)
    @Mapping(target = "idPraticas", ignore = true)
    @Mapping(target = "removeIdPratica", ignore = true)
    Invito toEntity(InvitoDTO invitoDTO);

    default Invito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Invito invito = new Invito();
        invito.setId(id);
        return invito;
    }
}
