// Reset Robotics 2019
package frc.robot.commands.Camera

// Import Libraries
import org.sertain.command.Command
// Import Subsystems
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