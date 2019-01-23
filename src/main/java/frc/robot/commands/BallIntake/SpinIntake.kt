package frc.robot.commands.BallIntake

import org.sertain.command.Command
import frc.robot.subsystems.BallIntake
import frc.robot.OI


import frc.robot.IDs

public class SpinIntake(leftInput: Double, rightInput: Double): Command()
{
    //constant for the speed the intake will spin when button is pressed
    var rightTriggerAxis: Double = rightInput + 1
    var leftTriggerAxis: Double = leftInput
    var spin: Double = 1.0
    var ids: IDs = IDs()
    public val deadzone: Double = 0.1

    //needs to be moved to OI
    override fun execute(): Boolean
    {
       
        if(rightTriggerAxis > 0.0)
            intake()
            spin=1.0
        if(leftTriggerAxis > 0.0)
            shoot()
            spin=-1.0
        if(leftTriggerAxis == 0.0 && rightTriggerAxis == 0.0)
            killMotor()
        if(leftTriggerAxis > 0 && rightTriggerAxis > 0)
            killMotor()
        return true
    }

    //function called while ball is being taken in
    fun intake(): Boolean
    {
        BallIntake.spin(spin)
        return false
    }

    //function being called when ball is being scored
    fun shoot(): Boolean
    {
        BallIntake.spin(spin)
        return false
    }
    fun killMotor()
    {
        BallIntake.spin(0.0)
    }
   
}