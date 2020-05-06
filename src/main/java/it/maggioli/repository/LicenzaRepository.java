package it.maggioli.repository;

import it.maggioli.domain.Licenza;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Licenza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenzaRepository extends JpaRepository<Licenza, Long> {
}
