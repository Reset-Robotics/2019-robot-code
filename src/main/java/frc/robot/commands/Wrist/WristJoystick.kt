// Reset Robotics 2019
package frc.robot.commands.Wrist

// Libraries
import org.sertain.command.Command
// Subsystems
import frc.robot.subsystems.Wrist
// Miscellaneous Imports
import frc.robot.OI
import frc.robot.data.WristData


public class WristJoystick : Command()
{
    val wristData: WristData = WristData()

    init { requires(Wrist) }

    override fun execute(): Boolean
    {   
        var throttle: Double = (((OI().joystickRight.getThrottle()*-1)+1)/2)
        var yDirection: Double = OI().xboxController.getRawAxis(5)

        if (Math.abs(yDirection) < wristData.deadzone) 
            yDirection = 0.0
        else 
            Wrist.move(yDirection*throttle)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Wrist.killMotors()
}