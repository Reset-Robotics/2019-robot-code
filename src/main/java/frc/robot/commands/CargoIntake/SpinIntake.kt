package frc.robot.commands.CargoIntake

import org.sertain.command.Command
import frc.robot.subsystems.CargoIntake
import frc.robot.OI

public class SpinIntake : Command()
{
    var rightTriggerAxis: Double = OI().xboxController.getRawAxis(2)
    var leftTriggerAxis: Double = -OI().xboxController.getRawAxis(3)
    var spin: Double = 1.0 //pow applied to the ball intake motor
    public val deadzone: Double = 0.1

    // Make sure we require any necessary objects/classes
    init 
    {
        requires(CargoIntake)
    }

    override fun execute(): Boolean
    {
        if(Math.abs(rightTriggerAxis) > 0.0 + deadzone) 
            intake() // If the right trigger is pressed more than the deadzone, intake the cargo
        if(Math.abs(leftTriggerAxis) > 0.0 + deadzone) 
            shoot() // If the left trigger is pressed more than the deadzone, shoot the cargo
        if(Math.abs(leftTriggerAxis) == 0.0 && Math.abs(rightTriggerAxis) == 0.0) 
            killMotor() // If neither triggers are pressed, kill motors
        if(Math.abs(leftTriggerAxis) > 0.0 + deadzone && Math.abs(rightTriggerAxis) > 0.0 + deadzone) 
            killMotor()
        // If both triggers are pressed, kill motors to prevent unwanted behavior
        
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