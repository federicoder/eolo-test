package it.maggioli.repository;

import it.maggioli.domain.Collaboratore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Collaboratore entity.
 */
@Repository
public interface CollaboratoreRepository extends JpaRepository<Collaboratore, Long> {

    @Query(value = "select distinct collaboratore from Collaboratore collaboratore left join fetch collaboratore.idCollaboratores",
        countQuery = "select count(distinct collaboratore) from Collaboratore collaboratore")
    Page<Collaboratore> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct collaboratore from Collaboratore collaboratore left join fetch collaboratore.idCollaboratores")
    List<Collaboratore> findAllWithEagerRelationships();

    @Query("select collaboratore from Collaboratore collaboratore left join fetch collaboratore.idCollaboratores where collaboratore.id =:id")
    Optional<Collaboratore> findOneWithEagerRelationships(@Param("id") Long id);
}
