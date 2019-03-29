package frc.robot.commands.Arm

import org.sertain.command.Command
import frc.robot.subsystems.Arm
import frc.robot.OI
import frc.robot.data.ArmData


public class ArmJoystick: Command()
{
    var armData: ArmData = ArmData()
    var yDirection: Double = 0.0
    var throttle: Double = 1.0



    // Make sure we require any necessary objects/classes
    init 
    {
        requires(Arm)
    }


    // Run all our code here
    override fun execute(): Boolean
    {   
        throttle= ((OI().joystickRight.getThrottle()*-1)+1)/2             
        yDirection = -OI().xboxController.getRawAxis(1)

        if (Math.abs(yDirection) < .25) yDirection = 0.0

        Arm.move(yDirection)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Arm.killMotors()
}