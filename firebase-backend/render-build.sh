#!/bin/bash

# Maven'i çalıştırılabilir hale getir
chmod +x mvnw

# JAVA_HOME değişkenini kontrol et
if [ -z "$JAVA_HOME" ]; then
  echo "JAVA_HOME is not set. Please configure it in the Render environment."
  exit 1
fi

# Maven ile projeyi build et
./mvnw clean install