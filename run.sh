#!/bin/bash

if [ ! -d "classes" ]; then
  echo "execute build.sh first";
  exit 1;
fi

current_path="$(pwd)"
certs_path="$current_path/certs"

export MQTT_BROKER_URL=ssl://localhost:8883
export MQTT_CLIENT_ID=mqtt_sub
export MQTT_SUB_TOPIC=\$SYS/broker/bytes/\#

export MQTT_CA_CRT="$certs_path/ca.crt"
export MQTT_CLIENT_CRT="$certs_path/client.crt"
export MQTT_CLIENT_KEY="$certs_path/client.key"

java \
-cp classes:libs/org.eclipse.paho.client.mqttv3-1.0.2.jar:\
libs/bcprov-jdk15on-155.jar:\
libs/bcpkix-jdk15on-155.jar \
MqttSubscribeSample
