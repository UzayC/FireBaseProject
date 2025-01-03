#!/bin/bash

# Maven'i indirin ve kurun
curl -sSL https://get.maven.org | bash

# Maven'i PATH'e ekleyin
export PATH=$PATH:$HOME/.maven/bin

# Projeyi build edin
mvn clean install
