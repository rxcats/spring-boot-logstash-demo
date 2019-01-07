FROM docker.elastic.co/kibana/kibana:6.5.4

COPY --chown=kibana:root kibana.yml /usr/share/kibana/config/kibana.yml

EXPOSE 5601
CMD ["kibana"]