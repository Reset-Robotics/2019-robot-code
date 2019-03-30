#include "FAB_LED.h"
#include "resetLEDs.h"
#include "Arduino.h"

  // Creation of object
  resetLEDs exampleStrip(30);

void setup() {
}


void loop() {
  // Decleration of colors
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  grbw lightBlue;
  lightBlue.r = 73; lightBlue.g = 249; lightBlue.b = 252; lightBlue.w = 0;
  grbw white;
  white.r = 55; white.g = 55; white.b = 55; white.w = 0;

  // Calling the desired method
  exampleStrip.climb(50, 1, purple);  
  exampleStrip.climb(50, 1, white);  

  //exampleStrip.climb_clear(5000, 1, purple);  
  //exampleStrip.climb_clear(5000, 1, lightBlue);  
}

