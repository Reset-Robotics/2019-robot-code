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
    val topHeight = MMData("Top", 10000.0)
    val middleHeight = MMData("Middle", 5000.0)
    val bottomHeight = MMData("Bottom", 0.0)
    val scoringHeightUp = MMData("ScoringUp", -6000.0)
    val scoringHeightDown = MMData("ScoringDown", 3500.0)

    // PID
    var kPIDLoopIdx: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.38
    var kGainskP: Double = 0.010
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.005
    var pidP: Double = 0.006
    var pidI: Double = 0.0
    var pidD: Double = 0.05
    var pidF: Double = 0.38

    // Encoders
    val encoder: Int = 0
    var kTimeoutMs: Int = 10

    // Misc
    var armState: String = "Bottom"
}