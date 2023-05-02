package com.example.server_parking.mqtt;
import com.example.server_parking.controller.CarInfoController;
import com.example.server_parking.dao.ParkingInfoDao;
import com.example.server_parking.entity.ParkingInfoEntity;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQTTService {
    private static final String MQTT_BROKER_URI = "tcp://175.24.202.87:1883";
    private static final String MQTT_CLIENT_ID = "parking_server";
    private static final String MQTT_TOPIC = "parking/lift_gate";
    private static final String MQTT_TOPIC_DISTANCE = "railing/distance";
    private IMqttClient mqttClient;
    @Autowired
    private ParkingInfoDao parkingInfoDao;
    public MQTTService() {
        try {
            mqttClient = new MqttClient(MQTT_BROKER_URI, MQTT_CLIENT_ID);
            mqttClient.connect();
        } catch (MqttException e) {
            Logger logger = LoggerFactory.getLogger(CarInfoController.class);
            logger.error("没连上");
            e.printStackTrace();
        }
        try {
            mqttClient = new MqttClient(MQTT_BROKER_URI, MQTT_CLIENT_ID);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // handle connection lost
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (MQTT_TOPIC_DISTANCE.equals(topic)) {
                        String distanceStr = new String(message.getPayload());
                        double distance = Double.parseDouble(distanceStr);
                        int parking_is_useful = distance < 200 ? 1 : 0;
                        setParkingIsUseful(parking_is_useful);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // handle delivery complete
                }
            });

            mqttClient.connect();
            mqttClient.subscribe(MQTT_TOPIC_DISTANCE);
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
    public void setParkingIsUseful(int parking_is_useful) {
        String parking_id = "2c25f2ad-e82d-4a20-acc3-161054a417f5"; // Replace with your actual parking_id
        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
        parkingInfoEntity.setParking_id(parking_id);
        parkingInfoEntity.setParking_is_useful(parking_is_useful);
        parkingInfoDao.updateUseFul(parkingInfoEntity);
    }
}
