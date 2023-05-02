#include <ESP8266WiFi.h>
#include <WiFiClient.h>

const char* ssid = "abcd";   // 无线局域网的名称
const char* password = "1234567889";   // 无线局域网的密码

const char* serverName = "localhost";  // 服务器的地址
const int serverPort = 8887;   // 服务器的端口号

WiFiClient client;    // WiFi客户端

const int TrigPin = 4;     //设置发射脚位，对应ESP8266 D2
const int EchoPin = 5;    //设置接收脚位，对应ESP8266 D1
float cm; 

void setup() {
  Serial.begin(115200);
  pinMode(TrigPin, OUTPUT);
  pinMode(EchoPin, INPUT); 
  WiFi.begin(ssid, password);   // 连接WiFi
  while (WiFi.status() != WL_CONNECTED) {  // 等待连接成功
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");
}

void loop() {
  cm = getDistance();   // 获取距离
  if (cm <= 70) {
    sendSignal();    // 距离小于等于70cm，向服务器发送不可用信号
  } else {
    sendSignal();    // 距离大于70cm，向服务器发送可用信号
  }
  delay(5000);   // 每5秒发送一次请求
}

float getDistance() {
  digitalWrite(TrigPin, LOW);       //用低高低电平发送脉冲给Trigpin引脚
  delayMicroseconds(2);             //微秒级延时
  digitalWrite(TrigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(TrigPin, LOW);
  cm = pulseIn(EchoPin, HIGH) / 58.0; //读取脉冲宽度，换算成厘米
  Serial.print("Distance: ");
  Serial.print(cm);                   //显示距离
  Serial.println("cm");                   //显示单位
  return cm;
}

void sendSignal() {
  if (client.connect(serverName, serverPort)) {   // 连接服务器
    Serial.println("Connected to server");
    String data = "parking_id=214121";
    // 发送POST请求
    client.println("POST /setIsUseful HTTP/1.1");
    client.println("Host: " + String(serverName));
    client.println("Content-Type: application/x-www-form-urlencoded");
    client.print("Content-Length: ");
    client.println(data.length());
    client.println();
    client.print(data);
    Serial.println("Signal sent");
  } else {
    Serial.println("Connection to server failed");
  }
  client.stop();   // 断开连接
}
