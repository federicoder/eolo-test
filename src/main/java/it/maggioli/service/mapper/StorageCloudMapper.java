package it.maggioli.service.mapper;


import it.maggioli.domain.*;
import it.maggioli.service.dto.StorageCloudDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StorageCloud} and its DTO {@link StorageCloudDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StorageCloudMapper extends EntityMapper<StorageCloudDTO, StorageCloud> {



    default StorageCloud fromId(Long id) {
        if (id == null) {
            return null;
        }
        StorageCloud storageCloud = new StorageCloud();
        storageCloud.setId(id);
        return storageCloud;
    }
}
