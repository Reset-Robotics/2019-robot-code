package frc.robot.commands.Camera

import org.sertain.command.Command
import frc.robot.subsystems.CameraController

public class SetFeed(feed: String): Command()
{
    var localFeed: String = feed;
    override fun execute(): Boolean
    {
        var localCameraController: CameraController = CameraController
        localCameraController.setStream(localFeed)
        
        return true;
    }
}