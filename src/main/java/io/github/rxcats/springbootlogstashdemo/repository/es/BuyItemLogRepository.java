package io.github.rxcats.springbootlogstashdemo.repository.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
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

        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource()
            .sort(new FieldSortBuilder("eventDate").order(SortOrder.DESC))
            .from(from)
            .size(size);

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

    public long sumOfItemQty(int itemId) {

        SearchSourceBuilder builder = SearchSourceBuilder.searchSource()
            .query(QueryBuilders.matchQuery("itemId", itemId))
            .aggregation(AggregationBuilders.sum("agg").field("itemQty"));

        SearchRequest request = new SearchRequest("buy_item_log");
        request.source(builder);

        long sum = 0;

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            Sum agg = response.getAggregations().get("agg");
            sum = (long) agg.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sum;

    }

}
