// Reset Robotics 2019
// NVIDIA JetsonTX1 Vision

// Libraries
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <iostream>
#include "opencv2/opencv.hpp"
#include "opencv2/core.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/cudaarithm.hpp"


int main()
{
    cv::cuda::printShortCudaDeviceInfo(cv::cuda::getDevice());

    printf("Current exposure settings:\n");
    system("v4l2 -ctl -d /dev/video0 -C exposure_auto");
    system("v4l2 -ctl -d /dev/video0 -C exposure_absolute");

    // Set exposure
    system("v4l2 -ctl -d /dev/video0 -c exposure_auto=1");
    system("v4l2 -ctl -d /dev/video0 -c exposure_absolute=5");

    // Print the exposures settings of the cameras
    printf("New exposure settings:\n");
    system("v4l2-ctl -d /dev/video0 -C exposure_absolute");
    int blur_size = 3; //size of the median blur kernel
    int img_scale_factor = 1; //halves the size of the picture


    // Images
    cv::Mat imgRaw; //input image
    cv::Mat imgResize; //image resized to 320x240
    cv::Mat imgBlur; //median blur
    cv::Mat imgHSV; //switch to HSV colorspace
    cv::Mat imgThreshold; //binary image from HSV thresholding
    cv::Mat imgContour; //finding countours messes up the threshold image
    cv::Mat imgOutput; //final image

    cv::cuda::GpuMat src, resize, blur, hsv, threshold;
	
    //HSV Thresholding Defaults (green LED ring and Exposure of 5 on camera)
    int h_lowerb = 97;
    int h_upperb = 179;
    int s_lowerb = 185;
    int s_upperb = 255;
    int v_lowerb = 52;
    int v_upperb = 255;

    // HSV Threshold Sliders (currently commented out cause unnecessary)
    cv::namedWindow("HSV Thresholding");
    cv::createTrackbar("Hue Lower Bound", "HSV Thresholding", &h_lowerb, 179);
    cv::createTrackbar("Hue Upper Bound", "HSV Thresholding", &h_upperb, 179);
    cv::createTrackbar("Saturation Lower Bound", "HSV Thresholding", &s_lowerb, 255);
    cv::createTrackbar("Saturation Upper Bound", "HSV Thresholding", &s_upperb, 255);
    cv::createTrackbar("Value Lower Bound", "HSV Thresholding", &v_lowerb, 255);
    cv::createTrackbar("Value Upper Bound", "HSV Thresholding", &v_upperb, 255);

    std::vector<std::vector<cv::Point> > contours; // array of contours (which are each an array of points)

    // Start video stream
    cv::VideoCapture input(1);
    cv::VideoWriter output;

    // Loop for each frame
    for (;;)
	{
        // Checks for video feed
        if (!input.read(imgRaw))
           break;
        }

    src.upload(imgRaw);

    // Gpu accelerated image transformations
    cv::cuda::resize(src, resize, cv::Size(input.get(CV_CAP_PROP_FRAME_WIDTH) / img_scale_factor, input.get(CV_CAP_PROP_FRAME_HEIGHT) / img_scale_factor), CV_INTER_CUBIC);
    resize.download(imgResize);

    cv::cuda::cvtColor(resize, hsv, CV_BGR2HSV);

    // Download GpuMat from the GPU
    hsv.download(imgHSV);

    cv::inRange(imgHSV, cv::Scalar(h_lowerb, s_lowerb, v_lowerb), cv::Scalar(h_upperb, s_upperb, v_upperb), imgThreshold);
    imgThreshold.copyTo(imgContour);
    cv::findContours(imgContour, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);

    // Create bounding rectangles to identify what contours are actual targets
    std::vector<cv::RotatedRect> boundRects(contours.size());
    for(int i = 0; i < contours.size(); i++)
        boundRects[i] = cv::minAreaRect(cv::Mat(contours[i]));

    // Identify contours which could be actual targets
    std::vector<std::vector<cv::Point> > poss_targets(0);
    for(int i = 0; i < boundRects.size(); i++)
	{
        cv::Size s = boundRects[i].size;

        // Filter based on how much of the bounding rectangle is filled up by the contour and rectangles that are too small
        if (s.height * s.width < 1.25 * cv::contourArea(contours[i]) && s.height * s.width > 30)
            poss_targets.insert(poss_targets.end(), contours[i]);
    }

    // If we identify two possible targets then they are correct targets
    std::vector<std::vector<cv::Point> > targets(0);
    if (poss_targets.size() == 2)
        targets = poss_targets;

    imgOutput = imgResize.clone(); // output image is the blurred image with information on it

    cv::drawContours(imgOutput, contours, -1, cv::Scalar(0, 255, 0)); // draw all contours in green

    // Save and show the output image
    output.write(imgOutput);
	cv::namedWindow("stream", CV_WINDOW_AUTOSIZE);
	cv::namedWindow("raw", CV_WINDOW_AUTOSIZE);
    cv::imshow("stream", imgThreshold);
	cv::imshow("raw", imgRaw);

    // If we identify targets print all this
    if (!targets.empty())
	{
      	cv::Rect r = boundingRect(targets[1]);
      	cv::Rect  r1 = boundingRect(targets[0]);
      	double centerX= r.x + (r.width/2);
        double centerX1= r1.x + (r1.width/2);
        double distance2Pixels= ((centerX + centerX1) / 2) - (640 / 2); // 640 is camera width
		std::cout << "Distance pix" << distance2Pixels << std::endl;
    }
}
