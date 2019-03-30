#include "FAB_LED.h"
#include "resetLEDs.h"
#include "Arduino.h"

// Creation of object
resetLEDs exampleStrip(12); //notice the decleration is now outside loop() - this means that the strip does not automatically reset colors upon finishing loop, allowing for this pattern

void setup() {
}

void loop() {
  // Decleration of colors
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  grbw green;
  green.r = 0; green.g = 175; green.b = 0; green.w = 0;

  // Calling the desired method (s)
  exampleStrip.climb(5000, 1, purple);
  exampleStrip.climb(5000, 1, green);  
}

