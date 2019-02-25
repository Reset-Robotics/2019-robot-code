package frc.robot.commands.Sandstorm.Sequences.StartPos.Level2Right.RocketRight.Level2Right

import org.sertain.command.Command
import frc.robot.commands.Drive.Auto.DriveByTime

public class 2Panel : Command()
{
    override fun execute(): Boolean
    {
        DriveByTime(0.0, -1.0, 0.0, 0.4, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}