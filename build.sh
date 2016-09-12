#!/bin/bash

if [ ! -d "classes" ]; then
  mkdir classes;
fi

javac -verbose \
-cp src:libs/org.eclipse.paho.client.mqttv3-1.0.2.jar:\
libs/bcprov-jdk15on-155.jar:\
libs/bcpkix-jdk15on-155.jar \
-d classes \
src/MqttSubscribeSample.java \
src/SslUtil.java
