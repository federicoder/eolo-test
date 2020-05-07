package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.InvitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invito} and its DTO {@link InvitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CollaboratoreMapper.class, ClienteMapper.class})
public interface InvitoMapper extends EntityMapper<InvitoDTO, Invito> {

    @Mapping(source = "collaboratore.id", target = "collaboratoreId")
    @Mapping(source = "cliente.id", target = "clienteId")
    InvitoDTO toDto(Invito invito);

    @Mapping(source = "collaboratoreId", target = "collaboratore")
    @Mapping(source = "clienteId", target = "cliente")
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
