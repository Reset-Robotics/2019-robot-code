// Reset Robotics 2019
#include "FAB_LED.h"
#include "resetLEDs6.h"
#include "Arduino.h"

void setup() 
{
}

void loop() 
{
  // Decleration of colors
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;

  // Creation of object
  resetLEDs6 exampleStrip(30);

  // Calling the desired method
  exampleStrip.strip_breathe(30, purple, 0, 1, 0.025);  
}
