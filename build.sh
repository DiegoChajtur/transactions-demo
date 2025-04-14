#!/bin/bash

BASEDIR=$(pwd)

cd $BASEDIR/consumer/docker
./build.sh

cd $BASEDIR/producer/docker
./build.sh 