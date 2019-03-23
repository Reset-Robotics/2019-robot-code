package frc.robot.data
import edu.wpi.first.wpilibj.DigitalInput

public data class ElevatorData(val polybiusIsDead: Boolean = true)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val leftMotor: Int = 16
    val rightMotor: Int = 17

    // Limit Switches
    /* val bottomSwitchRight: DigitalInput = DigitalInput(2)
    val bottomSwitchLeft: DigitalInput = DigitalInput(2)
    val topSwitchRight: DigitalInput = DigitalInput(2)
    val topSwitchLeft: DigitalInput = DigitalInput(2)
    */

    // Motion Magic
    data class MMData(val name: String, val data: Double)
    val cruiseVelocity = MMData("Cruise-Velocity", 19000.0)
    val acceleration = MMData("Acceleration", 11000.0)
    val topHeight = MMData("Top", 72000.0)
    var topCargoPos = MMData("TopCargo", 0.0) // temp
    var topPanelPos = MMData("TopPanel", 0.0) // temp
    val middleHeight = MMData("Middle", 35000.0)
    var middleCargoPos = MMData("MiddleCargo", -21000.0) // temp
    var middlePanelPos = MMData("MiddlePanel", -21000.0) // temp
    val bottomHeight = MMData("Bottom", 0.0)
    var bottomCargoPos = MMData("BotomCargo", 0.0) // temp
    var bottomPanelPos = MMData("BottomPanel", 0.0) // temp
    var cargoshipCargo = MMData("CargoshipCargo", 0.0) // temp

    // PID
    var kPIDLoopIdx: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.38
    var kGainskP: Double = 0.006
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.05 
    var pidP: Double = 0.006
    var pidI: Double = 0.0
    var pidD: Double = 0.05
    var pidF: Double = 0.38

    // Encoders
    val leftEncoder: Int = 0
    val rightEncoder: Int = 0
    var kTimeoutMs: Int = 0

    // Misc
    var elevatorState: String = "Bottom"
    var allowableLevelError: Double = 20.0
}