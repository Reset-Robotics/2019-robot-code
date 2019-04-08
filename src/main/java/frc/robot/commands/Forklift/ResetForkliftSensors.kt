// Reset Robotics 2019
package frc.robot.commands.Forklift

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Forklift


public class ResetForkliftSensor: Command()
{
    override fun execute(): Boolean
    {
        Forklift.ResetEncoders()

        return true;
    }
}