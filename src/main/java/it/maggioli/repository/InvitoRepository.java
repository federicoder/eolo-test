package it.maggioli.repository;

import it.maggioli.domain.Invito;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Invito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitoRepository extends JpaRepository<Invito, Long> {
}
