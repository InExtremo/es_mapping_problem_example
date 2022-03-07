package com.es.example.configs;


import com.es.example.model.TestEntity;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.es.example.repository")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);

    @Value("${elasticsearch.host:localhost}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port:9200}")
    private String elasticSearchPort;

    @Override
    @Bean
    public @NotNull RestHighLevelClient elasticsearchClient() {
        logger.info("es host {} : port {}", elasticSearchHost, elasticSearchPort);
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(elasticSearchHost
                        + ":"
                        + elasticSearchPort)
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(30))
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    @Override
    public @NotNull ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(new InnerClassDataWriter(), new InnerClassDataReader()));
    }


    @WritingConverter
    static class InnerClassDataWriter implements Converter<TestEntity.InnerClassData, Map<String, Object>> {
        @Override
        public Map<String, Object> convert(@NotNull TestEntity.InnerClassData source) {
            return Map.of("text_field", source.getTextField(), "other_text_field", source.getOtherTextField());
        }
    }

    @ReadingConverter
    static class InnerClassDataReader implements Converter<Map<String, Object>, TestEntity.InnerClassData> {

        @Override
        public TestEntity.InnerClassData convert(Map<String, Object> source) {
            String text_field = (String) source.get("text_field");
            String other_text_field = (String) source.get("other_text_field");
            return new TestEntity.InnerClassData(text_field, other_text_field);
        }
    }


}
