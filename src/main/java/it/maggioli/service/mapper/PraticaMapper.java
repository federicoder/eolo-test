package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.PraticaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pratica} and its DTO {@link PraticaDTO}.
 */
@Mapper(componentModel = "spring", uses = {LicenzaMapper.class})
public interface PraticaMapper extends EntityMapper<PraticaDTO, Pratica> {

    @Mapping(source = "licenza.id", target = "licenzaId")
    PraticaDTO toDto(Pratica pratica);

    @Mapping(target = "invitos", ignore = true)
    @Mapping(target = "removeInvito", ignore = true)
    @Mapping(source = "licenzaId", target = "licenza")
    Pratica toEntity(PraticaDTO praticaDTO);

    default Pratica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pratica pratica = new Pratica();
        pratica.setId(id);
        return pratica;
    }
}
