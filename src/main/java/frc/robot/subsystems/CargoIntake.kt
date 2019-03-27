package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*
import frc.robot.data.CargoIntakeData
import frc.robot.commands.CargoIntake.SpinIntake


public object CargoIntake : Subsystem()
{
    val cargoIntakeData: CargoIntakeData = CargoIntakeData()
    val intakeMotor: WPI_VictorSPX = WPI_VictorSPX(cargoIntakeData.motor)  // Creating the motor object
    var autoStopEnabled: Boolean = true // Sets whether the autostop will enage       

    override fun onCreate()
    {
        /* 
        this.intakeMotor.configContinuousCurrentLimit(40,0) // desired current after limit
		this.intakeMotor.configPeakCurrentLimit(35, 0) // max current
		this.intakeMotor.configPeakCurrentDuration(100, 0) // how long after max current to be limited (ms)
		this.intakeMotor.enableCurrentLimit(true)
        //this.intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, cargoIntakeData.kTimeoutMs);
        this.intakeMotor.setSensorPhase(true)
        this.intakeMotor.setInverted(false)
        this.intakeMotor.setNeutralMode(NeutralMode.Brake)
        */
    }
    
    fun spin(input: Double) 
    {
        var localSpin: Double = input
        intakeMotor.set(localSpin)
    }

    fun isAutoStopEnabled():Boolean { return autoStopEnabled; }
    
    fun toggleAutoStop():Boolean
    {
        autoStopEnabled = !autoStopEnabled
        return autoStopEnabled;
    }
    
   override val defaultCommand = SpinIntake()
}