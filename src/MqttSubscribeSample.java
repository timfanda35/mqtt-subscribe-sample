import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */
public class MqttSubscribeSample implements MqttCallback {

  private static final int qos = 2;
  private String brokerUrl;
  private String clientId;
  private String topic;

  public static void main(String[] args) {
    try {
      new MqttSubscribeSample(
        System.getenv("MQTT_BROKER_URL"),
        System.getenv("MQTT_CLIENT_ID"),
        System.getenv("MQTT_SUB_TOPIC"),
        System.getenv("MQTT_CA_CRT"),
        System.getenv("MQTT_CLIENT_CRT"),
        System.getenv("MQTT_CLIENT_KEY")
      );
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public MqttSubscribeSample(String brokerUrl, String clientId, String topic,
    String caFile, String clientFile, String clientKey)
    throws MqttException, MqttSecurityException {

    MqttClient client = new MqttClient(brokerUrl, clientId , null);
    client.setCallback(this);

    MqttConnectOptions options = new MqttConnectOptions();
    options.setConnectionTimeout(60);
    options.setKeepAliveInterval(60);
    options.setSocketFactory(SslUtil.getSocketFactory(
      caFile,
      clientFile,
      clientKey,
      ""
      ));

    client.connect(options);
    client.subscribe(topic, qos);
  }

  /****************************************************************/
  /* Methods to implement the MqttCallback interface              */
  /****************************************************************/

  /**
   * @see MqttCallback#connectionLost(Throwable)
   */
  public void connectionLost(Throwable cause) {
    // Called when the connection to the server has been lost.
    // An application may choose to implement reconnection
    // logic at this point. This sample simply exits.
    System.out.println("Connection to " + brokerUrl + " lost!" + cause);
    System.exit(1);
  }

  /**
   * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
   */
  public void deliveryComplete(IMqttDeliveryToken token) {
    // Called when a message has been delivered to the
    // server. The token passed in here is the same one
    // that was passed to or returned from the original call to publish.
    // This allows applications to perform asynchronous
    // delivery without blocking until delivery completes.
    //
    // This sample demonstrates asynchronous deliver and
    // uses the token.waitForCompletion() call in the main thread which
    // blocks until the delivery has completed.
    // Additionally the deliveryComplete method will be called if
    // the callback is set on the client
    //
    // If the connection to the server breaks before delivery has completed
    // delivery of a message will complete after the client has re-connected.
    // The getPendingTokens method will provide tokens for any messages
    // that are still to be delivered.
  }

  /**
   * @see MqttCallback#messageArrived(String, MqttMessage)
   */
  public void messageArrived(String topic, MqttMessage message) throws MqttException {
    // Called when a message arrives from the server that matches any
    // subscription made by the client
    System.out.println(
        "  Topic:\t" + topic +
        "  Message:\t" + new String(message.getPayload()) +
        "  QoS:\t" + message.getQos());
  }
}
