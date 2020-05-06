package it.maggioli.repository.search;

import it.maggioli.domain.Licenza;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Licenza} entity.
 */
public interface LicenzaSearchRepository extends ElasticsearchRepository<Licenza, Long> {
}
