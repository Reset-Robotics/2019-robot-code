package frc.robot.data
import edu.wpi.first.wpilibj.DigitalInput

public data class ElevatorData(val polybiusIsDead: Boolean = true)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val leftMotor: Int = 7
    val rightMotor: Int = 8

    // Limit Switches
    val bottomSwitchRight: DigitalInput = DigitalInput(2)
    val bottomSwitchLeft: DigitalInput = DigitalInput(2)
    val topSwitchRight: DigitalInput = DigitalInput(2)
    val topSwitchLeft: DigitalInput = DigitalInput(2)

    // Motion Magic
    data class MMData(val name: String, val data: Double)
    val cruiseVelocity = MMData("Cruise-Velocity", 19000.0)
    val acceleration = MMData("Acceleration", 11000.0)
    val topHeight = MMData("Top", 72000.0)
    val middleHeight = MMData("Middle", 35000.0)
    val bottomHeight = MMData("Bottom", 0.0)

    // PID
    var kPIDLoopIdx: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0 

    // Encoders
    val leftEncoder: Int = 7
    val rightEncoder: Int = 8
    var kTimeoutMs: Int = 0

    // Misc
    var elevatorState: String = "Bottom"
    var allowableLevelError: Double = 20.0
}