package frc.robot.data


public data class WristData(val teamNumber: Int = 6325)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val motor: Int = 15

    // Motion Magic
    data class MMData(val name: String, val data: Double)
   
    var topHeightPanel = MMData("Panel-Top", 0.0)
    var middleHeightPanel = MMData("Panel-Middle", 0.0)
    var bottomHeightPanel = MMData("Panel-Bottom", 0.0)
    var floorPanel = MMData("Panel-Floor", 0.0)
    var topHeightCargo = MMData("Cargo-Top", 0.0)
    var middleHeightCargo = MMData("Cargo-Middle", 0.0)
    var bottomHeightCargo = MMData("Cargo-Bottom", 0.0) 
    var cargoshipCargo = MMData("CargoshipCargo", 0.0) // temp

    val cruiseVelocity = 19000
    var acceleration = 11000

    // PID
    var kPIDLoopIdx: Int = 0
    var rightKSlotIdx: Int = 0
    var leftKSlotIdx: Int = 1
    var kGainskF: Double = 0.38
    var kGainskP: Double = 0.005
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.040

    // Encoders
    val encoder: Int = 0
    var kTimeoutMs: Int = 10

    // Misc
    var wristState: String = "Bottom"
    var intakeType: Boolean = false // false is ball true is panel
}