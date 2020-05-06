package it.maggioli.repository.search;

import it.maggioli.domain.Invito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Invito} entity.
 */
public interface InvitoSearchRepository extends ElasticsearchRepository<Invito, Long> {
}
