package it.maggioli.repository;

import it.maggioli.domain.StorageCloud;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StorageCloud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorageCloudRepository extends JpaRepository<StorageCloud, Long> {
}
