package com.ELK_POC.elasticSearch.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.elasticsearch.client.RestHighLevelClient;


@Configuration
@Lazy
public class ElasticConfiguration {

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
        RestClientBuilder builder=RestClient.builder(new HttpHost(elasticsearchHostname, elasticsearchPortName, elasticsearchScheme))
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
                        });
        return new RestHighLevelClient(builder);
    }

// GCP VM instance config
// @Bean(destroyMethod = "close")
// public RestHighLevelClient client() {
//
// RestHighLevelClient client=new RestHighLevelClient(
// RestClient.builder(new HttpHost("34.100.181.31", 9200, "http"))
// .setRequestConfigCallback(
// new RestClientBuilder.RequestConfigCallback() {
// @Override
// public RequestConfig.Builder customizeRequestConfig(
// RequestConfig.Builder requestConfigBuilder) {
// return requestConfigBuilder
// .setConnectTimeout(6000000)
// .setSocketTimeout(6000000);
// }
// }));
// return client;
// }

}
