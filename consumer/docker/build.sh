#!/bin/bash

MAVEN_IMAGE=maven:3.9.9-eclipse-temurin-17-alpine
APP_NAME=consumer-app
DOCKERFILE=Dockerfile
IMAGENAME=$APP_NAME-docker

docker run -it --rm --name $APP_NAME -v "$(pwd)/../":/usr/src/app -w /usr/src/app $MAVEN_IMAGE mvn clean install -Dmaven.test.skip=true

docker build -f $DOCKERFILE --tag $IMAGENAME ../