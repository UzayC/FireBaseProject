#!/bin/bash

# Maven'i çalıştırılabilir yap
chmod +x mvnw

# JAVA_HOME'u manuel olarak ayarla
export JAVA_HOME=/opt/java/openjdk
export PATH=$JAVA_HOME/bin:$PATH

# Projeyi Maven ile build et
./mvnw clean install