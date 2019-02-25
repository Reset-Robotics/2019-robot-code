package frc.robot.commands.Sandstorm.Sequences.StartPos.Level2Left

import org.sertain.command.Command
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