# logstash logging service

## installation redis docker image
```
docker run -d -p 6379:6379 --restart=always --name redis redis
```

## installation elasticsearch docker image
```
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 --restart=always -e "discovery.type=single-node" elasticsearch:6.5.4
```

## installation kibana docker image

- save as kibana.yml
```yaml
# Default Kibana configuration from kibana-docker.

server.name: kibana
server.host: "0"
elasticsearch.url: http://192.168.99.100:9200
xpack.monitoring.ui.container.elasticsearch.enabled: true
```

- save as Dockerfile
```dockerfile
FROM kibana:6.5.4

COPY --chown=kibana:root kibana.yml /usr/share/kibana/config/kibana.yml

EXPOSE 5601
CMD ["kibana"]
```

- installation
```
docker build -t docker-kibana .
docker stop kibana
docker rm kibana
docker run -d  -p 5601:5601 --restart=always --name kibana docker-kibana
```


## installation custom logstash docker image

- save as logstash.yml
```yaml
http.host: "0.0.0.0"
xpack.monitoring.enabled: true
xpack.monitoring.elasticsearch.url: http://192.168.99.100:9200
```

- save as logstash-redis.conf
```
input {
  redis {
    db => 0
    host => "192.168.99.100"
    port => 6379
    ssl => false
    data_type => "list"
    key => "buy_item_log"
  }
}

filter {
  json {
    source => "message"
  }
}

output {
  stdout {
    codec => json
  }
  elasticsearch {
    hosts => "192.168.99.100:9200"
    index => "buy_item_log"
  }
}
```

- save as Dockerfile
```dockerfile
FROM logstash:6.5.4
RUN rm -f /usr/share/logstash/config/logstash-sample.conf
RUN rm -f /usr/share/logstash/pipeline/logstash.conf

COPY --chown=logstash:root logstash.yml /usr/share/logstash/config/logstash.yml
COPY --chown=logstash:root logstash-redis.conf /usr/share/logstash/pipeline/logstash-redis.conf

CMD ["logstash"]
```

- installation
```
docker build -t docker-logstash .
docker stop logstash
docker rm logstash
docker run -d --restart=always --name logstash docker-logstash
```

- logs
```
docker logs -f logstash
```
