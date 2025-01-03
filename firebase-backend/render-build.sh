#!/bin/bash

# Maven'i çalıştırılabilir yap
chmod +x mvnw

# Projeyi build et
./mvnw clean install

# Maven'i indir ve kur
curl -o apache-maven.tar.gz https://downloads.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz
tar -xzf apache-maven.tar.gz
export PATH=$PATH:$(pwd)/apache-maven-3.8.8/bin

# OpenJDK'yi indir ve kur
curl -o openjdk.tar.gz https://download.java.net/java/GA/jdk17/0d1cfde4252546c693194c345fcedc12/35/GPL/openjdk-17_linux-x64_bin.tar.gz
tar -xzf openjdk.tar.gz || (echo "Tar extraction failed. Please check the downloaded file." && exit 1)
export JAVA_HOME=$(pwd)/jdk-17
export PATH=$PATH:$JAVA_HOME/bin

# Maven ile projeyi build et
mvn clean install
