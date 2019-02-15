package frc.robot.data


public data class DrivetrainData(val paramDeadzone: Double = 0.1)
{
    // Deadzones
    val deadzone: Double = 0.3
    
    // Motors
    val motorFrontLeft: Int = 1
    val motorFrontRight: Int = 2
    val motorBackLeft: Int = 3
    val motorBackRight: Int = 4

    // PID
    val pidP: Double = 0.006
    val pidI: Double = 0.0
    val pidD: Double = 0.0
    val pidF: Double = 0.0
    val turnThreshold: Double = 2.0 // how many degrees the robot has to be within for it to stop looking for the required angle
    var turnRate: Double = 0.0
    var driveAngle: Double = 0.0

    // Encoders
    val encoderFrontLeft: Int = 1
    val encoderFrontRight: Int = 2
    val encoderBackLeft: Int = 3
    val encoderBackRight: Int = 4

    // Misc
    val wheelCircumference: Double = 18.8495559215
}