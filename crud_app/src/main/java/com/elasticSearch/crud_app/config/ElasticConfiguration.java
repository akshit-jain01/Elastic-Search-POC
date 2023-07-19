package com.elasticSearch.crud_app.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ElasticConfiguration {
//    @Bean
//    public RestClient getRestClient() {
//        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9201)).build();
//        return restClient;
//    }

    @Value("${elasticsearch.hostname}")
    private String elasticsearchHostname;

    @Value("${elasticsearch.port}")
    private Integer elasticsearchPortName;

    @Value("${elasticsearch.scheme}")
    private String elasticsearchScheme;

    @Value("${elasticsearch.connection.timeout}")
    private Integer elasticsearchConnectionTimeout;

    @Value("${elasticsearch.socket.timeout}")
    private Integer elasticsearchSocketTimeout;

    // Elastic cloud configuration
    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
// CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
// credentialsProvider.setCredentials(AuthScope.ANY,
// new UsernamePasswordCredentials("elastic", "Kc9En64vUetR6ey1lmEMdE7g"));
//        Header[] headers = {
//                new BasicHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=7"),
//                new BasicHeader("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7")};
        Header[] headers = {
                new BasicHeader("Content-Type", "application/json")};

//        var httpClientConfigCallback = httpClientBuilder ->
//                httpClientBuilder
//                        .setDefaultCredentialsProvider(credentialsProvider)
//                        // this request & response header manipulation helps get around newer (>=7.16) versions
//                        // of elasticsearch-java client not working with older (<7.14) versions of Elasticsearch
//                        // server
//                        .setDefaultHeaders(
//                                List.of(
//                                        new BasicHeader(
//                                                HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())))
//                        .addInterceptorLast(
//                                (HttpResponseInterceptor)
//                                        (response, context) ->
//                                                response.addHeader("X-Elastic-Product", "Elasticsearch"));
//        var restClient =
//                RestClient.builder(elasticsearchHosts)
//                        .setHttpClientConfigCallback(httpClientConfigCallback)
//                        .build();


        RestClientBuilder builder = RestClient.builder(new HttpHost(elasticsearchHostname, elasticsearchPortName, elasticsearchScheme))
//                .setDefaultHeaders(headers)
                .setRequestConfigCallback(
                        new RestClientBuilder.RequestConfigCallback() {
                            @Override
                            public RequestConfig.Builder customizeRequestConfig(
                                    RequestConfig.Builder requestConfigBuilder) {
                                return requestConfigBuilder
                                        .setConnectTimeout(elasticsearchConnectionTimeout)
                                        .setSocketTimeout(elasticsearchSocketTimeout);
                            }
                        })
                //Need to set this client callback to define the response headers that are not defined automatically in earlier versions of elastic search.
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    httpClientBuilder.setDefaultHeaders(List.of(headers));
                    httpClientBuilder.addInterceptorLast((HttpResponseInterceptor)
                            (response, context) ->
                                    response.addHeader("X-Elastic-Product", "Elasticsearch"));
                    return httpClientBuilder;
                });
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                restHighLevelClient().getLowLevelClient(), new JacksonJsonpMapper());
    }


    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }

}
