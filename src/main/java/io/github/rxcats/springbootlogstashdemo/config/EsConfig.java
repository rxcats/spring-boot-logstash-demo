package io.github.rxcats.springbootlogstashdemo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    @Value("${elasticsearch.host}")
    String host;

    @Value("${elasticsearch.port}")
    int port;

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
            RestClient.builder(new HttpHost(host, port))
        );
    }

}
