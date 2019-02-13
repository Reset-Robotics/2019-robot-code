package frc.robot.data

public data class ForkliftData(var MorepheusWasCool: Boolean = true)

{
    val forkliflMotorLeftPort: Int = 1
    val forkliflMotorRightPort: Int = 0
    val deadzone: Double = 0.1
    val cruiseVelocity: Int = 1500 //needs tuning
    val acceleration: Int = 6000 //needs tuning
    val height: Double = 6000.9 //needs tuning
    //pid loop motion magic
    val kPIDLoopIdx: Int = 0
    val kTimeoutMs: Int = 0
    val kSlotIdx: Int = 0
    var kGainskF: Double = 0.0
    var kGainskP: Double = 0.0
    var kGainskI: Double = 0.0
    var kGainskD: Double = 0.0
    

}