// Reset Robotics 2019
package frc.robot.commands.Wrist

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Wrist
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.data.WristData


public class MoveToPos(position: String): Command()
{
    var localPos = position

    override fun execute(): Boolean
    {
        Wrist.wristMotionMagic(localPos)
        
        return true;
    }
}