/* 
 *  This is the LED control library used for Reset Robotics 2019 season
 *  Created by:
 *  Philip D.
 *  Bryce L.
 *  On 1/17/19
 */

#ifndef 2019ResetLEDs.h
#define 2019ResetLEDs.h

#include "Arduino.h"
#include "FAB_LED.h" // includes the FAB_LED library in this file so we can use its library functions

Class 2019ResetLEDs {
  public:
    2019ResetLEDs(uint8_t Number_Of_LEDs_In_Strip, uint8_t Max_LED_Brightness);
    void setPixelRGBW(uint8_t Position_Of_Pixel, uint8_t Red, uint8_t Green, uint8_t Blue, uint8_t White);
    void setPixel(uint8_t Position_Of_Pixel, grbw Color, double Brightness_Modifier = 1);
    void setStrip(grbw Color, double brightnessModifier = 1);
    void holdAndClear(uint16_t on_time, uint16_t off_time);
    void color_chase(uint8_t Wait, uint8_t Legnth, uint8_t offsets[], grbw Main, grbw Background);
    void color_bounce(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);
    void split_color_bounce(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);
    void color_tetris(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);
    void inverted_color_tetris(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);
    void strip_breathe(uint8_t Wait, grbw Color, double Min_Modifier, double Max_Modifier, double Step_Modifier);
    void diffuser_breathe(uint8_t Wait, grbw Color, double Min_Modifier, double Max_Modifier, double Step_Modifier, double strip1Length, double Strip_1_Start, double Strip_2_Length, double Strip_2_Start, double Strip_3_Length, double Strip_3_Start);
    void pixel_breathe(uint8_t Wait, uint8_t pixel, grbw Color, double Min_Modifier, double Max_Modifier, double Step_Modifier);
    void pixel_chase_breathe(uint8_t Next_Wait, uint8_t Breathe_Wait, grbw Color, grbw Background, uint8_t Legnth, uint8_t offsets[], double Min_Modifier, double Max_Modifier, double Step_Modifier);
    void color_chase_breathe(uint8_t Wait, grbw Color, grbw Background, uint8_t Legnth, uint8_t offsets[], double Min_Modifier, double Max_Modifier, double Step_Modifier);   
}

#endif