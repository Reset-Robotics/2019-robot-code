package frc.robot.commands.Elevator

import org.sertain.command.Command
import frc.robot.OI
import frc.robot.subsystems.Elevator

public class ElevateToPos(targetPos: String): Command ()
{
     init 
    {
        requires(Elevator)
    }
    override fun execute(): Boolean
    {
        var currentstate: Boolean = Elevator.whatIsElevatorState()
        Elevator.elevatorMM(!currentstate) 
        return true; 
    }
}