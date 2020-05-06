package it.maggioli.repository.search;

import it.maggioli.domain.Collaboratore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Collaboratore} entity.
 */
public interface CollaboratoreSearchRepository extends ElasticsearchRepository<Collaboratore, Long> {
}
