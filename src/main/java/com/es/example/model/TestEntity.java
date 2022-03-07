package com.es.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "test_entity")
public class TestEntity {
    @Id
    private String id;

    @Field(type = FieldType.Object)
    private List<InnerClassData> dataObject;

    @Field(type = FieldType.Flattened)
    private List<InnerClassData> dataFlattened;

    @Field(type = FieldType.Nested)
    private List<InnerClassData> dataNested;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InnerClassData {
        @Field(name = "text_field", type = FieldType.Keyword)
        private String textField;
        @MultiField(mainField = @Field(name = "other_text_field", type = FieldType.Text), otherFields = {
                @InnerField(suffix = "keyword", type = FieldType.Keyword),
                @InnerField(suffix = "analyzed", type = FieldType.Text, analyzer = "german")
        })
        private String otherTextField;
    }
}
