package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.InvitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invito} and its DTO {@link InvitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CollaboratoreMapper.class, ClienteMapper.class, PraticaMapper.class, ProfessionistaMapper.class})
public interface InvitoMapper extends EntityMapper<InvitoDTO, Invito> {

    @Mapping(source = "idUtente.id", target = "idUtenteId")
    @Mapping(source = "idUtente.id", target = "idUtenteId")
    @Mapping(source = "idPratica.id", target = "idPraticaId")
    @Mapping(source = "idUtente.id", target = "idUtenteId")
    InvitoDTO toDto(Invito invito);

    @Mapping(source = "idUtenteId", target = "idUtente")
    @Mapping(source = "idUtenteId", target = "idUtente")
    @Mapping(target = "idInvitos", ignore = true)
    @Mapping(target = "removeIdInvito", ignore = true)
    @Mapping(source = "idPraticaId", target = "idPratica")
    @Mapping(source = "idUtenteId", target = "idUtente")
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
