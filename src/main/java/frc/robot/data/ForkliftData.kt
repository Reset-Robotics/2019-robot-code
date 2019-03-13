package frc.robot.data

public data class ForkliftData(var MorepheusWasCool: Boolean = true)

{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val leftMotor: Int = 11
    val rightMotor: Int = 12

    // Limit Switches
    /*val bottomSwitchRight: DigitalInput = DigitalInput(2)
    val bottomSwitchLeft: DigitalInput = DigitalInput(2)
    val topSwitchRight: DigitalInput = DigitalInput(2)
    val topSwitchLeft: DigitalInput = DigitalInput(2)*/

    // Motion Magic
    data class MMData(val name: String, val data: Int)
    val cruiseVelocity = MMData("Cruise-Velocity", 1500)
    val acceleration = MMData("Acceleration", 6000)
    val topHeight = MMData("Top", 6000)
    val middleHeight = MMData("Middle", 3000)
    val bottomHeight = MMData("Bottom", 0)

    // PID
    val kPIDLoopIdx: Int = 0
    val kSlotIdx: Int = 0
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0

    // Encoders
    val leftEncoder: Int = 5
    val rightEncoder: Int = 6
    var kTimeoutMs: Int = 0
}