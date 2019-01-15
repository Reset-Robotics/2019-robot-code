package frc.robot


public class IDs
{
    public var joystickLeftIDs: HashMap<String, Int> = HashMap<String, Int>() // holds ids for different buttons and axes on the left driver joystick
    public var joystickRightIDs: HashMap<String, Int> = HashMap<String, Int>() // holds ids for different buttons and axes on the right driver joystick
    public var xboxIDs: HashMap<String, Int> = HashMap<String, Int>() // holds ids for different buttons and axes on the xbox controller
    public var driveMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // holds all our talon ids for drive motors
    public var pwmMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // holds all our PWM ids 
    public var pidValues: HashMap<String, Double> = HashMap<String, Double>() // holds all our PID values 

    public fun IDs()
    {
        // Driver Controls/OI
        joystickLeftIDs.put("USB-ID", 1)
        joystickLeftIDs.put("X-Axis", 0)
        joystickLeftIDs.put("Y-Axis", 1)
        joystickLeftIDs.put("Z-Axis", 2)
        joystickLeftIDs.put("Trigger", 1)
        joystickLeftIDs.put("Side-Thumb", 2)
        joystickLeftIDs.put("Top-Button-Bottom-Right", 3)
        joystickLeftIDs.put("Top-Button-Bottom-Left", 4)
        joystickLeftIDs.put("Top-Button-Top-Left", 5)
        joystickLeftIDs.put("Top-Button-Top-Right", 6)
        joystickLeftIDs.put("SliderAxis", 3)

        joystickRightIDs.put("USB-ID", 0)
        joystickRightIDs.put("X-Axis", 0)
        joystickRightIDs.put("Y-Axis", 1)
        joystickRightIDs.put("Z-Axis", 2)
        joystickRightIDs.put("Trigger", 1)
        joystickRightIDs.put("Side-Thumb", 2)
        joystickRightIDs.put("Top-Button-Bottom-Right", 3)
        joystickRightIDs.put("Top-Button-Bottom-Left", 4)
        joystickRightIDs.put("Top-Button-Top-Left", 5)
        joystickRightIDs.put("Top-Button-Top-Right", 6)

        xboxIDs.put("USB-ID", 2)
        xboxIDs.put("A-Button", 1)
        xboxIDs.put("B-Button", 2)
        xboxIDs.put("X-Button", 3)
        xboxIDs.put("Y-Button", 4)
        xboxIDs.put("Left-Bumper", 5)
        xboxIDs.put("Right-Bumper", 6)
        xboxIDs.put("Back-Button", 7)
        xboxIDs.put("Left-Joystick-Button", 8)
        xboxIDs.put("Right-Joystick-Button", 9) 
        xboxIDs.put("Left-Joystick-Y-Axis", 1)
        xboxIDs.put("Right-Joystick-Y-Axis", 5)

        // using Morpheus' IDs for placeholder and testing right now
        driveMotorIDs.put("Front-Left", 1)
        driveMotorIDs.put("Front-Right", 2)
        driveMotorIDs.put("Back-Left", 4)
        driveMotorIDs.put("Back-Right", 3)

        // pid tuning
        pidValues.put("P", 0.006)
        pidValues.put("I", 0.0)
        pidValues.put("D", 0.0)
        pidValues.put("F", 0.0)
    } 
}