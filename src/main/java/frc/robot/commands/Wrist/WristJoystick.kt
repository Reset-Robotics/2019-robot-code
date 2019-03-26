package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI
import frc.robot.data.WristData


public class WristJoystick : Command()
{
    val wristData: WristData = WristData()
    // Make sure we require any necessary objects/classes
    init 
    {
        requires(Wrist)
    }


    // Run all our code here
    override fun execute(): Boolean
    {   
        var throttle: Double = ((OI().joystickRight.getThrottle()*-1)+1)/2            
        var yDirection: Double = OI().xboxController.getRawAxis(5)

        if (Math.abs(yDirection) < wristData.deadzone) yDirection = 0.0
        else 
        {
        Wrist.move(yDirection*throttle)
        }

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Wrist.killMotors()
}