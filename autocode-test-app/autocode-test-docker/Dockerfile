FROM ubuntu:20.04

RUN apt-get update && \
    apt-get install -y nginx openjdk-11-jdk-headless && \
    mkdir -p /app/back && \
    mkdir /data

COPY target/autocode-test-back-*.jar /app/back/app.jar
COPY config/app.yaml /app/back/app.yaml
COPY target/test-app /app/front
COPY config/nginx.conf /etc/nginx/sites-available/default

EXPOSE 80

ENTRYPOINT nginx && java -jar /app/back/app.jar server /app/back/app.yaml
