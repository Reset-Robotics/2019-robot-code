package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*

//importing IDs
import frc.robot.IDs

public object BallIntake: Subsystem()
{
    val ids: IDs = IDs()//creating local ID values
    val ballIntakeMotor: WPI_TalonSRX = WPI_TalonSRX((ids.ballIntakeMotorIDs.get("Main"))?: 9)  //creating the motor object
    val encoderPort = ids.encoderPorts.get("Ball-Intake")//setting the encoder port
    public val deadzone: Double = 0.1 //trigger deadzone
    val kTimeoutMs: Int = 30//encoder timeout
   

    //autobrake constants
    val minimumSpeed: Int = 10//speed below which the autobrake will engage
    var autoStopEnabled: Boolean = true //sets whether the autostop will enage 
    var talonVoltage: Double = 0.0 // initializing the variable for the voltage of the talon
    var minimumMotorOutputPercent: Double = 50.0 // the value for the voltage above which the autostop will initialize
    var brake: Boolean = false //sets wether the motor is stopped by the autostop
   

    override fun onCreate()
    {
        ballIntakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											0, 
											kTimeoutMs);
        ballIntakeMotor.setSensorPhase(true)
        ballIntakeMotor.setInverted(false)
        ballIntakeMotor.setNeutralMode(NeutralMode.Coast)
    }
    fun spin(input: Double) 
    {
        var localSpin: Double = 0.0 //setting local spin value to default 0.0 so the motor wont spin

        //right trigger spinning in
        if(input > 0)
        {
            localSpin = 1.0
            brake = false
        }

        //left trigger spinning out
        if(input < 0)
        {
            localSpin = -1.0
            ballIntakeMotor.setNeutralMode(NeutralMode.Coast)
        }
        ballIntakeMotor
        //testing to see if the motor should autostop
        if(ballIntakeMotor.getSelectedSensorVelocity() < minimumSpeed && autoStopEnabled && ballIntakeMotor.getMotorOutputPercent() > minimumMotorOutputPercent)
            brake = true
            ballIntakeMotor.setNeutralMode(NeutralMode.Brake)
        
        //setting the motor speed or disabling the motor
        if(!brake)
            ballIntakeMotor.set(localSpin)

    }
    fun isAutoStopEnabled():Boolean
    {
        return autoStopEnabled
    }
    fun toggleAutoStop():Boolean
    {
        autoStopEnabled = !autoStopEnabled
        return autoStopEnabled
    }
}