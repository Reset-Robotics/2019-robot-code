// Reset Robotics 2019
package frc.robot.commands.Sandstorm.Sequences

// Libraries
import org.sertain.command.Command
// Miscellaneous Imports
import frc.robot.commands.Drive.Auto.DriveByTime


public class AutoDriveTest : Command()
{
    override fun execute(): Boolean
    {
        DriveByTime(0.0, -1.0, 0.0, 0.4, 2.0) // xdir, ydir, angle, throttle, time
        
        return true;
    }
}