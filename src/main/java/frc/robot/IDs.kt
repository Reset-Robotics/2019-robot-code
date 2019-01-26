package frc.robot


public class IDs
{
    public var joystickLeftIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds IDs for different buttons and axes on the left driver joystick
    public var joystickRightIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds IDs for different buttons and axes on the right driver joystick
    public var xboxIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds IDs for different buttons and axes on the xbox controller
    public var driveMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds all our Talon IDs for drive motors
    public var pwmMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds all our PWM IDs 
    public var forkliftMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds forklift motor IDs
    public var elevatorMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds elevator motor IDs
    public var cargoIntakeMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds cargointake motor IDs
    public var armMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds arm motor IDs
    public var wristMotorIDs: HashMap<String, Int> = HashMap<String, Int>() // Holds wrist motor IDs
    public var drivetrainPID: HashMap<String, Double> = HashMap<String, Double>() // Holds all our drivetrain PID values 
    public var elevatorPID: HashMap<String, Double> = HashMap<String, Double>() // Holds all our elevator PID values 
    public var encoderPorts: HashMap<String, Int> = HashMap<String, Int>() // Holds all our encoder port values
    public var deadzones: HashMap<String, Double> = HashMap<String, Double>() // Holds all our deadzones
    public var rBrakeSolenoid: IntArray = intArrayOf(0,1)
    public var panelIntakeSolenoid: IntArray = intArrayOf(2,3,4,5)


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
        driveMotorIDs.put("Front-Left", 12)
        driveMotorIDs.put("Front-Right", 11)
        driveMotorIDs.put("Back-Left", 23)
        driveMotorIDs.put("Back-Right", 30)

        // Temporary forklift motor IDs
        forkliftMotorIDs.put("Left", 1) 
        forkliftMotorIDs.put("Right", 0)


        // Temporary elevator motor IDs
        elevatorMotorIDs.put("Right", 1)
        elevatorMotorIDs.put("Left", 3)

        // Temporary arm motor IDs
        armMotorIDs.put("Main", 77)

        // Temporary wrist motor IDs
        wristMotorIDs.put("Main", 78)

        // Temporary cargointake motor IDs
        cargoIntakeMotorIDs.put("Main",9)

        // PID tuning drivetrain
        drivetrainPID.put("P", 0.006)
        drivetrainPID.put("I", 0.0)
        drivetrainPID.put("D", 0.0)
        drivetrainPID.put("F", 0.0)

        // PID tuning elevator
        elevatorPID.put("P", 0.006)
        elevatorPID.put("I", 0.0)
        elevatorPID.put("D", 0.0)
        elevatorPID.put("F", 0.0)

        // Motor encoder ports
        encoderPorts.put("Ball-Intake", 9)


        // Deadzones
        deadzones.put("Drivetrain", 0.1)
        deadzones.put("R-Brake", 0.1)
        deadzones.put("CargoIntake-Subsystem", 0.1)
        deadzones.put("CargoIntake-Spin", 0.1)
        deadzones.put("Forklift", 0.1)
    } 
}