package frc.robot.commands.Sandstorm.Sequences

import org.sertain.command.Command
import frc.robot.commands.Drive.Auto.DriveByTime

public class AutoDriveTest : Command()
{
    override fun execute(): Boolean
    {
        DriveByTime(1.0, 0.0, 0.0, 0.2, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}