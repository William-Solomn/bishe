#include <Servo.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266WebServer.h>

ESP8266WiFiMulti wifiMulti;
// 需求简版：
/*
  ①：在android app上点击按钮，舵机即可转动，电机一下，舵机就转动一下（接口1：正转。接口2：反转）
  ②：如果超声波测距仪测量的距离：
      在70cm范围内，则就向服务器发送信号，说明该车为正在使用（接口3，不可用信号），is_useful=0
      如果距离大于70cm，说明车已经离开，则就向服务器发送信号（接口4，可用信号，is_useful=1

*/

// WiFi网络信息
const char* ssid = "CMCC-pXm9-5G-FAST";
const char* password = "gf92pdxe";

// 服务器信息
const char* server_ip = "localhost";
const int server_port = 80;

// 创建Web服务器
ESP8266WebServer server(80);

Servo myServo;  // 定义Servo对象来控制
int pos = 0;    // 角度存储变量
bool ledState = false;
const int ledPin = 2;

const int TrigPin = 4;     //设置发射脚位，对应ESP8266 D2
const int EchoPin = 5;    //设置接收脚位，对应ESP8266 D1
float cm; 

void handleRoot() {
  String html = "<!DOCTYPE HTML>\n";
  html += "<html><head><title>Hello Server</title></head><body>";
  html += "<h1>Hello from ESP8266!</h1>";
  html += "<p>Click the button to turn on/off the LED:</p>";
  html += "<button onclick=\"toggleLED()\">Toggle LED</button>";
  html += "<script>function toggleLED(){var xhr=new XMLHttpRequest();xhr.open(\"GET\",\"/toggle\");xhr.send();}</script>";
  html += "</body></html>";
  server.send(200, "text/html", html);
}

void handleToggle() {
  ledState = !ledState;
  digitalWrite(ledPin, ledState ? HIGH : LOW);
  if(ledState){
    for (pos = 0; pos <= 90; pos ++) { // 0°到180°
      // in steps of 1 degree
      myServo.write(pos);              // 舵机角度写入
      delay(15);                       // 等待转动到指定角度
    }  
  }else{
    for (pos = 90; pos >= 0; pos --) { // 从180°到0°
      myServo.write(pos);              // 舵机角度写入
      delay(15);                       // 等待转动到指定角度
    }
    Serial.print(" ok ");
    delay(2000);//延时2s 
  }

  delay(2000);//延时2s
  
  server.send(200, "text/plain", ledState ? "on" : "off");
}

void setup() {
  Serial.begin(115200);
  myServo.attach(14); //D5  

  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW);
  
  wifiMulti.addAP("CMCC-pXm9-5G-FAST", "gf92pdxe");
  wifiMulti.addAP("abcd", "1234567889");
  wifiMulti.addAP("your-ssid-3", "your-password-3");
  
  pinMode(TrigPin, OUTPUT);
  pinMode(EchoPin, INPUT); 
  
  Serial.println("Connecting to WiFi...");
  while (wifiMulti.run() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");

  server.on("/", handleRoot);
  server.on("/toggle", handleToggle);

  server.begin();
  Serial.println("HTTP server started");
  Serial.print("Connected to WiFi, IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() { 
  server.handleClient();
  digitalWrite(TrigPin, LOW);       //用低高低电平发送脉冲给Trigpin引脚
  delayMicroseconds(2);             //微秒级延时
  digitalWrite(TrigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(TrigPin, LOW);
  cm = pulseIn(EchoPin, HIGH) / 58.0; //读取脉冲宽度，换算成厘米
  Serial.print(cm);                   //显示距离
  Serial.print("cm");                   //显示单位
  Serial.println();                   //回车
  delay(1000);
 

}
