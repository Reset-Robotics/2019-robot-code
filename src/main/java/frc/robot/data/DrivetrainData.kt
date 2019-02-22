package frc.robot.data


public data class DrivetrainData(val paramDeadzone: Double = 0.1)
{
    // Deadzones
    val deadzone: Double = 0.3
    
    // Motors
    val motorFrontLeft: Int = 0
    val motorFrontRight: Int = 1
    val motorBackLeft: Int = 2
    val motorBackRight: Int = 3

    // PID
    val pidP: Double = 0.006
    val pidI: Double = 0.0
    val pidD: Double = 0.0
    val pidF: Double = 0.0
    val turnThreshold: Double = 2.0 // how many degrees the robot has to be within for it to stop looking for the required angle
    var turnRate: Double = 0.0
    var driveAngle: Double = 0.0

    // Encoders
    val encoderFrontLeft: Int = 0
    val encoderFrontRight: Int = 1
    val encoderBackLeft: Int = 2
    val encoderBackRight: Int = 3

    // Misc
    val wheelCircumference: Double = 18.8495559215
}