#include "FAB_LED.h"
#include "resetLEDs.h"
#include "Arduino.h"

void setup() {
}

void loop() {
  // Decleration of colors
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;

  // Creation of object
  resetLEDs exampleStrip(12);

  // Calling the desired method
  exampleStrip.color_bounce(1000, 5, purple, purple, 6);  
}
