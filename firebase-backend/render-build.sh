#!/bin/bash

# Maven'i indir ve kur
curl -o apache-maven.tar.gz https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz
tar -xzf apache-maven.tar.gz
export PATH=$PATH:$(pwd)/apache-maven-3.8.8/bin

# Maven ile projeyi build et
mvn clean install
