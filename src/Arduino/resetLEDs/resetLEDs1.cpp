#include "Arduino.h"
#include "resetLEDs1.h"
#include "FAB_LED.h"

grbw*  pixels1;
double brightnessModifier1 = 1;
grbw Black1;

void setPixel(uint8_t pos, grbw color, double brightnessModifier1 = 1);

uint8_t _numPixels1; uint8_t _maxBrightness1;

sk6812<D, 1> LEDStrip1;

resetLEDs1::resetLEDs1(uint8_t numpixels1) {

  _numPixels1 = numpixels1;
  pixels1 = new grbw[_numPixels1];

  Black1.r = 0; Black1.b = 0; Black1.g = 0; Black1.w = 0;

  // This section uses a for loop to itterate through all pixels1 in the LED strip and it sets all of their color variables to 0. This clears the LED strip on the setup phase of the Arduino.
  for (uint8_t pos = 0; pos < _numPixels1; pos++) {

    pixels1[pos].g = 0;
    pixels1[pos].b = 0;
    pixels1[pos].r = 0;
    pixels1[pos].w = 0;
  }
  LEDStrip1.sendPixels(_numPixels1, pixels1); // Clears the LED strip by sending the new values (which we defined as all zeros above).
  //LEDStrip1.refresh(); // Hack: needed for apa102 to display last pixels1


  uint8_t offsets[] = {0, 10, 20};
}

// This function allows us to set certain pixels1 in the pixels1[] array. It sets the pixel at the number pos in the array and it sets it to the colors defined by each of the r, g, b, and w values defined in the other parameters. The function automatically accounts for the swapping necessary for the r and g values
void resetLEDs1::setPixelRGBW(uint8_t pos, uint8_t r, uint8_t g, uint8_t b, uint8_t w) {
  pixels1[pos].g = r;
  pixels1[pos].r = g;
  pixels1[pos].b = b;
  pixels1[pos].w = w;
}

// This function is similar to the function above; it allows us to set a specified pixel in the pixels1[] array. This function uses a grbw color variable in place of the individual r, g, b, and w values and sets the pixel at pos in the array to that color. (Note: grbw is a type declaration and you will have to declare your own grbw variable somewhere else in the script for colors to have meaning. This is a variable like everything else, words like "orange" or "purple" are not inherently understood by the library and must be defined explicity using the grbw type.)
void resetLEDs1::setPixel(uint8_t pos, grbw color, double brightnessModifier1 = 1) {
  pixels1[pos].g = color.r * brightnessModifier1;
  pixels1[pos].r = color.g * brightnessModifier1;
  pixels1[pos].b = color.b * brightnessModifier1;
  pixels1[pos].w = color.w * brightnessModifier1;
}

// This function allows us to set a whole strip of LEDs to a grbw color variable. We do not have to specify which LED like in previous functions as it will itterate through the strip and illuminate all of them to whatever grbw variable we pass in.
void resetLEDs1::setStrip(grbw color, double brightnessModifier1 = 1) {
  for (int i = 0; i < _numPixels1; i++) {
    setPixel(i, color, brightnessModifier1);
  }
}

// @brief Waits then clears the LED strip.
void resetLEDs1::holdAndClear(uint16_t on_time, uint16_t off_time) {
  delay(on_time); // waits for specified time in miliseconds
  LEDStrip1.clear(1000); // turns the LED strip on for 1000 miliseconds(1 second)
  delay(off_time); // waits for the specified time in miliseconds
}

// Four Colored LEDs running up the LED strip in a color of your choice.
void resetLEDs1::color_chase(uint8_t wait, uint8_t len, uint8_t offsets[], grbw main, grbw background) {
  setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
  LEDStrip1.sendPixels(_numPixels1, pixels1); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
  // Itterates through all LEDs in the strip by comparing against the _numPixels1 constant and set an offset pixel (or pixels1 in this case) to the main color.
  for (int led_number = -len; led_number < _numPixels1 + len - 1; led_number++) {
    for (int spot = 0; spot < len; spot++) {
      setPixel(offsets[0] + led_number + spot, main);
      setPixel(offsets[1] + led_number + spot, main);
    }
    setPixel(offsets[0] + led_number, background);
    setPixel(offsets[1] + led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
}

void resetLEDs1::color_chase(uint8_t wait, uint8_t len, uint8_t offsets[], grbw main) {
  setStrip(Black1); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
  LEDStrip1.sendPixels(_numPixels1, pixels1); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
  // Itterates through all LEDs in the strip by comparing against the _numPixels1 constant and set an offset pixel (or pixels1 in this case) to the main color.
  for (int led_number = -len; led_number < _numPixels1 + len - 1; led_number++) {
    for (int spot = 0; spot < len; spot++) {
      setPixel(offsets[0] + led_number + spot, main);
      setPixel(offsets[1] + led_number + spot, main);
    }
    setPixel(offsets[0] + led_number, Black1);
    setPixel(offsets[1] + led_number, Black1);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
}


void resetLEDs1::color_bounce(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  for (int led_number = -len; led_number < _numPixels1 + len - 1; led_number++) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
  for (int led_number = _numPixels1 + len - 1; led_number > -len - 1; led_number--) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
}


void resetLEDs1::split_color_bounce(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  // first half
  for (int led_number = -len/2; led_number < _numPixels1/2 - len; led_number++)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
  for (int led_number = _numPixels1/2; led_number > -len/2 - 1; led_number--)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }

  // second half
  for (int led_number = _numPixels1 + len; led_number > _numPixels1/2 + len; led_number--)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number - offset, main);
    }
    setPixel(led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
  for (int led_number = _numPixels1/2; led_number < _numPixels1; led_number++)
  {
    for (int offset = 0; offset < len; offset++)
    {
      setPixel(led_number + offset, main);
    }
    setPixel(led_number, background);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
}


void resetLEDs1::color_tetris(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  LEDStrip1.sendPixels(_numPixels1, pixels1);
  for (int r = 0; r < _numPixels1; r += len) {
    for (int led_number = -len; led_number < _numPixels1 + len - r; led_number++) {
      for (int offset = 0; offset < len; offset++) {
        setPixel(led_number + offset, main);
      }
      setPixel(led_number - len, background);
      LEDStrip1.sendPixels(_numPixels1, pixels1);
      delay(wait);
    }
  }
}


void resetLEDs1::inverted_color_tetris(uint8_t wait, uint8_t len, grbw main, grbw background) {
  setStrip(background);
  LEDStrip1.sendPixels(_numPixels1, pixels1);
  for (int r = 0; r > -_numPixels1; r += len) {
    for (int led_number = -len; led_number < _numPixels1 + len - r; led_number++) {
      for (int offset = 0; offset < len; offset++) {
        setPixel(-(led_number + offset), main, 0.1);
      }
      setPixel(-(led_number - len), background);
      LEDStrip1.sendPixels(_numPixels1, pixels1);
      delay(wait);
    }
  }
}


void resetLEDs1::strip_breathe(uint8_t wait, grbw color, double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    setStrip(color, i);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
    i = i + stepModifier;
  }
  for (double i = maxModifier; i > minModifier;)
  {
    setStrip(color, i);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
    i = i - stepModifier;
  }
}


void resetLEDs1::diffuser_breathe(uint8_t wait, grbw color, double minModifier, double maxModifier, double stepModifier, double strip1Length, double strip1Start, double strip2Length, double strip2Start, double strip3Length, double strip3Start) {
  for (double pixelNum = strip1Start; pixelNum < strip1Length;)
  {
    for (double breatheIncrement = minModifier; breatheIncrement < maxModifier;)
    {
      setPixel(pixelNum, color, breatheIncrement);
      LEDStrip1.sendPixels(_numPixels1, pixels1);
      delay(wait);
      breatheIncrement += stepModifier;
    }
    for (double breatheDecrement = maxModifier; breatheDecrement > minModifier;)
    {
      setPixel(pixelNum, color, breatheDecrement);
      LEDStrip1.sendPixels(_numPixels1, pixels1);
      delay(wait);
      breatheDecrement -= stepModifier;
    }

    pixelNum += 1;
  }
}


void resetLEDs1::pixel_breathe(uint8_t wait, uint8_t pixel, grbw color, double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    setPixel(pixel, color, i);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
    i = i + stepModifier;
  }
  for (double i = maxModifier; i > minModifier;)
  {
    setPixel(pixel, color, i);
    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
    i = i - stepModifier;
  }
}


void resetLEDs1::pixel_chase_breathe(uint8_t nextWait, uint8_t breatheWait, grbw color, grbw background, uint8_t len, uint8_t offsets[], double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    //setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
    LEDStrip1.sendPixels(_numPixels1, pixels1); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
    // Itterates through all LEDs in the strip by comparing against the _numPixels1 constant and set an offset pixel (or pixels1 in this case) to the main color.
    for (int led_number = -len; led_number < _numPixels1 + len - 1; led_number++)
    {
      for (int spot = 0; spot < len; spot++)
      {
        pixel_breathe(breatheWait, offsets[0] + led_number + spot, color, minModifier, maxModifier, stepModifier);
        //pixel_breathe(breatheWait, offsets[1] + led_number + spot, color, minModifier, maxModifier, stepModifier);
      }
      setPixel(offsets[0] + led_number, background);
      //setPixel(offsets[1] + led_number, background);
      LEDStrip1.sendPixels(_numPixels1, pixels1);
      delay(nextWait);
    }
  }
}


void resetLEDs1::color_chase_breathe(uint8_t wait, grbw color, grbw background, uint8_t len, uint8_t offsets[], double minModifier, double maxModifier, double stepModifier) {
  for (double i = minModifier; i < maxModifier;)
  {
    //setStrip(background); // uses the setStrip method we defined earlier in the code to set the whole LED strip to a background color stored in the variable 'background'
    LEDStrip1.sendPixels(_numPixels1, pixels1); // sends the setStrip method to the LEDs setting all the LEDs in the strip to the defined background color.
    // Itterates through all LEDs in the strip by comparing against the _numPixels1 constant and set an offset pixel (or pixels1 in this case) to the main color.
    for (int led_number = -len; led_number < _numPixels1 + len - 1; led_number++)
    {
      for (int spot = 0; spot < len; spot++)
      {
        pixel_breathe(wait, offsets[0] + led_number + spot, color, minModifier, maxModifier, stepModifier);
      }
      setPixel(offsets[0] + led_number, background);
      LEDStrip1.sendPixels(_numPixels1, pixels1);
      delay(wait);
    }
  }
}


// ---------------------------------------------------------
// START OF NEW METHODS
// ---------------------------------------------------------

// This method will blink a given color numBlinks times at intervals of wait
void resetLEDs1::color_flash(uint8_t wait, uint8_t numBlinks, grbw color) {
	for(int i = 0; i < numBlinks; i++){
    LEDStrip1.clear(1000);
    delay(wait);
    setStrip(color);
		LEDStrip1.sendPixels(_numPixels1, pixels1);
		delay(wait);
	}
  LEDStrip1.clear(1000);
  delay(wait);
}


// This method will blink between 2 colors numBlinks times at intervals of wait
void resetLEDs1::color_flash(uint8_t wait, uint8_t numBlinks, grbw color, grbw background) {
	for(int i = 0; i < numBlinks; i++){
		setStrip(color);
		LEDStrip1.sendPixels(_numPixels1, pixels1);
		delay(wait);
		setStrip(background);
		LEDStrip1.sendPixels(_numPixels1, pixels1);
		delay(wait);
	}
}

// sets pixels1 to a color in a snake-like progression
void resetLEDs1::climb(uint8_t wait, uint8_t jumpSize, grbw color){
	for(int i = 0; i < _numPixels1; i += jumpSize){
		for(int j = i; j < i + jumpSize; j++){
			setPixel(j, color);
		}
		LEDStrip1.sendPixels(_numPixels1, pixels1);
		delay(wait);
	}
}

// sets pixels1 to a color in a snake-like progression, then clears the strip
void resetLEDs1::climb_clear(uint8_t wait, uint8_t jumpSize, grbw color){
  setStrip(Black1);
  LEDStrip1.sendPixels(_numPixels1, pixels1);
	for(int i = 0; i < _numPixels1; i += jumpSize){
		for(int j = i; j < i + jumpSize; j++){
			setPixel(j, color);
		}
		LEDStrip1.sendPixels(_numPixels1, pixels1);
		delay(wait);
	}
}

// climbs the strip in a color, then flashes in a 2nd color
void resetLEDs1::impact(uint8_t wait, uint8_t wait2, grbw climbColor, grbw flashColor) {
  setStrip(Black1);
  LEDStrip1.sendPixels(_numPixels1, pixels1);

  climb_clear(wait, 1, climbColor);
  color_flash(wait2, 3, flashColor);
}

// sends a snake out from the middle in both directions
void resetLEDs1::middle_out(int wait, grbw color){

  for(int i = 0; i < _numPixels1/2;i++){

    setPixel((_numPixels1/2 + i), color);

    setPixel((_numPixels1/2 - i), color);

    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
  setStrip(color);
  LEDStrip1.sendPixels(_numPixels1, pixels1);
  delay(wait);
}

// sends a snake towards the middle from eother end
void resetLEDs1::out_middle(int wait, grbw color){

  for(int i = 0; i < _numPixels1/2;i++){

    setPixel(_numPixels1 - i, color);

    setPixel(i, color);

    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);

  }
  setStrip(color);
  LEDStrip1.sendPixels(_numPixels1, pixels1);
  delay(wait);
}

// snakes from the middle to the outside, then retracts back to the middle
void resetLEDs1::middle_out_middle(int wait, grbw color) {
  middle_out(wait, color);
  out_middle(wait, Black1);
}

// snakes from the outsides to the middle, then retracts
void resetLEDs1::out_middle_out(int wait, grbw color) {
  out_middle(wait, color);
  middle_out(wait, Black1);
}

// snakes out from the middle, then clears from the middle
void resetLEDs1::climb_out(uint8_t wait, grbw color){
  middle_out(wait, color);
  middle_out(wait, Black1);
}

// snakes out from the middle, then clears from the middle
void resetLEDs1::climb_in(uint8_t wait, grbw color){
  out_middle(wait, color);
  out_middle(wait, Black1);
}

// sends groups outwards from the center
void resetLEDs1::color_chase_in(uint8_t wait, uint8_t len, grbw main){

  for (int led_number = -len; led_number < _numPixels1/2 - len + 1; led_number++) {

    for (int spot = 0; spot < len; spot++) {
      setPixel(led_number + spot, main);

      setPixel((_numPixels1 - led_number) - spot, main);
    }
    setPixel(led_number, Black1);
    setPixel(led_number, Black1);

    setPixel((_numPixels1 - led_number), Black1);
    setPixel((_numPixels1 - led_number), Black1);

    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }
  setStrip(Black1);
  LEDStrip1.sendPixels(_numPixels1, pixels1);
}

void resetLEDs1::color_bounce_in(uint8_t wait, uint8_t len, grbw main){
  setStrip(Black1);
  for (int led_number = 0; led_number < _numPixels1/2; led_number++) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(_numPixels1 - led_number - offset, main);
	  setPixel(led_number + offset, main);
    }
    setPixel(led_number, Black1);
	setPixel(_numPixels1 - led_number, Black1);

    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }

  for (int led_number = _numPixels1/2; led_number > len/2; led_number--) {
    for (int offset = 0; offset < len; offset++) {
      setPixel(led_number - offset, main);
	  setPixel(_numPixels1 -+led_number + offset, main);
    }
    setPixel(led_number, Black1);
	setPixel(_numPixels1 - led_number, Black1);


    LEDStrip1.sendPixels(_numPixels1, pixels1);
    delay(wait);
  }






}
