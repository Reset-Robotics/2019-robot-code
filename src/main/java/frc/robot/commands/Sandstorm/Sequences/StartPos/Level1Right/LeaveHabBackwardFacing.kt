// Reset Robotics 2019
package frc.robot.commands.Sandstorm.Sequences.StartPos.Level1Right

// Libraries
import org.sertain.command.Command
// Miscellaneous Imports
import frc.robot.commands.Drive.Auto.DriveByTime


public class LeaveHabBackwardFacing : Command()
{
    override fun execute(): Boolean
    {
        DriveByTime(0.0, -1.0, 0.0, 1.0, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}