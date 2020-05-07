package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.CollaboratoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Collaboratore} and its DTO {@link CollaboratoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CollaboratoreMapper extends EntityMapper<CollaboratoreDTO, Collaboratore> {


    @Mapping(target = "invito", ignore = true)
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
