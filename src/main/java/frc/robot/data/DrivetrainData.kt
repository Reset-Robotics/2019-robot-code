package frc.robot.data


public data class DrivetrainData(val deadzone: Double)
{
    // Deadzones
    deadzone = 0.1
    
    // Motors
    val motorFrontLeft: Int = 3
    val motorFrontRight: Int = 4
    val motorBackLeft: Int = 2
    val motorBackRight: Int = 1

    // PID
    val pidP: Double = 0.006
    val pidI: Double = 0.0
    val pidD: Double = 0.0
    val pidF: Double = 0.0
    val turnThreshold: Double = 2.0 // how many degrees the robot has to be within for it to stop looking for the required angle
    var turnRate: Double = 0.0
    var driveAngle: Double = 0.0

    // Encoders
    val encoderFrontLeft: Int = 3
    val encoderFrontRight: Int = 4
    val encoderBackLeft: Int = 2
    val encoderBackRight: Int = 1

    // Misc
    val wheelCircumference: Double = 18.8495559215
}