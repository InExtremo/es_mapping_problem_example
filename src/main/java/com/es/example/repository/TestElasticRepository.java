package com.es.example.repository;

import com.es.example.model.TestEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestElasticRepository extends ElasticsearchRepository<TestEntity, String> {

}
