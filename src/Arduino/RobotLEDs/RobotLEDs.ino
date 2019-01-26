#include "FAB_LED.h"
#include "resetLEDs.h"
#include "Arduino.h"

void setup() {
}

void loop() {
  // Decleration of colors and offset values
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  grbw reset_purple;
  reset_purple.r = 70.5; reset_purple.g = 0; reset_purple.b = 85.5; reset_purple.w = 0;
  grbw black;
  black.r = 0; black.g = 0; black.b = 0; black.w = 0;
  
  uint8_t offsets[] = {0, 10, 20};


  // Creation of object
  resetLEDs LED_strip(12);

  // Calling the desired method
  LED_strip.strip_breathe(30, purple, 0, 1, 0.025);  
}
