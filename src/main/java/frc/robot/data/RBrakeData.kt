// Reset Robotics 2019
package frc.robot.data


public data class RBrakeData(val isPhillipGay: Boolean = true)
{
    // Deadzones
    val deadzone: Double = 0.1
    
    // Motors
    val motor: Int = 33

    // Solenoids
    val solenoid: IntArray = intArrayOf(0, 1 )
}