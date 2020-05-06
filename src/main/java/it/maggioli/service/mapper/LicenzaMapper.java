package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.LicenzaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Licenza} and its DTO {@link LicenzaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionistaMapper.class, StorageCloudMapper.class})
public interface LicenzaMapper extends EntityMapper<LicenzaDTO, Licenza> {

    @Mapping(source = "idLicenza.id", target = "idLicenzaId")
    @Mapping(source = "idLicenza.id", target = "idLicenzaId")
    LicenzaDTO toDto(Licenza licenza);

    @Mapping(source = "idLicenzaId", target = "idLicenza")
    @Mapping(source = "idLicenzaId", target = "idLicenza")
    @Mapping(target = "idLicenzas", ignore = true)
    @Mapping(target = "removeIdLicenza", ignore = true)
    Licenza toEntity(LicenzaDTO licenzaDTO);

    default Licenza fromId(Long id) {
        if (id == null) {
            return null;
        }
        Licenza licenza = new Licenza();
        licenza.setId(id);
        return licenza;
    }
}
