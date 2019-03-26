#include "Arduino.h"
#include "resetLEDs2.h"
#include "FAB_LED.h"

grbw*  pixels2;
double brightnessModifier2 = 1;
grbw Black2;

void setPixel(uint8_t pos, grbw color, double brightnessModifier2 = 1);

uint8_t _numPixels2; uint8_t _maxBrightness2;

sk6812<D, 2> LEDStrip2;

resetLEDs2::resetLEDs2(uint8_t numpixels2) {

  _numPixels2 = numpixels2;
  pixels2 = new grbw[_numPixels2];

  Black2.r = 0; Black2.b = 0; Black2.g = 0; Black2.w = 0;

  // This section uses a for loop to itterate through all pixels2 in the LED strip and it sets all of their color variables to 0. This clears the LED strip on the setup phase of the Arduino.
  for (uint8_t pos = 0; pos < _numPixels2; pos++) {

    pixels2[pos].g = 0;
    pixels2[pos].b = 0;
    pixels2[pos].r = 0;
    pixels2[pos].w = 0;
  }
  LEDStrip2.sendPixels(_numPixels2, pixels2); // Clears the LED strip by sending the new values (which we defined as all zeros above).
  //LEDStrip2.refresh(); // Hack: needed for apa102 to display last pixels2


  uint8_t offsets[] = {0, 10, 20};
}

// This function allows us to set certain pixels2 in the pixels2[] array. It sets the pixel at the number pos in the array and it sets it to the colors defined by each of the r, g, b, and w values defined in the other parameters. The function automatically accounts for the swapping necessary for the r and g values
void resetLEDs2::setPixelRGBW(uint8_t pos, uint8_t r, uint8_t g, uint8_t b, uint8_t w) {
  pixels2[pos].g = r;
  pixels2[pos].r = g;
  pixels2[pos].b = b;
  pixels2[pos].w = w;
}

// This function is similar to the function above; it allows us to set a specified pixel in the pixels2[] array. This function uses a grbw color variable in place of the individual r, g, b, and w values and sets the pixel at pos in the array to that color. (Note: grbw is a type declaration and you will have to declare your own grbw variable somewhere else in the script for colors to have meaning. This is a variable like everything else, words like "orange" or "purple" are not inherently understood by the library and must be defined explicity using the grbw type.)
void resetLEDs2::setPixel(uint8_t pos, grbw color, double brightnessModifier2 = 1) {
  pixels2[pos].g = color.r * brightnessModifier2;
  pixels2[pos].r = color.g * brightnessModifier2;
  pixels2[pos].b = color.b * brightnessModifier2;
  pixels2[pos].w = color.w * brightnessModifier2;
}

// This function allows us to set a whole strip of LEDs to a grbw color variable. We do not have to specify which LED like in previous functions as it will itterate through the strip and illuminate all of them to whatever grbw variable we pass in.
void resetLEDs2::setStrip(grbw color, double brightnessModifier2 = 1) {
  for (int i = 0; i < _numPixels2; i++) {
    setPixel(i, color, brightnessModifier2);
  }
}

// @brief Waits then clears the LED strip.
void resetLEDs2::holdAndClear(uint16_t on_time, uint16_t off_time) {
  delay(on_time); // waits for specified time in miliseconds
  LEDStrip2.clear(1000); // turns the LED strip on for 1000 miliseconds(1 second)
  delay(off_time); // waits for the specified time in miliseconds
}

// Four Colored LEDs running up the LED strip in a color of your choice.
void resetLEDs2::color_chase(uint8_t wait, uint8_t len, uint8_t offsets[], grbw main, grbw background) {
  setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
  LEDStrip2.sendPixels(_numPixels2, pixels2); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
  // Itterates through all LEDs in the strip by comparing against the _numPixels2 constant and set an offset pixel (or pixels2 in this case) to the main color.
  for (int led_number = -len; led_number < _numPixels2 + len - 1; led_number++) {
    for (int spot = 0; spot < len; spot++) {
      setPixel(offsets[0] + led_number + spot, main);
      setPixel(offsets[1] + led_number + spot, main);
    }
    setPixel(offsets[0] + led_number, background);
    setPixel(offsets[1] + led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
}

void resetLEDs2::color_chase(uint8_t wait, uint8_t len, uint8_t offsets[], grbw main) {
  setStrip(Black2); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
  LEDStrip2.sendPixels(_numPixels2, pixels2); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
  // Itterates through all LEDs in the strip by comparing against the _numPixels2 constant and set an offset pixel (or pixels2 in this case) to the main color.
  for (int led_number = -len; led_number < _numPixels2 + len - 1; led_number++) {
    for (int spot = 0; spot < len; spot++) {
      setPixel(offsets[0] + led_number + spot, main);
      setPixel(offsets[1] + led_number + spot, main);
    }
    setPixel(offsets[0] + led_number, Black2);
    setPixel(offsets[1] + led_number, Black2);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
}


void resetLEDs2::color_bounce(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  for (int led_number = -len; led_number < _numPixels2 + len - 1; led_number++) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
  for (int led_number = _numPixels2 + len - 1; led_number > -len - 1; led_number--) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
}


void resetLEDs2::split_color_bounce(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  // first half
  for (int led_number = -len/2; led_number < _numPixels2/2 - len; led_number++)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
  for (int led_number = _numPixels2/2; led_number > -len/2 - 1; led_number--)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }

  // second half
  for (int led_number = _numPixels2 + len; led_number > _numPixels2/2 + len; led_number--)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
  for (int led_number = _numPixels2/2; led_number < _numPixels2; led_number++)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
}


void resetLEDs2::color_tetris(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  LEDStrip2.sendPixels(_numPixels2, pixels2);
  for (int r = 0; r < _numPixels2; r += len) {
    for (int led_number = -len; led_number < _numPixels2 + len - r; led_number++) {
      for (int offset = 0; offset < len; offset++) {
        setPixel(led_number + offset, main);
      }
      setPixel(led_number - len, background);
      LEDStrip2.sendPixels(_numPixels2, pixels2);
      delay(wait);
    }
  }
}


void resetLEDs2::inverted_color_tetris(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  LEDStrip2.sendPixels(_numPixels2, pixels2);
  for (int r = 0; r > -_numPixels2; r += len) {
    for (int led_number = -len; led_number < _numPixels2 + len - r; led_number++) {
      for (int offset = 0; offset < len; offset++) {
        setPixel(-(led_number + offset), main, 0.1);
      }
      setPixel(-(led_number - len), background);
      LEDStrip2.sendPixels(_numPixels2, pixels2);
      delay(wait);
    }
  }
}


void resetLEDs2::strip_breathe(uint8_t wait, grbw color, double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    setStrip(color, i);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
    i = i + stepModifier;
  }
  for (double i = maxModifier; i > minModifier;)
  {
    setStrip(color, i);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
    i = i - stepModifier;
  }
}


void resetLEDs2::diffuser_breathe(uint8_t wait, grbw color, double minModifier, double maxModifier, double stepModifier, double strip1Length, double strip1Start, double strip2Length, double strip2Start, double strip3Length, double strip3Start) {
  for (double pixelNum = strip1Start; pixelNum < strip1Length;)
  {
    for (double breatheIncrement = minModifier; breatheIncrement < maxModifier;)
    {
      setPixel(pixelNum, color, breatheIncrement);
      LEDStrip2.sendPixels(_numPixels2, pixels2);
      delay(wait);
      breatheIncrement += stepModifier;
    }
    for (double breatheDecrement = maxModifier; breatheDecrement > minModifier;)
    {
      setPixel(pixelNum, color, breatheDecrement);
      LEDStrip2.sendPixels(_numPixels2, pixels2);
      delay(wait);
      breatheDecrement -= stepModifier;
    }

    pixelNum += 1;
  }
}


void resetLEDs2::pixel_breathe(uint8_t wait, uint8_t pixel, grbw color, double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    setPixel(pixel, color, i);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
    i = i + stepModifier;
  }
  for (double i = maxModifier; i > minModifier;)
  {
    setPixel(pixel, color, i);
    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
    i = i - stepModifier;
  }
}


void resetLEDs2::pixel_chase_breathe(uint8_t nextWait, uint8_t breatheWait, grbw color, grbw background, uint8_t len, uint8_t offsets[], double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    //setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
    LEDStrip2.sendPixels(_numPixels2, pixels2); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
    // Itterates through all LEDs in the strip by comparing against the _numPixels2 constant and set an offset pixel (or pixels2 in this case) to the main color.
    for (int led_number = -len; led_number < _numPixels2 + len - 1; led_number++)
    {
      for (int spot = 0; spot < len; spot++)
      {
        pixel_breathe(breatheWait, offsets[0] + led_number + spot, color, minModifier, maxModifier, stepModifier);
        //pixel_breathe(breatheWait, offsets[1] + led_number + spot, color, minModifier, maxModifier, stepModifier);
      }
      setPixel(offsets[0] + led_number, background);
      //setPixel(offsets[1] + led_number, background);
      LEDStrip2.sendPixels(_numPixels2, pixels2);
      delay(nextWait);
    }
  }
}


void resetLEDs2::color_chase_breathe(uint8_t wait, grbw color, grbw background, uint8_t len, uint8_t offsets[], double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    //setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
    LEDStrip2.sendPixels(_numPixels2, pixels2); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
    // Itterates through all LEDs in the strip by comparing against the _numPixels2 constant and set an offset pixel (or pixels2 in this case) to the main color.
    for (int led_number = -len; led_number < _numPixels2 + len - 1; led_number++)
    {
      for (int spot = 0; spot < len; spot++)
      {
        pixel_breathe(wait, offsets[0] + led_number + spot, color, minModifier, maxModifier, stepModifier);
      }
      setPixel(offsets[0] + led_number, background);
      LEDStrip2.sendPixels(_numPixels2, pixels2);
      delay(wait);
    }
  }
}


// ---------------------------------------------------------
// START OF NEW METHODS
// ---------------------------------------------------------

// This method will blink a given color numBlinks times at intervals of wait
void resetLEDs2::color_flash(uint8_t wait, uint8_t numBlinks, grbw color) {
	for(int i = 0; i < numBlinks; i++){
    LEDStrip2.clear(1000);
    delay(wait);
    setStrip(color);
		LEDStrip2.sendPixels(_numPixels2, pixels2);
		delay(wait);
	}
  LEDStrip2.clear(1000);
  delay(wait);
}


// This method will blink between 2 colors numBlinks times at intervals of wait
void resetLEDs2::color_flash(uint8_t wait, uint8_t numBlinks, grbw color, grbw background) {
	for(int i = 0; i < numBlinks; i++){
		setStrip(color);
		LEDStrip2.sendPixels(_numPixels2, pixels2);
		delay(wait);
		setStrip(background);
		LEDStrip2.sendPixels(_numPixels2, pixels2);
		delay(wait);
	}
}

// sets pixels2 to a color in a snake-like progression
void resetLEDs2::climb(uint8_t wait, uint8_t jumpSize, grbw color){
	for(int i = 0; i < _numPixels2; i += jumpSize){
		for(int j = i; j < i + jumpSize; j++){
			setPixel(j, color);
		}
		LEDStrip2.sendPixels(_numPixels2, pixels2);
		delay(wait);
	}
}

// sets pixels2 to a color in a snake-like progression, then clears the strip
void resetLEDs2::climb_clear(uint8_t wait, uint8_t jumpSize, grbw color){
  setStrip(Black2);
  LEDStrip2.sendPixels(_numPixels2, pixels2);
	for(int i = 0; i < _numPixels2; i += jumpSize){
		for(int j = i; j < i + jumpSize; j++){
			setPixel(j, color);
		}
		LEDStrip2.sendPixels(_numPixels2, pixels2);
		delay(wait);
	}
}

// climbs the strip in a color, then flashes in a 2nd color
void resetLEDs2::impact(uint8_t wait, uint8_t wait2, grbw climbColor, grbw flashColor) {
  setStrip(Black2);
  LEDStrip2.sendPixels(_numPixels2, pixels2);

  climb_clear(wait, 1, climbColor);
  color_flash(wait2, 3, flashColor);
}

// sends a snake out from the middle in both directions
void resetLEDs2::middle_out(int wait, grbw color){

  for(int i = 0; i < _numPixels2/2;i++){

    setPixel((_numPixels2/2 + i), color);

    setPixel((_numPixels2/2 - i), color);

    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
  setStrip(color);
  LEDStrip2.sendPixels(_numPixels2, pixels2);
  delay(wait);
}

// sends a snake towards the middle from eother end
void resetLEDs2::out_middle(int wait, grbw color){

  for(int i = 0; i < _numPixels2/2;i++){

    setPixel(_numPixels2 - i, color);

    setPixel(i, color);

    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);

  }
  setStrip(color);
  LEDStrip2.sendPixels(_numPixels2, pixels2);
  delay(wait);
}

// snakes from the middle to the outside, then retracts back to the middle
void resetLEDs2::middle_out_middle(int wait, grbw color) {
  middle_out(wait, color);
  out_middle(wait, Black2);
}

// snakes from the outsides to the middle, then retracts
void resetLEDs2::out_middle_out(int wait, grbw color) {
  out_middle(wait, color);
  middle_out(wait, Black2);
}

// snakes out from the middle, then clears from the middle
void resetLEDs2::climb_out(uint8_t wait, grbw color){
  middle_out(wait, color);
  middle_out(wait, Black2);
}

// snakes out from the middle, then clears from the middle
void resetLEDs2::climb_in(uint8_t wait, grbw color){
  out_middle(wait, color);
  out_middle(wait, Black2);
}

// sends groups outwards from the center
void resetLEDs2::color_chase_in(uint8_t wait, uint8_t len, grbw main){

  for (int led_number = -len; led_number < _numPixels2/2 - len + 1; led_number++) {

    for (int spot = 0; spot < len; spot++) {
      setPixel(led_number + spot, main);

      setPixel((_numPixels2 - led_number) - spot, main);
    }
    setPixel(led_number, Black2);
    setPixel(led_number, Black2);

    setPixel((_numPixels2 - led_number), Black2);
    setPixel((_numPixels2 - led_number), Black2);

    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }
  setStrip(Black2);
  LEDStrip2.sendPixels(_numPixels2, pixels2);
}

void resetLEDs2::color_bounce_in(uint8_t wait, uint8_t len, grbw main){
  setStrip(Black2);
  for (int led_number = 0; led_number < _numPixels2/2; led_number++) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(_numPixels2 - led_number - offset, main);
	  setPixel(led_number + offset, main);
    }
    setPixel(led_number, Black2);
	setPixel(_numPixels2 - led_number, Black2);

    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }

  for (int led_number = _numPixels2/2; led_number > len/2; led_number--) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number - offset, main);
	  setPixel(_numPixels2 -+led_number + offset, main);
    }
    setPixel(led_number, Black2);
	setPixel(_numPixels2 - led_number, Black2);


    LEDStrip2.sendPixels(_numPixels2, pixels2);
    delay(wait);
  }






}
