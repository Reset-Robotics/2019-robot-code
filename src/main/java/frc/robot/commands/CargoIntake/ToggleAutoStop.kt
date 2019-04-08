// Reset Robotics 2019
package frc.robot.commands.CargoIntake

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.CargoIntake


public class ToggleAutoStop: Command()
{
    override fun execute(): Boolean
    {
        CargoIntake.toggleAutoStop()

        return true
    }
}
