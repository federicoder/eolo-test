package it.maggioli.repository.search;

import it.maggioli.domain.StorageCloud;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StorageCloud} entity.
 */
public interface StorageCloudSearchRepository extends ElasticsearchRepository<StorageCloud, Long> {
}
