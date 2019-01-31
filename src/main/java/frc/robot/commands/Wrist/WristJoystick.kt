package frc.robot.commands.Wrist

import org.sertain.command.Command
import frc.robot.subsystems.Wrist
import frc.robot.OI


public class WristJoystick : Command()
{
    // Make sure we require any necessary objects/classes
    init 
    {
        requires(Wrist)
    }


    // Run all our code here
    override fun execute(): Boolean
    {              
        var yDirection: Double = OI().xboxJoystickRight.getY()

        if (Math.abs(yDirection) < Wrist.deadzone) yDirection = 0.0

        Wrist.move(yDirection)

        return false;
    }

    // Safely stop motors if the command is interrupted or destroyed
    override fun onDestroy() = Wrist.killMotors()
}