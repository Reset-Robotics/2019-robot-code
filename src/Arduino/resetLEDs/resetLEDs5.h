/*
 *  This is the LED control library used for Reset Robotics 2019 season
 *  Created by:
 *  Philip D.
 *  Bryce L.
 *  On 1/17/19
 */



#include "Arduino.h"
#include "FAB_LED.h" // includes the FAB_LED library in this file so we can use its library functions

class resetLEDs5 {
  public:

    resetLEDs5(uint8_t Number_Of_LEDs_In_Strip);
	// sk6812<D, 6> choosePort(uint8_t portNumber);

	// Old methods - from the original robot code
    void setPixelRGBW(uint8_t Position_Of_Pixel, uint8_t Red, uint8_t Green, uint8_t Blue, uint8_t White);
    void setPixel(uint8_t Position_Of_Pixel, grbw Color, double brightnessModifier = 1);
    void setStrip(grbw Color, double brightnessModifier = 1);
    void holdAndClear(uint16_t on_time, uint16_t off_time);

	void color_chase(uint8_t Wait, uint8_t Legnth, uint8_t groups, uint8_t distance, grbw Main, grbw Background);
    void color_chase(uint8_t Wait, uint8_t Legnth, uint8_t offsets[], grbw Main);

  	void color_bounce(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);
    void split_color_bounce(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);

  	void color_tetris(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);
    void inverted_color_tetris(uint8_t Wait, uint8_t Legnth, grbw Main, grbw Background);

  	void strip_breathe(uint8_t Wait, grbw Color, double Min_Modifier, double Max_Modifier, double Step_Modifier);
    void diffuser_breathe(uint8_t Wait, grbw Color, double Min_Modifier, double Max_Modifier, double Step_Modifier, double strip1Length, double Strip_1_Start, double Strip_2_Length, double Strip_2_Start, double Strip_3_Length, double Strip_3_Start);
    void pixel_breathe(uint8_t Wait, uint8_t pixel, grbw Color, double Min_Modifier, double Max_Modifier, double Step_Modifier);

  	void pixel_chase_breathe(uint8_t Next_Wait, uint8_t Breathe_Wait, grbw Color, grbw Background, uint8_t Legnth, uint8_t offsets[], double Min_Modifier, double Max_Modifier, double Step_Modifier);
    void color_chase_breathe(uint8_t Wait, grbw Color, grbw Background, uint8_t Legnth, uint8_t offsets[], double Min_Modifier, double Max_Modifier, double Step_Modifier);

  	//new methods - written specifically for 2019 robot
  	void color_flash(uint8_t wait, uint8_t numBlinks, grbw Color, grbw Background);
  	void color_flash(uint8_t wait, uint8_t numBlinks, grbw color);

  	void climb(uint8_t wait, uint8_t jumpSize, grbw color);
    void climb_clear(uint8_t wait, uint8_t jumpSize, grbw color);
    void climb_in(uint8_t wait, grbw color);
    void climb_out(uint8_t wait, grbw color);

    void impact(uint8_t climbWait, uint8_t flashWait, grbw climbColor, grbw flashColor);

    void middle_out(int wait, grbw color);
    void out_middle(int wait, grbw color);
    void middle_out_middle(int wait, grbw color);
    void out_middle_out(int wait, grbw color);

    void color_chase_in(uint8_t wait, uint8_t len, grbw color);
	void color_bounce_in(uint8_t wait, uint8_t len, grbw main);
};
