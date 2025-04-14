#!/bin/bash

docker compose down -t0 &&
docker container prune -f &&
docker volume prune --all -f