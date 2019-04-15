// Reset Robotics 2019
package frc.robot.commands.Sandstorm.Sequences.StartPos.Level2Right

// Libraries
import org.sertain.command.Command
// Miscellaneous Imports
import frc.robot.commands.Drive.Auto.DriveByTime
import frc.robot.commands.RBrake.Deploy


public class LeaveHab : Command()
{
    override fun execute(): Boolean
    {
        Deploy()
        DriveByTime(0.0, -1.0, 0.0, 1.0, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}