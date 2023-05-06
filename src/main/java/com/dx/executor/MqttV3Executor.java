package com.dx.executor;

import com.dx.entity.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dx.db.SaveSensorData;
import com.dx.mqttclient.BlockedThreadPool;
import com.dx.mqttclient.ConnectionPara;
import com.dx.mqttclient.PublishPara;
import com.dx.mqttclient.SubscribePara;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author 32825
 */
public class MqttV3Executor implements MqttCallback {

    ConnectionPara v3ConnectionParameters;
    PublishPara v3PublishParameters;
    SubscribePara v3SubscriptionParameters;
    boolean quiet = false;
    boolean debug = false;
    MqttClient v3Client;
    private int actionTimeout=10;

    BlockedThreadPool executorPool= BlockedThreadPool.createBlockedThreadPool(5,"workThread");

    // To allow a graceful disconnect.

    final Thread mainThread = Thread.currentThread();
    static volatile boolean keepRunning = true;

    /**
     * Initialises the MQTTv3 Executor
     * @param debug         - Whether to print debug data to the console
     * @param quiet         - Whether to hide error messages
     */
    public MqttV3Executor(ConnectionPara cp, boolean debug, boolean quiet) {
        this.v3ConnectionParameters = cp;
        this.debug = debug;
        this.quiet = quiet;
    }

    public void connect() {
        try {
            this.v3Client = new MqttClient(this.v3ConnectionParameters.getHostURI(),
                    this.v3ConnectionParameters.getClientID(), new MemoryPersistence());
            this.v3Client.setCallback(this);

            // Connect to Server
            logMessage(String.format("Connecting to MQTT Broker: %s, Client ID: %s", v3Client.getServerURI(),
                    v3Client.getClientId()));
            v3Client.connect(v3ConnectionParameters.getConOpts());
        } catch (MqttException e) {
            logError(e.getMessage());
        }
    }

        public void PublishContent(PublishPara pp){

        }

    public void subscribeTopic(SubscribePara sp){
        this.v3SubscriptionParameters=sp;
        // Subscribe to a topic
        logMessage(String.format("Subscribing to %s, with QoS %d", v3SubscriptionParameters.getTopic(),
                v3SubscriptionParameters.getQos()));
        try{
            String subscribeTopic= v3SubscriptionParameters.getTopic();
            String[] subscribeTopics=subscribeTopic.split(",");
            for(int i=0;i<subscribeTopics.length;++i){
                this.v3Client.subscribe(subscribeTopics[i],
                        v3SubscriptionParameters.getQos());
            }
            while (true) {
                // Do nothing
            }
//            disconnectClient();
//            closeClientAndExit();
        }catch (MqttException e){
            logError(e.getMessage());
        }
    }

    /**
     * Simple helper function to publish a message.
     * @param payload
     * @param qos
     * @param retain
     * @param topic
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    private void publishMessage(byte[] payload, int qos, boolean retain, String topic)
            throws MqttPersistenceException, MqttException {
        MqttMessage v3Message = new MqttMessage(payload);
        v3Message.setQos(qos);
        v3Message.setRetained(retain);
        v3Client.publish(topic, v3Message);
    }

    /**
     * Log a message to the console, nothing fancy.
     * @param message
     */
    private void logMessage(String message) {
        if (this.debug == true) {
            System.out.println(message);
        }
    }

    /**
     * Log an error to the console
     * @param error
     */
    private void logError(String error) {
        if (this.quiet == false) {
            System.err.println(error);
        }
    }

    private void disconnectClient() throws MqttException {
        // Disconnect
        logMessage("Disconnecting from server.");
        v3Client.disconnect();
    }

    private void closeClientAndExit() {
        // Close the client
        logMessage("Closing Connection.");
        try {
            this.v3Client.close();
            logMessage("Client Closed.");
            System.exit(0);
            mainThread.join();
        } catch (MqttException | InterruptedException e) {
            // End the Application
            System.exit(1);
        }

    }

    @Override
    public void connectionLost(Throwable cause) {
        if (v3ConnectionParameters.isAutomaticReconnectEnabled()) {
            logMessage(String.format("The connection to the server was lost, cause: %s. Waiting to reconnect.",
                    cause));
        } else {
            logMessage(String.format("The connection to the server was lost, cause: %s. Closing Client",
                    cause));
            closeClientAndExit();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logMessage(String.format("topic: %s, Qos: %d", v3SubscriptionParameters.getTopic(),
                v3SubscriptionParameters.getQos()));
        String resultStr = new String(message.getPayload());
        System.out.println("topic: " + topic);
        System.out.println("Qos: " + message.getQos());
        System.out.println("message content: " + new String(resultStr));
        String[] subscribeTopics=v3SubscriptionParameters.getTopic().split(",");
        if(topic.equals(subscribeTopics[0])){
//            executorPool.submit(() -> {
                ObjectMapper objectMapper = new ObjectMapper();
                Result result = null;
                try {
                    result = objectMapper.readValue(resultStr, Result.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                SaveSensorData.saveSensorData(result);
//            });
        }else if(topic.equals(subscribeTopics[1])){
            executorPool.submit(() -> {
                TomatoDiseaseExecutor te = new TomatoDiseaseExecutor();
                te.executeTomatoDisease(resultStr);
            });
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logMessage(String.format("Message %d was delivered.", token.getMessageId()));
    }
}
