package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.CollaboratoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collaboratore} and its DTO {@link CollaboratoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {InvitoMapper.class})
public interface CollaboratoreMapper extends EntityMapper<CollaboratoreDTO, Collaboratore> {

    @Mapping(source = "idInvito.id", target = "idInvitoId")
    CollaboratoreDTO toDto(Collaboratore collaboratore);

    @Mapping(target = "removeIdCollaboratore", ignore = true)
    @Mapping(target = "idCollaboratore", ignore = true)
    @Mapping(source = "idInvitoId", target = "idInvito")
    Collaboratore toEntity(CollaboratoreDTO collaboratoreDTO);

    default Collaboratore fromId(Long id) {
        if (id == null) {
            return null;
        }
        Collaboratore collaboratore = new Collaboratore();
        collaboratore.setId(id);
        return collaboratore;
    }
}
