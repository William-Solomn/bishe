package com.example.server_parking.mqtt;
import com.example.server_parking.controller.CarInfoController;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MQTTService {
    private static final String MQTT_BROKER_URI = "tcp://175.24.202.87:1883";
    private static final String MQTT_CLIENT_ID = "parking_server";
    private static final String MQTT_TOPIC = "parking/lift_gate";

    private IMqttClient mqttClient;

    public MQTTService() {
        try {
            mqttClient = new MqttClient(MQTT_BROKER_URI, MQTT_CLIENT_ID);
            mqttClient.connect();
        } catch (MqttException e) {
            Logger logger = LoggerFactory.getLogger(CarInfoController.class);
            logger.error("没连上");
            e.printStackTrace();
        }
    }

    public void sendLiftGateCommand() {
        try {
            MqttMessage message = new MqttMessage("1".getBytes());
            mqttClient.publish(MQTT_TOPIC, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
