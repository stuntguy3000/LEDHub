#define REDPIN 5
#define GREENPIN 6
#define BLUEPIN 3

boolean readingBlue = false;
boolean readingRed = false;
boolean readingGreen = false;
int red, blue, green;
char serialChar;
String serialCharInput;

void setup() {
  pinMode(REDPIN, OUTPUT);
  pinMode(GREENPIN, OUTPUT);
  pinMode(BLUEPIN, OUTPUT);

  Serial.begin(256000);
}

void loop()
{
  if (Serial.available() == 0) {
    return;
  }

  while (Serial.available() > 0) {
    serialChar = Serial.read();
    if (serialChar >= '0' && serialChar <= '9') {
      serialCharInput += serialChar;
    } else if (serialChar == 'r' || serialChar == 'R') {
      readingRed = true;
    } else if (serialChar == 'g' || serialChar == 'G') {
      readingGreen = true;
    } else if (serialChar == 'b' || serialChar == 'B') {
      readingBlue = true;
    } else if (serialChar == ';') {
      int colourValue = serialCharInput.toInt();

      if (readingRed) {
        red = colourValue;
      } else if (readingGreen) {
        green = colourValue;
      } else if (readingBlue) {
        blue = colourValue;
      }

      readingRed = false;
      readingGreen = false;
      readingBlue = false;

      serialCharInput = "";
    }
  }

  analogWrite(BLUEPIN, blue);
  analogWrite(GREENPIN, green);
  analogWrite(REDPIN, red);
}
