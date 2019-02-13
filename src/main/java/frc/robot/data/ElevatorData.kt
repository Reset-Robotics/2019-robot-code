package frc.robot.data
import edu.wpi.first.wpilibj.DigitalInput

public data class ElevatorData(val polybiusIsDead: Boolean = true)
{
    val elevatorLeftPort: Int = 1
    val elevatorRightPort: Int = 3
    val deadzone: Double = 0.1

    //limit switches
    val bottomSwitchRight: DigitalInput = DigitalInput(2)
    val bottomSwitchLeft: DigitalInput = DigitalInput(2)
    val topSwitchRight: DigitalInput = DigitalInput(2)
    val topSwitchLeft: DigitalInput = DigitalInput(2)

    //configuring motion magic
    var cruiseVelocity: Double = 19000.0  //temp
    var acceleration: Double = 11000.0  //temp
    var topHeight: Double = 72000.0 //temp
    var middleHeight: Double = 35000.0//temp
    var bottomHeight: Double = 0.0//temp

      //configuring PID Loop for motion magic to do- move to IDS
    var kPIDLoopIdx: Int = 0
    var kTimeoutMs: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0 

     var elevatorState: String = "Bottom"

     var allowableLevelError: Double = 20.0
    
}