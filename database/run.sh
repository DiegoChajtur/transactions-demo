#!/bin/bash

docker compose down &&
docker container prune -f &&
docker volume prune --all -f &&
docker compose up