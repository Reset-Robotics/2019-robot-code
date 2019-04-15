// Reset Robotics 2019
package frc.robot.data


public data class CargoIntakeData (val areWe254Yet: Boolean = false)
{
    // Deadzones
    val deadzone: Double = 0.3
    
    // Motors
    val motor: Int = 32
    val minimumSpeed: Int = 10
    val spinConst: Double = .5
    var talonVoltage: Double = 0.0 // Initializing the variable for the voltage of the talon
    var minimumMotorOutputPercent: Double = 50.0 // The value for the voltage above which the autostop will initialize

    // Encoders
    val encoder: Int = 13
    val kTimeoutMs: Int = 30// Encoder timeout

    // Misc
    var armState: String = "Bottom"
    var brake: Boolean = false // Sets whether the motor is stopped by the autostop
}