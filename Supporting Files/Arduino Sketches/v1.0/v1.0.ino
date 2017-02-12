#define REDPIN 5
#define GREENPIN 6
#define BLUEPIN 3

boolean readingBlue = false;
boolean readingRed = false;
boolean readingGreen = false;
boolean readingSpecial = false;
int red, blue, green;
char serialChar;
String serialCharInput;

void setup() {
  pinMode(REDPIN, OUTPUT);
  pinMode(GREENPIN, OUTPUT);
  pinMode(BLUEPIN, OUTPUT);

  Serial.begin(256000);
}

void loop() {
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
    } else if (serialChar == 's' || serialChar == 'S') {
      readingSpecial = true;
    } else if (serialChar == ';') {
      int colourValue = serialCharInput.toInt();

      if (readingRed) {
        red = colourValue;
      } else if (readingGreen) {
        green = colourValue;
      } else if (readingBlue) {
        blue = colourValue;
      } else if (readingSpecial) {
        if (serialCharInput == "1") {
          // HiveMC BlockParty
          lightsFade(255, 255, 0, 0, 0, 0, 1000);
          lightsFade(255, 69, 0, 0, 0, 0, 1000);
          lightsFade(255, 255, 0, 0, 0, 0, 1000);
          lightsFade(255, 69, 0, 0, 0, 0, 1000);
          lightsFade(255, 255, 0, 0, 0, 0, 1000);
          lightsFade(255, 69, 0, 0, 0, 0, 1000);
          lightsSet(255, 0, 0, 1000);
          lightsSet(255, 69, 0, 1000);
          lightsSet(255, 255, 0, 1000);
          lightsSet(0, 255, 0, 5000);
          lightsFade(0, 255, 0, 0, 0, 0, 5000);
        }
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

void lightsFade(int r1, int g1, int b1, int r2, int g2, int b2, int totalTime) {
  float differenceR = r2 - r1;
  float differenceG = g2 - g1;
  float differenceB = b2 - b1;

  float updateAmountR = 0, updateAmountG = 0, updateAmountB = 0;

  if (differenceR != 0) {
    updateAmountR = differenceR / totalTime;
  }

  if (differenceG != 0) {
    updateAmountG = differenceG / totalTime;
  }

  if (differenceB != 0) {
    updateAmountB = differenceB / totalTime;
  }

  float r = r1, g = g1, b = b1;
  for (int i = 0; i < totalTime; i++) {
    r += updateAmountR;
    g += updateAmountG;
    b += updateAmountB;

    analogWrite(BLUEPIN, b);
    analogWrite(GREENPIN, g);
    analogWrite(REDPIN, r);
            
    delay(1);
  }
}

void lightsSet(int r1, int g1, int b1, int delayTime) {
  analogWrite(BLUEPIN, b1);
  analogWrite(GREENPIN, g1);
  analogWrite(REDPIN, r1);
  delay(delayTime);
}


