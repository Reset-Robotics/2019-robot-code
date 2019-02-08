package frc.robot.commands.Sandstorm.Sequences

import org.sertain.command.CommandGroup
import frc.robot.commands.Drive.Auto.DriveByTime

public class AutoDriveTest : CommandGroup()
{
    override fun execute(): Boolean
    {
        DriveByTime(0.0, -1.0, 0.0, 1.0, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}