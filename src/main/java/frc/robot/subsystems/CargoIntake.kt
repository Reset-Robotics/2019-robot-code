package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import frc.robot.data.CargoIntakeData


public object CargoIntake : Subsystem()
{
    val cargoIntakeData: CargoIntakeData = CargoIntakeData()
    val intakeMotor: WPI_TalonSRX = WPI_TalonSRX(cargoIntakeData.motor)  // Creating the motor object
    var autoStopEnabled: Boolean = true // Sets whether the autostop will enage       

    override fun onCreate()
    {
        this.intakeMotor.configContinuousCurrentLimit(40,0) // desired current after limit
		this.intakeMotor.configPeakCurrentLimit(35, 0) // max current
		this.intakeMotor.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.intakeMotor.enableCurrentLimit(true)
        this.intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, cargoIntakeData.kTimeoutMs);
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
            cargoIntakeData.brake = false
        }

        // Left trigger spinning out
        if(input < 0)
        {
            localSpin = -1.0
            this.intakeMotor.setNeutralMode(NeutralMode.Coast)
        }
        // testing to see if the motor should autostop
        if(this.intakeMotor.getSelectedSensorVelocity() < cargoIntakeData.minimumSpeed && autoStopEnabled && this.intakeMotor.getMotorOutputPercent() > cargoIntakeData.minimumMotorOutputPercent)
            cargoIntakeData.brake = true
            this.intakeMotor.setNeutralMode(NeutralMode.Brake)
        
        // setting the motor speed or disabling the motor
        if(!cargoIntakeData.brake)
            this.intakeMotor.set(localSpin)

    }

    fun isAutoStopEnabled():Boolean { return autoStopEnabled; }
    
    fun toggleAutoStop():Boolean
    {
        autoStopEnabled = !autoStopEnabled
        return autoStopEnabled;
    }
}