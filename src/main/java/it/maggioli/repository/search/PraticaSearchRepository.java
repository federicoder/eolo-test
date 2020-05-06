package it.maggioli.repository.search;

import it.maggioli.domain.Pratica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pratica} entity.
 */
public interface PraticaSearchRepository extends ElasticsearchRepository<Pratica, Long> {
}
