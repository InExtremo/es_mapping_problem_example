package com.es.example.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchConfiguration {

    private final RestHighLevelClient elasticsearchClient;

    @Primary
    @Bean(name = {"elasticsearchRestTemplate"})
    public ElasticsearchRestTemplate elasticsearchRestTemplate(@NotNull ElasticsearchConverter elasticsearchConverter) {
        return new ElasticsearchRestTemplate(elasticsearchClient, elasticsearchConverter);
    }

}
