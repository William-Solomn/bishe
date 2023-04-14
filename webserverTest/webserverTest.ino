#include <ESP8266WiFiMulti.h>
#include <ESP8266WebServer.h>

ESP8266WiFiMulti wifiMulti;

ESP8266WebServer server(80);

const int ledPin = 2;
bool ledState = false;

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
  server.send(200, "text/plain", ledState ? "on" : "off");
}

void setup() {
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, LOW);

  Serial.begin(115200);

  wifiMulti.addAP("CMCC-pXm9-5G-FAST", "gf92pdxe");
  wifiMulti.addAP("abcd", "1234567889");
  wifiMulti.addAP("your-ssid-3", "your-password-3");

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
}
