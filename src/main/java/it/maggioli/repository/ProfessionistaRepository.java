package it.maggioli.repository;

import it.maggioli.domain.Professionista;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Professionista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionistaRepository extends JpaRepository<Professionista, Long> {
}
