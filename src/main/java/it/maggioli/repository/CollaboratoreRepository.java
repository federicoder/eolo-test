package it.maggioli.repository;

import it.maggioli.domain.Collaboratore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Collaboratore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaboratoreRepository extends JpaRepository<Collaboratore, Long> {
}
