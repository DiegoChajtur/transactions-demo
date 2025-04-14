FROM openjdk:17-jdk-slim

COPY target/*.jar /

COPY docker/entrypoint.sh /
ENTRYPOINT ["/bin/sh", "/entrypoint.sh"]
