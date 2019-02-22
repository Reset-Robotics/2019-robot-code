package frc.robot.commands.Elevator

import org.sertain.command.Command
import frc.robot.subsystems.Elevator

public class ResetElevatorSensor: Command()
{
    override fun execute(): Boolean
    {
        Elevator.ResetEncoders()
        return true;
    }
}