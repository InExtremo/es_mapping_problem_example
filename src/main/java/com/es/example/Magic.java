package com.es.example;

import com.es.example.model.TestEntity;
import com.es.example.repository.TestElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class Magic {
    private final TestElasticRepository repository;
    private final ElasticsearchRestTemplate restTemplate;


    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        TestEntity testEntity = new TestEntity();
        TestEntity.InnerClassData dataObject = new TestEntity.InnerClassData("text object a", "text object b");
        TestEntity.InnerClassData dataFlattened = new TestEntity.InnerClassData("text flattened c", "text flattened d");
        TestEntity.InnerClassData dataNested = new TestEntity.InnerClassData("text nested c", "text nested d");

        testEntity.setDataObject(Collections.singletonList(dataObject));
        testEntity.setDataFlattened(Collections.singletonList(dataFlattened));
        testEntity.setDataNested(Collections.singletonList(dataNested));

        repository.save(testEntity);

        restTemplate.indexOps(TestEntity.class).getMapping()
                .forEach((key, value) -> log.info(" {} -> {}", key, value));
    }
}
