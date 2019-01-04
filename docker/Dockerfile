FROM logstash:6.5.4
RUN rm -f /usr/share/logstash/config/logstash-sample.conf
RUN rm -f /usr/share/logstash/pipeline/logstash.conf

COPY --chown=logstash:root logstash.yml /usr/share/logstash/config/logstash.yml

COPY --chown=logstash:root logstash.conf /usr/share/logstash/pipeline/logstash.conf
COPY --chown=logstash:root logstash-redis.conf /usr/share/logstash/pipeline/logstash-redis.conf

EXPOSE 5000
CMD ["logstash"]