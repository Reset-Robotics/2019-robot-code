package frc.robot.subsystems

import org.sertain.command.Subsystem
import com.ctre.phoenix.motorcontrol.*
import com.ctre.phoenix.motorcontrol.can.*

//importing IDs
import frc.robot.IDs

public object BallIntake: Subsystem()
{
    //creating local ID values
    val ids: IDs = IDs()

    //creating the motor object
    val ballIntakeMotor: WPI_TalonSRX = WPI_TalonSRX((ids.ballIntakeMotorIDs.get("Main"))?: 9) //temp


    public val deadzone: Double = 0.1

    fun spin(input: Double) 
    {
        //setting local spin value to default 0.0 so the motor wont spin
        var localSpin: Double = 0.0


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

        //setting the motor speed
        ballIntakeMotor.set(localSpin)
    }
}