#include "FAB_LED.h"
#include "resetLEDs.h"
#include "Arduino.h"

  
grbw purple;  grbw reset_purple;  grbw white;  grbw black; uint8_t offsets[] = {0, 10, 20};



void setup() {
  // Decleration of colors and offset values
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  
  reset_purple.r = 70.5; reset_purple.g = 0; reset_purple.b = 85.5; reset_purple.w = 0;
  
  white.r = 10; white.g = 10; white.b = 10; white.w = 10;
  
  black.r = 0; black.g = 0; black.b = 0; black.w = 0;
}
resetLEDs LED_strip(12);



void loop() {
  // Decleration of colors and offset values
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  
  grbw reset_purple;
  reset_purple.r = 70.5; reset_purple.g = 0; reset_purple.b = 85.5; reset_purple.w = 0;
  
  grbw white;
  white.r = 10; white.g = 10; white.b = 10; white.w = 10;
  /*
  grbw black;
  black.r = 0; black.g = 0; black.b = 0; black.w = 0;
  */
  int input = 01;
  
  // Calling the desired method
  switch (input) {
  case 1:
    LED_strip.climb(100, 1, purple);
    LED_strip.climb(100, 1, white);
    break;
  case 2:
    LED_strip.climb(50, 1, purple);
    LED_strip.climb(50, 1, black);
    break;
  default:
    LED_strip.color_chase(100, 2, purple, white);
    //LED_strip.strip_breathe(30, purple, 0, 1, 0.025);
    break;
  }
  
  // Uncomment to clear the strip after each loop() iteration
  //LED_strip.clear();
}
