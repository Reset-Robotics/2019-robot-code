package frc.robot.commands.CargoIntake

import org.sertain.command.Command
import frc.robot.subsystems.CargoIntake
import frc.robot.OI

public class SpinIntake(leftInput: Double, rightInput: Double): Command()
{
    var rightTriggerAxis: Double = rightInput + 1
    var leftTriggerAxis: Double = leftInput
    var spin: Double = 1.0 //pow applied to the ball intake motor
    public val deadzone: Double = 0.1

    override fun execute(): Boolean
    {
        if(rightTriggerAxis > 0.0 + deadzone) intake() // If the right trigger is pressed more than the deadzone, intake the cargo
        if(leftTriggerAxis > 0.0 + deadzone) shoot() // If the left trigger is pressed more than the deadzone, shoot the cargo
        if(leftTriggerAxis == 0.0 && rightTriggerAxis == 0.0) killMotor() // If neither triggers are pressed, kill motors
        if(leftTriggerAxis > 0.0 + deadzone && rightTriggerAxis > 0.0 + deadzone) killMotor() // If both triggers are pressed, kill motors to prevent unwanted behavior
        
        return true;
    }

    fun intake(): Boolean
    {
        CargoIntake.spin(spin)
        return false;
    }

    fun shoot(): Boolean
    {
        CargoIntake.spin(-spin)
        return false;
    }

    fun killMotor() { CargoIntake.spin(0.0) }
}