// Reset Robotics 2019
package frc.robot.commands.Wrist

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Wrist
// Miscellaneous Imports
import frc.robot.OI

public class MoveByPow(pow: Double): Command()
{
    var speed: Double = pow;
    var throttle : Double = .33

    override fun execute(): Boolean
    {
        Wrist.move(speed * throttle)
        
        return true;
    }
}