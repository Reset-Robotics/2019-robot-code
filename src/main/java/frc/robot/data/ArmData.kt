package frc.robot.data


public data class ArmData(val coreyInTheHouse: Boolean = false)
{

    //val armMotor: WPI_TalonSRX = WPI_TalonSRX((IDs().armMotorIDs.get("Main")) ?: 77) //temp  
    val deadzone: Double = 0.1  
    val armMotorPort: Int = 77
    //data class MotionData(val name: String, val data: Double)
    val cruiseVelocity =  19000.0
    val acceleration = 11000.0
    val topHeight =  72000.0
    val middleHeight =  35000.0
    val bottomHeight = 0.0
    var kPIDLoopIdx: Int = 0
    var kTimeoutMs: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0 

    var armState: String = "Bottom"
}