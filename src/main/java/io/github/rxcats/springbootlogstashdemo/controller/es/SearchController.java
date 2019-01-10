package io.github.rxcats.springbootlogstashdemo.controller.es;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.github.rxcats.springbootlogstashdemo.entity.BuyItemLog;
import io.github.rxcats.springbootlogstashdemo.repository.es.BuyItemLogRepository;

@RestController
public class SearchController {

    @Autowired
    BuyItemLogRepository repository;

    @GetMapping(value = "/search/{from}")
    public List<BuyItemLog> search(@PathVariable(value = "from") int from) {
        return repository.search(from, 20);
    }

}
