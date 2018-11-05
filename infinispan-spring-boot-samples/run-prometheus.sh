#!/usr/bin/env bash
docker run -d --name=prometheus -p 9090:9090 -v /Users/katiaaresti/REDHAT/SPRING/infinispan-spring-boot/infinispan-spring-boot-samples/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
