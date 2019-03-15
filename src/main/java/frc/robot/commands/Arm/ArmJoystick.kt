package frc.robot.commands.Arm

import org.sertain.command.Command
import frc.robot.subsystems.Arm
import frc.robot.OI
import frc.robot.data.ArmData


public class ArmJoystick: Command()
{
    var armData: ArmData = ArmData()


    // Make sure we require any necessary objects/classes
    init 
    {
        requires(Arm)
    }


    // Run all our code here
    override fun execute(): Boolean
    {   
        var throttle: Double = ((OI().joystickLeft.getThrottle()*-1)+1)/2  
        var yDirection: Double = OI().xboxJoystickLeft.getY()

        if (Math.abs(yDirection) < armData.deadzone) yDirection = 0.0

        Arm.move(yDirection*throttle)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Arm.killMotors()
}