package io.github.rxcats.springbootlogstashdemo.es;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class EsTest {

    @Autowired
    RestHighLevelClient client;

    @Test
    void indexTest() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();

        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
            .timeout("1s")
            .source(builder);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("response:{}", response);

    }

    @Test
    void getTest() throws IOException {
        GetRequest getRequest = new GetRequest("posts", "doc", "1");
        getRequest.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);

        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        log.info("response:{}", response.getSource());

    }

}
