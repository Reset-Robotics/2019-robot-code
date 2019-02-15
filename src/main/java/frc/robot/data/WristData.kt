package frc.robot.data


public data class WristData(val teamNumber: Int = 6325)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val motor: Int = 4

    // Motion Magic
    data class MMData(val name: String, val data: Double)
    val cruiseVelocity = MMData("Cruise-Velocity", 19000.0)
    var acceleration = MMData("Acceleration", 11000.0)
    var topHeightPanel = MMData("Panel-Top", 72000.0)
    var middleHeightPanel = MMData("Panel-Middle", 35000.0)
    var bottomHeightPanel = MMData("Panel-Bottom", 0.0)
    var topHeightCargo = MMData("Cargo-Top", 72000.0)
    var middleHeightCargo = MMData("Cargo-Middle", 35000.0)
    var bottomHeightCargo = MMData("Cargo-Bottom", 0.0) 

    // PID
    var kPIDLoopIdx: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0

    // Encoders
    val encoder: Int = 14
    var kTimeoutMs: Int = 0

    // Misc
    var wristState: String = "Bottom"
    var intakeType: Boolean = false // false is ball true is panel
}