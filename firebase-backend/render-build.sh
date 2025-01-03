#!/bin/bash

# Maven'i çalıştırılabilir hale getir
chmod +x mvnw

# JAVA_HOME değişkenini kontrol et ve ayarla
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
    export PATH=$JAVA_HOME/bin:$PATH
fi

# Maven ile projeyi build et
./mvnw clean install