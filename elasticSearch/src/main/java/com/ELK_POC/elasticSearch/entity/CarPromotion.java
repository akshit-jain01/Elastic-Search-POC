package com.ELK_POC.elasticSearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(indexName = "car-promotion")
public class CarPromotion {

    @Id
    private String id;
    private String type;
    private String description;

    public CarPromotion(String type, String description) {
        this.type = type;
        this.description = description;
    }
}