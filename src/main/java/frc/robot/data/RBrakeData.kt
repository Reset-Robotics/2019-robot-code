package frc.robot.data


public data class RBrakeData(val isPhillipGay: Boolean = true)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val motor: Int = 2

    // Solenoids
    val solenoid: IntArray = intArrayOf(0, 1)
}