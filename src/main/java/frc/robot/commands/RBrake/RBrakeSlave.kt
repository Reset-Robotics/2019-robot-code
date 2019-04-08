// Reset Robotics 2019
package frc.robot.commands.RBrake

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.RBrake
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.data.RBrakeData


public class RBrakeSlave : Command()
{
    var rBrakeData: RBrakeData = RBrakeData()
    
    init { requires(RBrake) }

    override fun execute(): Boolean
    {
        // TODO: implement throttle slider
                
        var yDirection: Double = OI().joystickLeft.getY()
        var xDirection: Double = OI().joystickLeft.getX()
        
        var throttle: Double = 1.0 // Replace with slider throttle later

        if (Math.abs(yDirection) < rBrakeData.deadzone)
            yDirection = 0.0;

        if (Math.abs(xDirection) > 0.1)
        {
            RBrake.killMotors()
            RBrake.deployIn()
        }

        RBrake.driveRBrake(yDirection)

        return false;
    }

    // safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = RBrake.killMotors()
}