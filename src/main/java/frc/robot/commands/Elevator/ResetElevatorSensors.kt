// Reset Robotics 2019
package frc.robot.commands.Elevator

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Elevator


public class ResetElevatorSensor : Command()
{
    init { requires(Elevator) }
    
    override fun execute(): Boolean
    {
        Elevator.ResetEncoders()
        Elevator.setElevatorTargetNull()
        
        return true;
    }
}