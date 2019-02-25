package frc.robot.commands.Sandstorm.Sequences.StartPos.Level1Right

import org.sertain.command.Command
import frc.robot.commands.Drive.Auto.DriveByTime

public class LeaveHabForwardFacing : Command()
{
    override fun execute(): Boolean
    {
        DriveByTime(0.0, 1.0, 0.0, 1.0, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}