// Reset Robotics 2019
package frc.robot.commands.Elevator

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Elevator


public class ElevateToPos(targetPos: String = "Null"): Command ()
{
    init { requires(Elevator) }

    var localTargetPos= targetPos

    override fun execute(): Boolean
    {
        Elevator.elevatorMM(localTargetPos)
        
        return true; 
    }
}