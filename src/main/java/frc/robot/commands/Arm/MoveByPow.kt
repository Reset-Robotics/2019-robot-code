// Reset Robotics 2019
package frc.robot.commands.Arm

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Arm
// Miscellaneous Imports
import frc.robot.OI


public class MoveByPow(speed: Double): Command()
{
    var speed: Double = 1.0

    override fun execute(): Boolean
    {
        Arm.move(speed)
        
        return true;
    }
}