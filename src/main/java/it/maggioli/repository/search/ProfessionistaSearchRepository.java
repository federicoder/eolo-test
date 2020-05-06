package it.maggioli.repository.search;

import it.maggioli.domain.Professionista;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Professionista} entity.
 */
public interface ProfessionistaSearchRepository extends ElasticsearchRepository<Professionista, Long> {
}
