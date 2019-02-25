package frc.robot.commands.Sandstorm.Sequences.StartPos.Level1Right.RocketLeft.Level2Left

import org.sertain.command.Command
import frc.robot.commands.Drive.Auto.DriveByTime

public class 1Panel1Cargo : Command()
{
    override fun execute(): Boolean
    {
        DriveByTime(0.0, -1.0, 0.0, 0.4, 2.0) //xdir, ydir, angle, throttle, time
        
        return true;
    }
}