#!/bin/bash

# Script hatalarını daha kolay tespit etmek için
set -e

# Maven Wrapper'a çalıştırma izni ver
chmod +x mvnw

# Projeyi Maven Wrapper ile build et
./mvnw clean install || true

# Maven'i indir ve kur
if [ ! -d "apache-maven-3.8.8" ]; then
  echo "Downloading Maven..."
  curl -o apache-maven.tar.gz https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz
  tar -xzf apache-maven.tar.gz
  rm apache-maven.tar.gz
fi
export PATH=$PATH:$(pwd)/apache-maven-3.8.8/bin

# Java'yı indir ve kur
if [ ! -d "jdk-17" ]; then
  echo "Downloading OpenJDK..."
  curl -o openjdk.tar.gz https://download.java.net/java/GA/jdk17/0d1cfde4252546c693194c345fcedc12/35/GPL/openjdk-17_linux-x64_bin.tar.gz
  tar -xzf openjdk.tar.gz
  rm openjdk.tar.gz
fi
export JAVA_HOME=$(pwd)/jdk-17
export PATH=$PATH:$JAVA_HOME/bin

# Maven ile projeyi build et
echo "Building project with Maven..."
mvn clean install
