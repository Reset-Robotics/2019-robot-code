package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*

//importing IDs
import frc.robot.IDs

public object CargoIntake : Subsystem()
{
    val ids: IDs = IDs() // Create local instance of ID values
    val intakeMotor: WPI_TalonSRX = WPI_TalonSRX((ids.cargoIntakeMotorIDs.get("Main"))?: 9)  // Creating the motor object
    val encoderPort = ids.encoderPorts.get("CargoIntake") // Setting the encoder port
    public val deadzone: Double = ids.deadzones.get("CargoIntake-Subsystem") ?: 0.1 // Trigger deadzone
    val kTimeoutMs: Int = 30// Encoder timeout
   

    // Autobrake constants
    val minimumSpeed: Int = 10// Speed below which the autobrake will engage
    var autoStopEnabled: Boolean = true // Sets whether the autostop will enage 
    var talonVoltage: Double = 0.0 // Initializing the variable for the voltage of the talon
    var minimumMotorOutputPercent: Double = 50.0 // The value for the voltage above which the autostop will initialize
    var brake: Boolean = false // Sets wether the motor is stopped by the autostop
   

    override fun onCreate()
    {
        this.intakeMotor.configContinuousCurrentLimit(40,0) // desired current after limit
		this.intakeMotor.configPeakCurrentLimit(35, 0) // max current
		this.intakeMotor.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.intakeMotor.enableCurrentLimit(true)
        this.intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMs);
        this.intakeMotor.setSensorPhase(true)
        this.intakeMotor.setInverted(false)
        this.intakeMotor.setNeutralMode(NeutralMode.Coast)
    }
    
    fun spin(input: Double) 
    {
        var localSpin: Double = 0.0

        // Right trigger spinning in
        if(input > 0)
        {
            localSpin = 1.0
            brake = false
        }

        // Left trigger spinning out
        if(input < 0)
        {
            localSpin = -1.0
            this.intakeMotor.setNeutralMode(NeutralMode.Coast)
        }
        //testing to see if the motor should autostop
        if(this.intakeMotor.getSelectedSensorVelocity() < minimumSpeed && autoStopEnabled && this.intakeMotor.getMotorOutputPercent() > minimumMotorOutputPercent)
            brake = true
            this.intakeMotor.setNeutralMode(NeutralMode.Brake)
        
        //setting the motor speed or disabling the motor
        if(!brake)
            this.intakeMotor.set(localSpin)

    }

    fun isAutoStopEnabled():Boolean { return autoStopEnabled; }
    
    fun toggleAutoStop():Boolean
    {
        autoStopEnabled = !autoStopEnabled
        return autoStopEnabled;
    }
}