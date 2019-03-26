#include "FAB_LED.h"
#include "resetLEDs0.h"
#include "resetLEDs1.h"
#include "resetLEDs2.h"
#include "resetLEDs3.h"
#include "resetLEDs4.h"
#include "resetLEDs5.h"
#include "resetLEDs6.h"
#include "resetLEDs7.h"
#include "Arduino.h"
//#include <Wire.h>

  
grbw purple;  grbw reset_purple;  grbw white;  grbw off; uint8_t offsets[] = {0, 10, 20};

resetLEDs0 LED_strip(36);
void setup() 
{
  //Wire.begin(63);

  // Decleration of colors and offset values
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  
  reset_purple.r = 70.5; reset_purple.g = 0; reset_purple.b = 85.5; reset_purple.w = 0;
  
  white.r = 10; white.g = 10; white.b = 10; white.w = 10;
  
  off.r = 0; off.g = 0; off.b = 0; off.w = 0;
}



void loop() 
{
  /*
  int input = 1;

  // I2C data from Rio
  Wire.requestFrom(2, 1);
  while(Wire.available())
  {
    char received = Wire.read();
    input = received;
  }

  for (int i = 0; i < 3; i++)
  {
    Wire.requestFrom(2, 1);
    while(Wire.available())
    { 
       char received = Wire.read();
       input |= (received << 8);
    }
  }
` */
  // Decleration of colors and offset values
  /*
  grbw purple;
  purple.r = 55; purple.g = 0; purple.b = 55; purple.w = 0;
  
  grbw reset_purple;
  reset_purple.r = 70.5; reset_purple.g = 0; reset_purple.b = 85.5; reset_purple.w = 0;
  
  grbw white;
  white.r = 10; white.g = 10; white.b = 10; white.w = 10;
  
  grbw off;
  off.r = 0; off.g = 0; off.b = 0; off.w = 0;
  
  
  // Calling the desired method
  switch (input) {
  case 1:
    LED_strip.climb(100, 1, purple);
    LED_strip.climb(100, 1, white);
    break;
  case 2:
    LED_strip.climb(50, 1, purple);
    LED_strip.climb(50, 1, off);
    break;
  case 3:
    LED_strip.color_flash(30, 3, purple, off);
  default:
    LED_strip.strip_breathe(30, purple, 0, 1, 0.025);  
    //LED_strip.strip_breathe(30, purple, 0, 1, 0.025);
    break;
    */
  }
  
  // Uncomment to clear the strip after each loop() iteration
  //LED_strip.clear();
}
