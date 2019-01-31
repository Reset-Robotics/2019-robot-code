package frc.robot.commands.Arm

import org.sertain.command.Command
import frc.robot.subsystems.Arm
import frc.robot.OI
public class ArmJoystick: Command()
{
    // Make sure we require any necessary objects/classes
    init 
    {
        requires(Arm)
    }


    // Run all our code here
    override fun execute(): Boolean
    {              
        var yDirection: Double = OI().xboxJoystickLeft.getY()

        if (Math.abs(yDirection) < Arm.deadzone) yDirection = 0.0

        Arm.move(yDirection)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Arm.killMotors()
}