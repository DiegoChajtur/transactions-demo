#!/bin/bash

BASEDIR=$(pwd)/load_tests
IMAGENAME=grafana/k6

docker run -it --rm --name k6 --network host -v $BASEDIR/:/tmp/ -w /tmp/ $IMAGENAME run loadTest.js
