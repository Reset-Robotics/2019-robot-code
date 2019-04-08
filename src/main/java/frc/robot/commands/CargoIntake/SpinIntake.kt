// Reset Robotics 2019
package frc.robot.commands.CargoIntake

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.CargoIntake
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.data.OIData
import frc.robot.data.CargoIntakeData


public class SpinIntake : Command()
{
    // Initialize local objects for our data classes
    var oiData: OIData = OIData()
    var cargoIntakeData: CargoIntakeData = CargoIntakeData()

    var rightTriggerAxis: Double = OI().xboxController.getRawAxis(oiData.xboxRightTriggerAxis.id)
    var leftTriggerAxis: Double = OI().xboxController.getRawAxis(oiData.xboxLeftTriggerAxis.id)
    var spin: Double = cargoIntakeData.spinConst // Power applied to the ball intake motor; range from -1.0 to 1.0
    public val deadzone: Double = cargoIntakeData.deadzone

    init { requires(CargoIntake) } // Make sure we require the CargoIntake subsystem, as its necessary for default commands

    override fun execute(): Boolean
    {
        rightTriggerAxis = OI().xboxController.getRawAxis(oiData.xboxRightTriggerAxis.id)
        leftTriggerAxis = OI().xboxController.getRawAxis(oiData.xboxLeftTriggerAxis.id)
        
        // If the right trigger is pressed more than the deadzone, intake the cargo
        if(Math.abs(rightTriggerAxis) > 0.0 + deadzone) 
            intake()
        // If the left trigger is pressed more than the deadzone, shoot the cargo
        if(Math.abs(leftTriggerAxis) > 0.0 + deadzone) 
            shoot()
        // If neither triggers are pressed, kill motors
        if(Math.abs(leftTriggerAxis) == 0.0 && Math.abs(rightTriggerAxis) == 0.0) 
            killMotor()
        // If both triggers are pressed, kill motors to prevent unwanted behavior
        if(Math.abs(leftTriggerAxis) > 0.0 + deadzone && Math.abs(rightTriggerAxis) > 0.0 + deadzone) 
            killMotor()
        
        return false;
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