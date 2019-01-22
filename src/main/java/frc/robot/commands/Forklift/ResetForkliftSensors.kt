package frc.robot.commands.Forklift

import org.sertain.command.Command
import frc.robot.subsystems.Forklift

public class ResetElevatorSensor: Command()
{
    init
    {
        requires(Forklift)
    }
    override fun execute(): Boolean
    {
        Forklift.ResetEnconder()
        return true;
    }
}