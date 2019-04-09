// Reset Robotics 2019
#include "Arduino.h"
#include "resetLEDs3.h"
#include "FAB_LED.h"

grbw*  pixels3;
double brightnessModifier3 = 1;
grbw Black3;

void setPixel(uint8_t pos, grbw color, double brightnessModifier3 = 1);

uint8_t _numPixels3; uint8_t _maxBrightness3;

sk6812<D, 3> LEDStrip3;

resetLEDs3::resetLEDs3(uint8_t numpixels3) {

  _numPixels3 = numpixels3;
  pixels3 = new grbw[_numPixels3];

  Black3.r = 0; Black3.b = 0; Black3.g = 0; Black3.w = 0;

  // This section uses a for loop to itterate through all pixels3 in the LED strip and it sets all of their color variables to 0. This clears the LED strip on the setup phase of the Arduino.
  for (uint8_t pos = 0; pos < _numPixels3; pos++) {

    pixels3[pos].g = 0;
    pixels3[pos].b = 0;
    pixels3[pos].r = 0;
    pixels3[pos].w = 0;
  }
  LEDStrip3.sendPixels(_numPixels3, pixels3); // Clears the LED strip by sending the new values (which we defined as all zeros above).
  //LEDStrip3.refresh(); // Hack: needed for apa102 to display last pixels3


  uint8_t offsets[] = {0, 10, 20};
}

// This function allows us to set certain pixels3 in the pixels3[] array. It sets the pixel at the number pos in the array and it sets it to the colors defined by each of the r, g, b, and w values defined in the other parameters. The function automatically accounts for the swapping necessary for the r and g values
void resetLEDs3::setPixelRGBW(uint8_t pos, uint8_t r, uint8_t g, uint8_t b, uint8_t w) {
  pixels3[pos].g = r;
  pixels3[pos].r = g;
  pixels3[pos].b = b;
  pixels3[pos].w = w;
}

// This function is similar to the function above; it allows us to set a specified pixel in the pixels3[] array. This function uses a grbw color variable in place of the individual r, g, b, and w values and sets the pixel at pos in the array to that color. (Note: grbw is a type declaration and you will have to declare your own grbw variable somewhere else in the script for colors to have meaning. This is a variable like everything else, words like "orange" or "purple" are not inherently understood by the library and must be defined explicity using the grbw type.)
void resetLEDs3::setPixel(uint8_t pos, grbw color, double brightnessModifier3 = 1) {
  pixels3[pos].g = color.r * brightnessModifier3;
  pixels3[pos].r = color.g * brightnessModifier3;
  pixels3[pos].b = color.b * brightnessModifier3;
  pixels3[pos].w = color.w * brightnessModifier3;
}

// This function allows us to set a whole strip of LEDs to a grbw color variable. We do not have to specify which LED like in previous functions as it will itterate through the strip and illuminate all of them to whatever grbw variable we pass in.
void resetLEDs3::setStrip(grbw color, double brightnessModifier3 = 1) {
  for (int i = 0; i < _numPixels3; i++) {
    setPixel(i, color, brightnessModifier3);
  }
}

// @brief Waits then clears the LED strip.
void resetLEDs3::holdAndClear(uint16_t on_time, uint16_t off_time) {
  delay(on_time); // waits for specified time in miliseconds
  LEDStrip3.clear(1000); // turns the LED strip on for 1000 miliseconds(1 second)
  delay(off_time); // waits for the specified time in miliseconds
}

// Four Colored LEDs running up the LED strip in a color of your choice.
void resetLEDs3::color_chase(uint8_t wait, uint8_t len, uint8_t groups, uint8_t distance, grbw main, grbw Background) {
  setStrip(Background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
  LEDStrip3.sendPixels(_numPixels3, pixels3); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
  // Itterates through all LEDs in the strip by comparing against the _numPixels3 constant and set an offset pixel (or pixels3 in this case) to the main color.
  for (int led_number = -len - groups * distance; led_number < _numPixels3 + len - 1; led_number++) {
    for (int spot = 0; spot < len; spot++) {
      for(int group = 0; group < groups * distance; group += distance){
        setPixel(group + led_number + spot, main);
      }
    }
    for(int group = groups * distance; group >= 0; group -= distance) {
      setPixel(group + led_number, Background);
    }
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
}

void resetLEDs3::color_chase(uint8_t wait, uint8_t len, uint8_t offsets[], grbw main) {
  setStrip(Black3); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
  LEDStrip3.sendPixels(_numPixels3, pixels3); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
  // Itterates through all LEDs in the strip by comparing against the _numPixels3 constant and set an offset pixel (or pixels3 in this case) to the main color.
  for (int led_number = -len; led_number < _numPixels3 + len - 1; led_number++) {
    for (int spot = 0; spot < len; spot++) {
      setPixel(offsets[0] + led_number + spot, main);
      setPixel(offsets[1] + led_number + spot, main);
    }
    setPixel(offsets[0] + led_number, Black3);
    setPixel(offsets[1] + led_number, Black3);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
}


void resetLEDs3::color_bounce(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  for (int led_number = -len; led_number < _numPixels3 + len - 1; led_number++) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
  for (int led_number = _numPixels3 + len - 1; led_number > -len - 1; led_number--) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
}


void resetLEDs3::split_color_bounce(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  // first half
  for (int led_number = -len/2; led_number < _numPixels3/2 - len; led_number++)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
  for (int led_number = _numPixels3/2; led_number > -len/2 - 1; led_number--)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }

  // second half
  for (int led_number = _numPixels3 + len; led_number > _numPixels3/2 + len; led_number--)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
  for (int led_number = _numPixels3/2; led_number < _numPixels3; led_number++)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
}


void resetLEDs3::color_tetris(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  LEDStrip3.sendPixels(_numPixels3, pixels3);
  for (int r = 0; r < _numPixels3; r += len) {
    for (int led_number = -len; led_number < _numPixels3 + len - r; led_number++) {
      for (int offset = 0; offset < len; offset++) {
        setPixel(led_number + offset, main);
      }
      setPixel(led_number - len, background);
      LEDStrip3.sendPixels(_numPixels3, pixels3);
      delay(wait);
    }
  }
}


void resetLEDs3::inverted_color_tetris(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  LEDStrip3.sendPixels(_numPixels3, pixels3);
  for (int r = 0; r > -_numPixels3; r += len) {
    for (int led_number = -len; led_number < _numPixels3 + len - r; led_number++) {
      for (int offset = 0; offset < len; offset++) {
        setPixel(-(led_number + offset), main, 0.1);
      }
      setPixel(-(led_number - len), background);
      LEDStrip3.sendPixels(_numPixels3, pixels3);
      delay(wait);
    }
  }
}


void resetLEDs3::strip_breathe(uint8_t wait, grbw color, double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    setStrip(color, i);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
    i = i + stepModifier;
  }
  for (double i = maxModifier; i > minModifier;)
  {
    setStrip(color, i);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
    i = i - stepModifier;
  }
}


void resetLEDs3::diffuser_breathe(uint8_t wait, grbw color, double minModifier, double maxModifier, double stepModifier, double strip1Length, double strip1Start, double strip2Length, double strip2Start, double strip3Length, double strip3Start) {
  for (double pixelNum = strip1Start; pixelNum < strip1Length;)
  {
    for (double breatheIncrement = minModifier; breatheIncrement < maxModifier;)
    {
      setPixel(pixelNum, color, breatheIncrement);
      LEDStrip3.sendPixels(_numPixels3, pixels3);
      delay(wait);
      breatheIncrement += stepModifier;
    }
    for (double breatheDecrement = maxModifier; breatheDecrement > minModifier;)
    {
      setPixel(pixelNum, color, breatheDecrement);
      LEDStrip3.sendPixels(_numPixels3, pixels3);
      delay(wait);
      breatheDecrement -= stepModifier;
    }

    pixelNum += 1;
  }
}


void resetLEDs3::pixel_breathe(uint8_t wait, uint8_t pixel, grbw color, double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    setPixel(pixel, color, i);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
    i = i + stepModifier;
  }
  for (double i = maxModifier; i > minModifier;)
  {
    setPixel(pixel, color, i);
    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
    i = i - stepModifier;
  }
}


void resetLEDs3::pixel_chase_breathe(uint8_t nextWait, uint8_t breatheWait, grbw color, grbw background, uint8_t len, uint8_t offsets[], double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    //setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
    LEDStrip3.sendPixels(_numPixels3, pixels3); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
    // Itterates through all LEDs in the strip by comparing against the _numPixels3 constant and set an offset pixel (or pixels3 in this case) to the main color.
    for (int led_number = -len; led_number < _numPixels3 + len - 1; led_number++)
    {
      for (int spot = 0; spot < len; spot++)
      {
        pixel_breathe(breatheWait, offsets[0] + led_number + spot, color, minModifier, maxModifier, stepModifier);
        //pixel_breathe(breatheWait, offsets[1] + led_number + spot, color, minModifier, maxModifier, stepModifier);
      }
      setPixel(offsets[0] + led_number, background);
      //setPixel(offsets[1] + led_number, background);
      LEDStrip3.sendPixels(_numPixels3, pixels3);
      delay(nextWait);
    }
  }
}


void resetLEDs3::color_chase_breathe(uint8_t wait, grbw color, grbw background, uint8_t len, uint8_t offsets[], double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    //setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
    LEDStrip3.sendPixels(_numPixels3, pixels3); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
    // Itterates through all LEDs in the strip by comparing against the _numPixels3 constant and set an offset pixel (or pixels3 in this case) to the main color.
    for (int led_number = -len; led_number < _numPixels3 + len - 1; led_number++)
    {
      for (int spot = 0; spot < len; spot++)
      {
        pixel_breathe(wait, offsets[0] + led_number + spot, color, minModifier, maxModifier, stepModifier);
      }
      setPixel(offsets[0] + led_number, background);
      LEDStrip3.sendPixels(_numPixels3, pixels3);
      delay(wait);
    }
  }
}


// ---------------------------------------------------------
// START OF NEW METHODS
// ---------------------------------------------------------

// This method will blink a given color numBlinks times at intervals of wait
void resetLEDs3::color_flash(uint8_t wait, uint8_t numBlinks, grbw color) {
	for(int i = 0; i < numBlinks; i++){
    LEDStrip3.clear(1000);
    delay(wait);
    setStrip(color);
		LEDStrip3.sendPixels(_numPixels3, pixels3);
		delay(wait);
	}
  LEDStrip3.clear(1000);
  delay(wait);
}


// This method will blink between 2 colors numBlinks times at intervals of wait
void resetLEDs3::color_flash(uint8_t wait, uint8_t numBlinks, grbw color, grbw background) {
	for(int i = 0; i < numBlinks; i++){
		setStrip(color);
		LEDStrip3.sendPixels(_numPixels3, pixels3);
		delay(wait);
		setStrip(background);
		LEDStrip3.sendPixels(_numPixels3, pixels3);
		delay(wait);
	}
}

// sets pixels3 to a color in a snake-like progression
void resetLEDs3::climb(uint8_t wait, uint8_t jumpSize, grbw color){
	for(int i = 0; i < _numPixels3; i += jumpSize){
		for(int j = i; j < i + jumpSize; j++){
			setPixel(j, color);
		}
		LEDStrip3.sendPixels(_numPixels3, pixels3);
		delay(wait);
	}
}

// sets pixels3 to a color in a snake-like progression, then clears the strip
void resetLEDs3::climb_clear(uint8_t wait, uint8_t jumpSize, grbw color){
  setStrip(Black3);
  LEDStrip3.sendPixels(_numPixels3, pixels3);
	for(int i = 0; i < _numPixels3; i += jumpSize){
		for(int j = i; j < i + jumpSize; j++){
			setPixel(j, color);
		}
		LEDStrip3.sendPixels(_numPixels3, pixels3);
		delay(wait);
	}
}

// climbs the strip in a color, then flashes in a 2nd color
void resetLEDs3::impact(uint8_t wait, uint8_t wait2, grbw climbColor, grbw flashColor) {
  setStrip(Black3);
  LEDStrip3.sendPixels(_numPixels3, pixels3);

  climb_clear(wait, 1, climbColor);
  color_flash(wait2, 3, flashColor);
}

// sends a snake out from the middle in both directions
void resetLEDs3::middle_out(int wait, grbw color){

  for(int i = 0; i < _numPixels3/2;i++){

    setPixel((_numPixels3/2 + i), color);

    setPixel((_numPixels3/2 - i), color);

    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
  setStrip(color);
  LEDStrip3.sendPixels(_numPixels3, pixels3);
  delay(wait);
}

// sends a snake towards the middle from eother end
void resetLEDs3::out_middle(int wait, grbw color){

  for(int i = 0; i < _numPixels3/2;i++){

    setPixel(_numPixels3 - i, color);

    setPixel(i, color);

    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);

  }
  setStrip(color);
  LEDStrip3.sendPixels(_numPixels3, pixels3);
  delay(wait);
}

// snakes from the middle to the outside, then retracts back to the middle
void resetLEDs3::middle_out_middle(int wait, grbw color) {
  middle_out(wait, color);
  out_middle(wait, Black3);
}

// snakes from the outsides to the middle, then retracts
void resetLEDs3::out_middle_out(int wait, grbw color) {
  out_middle(wait, color);
  middle_out(wait, Black3);
}

// snakes out from the middle, then clears from the middle
void resetLEDs3::climb_out(uint8_t wait, grbw color){
  middle_out(wait, color);
  middle_out(wait, Black3);
}

// snakes out from the middle, then clears from the middle
void resetLEDs3::climb_in(uint8_t wait, grbw color){
  out_middle(wait, color);
  out_middle(wait, Black3);
}

// sends groups outwards from the center
void resetLEDs3::color_chase_in(uint8_t wait, uint8_t len, grbw main){

  for (int led_number = -len; led_number < _numPixels3/2 - len + 1; led_number++) {

    for (int spot = 0; spot < len; spot++) {
      setPixel(led_number + spot, main);

      setPixel((_numPixels3 - led_number) - spot, main);
    }
    setPixel(led_number, Black3);
    setPixel(led_number, Black3);

    setPixel((_numPixels3 - led_number), Black3);
    setPixel((_numPixels3 - led_number), Black3);

    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }
  setStrip(Black3);
  LEDStrip3.sendPixels(_numPixels3, pixels3);
}

void resetLEDs3::color_bounce_in(uint8_t wait, uint8_t len, grbw main){
  setStrip(Black3);
  for (int led_number = 0; led_number < _numPixels3/2; led_number++) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(_numPixels3 - led_number - offset, main);
	  setPixel(led_number + offset, main);
    }
    setPixel(led_number, Black3);
	setPixel(_numPixels3 - led_number, Black3);

    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }

  for (int led_number = _numPixels3/2; led_number > len/2; led_number--) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number - offset, main);
	  setPixel(_numPixels3 -+led_number + offset, main);
    }
    setPixel(led_number, Black3);
	setPixel(_numPixels3 - led_number, Black3);


    LEDStrip3.sendPixels(_numPixels3, pixels3);
    delay(wait);
  }






}
