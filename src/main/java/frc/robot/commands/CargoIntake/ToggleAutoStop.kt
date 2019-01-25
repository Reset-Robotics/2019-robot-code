package frc.robot.commands.CargoIntake

import org.sertain.command.Command
import frc.robot.subsystems.CargoIntake

public class ToggleAutoStop: Command()
{
    override fun execute(): Boolean
    {
        CargoIntake.toggleAutoStop()
        return true
    }
}
