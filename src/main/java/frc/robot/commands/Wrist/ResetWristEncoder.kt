// Reset Robotics 2019
package frc.robot.commands.Wrist

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Wrist
// Miscellaneous Imports
import frc.robot.OI

public class ResetWristEncoders(position: String): Command()
{
    override fun execute(): Boolean
    {
        Wrist.ResetEncoder()

        return true;
    }
}