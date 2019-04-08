// Reset Robotics 2019
package frc.robot.commands.Forklift

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Forklift
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.data.ForkliftData


public class ForkliftJoystick : Command ()
{
    val forkliftData: ForkliftData = ForkliftData()
    
    init { requires(Forklift) }

    override fun execute(): Boolean
    {
        var joystickInput: Double = OI().xboxController.getRawAxis(1)
        //var forkliftToggle: Double = OI().
        if (Math.abs(joystickInput) < forkliftData.deadzone)
            joystickInput = 0.0
            
        Forklift.lift(joystickInput)
        
        return false; 
    }
}