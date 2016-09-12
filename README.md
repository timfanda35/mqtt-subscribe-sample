MQTT Subscribe Sample
=====================

This project shows how connect to mqtt broker with certificate.

Requirement
-----------

* jdk
* mqtt broker
* ca.crt
* client.crt
* client.key

ca.crt, client.crt, client.key have to match mqtt borker configuration.

Build
-----

Run shell script:


    $ bash ./build.sh

Run
---

Edit `run.sh` for your environment:

    export MQTT_BROKER_URL=ssl://localhost:8883
    export MQTT_CLIENT_ID=mqtt_sub
    export MQTT_SUB_TOPIC=\$SYS/broker/bytes/\#

    export MQTT_CA_CRT="$certs_path/ca.crt"
    export MQTT_CLIENT_CRT="$certs_path/client.crt"
    export MQTT_CLIENT_KEY="$certs_path/client.key"


Run shell script:


    $ bash ./run.sh

Reference
---------

* [SslUilt.java](https://gist.github.com/rohanag12/07ab7eb22556244e9698)
* [Sample Logic](https://gist.github.com/sharonbn/4104301#file-readme-txt)
* [Paho - MQTT client Library](https://github.com/eclipse/paho.mqtt.java)
* [The Legion of the Bouncy Castle](https://www.bouncycastle.org/latest_releases.html)
