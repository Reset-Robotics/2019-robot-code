package frc.robot.commands.Elevator

import org.sertain.command.Command
import frc.robot.subsystems.Elevator

public class ResetElevatorSensor : Command()
{
	public fun ResetElevatorSensor() = requires(Elevator)
    
    override fun execute(): Boolean
    {
        Elevator.ResetEncoders()
        Elevator.setElevatorTargetNull()
        return true;
    }
}