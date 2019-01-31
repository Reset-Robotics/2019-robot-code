package frc.robot.commands.Elevator

import org.sertain.command.Command
//import frc.robot.OI
import frc.robot.subsystems.Elevator

public class ElevateToPos(targetPos: String = "Null"): Command ()
{
     init 
    {
        requires(Elevator)
    }
    var localTargetPos= targetPos
    override fun execute(): Boolean
    {
        Elevator.elevatorMM(localTargetPos)
        return true; 
    }
}