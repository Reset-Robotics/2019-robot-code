package frc.robot.commands.BallIntake

import org.sertain.command.Command
import frc.robot.subsystems.BallIntake

public class ToggleAutoStop: Command()
{
    override fun execute(): Boolean
    {
        BallIntake.toggleAutoStop()
        return true
    }
}
