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
    val minimumSpeed: Int = 10
    var autoStopEnabled: Boolean = true
    override fun onCreate()
    {
        ballIntakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
											0, 
											kTimeoutMs);
        ballIntakeMotor.setSensorPhase(true)
        ballIntakeMotor.setInverted(false)
    }
    fun spin(input: Double) 
    {
        var localSpin: Double = 0.0 //setting local spin value to default 0.0 so the motor wont spin

        //left trigger
        if(input > 0)
        {
            localSpin = 1.0
        }

        //right trigger
        if(input < 0)
        {
            localSpin = -1.0
        }

        //getting motor velocity
        if(ballIntakeMotor.getSelectedSensorVelocity() < minimumSpeed && autoStopEnabled)
            localSpin=0.0
        
         //setting the motor speed
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