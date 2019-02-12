package frc.robot.data

public data class CargoIntakeData (val isCoreyInTheHouse: Boolean = true)
{
    val intakeMotorPort: Int = 9
    val encoderPort: Int = 9
    val kTimeoutMs: Int = 30// Encoder timeout
    val minimumSpeed: Int = 10
    var autoStopEnabled: Boolean = true // Sets whether the autostop will enage 
    var talonVoltage: Double = 0.0 // Initializing the variable for the voltage of the talon
    var minimumMotorOutputPercent: Double = 50.0 // The value for the voltage above which the autostop will initialize
    var brake: Boolean = false // Sets wether the motor is stopped by the autostop

}