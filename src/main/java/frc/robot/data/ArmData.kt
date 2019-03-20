package frc.robot.data


public data class ArmData(val isBenGay: Boolean = true)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val motor: Int = 14

    // Motion Magic
    data class MMData(val name: String, val data: Double)
    val cruiseVelocity = MMData("Cruise-Velocity", 19000.0)
    val acceleration = MMData("Acceleration", 11000.0)
    val topHeight = MMData("Top", 72000.0)
    val middleHeight = MMData("Middle", 35000.0)
    val bottomHeight = MMData("Bottom", 0.0)
    val scoringHeight = MMData("Scoring", 45000.0)

    // PID
    var kPIDLoopIdx: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0 

    // Encoders
    val encoder: Int = 11
    var kTimeoutMs: Int = 0

    // Misc
    var armState: String = "Bottom"
}