# logstash logging service

## installation redis docker image
```
docker run -d -p 6379:6379 --restart=always --name redis redis
```

## installation elasticsearch, kibana docker image
```
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 --restart=always -e "discovery.type=single-node" elasticsearch:6.5.4

docker run -d --name kibana -p 5601:5601 --restart=always kibana:6.5.4
```

## installation custom logstash docker image

- save as logstash.yml
```yaml
http.host: "0.0.0.0"
xpack.monitoring.elasticsearch.url: http://192.168.99.100:9200
```

- save as logstash-redis.conf
```
input {
  redis {
    host => "127.0.0.1"
    type => "redis-input"
    # these settings should match the output of the agent
    data_type => "list"
    key => "logstash"
    # We use json_event here since the sender is a logstash agent
    message_format => "json_event"
  }
}

filter {
  json {
    source => "message"
  }
}

output {
  stdout{
    codec => rubydebug
  }
  elasticsearch {
    hosts => "192.168.99.100:9200"
  }
}
```

- save as logstash.conf
```
input {
  tcp {
    port => 5000
    codec => json
  }
}
 
filter {
  json {
    source => "message"
  }
}
 
output {
  stdout{
    codec => rubydebug
  }
  elasticsearch {
    hosts => "192.168.99.100:9200"
  }
}
```

- save as Dockerfile
```dockerfile
FROM logstash:6.5.4
RUN rm -f /usr/share/logstash/config/logstash-sample.conf
RUN rm -f /usr/share/logstash/pipeline/logstash.conf

COPY --chown=logstash:root logstash.yml /usr/share/logstash/config/logstash.yml

COPY --chown=logstash:root logstash.conf /usr/share/logstash/pipeline/logstash.conf
COPY --chown=logstash:root logstash-redis.conf /usr/share/logstash/pipeline/logstash-redis.conf

EXPOSE 5000
CMD ["logstash"]
```

- installation
```
; download image
docker pull logstash:6.5.4

; build
docker build -t docker-logstash .

; run
docker run -d -p 5000:5000 --restart=always --name docker-logstash docker-logstash

; logs
docker logs -f docker-logstash
```