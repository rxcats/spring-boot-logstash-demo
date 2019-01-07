package io.github.rxcats.springbootlogstashdemo.repository.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.springbootlogstashdemo.entity.BuyItemLog;

@Slf4j
@Repository
public class BuyItemLogRepository {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestHighLevelClient client;

    public List<BuyItemLog> search(int from, int size) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.sort(new FieldSortBuilder("eventDate").order(SortOrder.DESC));
        sourceBuilder.from(from);
        sourceBuilder.size(size);

        SearchRequest request = new SearchRequest("buy_item_log");
        request.source(sourceBuilder);

        List<BuyItemLog> results = new ArrayList<>();
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                BuyItemLog buyItemLog = objectMapper.convertValue(hit.getSourceAsMap(), BuyItemLog.class);
                results.add(buyItemLog);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

}
