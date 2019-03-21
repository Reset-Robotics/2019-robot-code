package frc.robot.commands.Camera

import org.sertain.command.Command
import frc.robot.subsystems.CameraController

public class SetFeed(feed: String): Command()
{
    var localFeed: Double = feed;
    override fun execute(): Boolean
    {
        CameraController.setStream(localFeed)
        
        return true;
    }
}