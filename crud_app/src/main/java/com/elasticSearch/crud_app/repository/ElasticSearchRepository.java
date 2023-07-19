package com.elasticSearch.crud_app.repository;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticSearch.crud_app.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.elasticSearch.crud_app.constant.ApplicationConstants.INDEX_NAME;

@Repository
@Slf4j
public class ElasticSearchRepository {

    @Autowired
    private ElasticsearchClient elasticsearchClient;


    public String createOrUpdateDocument(Product product) throws IOException {

        IndexResponse response = elasticsearchClient.index(i -> i
                .index(INDEX_NAME)
                .id(product.getId())
                .document(product)
        );
        if (response.result().name().equals("Created")) {
            return "Document has been successfully created.";
        } else if (response.result().name().equals("Updated")) {
            return "Document has been successfully updated.";
        }
        return "Error while performing the operation.";
    }

    public Product getDocumentById(String productId) throws IOException {
        Product product = null;
        GetResponse<Product> response = elasticsearchClient.get(g -> g
                        .index(INDEX_NAME)
                        .id(productId),
                Product.class
        );

        if (response.found()) {
            product = response.source();
            log.info("Product name " + product.getName());
        } else {
            log.info("Product not found");
        }

        return product;
    }

    public String deleteDocumentById(String productId) throws IOException {
        DeleteRequest request;
        if (productId == null)
            request = DeleteRequest.of(d -> d.index(INDEX_NAME));
        else
            request = DeleteRequest.of(d -> d.index(INDEX_NAME).id(productId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return "Product with id " + deleteResponse.id() + " has been deleted.";
        }
        log.info("Product not found");
        return "Product with id " + deleteResponse.id() + " does not exist.";

    }

    public List<Product> searchAllDocuments() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(INDEX_NAME));
        SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest, Product.class);
        List<Hit<Product>> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for (Hit<Product> object : hits) {
            log.info("The product is: " + object.source());
            products.add(object.source());
        }
        return products;
    }
}
