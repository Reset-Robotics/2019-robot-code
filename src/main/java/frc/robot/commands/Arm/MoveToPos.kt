// Reset Robotics 2019
package frc.robot.commands.Arm

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Arm
// Miscellaneous Imports
import frc.robot.OI


public class MoveToPos(position: String): Command()
{
    var localPos = position
    
    override fun execute(): Boolean
    {
        Arm.armMotionMagic(localPos)
        
        return true;
    }
}